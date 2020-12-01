package net.tce.task.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.tce.admin.service.AdminService;
import net.tce.admin.service.BitacoraTrackingService;
import net.tce.admin.service.NotificacionProgramadaService;
import net.tce.dao.RecordatorioDao;
import net.tce.dao.TrackingMonitorDao;
import net.tce.dto.BtcRecdrioDto;
import net.tce.dto.BtcTrackingDto;
import net.tce.dto.BtcTrackingMonPosDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.NotificacionProgramadaDto;
import net.tce.dto.RecordatorioDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsNotification;

@Component("TSTSReminder")
public class ThreadSingleTaskSchedulerReminder {
	final Logger log4jSN = Logger.getLogger("SCHED_REMINDER");
	final Logger log4j = Logger.getLogger( this.getClass());
	
	private  @Value("${send.reminder.active}") boolean reminderActive;
	private  @Value("${send.reminder.initial}") int reminderInitial;
	private  @Value("${send.reminder.interval}") int reminderInterval;
	
	JSONObject jsonObject;
	List<RecordatorioDto> lsRecordatorioDto;
	Iterator<RecordatorioDto> itRecordatorioDto;
	RecordatorioDto recordatorioDto;
	TrackingMonitorDto postulanteDto;
	HashMap <Long, List<RecordatorioDto>> htRecordatorioPostDto;
	HashMap <Long, List<RecordatorioDto>> htRecordatorioMonDto;
	List<RecordatorioDto> lsRecordatorioPostDto;
	List<RecordatorioDto> lsRecordatorioMonDto;
	BtcRecdrioDto btcRecdrioDto;
	List<BtcRecdrioDto> lsBtcRecdrioDto;
	Long idRecordatorio;
	Long idRelacionEmpresaPersona;
	Long idPosicion;
	Long idTrackingMon;
	Long idTrackingPos;
	BtcTrackingDto btcTrackingDto;
	BtcTrackingMonPosDto btcTrackingMonPosDto;
	String resp;
	Iterator<Long> itKeyRecordatorio;
	
	 @Autowired
	 private ScheduledExecutorService taskScheduler;
	 
	 @Autowired
	 private AdminService adminService;
	 
	 @Autowired 
	 private NotificacionProgramadaService notificacionProgramadaService;
	 
	 @Autowired 
	 private BitacoraTrackingService bitacoraTrackingService;
	 
	 @Autowired 
	 private TrackingMonitorDao trackingMonitorDao;
	 
	 @Autowired
	 private RecordatorioDao recordatorioDao;
	 
	
	 
