package net.tce.admin.adapter.rest;

import net.tce.admin.service.CurriculumService;
import net.tce.dto.CurriculumDto;
import net.tce.dto.MasivoDto;
import net.tce.util.Constante;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Goyo
 *
 */
@Controller
@RequestMapping(Constante.URI_CURRICULUM)
public class CurriculumAdapterRest extends ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());	
	
	@Autowired
	CurriculumService curriculumService;
	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_CREATECOMPLETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createfull(@RequestBody String json)  throws Exception  {
		Object object = curriculumService.createFull(gson.fromJson(json, CurriculumDto.class));
		return (object instanceof String) ? (String)object: gson.toJson(object);
	  }
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=Constante.URI_CREATEMASIVE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createMasive(@RequestBody String json)  throws Exception  {
		return curriculumService.createMasive(gson.fromJson(json, MasivoDto.class));
	  }
		
}
