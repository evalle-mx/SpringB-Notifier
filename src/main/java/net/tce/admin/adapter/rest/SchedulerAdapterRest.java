package net.tce.admin.adapter.rest;

import net.tce.dto.SchedulerDto;
import net.tce.task.service.SchedulerClassifiedDocService;
import net.tce.task.service.SearchCandidatesService;
import net.tce.util.Constante;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * @author Osy
 *
 */
@Controller
@RequestMapping(Constante.URI_TASK)
public class SchedulerAdapterRest extends ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());	
	
	Object object;
	
	@Autowired
	SchedulerClassifiedDocService schedulerClassifiedDocService;

	@Autowired
	SearchCandidatesService searchCandidatesService;
	
	/**	 
	 * Controlador expuesto que ejecuta el servicio de sincronización de documentos
	 * @param json, mensaje JSON  
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_TASK_SYNC_DOCS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String synchronizeDocs(@RequestBody String json) throws Exception {
		log4j.debug("<SchedulerAdapterRest> synchronizeClassificationDocs.  json :" + json);
		object=schedulerClassifiedDocService.synchronizeDocs(gson.fromJson(json, SchedulerDto.class));
		return ((object instanceof String) ? (String)object:gson.toJson(object));
		
	  }	
	


	/**
	 * Controlador expuesto que ejecuta el servicio de re-modelacion de documentos 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws Exception 
	 */
	@RequestMapping(value=Constante.URI_TASK_REMODEL, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String runReModel(@RequestBody String json) throws Exception {
		log4j.debug("<SchedulerAdapterRest> runReModel.  json :" + json);
		object=schedulerClassifiedDocService.runReModel(gson.fromJson(json, SchedulerDto.class));
		return ((object instanceof String) ? (String)object:gson.toJson(object));
	  }	
	
	
	/**
	 * Controlador expuesto que ejecuta el servicio de re-modelacion de documentos 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_TASK_RECLASSIFICATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String runReClassification(@RequestBody String json) throws Exception {
		log4j.debug("<SchedulerAdapterRest> runReClassification.  json :" + json);
		object=schedulerClassifiedDocService.runReClassification(gson.fromJson(json, SchedulerDto.class));
		return ((object instanceof String) ? (String)object:gson.toJson(object));
	  }	

	/**
	 * Controlador expuesto que ejecuta el servicio de búsqueda de candidatos
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_TASK_SEARCHCANDIDATES, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String runSearchCandidates(@RequestBody String json) throws Exception {
		log4j.debug("<SchedulerAdapterRest> runSearchCandidates.  json :" + json);
		return searchCandidatesService.searchCandidates(gson.fromJson(json, SchedulerDto.class));
	  }	
	
	/**
	 * Controlador expuesto que ejecuta el servicio de reload core solr
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_TASK_RELOAD_CORE_SOLR, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String runReloadCoreSolr(@RequestBody String json) throws Exception {
		log4j.debug("<SchedulerAdapterRest> runReloadCoreSolr.  json :" + json);
		object=schedulerClassifiedDocService.runReloadCoreSolr(gson.fromJson(json, SchedulerDto.class));
		return ((object instanceof String) ? (String)object:gson.toJson(object));
	  }		
}



