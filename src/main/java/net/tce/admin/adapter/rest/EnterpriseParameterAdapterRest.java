package net.tce.admin.adapter.rest;

import net.tce.admin.service.EmpresaParametroService;
import net.tce.dto.EmpresaParametroDto;
import net.tce.util.Constante;
import net.tce.util.UtilsTCE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Clase donde se aplica el MVC Spring REST para los servicos expuestos
 * @author evalle
 *
 */
@Controller
@RequestMapping(Constante.URI_ENTERPRISE)
public class EnterpriseParameterAdapterRest extends ErrorMessageAdapterRest{
	
	
	@Autowired
	EmpresaParametroService empresaParametroService;


	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio academicBackground 
	 * @param json, mensaje json del cliente
	 * @return JSON Message
	 * @throws ClassNotFoundException 
	 * @throws JsonSyntaxException
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String getParameters(@RequestBody String json) throws Exception  {
		log4j.debug("%%% getParameters --> json="+json);
		Object object=empresaParametroService.getExt(gson.fromJson(json, EmpresaParametroDto.class));
		if(object instanceof EmpresaParametroDto ){
			return UtilsTCE.getJsonMessageGson(null,object);
		}else{
			return new GsonBuilder().disableHtmlEscaping().create().toJson(object);
		}
		
	}
	
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json) throws Exception  {
		log4j.debug("%%% createParameters --> json="+json);
		Object object=empresaParametroService.create(gson.fromJson(json, EmpresaParametroDto.class));
		if(object instanceof EmpresaParametroDto ){
			return UtilsTCE.getJsonMessageGson(null,object);
		}else if(object instanceof String){
			return String.valueOf(object);
		}
		else{
			return new GsonBuilder().disableHtmlEscaping().create().toJson(object);
		}
	}
	
	@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json) throws Exception  {
		log4j.debug("%%% updateParameters --> json="+json);
		Object object=empresaParametroService.update(gson.fromJson(json, EmpresaParametroDto.class));
		if(object instanceof EmpresaParametroDto ){
			return UtilsTCE.getJsonMessageGson(null,object);
		}else if(object instanceof String){
			return String.valueOf(object);
		}
		else{
			return new GsonBuilder().disableHtmlEscaping().create().toJson(object);
		}
	}
	
	@RequestMapping(value=Constante.URI_DELETE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String delete(@RequestBody String json) throws Exception  {
		log4j.debug("%%% deleteParameters --> json="+json);
		Object object=empresaParametroService.delete(gson.fromJson(json, EmpresaParametroDto.class));
		if(object instanceof EmpresaParametroDto ){
			return UtilsTCE.getJsonMessageGson(null,object);
		}else if(object instanceof String){
			return String.valueOf(object);
		}
		else{
			return new GsonBuilder().disableHtmlEscaping().create().toJson(object);
		}
	}
	
	@RequestMapping(value=Constante.URI_MULTIPLE_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String updateMultiple(@RequestBody String json) throws Exception  {
		log4j.debug("%%% updMultipleParameters --> json="+json);
		Object object=empresaParametroService.updateMultiple(json);
				//gson.fromJson(json, JSONArray.class));
		if(object instanceof String){
			return String.valueOf(object);
		}else{
			return new GsonBuilder().disableHtmlEscaping().create().toJson(object);
		}
	}
	
	
	@RequestMapping(value=Constante.URI_RELOAD, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String reloadCache(@RequestBody String json) throws Exception  {
		log4j.debug("%%% reloadParameters --> json="+json);
		Object object=empresaParametroService.reloadCache(gson.fromJson(json, EmpresaParametroDto.class));
		if(object instanceof EmpresaParametroDto ){
			return UtilsTCE.getJsonMessageGson(null,object);
		}else if(object instanceof String){
			return String.valueOf(object);
		}else{
			return new GsonBuilder().disableHtmlEscaping().create().toJson(object);
		}
	}
}
