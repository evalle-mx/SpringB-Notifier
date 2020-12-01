package net.tce.task.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.inject.Inject;
import net.tce.admin.service.AdminService;
import net.tce.admin.service.ApplicantService;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.admin.service.RestJsonService;
import net.tce.app.exception.SystemTCEException;
import net.tce.cache.ParametrosCache;
import net.tce.dto.ControlProcesoDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.PosibleCandidatoDto;
import net.tce.dto.PosicionDto;
import net.tce.dto.SchedulerDto;
import net.tce.dto.VacancyDto;
import net.tce.solr.document.SolrDocTopics;
import net.tce.task.service.SchedulerClassifiedDocService;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import net.tce.dao.AreaDao;
import net.tce.dao.AreaPerfilDao;
import net.tce.dao.AreaPersonaDao;
import net.tce.dao.BitacoraTrackCandDao;
import net.tce.dao.BitacoraTrackPostDao;
import net.tce.dao.CandidatoDao;
import net.tce.dao.ControlProcesoDao;
import net.tce.dao.DocumentoClasificacionDao;
import net.tce.dao.PosibleCandidatoDao;
import net.tce.dao.TrackingPostulanteDao;
import net.tce.model.Area;
import net.tce.model.AreaPerfil;
import net.tce.model.AreaPersona;
import net.tce.model.Candidato;
import net.tce.model.ControlProceso;
import net.tce.model.DocumentoClasificacion;
import net.tce.model.EstatusOperativo;
import net.tce.model.EstatusProceso;
import net.tce.model.Persona;
import net.tce.model.PosibleCandidato;
import net.tce.model.TipoProceso;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Transactional
@Service("schedulerClassifiedDocService")
public class SchedulerClassifiedDocServiceImpl implements SchedulerClassifiedDocService {
	
	private @Value("${solr.tipodoc1.persona}")	Integer docTypePersona;
	private @Value("${solr.tipodoc1.posicion}")	Integer docTypePosicion;
	private @Value("${solr.tipodoc1.empresa}")	Integer docTypeEmpresa;

	static Logger log4j = Logger.getLogger( SchedulerClassifiedDocServiceImpl.class );
	static final Logger log4jSyncro = Logger.getLogger("SINCRO");

	
	Map<String,String> mapReplace;
    JSONObject jsonObject;
    EmpresaParametroDto empresaParametroDto;
    StringBuilder sb_solr,sb_per_area,sb_perf_area;
    List<AreaPersona> lsAreaPersona;
    List<AreaPerfil> lsAreaPerfil;
    Iterator<SolrDocTopics> itSolrDocTopics ;
    Iterator<AreaPersona> itAreaPersona;
    Iterator<AreaPerfil> itAreaPerfil;
    AreaPersona  areaPersona;
    AreaPerfil areaPerfil;
    List<SolrDocTopics> lsSolrDocTopics,lsRespSolrDocTopics ;
    SolrDocTopics solrDocTopics;
    List<DocumentoClasificacion> lsDocumentoClasificacion;
	Iterator<DocumentoClasificacion> ltDocumentoClasificacion;
	DocumentoClasificacion documentoClasificacion;
	PosibleCandidatoDto posibleCandidatoDto;
	Iterator<PosibleCandidatoDto> itPosibleCandidatoDto;
	Long idPosicion=null;
	Long idPosibleCandidato=null;
	Candidato candidato;
	PosicionDto posicionDto;
	StringTokenizer st;
	List<MensajeDto> lsRespMensajeDto;
	EstatusOperativo estatusOperativo;
	Object objResponse;
	int cont;

    @Autowired
	private EmpresaParametroService empresaParametroService;	
    
    @Autowired
    private AdminService adminService;

	@Autowired
	private ControlProcesoDao controlProcesoDao;

	@Autowired
	private AreaDao areaDao;

	@Autowired
	private DocumentoClasificacionDao documentoClasificacionDao;
	
	@Autowired
	private AreaPersonaDao areaPersonaDao;

	@Autowired
	private AreaPerfilDao areaPerfilDao;

	@Autowired
	private BitacoraTrackCandDao bitacoraTrackCandDao;
	
	@Autowired
	private BitacoraTrackPostDao bitacoraTrackPostDao;
	
	@Autowired
	private CandidatoDao candidatoDao;
	
	@Autowired
	private PosibleCandidatoDao posibleCandidatoDao;
	
	@Autowired
	private RestJsonService restJsonService;
		
	@Autowired
	private ApplicantService applicantService;
	
	@Autowired
	private TrackingPostulanteDao trackingPostulanteDao;
		
	
	@Inject
	Gson gson;
	
	/**
	 * Actualiza el contador de número de documentos clasificados para el modelo actual
	 * @param SchedulerDto
	 * @return void
	 */
	public void setNumDocumentsByQueryForModel(SchedulerDto schedulerDto)throws Exception{
		
		log4j.debug("setNumDocumentsByQueryForModel -> getCurrentModel :" + schedulerDto.getCurrentModel()+
				 " getQueryForModel=" + schedulerDto.getQueryForModel());
	    	try {
	    		jsonObject = new JSONObject();
				jsonObject.put("query", schedulerDto.getQueryForModel());
	    		
	    		List<SolrDocTopics> lsSolrDocTopics=(List<SolrDocTopics>)restJsonService.serviceRJOperNoStruc(
	    											jsonObject.toString(),Constante.OPERATIVE_COUNTBYQUERY_ENDPOINT);
	    		log4j.debug("setNumDocumentsByQueryForModel -> getNumDocs="+lsSolrDocTopics.get(0).getNumeroDocs());
	    		schedulerDto.setCurrentNumDocsByQueryForModel(Long.parseLong(lsSolrDocTopics.get(0).getNumeroDocs()));
				log4j.debug("setNumDocumentsByQueryForModel -> resp="+schedulerDto.getCurrentNumDocsByQueryForModel());
			} catch (Exception e) {
				schedulerDto.setCurrentNumDocsByQueryForModel(0L);
				log4j.error(" Error al obtener el numero de docs de solr "+e);
				e.printStackTrace();
				throw new SystemTCEException(" Error al obtener el numero de docs de solr "+e);
			}			
	}

	
	/**
	 * Verifica si existe un proceso específico ejecutándose actualmente
	 * @author Osy
	 * @param SchedulerDto schedulerDto
	 * @return Boolean
	 */
	public Boolean runningProcess(SchedulerDto schedulerDto){		
		if(controlProcesoDao.numberOpenProcess(schedulerDto) > 0){
			return true;			
		}else{		
			return false;
		}
	}
	
	/**
	 * Registra el inicio de un proceso específico (controlProceso)
	 * @author Osy
	 * @param SchedulerDto schedulerDto
	 * @return SchedulerDto
	 */
	public synchronized SchedulerDto beginProcess(SchedulerDto schedulerDto){
		log4j.debug("<beginProcess> getIdTipoProceso :" + schedulerDto.getIdTipoProceso());
		
		TipoProceso tipoProceso = new TipoProceso();
		tipoProceso.setIdTipoProceso(schedulerDto.getIdTipoProceso());
		EstatusProceso estatusProceso = new EstatusProceso();
		estatusProceso.setIdEstatusProceso(Constante.PROCESS_STATUS_IN_PROGRESS);
		ControlProceso controlProceso = new ControlProceso();
		controlProceso.setTipoProceso(tipoProceso);
		controlProceso.setEstatusProceso(estatusProceso);
		controlProceso.setFechaInicio(DateUtily.getToday());
		
		//si no es un demonio
		if(schedulerDto.getIdPersona() != null){
			Persona persona=new Persona();
			persona.setIdPersona(Long.parseLong(schedulerDto.getIdPersona()));
			controlProceso.setPersona(persona);
		}
		schedulerDto.setIdControlProceso((Long) controlProcesoDao.create(controlProceso));
		log4j.debug("<beginProcess>  idControlProcesoCreado:" + schedulerDto.getIdControlProceso());
		return schedulerDto;		
	}

	/**
	 * Registra el fin de un proceso específico (controlProceso)
	 * @author Osy
	 * @param SchedulerDto schedulerDto
	 * @return SchedulerDto
	 */
	public SchedulerDto endProcess(SchedulerDto schedulerDto){
		log4j.debug("<endProcess> Inicio...");
		log4j.debug("<endProcess> getIdControlProceso :" + schedulerDto.getIdControlProceso());
		log4j.debug("<endProcess> resultado :" + schedulerDto.getResultado());
		
		ControlProceso controlProceso = new ControlProceso();
		controlProceso = controlProcesoDao.read(schedulerDto.getIdControlProceso());		
		controlProceso.setFechaFin(DateUtily.getToday());
		EstatusProceso estatusProceso = new EstatusProceso();
		estatusProceso.setIdEstatusProceso(schedulerDto.getIdEstatusProceso());
		controlProceso.setEstatusProceso(estatusProceso);
		controlProceso.setResultado(schedulerDto.getResultado());
		//controlProcesoDao.saveOrUpdate(controlProceso);
		controlProcesoDao.merge(controlProceso);
		log4j.debug("<endProcess> merge Fin...");
		return schedulerDto;		
	}

