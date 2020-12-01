package net.tce.admin.adapter.rest;

import javax.inject.Inject;

import net.tce.admin.service.ModeloRscPosService;
import net.tce.dto.ModeloRscDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


/**
 * Funcionalidad de EndPoint Para <b>TRACKING</b> <br>
 * @author DhrDeveloper
 *
 */
@Controller
@RequestMapping(Constante.URI_TRACK_MODELO_RSC_POSICION)
public class ModeloRscPosAdapterRest extends ErrorMessageAdapterRest {

	@Inject
	private Gson gson;
	
	@Autowired
	private ModeloRscPosService modeloRscPosService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio Tracking
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		return modeloRscPosService.create(gson.fromJson(json, ModeloRscDto.class));
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion UPDATE del servicio Tracking 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Tracking
	 */
	/*@RequestMapping(value=Constante.URI_UPDATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String update(@RequestBody String json)  throws Exception {
		return modeloRscPosService.update(gson.fromJson(json, ModeloRscDto.class));
	}*/
	
}
