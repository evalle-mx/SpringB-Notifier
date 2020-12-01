package net.tce.admin.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.tce.admin.service.AdminService;
import net.tce.admin.service.BitacoraTrackingService;
import net.tce.admin.service.CurriculumService;
import net.tce.admin.service.NotificacionProgramadaService;
import net.tce.admin.service.TrackingPostulanteService;
import net.tce.dao.AreaPersonaDao;
import net.tce.dao.CandidatoDao;
import net.tce.dao.ModeloRscPosDao;
import net.tce.dao.ModeloRscPosFaseDao;
import net.tce.dao.MonitorDao;
import net.tce.dao.PersonaDao;
import net.tce.dao.PosibleCandidatoDao;
import net.tce.dao.PosicionDao;
import net.tce.dao.RelacionEmpresaPersonaDao;
import net.tce.dao.TrackingPostulanteDao;
import net.tce.dao.TrackingMonitorDao;
import net.tce.dto.BtcTrackCandDto;
import net.tce.dto.BtcTrackingDto;
import net.tce.dto.BtcTrackingMonPosDto;
import net.tce.dto.CandidatoTrackingDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.NotificacionProgramadaDto;
import net.tce.dto.SchedulerDto;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.dto.TrackingPostulanteDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.model.Candidato;
import net.tce.model.EstadoTracking;
import net.tce.model.EstatusOperativo;
import net.tce.model.ModeloRscPos;
import net.tce.model.Monitor;
import net.tce.model.PerfilPosicion;
import net.tce.model.Persona;
import net.tce.model.PosibleCandidato;
import net.tce.model.Posicion;
import net.tce.model.RelacionEmpresaPersona;
import net.tce.model.TipoPosibleCandidato;
import net.tce.model.ModeloRscPosFase;
import net.tce.model.TrackingPostulante;
import net.tce.model.TrackingMonitor;
import net.tce.task.service.SchedulerClassifiedDocService;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsNotification;
import net.tce.util.UtilsTCE;

@Transactional
@Service("trackingPostulanteService")
public class TrackingPostulanteServiceImpl implements TrackingPostulanteService{
	final Logger log4j = Logger.getLogger( this.getClass());

	Iterator<CandidatoTrackingDto> itCandidatosDto;
	List<ModeloRscPosFaseDto> lsModeloRscPosFaseDto;
	List<TrackingMonitorDto> lsTrackingMonitorDto;
	Iterator<TrackingMonitorDto> itTrackingMonitorDto;
	HashMap<String, TrackingMonitorDto> hmTrackingMonitorDto;
	StringBuilder sb;
	EstadoTracking estadoTracking;
	ModeloRscPosFaseDto modeloRscPosFaseCandidatoDto = null;
	ModeloRscPosFase modeloRscPosFase_Monitor;
	ModeloRscPosFase modeloRscPosFase;
	TrackingMonitor trackingMonitor;
	TrackingPostulante trackingPostulante;
	PosibleCandidato posibleCandidato;
	Candidato candidato;
	Posicion posicion;
	RelacionEmpresaPersona relacionEmpresaPersona;			
	Boolean confirmado=null;
	boolean soloUnaVez;
	boolean faseSigDeGeneral;
	String resp=null;
	StringBuilder sbRespError;
	EstatusOperativo estatusOperativo;
	Long idEstatusOperativo;
	JSONObject jsonObject = null;
	String pswIniSystem;
	Long idPersona;
	Long countCandidates;
	Long idTrackingPostulante;
	TrackingMonitorDto trackingMonitorDto;
	Monitor monitor;
	TrackingMonitorDto monitorDto;
	List<Long> lsEdoTrackingPostulante;
	Iterator<Long> itEdoTrackingPostulante;
	int numCandidatoSinErrores;
	BtcTrackingDto btcTrackingDto;
	BtcTrackCandDto btcTrackCandDto;
	BtcTrackingMonPosDto btcTrackingMonDto;
	BtcTrackingMonPosDto btcTrackingPosDto;
	Persona persona;
	PerfilPosicion perfilPosicion;
	CurriculumDto curriculumDto;
	boolean alMenosHayUnConfirmado;
	Long countConfirmado;
	boolean errorMonitor;
	CandidatoTrackingDto candidatoTrackingDto;
	List<MensajeDto> lsBTMensajeDto;
	Iterator<CandidatoTrackingDto> itCandidatoDto;
	Iterator<TrackingMonitorDto> itMonitoresDto;
	List<ModeloRscPosFase> lsModeloRscPosFase;
	Iterator<ModeloRscPosFase> itModeloRscPosFase;
	Iterator<ModeloRscPosFaseDto> itModeloRscPosFaseCandidatoDto;
	boolean todoCorrecto;
	Date fechaIni,fechaFin;
	String sMensajeError;
	BtcTrackingMonPosDto btcTrackingMonPosDto;
	String respBTSrv;
	List<Long> lsIdEstadoTracking;
	Long idEstadoTracking;
	ModeloRscPos modeloRscPos;
	Long idRepPos;
	Iterator<Monitor> itMonitor;
	List<Monitor> lsMonitor;
	
	@Autowired 
	private AdminService adminService;
	
	@Autowired 
	private CurriculumService curriculumService;
	
	@Autowired 
	private SchedulerClassifiedDocService schedulerClassifiedDocService;
	
	@Autowired
	private BitacoraTrackingService bitacoraTrackingService;
	
	@Autowired
	private TrackingPostulanteDao trackingPostulanteDao;
	
	@Autowired
	private TrackingMonitorDao trackingMonitorDao;
	
	@Autowired 
	private ModeloRscPosDao modeloRscPosDao;
	
	@Autowired 
	private RelacionEmpresaPersonaDao relacionEmpresaPersonaDao;
		
	@Autowired
	private NotificacionProgramadaService notificacionProgramadaService;	
	
	@Autowired 
	private PosibleCandidatoDao posibleCandidatoDao;
	
	@Autowired 
	private CandidatoDao candidatoDao ;
	
	@Autowired 
	private PersonaDao personaDao;
	
	@Autowired 
	private PosicionDao posicionDao;
	
	@Autowired
	private AreaPersonaDao areaPersonaDao;
	
	@Autowired
	private MonitorDao monitorDao;
	
	@Autowired
	private ModeloRscPosFaseDao modeloRscPosFaseDao;
	
	@Inject
	private Gson gson;
	
