package net.tce.admin.adapter.rest;


import net.tce.admin.service.ApplicantService;
import net.tce.dto.CandidatoDto;
import net.tce.dto.PosicionDto;
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
@RequestMapping(Constante.URI_APPLICANT)
public class ApplicantAdapterRest extends ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());	
	Object object;
	
	@Autowired
	ApplicantService applicantService;

	
	/**
	 * Controlador expuesto que ejecuta la funcion de buscar candidatos para una posicion especifica 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws java.lang.Exception 
	 */
	@RequestMapping(value=Constante.URI_APPLICANT_SEARCH_APPLICANTS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String searchApplicants(@RequestBody String json) throws java.lang.Exception {
		log4j.debug("&&&&&&  json=" + json);
		return applicantService.searchApplicants(gson.fromJson(json, PosicionDto.class));
	  }	

	/**
	 * Controlador expuesto que ejecuta la funcion de buscar candidatos para una posicion especifica 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 * @throws java.lang.Exception 
	 */
	@RequestMapping(value=Constante.URI_APPLICANT_GET_APPLICANTS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String getApplicants(@RequestBody String json) throws java.lang.Exception {
		object=applicantService.getApplicants(gson.fromJson(json, PosicionDto.class));
		if(object instanceof String){
			return (String)object;
		}else{
			return new StringBuilder("[").append(gson.toJson(object)).append("]").toString();
		}
	  }	
	

	/**
	 * Controlador expuesto que ejecuta la publicación del curriculum
	 * @param json, mensaje JSON
	 * @return,  mensaje JSON 
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_APPLICANT_SET_RESUME_PUBLICATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String setResumePublication(@RequestBody String json) throws JsonSyntaxException, Exception {
		log4j.debug("&&&&&&  json :" + json);
		object=applicantService.setResumePublication(gson.fromJson(json, CandidatoDto.class));
		return ((object instanceof String) ? (String)object:gson.toJson(object));
	  }	

	
}