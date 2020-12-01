package net.tce.admin.adapter.rest;

import net.tce.admin.service.BitacoraPosicionService;
import net.tce.dto.BtcPosicionDto;
import net.tce.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(Constante.URI_BTC_POSICION)
public class BitacoraPosicionAdapterRest extends ErrorMessageAdapterRest {

	@Autowired
	private  BitacoraPosicionService bitacoraPosicionService;
	
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json) throws  Exception  {
		return bitacoraPosicionService.create(gson.fromJson(json, BtcPosicionDto.class));
	  } 
	
}
