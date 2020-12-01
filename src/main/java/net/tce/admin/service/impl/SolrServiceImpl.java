package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import net.tce.admin.service.AdminService;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.admin.service.RestJsonService;
import net.tce.admin.service.SolrService;
import net.tce.app.exception.SystemTCEException;
import net.tce.cache.ParametrosCache;
import net.tce.cache.ThreadExceptionCache;
import net.tce.dao.DocumentoClasificacionDao;
import net.tce.dao.EmpresaDao;
/*import net.tce.dao.PerfilDao;
import net.tce.dao.PerfilPosicionDao;*/
import net.tce.dao.PersonaDao;
import net.tce.dao.PosicionDao;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.MensajeDto;
import net.tce.model.CertificacionEmpresa;
import net.tce.model.DocumentoClasificacion;
import net.tce.model.Empresa;
import net.tce.model.ExperienciaLaboral;
import net.tce.model.PerfilTextoNgram;
import net.tce.model.Persona;
import net.tce.model.Posicion;
import net.tce.model.PerfilPosicion;
import net.tce.solr.document.SolrDocTopics;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import net.tce.util.comparator.ExpLabDateComparator;

/**
 * Clase encargada de realizar servicios particulares en el Servidor Solr
 * @author Evalle
 *
 */
@Service("solrService")
public class SolrServiceImpl implements SolrService {
	
	Logger log4j = Logger.getLogger( SolrServiceImpl.class );
	
	private @Value("${solr.tipodoc1.persona}")	Integer docTypePersona;
	private @Value("${solr.tipodoc1.posicion}")	Integer docTypePosicion;
	private @Value("${solr.tipodoc1.empresa}")	Integer docTypeEmpresa;
	private StringBuilder sb ;
	private EmpresaParametroDto empresaParametroDto;
	private MensajeDto mensajeDto;
	
	@Autowired
	private PersonaDao personaDao;
	
	@Autowired
	private EmpresaDao empresaDao;
	
	/*@Autowired
	private PerfilDao perfilDao;*/
	
	@Autowired
	private PosicionDao posicionDao;

	/*@Autowired
	private PerfilPosicionDao perfilPosicionDao;	*/
	
	@Autowired
	private DocumentoClasificacionDao documentoClasificacionDao;

	
	@Autowired
	private SimpleAsyncTaskExecutor asynchTaskExecutor;
	
	
	@Autowired
	private RestJsonService restJsonService;
	
	@Autowired
	private EmpresaParametroService empresaParametroService;	
	
	@Autowired
	private AdminService adminService;
	
	@Inject
	Gson gson;
	
	/**
	 * Se manda a llamar al servicio de adicion de documentos a Solr
	 * @param pojoEntidad, documento a solr
	 * @param idSolr, identificador del documento en solr
	 * @return respuesta
	 */
	public  String writeOnSolrServer(Object pojoEntidad, String idSolr) throws Exception{
		String res = "-1";
		List<SolrDocTopics> lsResult;
		try {
			log4j.debug("<writeOnSolrServer>  idTextoClasificacion: " + idSolr );
			//Si la entidad no genero idSolr [TextoClasificacion], obtener por secuencia
			if(idSolr==null){
				log4j.debug("<writeOnSolrServer> No contiene idTextoClasificacion, se genera nuevo en base a persona Dao");
				idSolr = String.valueOf( personaDao.generaSecuencia(Constante.SECUENCIA_BD_CORECLASS));
				log4j.debug("<writeOnSolrServer> idSolr: "+idSolr);
			}
			SolrDocTopics solrDoc = convertToSolrDoc(pojoEntidad, idSolr);
			if(solrDoc==null){
				throw new SystemTCEException("El tipo de entidad no esta determinada en Solr", new NullPointerException());
			}
			//NumeroLimiteCategory
			solrDoc.setNumLimitCategory(getNumeroLimiteCategory());
			
			log4j.debug("<writeOnSolrServer> >>>>>>> ENVIANDO OBJETO JSON A OPERATIONAL_NO_STRUCTURED, SOLR CON ID: "+idSolr );
			lsResult = (List<SolrDocTopics>) restJsonService.serviceRJOperNoStruc(gson.toJson(solrDoc), 
															Constante.OPERATIVE_SAVECLASSDOC_ENDPOINT);
			log4j.debug("<writeOnSolrServer> Despues serviceRJOperNoStruc result.size :" + lsResult.size());
			if (lsResult == null || lsResult.size() == 0){
				log4j.debug("<writeOnSolrServer> Exito en la llamada del servicio");
				res = idSolr;						
			}else{
				log4j.debug("<writeOnSolrServer> Error en OPERATIONAL_NO_STRUCTURED type:"+lsResult.get(0).getType()+
						" getMessage:"+lsResult.get(0).getMessage());
				if(lsResult.get(0).getType().equals(Mensaje.SERVICE_TYPE_FATAL) ){
					throw new SystemTCEException(lsResult.get(0).getMessage());
				}
			}
		} catch (Exception e) {					
			e.printStackTrace();
			log4j.fatal("<SolrService> Error al insertar en Solr", e);
			throw new SystemTCEException("Error al insertar en Solr"+ e);
		}
		log4j.debug("Agregado en Solr? res="+res);
		
		return res;
	}
	
	
	/**
	 * Obtiene el parametro del número límite de categorias
	 * @param Ninguno
	 * @return Valor del parametro numeroLimiteCategory
	 */			
	@SuppressWarnings("unchecked")
	private String getNumeroLimiteCategory()throws Exception{
		empresaParametroDto=new EmpresaParametroDto();
		empresaParametroDto.setIdEmpresaConf(Constante.IDCONF_NULL);
		empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_APLICACION_GENERAL);
		empresaParametroDto.setContexto(Constante.CONTEXT_PARAM_NUM_LIMITE_CATEGORY);	
		Object object=empresaParametroService.get(empresaParametroDto,false);
		if(object instanceof EmpresaParametroDto ){
			return Constante.DEFAULT_NUM_LIMITE_CATEGORY;
		}else{
			List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
			log4j.debug("<numeroLimiteCategory> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
			return lsEmpresaParametroDto.get(0).getValor();
		}	
	}
	
