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

public class ReminderConfirmInscripJob extends QuartzJobBean {
	static final Logger log4jQz = Logger.getLogger("QUARTZ");
	String resp;
	SchedulerDto schedulerDto;

	private AdminService adminServiceCI;
	
	private ReminderService reminderServiceCI;
	
	public void setReminderServiceCI(ReminderService reminderServiceCI) {
		this.reminderServiceCI = reminderServiceCI;
	}
	
	public void setAdminServiceCI(AdminService adminServiceCI) {
		this.adminServiceCI = adminServiceCI;
	}
	
	/**
	 * 
	 */
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log4jQz.info("<executeInternal> SE EJECUTA EL QUARTZ para activar DEMONIO de Confirmar Inscripcion [REMINER_CONFIRM_INSCRIP_JOB] adminServiceCI:"+adminServiceCI+
				" reminderServiceCI:"+reminderServiceCI);
		schedulerDto=new SchedulerDto();
		//tipo de mensaje de Confirmar Inscripcion
		try {
			reminderServiceCI.sendReminder(new SchedulerDto(Constante.TYPE_REMINDER_CONFIRM_INSCRIP));
		} catch (Exception e) {
			e.printStackTrace();
			log4jQz.fatal("<executeInternal> Excepcion al iniciar el QUARTZ: "+e.getLocalizedMessage(), e);
			log4jQz.debug("<executeInternal> Se envía correo a los administradores");
			adminServiceCI.notificacionFatal(new MensajeDto(null,null,
											Mensaje.SERVICE_CODE_006,
											Mensaje.SERVICE_TYPE_FATAL,
											e.getMessage()));
		}
		
	}

}
