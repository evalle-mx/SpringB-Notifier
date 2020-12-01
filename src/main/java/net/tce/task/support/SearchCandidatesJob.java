package net.tce.task.support;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import net.tce.admin.service.AdminService;
import net.tce.dto.MensajeDto;
import net.tce.dto.SchedulerDto;
import net.tce.task.service.SearchCandidatesService;
import net.tce.util.Constante;
import net.tce.util.Mensaje;

/*
 * @DisallowConcurrentExecution
 */
public class SearchCandidatesJob extends QuartzJobBean {
	
	static final Logger log4j = Logger.getLogger("QUARTZ");
	
	String resp;
	
	private SearchCandidatesService searchCandidatesService;
	private AdminService adminServiceSC;
	

	public void setSearchCandidatesService(
			SearchCandidatesService searchCandidatesService) {
		this.searchCandidatesService = searchCandidatesService;
	}
	

	public void setAdminServiceSC(AdminService adminServiceSC) {
		this.adminServiceSC = adminServiceSC;
	}

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log4j.info("%% SE EJECUTA EL QUARTZ -> SEARCH_CANDIDATE_JOB adminServiceSC:"+adminServiceSC+
				" searchCandidatesService:"+searchCandidatesService);
		try {
			
			resp=searchCandidatesService.searchCandidates(new SchedulerDto(Constante.ID_EMPRESA_CONF_DEFAULT));
			//log4j.debug(" SearchCandidatesJob() ... resp="+resp);
			//se aplica el proceso 
			if(resp != null && resp.indexOf("code") != -1){
				//Hay error
				log4j.error("ERROR AL EJECUTAR SEARCH_CANDIDATE_JOB -> "+resp);
			}		
		} catch (Exception e) {			
			String error=ExceptionUtils.getStackTrace(e);
			log4j.error("ERROR AL EJECUTAR SEARCH_CANDIDATE_JOB -> "+error);
			e.printStackTrace();
			//se envía correo a los admin
			adminServiceSC.notificacionFatal(new MensajeDto(null,null,
											Mensaje.SERVICE_CODE_006,
											Mensaje.SERVICE_TYPE_FATAL,
											error));
		}

	}
}