	 @PostConstruct
	 public void scheduleRunnable() {
		 log4jSN.info("init ScheduleRunnable -> TSTSReminder-> reminderActive="+reminderActive+
					" reminderInitial="+reminderInitial+
					" reminderInterval="+reminderInterval);
		 log4j.info("init ScheduleRunnable -> TSTSReminder-> reminderActive="+reminderActive+
					" reminderInitial="+reminderInitial+
					" reminderInterval="+reminderInterval);
		 
		 taskScheduler.scheduleWithFixedDelay(new Runnable() {
			 
			  public void run() {
				  //log4jSN.debug("Se despierta proceso Reminder");
				  
				  //Se habilita 
				  if(reminderActive) {
					  try {
						  htRecordatorioPostDto=new HashMap<>();
						  htRecordatorioMonDto=new HashMap<>();
						  
						  //se aplica una busqueda de los recordatorios para monitores
						  lsRecordatorioDto=recordatorioDao.getByDateMonitor(DateUtily.getToday());
												  
						  //se manda objetos notificacionProgramada
						  if(lsRecordatorioDto != null && lsRecordatorioDto.size() > 0) {							
							  
							  log4jSN.debug("//------------------------------------------------------//");
							  log4jSN.debug("Monitor -> lsRecordatorioDto:"+lsRecordatorioDto.size() +
									  " getToday()="+DateUtily.thisDateFormated(
										 DateUtily.getToday(), Constante.DATE_FORMAT_JAVA));
							  
							  itRecordatorioDto=lsRecordatorioDto.iterator();
							  
							  while(itRecordatorioDto.hasNext()) {
								 recordatorioDto= itRecordatorioDto.next();
								 idRecordatorio=Long.parseLong(recordatorioDto.getIdRecordatorio());
								 
								 log4jSN.debug("Se manda Recordatorio al monitor -> idPersona:"+recordatorioDto.getIdPersona()+
											" nombre="+recordatorioDto.getNombreMonitor()+
											" idTrackingPostulante="+recordatorioDto.getIdTrackingPostulante());
								 
								  log4jSN.debug("<getByDate> tm_getFechaInicio="+DateUtily.thisDateFormated(
										  recordatorioDto.getFechaInicio(), Constante.DATE_FORMAT_JAVA));
								  
								 
								 //fase monitor_postulado
								 if(recordatorioDto.getIdTrackingPostulante() != null) {
									 
									 //se obtiene el postulante
									 postulanteDto=trackingMonitorDao.getByPostulante(recordatorioDto.getIdTrackingPostulante());

									log4jSN.debug("del postulante: "+postulanteDto.getNombre()+ 
												  " getbEnGrupo="+postulanteDto.getbEnGrupo());
									
									//Se crea una notificacion
									jsonObject = new JSONObject();
									jsonObject.put("idPivote", ""+recordatorioDto.getIdPersona());						
									jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_AGENDA_FMP);
									jsonObject.put("nombrePosicion",recordatorioDto.getNombrePosicion());
									jsonObject.put("nombreFase",recordatorioDto.getNombreFase().toUpperCase());
									jsonObject.put("comentario","recordatorio de su agenda en el cual");
									jsonObject.put("horaInicial",DateUtily.thisDateFormated(recordatorioDto.getFechaInicio(),
																							Constante.HOUR_FORMAT1));
									jsonObject.put("diaInicial",DateUtily.thisDateFormated(recordatorioDto.getFechaInicio(),
																							Constante.DATE_FORMAT1));
									jsonObject.put("nombre",postulanteDto.getNombre().toUpperCase());
									jsonObject.put("comentario2",postulanteDto.getbEnGrupo().booleanValue() ? 
																							" , en Panel, ": " ");
		
									notificacionProgramadaService.create(new NotificacionProgramadaDto(
																			(String)jsonObject.get("claveEvento"),
																			jsonObject.toString(),null));
									
									/** bitacora recordatorio **/
									//existe
									if(htRecordatorioPostDto.containsKey(recordatorioDto.getIdTrackingPostulante())) {
										htRecordatorioPostDto.get(recordatorioDto.getIdTrackingPostulante()).
																						add(recordatorioDto);										
									}else {
										//no existe
										lsRecordatorioPostDto=new ArrayList<RecordatorioDto>();
										lsRecordatorioPostDto.add(recordatorioDto);
										htRecordatorioPostDto.put(recordatorioDto.getIdTrackingPostulante(),
																lsRecordatorioPostDto);
									}
									
								 }else {
									//fase monitor
									 
									//Se crea una notificacion
									jsonObject = new JSONObject();
									jsonObject.put("idPivote", ""+recordatorioDto.getIdPersona());						
									jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_AGENDA_FM);
									jsonObject.put("nombrePosicion",recordatorioDto.getNombrePosicion());
									jsonObject.put("nombreFase",recordatorioDto.getNombreFase().toUpperCase());
									jsonObject.put("horaInicial",DateUtily.thisDateFormated(recordatorioDto.getFechaInicio(),
																							Constante.HOUR_FORMAT1));
									jsonObject.put("diaInicial",DateUtily.thisDateFormated(recordatorioDto.getFechaInicio(),
																							Constante.DATE_FORMAT1));
									jsonObject.put("horaFinal",DateUtily.thisDateFormated(recordatorioDto.getFechaFinal(),
																								Constante.HOUR_FORMAT1));
									jsonObject.put("diaFinal",DateUtily.thisDateFormated(recordatorioDto.getFechaFinal(),
																								Constante.DATE_FORMAT1));
									
									//se envia
									notificacionProgramadaService.create(new NotificacionProgramadaDto(
																			(String)jsonObject.get("claveEvento"),
																			jsonObject.toString(),null));
																		
									/** bitacora recordatorio **/
									//existe
									if(htRecordatorioMonDto.containsKey(recordatorioDto.getIdTrackingMonitor())) {
										htRecordatorioMonDto.get(recordatorioDto.getIdTrackingMonitor()).
																						add(recordatorioDto);										
									}else {
										//no existe
										lsRecordatorioMonDto=new ArrayList<RecordatorioDto>();
										lsRecordatorioMonDto.add(recordatorioDto);
										htRecordatorioMonDto.put(recordatorioDto.getIdTrackingMonitor(),
																lsRecordatorioMonDto);
									}
								 }
								 
								//se modifica el estatus: seAplico a true
								recordatorioDao.update(idRecordatorio, true);									
								log4jSN.debug("se aplico el idRecordatorio:"+recordatorioDto.getIdRecordatorio());
								
							  }		 
						  }
				 
				  
					  //se aplica una busqueda de los recordatorios para postulantes
					  lsRecordatorioDto=recordatorioDao.getByDatePostulante(DateUtily.getToday());
					
					  //se manda objetos notificacionProgramada					  
					  if(lsRecordatorioDto != null && lsRecordatorioDto.size() > 0) {
						
						  log4jSN.debug("Postulante -> lsRecordatorioDto:"+lsRecordatorioDto.size() +
								  " getToday()="+DateUtily.thisDateFormated(
									 DateUtily.getToday(), Constante.DATE_FORMAT_JAVA));
						  
						  itRecordatorioDto=lsRecordatorioDto.iterator();
						  
						  //se manda correos
						  while(itRecordatorioDto.hasNext()) {
							 recordatorioDto= itRecordatorioDto.next();
							 
							 log4jSN.debug("Se manda Recordatorio al Postulante -> idPersona_postulante:"+
									 	recordatorioDto.getIdPersona()+
										" nombre_monitor="+recordatorioDto.getNombreMonitor()+
										" idTrackingPostulante="+recordatorioDto.getIdTrackingPostulante());
							 
							//Se crea una notificacion
								jsonObject = new JSONObject();
								jsonObject.put("idPivote", ""+recordatorioDto.getIdPersona());						
								jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_AGENDA_FMP);
								jsonObject.put("nombrePosicion",recordatorioDto.getNombrePosicion());
								jsonObject.put("nombreFase",recordatorioDto.getNombreFase().toUpperCase());
								jsonObject.put("comentario","aviso para recordarle que");
								jsonObject.put("comentario2",recordatorioDto.getEnGrupo().booleanValue() ? 
																						" , en Panel, ": " ");
								jsonObject.put("horaInicial",DateUtily.thisDateFormated(recordatorioDto.getFechaInicio(),
																						Constante.HOUR_FORMAT1));
								jsonObject.put("diaInicial",DateUtily.thisDateFormated(recordatorioDto.getFechaInicio(),
																						Constante.DATE_FORMAT1));
								jsonObject.put("nombre",recordatorioDto.getNombreMonitor().toUpperCase());
	
								notificacionProgramadaService.create(new NotificacionProgramadaDto(
																		(String)jsonObject.get("claveEvento"),
																		jsonObject.toString(),null));
							 
								//se modifica el estatus: seAplico a true
								recordatorioDao.update(Long.parseLong(recordatorioDto.getIdRecordatorio()), true);									
								log4jSN.debug("se aplico el idRecordatorio:"+recordatorioDto.getIdRecordatorio());
								
								/** bitacora recordatorio **/
								//existe
								if(htRecordatorioPostDto.containsKey(recordatorioDto.getIdTrackingPostulante())) {
									htRecordatorioPostDto.get(recordatorioDto.getIdTrackingPostulante()).
																					add(recordatorioDto);										
								}else {
									//no existe
									lsRecordatorioPostDto=new ArrayList<RecordatorioDto>();
									lsRecordatorioPostDto.add(recordatorioDto);
									htRecordatorioPostDto.put(recordatorioDto.getIdTrackingPostulante(),
															lsRecordatorioPostDto);
								}
						  }  
					  }
					  
					  /** bitacora recordatorio **/
					  lsBtcRecdrioDto=new ArrayList<BtcRecdrioDto>();
					/*  log4j.debug("<bitacoraUpdate> bitacoraTracking -> htRecordatorioMonDto:"+ htRecordatorioMonDto.size()+
									" htRecordatorioPostDto="+htRecordatorioPostDto.size());*/

					  //Para monitores
					  if(htRecordatorioMonDto.size() > 0) {
						  bitacoraUpdate(htRecordatorioMonDto, false);
					  }
					  
					  //Para postulantes
					  if(htRecordatorioPostDto.size() > 0) {
						  bitacoraUpdate(htRecordatorioPostDto, true);
					  }
					  
					 } catch (Exception e) {
						 e.printStackTrace();
			             log4jSN.error("ERROR AL EJECUTAR SEND_REMINDER_SCHEDULED -> "+e.toString());
			    			
			              //se envía correo a los admin
			              adminService.notificacionFatal(new MensajeDto(null,null,
			    											Mensaje.SERVICE_CODE_006,
			    											Mensaje.SERVICE_TYPE_FATAL,
			    											ExceptionUtils.getStackTrace(e).substring(0,
			    											Constante.MESSAGE_FATAL_LENGHT).concat(" ...")));
			            //Inicia un cierre ordenado
			    		taskScheduler.shutdown();
					}  
				  }	 
			  }
		 }, reminderInitial, reminderInterval, TimeUnit.SECONDS);		 
	 }	
	 
	/**
	 * Actualiza la bitacora correspondiente a los recordatorios
	 * @param htRecordatorioDto, contiene objetos de recordatorio
	 * @param isTrackingPostulante, indica si es de TrackingPostulante el map
	 */
	 void bitacoraUpdate(HashMap <Long, List<RecordatorioDto>> htRecordatorioDto, boolean isTrackingPostulante) {
		 
		// log4j.debug("<bitacoraUpdate> isTrackingPostulante: "+ isTrackingPostulante);
		
		itKeyRecordatorio = htRecordatorioDto.keySet().iterator();
		while(itKeyRecordatorio.hasNext()){	
			idRelacionEmpresaPersona=null;
			idPosicion=null;
			idTrackingMon=null;
			idTrackingPos=null;
			
			lsRecordatorioDto=htRecordatorioDto.get(itKeyRecordatorio.next());
			
			itRecordatorioDto=lsRecordatorioDto.iterator();
			while(itRecordatorioDto.hasNext()) {
				recordatorioDto=itRecordatorioDto.next();
				
				if(idRelacionEmpresaPersona == null) {
				  idRelacionEmpresaPersona=recordatorioDto.getIdRelacionEmpresaPersona();
				}
				if(idPosicion == null) {
					idPosicion=recordatorioDto.getIdPosicion();
				}
				if(idTrackingMon == null) {
					idTrackingMon=recordatorioDto.getIdTrackingMonitor();
				}
				if(idTrackingPos == null) {
					idTrackingPos=recordatorioDto.getIdTrackingPostulante();
				}				
							  
				/** bitacora recordatorio **/
				btcRecdrioDto=new BtcRecdrioDto();
				btcRecdrioDto.setIdRecordatorio(Long.valueOf(recordatorioDto.getIdRecordatorio()));
				btcRecdrioDto.setSeAplico(Constante.BOL_TRUE_VAL);
				lsBtcRecdrioDto.add(btcRecdrioDto);
			}
			/*log4j.debug("<bitacoraUpdate>  idTrackingMon: "+ idTrackingMon+
						" idTrackingPos="+idTrackingPos+
						" lsBtcRecdrioDto="+lsBtcRecdrioDto.size()+
						" idPosicion="+idPosicion+
						" idRelacionEmpresaPersona="+idRelacionEmpresaPersona);*/
			
			btcTrackingDto=new BtcTrackingDto(); 
		  	btcTrackingMonPosDto=new BtcTrackingMonPosDto();
		  	btcTrackingMonPosDto.setIdTrackingPostulante(idTrackingPos);
		  	btcTrackingMonPosDto.setIdTrackingMonitor(idTrackingMon);
		  	btcTrackingMonPosDto.setLsBtcRecdrioDto(lsBtcRecdrioDto);
		  	btcTrackingDto.setBtcTrackingMonPosDto(btcTrackingMonPosDto);
			btcTrackingDto.setIdPosicion(idPosicion);
			btcTrackingDto.setIdRelacionEmpresaPersona(idRelacionEmpresaPersona);
			btcTrackingDto.setIdTipoModuloBitacora(isTrackingPostulante ? 
													Constante.TIPO_MOD_BTC_TRACK_POS_RCTRIO:
													Constante.TIPO_MOD_BTC_TRACK_MON_RCTRIO);
			btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_UPDATE);
			btcTrackingDto.setPorSistema(true);
			btcTrackingDto.setObservacion("Evento creado por el proceso automático ThreadSingleTaskSchedulerReminder");
			
			resp=bitacoraTrackingService.create(btcTrackingDto);
			//log4j.debug("<bitacoraUpdate>  resp: "+ resp);
			lsBtcRecdrioDto.clear();
		} 
	 }
	 
}