	/**
	 * Obtiene la versión del modelo de clasificación actual
	 * @author Osy
	 * @param SchedulerDto schedulerDto
	 * @return String
	 */
	public String getCurrentModelVersion(SchedulerDto schedulerDto){
		log4j.debug("<getCurrentModelVersion> getIdTipoProceso..." + schedulerDto.getIdTipoProceso());
		String currentModel=controlProcesoDao.lastResult(schedulerDto);	
		
		//Se guarda en cache
		ParametrosCache.putChmGeneral(Constante.PARAM_GENERAL_CURRRENTMODEL, currentModel);
		
		return 	currentModel;		
	}

	/**
	 * Obtiene la siguiente versión del modelo de clasificación
	 * @author Osy
	 * @param SchedulerDto schedulerDto
	 * @return String
	 */
	public String getNewModelVersion(){
		return String.valueOf(Integer.parseInt(ParametrosCache.getChmGeneral(Constante.PARAM_GENERAL_CURRRENTMODEL)) + 1);
	}

	/**
	 * Obtiene la versión anterior del modelo de clasificación
	 * @author Osy
	 * @param SchedulerDto schedulerDto
	 * @return String
	 */
	public String getPreviousModelVersion(){

		int current=Integer.parseInt(ParametrosCache.getChmGeneral(Constante.PARAM_GENERAL_CURRRENTMODEL));
		
		//Se guarda en cache
	    ParametrosCache.putChmGeneral(Constante.PARAM_GENERAL_PREVIOUSMODEL, String.valueOf(current > 0 ? current -1 :current));

		return 	ParametrosCache.getChmGeneral(Constante.PARAM_GENERAL_PREVIOUSMODEL);		
	}
	
	
	/**
	 * sincroniza los documentos  solr y confirmados
	 * @param SchedulerDto
	 * @return SchedulerDto
	 * @throws Exception 
	 */
	public Object synchronizeDocs(SchedulerDto schedulerDto) throws Exception {
		
		if(schedulerDto != null && 
		   (schedulerDto.getIdEmpresaConf() != null &&
		   schedulerDto.getIdPersona() != null &&
		   schedulerDto.getTipoSync() != null &&
		   UtilsTCE.isNumeric(schedulerDto.getTipoSync()))){
			
			log4j.debug("<synchronizeDocs> getTipoSync:"+ schedulerDto.getTipoSync());

			//se analiza 
			if(schedulerDto.getTipoSync().equals(Constante.TIPO_SYNC_DOCS_SOLR)){
				return synchronizeSolrDocs(schedulerDto);
			}else if(schedulerDto.getTipoSync().equals(Constante.TIPO_SYNC_DOCS_CONFIR)){
				return synchronizeClassificationDocs(schedulerDto);
			}else{
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006, 
						Mensaje.SERVICE_TYPE_ERROR, 
						Mensaje.MSG_ERROR));
			}			
		}else{
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006, 
					Mensaje.SERVICE_TYPE_ERROR, 
					Mensaje.MSG_ERROR));
		}		
	}
	

	/**
	 * Trae los documentos de Solr a la BD transaccional(tabla documento_clasificacion) y
	 * ejecuta el proceso de confirmación automática
	 * Esta operación puede ejecutarse bajo demanda o por scheduler
	 * @param SchedulerDto
	 * @return SchedulerDto
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Object synchronizeSolrDocs(SchedulerDto schedulerDto) throws Exception {
		SolrDocTopics solrDocTopics;
		lsSolrDocTopics = new ArrayList<SolrDocTopics>();;
		int numSolrDocs=0;
		String respService;
		
		//Se registra el proceso inicial
		schedulerDto.setIdTipoProceso(Constante.TIPO_PROCESO_SYNC_DOCS_SOLR.longValue());
		beginProcess(schedulerDto);
		
		//se obtienen los ids(documento_texto) de los docs publicados nuevos o modificados
		lsDocumentoClasificacion = documentoClasificacionDao.getDocsNoSql();		
		log4j.debug("<sincronizar_solr> lsIdsDocText:"+ lsDocumentoClasificacion.size());

		//si hay ids traerlos de solr a la bd
		if(lsDocumentoClasificacion != null && lsDocumentoClasificacion.size() > 0){
			
			//Primero se obtienen los ids de docs de Solr (nuevos o modificados) y se genera el query correspondiente
			cont=0;
			sb_solr= new StringBuilder(" id:(");
			sb_per_area=new StringBuilder();
			sb_perf_area=new StringBuilder();
			ltDocumentoClasificacion= lsDocumentoClasificacion.iterator();
			
			//hay mas
			while(ltDocumentoClasificacion.hasNext()){
				documentoClasificacion =ltDocumentoClasificacion.next();
				
				cont++;
				
				/*log4j.debug("<sincronizar_solr> getIdPersona:"+ 
						(documentoClasificacion.getPersona() == null ? null:
							documentoClasificacion.getPersona().getIdPersona())+
						" cont="+cont);*/
				
				//Para el queryNoSql
				sb_solr.append(documentoClasificacion.getIdTextoClasificacion());					
				
				
				//cuando se llega al limite o hay dosc restantes,
				//se ejecuta el query a solr
				if(cont == Constante.NUM_LIMITE_QUERY_SOLR.intValue() ||
				  !ltDocumentoClasificacion.hasNext()	) {
					
					try{	
						sb_solr.append(")");
						jsonObject= new JSONObject();
						jsonObject.put("query", sb_solr.toString());	
						
						//Se llama al servicio
						objResponse= restJsonService.serviceRJOperNoStruc(jsonObject.toString(), 
												    Constante.OPERATIVE_SEARCHCLASSBYQUERY_ENDPOINT);
						log4j.debug("<sincronizar_solr> objResponse:"+ objResponse.getClass().getName());
						//operationalTceResponse =gson.fromJson(stringResponse,
												//new TypeToken<List<SolrDocTopics>>(){}.getType());
						if(objResponse instanceof List){
							
							//Se adiciona la nueva lista a toda
							lsSolrDocTopics.addAll((List<SolrDocTopics>)objResponse);
							
							log4j.debug("<sincronizar_solr> lsSolrDocTopics="+lsSolrDocTopics.size());
							
							//se reinician 
							cont=0;
							sb_solr.delete(0, sb_solr.length());
							sb_solr.append(" id:(");
							
						}else{
							log4j.error("ERROR: no se pudo realizar la tarea ");
							//se registra el final del proceso 
							schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_ERROR.longValue());
							schedulerDto.setResultado("Error en la busqueda de documentos en Solr");
							endProcess(schedulerDto);
							log4j.debug(objResponse);
							return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
																	Mensaje.SERVICE_CODE_000, 
																	Mensaje.SERVICE_TYPE_FATAL, 
																	"ERROR EN OPERATIONAL"));
						}
					}catch (Exception e){
						log4j.error("<sincronizar_solr> ERROR: al Comunicarse con OperationalNoStructured [" 
									+ Constante.OPERATIVE_SEARCHCLASSBYQUERY_ENDPOINT +"] : "+  e);
						//se registra el final del proceso 
						schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_ERROR.longValue());
						schedulerDto.setResultado("Error en la busqueda de documentos en Solr: No se pudo comunicar con AppOperationalNoStructured.");
						endProcess(schedulerDto);
						throw new SystemTCEException("ERROR: al Comunicarse con OperationalNoStructured [" 
													+ Constante.OPERATIVE_SEARCHCLASSBYQUERY_ENDPOINT +"] : "+  e);
					}
				}else {
					//no se ha llegado al limite
					sb_solr.append(" OR ");
				}
				
				//solo para persona
				if(documentoClasificacion.getPersona() != null){
					
					//Para el query de areas
					sb_per_area.append(",").append(documentoClasificacion.getPersona().getIdPersona());										
				}
				
				//Posicion y perfil
				if(documentoClasificacion.getPerfil() != null){
					sb_perf_area.append(",").append(documentoClasificacion.getPerfil().getIdPerfil());	
				}
			}
			log4j.debug("<sincronizar_solr> sb_per_area:"+ 
						(sb_perf_area != null ? null:sb_per_area.substring(1, sb_per_area.length()).toString()));
			log4j.debug("<sincronizar_solr> sb_perf_area:"+ 
						(sb_perf_area != null ? null:sb_perf_area.substring(1, sb_perf_area.length()).toString()));
			log4j.debug("<sincronizar_solr>final lsSolrDocTopics="+lsSolrDocTopics.size());

			
			//se obtinene los dosc de solr
			if (lsSolrDocTopics.size() > 0){
				
				//Si recibe respuesta, evaluar si es lista de Docs o de Errores en Operational
				//Encabezado o primer documento
				 solrDocTopics = lsSolrDocTopics.get(0);
				if(solrDocTopics.getCode()!=null && solrDocTopics.getMessage()!=null){
					log4j.error("<sincronizar_solr> ERROR: en AppOperationalNoStructured" + solrDocTopics.getMessage() );
					//se registra el final del proceso 
					schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_ERROR.longValue());
					schedulerDto.setResultado("ERROR: en AppOperationalNoStructured" + solrDocTopics.getMessage());
					endProcess(schedulerDto);
					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_000, Mensaje.SERVICE_TYPE_FATAL, solrDocTopics.getMessage()));
				}
				
				//Se obtiene una lista de objetos area_persona
				if(sb_per_area != null && sb_per_area.length() > 0){					
					lsAreaPersona=areaPersonaDao.get(sb_per_area.substring(1, sb_per_area.length()).toString());
				}
				
				//Se obtiene una lista de objetos area_persona
				if(sb_perf_area != null && sb_perf_area.length() > 0){
					lsAreaPerfil=areaPerfilDao.get(sb_perf_area.substring(1, sb_perf_area.length()).toString());
				}
				
				log4j.debug("<sincronizar_solr>  lsAreaPersona="+lsAreaPersona+
						" lsAreaPerfil="+lsAreaPerfil);
				
				//Numero de documentos en Solr
				numSolrDocs= lsSolrDocTopics.size();
				
				//Se persiste en la BD
				boolean coincidePrincipal;
				StringBuilder sbAreas=new StringBuilder();
				ltDocumentoClasificacion= lsDocumentoClasificacion.iterator();			
				
				while(ltDocumentoClasificacion.hasNext()){
					documentoClasificacion=ltDocumentoClasificacion.next();
					documentoClasificacion.setEstatusClasificacion(
											Integer.parseInt(solrDocTopics.getClassified()));
					log4j.debug("<sincronizar_solr>  dc_getIdTC="+
								documentoClasificacion.getIdTextoClasificacion());
					
					itSolrDocTopics =  lsSolrDocTopics.iterator();
					while(itSolrDocTopics.hasNext()){
						solrDocTopics =itSolrDocTopics.next();
						
						//si coinciden los ids Solr
						if(solrDocTopics.getId().equals(documentoClasificacion.getIdTextoClasificacion())){		
							coincidePrincipal=false;
							sbAreas.delete(0, sbAreas.length());
							
							log4j.debug("<sincronizar_solr> coinciden solrDocTopics_IdTC()="+solrDocTopics.getId()+
									" getCategory="+solrDocTopics.getCategory()+
									" getCategoriesanalysis="+solrDocTopics.getCategoriesanalysis());
							
							//Solo para cv's
							//Se verifica si hay match entre las areas (principal_manualmente & modelo_clasificada)
							if(lsAreaPersona != null && lsAreaPersona.size() > 0 && 
								documentoClasificacion.getPersona() != null){
								itAreaPersona =lsAreaPersona.iterator();
								while(itAreaPersona.hasNext()){
									areaPersona =itAreaPersona.next();
									//coinciden id's
									if(areaPersona.getPersona().getIdPersona() == documentoClasificacion.getPersona().getIdPersona()){
										
										log4j.debug("<sincronizar_solr> Persona ->  getIdArea="+areaPersona.getArea().getIdArea());
										
										//Se verifica si el area principal(manual) coincide con una del modelo
										if(solrDocTopics.getCategory() != null && 
										   solrDocTopics.getCategory().length() > 0){
											
											 //hay coincidencia
											 if(Arrays.asList(solrDocTopics.getCategory().split(",")).
												contains(String.valueOf(areaPersona.getArea().getIdArea()))){
												 
												//el primero es principal
												if(areaPersona.isPrincipal()){												
													log4j.debug("<sincronizar_solr> el area coincide en la categoria");													
													documentoClasificacion.setArea(areaPersona.getArea());
													documentoClasificacion.setEstatusClasificacion(
															 	          Constante.ESTATUS_CLAS_VERIFICADO_NOMODELO);
													coincidePrincipal=true;
												}
												log4j.debug("<sincronizar_solr> coinciden getIdPersona="+
														areaPersona.getPersona().getIdPersona()+
														" area_isPrincipal="+areaPersona.isPrincipal()+
														" coincidePrincipal="+coincidePrincipal); 
												
												//si coincide la area principal entonces se aplica la misma politica 
												//a las demas areas que coincidan
												if(coincidePrincipal){
												 //areas que coincidieron
												 sbAreas.append(Constante.DELIMITADOR_AREAS_CONFIRM).
												 append(String.valueOf(areaPersona.getArea().getIdArea()));
												 
												 //Se persiste en area_persona
												 areaPersona.setConfirmada((true));
												 areaPersonaDao.update(areaPersona);																		
											  }
											}
										}	
									}
								}
							}
							
							//Se actualiza la tabla documento_clasificacion
							documentoClasificacion.setCategoria(solrDocTopics.getCategory());
							documentoClasificacion.setVersionModelo(solrDocTopics.getModelversion());
							documentoClasificacion.setCategoriasAnalisis(solrDocTopics.getCategoriesanalysis() == null ? "":
																			solrDocTopics.getCategoriesanalysis().toString());
							documentoClasificacion.setFechaModificacion(DateUtily.getToday());
							
							//Solo los doc que hubo areas de coincidencia
							if(sbAreas.length() > 0){
								documentoClasificacion.setAreasConfirmadas(sbAreas.substring(1, sbAreas.length()).toString());
								documentoClasificacion.setSincronizado(true);
								documentoClasificacion.setAutomatico(true);
								
								//Se actualiza el doc en Solr								
								try {
									lsRespSolrDocTopics=updateDocSolr( documentoClasificacion);									
									log4j.debug("<sincronizar_solr> result :" +
											(lsRespSolrDocTopics != null ? lsRespSolrDocTopics.size():null));
									
									//Si hay error en solr
									if(lsRespSolrDocTopics != null && lsRespSolrDocTopics.size() > 0){
										log4jSyncro.fatal("<sincronizar_solr> Error fatal al actualizar en Solr el id_doc:"+
																documentoClasificacion.getIdTextoClasificacion());
									}else{
										log4j.debug("<sincronizar_solr> doc confirmado:" + documentoClasificacion.getIdTextoClasificacion());
									}
								} catch (Exception e) {
										log4jSyncro.fatal("<sincronizar_solr> Error fatal al actualizar en Solr el id_doc:"+
														documentoClasificacion.getIdTextoClasificacion(), e);
									e.printStackTrace();
									 throw new SystemTCEException("<sincronizar_solr> Error fatal al actualizar en Solr el id_doc:"+
												documentoClasificacion.getIdTextoClasificacion(), e);
								}      
							}						
						
							//Se persiste
							documentoClasificacionDao.update(documentoClasificacion);														
							log4j.debug("<sincronizar_solr> se llamaverificarPosibleCandidato() -> getAreasConfirmadas="+
												documentoClasificacion.getAreasConfirmadas());
							
							//se verifica si es posible_candidato
							schedulerDto.setIdPersona(String.valueOf(documentoClasificacion.getPersona().getIdPersona()));
							schedulerDto.setIdAreasConfirmadas(documentoClasificacion.getAreasConfirmadas());
							respService=verificarPosibleCandidato(schedulerDto);
							
							log4j.debug("<sincronizar_solr>  ->  respService=="+respService);
							
							if(!respService.equals(Mensaje.SERVICE_MSG_OK_JSON)){
								//Se checa el error
							}else{
								//¿Se manda un correo al postulante?
								
							}
						
							break;
						}
					 }					
				}								
			}		
		}
		// se finaliza el proceso completo 
		schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_CLOSED.longValue());
		schedulerDto.setResultado(new StringBuilder("Sincronización de documentos de Solr :")
									.append(numSolrDocs).toString());
		endProcess(schedulerDto);
		return adminService.lastDateFinalSyncDocs(new ControlProcesoDto(
							schedulerDto.getIdPersona(),schedulerDto.getIdEmpresaConf(),
							Constante.TIPO_SYNC_DOCS_SOLR));
	}  
	
	/**
	 * Ejecuta el query a solr
	 * @param querySolr
	 * @param schedulerDto
	 * @return Object (List<SolrDocTopics>) o String
	 * @throws SystemTCEException
	 */
	/*@SuppressWarnings("unchecked")
	private Object querySolr(SchedulerDto schedulerDto) throws SystemTCEException {
		try{		
			jsonObject= new JSONObject();
			jsonObject.put("query", sb_solr);	
			
			//Se llama al servicio
			objResponse= restJsonService.serviceRJOperNoStruc(jsonObject.toString(), 
											Constante.OPERATIVE_SEARCHCLASSBYQUERY_ENDPOINT);
			log4j.debug("<sincronizar_solr> objResponse:"+ objResponse.getClass().getName());
			if(objResponse instanceof List){
				log4j.debug("Es lista, se castea a lsSolrDocTopics");
				return (List<SolrDocTopics>)objResponse;
			}else{
				log4j.error("ERROR: no se pudo realizar la tarea ");
				//se registra el final del proceso 
				schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_ERROR.longValue());
				schedulerDto.setResultado("Error en la busqueda de documentos en Solr");
				endProcess(schedulerDto);
				log4j.debug(objResponse);
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
														Mensaje.SERVICE_CODE_000, 
														Mensaje.SERVICE_TYPE_FATAL, 
														"ERROR EN OPERATIONAL TCE"));
			}
		}catch (Exception e){
			log4j.error("<sincronizar_solr> ERROR: al Comunicarse con OperationalNoStructured [" 
						+ Constante.OPERATIVE_SEARCHCLASSBYQUERY_ENDPOINT +"] : "+  e);
			//se registra el final del proceso 
			schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_ERROR.longValue());
			schedulerDto.setResultado("Error en la busqueda de documentos en Solr: No se pudo comunicar con AppOperationalNoStructured.");
			endProcess(schedulerDto);
			throw new SystemTCEException("ERROR: al Comunicarse con OperationalNoStructured [" 
										+ Constante.OPERATIVE_SEARCHCLASSBYQUERY_ENDPOINT +"] : "+  e);
		}
	}*/
	
	
	/**
	 * Se verifica si la persona es posible_candidato y si lo es, convertirlo en candidato
	 * @param idPersona, identificador de la persona
	 * @param idAreasConfirmadas, areas que hicieron match: preclasificadas y personales
	 * @param idEmpresaConf, identificador de la EmpresaConf
	 * @return respuesta ok o error
	 * @throws Exception 
	 */
	//long idPersona, String idAreasConfirmadas, String idEmpresaConf
	public String  verificarPosibleCandidato(SchedulerDto schedulerDto) throws Exception{
		String respApp=Mensaje.SERVICE_MSG_OK_JSON;
		lsRespMensajeDto=new ArrayList<MensajeDto>();
		idPosicion=null;
		idPosibleCandidato=null;
		
		log4j.debug("<verificarPosibleCandidato> idPersona:"+schedulerDto.getIdPersona()+
				" idEmpresaConf:"+schedulerDto.getIdEmpresaConf()+
				" idAreasConfirmadas:"+schedulerDto.getIdAreasConfirmadas());
		
		long idPersona=Long.parseLong(schedulerDto.getIdPersona());
		
		//se verifica que sea un posible candidato
		//se obtiene el idPosición y por el momento solo una area perfil
		List<PosibleCandidatoDto> lsPosibleCandidatoDto= posibleCandidatoDao.getAreas(idPersona);
		log4j.debug("<verificarPosibleCandidato> lsPosibleCandidatoDto:"+
					(lsPosibleCandidatoDto == null ? null:lsPosibleCandidatoDto ));
		
		//si es posible candidato
		if(lsPosibleCandidatoDto != null && lsPosibleCandidatoDto.size() > 0){					
		
			AreaPersona areaPersona;
			Area area;
			Persona persona;
			PosibleCandidato posibleCandidato;
			
			Iterator<PosibleCandidatoDto> itPosibleCandidatoDto= lsPosibleCandidatoDto.iterator();
			
			while(itPosibleCandidatoDto.hasNext()){
				posibleCandidatoDto=itPosibleCandidatoDto.next();
				
				 log4j.debug("<verificarPosibleCandidato> getIdArea:"+posibleCandidatoDto.getIdArea()+
								" Confirmado="+posibleCandidatoDto.isConfirmado()+
								" getIdPosicion="+posibleCandidatoDto.getIdPosicion()+
								" getIdPosibleCandidato="+posibleCandidatoDto.getIdPosibleCandidato());
				
				//si hay areas coincidentes
				if(schedulerDto.getIdAreasConfirmadas() != null){
					
					//se obtienen las areas en que hubo coincidencia con las areas de la persona
					//y las preclasificadas por el sistema
					 st=new StringTokenizer(schedulerDto.getIdAreasConfirmadas(),
							 				Constante.DELIMITADOR_AREAS_CONFIRM);					 
					 
					 //Se recorre las areas que hicieron match
					 while(st.hasMoreTokens()){				 				 			
						
						//si almenos coincide una area, se obtiene la posicion
						if(posibleCandidatoDto.getIdArea() == Long.parseLong(st.nextToken())){
							idPosicion=posibleCandidatoDto.getIdPosicion();
							idPosibleCandidato=posibleCandidatoDto.getIdPosibleCandidato();
							log4j.debug("<verificarPosibleCandidato> coinciden getIdArea:"+posibleCandidatoDto.getIdArea());
							break;
						}					
						
						//si ya hay idPosicion
						if(idPosicion != null) break ;
					 }
				}
				
				 log4j.debug("<verificarPosibleCandidato>  Confirmado="+posibleCandidatoDto.isConfirmado()+
							" idPosicion="+idPosicion);
				
				//Si no hay coincidencias y si esta confirmado el posible_candidato
				//Se le adiciona el area de la posicion
				if(idPosicion == null){				 
					 if(posibleCandidatoDto.isConfirmado()){
						 
						 //existe el area en la tabla areaPersona pero no esta confirmada
						 areaPersona=areaPersonaDao.read(idPersona, posibleCandidatoDto.getIdArea());
						 
						 log4j.debug("<verificarPosibleCandidato> areaPersona:"+areaPersona);
						 
						 //existe el objeto
						 if(areaPersona != null){
							 areaPersona.setConfirmada(true);						 
						 }else{
							 
							 //No existe en la lista de areas de la persona
							 areaPersona = new AreaPersona();
							 area = new Area();
							 persona=new Persona();
							 persona.setIdPersona(idPersona);
							 area.setIdArea(posibleCandidatoDto.getIdArea());
							 areaPersona.setArea(area);
							 areaPersona.setPersona(persona);
							 areaPersona.setPrincipal(false);
							 areaPersona.setConfirmada(true);
							 areaPersona.setPersonal(false);
							 
							 log4j.debug("<verificarPosibleCandidato> create areaPersona");
							 areaPersonaDao.create(areaPersona);
						 }
						 
						 idPosicion=posibleCandidatoDto.getIdPosicion();
						 idPosibleCandidato=posibleCandidatoDto.getIdPosibleCandidato();
					 }				
				 }
				
				 log4j.debug("<verificarPosibleCandidato>  getIdPosicion:"+idPosicion+
					 	 " idPosibleCandidato="+idPosibleCandidato);
				 
				 //si hay posicion se aplica searchApplicants
				 if(idPosicion != null){
					 posicionDto=new PosicionDto();
					 posicionDto.setIdPosicion(idPosicion);
					 posicionDto.setHuboCambioPosicion(true);
					 posicionDto.setIdEmpresaConf(schedulerDto.getIdEmpresaConf());
					 
					 respApp=applicantService.searchApplicants(posicionDto);
					 log4j.debug("<verificarPosibleCandidato>se invoca searchApplicants ->  respApp:"+respApp+
							 " isConfirmado="+posibleCandidatoDto.isConfirmado());
					 
					 //todo bien
					 if(respApp.equals(Mensaje.SERVICE_MSG_OK_JSON)){
						 
						 //Se obtiene el objeto candidato de la persona
						 candidato=candidatoDao.get(idPosicion, idPersona);
						 log4j.debug("<verificarPosibleCandidato>  candidato:"+candidato);
						 
						 //se asigna orden al candidato
						 seAsignaOrdenCandidato(candidato, idPosicion, new Short((short) 0));
						 
						//Se pone estatusOperativo a seleccionado
						 candidato.setEstatusOperativo(new EstatusOperativo(
											 Constante.ESTATUS_CANDIDATO_OPERATIVO_EN_PROCESO,null));	
						 candidato.setConfirmado(posibleCandidatoDto.isConfirmado());
						 
						 
						 //se actualiza la tabla tracking_esquema_persona
						 trackingPostulanteDao.updateCandidate(candidato.getIdCandidato(),
								 								   idPosibleCandidato);
						 
						 log4j.debug("<verificarPosibleCandidato>  actualiza la tabla tracking_esquema_persona");
						 
						 //Se obtiene el Posible_Candidato correspondiente
						 posibleCandidato=posibleCandidatoDao.read(idPosibleCandidato);
						 
						 //se modifica comentario
						 posibleCandidato.setComentario("El sistema lo desactivo en forma automática ya que muto a candidato");
						 
						 //Se pone EstatusOperativo 
						 posibleCandidato.setEstatusOperativo(new EstatusOperativo(
										 Constante.ESTATUS_CANDIDATO_OPERATIVO_EN_PROCESO,null));
						 
						 //se actualiza bitacora tracking						
						 //bitacora_track_cand
						  log4j.debug("<verificarPosibleCandidato> actualiza BitacoraTrackCand");
						  bitacoraTrackCandDao.updateCandidateByPC_TEB_TMB(candidato.getIdCandidato(), 
																		  	idPosibleCandidato, 
																			Constante.TIPO_EVEN_BTC_CONFIRM,
																			Constante.TIPO_MOD_BTC_PRE_CAND);	
																  
						  
						 /* lsIdBitacoraTrackCand=bitacoraTrackCandDao.get(idPosibleCandidato, 
															Constante.TIPO_EVEN_BTC_CONFIRM,
															Constante.TIPO_MOD_BTC_PRE_CAND);
						  
						  log4j.debug("<verificarPosibleCandidato> lsIdBitacoraTrackCand="+
																lsIdBitacoraTrackCand.size());
						  if(lsIdBitacoraTrackCand.size() > 0) {
							  itIdBitacoraTrackCand=lsIdBitacoraTrackCand.iterator();
							  while(itIdBitacoraTrackCand.hasNext()) {
								  sb.append(itIdBitacoraTrackCand.next());
								  
								  //se investiga si ya no hay mas
								  if(itIdBitacoraTrackCand.hasNext()) {
									  sb.append(",");
								  }
							  }
							  
							  //se actualiza en BitacoraTrackCand
							  log4j.debug("<verificarPosibleCandidato> IdBitacoraTrackCand's="+sb.toString());
							  bitacoraTrackCandDao.updateCandidateByPC_TEB_TMB(candidato.getIdCandidato(), 
									  														sb.toString());							  
							  sb.delete(0, sb.length());							  
						  }*/
						 
						 //bitacora_track_post
						 log4j.debug("<verificarPosibleCandidato> actualiza BitacoraTrackPost");
						 bitacoraTrackPostDao.updateCandidateByPC_TEB_TMB(candidato.getIdCandidato(), 
																		 idPosibleCandidato, 
																		 Constante.TIPO_EVEN_BTC_CREATE, 
																		 Constante.TIPO_MOD_BTC_TRACK_POS);
						/* lsBitacoraTrackPost= bitacoraTrackPostDao.get(idPosibleCandidato, 
																	 Constante.TIPO_EVEN_BTC_CREATE, 
																	 Constante.TIPO_MOD_BTC_TRACK_POS);
						 
						log4j.debug("<verificarPosibleCandidato> lsBitacoraTrackPost="+
								  								lsBitacoraTrackPost.size());
						  
						if(lsBitacoraTrackPost.size() > 0) {
							
							itBitacoraTrackPost=lsBitacoraTrackPost.iterator();
							while(itBitacoraTrackPost.hasNext()) {
								  sb.append(itBitacoraTrackPost.next());
								  
								  //se investiga si ya no hay mas
								  if(itBitacoraTrackPost.hasNext()) {
									  sb.append(",");
								  }
							}
						
							//se actualiza en BitacoraTrackCand
							log4j.debug("<verificarPosibleCandidato> idBitacoraTrackPost's="+sb.toString());
							bitacoraTrackPostDao.updateCandidateByPC_TEB_TMB(candidato.getIdCandidato(), 
								  														sb.toString());											
						}			*/			 
					 }else{
						 lsRespMensajeDto.add(new MensajeDto("idPosicion",
								 							idPosicion.toString(),
										  					respApp));	
					 }
				 }	
			 
			} 
		}
		 if(lsRespMensajeDto.size() > 0){
			 respApp =gson.toJson(lsRespMensajeDto);
		 }
		 log4j.debug("<verificarPosibleCandidato> salir respApp:"+respApp);
		return respApp;
	}
	
	/**
	 * Se le asigna el numero de orden al candidato 
	 * @param Candidato, el objeto candidato
	 */
	synchronized void  seAsignaOrdenCandidato(Candidato candidato, Long idPosicion, Short order ){
		
		//Se obtiene el orden máximo de los candidatos
		if(order == null){
			order=candidatoDao.getMaxOrder(idPosicion);		
			log4j.debug("<seAsignaOrdenCandidato>  maxOrder_1:"+order);		
			order=(short) (order.shortValue() + (short)1);		
			
		}
		log4j.debug("<seAsignaOrdenCandidato>  maxOrder_2:"+order);
		//se asigna el orden
		candidato.setOrden(order.shortValue());
		
	}
	
	/**
	 * Actualiza las tablas area_persona o/y area_perfil solo para documentos confirmados.
	 * Esta operación puede ejecutarse bajo demanda o por scheduler
	 * @param SchedulerDto
	 * @return SchedulerDto
	 * @throws Exception 
	 */
	public Object synchronizeClassificationDocs(SchedulerDto schedulerDto) throws Exception {
		log4j.debug("<synchronizeClassificationDocs> Inicio...");
		List<AreaPersona> lsAreaPersona;
		List<String> lsAreasConfir;
		Iterator<String> itAreasConfir;
		AreaPersona areaPersona;
		//AreaPerfil areaPerfil;
		int numConfirDocs=0;
		
			
		//Se registra el proceso inicial
		schedulerDto.setIdTipoProceso(Constante.TIPO_PROCESO_SYNC_DOCS_CONFIR.longValue());
		beginProcess(schedulerDto);
			
		//Se obtienene los docs confirmados
		lsDocumentoClasificacion = documentoClasificacionDao.getDocsConfir();
		
		log4j.debug("<sincronizar_confirmados> lsIdsDocText:"+ lsDocumentoClasificacion.size());

		//si hay
		if(lsDocumentoClasificacion != null && lsDocumentoClasificacion.size() > 0){
			Area area;
			ltDocumentoClasificacion= lsDocumentoClasificacion.iterator();			
			while(ltDocumentoClasificacion.hasNext()){
				
				documentoClasificacion =ltDocumentoClasificacion.next();
				
				log4j.debug("<sincronizar_confirmados> getAreasConfirmadas:"+ 
							documentoClasificacion.getAreasConfirmadas());
				
				//Se obtienen los id's de las areas confirmadas
				lsAreasConfir=new LinkedList<String>(Arrays.asList(documentoClasificacion.
																   getAreasConfirmadas().split(",")));
				//size areas confirmada
				numConfirDocs=lsAreasConfir.size();
				
				log4j.debug("<sincronizar_confirmados> lsAreasConfir="+(lsAreasConfir != null ? lsAreasConfir.size():null));	
				
				//Para los cv's
				if(documentoClasificacion.getPersona() != null){
					
					lsAreaPersona=areaPersonaDao.get(String.valueOf(
								  documentoClasificacion.getPersona().getIdPersona()));
					
					log4j.debug("<sincronizar_confirmados> getIdPersona:"+ documentoClasificacion.getPersona().getIdPersona()+
								" lsAreaPersona="+(lsAreaPersona != null ? lsAreaPersona.size():null));					
					
					//Se persisten las areas confirmadas
					Iterator<AreaPersona> itAreaPersona=lsAreaPersona.iterator();
					while(itAreaPersona.hasNext()){
						areaPersona=itAreaPersona.next();
						
						log4j.debug("<sincronizar_confirmados>  IdArea:"+
									areaPersona.getArea().getIdArea());
						
						//Hay coincidencia de las areas personales con las confirmadas
						if(lsAreasConfir.contains(String.valueOf(
							areaPersona.getArea().getIdArea()))){
										
							log4j.debug("<sincronizar_confirmados> update en areaPersona para getIdAreaPersona:"+areaPersona.getIdAreaPersona());
							
							//Se persiste en area_persona
							areaPersona.setConfirmada(true);
							areaPersonaDao.update(areaPersona);															
							
							//Se quita de la lista
							lsAreasConfir.remove(String.valueOf(
							areaPersona.getArea().getIdArea()));							
							
						}else{
							log4j.debug("<sincronizar_confirmados>no hay mach -> isPersonal:"+areaPersona.isPersonal());
							
							//si el registro fue creado por el admin
							if(!areaPersona.isPersonal()){
								log4j.debug("<sincronizar_confirmados> se borra getIdAreaPersona:"+areaPersona.getIdAreaPersona());
								
								//Se borra fisicamente
								areaPersonaDao.delete(areaPersona);
								
								log4j.debug("<sincronizar_confirmados> se borra getIdPersona:"+areaPersona.getPersona().getIdPersona()+
										" getIdArea="+areaPersona.getArea().getIdArea());
								
								//ver si existe candidato asociado
								List<Candidato> lsCandidato=candidatoDao.getCanditatosAreaPersona(areaPersona.getPersona().getIdPersona(),
																									areaPersona.getArea().getIdArea());
								log4j.debug("<sincronizar_confirmados>  lsCandidato:"+(lsCandidato != null ? lsCandidato.size():null));
								if(lsCandidato != null && lsCandidato.size() > 0){
									Iterator<Candidato> itCandidato=lsCandidato.iterator();
									while(itCandidato.hasNext()){
										Candidato candidato=itCandidato.next();
										log4j.debug("<sincronizar_confirmados> se elimina getIdCandidato:"+candidato.getIdCandidato());
										//se eliminan
										candidatoDao.delete(candidato);
									}									
								}								
							}else{
								log4j.debug("<sincronizar_confirmados> update en areaPersona(personal) para getIdAreaPersona:"+areaPersona.getIdAreaPersona());
								
								//Ya que esta area no esta en las confirmadas y es personal
								//se pone false
								areaPersona.setConfirmada(false);
								areaPersonaDao.update(areaPersona);
							}
						}
					}
					
					log4j.debug("<sincronizar_confirmados> quedan todavia lsAreasConfir="+
											(lsAreasConfir != null ? lsAreasConfir.size():null));
					
					//hay areas confirmadas
					if(lsAreasConfir.size() > 0){
						itAreasConfir=lsAreasConfir.iterator();
						while(itAreasConfir.hasNext()){							
							areaPersona=new AreaPersona();
							area=new Area();
							area.setIdArea(Long.parseLong(itAreasConfir.next()));
							areaPersona.setArea(area);							
							areaPersona.setPersona(documentoClasificacion.getPersona());
							areaPersona.setConfirmada(true);
							areaPersona.setPersonal(false);
							
							log4j.debug("<sincronizar_confirmados> create en areaPersona para idArea="+area.getIdArea());
							
							//Se persiste
							areaPersonaDao.create(areaPersona);																							
						}
					}					
				}
				
				//Posicion_perfil -- QUEDA PENDIENTE ---
				/*if(documentoClasificacion.getPerfil() != null){
					lsAreaPerfil=areaPerfilDao.get(String.valueOf(
							documentoClasificacion.getPerfil().getIdPerfil()));
					log4j.debug("<sincronizar_confirmados> getIdPersona:"+ documentoClasificacion.getPerfil().getIdPerfil()+
							" lsAreaPerfil="+(lsAreaPerfil != null ? lsAreaPerfil.size():null));	
					
				}*/
				
				//Se sincronizó
				documentoClasificacion.setSincronizado(true);
				documentoClasificacionDao.update(documentoClasificacion);
								
				log4j.debug("<sincronizar_confirmados>  se verifica si es posible_candidato");
				
				//se verifica si es posible_candidato
				schedulerDto.setIdPersona(String.valueOf(documentoClasificacion.getPersona().getIdPersona()));
				schedulerDto.setIdAreasConfirmadas(documentoClasificacion.getAreasConfirmadas());
				verificarPosibleCandidato(schedulerDto);
								
				
				//Se actualiza el doc en Solr								
				//try {
					lsRespSolrDocTopics=updateDocSolr( documentoClasificacion);									
					log4j.debug("<sincronizar_confirmados> result :" +
							(lsRespSolrDocTopics != null ? lsRespSolrDocTopics.size():null));
					
					//Si hay error en solr
					if(lsRespSolrDocTopics != null && lsRespSolrDocTopics.size() > 0){
						log4jSyncro.fatal("<sincronizar_confirmados> Error fatal al actualizar en Solr el id_doc:"+
								documentoClasificacion.getIdTextoClasificacion());
					}else{
						log4j.debug("<synchronizeClassificationDocs> doc confirmado:" + documentoClasificacion.getIdTextoClasificacion());
					}
				/*} catch (SystemTCEException e) {
					log4jSyncro.fatal("<sincronizar_confirmados> Error fatal al actualizar en Solr el id_doc:"+
								documentoClasificacion.getIdTextoClasificacion(), e);
					e.printStackTrace();
				}  */    				
			}			
		}
		// se finaliza el proceso completo 
		schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_CLOSED.longValue());
		schedulerDto.setResultado(new StringBuilder("Sincronización de documentos confirmados:").
									append(numConfirDocs).toString());
		endProcess(schedulerDto);
	
		return adminService.lastDateFinalSyncDocs(new ControlProcesoDto(
				schedulerDto.getIdPersona(),schedulerDto.getIdEmpresaConf(),
				Constante.TIPO_SYNC_DOCS_CONFIR));		
	}
	
	
	/**
	 * Se actualiza el doc correspondiente enSolr
	 * @param documentoClasificacion
	 * @throws SystemTCEException 
	 */
	private  List<SolrDocTopics>  updateDocSolr(DocumentoClasificacion documentoClasificacion) throws Exception{
		solrDocTopics = new SolrDocTopics();
		solrDocTopics.setId(documentoClasificacion.getIdTextoClasificacion());
		solrDocTopics.setClassified(String.valueOf(documentoClasificacion.getEstatusClasificacion()));
		solrDocTopics.setCategory(documentoClasificacion.getAreasConfirmadas());
		solrDocTopics.setTarget("/" + String.valueOf(documentoClasificacion.getArea().getIdArea()) + "/" + documentoClasificacion.getIdTextoClasificacion());
		
		log4j.debug("<updateDocSolr> Se actualiza en Solr getIdTextoClasificacion::" + documentoClasificacion.getIdTextoClasificacion());
		return restJsonService.serviceRJOperNoStruc(gson.toJson(solrDocTopics), Constante.OPERATIVE_UPDATECLASSSTATUS_ENDPOINT);				
	}
	
	
	/**
	 * Obtiene el mapa de las categorías (incluyendo ancestros) de una lista de categorias dada
	 * @param String categories
	 * @return HashMap<Long, Long>
	 * @author osy
	 */
	public HashMap<Long, Long> getCategoriesMap(String categories){		
		log4j.debug("<getCategoriesMap> Inicio...");

		HashMap<Long, Long> mapAreas = new HashMap<Long, Long>();
		List<Long> areas = new ArrayList<Long>();
		
		// Ciclo para obtener las áreas y sus ancestros
		StringTokenizer st = new StringTokenizer(categories, ",");
		while (st.hasMoreElements()) {
			String extractedArea = (String) st.nextElement();
			log4j.debug("<insertCategorieAsArea> Area a registrar :" + extractedArea);
			log4j.debug("<insertCategorieAsArea> Get parents");
			areas = areaDao.getParents(Long.valueOf(extractedArea));
			Iterator<Long> itAreas = areas.iterator();
			while(itAreas.hasNext()){
				Long areaToRegister = itAreas.next();
				mapAreas.put(areaToRegister, areaToRegister);
			}		
		}
		log4j.debug("<getCategoriesMap> Fin...");
		return mapAreas;
	}
	
	/**
	 * Ejecuta el proceso de re-modelacion
	 * Esta operación puede ejecutarse bajo demanda o por scheduler
	 * @param SchedulerDto
	 * @return String
	 * @throws Exception 
	 */
	public synchronized Object runReModel(SchedulerDto schedulerDto) throws Exception{
		log4j.debug("<runReModel> inicio... getIdEmpresaConf="+schedulerDto.getIdEmpresaConf()+
				" getIdPersona="+schedulerDto.getIdPersona());
		
		if(schedulerDto.getIdEmpresaConf() != null && schedulerDto.getIdPersona() != null){
			
			//Se pone el id de proceso
			schedulerDto.setIdTipoProceso(Constante.TIPO_PROCESO_REMODEL_CLASS.longValue());
			
			// un nuevo modelo, en este último caso se agrega la condición de que sólo contabilize para el modelo actual
			//Se reemplaza comodines
			mapReplace=new HashMap<String, String>(); 
			mapReplace.put(Constante.PARAM_SOLR_MV,ParametrosCache.getChmGeneral(Constante.PARAM_GENERAL_CURRRENTMODEL));
			schedulerDto.setQueryForModel(getQueryFor(Constante.CONTEXT_PARAM_FOR_MODEL,
															Constante.DEFAULT_QUERY_MODEL_SOLR,
															mapReplace));
	
			// Obtiene el número de documentos verificados necesarios para generar un modelo nuevo
			schedulerDto.setNumDocsForModel(getnumDocsForModel());
	
			// El proceso de remodelado se hace bajo demanday ya no es un proceso automatico
			//schedulerDto.setReclassificationAuto(getReclassificationAuto());
			
			log4j.debug("<remodel> schedulerDto.getQueryForModel :" + schedulerDto.getQueryForModel());
			log4j.debug("<remodel> schedulerDto.getNumDocsForModel :" + schedulerDto.getNumDocsForModel());
			log4j.debug("<remodel> schedulerDto.getReclassificationAuto :" + schedulerDto.getReclassificationAuto());
			
			// Se obtiene y asigna : número de documentos actuales en SOLR de acuerdo al criterio (query) de documentos para regenerar modelo,
			// la lista de contadores por estatus de clasificación. Siempre se considera el modelo actual. 
			schedulerDto.setCurrentModel(getCurrentModelVersion(schedulerDto));
			
			//Se obtiene el numero de docs en solr para ver si la cuota es suficiente para la creacion del modelo
		    if(schedulerDto.getCurrentModel() != null){	
		    	setNumDocumentsByQueryForModel(schedulerDto);
		    }	    
			
			log4j.debug("<remodel> schedulerDto.getCurrentNumDocsByQueryForModel :" + schedulerDto.getCurrentNumDocsByQueryForModel()+
						" getNumDocsForModel=" + schedulerDto.getNumDocsForModel()+ " getCurrentModel="+schedulerDto.getCurrentModel());
			
			//Obtiene conteo de documentos por estatus classified
			//schedulerClassifiedDocService.setDocumentListByStatusAndCurrentModel(schedulerDto);
			//log4j.debug("<remodel> schedulerDto.getStatusList().size() :" + schedulerDto.getStatusList().size());
			//log4j.debug("<remodel> getReclassificationAuto :" + schedulerDto.getReclassificationAuto());
			
			
			//Por el momento no se toma en cuenta si el proceso de reclasificación esta en automático
			//&& schedulerDto.getReclassificationAuto() == 1	
		
			// Se verifica que el número de documentos verificados sea el adecuado para generar un nuevo modelo	
			if(schedulerDto.getCurrentNumDocsByQueryForModel() >= schedulerDto.getNumDocsForModel()){
				log4j.debug("<remodel> cumple numero de docs...");
			
				// Se genera un nuevo identificador de versión del modelo
				schedulerDto.setNewModel(getNewModelVersion());

				// Se inicia el proceso completo
				beginProcess(schedulerDto);
				
				log4j.debug("<remodel> Identificador de proceso creado :" + schedulerDto.getIdControlProceso());
				
				// Creación de un nuevo modelo
				String resp= getCreateModel(schedulerDto);

				//se registra el proceso
				log4j.debug("$%& se registra el proceso");
				if(resp != Mensaje.SERVICE_MSG_OK_JSON ){
					log4j.debug("<remodel> Error en getCreateModel getCode");
					schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_ERROR.longValue());
					schedulerDto.setResultado("Error al crear un nuevo modelo -> resp:"+resp);
					endProcess(schedulerDto);
					return resp;
				}else{
					// se finaliza el proceso completo - generación de modelo y reclasificación en SOLR
					log4j.debug("<remodel> Se crea el modelo:"+schedulerDto.getNewModel());
					schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_CLOSED.longValue());
					schedulerDto.setResultado(schedulerDto.getNewModel());
					endProcess(schedulerDto);
					
					// Actualiza el caché de los parámetros generales correspondientes
					ParametrosCache.replaceChmGeneral(Constante.PARAM_GENERAL_CURRRENTMODEL, schedulerDto.getNewModel());
					ParametrosCache.replaceChmGeneral(Constante.PARAM_GENERAL_PREVIOUSMODEL, schedulerDto.getCurrentModel());
					return adminService.lastDateFinalRemodel(new ControlProcesoDto(schedulerDto.getIdPersona(),schedulerDto.getIdEmpresaConf()));

				}
			}else{
				log4j.info("No se pudo crear el modelo,  no se satisface la cuota de documentos:");
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
													Mensaje.SERVICE_CODE_008,
													Mensaje.SERVICE_TYPE_INFORMATION,
													Mensaje.MSG_NO_CUOTA+", que es de "+schedulerDto.getNumDocsForModel()+" documentos. "+
													" Solo hay "+schedulerDto.getCurrentNumDocsByQueryForModel()+" documentos"));
			}
			
		}else{
			log4j.error("ERROR: runReModel() -->  IdEmpresaConf nulo o/y  IdPersona nulo");
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
												Mensaje.SERVICE_CODE_001,
												Mensaje.SERVICE_TYPE_FATAL,
												Mensaje.MSG_ERROR_SISTEMA));	
		}			
	}

	/**
	 * Obtiene el parametro para saber si se debe ejecutar de manera automática la reclasificación 
	 * @param Ninguno
	 * @return QUERY Valor del parametro reclassificationAuto
	 */			
	@SuppressWarnings("unchecked")
	private Short getReclassificationAuto() throws Exception{
		log4j.debug("<getReclassificationAuto> Inicio...");
		empresaParametroDto=new EmpresaParametroDto();
		empresaParametroDto.setIdEmpresaConf(Constante.IDCONF_NULL);
		empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_APLICACION_GENERAL);
		empresaParametroDto.setContexto(Constante.CONTEXT_PARAM_RECLASSIFICATION_AUTO);
		log4j.debug("<getReclassificationAuto> Antes de obtener parametro empresaParametroDto :" + empresaParametroDto);
		Object object=empresaParametroService.get(empresaParametroDto,false);
		if(object instanceof EmpresaParametroDto ){
			return Constante.RECLASSIFICATION_AUTO;
		}else{
			List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
			log4j.debug("<getQueryForModel> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
			return Short.valueOf(lsEmpresaParametroDto.get(0).getValor());
		}
	}	
	
	/**
	 * Obtiene el parametro del número de documentos para generar el modelo (empresa TCE) de la base de datos, 
	 * @param Ninguno
	 * @return QUERY Valor del parametro numDocsForModel
	 */			
	@SuppressWarnings("unchecked")
	private int getnumDocsForModel() throws Exception{
		empresaParametroDto=new EmpresaParametroDto();
		empresaParametroDto.setIdEmpresaConf(Constante.IDCONF_NULL);
		empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_APLICACION_GENERAL);
		empresaParametroDto.setContexto(Constante.CONTEXT_PARAM_NUMDOCS_FOR_MODEL);	
		Object object=empresaParametroService.get(empresaParametroDto,false);
		if(object instanceof EmpresaParametroDto ){
			return Constante.DEFAULT_NUM_DOCS_MODEL;
		}else{
			List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
			log4j.debug("<getQueryForModel> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
			return Integer.parseInt(lsEmpresaParametroDto.get(0).getValor());
		}	
	}
	
	/**
	 * SCarga de nuevo el core de Solr para tomar el nuevo modelo
	 * @param SchedulerDto
	 * @return String
	 * @throws Exception 
	 */
	public synchronized Object runReloadCoreSolr(SchedulerDto schedulerDto) throws Exception{
		//filtros
		if(schedulerDto.getIdEmpresaConf() != null && schedulerDto.getIdPersona() != null){
			log4j.debug("runReloadCoreSolr() -> Se reinicia el core de solr ");
			List<SolrDocTopics> lsSolrDocTopics=null;
			boolean todoBien=true;			
			try {	
				
				//Se pone el id de proceso
				schedulerDto.setIdTipoProceso(Constante.TIPO_PROCESO_RELOAD_CORE_SOLR.longValue());
				beginProcess(schedulerDto);
				
				//Se reinicia el core de solr
				jsonObject = new JSONObject();
				jsonObject.put("idCore", Constante.SOLR_CORE);
				lsSolrDocTopics=(List<SolrDocTopics>)restJsonService.serviceRJOperNoStruc(jsonObject.toString(),
																			Constante.OPERATIVE_RELOAD_ENDPOINT);
				log4j.debug("#$%  lsSolrDocTopics="+lsSolrDocTopics.size());
				//Hay un error
				if(lsSolrDocTopics.size() == 1){
					log4j.error("No se pudo reiniciar el Core, para la reclasificacion");
					todoBien=false;
				}			
			} catch (Exception e) {
				log4j.error("No se pudo reiniciar el Core, para la reclasificacion",e);
				e.printStackTrace();
				 throw new SystemTCEException("No se pudo reiniciar el Core, para la reclasificacion",e);
			}
			
			//Hay un error
			if(todoBien){
				schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_CLOSED.longValue());
				endProcess(schedulerDto);
				return 	 adminService.lastDateFinalReloadCoreSolr(new ControlProcesoDto(schedulerDto.getIdPersona(),schedulerDto.getIdEmpresaConf()));
			}else{
				//Se actualiza el proceso
				schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_ERROR.longValue());
				endProcess(schedulerDto);
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_000,
							Mensaje.SERVICE_TYPE_FATAL,
							Mensaje.MSG_ERROR_SISTEMA));
			}				
		}else{
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_001,
					Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR_SISTEMA));
		}		
	}
	
	
	/**
	 * Ejecuta el proceso de re-clasificacion
	 * Esta operación puede ejecutarse bajo demanda o por scheduler
	 * @param SchedulerDto
	 * @return String
	 */
	public synchronized Object runReClassification(SchedulerDto schedulerDto) throws Exception{

		//filtros
		if(schedulerDto.getIdEmpresaConf() != null && schedulerDto.getIdPersona() != null){
			boolean todoBien=true;
			List<SolrDocTopics> lsSolrDocTopics=null;
	
			//try {
		    	log4j.info(" Se reinicio sin problemas el core="+Constante.SOLR_CORE);
				log4j.debug("Empieza a reclasificar  los docs. Se trae la lista de documentos de Solr");
				
				
				
				// Reclasificación de documentos en SOLR
				//Se especifica el parametro a sustituir en el query
				mapReplace=new HashMap<String, String>(); 
				mapReplace.put(Constante.PARAM_SOLR_MV,ParametrosCache.getChmGeneral(
												Constante.PARAM_GENERAL_PREVIOUSMODEL));				
				//Se trae la lista de documentos de Solr 
				jsonObject = new JSONObject();
				jsonObject.put("query", getQueryFor(Constante.CONTEXT_PARAM_FOR_RECLASSIFIED,
													Constante.DEFAULT_QUERY_RECLASSIFIED_SOLR,mapReplace));	
				log4j.info(" Se obtienen los docs bajo el query a solr : "+jsonObject.toString());
				lsSolrDocTopics =gson.fromJson(gson.toJson(restJsonService.serviceRJOperNoStruc(
								 jsonObject.toString(), Constante.OPERATIVE_SEARCHCLASSBYQUERY_ENDPOINT)),
								 new TypeToken<List<SolrDocTopics>>(){}.getType());
				log4j.debug("lsSolrDocTopics :" + lsSolrDocTopics.size());
				//Hay docs
				if (lsSolrDocTopics.size() > 0){
					log4j.debug("#$% Se reclasifican con el nuevo modelo ");
					
					//Se pone el id de proceso
					schedulerDto.setIdTipoProceso(Constante.TIPO_PROCESO_RECLASS_DOCS.longValue());
					beginProcess(schedulerDto);
					
					//Se adciona la nueva version del modelo
					Iterator<SolrDocTopics> itSolrDocTopics= lsSolrDocTopics.iterator();
					while(itSolrDocTopics.hasNext()){
						itSolrDocTopics.next().setModelversion(ParametrosCache.getChmGeneral(
												Constante.PARAM_GENERAL_CURRRENTMODEL));
					}
					log4j.debug("Se mandan a solr docs="+lsSolrDocTopics.size());
					//Se reclasifican con el nuevo modelo
					lsSolrDocTopics=(List<SolrDocTopics>)restJsonService.serviceRJOperNoStruc(
														gson.toJson(lsSolrDocTopics),
														Constante.OPERATIVE_BULKUPDATE_ENDPOINT);
					log4j.debug("#$%  respuesta al reclasificar en solr lsSolrDocTopics="+lsSolrDocTopics.size());
					//Hay un error
					if(lsSolrDocTopics.size() == 1){
						log4j.error("No se pudieron Reclasificar los documentos");
						todoBien=false;
					}
				//Esto no debe pasar	
				}else{
					log4j.info("No hay documentos para Reclasificar");
					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
													Mensaje.SERVICE_CODE_008,
													Mensaje.SERVICE_TYPE_INFORMATION,
													Mensaje.MSG_NO_CUOTA+" o no hay documentos para Reclasificar"));				
				}				
			/*} catch (Exception e) {
				log4j.error("No se pudo obtener los documentos para su reclasificación");
				todoBien=false;
				e.printStackTrace();
			}*/
			//Hay un error
			if(todoBien){
				schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_CLOSED.longValue());
				endProcess(schedulerDto);
				return adminService.lastDateFinalReclassDocs(new ControlProcesoDto(schedulerDto.getIdPersona(),schedulerDto.getIdEmpresaConf()));

			}else{
				//Se actualiza el proceso
				schedulerDto.setIdEstatusProceso(Constante.PROCESS_STATUS_ERROR.longValue());
				endProcess(schedulerDto);
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_000,Mensaje.SERVICE_TYPE_FATAL,
							Mensaje.MSG_ERROR_SISTEMA));
			}	
		}else{
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_001,
					Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR_SISTEMA));
		}
	}
	
	
	/**
	 * Obtiene el query para aplicar en solr 		
	 * @param contexto, 
	 * @param queryDefault, se usa este query si no existe la informacion en la base de datos
	 * @param mapReplace, contiene los parametros a reemplazar en el query 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getQueryFor(String contexto, String queryDefault, Map<String,String> mapReplace) throws Exception{
		log4j.debug("<getQueryForModel> Inicio...");
		empresaParametroDto=new EmpresaParametroDto();
		empresaParametroDto.setIdEmpresaConf(Constante.IDCONF_NULL);
		empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_APLICACION_GENERAL);
		empresaParametroDto.setContexto(contexto);
		log4j.debug("<getQueryForModel> Antes de obtener parametro empresaParametroDto :" + empresaParametroDto);
		Object object=empresaParametroService.get(empresaParametroDto,false);
		StringBuilder sbQuery;
		if(object instanceof EmpresaParametroDto ){
			sbQuery= new StringBuilder(queryDefault);
			log4j.warn(" No se pudo obtener el query de la tabla EMPRESA_PARAMETRO, para crear el nuevo modelo. Se usará el de default:"+
						sbQuery.toString());			
		}else{
			List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
			sbQuery= new StringBuilder( lsEmpresaParametroDto.get(0).getValor());
		}
		return replaceWildQuery(sbQuery,  mapReplace);
	}	
	
	/**
	 * Reemplaza el valor de parametro en el query dado detectando un comodin
	 * @param sbQuery, la petición a solr
	 * @param mapReplace, contiene los parametros a reemplazar en el query
	 * @return
	 */
	private  String replaceWildQuery(StringBuilder sbQuery, Map<String,String> mapReplace){
		
		if(sbQuery != null && mapReplace != null && mapReplace.size() > 0) {
			int index=0;
			String keyReplace=null;
			Iterator<String> itKeyReplace = mapReplace.keySet().iterator();
			while(itKeyReplace.hasNext()){
				keyReplace = itKeyReplace.next();
				index=sbQuery.indexOf(keyReplace);
				log4j.debug("replaceWildQuery -> keyReplace= "+keyReplace+" index="+index);
				//Se verifica si se encuentra el parametro en el query
				if(index  != -1 && sbQuery.substring(index + keyReplace.length()+1, index + keyReplace.length() + 2).equals(Constante.PARAM_SOLR_COMODIN)){				
					sbQuery.replace(index + keyReplace.length() + 1, index + keyReplace.length() + 2, mapReplace.get(keyReplace));
				}
			}
		}
		return sbQuery.toString();
	}

	
	/**
	 * Se crea el objeto json y se hace la llamada al servicio correspondiente que crea el modelo_mahout
	 * @param schedulerDto
	 * @return
	 * @throws Exception 
	 */
	public String getCreateModel(SchedulerDto schedulerDto) throws Exception
    {
		log4j.debug("<getCreateModel> Inicio...");
		log4j.debug("<getCreateModel> Endpoint for request..." + Constante.OPERATIVE_CREATEMODEL_ENDPOINT);
        String result =  Mensaje.SERVICE_MSG_OK_JSON;        
    
		jsonObject = new JSONObject();
		jsonObject.put("query", schedulerDto.getQueryForModel());
		jsonObject.put("idModelo",schedulerDto.getNewModel());
    	
		log4j.debug("<getCreateModel> Antes getJsonFromService jsonObject.toString() :" + jsonObject.toString());
		log4j.debug("<getCreateModel> Antes getJsonFromService endPoint URI :" + 
					Constante.OPERATIVE_CREATEMODEL_ENDPOINT);
		List<SolrDocTopics> resp =(List<SolrDocTopics>) restJsonService.serviceRJOperNoStruc(jsonObject.toString(), 
        							Constante.OPERATIVE_CREATEMODEL_ENDPOINT);
		
		log4j.debug("<getCreateModel> result :" + resp.size());
		//Se verifica si hay un error
		if(resp == null || resp.size() == 1){
    		log4j.debug("<getCreateModel> resp :" + resp.get(0).getClass().getName());
			result=UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_000,Mensaje.SERVICE_TYPE_FATAL,
							Mensaje.MSG_ERROR));
		}
		log4j.debug("<getCreateModel> Fin...");
        return result;
    }

}
