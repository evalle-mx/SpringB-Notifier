package net.tce.admin.adapter.rest;

import javax.inject.Inject;

import net.tce.admin.service.MonitorService;
import net.tce.dto.MonitorDto;
import net.tce.util.Constante;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

@Controller
@RequestMapping(Constante.URI_MONITOR)
public class MonitorAdapterRest extends ErrorMessageAdapterRest {
	final Logger log4j = Logger.getLogger( this.getClass());

	@Inject
	private Gson gson;
	
	@Autowired
	private MonitorService monitorService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio Monitor
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		log4j.debug(" json:"+json);
		return monitorService.create(gson.fromJson(json, MonitorDto.class));
	}
	
}