	/**
	 * En base a Pojo-model, procesa los datos para generar un documento de SolrDoc
	 * @param entidad
	 * @param idSolr
	 * @return
	 */
	private SolrDocTopics convertToSolrDoc(Object entidad, String idSolr ) throws Exception{
		log4j.debug("<convertToSolrDoc>....");
		SolrDocTopics solrDoc = null;
		StringBuilder stContenido = null;
		List<ExperienciaLaboral> lsExperienciaLaboral;
		
		/* CASO PERSONA */
		 if(entidad instanceof Persona){
				Persona persona = (Persona)entidad;
				solrDoc = new SolrDocTopics();
				solrDoc.setId(idSolr);
				solrDoc.setDoctype(docTypePersona);
				solrDoc.setModelversion(ParametrosCache.getChmGeneral(Constante.PARAM_GENERAL_CURRRENTMODEL));
								
				stContenido = new StringBuilder();
				
				if(persona.getExperienciaLaborals() != null && persona.getExperienciaLaborals().size() > 0){
					log4j.debug("<convertToSolrDoc> getIdPersona: " + persona.getIdPersona());
					
					lsExperienciaLaboral=new ArrayList<ExperienciaLaboral>();
					lsExperienciaLaboral.addAll(persona.getExperienciaLaborals());
					
					//Se ordena por fecha de inicio, del mas actual al mas viejo
					Collections.sort(lsExperienciaLaboral, new ExpLabDateComparator());  
					
					log4j.debug("<convertToSolrDoc> despues de ordenar -> lsExperienciaLaboral: " +lsExperienciaLaboral.size());

					
					Iterator<ExperienciaLaboral> itExperienciaLaboral= lsExperienciaLaboral.iterator();
					while(itExperienciaLaboral.hasNext()){
						ExperienciaLaboral experiencia = itExperienciaLaboral.next();
						
						if(experiencia.getFechaInicio() == null){
							experiencia.setFechaInicio(DateUtily.creaFecha(
														String.valueOf(experiencia.getAnioInicio()), 
														String.valueOf(experiencia.getMesByMesInicio().getIdMes()), 
														String.valueOf(experiencia.getDiaInicio())));
						}
						
						log4j.debug("<convertToSolrDoc>  getIdExperienciaLaboral="+experiencia.getIdExperienciaLaboral()+
								" getFechaInicio:"+experiencia.getFechaInicio()+
								" tiempo_experiencia="+DateUtily.calculaEdad(experiencia.getFechaInicio())+
								" stContenido="+stContenido.length() );
						
						//Si la fecha es nula
					/*	if(experiencia.getFechaInicio() == null){
							experiencia.setFechaInicio(
									DateUtily.creaFecha(String.valueOf(experiencia.getAnioInicio()), 
														String.valueOf(experiencia.getMesByMesInicio().getIdMes()), 
														String.valueOf(experiencia.getDiaInicio())));
						}*/
						
						
						//Almenos se toma el texto de uno
						if(stContenido.length() == 0){
							 log4j.debug("<convertToSolrDoc>  se adiciona el primero ");
							stContenido.append(experiencia.getPuesto() !=null ? experiencia.getPuesto()
								.concat(" : "):"")
							.append(experiencia.getTexto() != null ? experiencia.getTexto()
								.concat(Constante.SOLR_CONTENTSEPARATOR):"");
						}else{
							
							//Se aplica el filtro de maximo de años de experiencia
							 if(DateUtily.calculaEdad(experiencia.getFechaInicio()).byteValue() <= 
								getMaxExperiencia().byteValue()){
								 
								 log4j.debug("<convertToSolrDoc>  se adiciona por < años_max ");
								 
								 stContenido.append(experiencia.getPuesto() !=null ? experiencia.getPuesto()
											.concat(" : "):"")
										.append(experiencia.getTexto() != null ? experiencia.getTexto()
											.concat(Constante.SOLR_CONTENTSEPARATOR):"");
							 }
						}
					}
					stContenido.append(" | ");
					
					
				}
				/*if(persona.getEscolaridads() != null && persona.getEscolaridads().size()>0){
					Iterator<Escolaridad> itEscolaridad= persona.getEscolaridads().iterator();
					log4j.debug("<convertToSolrDoc> Extrayendo datos de escolaridad (Titulo & texto) de " + persona.getEscolaridads().size() + " escolaridades ");
					while(itEscolaridad.hasNext()){
						Escolaridad escolaridad = itEscolaridad.next();
						stContenido.append(escolaridad.getTitulo()!=null?escolaridad.getTitulo()
								.concat(" : "):"");	//.concat(Constante.SOLR_CONTENTSEPARATOR):"");
						stContenido.append(escolaridad.getTexto()!=null?escolaridad.getTexto()
								.concat(Constante.SOLR_CONTENTSEPARATOR):"");
					}
				}
				//  JULIO 2015: SE AGREGA HABILIDADES AL DOCUMENTO EN SOLR 
				if(persona.getHabilidads() != null && persona.getHabilidads().size()>0){
					Iterator<Habilidad> itHabilidad= persona.getHabilidads().iterator();
					log4j.debug("<convertToSolrDoc> Extrayendo datos de Habilidad ( descripcion): " + persona.getHabilidads().size());
					while(itHabilidad.hasNext()){
						Habilidad habilidad = itHabilidad.next();
						stContenido.append(habilidad.getDescripcion()!=null?habilidad.getDescripcion()
								.concat(Constante.SOLR_CONTENTSEPARATOR):"");
					}
				}*/
				solrDoc.setContent(stContenido.toString());
			}/* fin de if Persona */
		 /* CASO EMPRESA */
		 else if(entidad instanceof Empresa){ 
				Empresa empresa = (Empresa)entidad;
				solrDoc = new SolrDocTopics();
				solrDoc.setId(idSolr);
				solrDoc.setDoctype(docTypeEmpresa);
				solrDoc.setModelversion(ParametrosCache.getChmGeneral(Constante.PARAM_GENERAL_CURRRENTMODEL));
				
				stContenido = new StringBuilder();
				
				if(empresa.getTexto()!=null ){
					stContenido.append(empresa.getTexto()
							.concat(Constante.SOLR_CONTENTSEPARATOR));
				}
				log4j.debug("<convertToSolrDoc> evaluando certificaciones...");
				if(empresa.getCertificacionEmpresas()!=null && empresa.getCertificacionEmpresas().size()>0){
					Iterator<CertificacionEmpresa> itCertificacionEmpresa= empresa.getCertificacionEmpresas().iterator();
					log4j.debug("<convertToSolrDoc> Extrayendo datos de certificacion Empresa (texto): " + empresa.getCertificacionEmpresas().size());
					while(itCertificacionEmpresa.hasNext()){
						CertificacionEmpresa certificacionEmpresa = itCertificacionEmpresa.next();
						stContenido.append(certificacionEmpresa.getTexto()!=null?certificacionEmpresa.getTexto()
								.concat(Constante.SOLR_CONTENTSEPARATOR):"");
					}
				}
				solrDoc.setContent(stContenido.toString());
			}   /* fin de if Empresa */
		 /* CASO PERFIL-POSICION */
		 else if(entidad instanceof Posicion){
				Posicion posicion = (Posicion)entidad;
				solrDoc = new SolrDocTopics();
				solrDoc.setId(idSolr);
				solrDoc.setDoctype(docTypePosicion);
				solrDoc.setModelversion(ParametrosCache.getChmGeneral(Constante.PARAM_GENERAL_CURRRENTMODEL));

				stContenido = new StringBuilder();
				
				if(posicion.getPerfilPosicions() != null && posicion.getPerfilPosicions().size() > 0){
					Iterator<PerfilPosicion> itPerfilPosicion = posicion.getPerfilPosicions().iterator();
					while(itPerfilPosicion.hasNext()){
						PerfilPosicion perfilPosicion = itPerfilPosicion.next();
						// Sólo considera los textos del perfil interno
						Iterator<PerfilTextoNgram>  itPerfilTextoNgram = perfilPosicion.getPerfil().getPerfilTextoNgrams().iterator();
						while(itPerfilTextoNgram.hasNext()){
							PerfilTextoNgram perfilTextoNgram = itPerfilTextoNgram.next();
							stContenido.append(perfilTextoNgram.getTexto() !=null ? perfilTextoNgram.getTexto()
									.concat(Constante.SOLR_CONTENTSEPARATOR):"");
						}							
					}
				}
				solrDoc.setContent(stContenido.toString());			 
		 } /* fin de if Posicion */
		 
		 log4j.debug("<convertToSolrDoc>solrDoc:");
		 log4j.debug(solrDoc + "\n\n");
		 
		 return solrDoc;
	}
	
	
	/**
	 * Obtiene el parametro de QualityOfService QOS global (empresa TCE) de la base de datos, el cual es el numero minimo de candidatos 
	 * que se deben regresar en una busqueda antes de hacer uso de los candidatos no aceptados 
	 * @param Ninguno
	 * @return QOS Valor del parametro QualityOfService
	 */			
	@SuppressWarnings("unchecked")
	private Integer getMaxExperiencia()throws Exception{
		EmpresaParametroDto empresaParametroDto=new EmpresaParametroDto();
		empresaParametroDto.setIdEmpresaConf(String.valueOf(Constante.IDCONF_NULL));
		empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_APLICACION_GENERAL);
		empresaParametroDto.setContexto(Constante.MAX_ANIOS_EXPERIENCIA);
		Object object=empresaParametroService.get(empresaParametroDto,true);
		if(object instanceof EmpresaParametroDto ){
			return new Integer(Constante.MAX_ANIOS_EXPERIENCIA_DEFAULT);
		}else{
			List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
			log4j.debug("<getMaxExperiencia> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
			return Integer.parseInt(lsEmpresaParametroDto.get(0).getValor());
		}	
	}	
	
	
	
