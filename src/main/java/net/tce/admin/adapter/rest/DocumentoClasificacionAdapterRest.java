package net.tce.admin.adapter.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonSyntaxException;

import net.tce.admin.adapter.rest.ErrorMessageAdapterRest;
import net.tce.admin.service.DocumentoClasificacionService;
import net.tce.dto.DocumentoClasificacionDto;
import net.tce.util.Constante;

@Controller
@RequestMapping(Constante.URI_DOCS_CLASS)
public class DocumentoClasificacionAdapterRest extends ErrorMessageAdapterRest {

	Logger log4j = Logger.getLogger( DocumentoClasificacionAdapterRest.class );
	
	@Autowired
	DocumentoClasificacionService documentoClasificacionService;
	
	
	@RequestMapping(value=Constante.URI_DOCS_LOADTOKENS, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String loadTokens(@RequestBody String json) throws Exception {
		log4j.debug(" #$% json:"+json);
		Object object= documentoClasificacionService.loadTokens( json );
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}
	
	@RequestMapping(value=Constante.URI_DOCS_CLASSIFYBYLOT, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String classifyByLot(@RequestBody String json) throws JsonSyntaxException, Exception {
		log4j.debug(" ==> json:"+json);
		return (String)documentoClasificacionService.classifyByLot( gson.fromJson(json, DocumentoClasificacionDto.class) );
	}
}
