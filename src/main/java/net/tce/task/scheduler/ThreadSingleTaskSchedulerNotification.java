package net.tce.task.scheduler;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import net.tce.admin.service.AdminService;
import net.tce.admin.service.NotificationService;
import net.tce.dao.NotificacionProgramadaDao;
import net.tce.dto.MensajeDto;
import net.tce.dto.NotificationDto;
import net.tce.model.NotificacionProgramada;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Component("TSTSNotification")
public class ThreadSingleTaskSchedulerNotification {
	static final Logger log4jSN = Logger.getLogger("SCHED_NOTIFY");
	final Logger log4j = Logger.getLogger( this.getClass());

	private  @Value("${send.notification.active}") boolean notificationActive;	
	private  @Value("${send.notification.initial}") int notificationInitial;
	private  @Value("${send.notification.interval}") int notificationInterval;
	private  @Value("${send.notification.random.min}") int notificationRandomMin;
	private  @Value("${send.notification.random.max}") int notificationRandomMax;
	

	 @Autowired
	 private ScheduledExecutorService taskScheduler;
	 
	 @Autowired
	 private AdminService adminService;
	 
	 @Autowired
	 private NotificationService notificationService;
	 
	 @Autowired
	 private NotificacionProgramadaDao notificacionProgramadaDao;
	 
	 @Inject
	 private Gson gson;
	 
	 
	 @PostConstruct
	 public void scheduleRunnable() {
		 log4j.info("init ScheduleRunnable -> TSTSNotification -> notificationActive="+notificationActive+
				    " notificationInitial="+notificationInitial+
				    " notificationInterval="+notificationInterval+
					" notificationRandomMin="+notificationRandomMin+
					" notificationRandomMax="+notificationRandomMax);;
		 log4jSN.info("init scheduleRunnable() -> notificationActive="+notificationActive+
				    " notificationInitial="+notificationInitial+
				    " notificationInterval="+notificationInterval+
					" notificationRandomMin="+notificationRandomMin+
					" notificationRandomMax="+notificationRandomMax);
		 
		 //Se habilita el Send Notification
		   if(notificationActive) {
			 taskScheduler.scheduleWithFixedDelay(new Runnable() {
				 	int delay=0;
				 	String resp;
			    	List<NotificacionProgramada> lsNotificacionProgramada;
			    	Iterator<NotificacionProgramada> itNotificacionProgramada;
			    	NotificacionProgramada notificacionProgramada;
			    	
			        public void run() {
//			        	log4jSN.debug("Se despierta proceso sendNotifications");
			        	
			        	try {
			        	
				        	lsNotificacionProgramada=notificacionProgramadaDao.getModel(false,true);
				        	log4jSN.debug("lsNotificacionProgramada="+
									(lsNotificacionProgramada == null ? null:lsNotificacionProgramada.size()));
						
				        	//hay mensajes
							if(lsNotificacionProgramada != null &&
							   lsNotificacionProgramada.size() > 0){
								log4jSN.debug("Existen " + lsNotificacionProgramada.size() + " notificaciones que enviar " );
								log4j.debug("Existen " + lsNotificacionProgramada.size() + " notificaciones por enviar " );
								itNotificacionProgramada=lsNotificacionProgramada.iterator();
								while(itNotificacionProgramada.hasNext()){
									notificacionProgramada=itNotificacionProgramada.next();
									
									//Se obtiene un numero aleatotio para el delay de envío
									delay=UtilsTCE.getRandomNumberInRange(notificationRandomMin,
			        											 		 notificationRandomMax);
									log4jSN.debug("delay="+delay+" idNotificacionProgramada="+
			        								notificacionProgramada.getIdNotificacionProgramada());
									
									//Se ejecuta el delay
									Thread.sleep(delay * 1000);
									
					                log4jSN.info("Se procesa notificacion: "+notificacionProgramada.getJson());
					                log4j.debug("Se procesa notificacion: "+notificacionProgramada.getJson());
									
									//se crea un nuevo envio de notificacion
									resp=(String)notificationService.create(gson.fromJson(
											notificacionProgramada.getJson(), NotificationDto.class));
																
									//si el proceso de envio y almacenamiento fue exitoso [] se actualiza el dato en notificacion programada
									if(resp.equals(Mensaje.SERVICE_MSG_OK_JSON)){
										//Se cambia el estatus enviada
										notificacionProgramadaDao.updateEnviado(true, 
												notificacionProgramada.getIdNotificacionProgramada());	
									}	
								}
							  }
				            } catch (Exception e) {
				                e.printStackTrace();
				                log4jSN.error("ERROR AL EJECUTAR SEND_NOTIFICATION_SCHEDULED: "+e.toString(), e);
				                log4jSN.error("Se detiene el proceso para evitar saturacion...");
				                //se envía correo a los admin
				                adminService.notificacionFatal(new MensajeDto(null,null,
				    											Mensaje.SERVICE_CODE_006,
				    											Mensaje.SERVICE_TYPE_FATAL,
				    											ExceptionUtils.getStackTrace(e).substring(0,
				    											Constante.MESSAGE_FATAL_LENGHT).concat(" ...")));
				               //Se detiene el proceso para evitar saturacion
				    			taskScheduler.shutdown();
				            }     		
//			        	log4jSN.debug("termina  ");	          
			        }
			  }, notificationInitial, notificationInterval, TimeUnit.SECONDS);	
		   }	 
	 }
	 
	 
	 
}
