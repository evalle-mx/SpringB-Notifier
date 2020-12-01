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
/**
 * EndPoint para servicios de <b>Notificacion (programada)</b> <br>
 * @author Evalle
 *
 */
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
	
	/**
	 * Controlador expuesto que ejecuta la funcion get del servicio Rol 
	 * @param json
	 * @return  un mensaje json con una lista de objetos Rol
	 * @throws java.lang.Exception 
	 */
	@RequestMapping(value=Constante.URI_GET, method=RequestMethod.POST,headers = Constante.ACEPT_REST_JSON)
	public @ResponseBody String get(@RequestBody String json) throws Exception  {
		Object object=notificacionProgramadaService.get(gson.fromJson(json, NotificacionProgramadaDto.class));
		return  (object instanceof String) ? (String)object:gson.toJson(object);
	}

}