	@Inject
	private ConversionService converter;
	
	
	/**
	 * Se crea un nuevo TrackingPostulante
	 * @param trackingPostulanteDto
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String create(TrackingPostulanteDto trackingPostulanteDto) throws Exception {
		log4j.debug("<create> -> getIdEmpresaConf="+trackingPostulanteDto.getIdEmpresaConf()+
				" getIdPersona="+trackingPostulanteDto.getIdPersona()+
				" getIdPosicion="+trackingPostulanteDto.getIdPosicion()+
				" idEstadoTracking="+trackingPostulanteDto.getIdEstadoTracking()+
				" idModeloRscPosFase="+trackingPostulanteDto.getIdModeloRscPosFase()+
				" idRelacionEmpresaPersona="+trackingPostulanteDto.getIdRelacionEmpresaPersona()+
				" idCandidato="+trackingPostulanteDto.getIdCandidato()+
				" iPosibleCandidato="+trackingPostulanteDto.getIdPosibleCandidato()+
				" comentario="+trackingPostulanteDto.getComentario()+
				" fechaInicio="+trackingPostulanteDto.getFechaInicio()+
				" fechaFin="+trackingPostulanteDto.getFechaFin());	
		filtros(trackingPostulanteDto,Constante.F_CREATE);
		log4j.debug("<create> -> getCode="+trackingPostulanteDto.getCode());
		if(trackingPostulanteDto.getCode() == null){
			todoCorrecto=true;
			fechaIni=null;
			fechaFin=null;
			sMensajeError=null;
			
			modeloRscPosFase=modeloRscPosFaseDao.read(Long.parseLong(
								trackingPostulanteDto.getIdModeloRscPosFase()));
			log4j.debug("<create> modeloRscPosFase:"+modeloRscPosFase);
			
			//Se obtiene modeloRscPos
			modeloRscPos=modeloRscPosFase.getModeloRscPos();
			log4j.debug("<create>  modeloRscPos="+modeloRscPos);
			
			//Se obtiene Posicion
			posicion=modeloRscPos.getPerfilPosicion().getPosicion();
			log4j.debug("<create>  posicion="+posicion);
			
			//se asigna
			idRepPos=Long.parseLong(trackingPostulanteDto.getIdRelacionEmpresaPersona());
			
			//hay fechas
			if(trackingPostulanteDto.getFechaInicio() != null &&
				trackingPostulanteDto.getFechaFin() != null) {

				log4j.debug("<create> en analisis de fechas");
				
				// se verifica si la fechaInicio tiene formato  correcto
				if(!DateUtily.isValidDate(trackingPostulanteDto.getFechaInicio(), 
													Constante.DATE_FORMAT_JAVA)) {
					todoCorrecto=false;
					log4j.debug("<create> falla formato getFechaInicio="+trackingPostulanteDto.getFechaInicio());
					sMensajeError="La fecha inicial de la Agenda debe tener formato correcto";
					
				}else {
					fechaIni=DateUtily.string2Date(trackingPostulanteDto.getFechaInicio(), 
																Constante.DATE_FORMAT_JAVA);
					
					// se verifica si la fechaFin tiene formato  correcto
					if(!DateUtily.isValidDate(trackingPostulanteDto.getFechaFin(), 
														Constante.DATE_FORMAT_JAVA)) {
						todoCorrecto=false;
						log4j.debug("<create> falla formato getFechaFin="+trackingPostulanteDto.getFechaFin());
						sMensajeError="La fecha final de la Agenda debe tener formato correcto";
						
					}else {
						fechaFin=DateUtily.string2Date(trackingPostulanteDto.getFechaFin(), 
																Constante.DATE_FORMAT_JAVA);
					}
				}
				log4j.debug("<create>1  todoCorrecto="+todoCorrecto);
				
				//Comparación de fecha
				if(todoCorrecto) {
						
					//la fecha Inicial debe ser menor a la fecha Final
					if(todoCorrecto && DateUtily.compareDt1Dt2(fechaIni, fechaFin) == 1) {
						todoCorrecto=false;
						sMensajeError="La fecha Inicial de la Agenda debe ser menor a la fecha Final";
					}
				}
			}else if(trackingPostulanteDto.getFechaInicio() == null &&
					   trackingPostulanteDto.getFechaFin() != null){
				todoCorrecto=false;
				sMensajeError="Fecha Inicial debe tener valor si Fecha Final lo tiene";	
				
			}else if(trackingPostulanteDto.getFechaInicio() != null &&
					   trackingPostulanteDto.getFechaFin() == null){
				todoCorrecto=false;
				sMensajeError="Fecha Final debe tener valor si Fecha Inicial lo tiene";	
			}
			log4j.debug("<create>2  todoCorrecto="+todoCorrecto);
			
			//sale, seguimos
			if(todoCorrecto) {
				estadoTracking=new EstadoTracking();
								
				//setters
				trackingPostulante=converter.convert(trackingPostulanteDto, TrackingPostulante.class);
				log4j.debug("<create>  trackingPostulante="+trackingPostulante);
				
				
				//se investiga el estatus del track posterior
				
				// verificar si hay actividad posterior para el mismo orden						
				lsIdEstadoTracking= trackingMonitorDao.getActUpByPosRepOrder(posicion.getIdPosicion(),
																		modeloRscPosFase.getOrden(),
																		modeloRscPosFase.getActividad(),
																		idRepPos);
				log4j.debug("<delete> lsIdEstadoTracking(actividad posterior)="+ 
														lsIdEstadoTracking.size());
			
				//se verifica si hay actividad posterior
				if(lsIdEstadoTracking.size() > 0) {
					idEstadoTracking=lsIdEstadoTracking.get(0);
				}else {
					//si no hay actividad posterior, entonces se verifica si hay orden posterior
					lsIdEstadoTracking=trackingMonitorDao.getOrderUpByPosRepOrder(
																		posicion.getIdPosicion(),
																		modeloRscPosFase.getOrden(),
																		idRepPos);
					
					log4j.debug("<delete> lsIdEstadoTracking(orden posterior)="+
										lsIdEstadoTracking.size());
					
					//se verifica si hay orden posterior
					if(lsIdEstadoTracking.size() > 0) {
						idEstadoTracking=	lsIdEstadoTracking.get(0);
					}
				}
				log4j.debug("<delete> del track posterior idEstadoTracking="+idEstadoTracking);
				
				//Si hay track posterior 
				if(idEstadoTracking != null) {
					
					//Para el estatus 'No cumplido'
					if(idEstadoTracking.longValue() == Constante.EDO_TRACK_NO_CUMPLIDO.longValue()) {
						estadoTracking.setIdEstadoTracking(idEstadoTracking);
					}else {
						//Para los estatus: 'En curso', 'Cumplido' y 'Rechazado'							
						trackingPostulante.setComentario(new StringBuilder(Constante.REASIG_TRACK_MON_ROL).
												append(modeloRscPos.getRol().getDescripcion()).toString());
						estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_CUMPLIDO);
					}
				}else {
					estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_NO_CUMPLIDO);
				}
			
				trackingPostulante.setEstadoTracking(estadoTracking);
			
								
				//se persiste
				idTrackingPostulante=(long)trackingPostulanteDao.create(trackingPostulante);
				
				log4j.debug("<update>  se crea idTrackingPostulante="+idTrackingPostulante);
				
				//create respuesta
				trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(null,new 
														MensajeDto("idTrackingPostulante",
														String.valueOf(idTrackingPostulante),
														Mensaje.SERVICE_CODE_004,
														Mensaje.SERVICE_TYPE_INFORMATION,
														null)));
				
				/**  BitacoraTracking */			
				//bitacora tracking monitor
				btcTrackingDto=new BtcTrackingDto();
				btcTrackingMonPosDto=new BtcTrackingMonPosDto();
				btcTrackingMonPosDto.setIdTrackingPostulante(idTrackingPostulante);
				btcTrackingMonPosDto.setIdModeloRscPosFase(Long.parseLong(
										trackingPostulanteDto.getIdModeloRscPosFase()));
				btcTrackingMonPosDto.setIdEstadoTracking(trackingPostulante.getEstadoTracking().
																		getIdEstadoTracking());
				btcTrackingMonPosDto.setIdCandidato(trackingPostulanteDto.getIdCandidato() != null ?
										Long.parseLong(trackingPostulanteDto.getIdCandidato()):null);
				btcTrackingMonPosDto.setIdPosibleCandidato(trackingPostulanteDto.getIdPosibleCandidato() != null ?
												Long.parseLong(trackingPostulanteDto.getIdPosibleCandidato()):null);
				btcTrackingMonPosDto.setComentario(trackingPostulante.getComentario());
				btcTrackingMonPosDto.setFechaInicio(trackingPostulante.getFechaInicio());
				btcTrackingMonPosDto.setFechaFin(trackingPostulante.getFechaFin());
				btcTrackingMonPosDto.setNotifEnviada(trackingPostulante.isNotificacionEnviada() ?						 
													Constante.BOL_TRUE_VAL:
													Constante.BOL_FALSE_VAL);
				
												
				btcTrackingDto.setBtcTrackingMonPosDto(btcTrackingMonPosDto);
				btcTrackingDto.setIdBitacoraTrackRel(trackingPostulanteDto.getIdBitacoraTrack());
				btcTrackingDto.setIdPosicion(Long.parseLong(trackingPostulanteDto.getIdPosicion()));
				btcTrackingDto.setIdRelacionEmpresaPersona(adminService.getRelacionEmpresaPersona(
															trackingPostulanteDto.getIdPersona(), 
															trackingPostulanteDto.getIdEmpresaConf()).
											   				getIdRelacionEmpresaPersona());
				btcTrackingDto.setIdTipoModuloBitacora(Constante.TIPO_MOD_BTC_TRACK_POS);
				btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_CREATE);
				btcTrackingDto.setPorSistema(trackingPostulanteDto.getIdBitacoraTrack() != null ?
											true:false);
				
				//se crea
				resp=bitacoraTrackingService.create(btcTrackingDto);
				log4j.debug("<create> bitacoraTrackingPostulado -> resp: "+ resp);								
			}else {
				trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
											null, 
											new MensajeDto(null,null,
											Mensaje.SERVICE_CODE_006,
											Mensaje.SERVICE_TYPE_ERROR,
											sMensajeError)));
			}			
		}else{
			trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(null, 
												new MensajeDto(null,null,
												trackingPostulanteDto.getCode(),
												trackingPostulanteDto.getType(),
												trackingPostulanteDto.getMessage())));
		}
				
		log4j.debug("<create> return getMessages="+trackingPostulanteDto.getMessages());
		return trackingPostulanteDto.getMessages();
	}
	
	/**
	 * Se crea un nuevo tracking monitor, si corresponde TrackingPostulante
	 * @param trackingPostulanteDto
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String createAll(TrackingPostulanteDto trackingPostulanteDto)
			throws Exception {
		log4j.debug("<createAll> -> getIdEmpresaConf="+trackingPostulanteDto.getIdEmpresaConf()+
				" getIdPersona="+trackingPostulanteDto.getIdPersona()+
				" getIdPerfil="+trackingPostulanteDto.getIdPerfil()+
				" getIdPosicion="+trackingPostulanteDto.getIdPosicion()+
				" getMonitores="+(trackingPostulanteDto.getMonitores() == null ? null:
									trackingPostulanteDto.getMonitores()));
		
		filtros(trackingPostulanteDto,Constante.F_CREATE_ALL);
		log4j.debug("<createAll> -> getCode="+trackingPostulanteDto.getCode());
		if(trackingPostulanteDto.getCode() == null){			
			 alMenosHayUnConfirmado=false;
			 errorMonitor=false;
			
			//si solo hay uno sin error
			numCandidatoSinErrores=0;
			
			
			//PRIMERA PARTE:
			//se obtiene PerfilPosicion, en este caso solo hay un perfil
			//si hubiera mas de un perfil sería dado desde la petición en el json
			perfilPosicion=adminService.getPerfilPosicion(trackingPostulanteDto.
																	getIdPosicion());
			
			log4j.debug("<createAll> Posicion -> getNombre="+perfilPosicion.getPosicion().getNombre());
			
			//Se pone el idperfilPosicion y nombre de la Posición
			trackingPostulanteDto.setIdPerfilPosicion(String.valueOf(perfilPosicion.
														  			getIdPerfilPosicion()));
			posicion=perfilPosicion.getPosicion();
			trackingPostulanteDto.setNombrePosicion(posicion.getNombre());
			
			//se obtiene de la persona que esta ejecutando
			trackingPostulanteDto.setIdRelacionEmpresaPersona(String.valueOf(
											adminService.getRelacionEmpresaPersona(
											trackingPostulanteDto.getIdPersona(), 
											trackingPostulanteDto.getIdEmpresaConf()).
											getIdRelacionEmpresaPersona()));
			//idREPPrincipal=;
			
			//Se obtienen los monitores y candidatos
			itMonitoresDto=trackingPostulanteDto.getMonitores().iterator();
			
			while(itMonitoresDto.hasNext()){
				monitorDto=itMonitoresDto.next();
				log4j.debug("<createAll> getIdMonitor_IdPersona="+monitorDto.getIdPersona());
				
				//Se verifica si existe el monitor
				if(monitorDao.countMonitor(posicion.getIdPosicion(),
											Long.valueOf(monitorDto.getIdPersona())) == 0){
					//No hay monitor relacionado a la persona y posición
					trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
												trackingPostulanteDto.getMessages(), 
												new MensajeDto("idPersona",
												monitorDto.getIdPersona(),
					  							Mensaje.SERVICE_CODE_002,
												Mensaje.SERVICE_TYPE_ERROR, 
												"Para el monitor. "+Mensaje.MSG_ERROR_EMPTY)));
					errorMonitor=true;
					break;
				}
				
				
				// Con los candidatos se crean registros en la tabla: Tracking_Postulante				 
				itCandidatosDto=monitorDto.getCandidatos().iterator();
				while(itCandidatosDto.hasNext()){
					
					//sumamos 1 por candidato
					numCandidatoSinErrores++;
					
					candidatoTrackingDto=itCandidatosDto.next();
					
					log4j.debug("<createAll> Tracking_Esquema_Persona -> getIdCandidato="+candidatoTrackingDto.getIdCandidato()+
								" getIdModeloRscPos="+candidatoTrackingDto.getIdModeloRscPos()+
								" isError="+candidatoTrackingDto.isError()+
								" getConfirmado="+candidatoTrackingDto.getConfirmado());
					
					//Se analiza si esta confirmado
					if(candidatoTrackingDto.getConfirmado() != null){
						confirmado=(Integer.valueOf(candidatoTrackingDto.getConfirmado()).intValue()  == 0 ? false:true);
						
						//almenos un confirmado
						if(confirmado.booleanValue()){
							alMenosHayUnConfirmado=true;
						}
					}else{
						confirmado=null;
					}
					log4j.debug("<createAll> Tracking_Esquema_Persona -> confirmado="+confirmado);			
					
					//Si el postulante es posible_candidato_a o posible_candidato_b
					if(!candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_1)){
						
						//Se genera Persona y RelacionEmpresaPersona
						curriculumDto=new CurriculumDto();
						
						//IdEmpresaConf
						curriculumDto.setIdEmpresaConf(trackingPostulanteDto.getIdEmpresaConf());
						
						//Para POSIBLE_CANDIDATO_2
						if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_2)){
							
							 persona=personaDao.read(Long.parseLong(candidatoTrackingDto.getIdPersona()));
							log4j.debug("<createAll> POSIBLE_CANDIDATO_2 -> persona="+persona);				
							
							if(persona != null){
								log4j.debug("<createAll> POSIBLE_CANDIDATO_2 -> idPersona="+persona.getIdPersona()+
										" idPosicion="+posicion.getIdPosicion());
								
								log4j.debug("<createAll> POSIBLE_CANDIDATO_2 ->  se revisa si la persona es posible_candidato" );	
								
								//Se comprueba si la persona ya es posible_candidato para la posición correspondiente						
								if(posibleCandidatoDao.getIdPosibleCandidato(persona.getIdPersona(), 
																			posicion.getIdPosicion()) != null){
									log4j.debug("<createAll> POSIBLE_CANDIDATO_2 -> la persona es posible_candidato" );		
									
									//Se comprueba que existe idPersona
									trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
																trackingPostulanteDto.getMessages(), 
											  					new MensajeDto("idPersona",
											  					candidatoTrackingDto.getIdPersona(),
									  							Mensaje.SERVICE_CODE_016,
																Mensaje.SERVICE_TYPE_ERROR,
																"Ya existe Posible_Candidato para esta persona")));
									candidatoTrackingDto.setError(true);
									
									//se quita 1 por el error
									numCandidatoSinErrores--;
									
									continue;								
								}
								log4j.debug("<createAll> POSIBLE_CANDIDATO_2 -> se revisa si la persona  es precandidato indexado " );	
								
								//Se comprueba si la persona ya es precandidato indexado para la posición correspondiente								
								if(candidatoDao.get(posicion.getIdPosicion(),persona.getIdPersona()) != null ){
									
									log4j.debug("<createAll> POSIBLE_CANDIDATO_2 -> la persona ya es precandidato indexado " );	

									//Se comprueba que existe idPersona
									trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
																trackingPostulanteDto.getMessages(), 
											  					new MensajeDto("idPersona",
											  					candidatoTrackingDto.getIdPersona(),
									  							Mensaje.SERVICE_CODE_016,
																Mensaje.SERVICE_TYPE_ERROR,
																"Ya existe Pre-candidato indexado para esta persona")));
									candidatoTrackingDto.setError(true);
									
									//se quita 1 por el error
									numCandidatoSinErrores--;
									
									continue;
								}
							
							//Se pone el idPersona
							curriculumDto.setIdPersona(candidatoTrackingDto.getIdPersona());
							
							}else{
							
								//Se comprueba que existe idPersona
								trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
															trackingPostulanteDto.getMessages(), 
										  					new MensajeDto("idPersona",
										  					candidatoTrackingDto.getIdPersona(),
								  							Mensaje.SERVICE_CODE_002,
															Mensaje.SERVICE_TYPE_ERROR, 
															Mensaje.MSG_ERROR_EMPTY)));
								candidatoTrackingDto.setError(true);
								
								//se quita 1 por el error
								numCandidatoSinErrores--;
								
								continue;
							}							
							
						//Para POSIBLE_CANDIDATO_3	
						}else if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_3)){

							//email
							//Se verifica que no exista el correo
							curriculumDto.setEmail(candidatoTrackingDto.getEmail());
							List<CurriculumDto> lsCurriculumDto= personaDao.findEmail(curriculumDto.
																					  getEmail().trim());
							log4j.debug("<createAll> POSIBLE_CANDIDATO_3 -> lsCurriculumDto="+lsCurriculumDto);				
							
							//si existe el email
							if(lsCurriculumDto != null && lsCurriculumDto.size() > 0){
								trackingPostulanteDto.setMessages( UtilsTCE.getJsonMessageGson(
																		trackingPostulanteDto.getMessages(), 
													  					new MensajeDto("email",curriculumDto.getEmail(),
													  					Mensaje.SERVICE_CODE_002,
													  					Mensaje.SERVICE_TYPE_ERROR,
													  					Mensaje.FILTER_EMAIL_EXISTE)));
								log4j.debug("<createAll> posibleCandidato -> si existe el email -> continuar");	
								candidatoTrackingDto.setError(true);
								
								//se quita 1 por el error
								numCandidatoSinErrores--;
								
								continue;
							}
							
							//nombre
							curriculumDto.setNombre(candidatoTrackingDto.getNombre());
							curriculumDto.setApellidoPaterno(candidatoTrackingDto.getApellidoPaterno());
							
							if(candidatoTrackingDto.getApellidoMaterno() != null){
								curriculumDto.setApellidoMaterno(candidatoTrackingDto.getApellidoMaterno());
							}
													
							//Se genera el psw
							candidatoTrackingDto.setPwsCad(UtilsTCE.
												getCadenaAlfanumAleatoria(Constante.LENGHT_PASSWORD));
							
							//se encripta el psw
							curriculumDto.setPassword(Hashing.sha256().hashString(
													candidatoTrackingDto.getPwsCad(),
													StandardCharsets.UTF_8).toString());
							
							//si no esta confirmado guardar el password sin encrptar tambien
							//para cuando se confirme en un futuro
							if(confirmado == null || !confirmado.booleanValue()){
								curriculumDto.setPasswordIniSistema(candidatoTrackingDto.getPwsCad());				
							}
							
							log4j.debug("<createAll> posibleCandidato -> getEmail:"+curriculumDto.getEmail()+								
										" psw:"+candidatoTrackingDto.getPwsCad()+
										" pswEncrip:"+curriculumDto.getPassword());

							//Se crea persona
							resp=curriculumService.createDetail(curriculumDto);
							log4j.debug("<createAll> posibleCandidato ->se crea persona -> resp:"+resp);
							
							//algun error
							if(!resp.equals(Mensaje.SERVICE_MSG_OK_JSON)){
								trackingPostulanteDto.setMessages( UtilsTCE.getJsonMessageGson(
														trackingPostulanteDto.getMessages(), 
									  					new MensajeDto("email",curriculumDto.getEmail(),
									  					resp)));
								candidatoTrackingDto.setError(true);
								
								//se quita 1 por el error
								numCandidatoSinErrores--;
								
								continue;
							}
						}
						
						//Si no hay error, seguir
						if(!candidatoTrackingDto.isError()){
						
							log4j.debug("<createAll> se crea persona -> getIdPersona="+curriculumDto.getIdPersona()+
										" nombrePosicion="+trackingPostulanteDto.getNombrePosicion());						
							
							//Se genera objeto PosibleCandidato
							posibleCandidato= new PosibleCandidato();
							
							log4j.debug("<createAll> Se crea PosibleCandidato");
							
							perfilPosicion=new PerfilPosicion();
							perfilPosicion.setIdPerfilPosicion(Long.parseLong(trackingPostulanteDto.
																				getIdPerfilPosicion()));
							posibleCandidato.setPerfilPosicion(perfilPosicion);
							
							relacionEmpresaPersona=new RelacionEmpresaPersona();
							
							//Se obtiene el IdRelacionEmpresaPersona
							relacionEmpresaPersona.setIdRelacionEmpresaPersona(adminService.getRelacionEmpresaPersona(
																				curriculumDto.getIdPersona(), 
																				trackingPostulanteDto.getIdEmpresaConf()).
																				getIdRelacionEmpresaPersona());
							
							candidatoTrackingDto.setIdRelacionEmpresaPersona(String.valueOf(
															relacionEmpresaPersona.getIdRelacionEmpresaPersona()));
							
							posibleCandidato.setRelacionEmpresaPersona(relacionEmpresaPersona);	
							
							//Se pone estatusOperativo a seleccionado
							posibleCandidato.setEstatusOperativo(new EstatusOperativo(
											 Constante.ESTATUS_CANDIDATO_OPERATIVO_SELECCIONADO,null));									
							
							//Se analiza el estatus de confirmado
							if(confirmado != null && confirmado.booleanValue()){
								posibleCandidato.setConfirmado(confirmado.booleanValue());
																
							}					
							
							log4j.debug(" <createAll> posibleCandidato ->  getPerfilPosicion=="+posibleCandidato.getPerfilPosicion()+
										" getIdRelacionEmpresaPersona="+candidatoTrackingDto.getIdRelacionEmpresaPersona());
							
							//Se analiza si fue confirmado el posible Candidato
							//Para POSIBLE_CANDIDATO_2
							if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_2)){													
								
								//se investiga si tiene almenos una area_persona confirmada 						
								countConfirmado=areaPersonaDao.countConfirmado(Long.parseLong(
																candidatoTrackingDto.getIdPersona()));
								
								log4j.debug("<createAll>  ES_POSIBLE_CANDIDATO_2 ->  countConfirmado=="+countConfirmado);
								
								 //Caso 1. El postulante publicó su CV y fue clasificado con area distinta a la posicion 
								 if(countConfirmado != null && countConfirmado > 0){
									
									//Tipo_Posible_Candidato
									posibleCandidato.setTipoPosibleCandidato(new TipoPosibleCandidato(
																			Constante.POSIBLE_CANDIDATO_2_1));
									 
									candidatoTrackingDto.setIdTipoPosibleCandidato(Constante.POSIBLE_CANDIDATO_2_1.toString());
															 
								 }else{
									//Caso 2. El postulante esta activo pero no ha publicado o/y 
									//no tiene ninguna area confirmada
									 
									//Tipo_Posible_Candidato
									posibleCandidato.setTipoPosibleCandidato(new TipoPosibleCandidato(
																			Constante.POSIBLE_CANDIDATO_2_2));
									
									candidatoTrackingDto.setIdTipoPosibleCandidato(
																	Constante.POSIBLE_CANDIDATO_2_2.toString());
								 }							
							//Para POSIBLE_CANDIDATO_3	
							}else if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_3)){
								
								//Tipo_Posible_Candidato
								posibleCandidato.setTipoPosibleCandidato(new TipoPosibleCandidato(
																		Constante.POSIBLE_CANDIDATO_3));
							}
							
							//Se crea PosibleCandidato						
							candidatoTrackingDto.setIdPosibleCandidato(((Long)posibleCandidatoDao.
																		create(posibleCandidato)).toString());		
							
							log4j.debug("<createAll> posibleCandidato ->  getPerfilPosicion=="+posibleCandidato.getPerfilPosicion()+
									" getRelacionEmpresaPersona="+posibleCandidato.getRelacionEmpresaPersona());
							
							//bitacora tracking
							//bitacora candidatos
							btcTrackingDto=new BtcTrackingDto(); 
							btcTrackCandDto=new BtcTrackCandDto();
							btcTrackCandDto.setIdPosibleCandidato(Long.valueOf(
											candidatoTrackingDto.getIdPosibleCandidato()));
							btcTrackCandDto.setIdEstatusOperativo(posibleCandidato.
											getEstatusOperativo().getIdEstatusOperativo());
							btcTrackCandDto.setConfirmado(confirmado != null ? 
														 (confirmado.booleanValue()):false);
							btcTrackCandDto.setIdPerfilPosicion(posibleCandidato.
												getPerfilPosicion().getIdPerfilPosicion());
							btcTrackCandDto.setIdRelacionEmpresaPersona(relacionEmpresaPersona.
																getIdRelacionEmpresaPersona());	
							btcTrackCandDto.setIdTipoPosibleCandidato(posibleCandidato.
											getTipoPosibleCandidato().getIdTipoPosibleCandidato());							
							 
							btcTrackingDto.setBtcTrackCandDto(btcTrackCandDto);
							btcTrackingDto.setIdPosicion(posicion.getIdPosicion());
							btcTrackingDto.setIdRelacionEmpresaPersona(Long.valueOf(trackingPostulanteDto.
																			getIdRelacionEmpresaPersona()));
							btcTrackingDto.setIdTipoModuloBitacora(confirmado != null ?
											(confirmado ? Constante.TIPO_MOD_BTC_CAND:
													Constante.TIPO_MOD_BTC_PRE_CAND):
													Constante.TIPO_MOD_BTC_PRE_CAND);
							btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_ESCOGE);
							btcTrackingDto.setPorSistema(false);
							
							resp=bitacoraTrackingService.create(btcTrackingDto);
							log4j.debug("<createAll> bitacoraTracking -> resp: "+ resp);
							lsBTMensajeDto=gson.fromJson(resp, new TypeToken<List<MensajeDto>>(){}.getType());
							log4j.debug("<createAll> bitacoraTracking -> lsBTMensajeDto: "+ 
										(lsBTMensajeDto != null ? lsBTMensajeDto.size():null));
							candidatoTrackingDto.setIdBitacoraTrack(Long.parseLong(lsBTMensajeDto.get(0).getValue()));							
						}	
							
					//Para POSIBLE_CANDIDATO_1
					}else{						
						log4j.debug("<createAll> POSIBLE_CANDIDATO_1 idCandidato:"+candidatoTrackingDto.getIdCandidato()+
								" getOrden="+candidatoTrackingDto.getOrden());
						
						 candidato=candidatoDao.read(Long.parseLong(
															  candidatoTrackingDto.getIdCandidato()));
						log4j.debug("<createAll> POSIBLE_CANDIDATO_1 Candidato:"+candidato);
						
						//existe candidato
						if(candidato != null){
							
							log4j.debug("<createAll> POSIBLE_CANDIDATO_1 candidato_IdEstatusOperativo:"+
											candidato.getEstatusOperativo().getIdEstatusOperativo() );
							
							//si el candidato esta inactivo, significa que tiene su tracking
							if(candidato.getEstatusOperativo().getIdEstatusOperativo() ==
							   Constante.ESTATUS_CANDIDATO_OPERATIVO_INACTIVO.longValue()) {
								
								//se verifica si el estatus de algun tracking_monitor esta rechazado
								if(trackingMonitorDao.countByPosREPPost(candidato.getIdCandidato(),
										 				Constante.EDO_TRACK_RECHAZADO.longValue()) > 0) {
									
									//se cambia el estatus a descartado								
									candidato.setEstatusOperativo(new EstatusOperativo(
											Constante.ESTATUS_CANDIDATO_OPERATIVO_DESCARTADO.longValue(),null));
								}else {
									//se cambia el estatus a  en proceso								
									candidato.setEstatusOperativo(new EstatusOperativo(
											Constante.ESTATUS_CANDIDATO_OPERATIVO_EN_PROCESO.longValue(),null));
								}
								
								//y se quita de la lista
								itCandidatosDto.remove();
								
							}else {
								 //se investiga si ya existe tracking para este candidato
								 countCandidates=trackingPostulanteDao.countRecordsCandidates(
										 									candidato.getIdCandidato());
								 log4j.debug("<createAll> countCandidates:"+countCandidates);
								 
								 if(countCandidates != null && countCandidates.longValue() > 0){
									 
										//Se comprueba que existe idPersona
										trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
																	trackingPostulanteDto.getMessages(), 
												  					new MensajeDto("idCandidato",
												  					candidatoTrackingDto.getIdCandidato(),
										  							Mensaje.SERVICE_CODE_016,
																	Mensaje.SERVICE_TYPE_ERROR,
																	"Ya existe Tracking para este preCandidato")));
										candidatoTrackingDto.setError(true);
										
										//se quita 1 por el error
										numCandidatoSinErrores--;
										
										continue;	
								 }
								
								
								  //Se efectua el update
								  if(candidatoTrackingDto.getOrden() != null){
									  candidato.setOrden(Short.parseShort(
											  			 candidatoTrackingDto.getOrden()));
								  }
								  
								  if(confirmado != null){
									  candidato.setConfirmado(confirmado);
								  }
								 							  
								//Se pone estatusOperativo a seleccionado
								candidato.setEstatusOperativo(new EstatusOperativo(
												Constante.ESTATUS_CANDIDATO_OPERATIVO_SELECCIONADO.longValue(),null));
								log4j.debug("<createAll> se escoge idCandidato= "+candidato.getIdCandidato()); 
								
								//bitacora candidatos
								btcTrackingDto=new BtcTrackingDto(); 
								btcTrackCandDto=new BtcTrackCandDto();
								btcTrackCandDto.setIdCandidato(candidato.getIdCandidato());
								btcTrackCandDto.setIdEstatusOperativo(Constante.
												ESTATUS_CANDIDATO_OPERATIVO_SELECCIONADO.longValue());
								btcTrackCandDto.setConfirmado(confirmado != null ? 
															 (confirmado.booleanValue()):false);
								 
								btcTrackingDto.setBtcTrackCandDto(btcTrackCandDto);
								btcTrackingDto.setIdPosicion(posicion.getIdPosicion());
								btcTrackingDto.setIdRelacionEmpresaPersona(Long.valueOf(trackingPostulanteDto.
																				getIdRelacionEmpresaPersona()));
								btcTrackingDto.setIdTipoModuloBitacora(confirmado != null ?
												(confirmado ? Constante.TIPO_MOD_BTC_CAND:
														Constante.TIPO_MOD_BTC_PRE_CAND):
														Constante.TIPO_MOD_BTC_PRE_CAND);
								btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_ESCOGE);
								btcTrackingDto.setPorSistema(false);
								
								resp=bitacoraTrackingService.create(btcTrackingDto);
								log4j.debug("<createAll> bitacoraTracking -> resp: "+ resp);
								lsBTMensajeDto=gson.fromJson(resp, new TypeToken<List<MensajeDto>>(){}.getType());
								log4j.debug("<createAll> bitacoraTracking -> lsBTMensajeDto: "+ 
											(lsBTMensajeDto != null ? lsBTMensajeDto.size():null));
								candidatoTrackingDto.setIdBitacoraTrack(Long.parseLong(lsBTMensajeDto.get(0).getValue()));								
							} 
						  }else{
							  log4j.warn("El idCandidato:"+candidatoTrackingDto.getIdCandidato()+" , no existe" );
							  
							  //Se pone idCandidato en el mensaje de regreso						
							  trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
																	trackingPostulanteDto.getMessages(), 
																	new MensajeDto("idCandidato",
																	candidatoTrackingDto.getIdCandidato(),
																	Mensaje.SERVICE_CODE_002,
																	Mensaje.SERVICE_TYPE_ERROR, 
																	Mensaje.MSG_ERROR_EMPTY)));
								candidatoTrackingDto.setError(true);
								
								//se quita 1 por el error
								numCandidatoSinErrores--;
							  continue;
						  }		
					}					
				}									
			}
			
			log4j.debug("<createAll> alMenosHayUnConfirmado="+alMenosHayUnConfirmado+
						" numCandidatoSinErrores="+numCandidatoSinErrores);
			
			//Se crean los trackings(BD): postulante y monitor, 
			//Si hay almenos un postulante confirmado
			if(!errorMonitor && (alMenosHayUnConfirmado && numCandidatoSinErrores > 0)){
				createTrackingPersonaMonitor(trackingPostulanteDto);
			}
					
		}else{
			trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(null, 
												new MensajeDto(null,null,
												trackingPostulanteDto.getCode(),
												trackingPostulanteDto.getType(),
												trackingPostulanteDto.getMessage())));
		}
		
		if(trackingPostulanteDto.getMessages() == null){
			trackingPostulanteDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
		}
		log4j.debug("<createAll> return getMessages="+trackingPostulanteDto.getMessages());
		return trackingPostulanteDto.getMessages();
	}
	
	/**
	 * Se confirma la adicion del candidato y en su caso se crea el tracking correspondiente
	 * @param trackingPostulanteDto
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String confirm(TrackingPostulanteDto trackingPostulanteDto)
			throws Exception {
		
		filtros(trackingPostulanteDto,Constante.F_CONFIRM);
		log4j.debug("<confirm> -> getCode="+trackingPostulanteDto.getCode());
		if(trackingPostulanteDto.getCode() == null){
			
			//se obtiene de la persona que esta ejecutando
			trackingPostulanteDto.setIdRelacionEmpresaPersona(String.valueOf(
										adminService.getRelacionEmpresaPersona(
										trackingPostulanteDto.getIdPersona(), 
										trackingPostulanteDto.getIdEmpresaConf()).
										getIdRelacionEmpresaPersona()));
			
			//Se obtiene el nombre de la posición
			posicion=posicionDao.read(Long.valueOf(trackingPostulanteDto.getIdPosicion()));
			if(posicion != null){
				candidato=null;
				posibleCandidato=null;
				
				//si solo hay uno sin error
				numCandidatoSinErrores=0;
				
				//se pone nombre de la posicion
				trackingPostulanteDto.setNombrePosicion(posicion.getNombre());
				
				//Se obtienen los monitores y candidatos
				Iterator<TrackingMonitorDto> itMonitoresDto=trackingPostulanteDto.
															getMonitores().iterator();
				
				//Se adiciona datos al paquete
				while(itMonitoresDto.hasNext()){
					monitorDto=itMonitoresDto.next();
					log4j.debug(" getIdMonitor_IdPersona="+monitorDto.getIdPersona());
					
					// Con los candidatos se crean registros en la tabla: Tracking_Postulante			 
					itCandidatosDto=monitorDto.getCandidatos().iterator();
					while(itCandidatosDto.hasNext()){
						 candidatoTrackingDto=itCandidatosDto.next();
						
						numCandidatoSinErrores++;
						
						//se pone forzosamente 
						candidatoTrackingDto.setConfirmado("1");											
						
						//Para POSIBLE_CANDIDATO_2 y Para POSIBLE_CANDIDATO_3
						if(!candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_1)){
							
							//Se obtiene idPersona
							idPersona=posibleCandidatoDao.getIdPersona(Long.parseLong(
															candidatoTrackingDto.getIdPosibleCandidato()));
							
							if(idPersona != null){
								
								//Se verifica que no exista el tracking_persona del posible_candidato
								 countCandidates=trackingPostulanteDao.countRecordsPossibleCandidates(Long.parseLong(
																			candidatoTrackingDto.getIdPosibleCandidato()));
								 
								 if(countCandidates != null && countCandidates.longValue() > 0){
									 	numCandidatoSinErrores--;
										
										candidatoTrackingDto.setError(true);
										
										//Se comprueba que no existe pswIniSystem
										trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
																	trackingPostulanteDto.getMessages(), 
												  					new MensajeDto("idPosibleCandidato",
												  					candidatoTrackingDto.getIdPosibleCandidato(),
										  							Mensaje.SERVICE_CODE_016,
																	Mensaje.SERVICE_TYPE_ERROR, 
																	"Ya existe un tracking para este Posible Candidato")));
										log4j.error("Existe el tracking_persona del idPosibleCandidato="+
													candidatoTrackingDto.getIdPosibleCandidato());
										continue;
								 }
								
								
								candidatoTrackingDto.setIdPersona(idPersona.toString());
								candidatoTrackingDto.setIdRelacionEmpresaPersona(String.valueOf(
															adminService.getRelacionEmpresaPersona(
															candidatoTrackingDto.getIdPersona(), 
															trackingPostulanteDto.getIdEmpresaConf()).
															getIdRelacionEmpresaPersona()));	
								
								//Se obtiene el posibleCandidato
								posibleCandidato=posibleCandidatoDao.read(Long.parseLong(
																	candidatoTrackingDto.getIdPosibleCandidato()));															
								
								//actualiza propiedad confirmado
								posibleCandidato.setConfirmado(Integer.valueOf(candidatoTrackingDto.
																getConfirmado()).intValue()  == 1 ? true:false);
							
								//pone IdTipoPosibleCandidato
								candidatoTrackingDto.setIdTipoPosibleCandidato(String.valueOf(
																posibleCandidato.getTipoPosibleCandidato().
																getIdTipoPosibleCandidato()));
								
								idEstatusOperativo=posibleCandidato.getEstatusOperativo().getIdEstatusOperativo();
							}else{
								numCandidatoSinErrores--;
								
								candidatoTrackingDto.setError(true);
								
								//Se comprueba que no existe pswIniSystem
								trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
															trackingPostulanteDto.getMessages(), 
										  					new MensajeDto("idPosibleCandidato",
										  					candidatoTrackingDto.getIdPosibleCandidato(),
								  							Mensaje.SERVICE_CODE_002,
															Mensaje.SERVICE_TYPE_ERROR, 
															Mensaje.MSG_ERROR_EMPTY)));
								log4j.error("No existe la persona para idPosibleCandidato="+
											candidatoTrackingDto.getIdPosibleCandidato());
							}							
						}else {
							//precandidato 1
							//se analiza si el estatus_operativo=invalido
							candidato=candidatoDao.read(Long.parseLong(
										candidatoTrackingDto.getIdCandidato()));
							idEstatusOperativo=candidato.getEstatusOperativo().getIdEstatusOperativo();
							
							log4j.debug("<confirm> -> idCandidato="+candidatoTrackingDto.getIdCandidato()+
									" idEstatusOperativo="+idEstatusOperativo);
							
							//si esta inactivo
							if(idEstatusOperativo.longValue() == Constante.ESTATUS_CANDIDATO_OPERATIVO_INACTIVO) {
								log4j.debug("<confirm> -> se cambia el estatus operativo A \"PROCESO\" candidato:"+
										candidato);
								
								
								//se verifica si el estatus de algun tracking_monitor esta rechazado
								if(trackingMonitorDao.countByPosREPPost(candidato.getIdCandidato(),
										 				Constante.EDO_TRACK_RECHAZADO.longValue()) > 0) {
									
									//se cambia el estatus a descartado								
									candidato.setEstatusOperativo(new EstatusOperativo(
											Constante.ESTATUS_CANDIDATO_OPERATIVO_DESCARTADO.longValue(),null));
								}else {
									//se cambia el estatus a  en proceso								
									candidato.setEstatusOperativo(new EstatusOperativo(
											Constante.ESTATUS_CANDIDATO_OPERATIVO_EN_PROCESO.longValue(),null));
								}
								
								//se quita de la lista
								itCandidatosDto.remove();								
							}
						}
						
						//Para POSIBLE_CANDIDATO_3
						if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_3)){
								
							//Se obtiene el password							
							pswIniSystem=posibleCandidatoDao.getPswIniSystem(Long.parseLong(
															candidatoTrackingDto.getIdPosibleCandidato()));
							log4j.debug("ES_POSIBLE_CANDIDATO_3 ->  pswIniSystem="+pswIniSystem);
							
							if(pswIniSystem != null){
								candidatoTrackingDto.setPwsCad(pswIniSystem);															
							}else{
								numCandidatoSinErrores--;
								
								log4j.error("No hay pswIniSystem para idPosibleCandidato="+
																		candidatoTrackingDto.getIdPosibleCandidato());
								candidatoTrackingDto.setError(true);
								
								//Se comprueba que no existe pswIniSystem
								trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
															trackingPostulanteDto.getMessages(), 
										  					new MensajeDto("idPosibleCandidato",
										  					candidatoTrackingDto.getIdPosibleCandidato(),
								  							Mensaje.SERVICE_CODE_002,
															Mensaje.SERVICE_TYPE_ERROR, 
															Mensaje.MSG_ERROR_EMPTY)));
								
							}
						}	
						
						log4j.debug("<confirm> -> candidato="+candidato+
									" posibleCandidato="+posibleCandidato);
						
						//bitacora tracking
						btcTrackingDto=new BtcTrackingDto(); 
						btcTrackCandDto=new BtcTrackCandDto();
						btcTrackCandDto.setIdCandidato(candidato != null ? 
											candidato.getIdCandidato(): null);
						btcTrackCandDto.setIdPosibleCandidato(posibleCandidato != null ?
											posibleCandidato.getIdPosibleCandidato():null);
						btcTrackCandDto.setIdEstatusOperativo(idEstatusOperativo);
						btcTrackCandDto.setConfirmado(true);
						btcTrackCandDto.setIdPerfilPosicion(posibleCandidato != null ? 
								posibleCandidato.getPerfilPosicion().getIdPerfilPosicion():null);
						btcTrackCandDto.setIdRelacionEmpresaPersona(
								candidatoTrackingDto.getIdRelacionEmpresaPersona() != null ?
								Long.parseLong(candidatoTrackingDto.getIdRelacionEmpresaPersona()):null);	
						btcTrackCandDto.setIdTipoPosibleCandidato(posibleCandidato != null ? 
														posibleCandidato.getTipoPosibleCandidato().
														getIdTipoPosibleCandidato():null);							
							 
						btcTrackingDto.setBtcTrackCandDto(btcTrackCandDto);
						btcTrackingDto.setIdPosicion(posicion.getIdPosicion());
						btcTrackingDto.setIdRelacionEmpresaPersona(Long.valueOf(trackingPostulanteDto.
																		getIdRelacionEmpresaPersona()));
						btcTrackingDto.setIdTipoModuloBitacora(posibleCandidato != null ?
																Constante.TIPO_MOD_BTC_PRE_CAND:
																Constante.TIPO_MOD_BTC_CAND);
						btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_CONFIRM);
						btcTrackingDto.setPorSistema(false);
						
						resp=bitacoraTrackingService.create(btcTrackingDto);
						log4j.debug("<confirm> bitacoraTracking -> resp: "+ resp);
						lsBTMensajeDto=gson.fromJson(resp, new TypeToken<List<MensajeDto>>(){}.getType());
						log4j.debug("<confirm> bitacoraTracking -> lsBTMensajeDto: "+ 
									(lsBTMensajeDto != null ? lsBTMensajeDto.size():null));
						candidatoTrackingDto.setIdBitacoraTrack(Long.parseLong(lsBTMensajeDto.get(0).getValue()));
						
					}
				}
				
				log4j.error("numCandidatoSinErrores="+numCandidatoSinErrores);
				
				if(numCandidatoSinErrores > 0){
					
					//Se crean los trackings: postulante y monitor
					createTrackingPersonaMonitor(trackingPostulanteDto);
				}								
			}else{
				
				//Se comprueba que existe idPosicion
				trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
											trackingPostulanteDto.getMessages(), 
						  					new MensajeDto("idPosicion",
						  					trackingPostulanteDto.getIdPosicion(),
				  							Mensaje.SERVICE_CODE_002,
											Mensaje.SERVICE_TYPE_ERROR, 
											Mensaje.MSG_ERROR_EMPTY)));
			}
			
		}else{
			trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(null, 
												new MensajeDto(null,null,
												trackingPostulanteDto.getCode(),
												trackingPostulanteDto.getType(),
												trackingPostulanteDto.getMessage())));
		}

		
		log4j.debug(" return getMessages="+trackingPostulanteDto.getMessages());
		return trackingPostulanteDto.getMessages();
	}


	
	/**
	 * Se crea el Tracking para el postulante y el monitor
	 * @param trackingPostulanteDto
	 * @throws Exception 
	 */
	private void createTrackingPersonaMonitor(TrackingPostulanteDto trackingPostulanteDto) 
																					throws Exception{
		String respService;		
		hmTrackingMonitorDto= new HashMap<String, TrackingMonitorDto>();
		lsEdoTrackingPostulante=new ArrayList<Long>();
	    sb=new StringBuilder();
		
		//Se obtienen todos los tracking_monitors de la posicion
		lsTrackingMonitorDto =trackingMonitorDao.getAllByPosicion(posicion.getIdPosicion());
		
		log4j.debug("<createTrackingPersonaMonitor> idPosicion="+posicion.getIdPosicion()+
					" lsTrackingMonitorDto="+(lsTrackingMonitorDto == null ? null:lsTrackingMonitorDto.size()));

		//hay datos
		if(lsTrackingMonitorDto != null && lsTrackingMonitorDto.size() > 0) {
			
			itTrackingMonitorDto=lsTrackingMonitorDto.iterator();
			while(itTrackingMonitorDto.hasNext()) {
				trackingMonitorDto=itTrackingMonitorDto.next();
				
				log4j.debug("<createTrackingPersonaMonitor> idEstadoTracking="+trackingMonitorDto.getIdEstadoTracking()+
						   " idTrackingPostulante="+ trackingMonitorDto.getIdTrackingPostulante()+
						   " idMonitor="+trackingMonitorDto.getIdMonitor());
				
				//solo para tracks generales
				if((trackingMonitorDto.getIdEstadoTracking().equals(Constante.EDO_TRACK_CUMPLIDO.toString())  
				 // || trackingMonitorDto.getIdEstadoTracking().equals(Constante.EDO_TRACK_RECHAZADO.toString())) 
				  || trackingMonitorDto.getIdEstadoTracking().equals(Constante.EDO_TRACK_EN_CURSO.toString()))
				   && trackingMonitorDto.getIdTrackingPostulante() == null	) {
					
					
					//se crea key: idMonitor + "," + orden + "," + actividad
					sb.append(trackingMonitorDto.getIdMonitor()).append(",").
					append(trackingMonitorDto.getOrden()).append(",").
					append(trackingMonitorDto.getActividad());
					
					log4j.debug("<createTrackingPersonaMonitor> se adiciona al hm -> sb="+sb.toString());
					
					if(!hmTrackingMonitorDto.containsKey(sb.toString())) {
							
						log4j.debug("<createTrackingPersonaMonitor> adiciona al map");
						//se ponen los primeros tracks generales hasta encontrar un particualr
						hmTrackingMonitorDto.put(sb.toString(), trackingMonitorDto);					
					}
					//se borra
					sb.delete(0, sb.length());
				}else {					
					break;
				}	
			}
		}
		
		log4j.debug("<createTrackingPersonaMonitor> hmTrackingMonitorDto="+hmTrackingMonitorDto.size());

		
		//PRIMERA PARTE: se escribe en la tabla trackingPostulante
		//Se obtienen los monitores y candidatos
		itMonitoresDto=trackingPostulanteDto.getMonitores().iterator();
		
		//Se recorren los monitores
		while(itMonitoresDto.hasNext()){
			 monitorDto=itMonitoresDto.next();
			log4j.debug("<createTrackingPersonaMonitor>  monitor_getIdPersona="+monitorDto.getIdPersona()+
					" getIdModeloRscPos="+monitorDto.getIdModeloRscPos());
			
			// Con los candidatos se crean registros en la tabla: trackingPostulante			 
			itCandidatosDto=monitorDto.getCandidatos().iterator();
			while(itCandidatosDto.hasNext()){
				 candidatoTrackingDto=itCandidatosDto.next();
				
				//Si no hay error, seguir
				if(!candidatoTrackingDto.isError()){
					
					//Se analiza si esta confirmado
					if(candidatoTrackingDto.getConfirmado() != null){
						confirmado=(Integer.valueOf(candidatoTrackingDto.getConfirmado()).
																intValue()  == 0 ? false:true);
					}else{
						confirmado=null;
					}
					
					//si esta confirmado, se crea una notificacion
					if(confirmado != null && confirmado.booleanValue()){
						
						log4j.debug("<createTrackingPersonaMonitor> estados candidato -> isError="+
										candidatoTrackingDto.isError());
						
						// ES_POSIBLE_CANDIDATO_1
						if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_1)){
							
							//set Candidato
							candidato=new Candidato();
							candidato.setIdCandidato(Long.parseLong(candidatoTrackingDto.getIdCandidato()));																			
							
							//se obtiene RelacionEmpresaPersona							
							relacionEmpresaPersona =relacionEmpresaPersonaDao.getRelacionEmpresaPersona(
																			candidato.getIdCandidato(),
																			trackingPostulanteDto.
																			getIdEmpresaConf());
							
							log4j.debug("<createTrackingPersonaMonitor> estados candidato -> relacionEmpresaPersona="+
											relacionEmpresaPersona);
							
							//existe relacionEmpresaPersona
							if(relacionEmpresaPersona == null){
								log4j.error("No hay registro en la tabla RelacionEmpresaPersona para idCandidato="+
											candidato.getIdCandidato());
								
								candidatoTrackingDto.setError(true);
							
								//Se comprueba que no existe pswIniSystem
								trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
															trackingPostulanteDto.getMessages(), 
										  					new MensajeDto("idCandidato",
										  					candidatoTrackingDto.getIdCandidato(),
								  							Mensaje.SERVICE_CODE_002,
															Mensaje.SERVICE_TYPE_ERROR, 
															Mensaje.MSG_ERROR_EMPTY)));							
								continue;
							}								
						 }
						
						//Se crea lista de 
						lsModeloRscPosFaseDto=new ArrayList<ModeloRscPosFaseDto>();
						
						//Obtener la lista de fases del modelo ModeloRscPos para el candidato
						lsModeloRscPosFase=modeloRscPosDao.getModeloRscPosFases(Long.parseLong(candidatoTrackingDto.
																								getIdModeloRscPos()));
						
						log4j.debug("<createTrackingPersonaMonitor> estados candidato -> lsModeloRscPosFase="+
									lsModeloRscPosFase.size());
						log4j.debug("<createTrackingPersonaMonitor>  posible_candidato_2 y 3 IdRelacionEmpresaPersona="+
									candidatoTrackingDto.getIdRelacionEmpresaPersona());
						log4j.debug("<createTrackingPersonaMonitor> 2 posible_candidato_1 IdRelacionEmpresaPersona="+
									(relacionEmpresaPersona == null ? null:relacionEmpresaPersona.
																		getIdRelacionEmpresaPersona()));
						
						itModeloRscPosFase =lsModeloRscPosFase.iterator();
						while(itModeloRscPosFase.hasNext()){
							
							//set modeloRscPosFase
							modeloRscPosFase= itModeloRscPosFase.next();
							
							//Solo las fases activas
							if(modeloRscPosFase.isActivo()){
							
								//Se crea el objeto TrackingPostulante
								trackingPostulante=new TrackingPostulante();
								trackingPostulante.setModeloRscPosFase(modeloRscPosFase);
								
								//estadoTracking
								estadoTracking=new EstadoTracking();
								estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_NO_CUMPLIDO);
											
								log4j.debug("<createTrackingPersonaMonitor>  ordenTrackCumplido="+
											trackingPostulanteDto.getOrdenTrackCumplido());
							
								//Se crea el objeto ModeloRscPosFaseDto							
								modeloRscPosFaseCandidatoDto=new ModeloRscPosFaseDto();
									
								// ES_POSIBLE_CANDIDATO_2 o ES_POSIBLE_CANDIDATO_3
								if(!candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_1)){
									
									// set posibleCandidato
									posibleCandidato= new PosibleCandidato();
									posibleCandidato.setIdPosibleCandidato(Long.parseLong(
														candidatoTrackingDto.getIdPosibleCandidato()));								
									trackingPostulante.setPosibleCandidato(posibleCandidato);
									
									//se inyecta  IdRelacionEmpresaPersona
									modeloRscPosFaseCandidatoDto.setIdRelacionEmpresaPersona(Long.parseLong(
																	candidatoTrackingDto.getIdRelacionEmpresaPersona()));							
								}else{	
									// ES_POSIBLE_CANDIDATO_1
									//set candidato		
									trackingPostulante.setCandidato(candidato);
									
									log4j.debug("<createTrackingPersonaMonitor>  Orden="+modeloRscPosFase.getOrden()+
											" getIdEstadoTracking="+ estadoTracking.getIdEstadoTracking());
									
									 //relacionEmpresaPersona								
									modeloRscPosFaseCandidatoDto.setIdRelacionEmpresaPersona(relacionEmpresaPersona.
							    											getIdRelacionEmpresaPersona());																																
								}	
								//para OrdenTrackDefaultCumplido
								if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_2) ||
								   candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_1)){
									
									//Se inicializa el EstadoTracking.
									if(trackingPostulanteDto.getOrdenTrackCumplido() != null ){
										
										if(modeloRscPosFase.getOrden() <= 
										   trackingPostulanteDto.getOrdenTrackCumplido().shortValue()){
											   estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_CUMPLIDO.longValue());
											   trackingPostulante.setComentario(Mensaje.MSG_SYSTEM_REGISTER_DEFAULT);
											   trackingPostulante.setFechaInicio(DateUtily.getToday());
											   trackingPostulante.setFechaFin(DateUtily.getToday());										
										}							
									}	
								}
														
								//Se pone el estadoTracking
								trackingPostulante.setEstadoTracking(estadoTracking);
								
								//set dtos para 
								modeloRscPosFaseCandidatoDto.setIdEstadoTracking(estadoTracking.getIdEstadoTracking());
								if(trackingPostulante.getComentario() != null ){
									modeloRscPosFaseCandidatoDto.setComentario(
													trackingPostulante.getComentario());
								}
								if(trackingPostulante.getFechaInicio() != null ){
									modeloRscPosFaseCandidatoDto.setFechaInicio(
													trackingPostulante.getFechaInicio());
								}
								if(trackingPostulante.getFechaFin() != null ){
									modeloRscPosFaseCandidatoDto.setFechaFin(
													trackingPostulante.getFechaFin());
								}
								log4j.debug("<createTrackingPersonaMonitor>  getIdEstadoTracking="+
												trackingPostulante.getEstadoTracking().getIdEstadoTracking()+
											" getPosibleCandidato="+(trackingPostulante.getPosibleCandidato() != null ? 
															trackingPostulante.getPosibleCandidato().getIdPosibleCandidato():null)+
											" getCandidato="+(trackingPostulante.getCandidato()!= null ? 
															trackingPostulante.getCandidato().getIdCandidato():null)+
											" getComentario="+trackingPostulante.getComentario());						
								
								//create y set TrackingPostulante
								modeloRscPosFaseCandidatoDto.setIdTrackingPostulante((Long)trackingPostulanteDao.
																				create(trackingPostulante));
								
								modeloRscPosFaseCandidatoDto.setIdModeloRscPosFase(modeloRscPosFase.getIdModeloRscPosFase());						
								lsModeloRscPosFaseDto.add(modeloRscPosFaseCandidatoDto);
								
								log4j.debug("<createTrackingPersonaMonitor>  getIdTrackingPostulante="+
											 modeloRscPosFaseCandidatoDto.getIdTrackingPostulante()+
											" setIdModeloRscPosFase="+modeloRscPosFaseCandidatoDto.getIdModeloRscPosFase());
								
								//bitacora tracking postulante
								btcTrackingPosDto= new BtcTrackingMonPosDto();							
								btcTrackingPosDto.setIdTrackingPostulante(modeloRscPosFaseCandidatoDto.
																				getIdTrackingPostulante());								
								btcTrackingPosDto.setIdEstadoTracking(estadoTracking.getIdEstadoTracking());
								btcTrackingPosDto.setComentario(trackingPostulante.getComentario());
								btcTrackingPosDto.setFechaInicio(trackingPostulante.getFechaInicio());
								btcTrackingPosDto.setFechaFin(trackingPostulante.getFechaInicio());
								btcTrackingPosDto.setNotifEnviada(trackingPostulante.isNotificacionEnviada() ? 
																	Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL);
								btcTrackingPosDto.setIdModeloRscPosFase(modeloRscPosFase.getIdModeloRscPosFase());
								btcTrackingPosDto.setIdCandidato(trackingPostulante.getCandidato() == null ? null:
																trackingPostulante.getCandidato().getIdCandidato());
								btcTrackingPosDto.setIdPosibleCandidato(trackingPostulante.getPosibleCandidato() == null ?
														null:trackingPostulante.getPosibleCandidato().getIdPosibleCandidato());
								modeloRscPosFaseCandidatoDto.setBtcTrackingMonPosDto(btcTrackingPosDto); 
							}
						}
						log4j.debug(" lsModeloRscPosFaseDto="+(lsModeloRscPosFaseDto == null ? null:lsModeloRscPosFaseDto.size()));
						candidatoTrackingDto.setLsModeloRscPosFaseDto(lsModeloRscPosFaseDto);
					}
				}
			}
			
			//SEGUNDA PARTE:
			// Con los monitores se crean registros en la tabla: Tracking_Monitor				 		
			//Se analiza si el monitor es principal
			lsMonitor=null;
			if(modeloRscPosDao.getCountMonitor(Long.parseLong(monitorDto.getIdModeloRscPos()), 
											Long.parseLong(monitorDto.getIdPersona()), true) > 0){
				
				//Como es monitor principal sus candidatos se les asigna a todos los monitores
				lsMonitor=monitorDao.getAllMonitors(posicion.getIdPosicion());
				log4j.debug("<createTrackingPersonaMonitor> monitor principal");
				
			}else{
				//como es monitor secundario sus candidatos solo son asignados a él
				lsMonitor=modeloRscPosDao.getMonitor(Long.parseLong(monitorDto.getIdModeloRscPos()),
															   Long.parseLong(monitorDto.getIdPersona()));
				log4j.debug("<createTrackingPersonaMonitor> monitor secundario");
			}
			
			log4j.debug("estados candidato -> lsMonitor="+lsMonitor.size());
						
			//Se recorren los candidatos para obtener: idTrackingPostulante y idModeloRscPosFase
			itCandidatosDto=monitorDto.getCandidatos().iterator();
			while(itCandidatosDto.hasNext()){
				faseSigDeGeneral=true;
				//antRechazo=false;
				candidatoTrackingDto=itCandidatosDto.next();
				log4j.debug("<createTrackingPersonaMonitor> Tracking_Monitor -> getIdCandidato="+
								candidatoTrackingDto.getIdCandidato()+
						" getLsModeloRscPosFaseDto="+(candidatoTrackingDto.getLsModeloRscPosFaseDto() == null ? 
													null:candidatoTrackingDto.getLsModeloRscPosFaseDto().size())+
						" isError="+candidatoTrackingDto.isError()+
						" confirmado="+candidatoTrackingDto.getConfirmado());
				
				//Se analiza si esta confirmado
				if(candidatoTrackingDto.getConfirmado() != null){
					confirmado=(Integer.valueOf(candidatoTrackingDto.getConfirmado()).intValue()  == 0 ? false:true);
				}else{
					confirmado=null;
				}	
				
				//si esta confirmado
				if(confirmado != null && confirmado.booleanValue()){
					//Si no hay error anterior
					if(!candidatoTrackingDto.isError()){
					
						itMonitor=lsMonitor.iterator();
						
						while(itMonitor.hasNext()){
							soloUnaVez=true;
							trackingMonitor=new TrackingMonitor();
							monitor=itMonitor.next();
														
							//se obtiene  modeloRscPosFase
							modeloRscPosFase=monitor.getModeloRscPosFase();
							
							log4j.debug("<createTrackingPersonaMonitor> Tracking_Monitor -> idMonitor="+
										monitor.getIdMonitor()+
										" getOrden="+modeloRscPosFase.getOrden()+
										" getActividad="+modeloRscPosFase.getActividad());
							
							
							//se obtiene el tracking_esquema del monitor
							modeloRscPosFase_Monitor=monitor.getModeloRscPosFase();
							
							log4j.debug("<createTrackingPersonaMonitor>  monitor  idModeloRscPosFaseMonitor="+
										modeloRscPosFase_Monitor.getIdModeloRscPosFase()+
										" idModeloRscPosFaseMonitorRel="+(
										modeloRscPosFase_Monitor.getModeloRscPosFase() != null ?
										modeloRscPosFase_Monitor.getModeloRscPosFase().getIdModeloRscPosFase():null));
							
							//Se asigna el id_monitor							
							trackingMonitor.setMonitor(monitor);							
							
							//Se encuentra el idTrackingPostulante del candidato correspondiente al
							//idModeloRscPosFase del monitor,basandose en la relación en la tabla ModeloRscPosFase
							itModeloRscPosFaseCandidatoDto=candidatoTrackingDto. getLsModeloRscPosFaseDto().
																					   				iterator();
							
							//Se analiza si hay estados del candidato que hacen match con el del monitor
							while(itModeloRscPosFaseCandidatoDto.hasNext()){								
								modeloRscPosFaseCandidatoDto=itModeloRscPosFaseCandidatoDto.next();
								btcTrackingPosDto=null;
								
								//solo una vez se pone IdRelacionEmpresaPersona del postulante
								if(soloUnaVez){									
									relacionEmpresaPersona=new RelacionEmpresaPersona();
									relacionEmpresaPersona.setIdRelacionEmpresaPersona(modeloRscPosFaseCandidatoDto.
																		getIdRelacionEmpresaPersona().longValue());
									//Se pone RelacionEmpresaPersona para el postulante
									trackingMonitor.setRelacionEmpresaPersona(relacionEmpresaPersona);
									soloUnaVez=false;
								}
								
								//IdModeloRscPosFase del monitor y del  candidato coinciden
								if(modeloRscPosFase_Monitor.getModeloRscPosFase() != null){
									
									//hay match
									if(modeloRscPosFase_Monitor.getModeloRscPosFase().getIdModeloRscPosFase() ==
											  modeloRscPosFaseCandidatoDto.getIdModeloRscPosFase()){										
										
										log4j.debug("match modeloRscPosFaseCandidatoDto -> idModeloRscPosFase="+
												modeloRscPosFaseCandidatoDto.getIdModeloRscPosFase()+
											" comentario="+modeloRscPosFaseCandidatoDto.getComentario());
										trackingPostulante=new TrackingPostulante();
										/*trackingPostulante=trackingPostulanteDao.read(modeloRscPosFaseCandidatoDto.
																getIdTrackingPostulante());*/
										trackingPostulante.setIdTrackingPostulante(modeloRscPosFaseCandidatoDto.
																			getIdTrackingPostulante().longValue());
										trackingMonitor.setTrackingPostulante(trackingPostulante);
										
										//set EstadoTracking
										if(modeloRscPosFaseCandidatoDto.getIdEstadoTracking() != null){										
											trackingMonitor.setEstadoTracking(new EstadoTracking(
																	modeloRscPosFaseCandidatoDto.getIdEstadoTracking()));
										}
										
										if(modeloRscPosFaseCandidatoDto.getComentario() != null){
											trackingMonitor.setComentario(
																modeloRscPosFaseCandidatoDto.getComentario());
										}	
										
										if(modeloRscPosFaseCandidatoDto.getFechaInicio() != null){
											trackingMonitor.setFechaInicio(
													modeloRscPosFaseCandidatoDto.getFechaInicio());
										}
										
										if(modeloRscPosFaseCandidatoDto.getFechaFin() != null){
											trackingMonitor.setFechaFin(
													modeloRscPosFaseCandidatoDto.getFechaFin());
										}
										
										log4j.debug("<createTrackingPersonaMonitor> final  match trackingMonitor_IdEstadoTracking="+
													trackingMonitor.getEstadoTracking().getIdEstadoTracking()+
													" faseSigDeGeneral="+faseSigDeGeneral);
										
										//si la fase despues de las generales cumplidas es particular
										if(trackingMonitor.getEstadoTracking().getIdEstadoTracking() == 
													Constante.EDO_TRACK_NO_CUMPLIDO  && faseSigDeGeneral) { 
										
											
											log4j.debug("<createTrackingPersonaMonitor> final  match se adiciona a lsEdoTrackingPostulante idTrackingPostulante="+
													trackingPostulante.getIdTrackingPostulante());
											
											//se adiciona solo al IdTrackingPostulante que no este en la lista
											if(!lsEdoTrackingPostulante.contains(modeloRscPosFaseCandidatoDto.
																						getIdTrackingPostulante())) {
												lsEdoTrackingPostulante.add(modeloRscPosFaseCandidatoDto.
																			getIdTrackingPostulante());
											}
										}
										
										//bitacora tracking										
										if(modeloRscPosFaseCandidatoDto.getBtcTrackingMonPosDto() != null) {
											btcTrackingPosDto=modeloRscPosFaseCandidatoDto.getBtcTrackingMonPosDto();										
										}
										
										break;
									}									
								   }else{
									   
									//set EstadoTracking
									estadoTracking=new EstadoTracking();
									estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_NO_CUMPLIDO);
									trackingMonitor.setEstadoTracking(estadoTracking);
									log4j.debug(" no match IdEstadoTracking="+
												modeloRscPosFaseCandidatoDto.getIdEstadoTracking());
									break;
								}								
							}
																
							log4j.debug(" hmTrackingMonitorDto="+hmTrackingMonitorDto.size() +									
										" faseSigDeGeneral="+faseSigDeGeneral);
							
							//Se busca en el map si hay tracks generales de otros candidatos 
							// hasta el track particular
							if(hmTrackingMonitorDto.size() > 0) {
								
								//key: idMonitor + "," + orden + "," + actividad
								sb.append(monitor.getIdMonitor()).append(",").
								append(modeloRscPosFase.getOrden()).append(",").
								append(modeloRscPosFase.getActividad());
								
								//se busca en el map
								if(hmTrackingMonitorDto.containsKey(sb.toString())) {
									faseSigDeGeneral=true;
									trackingMonitorDto=hmTrackingMonitorDto.get(sb.toString());
									
									log4j.debug("<createTrackingPersonaMonitor>  se crea trackingMonitor para: "+
												sb.toString());
									
									//se crea el trackingMonitor
									estadoTracking=new EstadoTracking();
									estadoTracking.setIdEstadoTracking(Long.parseLong(
													trackingMonitorDto.getIdEstadoTracking()));								
									trackingMonitor.setEstadoTracking(estadoTracking);
									trackingMonitor.setComentario(trackingMonitorDto.getComentario());
									if(trackingMonitorDto.getFechaInicio() != null) {
										trackingMonitor.setFechaInicio(DateUtily.string2Date(
																	trackingMonitorDto.getFechaInicio(), 
																	Constante.DATE_FORMAT_JAVA));
									}
									if(trackingMonitorDto.getFechaFin() != null) {
										trackingMonitor.setFechaFin(DateUtily.string2Date(
																	trackingMonitorDto.getFechaFin(), 
																	Constante.DATE_FORMAT_JAVA));
									}
									trackingMonitor.setOculto(false);
									
									//si este track fue de rechazo
									/*if(trackingMonitorDto.getIdEstadoTracking().equals(
																Constante.EDO_TRACK_RECHAZADO.toString())) {
										antRechazo=true;
										
										//se cambia el estatus de operación del candidato a descartado
										candidatoDao.updateEstatusOperativo(candidato.getIdCandidato(), 
														Constante.ESTATUS_CANDIDATO_OPERATIVO_DESCARTADO.
														longValue());
										
									}*/
									
									//si este track esta en curso el siguiente track se queda como esta
									if(trackingMonitorDto.getIdEstadoTracking().equals(
									Constante.EDO_TRACK_EN_CURSO.toString())) {
										faseSigDeGeneral=false;
									}
									
									//se quita del map
									hmTrackingMonitorDto.remove(sb.toString());
								}
								
								//se borra
								sb.delete(0, sb.length());	
							}else {
								
								//solo cuando es la primera fase se pone EstadoTracking= 2
								if(modeloRscPosFase.getOrden() == Constante.NUM_ORDEN_INICIAL.shortValue() &&
								   modeloRscPosFase.getActividad() == Constante.NUM_ACTIVIDAD_INICIAL.shortValue()) {
									log4j.debug("<createTrackingPersonaMonitor>1 Se cambia IdEstadoTracking= EDO_TRACK_EN_CURSO");
									estadoTracking=new EstadoTracking();
									estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_EN_CURSO);								
									trackingMonitor.setEstadoTracking(estadoTracking);
									trackingMonitor.setOculto(true);
									faseSigDeGeneral=false;
								}
							}
							
							log4j.debug("faseSigDeGeneral="+faseSigDeGeneral+ " trackingMonitor_idEstadoTracking="+
									trackingMonitor.getEstadoTracking().getIdEstadoTracking());
							
							//se aplica para la fase posterior, cuando hubo tracks generales(hmTrackingMonitorDto)
							if(trackingMonitor.getEstadoTracking().getIdEstadoTracking() == Constante.EDO_TRACK_NO_CUMPLIDO
							    && faseSigDeGeneral) { //&& !antRechazo) {
								log4j.debug("<createTrackingPersonaMonitor>2 Se cambia IdEstadoTracking= EDO_TRACK_EN_CURSO");
								estadoTracking=new EstadoTracking();
								estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_EN_CURSO);								
								trackingMonitor.setEstadoTracking(estadoTracking);
								trackingMonitor.setOculto(false);
								faseSigDeGeneral=false;
							}
							
							log4j.debug("final_create trackingMonitor->  faseSigDeGeneral="+faseSigDeGeneral+ 
								" trackingMonitor_idEstadoTracking="+trackingMonitor.getEstadoTracking().getIdEstadoTracking());
							
							/*log4j.debug("final_create trackingMonitor-> idTrackingPostulante="+
									(trackingMonitor.getTrackingPostulante() == null ? null:
									trackingMonitor.getTrackingPostulante().getIdTrackingPostulante())+
									" trackingPostulante_idEstadoTracking="+
									(trackingMonitor.getTrackingPostulante() == null ? null:
									trackingMonitor.getTrackingPostulante().getEstadoTracking().getIdEstadoTracking()));*/							
							log4j.debug("final_create trackingMonitor->  idBitacoraTrack="+
											candidatoTrackingDto.getIdBitacoraTrack()+
											" trackingPostulante="+trackingMonitor.getTrackingPostulante());
							
							//Se persiste el monitor
							//bitacora tracking monitor
							btcTrackingDto=new BtcTrackingDto();
							btcTrackingMonDto= new BtcTrackingMonPosDto();							
							btcTrackingMonDto.setIdMonitor(monitor.getIdMonitor());
							btcTrackingMonDto.setIdTrackingPostulante(trackingMonitor.getTrackingPostulante() == null ?
												null:trackingMonitor.getTrackingPostulante().getIdTrackingPostulante());
							btcTrackingMonDto.setIdRelacionEmpresaPersona(trackingMonitor.
													getRelacionEmpresaPersona().getIdRelacionEmpresaPersona());
							btcTrackingMonDto.setIdEstadoTracking(trackingMonitor.getEstadoTracking().
																					getIdEstadoTracking());
							btcTrackingMonDto.setComentario(trackingMonitor.getComentario());
							btcTrackingMonDto.setFechaInicio(trackingMonitor.getFechaInicio());
							btcTrackingMonDto.setFechaFin(trackingMonitor.getFechaInicio());
							btcTrackingMonDto.setEnGrupo(trackingMonitor.isEnGrupo() ? 
															Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL);
							btcTrackingMonDto.setNotifEnviada(trackingMonitor.isNotificacionEnviada() ? 
															Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL);
							btcTrackingMonDto.setIdTrackingMonitor((long)trackingMonitorDao.create(trackingMonitor));
							
							btcTrackingDto.setBtcTrackingMonPosDto(btcTrackingMonDto);
							btcTrackingDto.setIdPosicion(Long.valueOf(trackingPostulanteDto.getIdPosicion()));
							btcTrackingDto.setIdRelacionEmpresaPersona(Long.valueOf(trackingPostulanteDto.
																			getIdRelacionEmpresaPersona()));
							btcTrackingDto.setIdTipoModuloBitacora(Constante.TIPO_MOD_BTC_TRACK_MON);
							btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_CREATE);
							btcTrackingDto.setPorSistema(candidatoTrackingDto.getIdBitacoraTrack() == null ?
																								false:true);
							btcTrackingDto.setIdBitacoraTrackRel(candidatoTrackingDto.getIdBitacoraTrack());
							
							resp=bitacoraTrackingService.create(btcTrackingDto);
							log4j.debug("<createAll> bitacora tracking monitor -> resp: "+ resp);
							lsBTMensajeDto=gson.fromJson(resp, new TypeToken<List<MensajeDto>>(){}.getType());
							log4j.debug("<createAll> bitacora tracking monitor -> lsBTMensajeDto: "+ 
										(lsBTMensajeDto != null ? lsBTMensajeDto.size():null));
							
							
							//bitacora tracking postulante							
							if(trackingMonitor.getTrackingPostulante() != null) {
								btcTrackingDto=new BtcTrackingDto();
								btcTrackingDto.setBtcTrackingMonPosDto(btcTrackingPosDto);
								btcTrackingDto.setIdPosicion(Long.valueOf(trackingPostulanteDto.getIdPosicion()));
								btcTrackingDto.setIdRelacionEmpresaPersona(Long.valueOf(trackingPostulanteDto.
																					getIdRelacionEmpresaPersona()));
								btcTrackingDto.setIdTipoModuloBitacora(Constante.TIPO_MOD_BTC_TRACK_POS);
								btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_CREATE);
								btcTrackingDto.setPorSistema(true);
								btcTrackingDto.setIdBitacoraTrackRel(Long.valueOf(lsBTMensajeDto.get(0).getValue()));
								resp=bitacoraTrackingService.create(btcTrackingDto);
								log4j.debug("<createAll> bitacora tracking postulante -> resp: "+ resp);
							}
						}
							
						//Se manda correo para que ingrese al sistema
						//Para POSIBLE_CANDIDATO_1
						if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_1)){
							
							//se modifica estatus
							candidato=candidatoDao.read(Long.parseLong(candidatoTrackingDto.getIdCandidato()));
						
							//Se pone estatusOperativo: invitado
							candidato.setEstatusOperativo(new EstatusOperativo(
												 Constante.ESTATUS_CANDIDATO_OPERATIVO_INVITADO,null));
							
							//se confirma a true
							candidato.setConfirmado(true);	
							
							//Se manda la notificación
							jsonObject = new JSONObject();
							jsonObject.put("idPivote", candidatoTrackingDto.getIdCandidato());						
							jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_1);
							jsonObject.put("nombrePosicion",trackingPostulanteDto.getNombrePosicion());
							
							//Se pone la notificación
							notificacionProgramadaService.create(new NotificacionProgramadaDto(
																(String)jsonObject.get("claveEvento"),
																jsonObject.toString(),null));
						
							//Ya que no importa la contestación del candidato, se pone estatusOperativo: en proceso
							candidato.setEstatusOperativo(new EstatusOperativo(
												 Constante.ESTATUS_CANDIDATO_OPERATIVO_EN_PROCESO,null));							
							
						//Para POSIBLE_CANDIDATO_2	
						}else if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_2)){
							
							//ESTATUS_CANDIDATO_OPERATIVO_INVITADO
							posibleCandidato=posibleCandidatoDao.read(Long.parseLong(
																candidatoTrackingDto.getIdPosibleCandidato()));													
							
							//Caso 1
							if(candidatoTrackingDto.getIdTipoPosibleCandidato().equals(
																Constante.POSIBLE_CANDIDATO_2_1.toString())){
								
								//Se verifica si es posible candidato
								respService=schedulerClassifiedDocService.verificarPosibleCandidato( 
													new SchedulerDto(trackingPostulanteDto.getIdEmpresaConf(), 
													candidatoTrackingDto.getIdPersona()));
																							
								log4j.debug("<createTrackingPersonaMonitor>  ES_POSIBLE_CANDIDATO_2 - CASO_1 -> respService="+
								respService);
								
								if(!respService.equals(Mensaje.SERVICE_MSG_OK_JSON)){
									trackingPostulanteDto.setMessages(UtilsTCE.getJsonMessageGson(
																			trackingPostulanteDto.getMessages(), 
														  					new MensajeDto("idPersona",
														  					candidatoTrackingDto.getIdPersona(),
														  					respService)));
								}
								
								//Se pone estatusOperativo a invitado
								posibleCandidato.setEstatusOperativo(new EstatusOperativo(
										 			Constante.ESTATUS_CANDIDATO_OPERATIVO_INVITADO,null));
								
								//Se manda el mismo que el precandidato1
								jsonObject = new JSONObject();
								jsonObject.put("idPivote", candidatoTrackingDto.getIdPosibleCandidato());						
								jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_2_1);
								jsonObject.put("nombrePosicion",trackingPostulanteDto.getNombrePosicion());
								
								//Se pone la notificación para el demonio
								notificacionProgramadaService.create(new NotificacionProgramadaDto(
																	(String)jsonObject.get("claveEvento"),
																	jsonObject.toString(),null));
								
								//Se pone estatusOperativo a invitado
								posibleCandidato.setEstatusOperativo(new EstatusOperativo(
										 			Constante.ESTATUS_CANDIDATO_OPERATIVO_EN_PROCESO,null));
								
							//Caso 2
							}else if(candidatoTrackingDto.getIdTipoPosibleCandidato().equals(
																Constante.POSIBLE_CANDIDATO_2_2.toString())){
								
								//Se pone estatusOperativo a invitado
								posibleCandidato.setEstatusOperativo(new EstatusOperativo(
										 			Constante.ESTATUS_CANDIDATO_OPERATIVO_INVITADO,null));
								//Se manda notificacion
								jsonObject = new JSONObject();
								jsonObject.put("idPivote", candidatoTrackingDto.getIdPosibleCandidato());						
								jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_2_2);
								jsonObject.put("nombrePosicion",trackingPostulanteDto.getNombrePosicion());
								
								//Se pone la notificación para el demonio
								notificacionProgramadaService.create(new NotificacionProgramadaDto(
																	(String)jsonObject.get("claveEvento"),
																	jsonObject.toString(),null));								
							}							
							
						}//Para POSIBLE_CANDIDATO_3
						else if(candidatoTrackingDto.getTipoPostulante().equals(Constante.ES_POSIBLE_CANDIDATO_3)){
							jsonObject = new JSONObject();
							jsonObject.put("idPivote", candidatoTrackingDto.getIdPosibleCandidato());						
							jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_3);
							jsonObject.put("nombrePosicion",trackingPostulanteDto.getNombrePosicion());
							jsonObject.put("password",candidatoTrackingDto.getPwsCad());
							
							log4j.debug("Se manda la Notification ");
							
							//Se pone la notificación
							notificacionProgramadaService.create(new NotificacionProgramadaDto(
																(String)jsonObject.get("claveEvento"),
													  			jsonObject.toString(),null));
							
							//ESTATUS_CANDIDATO_OPERATIVO_INVITADO
							posibleCandidato=posibleCandidatoDao.read(Long.parseLong(
																candidatoTrackingDto.getIdPosibleCandidato()));
							
							//Se pone estatusOperativo a invitado
							posibleCandidato.setEstatusOperativo(new EstatusOperativo(
									 			Constante.ESTATUS_CANDIDATO_OPERATIVO_INVITADO,null));
							
						}	
						log4j.debug("<createTrackingPersonaMonitor> NotificationDto se invoca al servicio jsonObject="+jsonObject);					
					}
				}
			}
		}		
		
		log4j.debug(" lsEdoTrackingPostulante="+lsEdoTrackingPostulante);

		
		//se modifican los estatus traking del postulante
		if(lsEdoTrackingPostulante.size() > 0) {
			
			itEdoTrackingPostulante=lsEdoTrackingPostulante.iterator();
			
			//se recorre
			while(itEdoTrackingPostulante.hasNext()) {
				
				//se modifica el edotrack
				trackingPostulanteDao.updateEdoTracking(itEdoTrackingPostulante.next(),
											Constante.EDO_TRACK_EN_CURSO.longValue());
			}	
		}
		
		
		log4j.debug(" salio del ciclo getMessages="+trackingPostulanteDto.getMessages() );
		
		//si no hay mensajes
		if(trackingPostulanteDto.getMessages() == null){			
			trackingPostulanteDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
		}
	}

	
	
	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto trackingPostulanteDto
	 * @param trackingPostulanteDto
	 * @param funcion es el tipo de evento
	 * @throws Exception
	 */
	private void filtros(TrackingPostulanteDto trackingPostulanteDto, int funcion) throws Exception{
		boolean error=false;
		if(trackingPostulanteDto != null){
			
			
			if(funcion == Constante.F_CREATE_ALL || 
				funcion == Constante.F_CREATE || 
				funcion == Constante.F_CONFIRM ){
				 if(trackingPostulanteDto.getIdPosicion() == null ){
					 error=true;
				 }	
				 
			if(!error && (funcion == Constante.F_CREATE_ALL || 
			   funcion == Constante.F_CONFIRM )){
				 
				 log4j.debug("<filtros> -> 3 error="+error);
				 //Se revisa si hay personas(monitores y candidatos)
				 if(trackingPostulanteDto.getMonitores() == null || 
					trackingPostulanteDto.getMonitores().size() == 0){
					 error=true;
				 }else{
					 
					 log4j.debug("<filtros> -> 4 error="+error);
					//Se revisa si hay almenos un monitor y almenos un candidato
					 Iterator<TrackingMonitorDto> itMonitor=trackingPostulanteDto.
							 								getMonitores().iterator();
					 while(itMonitor.hasNext()){
						 TrackingMonitorDto monitor=itMonitor.next();
						 //No hay monitor
						 if(monitor == null){
							 error=true;
							 break;							
						 }
						 
						 //No hay IdModeloRscPos
						 if(monitor.getIdModeloRscPos() == null){
							 error=true;
							 break;							
						 }
						 
						 //No hay IdPersona
						 if(monitor.getIdPersona() == null){
							 error=true;
							 break;							
						 }
						 
						 //No hay candidatos
						 if(monitor.getCandidatos() == null ||
							monitor.getCandidatos().size() == 0){
							 error=true;
							 break;	
						 }else{
							 //almenos un candidato o posible
							 itCandidatoDto=monitor.getCandidatos().iterator();
							 while(itCandidatoDto.hasNext()){
								 candidatoTrackingDto=itCandidatoDto.next();
								 
								 if(candidatoTrackingDto.getTipoPostulante() == null){
										 error=true;
										 break;	
									 }	
								 
								//No hay IdModeloRscPos
								 if(candidatoTrackingDto.getIdModeloRscPos() == null){
									 error=true;
									 break;							
								 }
								 
								 if(funcion == Constante.F_CREATE_ALL ){
									 log4j.debug("<filtros> -> getIdCandidato="+candidatoTrackingDto.getIdCandidato()+
											 " getTipoPostulante="+candidatoTrackingDto.getTipoPostulante()+
											 " getEmail="+candidatoTrackingDto.getEmail());
									 
									 if(candidatoTrackingDto.getConfirmado() != null &&
										(Integer.valueOf(candidatoTrackingDto.getConfirmado()).intValue()  != 0 &&
										 Integer.valueOf(candidatoTrackingDto.getConfirmado()).intValue()  != 1)){
										 error=true;
										 break;	
									 }									 
										
									 log4j.debug("<filtros> -> 5 error="+error);
									 //Para posible_candidato_1
									 if(candidatoTrackingDto.getTipoPostulante().
										equals(Constante.ES_POSIBLE_CANDIDATO_1)){
										 
										 if(candidatoTrackingDto.getIdCandidato() == null ||
											candidatoTrackingDto.getOrden() == null){
											 error=true;
											 break;	
										 }										 								
									 }
									 
									 log4j.debug("<filtros> -> confirmado="+candidatoTrackingDto.getConfirmado());
									 
									 log4j.debug("<filtros> -> 6 error="+error);
									 //para posible_candidato_2
									 if(candidatoTrackingDto.getTipoPostulante().
										 equals(Constante.ES_POSIBLE_CANDIDATO_2) && 
										 (candidatoTrackingDto.getIdPersona() == null )){
										 error=true;
										 break;	
									 }
									 log4j.debug("<filtros> -> 7 error="+error);
									 //para posible_candidato_3
									 if(candidatoTrackingDto.getTipoPostulante().
										 equals(Constante.ES_POSIBLE_CANDIDATO_3) && 
										 (candidatoTrackingDto.getEmail() == null || 
										 candidatoTrackingDto.getNombre() == null || 
										 candidatoTrackingDto.getApellidoPaterno() == null)){
										 error=true;
										 break;	
									 }
								 }
								 log4j.debug("<filtros> -> 10 error="+error);
								 if(funcion == Constante.F_CONFIRM ){
									 log4j.debug("<filtros> -> getIdPosibleCandidato="+candidatoTrackingDto.getIdPosibleCandidato()+
											 " getIdTipoPosibleCandidato="+candidatoTrackingDto.getIdTipoPosibleCandidato());
									 
									 //Para posible_candidato_1
									 if(candidatoTrackingDto.getTipoPostulante().
										equals(Constante.ES_POSIBLE_CANDIDATO_1)){
										 
										 if(candidatoTrackingDto.getIdCandidato() == null){
											 error=true;
											 break; 
										 }										 
									 }
									 
									 log4j.debug("<filtros> -> 8 error="+error);
									 //Para posible_candidato_2 y posible_candidato_3
									 if(!candidatoTrackingDto.getTipoPostulante().
										equals(Constante.ES_POSIBLE_CANDIDATO_1) && 
										candidatoTrackingDto.getIdPosibleCandidato() == null){
										 error=true;
										 break;
									 } 
								 } 
								 log4j.debug("<filtros> -> 9 error="+error);
							 }
						 }								 
						 if(error) break;
					}
				 }
				} 
			 }
			 log4j.debug("<filtros> -> 11 error="+error);
			
			 //para create
			 if(!error && funcion == Constante.F_CREATE ){
				 
				 if(trackingPostulanteDto.getIdModeloRscPosFase() == null ||
					trackingPostulanteDto.getIdEstadoTracking() == null ||
					trackingPostulanteDto.getIdRelacionEmpresaPersona() == null ||
					(trackingPostulanteDto.getIdCandidato() == null && 
					 trackingPostulanteDto.getIdPosibleCandidato() == null)){
					 error=true;
				 }
			 }
			 
			
			 //create, update y dataConf
			 if(!error && (trackingPostulanteDto.getIdEmpresaConf() == null || 
				trackingPostulanteDto.getIdPersona() == null)){
				 error=true;
			 }
			 log4j.debug("<filtros> error="+error);
			
		}else{
			error=true;
		}
		 if(error){
			 trackingPostulanteDto.setMessage(Mensaje.MSG_ERROR);
			 trackingPostulanteDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 trackingPostulanteDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
	}

	
	
	
}
