package net.tce.config;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.tce.task.support.ReminderConfirmInscripJob;
import net.tce.task.support.ReminderPublicationJob;
import net.tce.task.support.SearchCandidatesJob;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import org.apache.log4j.Logger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * 
 * @author goyo
 *
 */
@Configuration
public class QuartzConfiguration {
	Logger log4j = Logger.getLogger( this.getClass());
	
	//// sincronización de Documentos
	/*@Value("${trigger.sync.docs.inidate}")
	private String syncDocsIniDate;
	
	@Value("${trigger.sync.docs.start}")
	private boolean syncDocsStart;
	
	@Value("${trigger.sync.docs.interval}")
	private int syncDocsInterval;*/
	
	
	///// Busqueda de Candidatos
	@Value("${trigger.search.candidates.inidate}")
	private String searchCandidatesIniDate;
	
	@Value("${trigger.search.candidates.start}")
	private boolean searchCandidatesStart;
	
	@Value("${trigger.search.candidates.interval}")
	private int searchCandidatesInterval;
	
	
	//// Confirmación de Inscripción	
	@Value("${trigger.send.reminder.confirm.inscrip.inidate}")
	private String sendReminderConfirmInscripIniDate;
	
	@Value("${trigger.send.reminder.confirm.inscrip.start}")
	private boolean sendReminderConfirmInscripStart;
	
	@Value("${trigger.send.reminder.confirm.inscrip.interval}")
	private int sendReminderConfirmInscripInterval;

	
	//// Notificación para que publiquen su cv	
	@Value("${trigger.send.reminder.publication.inidate}")
	private String sendReminderPublicationDateInidate;
	
	@Value("${trigger.send.reminder.publication.start}")
	private boolean sendReminderPublicationStart;
	
	@Value("${trigger.send.reminder.publication.interval}")
	private int sendReminderPublicationInterval;
	
	

