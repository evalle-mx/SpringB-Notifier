package net.tce.admin.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.tce.admin.service.DocumentoClasificacionService;
import net.tce.admin.service.RestJsonService;
import net.tce.app.exception.SystemTCEException;
import net.tce.dao.DocumentoClasificacionDao;
import net.tce.dto.DocumentoClasificacionDto;
import net.tce.dto.MensajeDto;
import net.tce.model.Area;
import net.tce.model.DocumentoClasificacion;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("documentoClasificacionService")
public class DocumentoClasificacionServiceImpl implements DocumentoClasificacionService{

	Logger log4j = Logger.getLogger( DocumentoClasificacionServiceImpl.class );
	
	
	@Autowired
	private RestJsonService restJsonService;
	
	@Autowired
	private DocumentoClasificacionDao docClasificacionDao;

	@Inject
	private ConversionService converter;
	
	/**
	 * Se manda a llamar al servico externo de seedClassifier
	 * @param inputJson, mensaje json
	 * @return 
	 * @throws EException 
	 */
	public Object loadTokens(String inputJson) throws Exception{
		log4j.debug("<DocumentoClasificacionServiceImpl>loadTokens ...");
		Object objUDA = null;
		try {
			objUDA = restJsonService.serviceRJOperNoStruc(inputJson,Constante.OPERATIVE_SEED_CLASSIFIER);
		} catch (Exception e) {
	    	log4j.error(" Error al llamar al servicio de la App OperationalTCE: "+e);
	    	e.printStackTrace();
	    	 throw new SystemTCEException("Error al llamar al servicio de la App OperationalTCE: "+e);		
		}
		return objUDA;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Transactional(readOnly=true)
	public Object get(DocumentoClasificacionDto docClasificacionDto) throws Exception {
		log4j.debug("<DocumentoClasificacionService> get ");
		docClasificacionDto = validaDto(docClasificacionDto, Constante.F_GET);
				
		if(docClasificacionDto.getCode() == null && docClasificacionDto.getMessages() == null){
			HashMap<String, Object> filtros = new HashMap<String, Object>();
			if(docClasificacionDto.getEstatusClasificacion()!=null){
				filtros.put("estatusClasificacion", Integer.parseInt(docClasificacionDto.getEstatusClasificacion()));
			}
			if(docClasificacionDto.getIdArea()!=null){
				filtros.put("area.idArea", Long.parseLong(docClasificacionDto.getIdArea()));
			}
			
			
			ArrayList<String> orderby=new ArrayList<String>();			
			orderby.add(0,"idTextoClasificacion");			
			
			filtros.put(Constante.SQL_ORDERBY,orderby );
			
			//*/
			List<DocumentoClasificacion> lsDocsClasifica = docClasificacionDao.getByFilters(filtros);
			
			if(lsDocsClasifica !=null && lsDocsClasifica.size()>=0){
				log4j.debug("<get> Encontrad@s " + lsDocsClasifica.size() + " Documentos clasificados ");
				List<DocumentoClasificacionDto> lsDocsClasifDto = new LinkedList<DocumentoClasificacionDto>();
				Iterator<DocumentoClasificacion> itDocsClasif = lsDocsClasifica.iterator();
				DocumentoClasificacionDto dto = null;
				while(itDocsClasif.hasNext()){
					DocumentoClasificacion docClasificacion = itDocsClasif.next();
					dto = converter.convert(docClasificacion, DocumentoClasificacionDto.class );
//					log4j.debug("docClasificacion.idTipoDocumento(): " + docClasificacion.getTipoDocumento().getIdTipoDocumento() );
					if(docClasificacion.getPerfil()!=null 
//							//&& docClasificacion.getTipoDocumento().getIdTipoDocumento() == 2
							){
						log4j.debug("docClasificacion.idPerfil: " + docClasificacion.getPerfil().getIdPerfil());
						log4j.debug("docClasificacion.idPosicion: " + docClasificacion.getPosicion().getIdPosicion());
//						dto.setIdPosicion(getIdPosicion(docClasificacion.getPerfil().getIdPerfil()));
					}					
					lsDocsClasifDto.add(dto);
				}//*/
			/*
			List<DocumentoClasificacionDto> lsDocsClasifDto = docClasificacionDao.getDtos();
			if(lsDocsClasifDto !=null && lsDocsClasifDto.size()>=0){
				log4j.debug("Encontrad@s " + lsDocsClasifDto.size() + " Documentos (Dto's) clasificados ");//*/
				return lsDocsClasifDto; 
			}else {
				log4j.debug("lsDocsClasifica es null o cero");
				docClasificacionDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_003,Mensaje.SERVICE_TYPE_WARNING,
						Mensaje.MSG_WARNING)));
			}
		}
		else{
			if(docClasificacionDto.getMessages() == null){
				docClasificacionDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						docClasificacionDto.getCode(), docClasificacionDto.getType(),
						docClasificacionDto.getMessage())));
			}			
			else{
				docClasificacionDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
			}
		}
		return docClasificacionDto.getMessages();
	}
	
	/**
	 * METODO CRUD para actualizar un registro de DocumentoClasificacion
	 * @param docClasificacionDto
	 * @return
	 */
	private DocumentoClasificacionDto updateDto(DocumentoClasificacionDto docClasificacionDto){
		docClasificacionDto = validaDto(docClasificacionDto, Constante.F_UPDATE);
		
		if(docClasificacionDto.getCode() == null && docClasificacionDto.getMessages() == null){
			String iddc = docClasificacionDto.getIdDocumentoClasificacion();
			log4j.debug("<updateDto>leyendo doc por Id");
			DocumentoClasificacion doc = 
					docClasificacionDao.read(Long.parseLong(docClasificacionDto.getIdDocumentoClasificacion()));
			if(doc!=null){
				doc.setIdDocumentoClasificacion(Long.parseLong(iddc) );//****
				
				if(docClasificacionDto.getIdArea()!=null){
					doc.setArea(new Area(Long.parseLong(docClasificacionDto.getIdArea()), null, true));
				}
				/* if(docClasificacionDto.getCategoriaPrimaria()!=null){
					doc.setCategoriaPrimaria(docClasificacionDto.getCategoriaPrimaria());
				}*/
				if(docClasificacionDto.getCategoria()!=null){
					doc.setAreasConfirmadas(docClasificacionDto.getCategoria());
				}
				
				if(docClasificacionDto.getEstatusClasificacion()!=null){
					doc.setEstatusClasificacion(Integer.parseInt(docClasificacionDto.getEstatusClasificacion()));
				}
					
				log4j.debug("actualizar: " + doc.toString() );
				log4j.debug(doc.getIdDocumentoClasificacion()+": " 
						+ (doc.getArea()!=null?doc.getArea().getIdArea():doc.getArea()) + ", "
						+ doc.getCategoria() + " (" + doc.getEstatusClasificacion() + ")");
				
				docClasificacionDao.update(doc);
				
				log4j.debug("Se actualizo elemento solicitado (updateDto)");
			}else{
				/* Documento no existe */
				docClasificacionDto.setCode(Mensaje.SERVICE_CODE_002);
				docClasificacionDto.setType(Mensaje.SERVICE_TYPE_FATAL);
				docClasificacionDto.setMessage("("+iddc+") "+Mensaje.MSG_ERROR_EMPTY);
			}
			
			return docClasificacionDto;
		}else{
			return docClasificacionDto;
		}
	}
	
	/**
	 * Servicio para Clasificar (Confirmar preClasificacion) de manera automatica, con restricciones opcionales
	 * @param docClasificacionDto
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String classifyByLot(DocumentoClasificacionDto docClasificacionDto) throws Exception {
		log4j.debug("<classifyByLot> idEmpresaConf: " + docClasificacionDto.getIdEmpresaConf()
				+ " estatusClasificacion (tope): " + docClasificacionDto.getEstatusClasificacion()
				+ " aplicaModelo: " + docClasificacionDto.getAplicaModelo()
				);
		//0. validar el dto,(si estatusClas es != null: validar que sea numero y tipo valido; masivoModelo != null)
		String topeClasificacion = docClasificacionDto.getEstatusClasificacion(); //A partir de este tope, autoClasificar segun matriz
		String tipoDoc = docClasificacionDto.getIdTipoDocumento();
		String idEmpresaConf = docClasificacionDto.getIdEmpresaConf();
		boolean aplicaModelo = false; //por default, no se manda a modelo
		//1. Buscar los documentos clasificados (GET)
		
		docClasificacionDto = new DocumentoClasificacionDto();
		docClasificacionDto.setIdEmpresaConf( idEmpresaConf );
		docClasificacionDto.setIdTipoDocumento(tipoDoc);
		
		Object getResp = get(docClasificacionDto);
		
		if(getResp instanceof List<?>){
			if(docClasificacionDto.getAplicaModelo()!=null && docClasificacionDto.getAplicaModelo().equals("1")){
				aplicaModelo = true;	//Falso en cualquier otro caso (null, 0 o ?)
			}
			
			List<DocumentoClasificacionDto> lsDocsClasifDto = (List<DocumentoClasificacionDto>)getResp;
			int totalDocs = lsDocsClasifDto.size();
			int totalVerificados = 0;
			String idError = null;
			Iterator<DocumentoClasificacionDto> itDocsClasif = lsDocsClasifDto.iterator();
			
			while (itDocsClasif.hasNext()) {
				DocumentoClasificacionDto dtoClass = itDocsClasif.next();
				setAreaByMat(dtoClass, topeClasificacion);
				if(dtoClass.getIdArea()!=null){
					dtoClass.setIdEmpresaConf(idEmpresaConf);
					idError = dtoClass.getIdDocumentoClasificacion();	//Se pierde en caso de error en filtros
					dtoClass.setEstatusClasificacion( String.valueOf(aplicaModelo ? 
											Constante.ESTATUS_CLAS_VERIFICADO_MODELO:
											Constante.ESTATUS_CLAS_VERIFICADO_NOMODELO) );
//					log4j.debug("Dto.getId: " +  );
					dtoClass= updateDto(dtoClass);
					if(dtoClass.getCode()!=null && dtoClass.getType()!=null){
						log4j.debug("Dto.getId: " + dtoClass.getIdDocumentoClasificacion() );
						docClasificacionDto.setMessages(UtilsTCE.getJsonMessageGson(docClasificacionDto.getMessages(), 
																		new MensajeDto("idDocumentoClasificacion",idError,
																		dtoClass.getCode(),dtoClass.getType(),
																		dtoClass.getMessage())));
					}
					else{  totalVerificados++;}
				}
			}
			docClasificacionDto.setMessages(UtilsTCE.getJsonMessageGson(docClasificacionDto.getMessages(), new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_004,Mensaje.SERVICE_TYPE_INFORMATION,
					"Se actualizarón "+totalVerificados +" de " + totalDocs +" encontrados ")));
		}else{
			log4j.debug("Error, No se encontraron documentos clasificados");
			docClasificacionDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
					Mensaje.MSG_ERROR_EMPTY)));
		}
		
		return docClasificacionDto.getMessages();
	}
	
	/**
	 * Procesa un Dto en base a su propiedad Categorias, para determinar/Confirmar automaticamente, cual es el area con mayor
	 * afinidad.
	 * @param docClassify Dto de Documento Clasificación
	 * @param topeClasificacion Criterio minimo para hacer la autoConfirmación (Valor de clasificación Solr).
	 */
	private void setAreaByMat(DocumentoClasificacionDto docClassify, String topeClasificacion) {
		log4j.debug("<setAreaByMat> docClassify.id:" + docClassify.getIdDocumentoClasificacion() 
				+ ", TopeCLas: "+ topeClasificacion + ", docClassify.categoriasAnalisis" +docClassify.getCategoriasAnalisis() );
		Integer tope = new Integer("1"); //Por default, clasifica los que esten preclasificados con certidumbre 1 o 2
		String areaFinal = null;
		List<String> lsData = Arrays.asList(docClassify.getCategoriasAnalisis().replace("[", "").replace("]", "").split("\\s*,\\s*"));
		if(lsData!=null && !lsData.isEmpty()){
			//Se separo en tokens, validar si es un arreglo de triadas
			int mod = lsData.size()%3;
			int dtIndx = 0;
//			log4j.debug("mod: " + mod);
			if(mod==0){
				if(topeClasificacion!=null){
					tope = Integer.parseInt(topeClasificacion);
				}
				String area;
				BigDecimal bdCalifica;
				Integer preCla;
				List<ClasificacionVo> lsClasVo = new ArrayList<ClasificacionVo>();
				ClasificacionVo vo = null; 
				//Se iteran para separarla en objetos de Triada (area, calificacion, preClasificacion)
				Iterator<String> itSt = lsData.iterator();
				while(itSt.hasNext()){
					if(dtIndx == 0){
						area = itSt.next();
						vo = this.new ClasificacionVo(area);				
						dtIndx++;
					}else if(dtIndx==1){
						bdCalifica = new BigDecimal(itSt.next());
						vo.setBdCalifica(bdCalifica);
						dtIndx++;
					}else {
						preCla = Integer.parseInt(itSt.next());
						vo.setPreCla(preCla);						
						if(preCla >= tope ){
							lsClasVo.add(vo);
						}
						area= null;
						bdCalifica = null;
						preCla = null;
						vo = null;
						dtIndx=0;
					}
				}
				
				//Una vez en objetosTriada, se busca las clasificaciones altas a bajas (2,1,0) hasta el limite
				if(!lsClasVo.isEmpty()){
					ClasificacionVo voTmp;
					if(lsClasVo.size()==1){
						voTmp = lsClasVo.get(0);
					}else{
						Iterator<ClasificacionVo> itVo = lsClasVo.iterator();
						voTmp = this.new ClasificacionVo(null, new BigDecimal("0"), new Integer("-1"));
						while(itVo.hasNext()){
							voTmp.setBigger( itVo.next() );
						}
					}
					areaFinal = voTmp.getArea();
					docClassify.setIdArea(areaFinal);
					log4j.debug("Se obtuvo area para " +docClassify.getIdDocumentoClasificacion());
				}
//				else{ log4j.error("<setAreaByMat> El arreglo no contiene valores preclasificados aceptables (" + tope + ")");}
			}
//			else{  log4j.error("<setAreaByMat> NO es un arreglo de tercias valido");  }
			
		}
		else{  log4j.error("<setAreaByMat> NO es un arreglo valido");}
	}
	
	/**
	 * Data validations for required parameters
	 * @param pruebaDto
	 * @param funcion
	 * @return
	 */
	private DocumentoClasificacionDto validaDto (DocumentoClasificacionDto docClassDto, int funcion){
		boolean error = false;
		
		if(docClassDto==null){
			error = true; 
			log4j.error("<validaDto> docClasificacionDto es null");
		}else{
			if(docClassDto.getIdEmpresaConf()== null){
				error = true; 
				log4j.error("<validaDto> idEmpresaConf es null");
			}else{
				if(funcion == Constante.F_READ){
					if(docClassDto.getIdDocumentoClasificacion()==null){
						error =true;
						log4j.error("<validaDto> IdDocumentoClasificacion es null");
					}
				}
				if(funcion == Constante.F_CREATE){
					if(docClassDto.getIdArea()==null){
						error =true;
						log4j.error("<validaDto> idArea es null");
					}
					if(docClassDto.getIdTextoClasificacion()==null){
						error =true;
						log4j.error("<validaDto> IdentificadorDoc es null");
					}
					if(docClassDto.getEstatusClasificacion()==null){
						error =true;
						log4j.error("<validaDto> EstatusClasificacion es null");
					}
				}
				if(funcion == Constante.F_UPDATE){
					if(docClassDto.getIdDocumentoClasificacion()==null){
						error =true;
						log4j.error("<validaDto> IdPosicionPrueba es null");
					}
					if(docClassDto.getIdArea()==null && docClassDto.getCategoria()==null){
						error=true;
						log4j.error("<validaDto> Area y Categoria son null (uno es requerido)");
					}
					if(docClassDto.getIdArea()!=null){
						if(! (UtilsTCE.isPositiveLong(docClassDto.getIdArea()) ) ){
							error=true;
							log4j.error("<validaDto> Area es invalida");
						}
					}
				}
			}
		} /* Validation segment ends */
		
		if(error){
			docClassDto=new DocumentoClasificacionDto();
			docClassDto.setMessage(Mensaje.MSG_ERROR);
			docClassDto.setType(Mensaje.SERVICE_TYPE_ERROR);
			docClassDto.setCode(Mensaje.SERVICE_CODE_006);
		}
		return docClassDto;
	}
	
	class ClasificacionVo
	{
		private String area;
		   private BigDecimal bdCalifica;
		   private Integer preCla;
		   
		   ClasificacionVo(String area) {
			   this.area = area;
		   }
		   
		   ClasificacionVo (String area,  BigDecimal bdCalifica, Integer preCla)
		   {
		      this.area = area;
		      this.bdCalifica = bdCalifica;
		      this.preCla = preCla;
		   }
		   
		   public void setBigger(ClasificacionVo vo){
			   if(vo!=null && this.bdCalifica!=null && this.preCla!=null){
				   if(vo.preCla!=null && vo.preCla>=this.preCla){
					   if(vo.bdCalifica!=null && vo.bdCalifica.compareTo(this.bdCalifica) == 1 ){
						   this.area = vo.getArea();
						   this.bdCalifica = vo.getBdCalifica();
						   this.preCla = vo.getPreCla();
					   }
				   }
			   }
		   }

			public String getArea() {
				return area;
			}
		
			public void setArea(String area) {
				this.area = area;
			}
		
			public BigDecimal getBdCalifica() {
				return bdCalifica;
			}
		
			public void setBdCalifica(BigDecimal bdCalifica) {
				this.bdCalifica = bdCalifica;
			}
		
			public Integer getPreCla() {
				return preCla;
			}
		
			public void setPreCla(Integer preCla) {
				this.preCla = preCla;
			}
			
			public String toString(){
				return "[area:" + this.getArea() + ", bdCalifica:"+ this.getBdCalifica() + ", preCla:"+this.getPreCla() + "]"; 
			}
	   
	}
}
