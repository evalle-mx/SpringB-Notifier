package net.tce.admin.adapter.rest;

import net.tce.admin.service.CurriculumEmpresaService;
import net.tce.dto.CurriculumEmpresaDto;
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
 * @author 
 *
 */
@Controller
@RequestMapping(Constante.URI_CURRICULUM_EMPRESA)
public class CurriculumEmpresaAdapterRest extends ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());	
	
	@Autowired
	CurriculumEmpresaService curriculumEmpresaService;
	
	
	/**
	 * Controlador expuesto que ejecuta las operaciones de publicación de curriculum empresarial
	 * @param json, mensaje JSON
	 * @return,  mensaje JSON 
	 * @throws Exception
	 * @throws JsonSyntaxException 
	 */
	@RequestMapping(value=Constante.URI_CURRICULUM_EMPRESA_PUBLICATION, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON+";charset=UTF-8")
	public @ResponseBody String setEnterpriseResumePublication(@RequestBody String json) throws JsonSyntaxException, Exception {
		Object object=curriculumEmpresaService.setEnterpriseResumePublication(gson.fromJson(json, CurriculumEmpresaDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	  }	


	
}
