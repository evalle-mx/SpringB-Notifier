package net.tce.admin.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import javax.inject.Inject;
import net.tce.admin.service.CurriculumService;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.admin.service.RestJsonService;
import net.tce.admin.service.SolrService;
import net.tce.app.exception.SystemTCEException;
import net.tce.assembler.CurriculumAssembler;
import net.tce.cache.EscolaridadCache;
import net.tce.dao.AreaPersonaDao;
import net.tce.dao.CandidatoDao;
import net.tce.dao.DomicilioDao;
import net.tce.dao.EmpresaConfDao;
import net.tce.dao.EscolaridadDao;
import net.tce.dao.EstatusEscolarDao;
import net.tce.dao.ExperienciaLaboralDao;
import net.tce.dao.GradoAcademicoDao;
import net.tce.dao.HabilidadDao;
import net.tce.dao.HistoricoPasswordDao;
import net.tce.dao.PaisDao;
import net.tce.dao.PersonaDao;
import net.tce.dao.PersonaPaisDao;
import net.tce.dao.RelacionEmpresaPersonaDao;
import net.tce.dao.RolDao;
import net.tce.dto.AcademicBackgroundDto;
import net.tce.dto.CandidatoDto;
import net.tce.dto.ComunDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.MasivoDto;
import net.tce.dto.LocationInfoDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.PaisDto;
import net.tce.dto.PersonSkillDto;
import net.tce.dto.SettlementDto;
import net.tce.dto.WorkExperienceDto;
import net.tce.model.Area;
import net.tce.model.AreaPersona;
import net.tce.model.Candidato;
import net.tce.model.Domicilio;
import net.tce.model.Empresa;
import net.tce.model.EmpresaConf;
import net.tce.model.Escolaridad;
import net.tce.model.EstatusCandidato;
import net.tce.model.EstatusEscolar;
import net.tce.model.EstatusInscripcion;
import net.tce.model.EstatusOperativo;
import net.tce.model.ExperienciaLaboral;
import net.tce.model.GradoAcademico;
import net.tce.model.Habilidad;
import net.tce.model.HistoricoPassword;
import net.tce.model.Pais;
import net.tce.model.Persona;
import net.tce.model.PersonaPais;
import net.tce.model.RelacionEmpresaPersona;
import net.tce.model.Rol;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.ParametersTCE;
import net.tce.util.UtilsTCE;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Clase donde se aplica las politicas de negocio del servicio CurriculumManagement
 * @author Goyo
 *
 */
@Transactional
@Service("curriculumService")
public class CurriculumServiceImpl implements CurriculumService{
	Logger log4j = Logger.getLogger( this.getClass());
	//static final Logger //log4jMvs = Logger.getLogger("MASIVECVS");


	JSONObject jsonObject ;
	static Workbook workbook=null;
	static List<String> lsErroresClaveInt=null;
	static CurriculumDto curriculumDto;
	static List<CurriculumDto> curriculos;
	static MasivoDto masivoDto;
	
	//  Data Access Object's (DAO) 

	@Autowired
	private GradoAcademicoDao gradoAcademicoDao;
	
	@Autowired
	private EstatusEscolarDao estatusEscolarDao;
	
	@Autowired
	private PersonaDao personaDao;
	
	@Autowired
	private RelacionEmpresaPersonaDao relacionEmpresaPersonaDao;
	
	@Autowired
	private DomicilioDao domicilioDao;
	
	@Autowired
	private EscolaridadDao escolaridadDao;
	
	@Autowired
	private ExperienciaLaboralDao experienciaLaboralDao;
	
	@Autowired
	private HabilidadDao habilidadDao;
	
	@Autowired
	private CandidatoDao candidatoDao;
	
	@Autowired
	private PaisDao paisDao;
	
	@Autowired
	PersonaPaisDao personaPaisDao;
	
	@Autowired
	private AreaPersonaDao areaPersonaDao;
	
	@Autowired
	private RolDao rolDao;
	
	@Autowired
	private HistoricoPasswordDao historicoPasswordDao;
	
	//   Interfaces de Servicio 	
	
	@Autowired
	private EmpresaParametroService empresaParametroService;
	
	@Autowired
	private SolrService solrService;
	
	@Autowired
	private RestJsonService restJsonService;
	
	@Autowired
	private EmpresaConfDao empresaConfDao;
	
	//   Herramientas y Utilerias  
	
	@Inject
	private CurriculumAssembler curriculumAssembler;
	
	@Inject
	Gson gson;
	
	@Inject
	private ConversionService converter;
	
	private List<EmpresaParametroDto> lsEmpresaParametroPublicacionDto = null;
	HashMap<String, Object> currFilters;
	PersonaPais personaPais;
	Pais pais;
	
	
		
	/**
	 * <b>BUS DE PROCESO</b> encargado de crear/actualizar un registro en el contexto persona para carga directa
	 * @param curriculumDto, objeto que contiene la informacion para usar registro 
	 * @return  la respuesta correspondiente a la tarea
	 */
	@SuppressWarnings("unchecked")
	public Object createFull(CurriculumDto curriculumDto)  throws Exception  {
		log4j.debug("<createFull>.... ");
		
		//I. VERIFICA INTEGRIDAD (1) existencia de datos requeridos
		log4j.debug("<createFull> getIdEmpresaConf="+curriculumDto.getIdEmpresaConf());

		// Genera lista de parametros de verificación/Publicación 
		Object obLsParametros= null;
		if(lsEmpresaParametroPublicacionDto==null){
			log4j.debug("<createFull> Generando empresa parametro para publicación de Persona ");
			EmpresaParametroDto empresaParametroDto=new EmpresaParametroDto();
			empresaParametroDto.setIdEmpresaConf(Constante.ID_EMPRESA_CONF_MASIVO);
			empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_ATRIBUTO_REQUERIDO);
			obLsParametros=empresaParametroService.get(empresaParametroDto,true);	
		}else{
			log4j.debug("<createFull> Ya existen parametros de publicacion para persona ");
			obLsParametros= lsEmpresaParametroPublicacionDto;
		}
		
