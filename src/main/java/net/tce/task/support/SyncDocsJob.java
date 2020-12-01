package net.tce.task.support;

import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.google.gson.Gson;

import net.tce.admin.service.AdminService;
import net.tce.dto.MensajeDto;
import net.tce.dto.SchedulerDto;
import net.tce.task.service.SchedulerClassifiedDocService;
import net.tce.util.Constante;
import net.tce.util.Mensaje;

/*
 * @DisallowConcurrentExecution
 */
public class SyncDocsJob extends QuartzJobBean {
	static final Logger log4j = Logger.getLogger("QUARTZ");
	String resp;
	
	@Inject
	Gson gson;
	
	private SchedulerClassifiedDocService schedulerClassifiedDocService;	
	private AdminService adminService;


	public void setSchedulerClassifiedDocService(
			SchedulerClassifiedDocService schedulerClassifiedDocService) {
		this.schedulerClassifiedDocService = schedulerClassifiedDocService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	/**
	 * Ejecutar JOB
	 * REVISAR BIEN ESTE JOB
	 */
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log4j.info("%% SE EJECUTA TASK: SYNC_DOCS_SOLR_JOB adminService:"+adminService+
				" schedulerClassifiedDocService:"+schedulerClassifiedDocService);	
		/*try {
			//synchronizeSolrDocs
			
			Object object=schedulerClassifiedDocService.synchronizeSolrDocs(new SchedulerDto(
																Constante.ID_EMPRESA_CONF_DUMMY,
																Constante.ID_PERSONA_DUMMY));	
			resp=((object instanceof String) ? (String)object:gson.toJson(object));
			
			//se aplica el proceso 
			if(resp != null && resp.indexOf("code") != -1){
				//Hay error
				log4j.error("ERROR AL EJECUTAR SYNC_DOCS_SOLR_JOB -> "+resp);
			}else{
				log4j.info("%% TERMINA TASK: SYNC_DOCS_SOLR_JOB -> Date:"+resp);
			}
			
			
			//synchronizeClassificationDocs
			log4j.info("%% SE EJECUTA TASK: SYNC_DOCS_CONFIR_JOB ");	
			object=schedulerClassifiedDocService.synchronizeClassificationDocs(new SchedulerDto(
																Constante.ID_EMPRESA_CONF_DUMMY,
																Constante.ID_PERSONA_DUMMY));	
			resp=((object instanceof String) ? (String)object:gson.toJson(object));
			
			//se aplica el proceso 
			if(resp != null && resp.indexOf("code") != -1){
				//Hay error
				log4j.error("ERROR AL EJECUTAR SYNC_DOCS_CONFIR_JOB -> "+resp);
			}else{
				log4j.info("%% TERMINA TASK: SYNC_DOCS_CONFIR_JOB -> Date:"+resp);
			}
			
		} catch (Exception e) {
			log4j.error("ERROR AL EJECUTAR SYNC_DOCS_JOB -> "+e.toString());
			e.printStackTrace();
			//se envía correo a los admin
			adminService.notificacionFatal(new MensajeDto(null,null,
											Mensaje.SERVICE_CODE_006,
											Mensaje.SERVICE_TYPE_FATAL,
											e.getMessage()));
		}*/

	}
}