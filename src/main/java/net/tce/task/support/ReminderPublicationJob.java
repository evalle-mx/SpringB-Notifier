package net.tce.task.support;

import net.tce.admin.service.AdminService;
import net.tce.dto.MensajeDto;
import net.tce.dto.SchedulerDto;
import net.tce.task.service.ReminderService;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ReminderPublicationJob extends QuartzJobBean {
	static final Logger log4jQz = Logger.getLogger("QUARTZ");
	String resp;
	SchedulerDto schedulerDto;
	
	private AdminService adminServicePub;
	
	private ReminderService reminderServicePub;
	
	public void setReminderServicePub(ReminderService reminderService) {
		this.reminderServicePub = reminderService;
	}
	
	public void setAdminServicePub(AdminService adminServicePub) {
		this.adminServicePub = adminServicePub;
	}
	
	/**
	 * 
	 */
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log4jQz.info("<executeInternal> SE EJECUTA EL QUARTZ para activar DEMONIO de Recordatorio Publicacion [REMINER_PUBLICATION_JOB] adminService:"+adminServicePub+
				" reminderServicePub:"+reminderServicePub);
		schedulerDto=new SchedulerDto();
		//tipo de mensaje de Confirmar Inscripcion
		try {
			reminderServicePub.sendReminder(new SchedulerDto(Constante.TYPE_REMINDER_PUBLICATION));
		} catch (Exception e) {
			e.printStackTrace();
			log4jQz.fatal("<executeInternal> Excepcion al iniciar el QUARTZ: "+e.getLocalizedMessage(), e);
			log4jQz.debug("<executeInternal> Se envía correo a los administradores");
			adminServicePub.notificacionFatal(new MensajeDto(null,null,
											Mensaje.SERVICE_CODE_006,
											Mensaje.SERVICE_TYPE_FATAL,
											e.getMessage()));
		}
		
	}

}