	/**
	 * Envia una solicitud al generador de Hilos
	 */
	public void threadwriteInSolr(final Object pojoEntidad) {
		log4j.debug("<threadwriteInSolr> ");
		
		log4j.debug("<threadwriteInSolr> Ejecuta hilo (ASynchTaskExecutor) para escritura en Solr [writeInSolrPublication] ");
		
		if (this.asynchTaskExecutor != null) {
			this.asynchTaskExecutor.execute(new Runnable() {
				public void run() {
                	try {
                		log4j.debug("<threadwriteInSolr> Tarea [Thread.currentThread().Id()]: "+ Thread.currentThread().getId() );
                		writeInSolr(pojoEntidad);
                		
                		//Si hay un registro antes hubo una exception fatal 
                		//y se limpia
                		if(!ThreadExceptionCache.isEmpty()){
                			ThreadExceptionCache.clean();
                		}
                		
					}catch (Exception e) {
						 mensajeDto=new MensajeDto(null,null,
												Mensaje.SERVICE_CODE_006,
												Mensaje.SERVICE_TYPE_FATAL,
												Mensaje.MSG_ERROR);
						
						//Se guarda en cache la exception
						//divisor tipo HTTP 
						if(e.getMessage().contains(Constante.DIVISOR_HTTP)){
							String[] arMensaje= e.getMessage().split(Constante.DIVISOR_HTTP);
							log4j.error("threadwriteInSolr() -> arMensaje[0]: "+arMensaje[0]+
									" arMensaje[1]: "+arMensaje[1]);
							
							//es numerico
							if(UtilsTCE.isNumeric(arMensaje[0])){ 
								  //HTTP 4xx
								 if (Constante.HTTP_ERROR_CLIENT_4xx_INI <= Integer.parseInt(arMensaje[0]) &&
								    Integer.parseInt(arMensaje[0]) < Constante.HTTP_ERROR_CLIENT_4xx_FIN ){
								    	mensajeDto.setCode(Mensaje.SERVICE_CODE_200);
								    	mensajeDto.setMessage(Mensaje.MSG_FATAL_OPERATIONAL_NO_STRUCTURED);
								   }
							}						
						}else if(e.getMessage().contains(Constante.DIVISOR_ESTATUS)){
						 	mensajeDto.setCode(Mensaje.SERVICE_CODE_200);
					    	mensajeDto.setMessage(Mensaje.MSG_FATAL_OPERATIONAL_NO_STRUCTURED);
						}
						//Se adiciona al cache
						ThreadExceptionCache.put(Thread.currentThread().getId(), mensajeDto);
						
						//Se manda la notificación
						mensajeDto.setMessage(e.toString());
						adminService.notificacionFatal(mensajeDto);
						
						log4j.error("Error grave al publicar en Solr, numHilo:"+
									Thread.currentThread().getId()+"--> error:"+e.getMessage()+
									" toString:"+e.toString());
						//TODO, enviar notificación de Error fatal a administrador
						e.printStackTrace();
					}
                	log4j.debug("$$$%% El hilo de la tarea "+Thread.currentThread().getId()+" termina ");
                }
			});
		}
		log4j.debug("<threadwriteInSolr> regresa void, se ha programado el Hilo ");
	}
	