		if(obLsParametros instanceof EmpresaParametroDto ){
			log4j.error("<createFull> No existen criterios en la tabla EMPRESA_PARAMETRO para publicar curriculum");
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_002, Mensaje.SERVICE_TYPE_FATAL, Mensaje.MSG_ERROR));
		}else {
				try{
					//se carga info de grados academicos y estatus academicos
					setNivelEscolaridad();					
					curriculumDto.setDiasExperienciaLaboral(null);
				
					List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)obLsParametros;
					lsEmpresaParametroPublicacionDto = lsEmpresaParametroDto;
					log4j.debug("<createFull> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
					
					//   II. Realiza calculos en base a DTO
					Iterator<EmpresaParametroDto> itParametro = lsEmpresaParametroDto.iterator();
					while(itParametro.hasNext()){
						EmpresaParametroDto empParametroDto = itParametro.next();
						if(empParametroDto.getFuncion()!=null && !empParametroDto.getFuncion().trim().equals("")){
							log4j.debug("<createFull>\n valor: " + empParametroDto.getValor() 
									+ "\n funcion: "+empParametroDto.getFuncion());
							Class<?>[] argumentTypes = { CurriculumDto.class};
							Object[] arguments = { curriculumDto };
							UtilsTCE.executeReflexion(new ParametersTCE(),empParametroDto.getFuncion(),null, argumentTypes,arguments);
						}
					}
					log4j.debug("<createFull>  diasExperiencia= " +curriculumDto.getDiasExperienciaLaboral() );
					log4j.debug("<createFull> gradoMaximo= " + curriculumDto.getIdGradoAcademicoMax() );
					log4j.debug("<createFull> estatusEscolarMax= " + curriculumDto.getIdEstatusEscolarMax() );
					log4j.debug("<createFull> titulo_max= " + curriculumDto.getTituloMax() );
					// III. VALIDAR CONTRA EMPRESA PARAMETRO's
					//List<CandidatoDto> lsCandidatoDtoOut = ValidadorPublicacion.mainResumePersonaDtoValidations(curriculumDto, lsEmpresaParametroDto);
					List<CandidatoDto> lsCandidatoDtoOut = ParametersTCE.mainResumePersonaDtoValidations(
																		curriculumDto, lsEmpresaParametroDto);

					
					//SE verifica si existe el pais
					List<PaisDto> lsPaisDto=paisDao.getPais(curriculumDto.getStPais());
					log4j.debug("persona_pais -> lsPais:" +(lsPaisDto == null ? null:	lsPaisDto.size()));
					//Se ordena la lista
					if(lsPaisDto != null && lsPaisDto.size() > 0){
						if( lsPaisDto.size() > 1){
							Collections.sort(lsPaisDto); 
						}							
						curriculumDto.setIdPais(String.valueOf(lsPaisDto.get(0).getIdPais()));
						log4j.debug("persona_pais -> ok IdPais:" + curriculumDto.getIdPais());
					}else{
						//si no se encontro id de pais
						lsCandidatoDtoOut.add(new CandidatoDto("stPais", "No se reconoce el País"));
					}
					
					if(lsCandidatoDtoOut!=null && lsCandidatoDtoOut.size() > 0){
						// Hay errores, se devuelve lista de errores 
						log4j.debug("<createFull> errores encontrados: "+ lsCandidatoDtoOut.size() );
						// CandidatoDto errorHeaderDto = new CandidatoDto();//se crea este dto como encabezado de la lista de errores
						//errorHeaderDto.setIdExterno(idExterno);
						//errorHeaderDto.setMessages(Mensaje.MSG_PUBLICATION_FAIL);
						//lsCandidatoDtoOut.add(0, errorHeaderDto); 
						return lsCandidatoDtoOut;
					}else{
						curriculumDto.setIdEstatusInscripcion(String.valueOf(Constante.ESTATUS_INSCRIPCION_PUBLICADO));
						// IV. SE REALIZA LA PERSISTENVIA (save/update) DE PERSONA Y RELACIONADAS
						Object persistenceCV = saveOrReplaceCvExterno(curriculumDto);
						if(persistenceCV instanceof CurriculumDto){
							// Si regresa Dto, significa que hubo error 
							log4j.debug("<createFull> Regresa Dto, significa que hubo error");
							return curriculumDto.getMessages();
						}else if(persistenceCV instanceof Persona){
							//ENVIAR A SOLR (hilo independiente) 			
							log4j.debug("<createFull> >>>  ENVIAR A SOLR ");
							Persona personaPojo = (Persona)persistenceCV;
							solrService.threadwriteInSolr(personaPojo);	
							curriculumDto.setMessages("[{\"status\":\"OK\"}]");
						}else{
							throw new NullPointerException("El objeto devuelto es incorrecto");
						}
					}
				}catch (Exception e){
					log4j.fatal("Excepción...", e );
					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
							Mensaje.MSG_ERROR));
				}				
			}
		
		log4j.debug("<createFull> 2 getMessage()=" + curriculumDto.getMessages() );
		return curriculumDto.getMessages();
	}
	
	/**
	 *  Se pone los niveles de GradoAcademico y EstatusEscolar,  en cache
	 */
	private void setNivelEscolaridad(){
		log4j.debug("setNivelEscolaridad() ->  isEmptyNivelGradoAcademico=" + EscolaridadCache.isEmptyNivelGradoAcademico()+
				" isEmptyNivel ="+EscolaridadCache.isEmptyNivelEstatusEscolar());
		
		//Se verifica si esta vacío NivelGradoAcademico
		if(EscolaridadCache.isEmptyNivelGradoAcademico()){
			Iterator<GradoAcademico> itGradoAcademico=gradoAcademicoDao.findAll().iterator();
			while(itGradoAcademico.hasNext()){
				GradoAcademico gradoAcademico=itGradoAcademico.next();
				if(gradoAcademico.getNivel() != null){
					EscolaridadCache.putNivelGradoAcademico(gradoAcademico.getIdGradoAcademico(),
														gradoAcademico.getNivel());
				}
			}
		}
		
		//Se verifica si esta vacío NivelEstatusEscolar
		if(EscolaridadCache.isEmptyNivelEstatusEscolar()){
			Iterator<EstatusEscolar> itEstatusEscolar =estatusEscolarDao.findAll().iterator();
			while(itEstatusEscolar.hasNext()){
				EstatusEscolar estatusEscolar=itEstatusEscolar.next();
				if(estatusEscolar.getNivel() > 0){
					EscolaridadCache.putNivelEstatusEscolar(estatusEscolar.getIdEstatusEscolar(), 
															estatusEscolar.getNivel());
				}
			}
		}
	}
	
	
	/**
	 * Procesa multiples cv's en un llamado de Cliente Rest
	 * @param lsCurriculumDto
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public String createMasive(MasivoDto cvMsdto) throws Exception {		
		try {
			//Se crean documentos apartir de archivos excels
			List<MasivoDto> lsMasivoDto=loadFromFileExcel(cvMsdto.getMsvDir());		
			
			if(lsMasivoDto != null && lsMasivoDto.size() > 0){
				log4j.debug("lsMasivoDto="+lsMasivoDto.size() +
						" getIdEmpresaConf="+lsMasivoDto.get(0).getIdEmpresaConf());							
				
				//Se verifica si existe la empresaConf
				Long idEmpresa=empresaConfDao.getEmpresa(lsMasivoDto.get(0).getIdEmpresaConf());
				log4j.debug("<createMasive>  idEmpresa="+idEmpresa);
				
				//Existe la empresa
				if(idEmpresa != null){
					Object object;
					String resp;
			    	StringBuilder sb=new StringBuilder("[");
			    	Iterator<MasivoDto> itMasivoDto=lsMasivoDto.iterator();		    	
					while(itMasivoDto.hasNext()){
						cvMsdto=itMasivoDto.next();
					 	sb.append("{").append("\"idEmpresaConf\":\"").
					 	append(cvMsdto.getIdEmpresaConf()).append("\",").
					 	append("\"cvs\": [");
					 	
						Iterator<CurriculumDto> itCvs = cvMsdto.getCurriculos().iterator();
						while(itCvs.hasNext()){
							CurriculumDto cvdto = itCvs.next();
							cvdto.setIdEmpresa(idEmpresa.toString());
							
							//Se aplican los filtros de dataconf
							//Se llama al servicio de notificación
							resp=(String)restJsonService.serviceRJTransacStruc(gson.toJson(cvdto),
														new StringBuilder(Constante.URI_CURRICULUM).
														append(Constante.URI_DATA_CONF_MASIVE).toString());
							
							log4j.debug("<createMasive> -> resp:"+resp);
							
							//Se analiza la respuesta
							//Paso los filtros dataConf
							if(resp.equals("")){
								//Se verifican si los CVs son publicables. 
								//Se crean registros solo a los CVs publicados.
								object =createFull(cvdto);	
								
								if(object != null){
									mensajeSalidaMsv(sb, cvdto.getClaveInterna(),
													curriculumAssembler.restResponse(object));
									if(itCvs.hasNext()){
										sb.append(",");
									}
								}
							}else{
								mensajeSalidaMsv(sb, cvdto.getClaveInterna(),resp);
								if(itCvs.hasNext()){
									sb.append(",");
								}
							}		
						}
						sb.append("]");
						sb.append("}");
						
						if(itMasivoDto.hasNext()){
							sb.append(",");
						}
					}
					sb.append("]");
					log4j.debug("<createMasive> resp="+sb.toString());
					return sb.toString();
				}else{
					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
							Mensaje.MSG_ERROR_ID_EMPRESA_CONF));
				}
			}else{
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_003,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR_MASIVE_CV_XLS));
			}			
		} catch (Exception e) {
			log4j.fatal("Falló el servicio createMasive(). ", e );
			 throw new SystemTCEException("Falló el servicio createMasive(). ", e);
		}
		
	}
	
	/**
	 * 
	 * @param sb
	 * @return
	 */
	private void mensajeSalidaMsv(StringBuilder sb,String claveInterna, String messages){
		sb.append("{").
		append("\"claveInterna\":\"").
		append(claveInterna).append("\",").
		append("\"messages\":").
		append(messages).
		append("}");
		
	}
	
	/**
     * Se crean documentos apartir de archivos excels
     * @param classifierDto
     * @throws Exception
     */
    private static List<MasivoDto> loadFromFileExcel(String  seedDir) throws Exception{
    	List<MasivoDto> lsMasivoDto=null;
    	//log4jMvs.debug(" &%$ seedDir="+seedDir);
    	//directorio donde estan los archivos excels
		File dir = new File(seedDir);
		FileFilter fileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isFile();
	        }
	    };
	    
	    //Se aplica el filtro
    	File[] files = dir.listFiles(fileFilter);
    	//log4jMvs.debug("files="+files.length);
    	if(files!=null && files.length>0){
    		lsMasivoDto=new ArrayList<MasivoDto>();
    		
    		//Se obtine la ultima secuencia de este tipo de semilla
    		for(int x=0;x<files.length;x++){
    			//log4jMvs.debug("\n\n\t\t--------------------------------------------------------------------------------------------"+
						//"-------------------------------------------------------------------------------------------"+
						//"--------------------------------------------------------------------------------------");
    				//log4jMvs.debug("%& getCanonicalPath="+files[x].getCanonicalPath()+
  			    					//" getName="+files[x].getName()+
  			    					//" indexOf="+files[x].getName().subSequence(files[x].getName().
  			    								//indexOf(".")+1, files[x].getName().length()));
    				
    			  	FileInputStream fileIS = new FileInputStream(files[x]);
    			  	//excel 97/2000/XP/2003
    			  	if(files[x].getName().subSequence(files[x].getName().
					   indexOf(".")+1, files[x].getName().length()).equals("xls")){
    			  		//Get the workbook instance for XLS file 
    			  		workbook = new HSSFWorkbook(fileIS);
			 		    
			 		 //excel 2007/2010 XML Format
    			  	}else if(files[x].getName().subSequence(files[x].getName().
	    					indexOf(".")+1, files[x].getName().length()).equals("xlsx")){
    			  		 //Create Workbook instance holding reference to .xlsx file
    			  		 workbook = new XSSFWorkbook(fileIS);			  
    			  	}
			     
    			    //	boolean firstRow=true;
    			  	//log4jMvs.debug(" $% contSheets="+workbook.getNumberOfSheets());
    			  	lsErroresClaveInt=new  ArrayList<String>();
    			  	putMasiveCvs(workbook.getSheet(Constante.MSV_CVS_EMP), Constante.MSV_CVS_EMP);
    			  	if(masivoDto != null){
    			  		curriculos= new ArrayList<CurriculumDto>();
    			  		//curriculum
    			  		putMasiveCvs(workbook.getSheet(Constante.MSV_CVS), Constante.MSV_CVS);
    			  		if(curriculos.size() > 0){  
    			  			//localizacion
    			  			putMasiveCvs(workbook.getSheet(Constante.MSV_CVS_UBICACION), Constante.MSV_CVS_UBICACION);
    			  			
    			  			//experiencias
    			  			putMasiveCvs(workbook.getSheet(Constante.MSV_CVS_EXPER), Constante.MSV_CVS_EXPER);
    			  			
    			  			//escolaridades
    			  			putMasiveCvs(workbook.getSheet(Constante.MSV_CVS_ESCOLAR), Constante.MSV_CVS_ESCOLAR);
    			  			
    			  			//habilidades
    			  			putMasiveCvs(workbook.getSheet(Constante.MSV_CVS_HABILIDAD), Constante.MSV_CVS_HABILIDAD);
    			  			masivoDto.setCurriculos(curriculos);
    			  			lsMasivoDto.add(masivoDto);
    			  		}			  			
    			  	}
		 		    fileIS.close();
    		}
    	}
    	//hay errores
    	if(lsErroresClaveInt != null && lsErroresClaveInt.size() > 0){
    		//log4jMvs.error(" Errores en los cvs:"+lsErroresClaveInt+", los cuales no fueron creados");
    		if(lsMasivoDto != null && lsMasivoDto.size() > 0){
    			Iterator<MasivoDto> itcvm=lsMasivoDto.iterator();
    			while(itcvm.hasNext()){
    				MasivoDto cvm=itcvm.next();
    				if(cvm.getCurriculos() != null && cvm.getCurriculos().size() > 0){
    					Iterator<CurriculumDto> itCv=cvm.getCurriculos().iterator();
    					while(itCv.hasNext()){
    						CurriculumDto curriculumDto=itCv.next();
    						//si existe en la lista de errores se elimina de la lista de salida
    						if(lsErroresClaveInt.contains(curriculumDto.getClaveInterna())){
    							itCv.remove();
    						}
    					}
    				}
    			}
    		}
    	}  	
    	return lsMasivoDto;
    }
    
    /**
	 * 
	 * @param sheet
	 * @param tipoHoja
	 */
	private static void putMasiveCvs(Sheet sheet, String tipoHoja){
		if(sheet != null){
			LocationInfoDto locationInfoDto;
			WorkExperienceDto workExperienceDto;
			AcademicBackgroundDto academicBackgroundDto;
			PersonSkillDto habilidadDto;
			String claveInterna=null;
			boolean firstRow=true;
	  		Iterator<Row> itrow=sheet.iterator();
	  		int renglon=2;
	  		 //log4jMvs.debug("\n//--// tipoHoja="+tipoHoja+"  //--//");
	  		
  			 while(itrow.hasNext()) {
  				  Row row = itrow.next();
  				 //a partir del segundo renglon
  				 if(firstRow){
  					
  					//log4jMvs.debug("\n\t\t"+row.getCell(0) +"\t"+ row.getCell(1)+"\t"+ row.getCell(2)+"\t"+ row.getCell(3)+
	        						 // "\t"+ row.getCell(4)+"\t"+ row.getCell(5)+"\t"+ row.getCell(6)+"\t"+ row.getCell(7)+
	        						 // "\t"+ row.getCell(8)+"\t"+ row.getCell(9)+"\t"+ row.getCell(10)+"\t\t"+ row.getCell(11)+
	        						 // "\t\t"+ row.getCell(12)+"\t\t"+ row.getCell(13)+"\t"+ row.getCell(14)+"\t"+row.getCell(15));
  					
	    
  					firstRow=false;
  					continue;
  				 }
  				 //se inicializa
  				 if(row.getCell(0) != null && !tipoHoja.equals(Constante.MSV_CVS_EMP) && !tipoHoja.equals(Constante.MSV_CVS)){
	  				claveInterna=row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC ? 
	  								String.valueOf((int)row.getCell(0).getNumericCellValue()):
	  								row.getCell(0).getStringCellValue();
  				 }
	  			 
  				 //hoja EMPRESA_CVS
  				 if(tipoHoja.equals(Constante.MSV_CVS_EMP)){
		 		     //se filtran las coluumnas
		 		     if(row.getPhysicalNumberOfCells() == 1 && 
		 		    	row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK ) {
		 		    	 	
		 		    	 	masivoDto=new MasivoDto();
		 		    	 	masivoDto.setIdEmpresaConf(String.valueOf((int)row.getCell(0).getNumericCellValue()));
				  			//log4jMvs.debug("\n\t\t"+masivoDto.getIdEmpresaConf() );	
		 		     }
		 		 //hoja CVS
  				 }else if(tipoHoja.equals(Constante.MSV_CVS)){
  					//se filtran las coluumnas
		 		    /* if(row.getPhysicalNumberOfCells() > 4 && 
		 		    		row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK && 		 		    	
			 		    	row.getCell(11) != null && row.getCell(11).getCellType() != Cell.CELL_TYPE_BLANK &&
			 		    	row.getCell(12) != null && row.getCell(12).getCellType() != Cell.CELL_TYPE_BLANK && 
			 		    	row.getCell(13) != null && row.getCell(13).getCellType() != Cell.CELL_TYPE_BLANK) {*/
		 		    	 
		 		    	curriculumDto=new CurriculumDto();
		 		    	curriculumDto.setIdEmpresaConf(masivoDto.getIdEmpresaConf());
	 		    	 	curriculumDto.setClaveInterna(row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC ? 
  													String.valueOf((int)row.getCell(0).getNumericCellValue()):
  													row.getCell(0).getStringCellValue());
	 		    	 	if(row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK)
	 		    	 		curriculumDto.setNombre(row.getCell(1).getStringCellValue());
	 		    	 	if(row.getCell(2) != null && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK)
	 		    	 		curriculumDto.setApellidoPaterno(row.getCell(2).getStringCellValue());
	 		    	 	if(row.getCell(3) != null && row.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK)
	 		    	 		curriculumDto.setApellidoMaterno(row.getCell(3).getStringCellValue());
	 		    	 	if(row.getCell(4) != null && row.getCell(4).getCellType() != Cell.CELL_TYPE_BLANK){
	 		    	 		curriculumDto.setIdEstadoCivil(new StringTokenizer(row.getCell(4).getStringCellValue(),"-").
	 		    	 										nextToken().trim());
	 		    	 	}
		 		    	
	 		    	 	if(row.getCell(5) != null && row.getCell(5).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setPermisoTrabajo(new StringTokenizer(row.getCell(5).getStringCellValue(),"-").
		 		    										nextToken().trim());
		 		    	}
	 		    	 	if(row.getCell(6) != null && row.getCell(6).getCellType() != Cell.CELL_TYPE_BLANK)
	 		    			curriculumDto.setStPais(row.getCell(6).getStringCellValue());
	 		    	 	
		 		    	if(row.getCell(7) != null && row.getCell(7).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setCambioDomicilio(new StringTokenizer(row.getCell(7).getStringCellValue(),"-").
		 		    										nextToken().trim());
		 		    	}
		 		    	if(row.getCell(8) != null && row.getCell(8).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setIdTipoDispViajar(new StringTokenizer(row.getCell(8).getStringCellValue(),"-").
		 		    										nextToken().trim());
		 		    	}
		 		    	
		 		    	if(row.getCell(9) != null && row.getCell(9).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    	   	curriculumDto.setDisponibilidadHorario(new StringTokenizer(row.getCell(9).
		 		    	   											getStringCellValue(),"-").nextToken().trim());
		 		    	}
		 		    	if(row.getCell(10) != null && row.getCell(10).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setIdTipoJornada(new StringTokenizer(row.getCell(10).getStringCellValue(),"-").
		 		    									nextToken().trim());
		 		    	}
		 		    	if(row.getCell(11) != null && row.getCell(11).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setEdad(String.valueOf((int)row.getCell(11).getNumericCellValue()));
		 		    	}
		 		    	
		 		    	if(row.getCell(12) != null && row.getCell(12).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setSalarioMin(String.valueOf((int)row.getCell(12).getNumericCellValue()));
		 		    	}

		 		    	if(row.getCell(13) != null && row.getCell(13).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setSalarioMax(String.valueOf((int)row.getCell(13).getNumericCellValue()));
		 		    	}
		 		    	
		 		    	if(row.getCell(14) != null && row.getCell(14).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setIdTipoGenero(new StringTokenizer(row.getCell(14).getStringCellValue(),"-").
		 		    										nextToken().trim());
		 		    	}
		 		    	if(row.getCell(15) != null && row.getCell(15).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setEmail(row.getCell(15).getStringCellValue());
		 		    	}
		 		    	if(row.getCell(16) != null && row.getCell(16).getCellType() != Cell.CELL_TYPE_BLANK){
		 		    		curriculumDto.setIdAreaPrincipal((String.valueOf((int)row.getCell(16).
		 		    															getNumericCellValue())));
		 		    	}
		 		    	
	 		 		    	////log4jMvs.debug("%&%  CVS -> tipoClaveInterna:"+row.getCell(1).getCellType());
			 		    	//log4jMvs.debug("\n\t\t"+curriculumDto.getClaveInterna()+"\t\t"+ row.getCell(1)+"\t"+
			 		    			 // "\t\t"+ row.getCell(2)+"\t"+ row.getCell(3)+"\t\t"+ row.getCell(4)+
	        						 // "\t\t"+ row.getCell(5)+"\t"+ row.getCell(6)+"\t\t"+ row.getCell(7)+"\t"+
	        						 //  row.getCell(8)+"\t\t"+ row.getCell(9)+"\t"+ row.getCell(10)+"\t\t\t\t"+
	        						 // row.getCell(11)+"\t"+ row.getCell(12)+"\t\t"+ row.getCell(13)+"\t\t"+
	        						 //  row.getCell(14)+"\t\t"+ row.getCell(15));
			 		    	
			 		    	curriculos.add(curriculumDto);
			 		 //Nulo los obligatorios
		 		     /*}else{
		 		    	 if(row.getCell(0) != null ){
	 		 		    		putError(claveInterna, renglon,Constante.MSV_CVS);
		 		    	 }
		 		     }*/
		 		 
		 		  //hoja de LOCALIZACIONES
  				  }else if(tipoHoja.equals(Constante.MSV_CVS_UBICACION)){
  					//se filtran las coluumnas
 		 		    if(row.getPhysicalNumberOfCells() >= 5 && 
 		 		    	row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK && 
 		 		    	row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && 
 		 		    	row.getCell(2) != null && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK && 
 		 		    	row.getCell(3) != null && row.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK && 
 		 		    	row.getCell(4) != null && row.getCell(4).getCellType() != Cell.CELL_TYPE_BLANK){
 		 		    	locationInfoDto=new LocationInfoDto();
	 		    		locationInfoDto.setStPais(row.getCell(1).getStringCellValue());		 		    	
	 		    		locationInfoDto.setStEstado(row.getCell(2).getStringCellValue());		 		    	
	 		    		locationInfoDto.setStMunicipio(row.getCell(3).getStringCellValue()); 
	 		    		locationInfoDto.setStColonia(row.getCell(4).getStringCellValue());
 		 		    	
 		 		    	if(row.getCell(5) != null && row.getCell(5).getCellType() != Cell.CELL_TYPE_BLANK)
 		 		    		locationInfoDto.setCodigoPostal(String.valueOf((int)row.getCell(5).getNumericCellValue()));
 		 		    	
 		 		    	if(row.getCell(6) != null && row.getCell(6).getCellType() != Cell.CELL_TYPE_BLANK)
 		 		    		locationInfoDto.setLongitud(String.valueOf(row.getCell(6).getNumericCellValue()));
 		 		    	
 		 		    	if(row.getCell(7) != null && row.getCell(7).getCellType() != Cell.CELL_TYPE_BLANK)
 		 		    		locationInfoDto.setLatitud(String.valueOf(row.getCell(7).getNumericCellValue()));
 		 		    	
 		 		    	//log4jMvs.debug("\n\t\t"+claveInterna.toString() +"\t\t"+ row.getCell(1)+"\t"+ row.getCell(2)+"\t\t"+ row.getCell(3)+
 		 		    					//	"\t"+ row.getCell(4)+"\t"+ locationInfoDto.getCodigoPostal()+"\t"+ row.getCell(6)+
 		 		    						//"\t"+ row.getCell(7));
 		 		    	//se adiciona al cv correspondiente
 		 		    	//si no esta en la lista de errores
 		 		    	if(!lsErroresClaveInt.contains(claveInterna)){
 		 		    			addInCv( locationInfoDto, claveInterna);
 		 		    	}
 		 		     }else{
 		 		    	if(row.getCell(0) != null){
 		 		    		putError(claveInterna, renglon,Constante.MSV_CVS_UBICACION);
 		 		    	}
 		 		     }
 		 		   //experiencias
  				  }else if(tipoHoja.equals(Constante.MSV_CVS_EXPER)){
  					//se filtran las coluumnas
  		 		     if(row.getPhysicalNumberOfCells() >= 7 && 
  		 		    	row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK && 
  		 		    	row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && 
  		 		    	row.getCell(2) != null && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK && 
  		 		    	row.getCell(3) != null && row.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK && 
  		  		 		row.getCell(4) != null && row.getCell(4).getCellType() != Cell.CELL_TYPE_BLANK && 
  		  		 		row.getCell(5) != null && row.getCell(5).getCellType() != Cell.CELL_TYPE_BLANK && 
  		 		    	row.getCell(10) != null && row.getCell(10).getCellType() != Cell.CELL_TYPE_BLANK ){
  		 		    	 workExperienceDto=new WorkExperienceDto();
  		 		    	 
  		 		    	workExperienceDto.setPuesto(row.getCell(1).getStringCellValue());
  		 		    	workExperienceDto.setIdTipoJornada(new StringTokenizer(row.getCell(2).getStringCellValue(),"-").
															nextToken().trim());
  		 		    	workExperienceDto.setTrabajoActual(new StringTokenizer(row.getCell(3).getStringCellValue(),"-").
  		 		    										nextToken().trim());
  		 		    	workExperienceDto.setAnioInicio(String.valueOf((int)row.getCell(4).getNumericCellValue()));
  		 		    	workExperienceDto.setMesInicio(String.valueOf((int)row.getCell(5).getNumericCellValue()));
  		 		    	
  		 		    	if(row.getCell(6) != null && row.getCell(6).getCellType() != Cell.CELL_TYPE_BLANK)
  		 		    		workExperienceDto.setDiaInicio(String.valueOf((int)row.getCell(6).getNumericCellValue()));
  		 		    	
  		 		    	if(row.getCell(7) != null && row.getCell(7).getCellType() != Cell.CELL_TYPE_BLANK)
  		 		    		workExperienceDto.setAnioFin(String.valueOf((int)row.getCell(7).getNumericCellValue()));
  		 		    	
  		 		    	if(row.getCell(8) != null && row.getCell(8).getCellType() != Cell.CELL_TYPE_BLANK)
  		 		    		workExperienceDto.setMesFin(String.valueOf((int)row.getCell(8).getNumericCellValue()));
  		 		    		
  		 		    	if(row.getCell(9) != null && row.getCell(9).getCellType() != Cell.CELL_TYPE_BLANK)
  		 		    		workExperienceDto.setDiaFin(String.valueOf((int)row.getCell(9).getNumericCellValue()));
  		 		    	
  		 		    	workExperienceDto.setTexto(row.getCell(10).getStringCellValue());
  		 		    	////log4jMvs.debug("%&%   EXPERIENCIAS -> tipoClaveInterna:"+row.getCell(0).getCellType());
  		 		    	//log4jMvs.debug("\n\t\t"+claveInterna +"\t\t"+ row.getCell(1)+"\t"+ row.getCell(2)+
  		 		    						//"\t"+ row.getCell(3)+"\t"+ workExperienceDto.getAnioInicio()+
  		 		    						//"\t"+workExperienceDto.getMesInicio()+
  		 		    						//"\t"+ workExperienceDto.getDiaInicio()+"\t"+ workExperienceDto.getAnioFin()+
  		 		    						//"\t"+ workExperienceDto.getMesFin()+"\t"+ workExperienceDto.getDiaFin()+
  		 		    						//"\t"+ row.getCell(10));
  		 		    	
  		 		    	//se adiciona al cv correspondiente
 		 		    	//si no esta en la lista de errores
 		 		    	if(!lsErroresClaveInt.contains(claveInterna)){
 		 		    			addInCv( workExperienceDto, claveInterna);
 		 		    	} 
  		 		     }else{
  		 		    	if(row.getCell(0) != null){
  		 		    		putError(claveInterna, renglon,Constante.MSV_CVS_EXPER);	  		
  		 		    	}
  		 		     }
  				  //escolaridad
				  }else if(tipoHoja.equals(Constante.MSV_CVS_ESCOLAR)){
					  if(row.getPhysicalNumberOfCells() >= 8 && 
  		 		    	row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK && 
  		 		    	row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && 
  		 		    	row.getCell(2) != null && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK && 
  		 		    	row.getCell(3) != null && row.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK && 
  		  		 		row.getCell(4) != null && row.getCell(4).getCellType() != Cell.CELL_TYPE_BLANK && 
  		 		    	row.getCell(9) != null && row.getCell(9).getCellType() != Cell.CELL_TYPE_BLANK ){
						    academicBackgroundDto=new AcademicBackgroundDto();
						    
						    academicBackgroundDto.setIdGradoAcademico(new StringTokenizer(row.getCell(1).getStringCellValue(),"-").
																		nextToken().trim());
						    academicBackgroundDto.setIdEstatusEscolar(new StringTokenizer(row.getCell(2).getStringCellValue(),"-").
																		nextToken().trim());
						    academicBackgroundDto.setAnioInicio(String.valueOf((int)row.getCell(3).getNumericCellValue()));
						    academicBackgroundDto.setMesInicio(String.valueOf((int)row.getCell(4).getNumericCellValue()));
	  		 		    	
	  		 		    	if(row.getCell(5) != null && row.getCell(5).getCellType() != Cell.CELL_TYPE_BLANK)
	  		 		    		academicBackgroundDto.setDiaInicio(String.valueOf((int)row.getCell(5).getNumericCellValue()));
	  		 		    	if(row.getCell(6) != null && row.getCell(6).getCellType() != Cell.CELL_TYPE_BLANK)
	  		 		    		academicBackgroundDto.setAnioFin(String.valueOf((int)row.getCell(6).getNumericCellValue()));
	  		 		    	if(row.getCell(7) != null && row.getCell(7).getCellType() != Cell.CELL_TYPE_BLANK)
	  		 		    		academicBackgroundDto.setMesFin(String.valueOf((int)row.getCell(7).getNumericCellValue()));
	  		 		    		
	  		 		    	if(row.getCell(8) != null && row.getCell(8).getCellType() != Cell.CELL_TYPE_BLANK)
	  		 		    		academicBackgroundDto.setDiaFin(String.valueOf((int)row.getCell(8).getNumericCellValue()));
	  		 		    	
	  		 		    	academicBackgroundDto.setTexto(row.getCell(9).getStringCellValue());
	  		 		    	////log4jMvs.debug("%&%   CVS_ESCOLAR -> tipoClaveInterna:"+row.getCell(0).getCellType());
	  		 		    	//log4jMvs.debug("\n\t\t"+claveInterna +
	  		 		    			//	"\t\t"+ academicBackgroundDto.getIdGradoAcademico()+
	  		 		    			//	"\t\t\t"+ academicBackgroundDto.getIdEstatusEscolar()+
	 		    					//	"\t\t\t"+ academicBackgroundDto.getAnioInicio()+
	 		    					//	"\t\t"+academicBackgroundDto.getMesInicio()+
	 		    					//	"\t\t"+ academicBackgroundDto.getDiaInicio()+"\t\t"+ academicBackgroundDto.getAnioFin()+
	 		    					//	"\t"+ academicBackgroundDto.getMesFin()+"\t"+ academicBackgroundDto.getDiaFin()+
	 		    					//	"\t"+ row.getCell(9));
	  		 		    	
	  		 		    	
	  		 		    	//se adiciona al cv correspondiente
	 		 		    	//si no esta en la lista de errores
	 		 		    	if(!lsErroresClaveInt.contains(claveInterna)){
	 		 		    			addInCv(academicBackgroundDto, claveInterna);
	 		 		    	} 
					  }else{
						  if(row.getCell(0) != null){
							  //log4jMvs.error("De la hoja "+Constante.MSV_CVS_ESCOLAR+", falto almenos un dato en el renglon:"+renglon+
			 		    				//" , cuya clave interna es:"+row.getCell(0));
							  if(!lsErroresClaveInt.contains(claveInterna)){
				 		    	lsErroresClaveInt.add(claveInterna);
				 		      }
						  }
					  }		
				  //habilidades
				  }else if(tipoHoja.equals(Constante.MSV_CVS_HABILIDAD)){
					  if(row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK && 
		  		 		 (row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK &&
		  		 		 row.getCell(2) != null && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK)){
						  habilidadDto=new PersonSkillDto();
						  
						  habilidadDto.setIdDominio(new StringTokenizer(row.getCell(1).getStringCellValue(),"-").
													nextToken().trim());
						  habilidadDto.setDescripcion(row.getCell(2).getStringCellValue());
						  //log4jMvs.debug("\n\t\t"+claveInterna +
		 		    			//	"\t\t"+  habilidadDto.getIdDominio()+
		 		    			///	"\t\t\t"+  habilidadDto.getDescripcion());
						  
						  	//se adiciona al cv correspondiente
	 		 		    	//si no esta en la lista de errores
	 		 		    	if(!lsErroresClaveInt.contains(claveInterna)){
	 		 		    			addInCv(habilidadDto, claveInterna);
	 		 		    	} 
					  }else{
						  if(row.getCell(0) != null){
							  putError(claveInterna, renglon,Constante.MSV_CVS_HABILIDAD);
						  }
					  }
				  }
  			   renglon++;
  			 }
	  	}
	}
	
	/**
	 * Se adiciona los objetos en el CurriculumDto correspondiente
	 * @param tipoHoja, el tipo de hoja
	 * @param object, el objeto
	 * @param claveInterna, clave unica del cv
	 */
	private static void addInCv( Object object, String claveInterna){
		Iterator<CurriculumDto> itcv=curriculos.iterator();
		////log4jMvs.debug("#$% addInCv() -> claveInterna:"+claveInterna);
		while(itcv.hasNext()){
			CurriculumDto curriculumDto=itcv.next();
			////log4jMvs.debug("#$% addInCv() -> getClaveInterna:"+curriculumDto.getClaveInterna()+
				//	" claveInterna="+claveInterna);
			//misma clave interna
			if(curriculumDto.getClaveInterna().equals(claveInterna)){
			//	//log4jMvs.debug("---* addInCv() -> object:"+object.getClass().getName());
				//ubicacion
				if(object instanceof LocationInfoDto){
					if(curriculumDto.getLocalizacion() == null){
						curriculumDto.setLocalizacion(new ArrayList<LocationInfoDto>());
					}
					curriculumDto.getLocalizacion().add((LocationInfoDto)object);
				//experiencias
				}else if(object instanceof WorkExperienceDto){
					if(curriculumDto.getExperienciaLaboral() == null){
						curriculumDto.setExperienciaLaboral(new ArrayList<WorkExperienceDto>());
					}
					curriculumDto.getExperienciaLaboral().add((WorkExperienceDto)object);
				//escolaridad
				}else if(object instanceof AcademicBackgroundDto){
					if(curriculumDto.getEscolaridad() == null){
						curriculumDto.setEscolaridad(new ArrayList<AcademicBackgroundDto>());
					}
					curriculumDto.getEscolaridad().add((AcademicBackgroundDto)object);
				//Habilidades
				}else if(object instanceof PersonSkillDto){
					if(curriculumDto.getHabilidad() == null){
						curriculumDto.setHabilidad(new ArrayList<PersonSkillDto>());
					}
					curriculumDto.getHabilidad().add((PersonSkillDto)object);
				}
				break;
			}
			
		}
	}
	
	/**
	 * Se adiciona el error en la lista
	 * @param claveInterna
	 * @param renglon
	 * @param hoja
	 */
	static void putError(String claveInterna, int renglon,String hoja){
		//log4jMvs.error("De la hoja "+hoja+", falto almenos un dato en el renglon:"+renglon+
 				//" , cuya clave interna es:"+claveInterna);
	  if(!lsErroresClaveInt.contains(claveInterna)){
	    	lsErroresClaveInt.add(claveInterna);
	      }
	}
	
	
	/**
	 * Metodo encargado de persistir los datos de Curriculum, haciendo una busqueda en base al Identificador externo, con los siguientes escenarios:
	 * <ul><li>En caso de existir datos, realizar una purga en cascada y una actualización/inserción</li>
	 * <li>Si no existen datos, crear nuevos datos </li>
	 * </ul>
	 * para ambos casos, se debe devolver un IDentificador externo
	 * @param curriculumDto
	 * @return
	 * @throws Exception
	 */
	private Object saveOrReplaceCvExterno(CurriculumDto curriculumDto) throws Exception {
		log4j.debug("<saveOrReplaceCvExterno> Inicio");	
		//String claveInterna = curriculumDto.getClaveInterna(); //getIdExterno().trim();
		if(curriculumDto.getClaveInterna() == null || curriculumDto.getClaveInterna().trim().equals("")){
			log4j.error("<saveOrReplaceCvExterno> claveInterna es invalido "+curriculumDto.getClaveInterna());	
			curriculumDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
																	Mensaje.SERVICE_CODE_003,
																	Mensaje.SERVICE_TYPE_ERROR,
																	Mensaje.MSG_ERROR)) );
			return curriculumDto;
		}
		
		//2] buscar el registro con identificador externo
		log4j.debug("<saveOrReplaceCvExterno> Busqueda de persona con Clave: " + curriculumDto.getClaveInterna() +
				" en empresa: " + curriculumDto.getIdEmpresa());
		
		Long idPersona = null; 
		String idTextoClasificacion = null;
		Object objFind = personaDao.getDtoByIdExterno(curriculumDto.getClaveInterna(), 
													  Long.valueOf(curriculumDto.getIdEmpresa()));
		if(objFind instanceof ComunDto){
			ComunDto dto =  (ComunDto)objFind;
			if(dto.getCode()!=null){
				idPersona = new Long(dto.getCode());
				idTextoClasificacion = dto.getType();
			}
			log4j.debug("ComunDto.idPersonaDB = "+dto.getCode());
			log4j.debug("ComunDto.idTextoClasificacion = "+dto.getType());
		}
		
		log4j.debug("idPersona encontrado en query? " + idPersona );

		//3] Creamos nuevo POJO de persona 
		Persona personaDb = new Persona();
		personaDb.setFechaCreacion(DateUtily.getToday());
		personaDb.setFechaModificacion(DateUtily.getToday());			
		
		//4] asignamos valores de DTO a POJO
		personaDb = curriculumAssembler.getPersona(personaDb, curriculumDto, false);
		
		if(idPersona!=null){
			//5] si existen datos, se asigna idPersona a nuevo objeto y se realiza update
			//5.1]Persona (datos) existe en la Base y se toma el id
			log4j.debug("<salvaCurriculumExterno> encontro datos de persona, es Update");
			personaDb.setIdPersona(idPersona);
			log4j.debug("Se actualiza persona " + idPersona );
			
			//5.2]Eliminar datos en cascada
			domicilioDao.deleteByPersona(idPersona);
			escolaridadDao.deleteByPersona(idPersona);
			experienciaLaboralDao.deleteByPersona(idPersona);
			habilidadDao.deleteByPersona(idPersona);
			personaPaisDao.deleteByPersona(idPersona);
			
			//Una persona puede ser multiples candidatos
			currFilters= new HashMap<String, Object>();
			currFilters.put("persona.idPersona",idPersona);
		    List<Candidato> lsCandidato= candidatoDao.getByFilters(currFilters);
		    log4j.debug("<saveOrReplaceCvExterno> --> lsCandidato="+lsCandidato);
		    
		    //Se eliminan los candidato
		    if(lsCandidato != null && lsCandidato.size() > 0){
		    	Iterator<Candidato> itCandidato= lsCandidato.iterator();
		    	while(itCandidato.hasNext()){
		    		Candidato candidato=itCandidato.next();
		    		candidato.setModificado(true);
		    		
		    		//Se pone estatusOperativo 
					 candidato.setEstatusOperativo(new EstatusOperativo(
								 Constante.ESTATUS_CANDIDATO_OPERATIVO_INACTIVO,null));	
		    		candidatoDao.merge(candidato);
		    	} 
		    }
			
			  //** agregar otras relaciones, no usadas hasta ahora
			//5.3]Insertar datos nuevos
			personaDao.merge(personaDb);
		}else{
			//5b] si NO existen datos, Se Crea nueva persona
//				//5b.1] FIX se valida que no exista correo/empresa, en caso de q claveInterna no este registrada
//				Boolean existe = personaDao.existByEmail(curriculumDto.getEmail(), idEmpresa);
//				if(existe){
//					curriculumDto.setCode(Mensaje.SERVICE_CODE_009);
//					curriculumDto.setType(Mensaje.SERVICE_TYPE_ERROR);
//					curriculumDto.setMessage(Mensaje.MSG_MASIVE_DUPLICATEEMAIL);
//					
//					curriculumDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
//							Mensaje.SERVICE_CODE_009,Mensaje.SERVICE_TYPE_ERROR,
//							Mensaje.MSG_MASIVE_DUPLICATEEMAIL)) 
//							);
//					return curriculumDto;
//				}
//				
			log4j.debug("Se Crea nueva persona " );
			idPersona = (Long) personaDao.create(personaDb);
			
			log4j.debug("Se Creo nueva persona " + idPersona );
			RelacionEmpresaPersona relNueva = new RelacionEmpresaPersona();				
			Empresa empresaExt = new Empresa();
			log4j.debug("dto.IdEmpresa: " + curriculumDto.getIdEmpresa() );
			empresaExt.setIdEmpresa(new Long(curriculumDto.getIdEmpresa()));
			
			Rol rol = new Rol();
			rol.setIdRol(Constante.ROL_CANDIDATO);
			
			relNueva.setPersona(personaDb);
			relNueva.setEmpresa(empresaExt);
			relNueva.setClaveInterna(curriculumDto.getClaveInterna());
			relNueva.setFechaCreacion(DateUtily.getToday());
			relNueva.setRol(rol);
			relNueva.setEstatusRegistro(true);
			relNueva.setRepresentante(false);
			log4j.debug("tipoRel: " + rol);
			
			log4j.debug("Se Crea nueva Relacion de externo p:" + idPersona +" -E: "+curriculumDto.getIdEmpresa() );
			relacionEmpresaPersonaDao.create(relNueva);
		}
		
		//6.1] persona_pais
		log4j.debug("persona_pais -> getStPais=" + curriculumDto.getStPais()+ " getIdPais="+curriculumDto.getIdPais());
		if(curriculumDto.getIdPais() != null){					
			personaPais=new PersonaPais();
			pais=new Pais();
			pais.setIdPais(Long.parseLong(curriculumDto.getIdPais()));
			personaPais.setPais(pais);				
			personaPais.setPersona(personaDb);
			personaPaisDao.create(personaPais);		
		}			
		
		//6.1] domicilios
		LocationInfoDto infoDto;
		Iterator<LocationInfoDto> itLocationInfoDto = curriculumDto.getLocalizacion().iterator();
		//Set<Domicilio> domiciliosPersona = personaDb.getDomicilios();		
		while(itLocationInfoDto.hasNext()){
			infoDto = itLocationInfoDto.next();
			//Se buscan los id's correspondientes
			setIdsLocation(infoDto);
			//Se verifica si hay algun error
			if(infoDto.getMessages() != null){
				curriculumDto.setMessages(infoDto.getMessages());
				return curriculumDto;
			}
			log4j.debug("getIdMunicipio="+infoDto.getIdMunicipio()+
						" getIdAsentamiento="+infoDto.getIdAsentamiento());
			Domicilio domicilio=converter.convert(infoDto, Domicilio.class);
			if(infoDto.getLatitud() != null)
				domicilio.setGoogleLatitude(new BigDecimal(infoDto.getLatitud()));
			if(infoDto.getLongitud() != null)
				domicilio.setGoogleLongitude(new BigDecimal(infoDto.getLongitud()));
			domicilio.setPersona(personaDb);
			domicilioDao.create(domicilio);
			//domiciliosPersona.add(domicilio);
		}
		//personaDb.setDomicilios(domiciliosPersona);
		
		
		//6.2] Historial Academico
		if(curriculumDto.getEscolaridad() != null){
			log4j.debug("agregando Historial Academico....");
			Iterator<AcademicBackgroundDto> itAcademic = curriculumDto.getEscolaridad().iterator();
			Set<Escolaridad> escolaridadPersona = personaDb.getEscolaridads();
			while(itAcademic.hasNext()){
				Escolaridad escolaridad = curriculumAssembler.getEscolaridad(
													new Escolaridad(), itAcademic.next());
				escolaridad.setPersona(personaDb);
				escolaridadDao.create(escolaridad);
				escolaridadPersona.add(escolaridad);
			}
			personaDb.setEscolaridads(escolaridadPersona);
		}
		
		//6.3] Experiencias Laborales
		if(curriculumDto.getExperienciaLaboral() != null){
			log4j.debug("agregando Experiencias Laborales....");
			Iterator<WorkExperienceDto> itExperiencia = curriculumDto.getExperienciaLaboral().iterator();
			Set<ExperienciaLaboral> experienciaPersona = personaDb.getExperienciaLaborals();
			while(itExperiencia.hasNext()){
				ExperienciaLaboral experiencia = curriculumAssembler.getExperienciaLaboral(
													new ExperienciaLaboral(), itExperiencia.next());
				experiencia.setPersona(personaDb);
				experienciaLaboralDao.create(experiencia);
				experienciaPersona.add(experiencia);
			}
			personaDb.setExperienciaLaborals(experienciaPersona);
		}
		//6.3] Habilidades
		if(curriculumDto.getHabilidad() != null){
			Iterator<PersonSkillDto> itHabilidadDto=curriculumDto.getHabilidad().iterator();
			Set<Habilidad> habilidads= personaDb.getHabilidads();
			while(itHabilidadDto.hasNext()){
				Habilidad  habilidad=converter.convert(itHabilidadDto.next(), Habilidad.class);
				habilidad.setPersona(personaDb);
				habilidadDao.create(habilidad);
				habilidads.add(habilidad);
			}
			personaDb.setHabilidads(habilidads);
		}
		log4j.debug("curriculumDto.getIdAreaPrincipal()");
		
		//areaPersona
		if(curriculumDto.getIdAreaPrincipal() != null){
			 AreaPersona areaPersona = new AreaPersona();
			 Area area = new Area();
			 area.setIdArea(Long.parseLong(curriculumDto.getIdAreaPrincipal()));
			 areaPersona.setArea(area);
			 areaPersona.setPersona(personaDb);
			 areaPersona.setPrincipal(true);
			 areaPersona.setConfirmada(false);
			 areaPersona.setPersonal(true);
			 
			 log4j.debug("<saveOrReplaceCvExterno> create");
			 areaPersonaDao.create(areaPersona );
		}
		
		return personaDb;
		
	}	
	
	/**
	 * 
	 * @param infoDto
	 * @throws JSONException
	 */
	private void setIdsLocation(LocationInfoDto locationInfoDto) throws Exception{
		//Se obtienen los id's correspondientes dado los strings
		jsonObject = new JSONObject();
		jsonObject.put("country", locationInfoDto.getStPais());
		
		if(locationInfoDto.getStEstado() != null)
			jsonObject.put("admin_area_level_1",locationInfoDto.getStEstado());
		if(locationInfoDto.getStMunicipio() != null)
			jsonObject.put("locality",locationInfoDto.getStMunicipio());
		if(locationInfoDto.getStColonia() != null)
			jsonObject.put("neighborhood",locationInfoDto.getStColonia());		
		if(locationInfoDto.getCodigoPostal() != null)
			jsonObject.put("postalCode",StringUtils.leftPad(locationInfoDto.getCodigoPostal(), 5, "0"));
		
		log4j.debug("setIdsLocation() Se manda a llamar el servico de get_Settlement");
		//Se manda a llamar el servico de get_Settlement
		List<SettlementDto> lsSettlementDto= gson.fromJson((String)restJsonService.
					serviceRJTransacStruc(jsonObject.toString(),
					new StringBuilder(Constante.URI_SETTLEMENT).append(Constante.URI_GET).toString()),
					new TypeToken<List<SettlementDto>>(){}.getType());
		log4j.debug("setIdsLocation() lsSettlementDto="+lsSettlementDto.get(0).getPersistence());

		if(lsSettlementDto.size() > 0 && lsSettlementDto.get(0).getPersistence() != null){
			if(lsSettlementDto.get(0).getPersistence().getIdCountry() != null){
				locationInfoDto.setIdPais(lsSettlementDto.get(0).getPersistence().getIdCountry().toString());						
			}
			if(lsSettlementDto.get(0).getPersistence().getIdAdmin_area_level_1() != null){
				locationInfoDto.setIdEstado(lsSettlementDto.get(0).getPersistence().getIdAdmin_area_level_1().toString());
			}
			if(lsSettlementDto.get(0).getPersistence().getIdLocality() != null){
				locationInfoDto.setIdMunicipio(lsSettlementDto.get(0).getPersistence().getIdLocality().toString());
			}
			if(lsSettlementDto.get(0).getPersistence().getIdNeighborhood() != null){
				locationInfoDto.setIdAsentamiento(lsSettlementDto.get(0).getPersistence().getIdNeighborhood().toString());
			}
			if(lsSettlementDto.get(0).getPersistence().getPostalCode() != null){
				locationInfoDto.setCodigoPostal(lsSettlementDto.get(0).getPersistence().getPostalCode().toString());
			}
		}else{
			//No se encontraron los ids
			locationInfoDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_003,Mensaje.SERVICE_TYPE_ERROR,Mensaje.MSG_ERROR)) );			
		}
	}
	
	/**
	 * Crea un nuevo registro  en el contexto persona (por email y PWD)
	 * @param curriculumDto, objeto que contiene la informacion para usar registro 
	 * @return  la respuesta correspondiente a la tarea
	 * @throws Exception 
	 */
	public String createDetail(CurriculumDto curriculumDto) throws Exception {
		//Se verifica si existe rol inicial
		//Solo debe de haber uno
		currFilters  = new HashMap<String, Object>();
		currFilters.put("rolInicial",new Boolean(true));
		List<Rol> lsRol=rolDao.getByFilters(currFilters);
		
		if(lsRol != null && lsRol.size() == 1){
			EmpresaConf empresaConf=empresaConfDao.read(Long.parseLong(curriculumDto.getIdEmpresaConf()));
			log4j.debug("&%%&& empresaConf:"+empresaConf );
			if(empresaConf != null){
				Date fechaCreacion=DateUtily.getToday();
				Persona persona = new Persona();
				log4j.debug("&&& getEmail:"+curriculumDto.getEmail()+" password:"+curriculumDto.getPassword());
				persona.setEmail(curriculumDto.getEmail());
				EstatusInscripcion estatusInscripcion=new EstatusInscripcion();
				estatusInscripcion.setIdEstatusInscripcion(Constante.ESTATUS_INSCRIPCION_CREADO.longValue());
				persona.setEstatusInscripcion(estatusInscripcion);
				persona.setFechaCreacion(fechaCreacion);
				persona.setFechaConfirmarInscripcion(fechaCreacion);
				persona.setNumeroConfirmarInscripcion((short)0);
				persona.setNumeroDebePublicar((short)0);
				
				if(curriculumDto.getNombre() != null){
					persona.setNombre(curriculumDto.getNombre());
				}				
				
				if(curriculumDto.getApellidoPaterno() != null){
					persona.setApellidoPaterno(curriculumDto.getApellidoPaterno());
				}
				
				if(curriculumDto.getApellidoMaterno() != null){
					persona.setApellidoMaterno(curriculumDto.getApellidoMaterno());
				}
				
				//Se persiste la persona
				curriculumDto.setIdPersona(((Long)personaDao.create(persona)).toString());
				log4j.debug("&%%&& idPersona:"+curriculumDto.getIdPersona()+
						    " pass:"+curriculumDto.getPassword());
				
				//Inserta el nuevo password
				HistoricoPassword historicoPassword=new HistoricoPassword();
				historicoPassword.setFecha(fechaCreacion);
				historicoPassword.setPassword(curriculumDto.getPassword());
				historicoPassword.setPersona(persona);
				
				if(curriculumDto.getPasswordIniSistema() != null){
					historicoPassword.setPswIniSystem(curriculumDto.getPasswordIniSistema());
				}
				
				Object idTmp = historicoPasswordDao.create(historicoPassword);
				log4j.debug("idHistoricoPassword: " + idTmp );
				
				// obtener empresa por el idEmpresaConf
				log4j.debug("&%%&& getIdEmpresaConf:"+curriculumDto.getIdEmpresaConf());
				
				//se crea un registro en la tabla Relacion_empresa-persona				
				RelacionEmpresaPersona relacionEmpresaPersona=new RelacionEmpresaPersona();
				relacionEmpresaPersona.setEmpresa(empresaConf.getEmpresa());
				relacionEmpresaPersona.setFechaCreacion(DateUtily.getToday());
				relacionEmpresaPersona.setPersona(persona);
				relacionEmpresaPersona.setRepresentante(false);
				relacionEmpresaPersona.setEstatusRegistro(true);
				relacionEmpresaPersona.setRol(lsRol.get(0));
				
				log4j.debug("rol_inicial: " + relacionEmpresaPersona.getRol().getDescripcion() );
				
				//Se crea el registro
				curriculumDto.setIdRelacionEmpresaPersona(((Long)relacionEmpresaPersonaDao.
														create(relacionEmpresaPersona)).toString());
				
				log4j.debug("idRelacionEmpresaPersona: " + curriculumDto.getIdRelacionEmpresaPersona() );
				
				return Mensaje.SERVICE_MSG_OK_JSON;
				
			}else{
				log4j.error("No existe, en la tabla EMPRESA_CONF, el IdEmpresaConf:"+
														curriculumDto.getIdEmpresaConf());
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
													Mensaje.SERVICE_CODE_002,
													Mensaje.SERVICE_TYPE_FATAL,
													Mensaje.MSG_ERROR));
			}			
		}else{
			log4j.error("No hay Rol unico o hay mas de un Rol:");
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
												Mensaje.SERVICE_CODE_002,
												Mensaje.SERVICE_TYPE_FATAL,
												Mensaje.MSG_ERROR));
		}
		
	}
}