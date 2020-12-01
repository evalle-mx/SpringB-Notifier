package net.tce.admin.adapter.rest;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import net.tce.admin.service.BitacoraTrackingService;
import net.tce.dto.BtcTrackingDto;
import net.tce.dto.ModeloRscDto;
import net.tce.util.Constante;

@Controller
@RequestMapping(Constante.URI_BTC_TRACK_)
public class BitacoraTrackingAdapterRest  extends ErrorMessageAdapterRest {

	@Inject
	private Gson gson;
	
	@Autowired
	private BitacoraTrackingService  bitacoraTrackingService;
	/**
	 * Controlador expuesto que ejecuta la funcion CREATE del servicio Tracking
	 * @param json
	 * @return idTracking
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json)  throws Exception {
		return bitacoraTrackingService.create(gson.fromJson(json, BtcTrackingDto.class));
	}
	
}