	/**
	 * Escribe/actualiza un archivo en el servidor de Solr, por medio de su id (TextoClasificación)
	 * @throws Exception 
	 */
	public  void  writeInSolr(Object pojoEntidad) throws Exception{
		log4j.debug("<writeInSolr> ");
		if(pojoEntidad !=null){
			boolean writed = false, suported = true;
			sb =new StringBuilder();
			//int intentos = 0, maxIntentos = 2;
			String idSolr, idTextoClasificacion;
			DocumentoClasificacion documentoClasificacion;
			Set<DocumentoClasificacion> setDocumentoClasificacions=null;
			
			//do{
				idSolr = null;
				idTextoClasificacion = null;
				
				if(pojoEntidad instanceof Persona){					
					Persona persona = (Persona)pojoEntidad;
					log4j.debug("<writeInSolr> IdPersona: " + persona.getIdPersona() );
					
					setDocumentoClasificacions=persona.getDocumentoClasificacions();
					log4j.debug("<writeInSolr> se asigna a setDocumentoClasificacions:: " );

//					log4j.debug("<writeInSolr> setDocumentoClasificacions: " + setDocumentoClasificacions );
					if(setDocumentoClasificacions != null && 
					   setDocumentoClasificacions.size() == 1){
						idTextoClasificacion=setDocumentoClasificacions.
											  iterator().next().getIdTextoClasificacion();						
					}
					sb.append("Persona ").append(persona.getIdPersona());
					//si la entidad tiene id para solr se asigna
					idSolr = writeOnSolrServer(pojoEntidad, idTextoClasificacion);
					if(!idSolr.equals("-1") && UtilsTCE.isValidId(idSolr)){
						writed = true;
						log4j.debug("<writeInSolr> idTextoClasificacion: " + idTextoClasificacion );
						if(idTextoClasificacion == null){
							//se crea documento Clasificacion
							documentoClasificacion= new DocumentoClasificacion();
							documentoClasificacion.setPersona(persona);
							documentoClasificacion.setIdTextoClasificacion(idSolr);
							documentoClasificacion.setFechaModificacion(DateUtily.getToday());
							log4j.debug("<writeInSolr> create documentoClasificacionDao" );
							documentoClasificacionDao.create(documentoClasificacion);
						}						
						
						//persona
						persona.setClasificado(writed);
						persona.setFechaModificacion(DateUtily.getToday());
						persona.setSePreClasifica(false);
						personaDao.update(persona);
						
					}/*else{
						intentos++;
					}*/
				}else if(pojoEntidad instanceof Empresa){
					Empresa empresa = (Empresa)pojoEntidad;
					setDocumentoClasificacions=empresa.getDocumentoClasificacions();
					
					if(setDocumentoClasificacions != null && 
					   setDocumentoClasificacions.size() == 1){
							idTextoClasificacion=setDocumentoClasificacions.
												  iterator().next().getIdTextoClasificacion();						
						}
					log4j.debug("idTextoClasificacion en empresa: " + idTextoClasificacion );
					sb.append("Empresa ").append(empresa.getIdEmpresa());
					//si la entidad tiene id para solr se asigna
					idSolr = writeOnSolrServer(pojoEntidad, idTextoClasificacion);
					if(!idSolr.equals("-1") && UtilsTCE.isValidId(idSolr)){
						writed = true;

						if(idTextoClasificacion == null){
							//se crea documento Clasificacion
							documentoClasificacion= new DocumentoClasificacion();
							documentoClasificacion.setEmpresa(empresa);
							documentoClasificacion.setIdTextoClasificacion(idSolr);
							documentoClasificacion.setFechaModificacion(DateUtily.getToday());
							documentoClasificacionDao.create(documentoClasificacion);
						}
						
						empresa.setClasificado(writed);
						empresa.setFechaModificacion(DateUtily.getToday());
						empresaDao.update(empresa);
					}/*else{
						intentos++;
					}*/
				}
				else if(pojoEntidad instanceof Posicion){

					log4j.debug("<writeInSolrPublication> Documento Posicion.... ");					
					Posicion posicion = (Posicion)pojoEntidad;					
					log4j.debug("<writeInSolrPublication> Obteniendo el id texto clasificación del perfil interno ");
					// Obtiene el id texto clasificación del perfil interno
					sb.append(" Posicion:").append(posicion.getIdPosicion());
					
					setDocumentoClasificacions=posicion.getDocumentoClasificacions();
					if(setDocumentoClasificacions != null && 
						setDocumentoClasificacions.size() == 1){
							idTextoClasificacion=setDocumentoClasificacions.
												  iterator().next().getIdTextoClasificacion();						
						}
					
					log4j.debug("<writeInSolrPublication> idTextoClasificacion en perfil: " + idTextoClasificacion );
					//si la entidad tiene id para solr se asigna
					idSolr = writeOnSolrServer(pojoEntidad, idTextoClasificacion);
					if(!idSolr.equals("-1") && UtilsTCE.isValidId(idSolr)){
						writed = true;
						/*if(perfil != null){
							perfil.setIdTextoClasificacion(idSolr);
							perfil.setClasificado(writed);
							perfilDao.update(perfil);							
						}*/
						if(idTextoClasificacion == null){
							//se crea documento Clasificacion
							documentoClasificacion= new DocumentoClasificacion();
							documentoClasificacion.setPosicion(posicion);
							documentoClasificacion.setIdTextoClasificacion(idSolr);
							documentoClasificacion.setFechaModificacion(DateUtily.getToday());
						
							log4j.debug("<writeInSolrPublication> Iterando para buscar perfil interno");
							//Iteración para buscar el perfil interno. Por ahora solo hay un perfil				
							if(posicion.getPerfilPosicions().size() > 0){
								Iterator<PerfilPosicion> itPerfilPosicion =  posicion.getPerfilPosicions().iterator(); 
								if(itPerfilPosicion.hasNext()){
									PerfilPosicion perfilPosicion = itPerfilPosicion.next();
									log4j.debug("<writeInSolrPublication> Dentro de ciclo, Verificando si es perfil interno idPerfil :" +
									perfilPosicion.getPerfil().getIdPerfil());
									documentoClasificacion.setPerfil(perfilPosicion.getPerfil());
								}				
							}
							documentoClasificacionDao.create(documentoClasificacion);
						}
						//posicion
						posicion.setClasificado(writed);
						posicion.setFechaModificacion(DateUtily.getToday());
						posicionDao.update(posicion);
						
					}/*else{
						intentos++;
					}*/
				}
				else{
					log4j.error("Entidad no soportada en Solr" + pojoEntidad.getClass().getName());
					suported = false;
					//intentos=maxIntentos;
				}
			//}while(!writed && intentos<maxIntentos);//Realiza si no se ha escrito y lleva menos de n intentos
			
			if(!writed && suported){
				log4j.debug("Se alcanzaron los intentos maximos para realizar la carga en Solr, realizando proceso de notificación para " + sb.toString());
				//TODO, enviar notificación a administrador
			}
			
		}else{
			log4j.debug("<SolrService> La entidad enviada es nula, no hay documento a escribir ");
		}
	}
	
	
}
