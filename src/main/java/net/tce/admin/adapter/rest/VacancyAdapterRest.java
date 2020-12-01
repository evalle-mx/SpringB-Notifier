package net.tce.admin.adapter.rest;

import net.tce.admin.service.VacancyService;
import net.tce.dto.VacancyDto;
import net.tce.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.JsonSyntaxException;

@Controller
@RequestMapping(Constante.URI_VACANCY)
public class VacancyAdapterRest  extends ErrorMessageAdapterRest {
	
	@Autowired
	private VacancyService vacancyService;

	
	
	/**
	 * Controlador expuesto que ejecuta las operaciones de publicación de posición
	 * @param json, mensaje JSON
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_VACANCYPUBLICATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String setVacancyPublication(@RequestBody String json) throws JsonSyntaxException, Exception {
		log4j.debug("&&&&&&  json :" + json);
		return gson.toJson(vacancyService.setVacancyPublication(gson.fromJson(json, VacancyDto.class)));
	  }	
	
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio vacancy 
	 * @param json
	 * @return un mensaje json informativo
	 */
	/*@RequestMapping(value=Constante.URI_CREATECOMPLETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createComplete(@RequestBody String json)  {
		Object object = vacancyService.createComplete(gson.fromJson(json, VacancyDto.class));
		String restResponse =(object instanceof String) ? (String)object: gson.toJson(object);
		if(!restResponse.trim().startsWith("[")){
			restResponse = "[".concat(restResponse).concat("]"); //Se agrega para cumplir el estandar, pues el masivo usa este servicio
		}
		return restResponse;
	  }*/
	
	/**
	 * Controlador expuesto que ejecuta la funcion update del servicio curriculum 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	/*@RequestMapping(value=Constante.URI_CREATEMASIVE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String createMasive(@RequestBody String json)  {
		Object object = vacancyService.createMasive(gson.fromJson(json, MasivoDto.class));
		return (object instanceof String) ? (String)object: gson.toJson(object);
	  }*/
	
}