	@Autowired
	private ApplicationContext appContext;
	
	
	/**
	 * Se crea el bean del job correspondiente al servicio searchCandidates
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean searchCandidatesJob(){
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(SearchCandidatesJob.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("searchCandidatesService", appContext.getBean("searchCandidatesService") );
		map.put("adminServiceSC", appContext.getBean("adminService") );
		factory.setJobDataAsMap(map);
		factory.setName("searchCandidatesJob");
		factory.setDurability(true);
		return factory;
	}
	
//	/**
//	 * Se crea el bean del job correspondiente al servicio de sincronización de documentos
//	 * @return
//	 */
//	@Bean
//	public JobDetailFactoryBean syncDocsJob(){
//		JobDetailFactoryBean factory = new JobDetailFactoryBean();
//		factory.setJobClass(SyncDocsJob.class);
//		Map<String,Object> map = new HashMap<String,Object>();
//		//map.put("schedulerClassifiedDocService", appContext.getBean("schedulerClassifiedDocService") );
//		map.put("reclassifyDocService", appContext.getBean("reclassifyDocService") );
//		map.put("adminService", appContext.getBean("adminService") );
//		factory.setJobDataAsMap(map);
//		factory.setName("syncDocsJob");
//		factory.setDurability(true);
//		return factory;
//	}
	
	
	/**
	 * Se crea el bean del job correspondiente al servicio reminConfirmInscripJob
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean reminderConfirmInscripJob(){
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(ReminderConfirmInscripJob.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("reminderServiceCI", appContext.getBean("reminderService") );
		map.put("adminServiceCI", appContext.getBean("adminService") );
		factory.setJobDataAsMap(map);
		factory.setName("reminderConfirmInscripJob");
		factory.setDurability(true);
		return factory;
	}
	
	
	/**
	 * Se crea el bean del job correspondiente al servicio reminderConfirmInscripJob
	 * @return
	 */
	@Bean
	public JobDetailFactoryBean reminderPublicationJob(){
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(ReminderPublicationJob.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("reminderServicePub", appContext.getBean("reminderService") );
		map.put("adminServicePub", appContext.getBean("adminService") );
		factory.setJobDataAsMap(map);
		factory.setName("reminderPublicationJob");
		factory.setDurability(true);
		return factory;
	}
	
	
//	/**
//	 * Se configura el disparador de syncDocsJob
//	 * @return
//	 * @throws ParseException 
//	 */
	/*@Bean
	public SimpleTriggerFactoryBean syncDocsTaskTrigger() throws ParseException{
		SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
		stFactory.setJobDetail(syncDocsJob().getObject());
		stFactory.setRepeatInterval(syncDocsInterval);
		stFactory.setStartTime(DateUtily.string2Date(syncDocsIniDate,
				 									 Constante.DATE_FORMAT_JAVA));
		log4j.info("%%% QUARTZ: syncDocsJob -> inicio:"+syncDocsIniDate+
				" activo:"+syncDocsStart+" %%%");

		return stFactory;
	}*/
	
	/**
	 * Se configura el disparador de searchCandidatesJob
	 * @return
	 * @throws ParseException 
	 */
	@Bean
	public SimpleTriggerFactoryBean searchCandidatesTaskTrigger() throws ParseException{
		SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
		stFactory.setJobDetail(searchCandidatesJob().getObject());
		stFactory.setRepeatInterval(searchCandidatesInterval);
		stFactory.setStartTime(DateUtily.string2Date(searchCandidatesIniDate,
				 									Constante.DATE_FORMAT_JAVA));
		log4j.info("<searchCandidatesTaskTrigger> QUARTZ: searchCandidatesJob -> inicio:"+searchCandidatesIniDate+
				" activo:"+searchCandidatesStart+" %%%");
		return stFactory;
	}
	
	/**
	 * Se configura el disparador de reminConfirmInscripJob
	 * @return
	 * @throws ParseException 
	 */
	@Bean
	public SimpleTriggerFactoryBean reminConfirmInscripTrigger() throws ParseException{
		SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
		stFactory.setJobDetail(reminderConfirmInscripJob().getObject());
		//stFactory.setStartDelay(sendReminderConfirmInscripDelay);
		stFactory.setRepeatInterval(sendReminderConfirmInscripInterval);
		stFactory.setStartTime(DateUtily.string2Date(sendReminderConfirmInscripIniDate,
													 Constante.DATE_FORMAT_JAVA));
		log4j.info("<reminConfirmInscripTrigger> QUARTZ: reminConfirmInscripJob -> inicio:"+sendReminderConfirmInscripIniDate+
				" activo:"+sendReminderConfirmInscripStart+" %%%");
		return stFactory;
	}
	
	/**
	 * Se configura el disparador de reminderConfirmInscripJob
	 * @return
	 * @throws ParseException 
	 */
	@Bean
	public SimpleTriggerFactoryBean reminPublicationTrigger() throws ParseException{
		SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
		stFactory.setJobDetail(reminderPublicationJob().getObject());
		stFactory.setRepeatInterval(sendReminderPublicationInterval);
		stFactory.setStartTime(DateUtily.string2Date(sendReminderPublicationDateInidate,
													 Constante.DATE_FORMAT_JAVA));
		log4j.info("<reminPublicationTrigger> QUARTZ: reminPublicationJob  -> inicio:"+sendReminderPublicationDateInidate+
				" activo:"+sendReminderPublicationStart+" %%%");
		return stFactory;
	}
	
	
	/**
	 * Metodo PRINCIPAL para inicializar los demonios (QUARTZ)
	 * @return
	 * @throws ParseException 
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws ParseException {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		List<Trigger> lsTrigger =new ArrayList<Trigger>();

		//se habilita trigger de searchCandidatesJob
		if(searchCandidatesStart){
			lsTrigger.add(searchCandidatesTaskTrigger().getObject());
		}
		
		//se habilita trigger de syncDocsJob
		/*if(syncDocsStart){
			lsTrigger.add(syncDocsTaskTrigger().getObject());
		}*/
		
		//se habilita trigger de reminConfirmInscripJob
		if(sendReminderConfirmInscripStart){
			lsTrigger.add(reminConfirmInscripTrigger().getObject());
		}
		
		//se habilita trigger de reminPublicationJob
		if(sendReminderPublicationStart){
			lsTrigger.add(reminPublicationTrigger().getObject());
		}
		
		//Si se activo al menos un Trigger
		if(lsTrigger.size() > 0){
			scheduler.setTriggers(lsTrigger.toArray(new Trigger[lsTrigger.size()]));
		}
		return scheduler;
	}
}
