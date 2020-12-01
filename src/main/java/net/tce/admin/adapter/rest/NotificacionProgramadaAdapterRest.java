package net.tce.admin.adapter.rest;

import net.tce.admin.service.NotificacionProgramadaService;
import net.tce.admin.service.NotificationService;
import net.tce.dto.NotificacionProgramadaDto;
import net.tce.dto.NotificationDto;
import net.tce.util.Constante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(Constante.URI_NOTIFY_PROG)
public class NotificacionProgramadaAdapterRest extends ErrorMessageAdapterRest  {

	@Autowired
	private NotificacionProgramadaService notificacionProgramadaService;
	
	/**
	 * Controlador expuesto que ejecuta la funcion create del servicio notificacion Programada 
	 * @param json
	 * @return un mensaje json informativo
	 * @throws Exception 
	 */
	@RequestMapping(value=Constante.URI_CREATE, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String create(@RequestBody String json) throws Exception {
		return notificacionProgramadaService.create(gson.fromJson(json, NotificacionProgramadaDto.class));
	  }

}
