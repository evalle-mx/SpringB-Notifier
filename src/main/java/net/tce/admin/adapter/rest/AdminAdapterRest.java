package net.tce.admin.adapter.rest;

import net.tce.admin.service.AdminService;
import net.tce.admin.service.CurriculumService;
import net.tce.dto.ControlProcesoDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.WorkExperienceDto;
import net.tce.util.Constante;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(Constante.URI_ADMIN)
public class AdminAdapterRest extends ErrorMessageAdapterRest {

	Logger log4j = Logger.getLogger( this.getClass());	

	@Autowired
	AdminService adminService;
	
	@Autowired
	CurriculumService curriculumService;
	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion lastDateSyncDocs
	 * @param json
	 * @return  
	 */
	@RequestMapping(value=Constante.URI_LASTDATE_SYNC_DOCS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String lastDateSyncDocs(@RequestBody String json)  {
		log4j.debug("lastDateSyncDocs ->  json"+json);
		Object object=adminService.lastDateFinalSyncDocs(gson.fromJson(json, ControlProcesoDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  }
	
	/**
	 * Controlador expuesto que ejecuta la funcion lastDateFinalRemodel
	 * @param json
	 * @return  
	 */
	@RequestMapping(value=Constante.URI_LASTDATE_REMODEL, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String lastDateFinalRemodel(@RequestBody String json)  {
		Object object=adminService.lastDateFinalRemodel(gson.fromJson(json, ControlProcesoDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  }
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion lastDateFinalReloadCoreSolr
	 * @param json
	 * @return  
	 */
	@RequestMapping(value=Constante.URI_LASTDATE_RELOAD_CORE_SOLR, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String lastDateFinalReloadCoreSolr(@RequestBody String json)  {
		Object object=adminService.lastDateFinalReloadCoreSolr(gson.fromJson(json, ControlProcesoDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  }
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion lastDateFinalReclassDocs 
	 * @param json
	 * @return  
	 */
	@RequestMapping(value=Constante.URI_LASTDATE_RECLASS_DOCS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String lastDateFinalReclassDocs(@RequestBody String json)  {
		Object object=adminService.lastDateFinalReclassDocs(gson.fromJson(json, ControlProcesoDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  }
	
}
