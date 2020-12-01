package net.tce.task.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.admin.service.NotificacionProgramadaService;
import net.tce.app.exception.SystemTCEException;
import net.tce.dao.PersonaDao;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.NotificacionDto;
import net.tce.dto.NotificacionProgramadaDto;
import net.tce.dto.SchedulerDto;
import net.tce.task.service.ReminderService;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsNotification;

@Transactional
@Service("reminderService")
public class ReminderServiceImpl implements ReminderService{

	static final Logger log4j = Logger.getLogger("QUARTZ");
	StringBuilder sbMensaje;
	Date fechaActual;
	JSONObject jsonNotProgamada;
	
	@Autowired
	private EmpresaParametroService empresaParametroService;
	
	@Autowired 
	private NotificacionProgramadaService notificacionProgramadaService;
	
	@Autowired
	private PersonaDao personaDao;
	
	/**
	 * Se encarga de mandar recordatorios
	 */
	public synchronized String sendReminder(SchedulerDto schedulerDto) throws Exception{
		log4j.debug("<sendReminder> \n "+schedulerDto);
		int timeParametro=0;
		int numParametro=0;
		String resp=Mensaje.SERVICE_MSG_OK_JSON;
		sbMensaje=new StringBuilder();
		Date dateActual=DateUtily.getToday();
		
		//Recordatorios sobre la confirmación de la inscripción
		EmpresaParametroDto empresaParametroDto=new EmpresaParametroDto();
		empresaParametroDto.setIdEmpresaConf(String.valueOf(Constante.IDCONF_NULL));
		empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_APLICACION_GENERAL);
		
		//Confirmarción de Inscripcion
		if(schedulerDto.getTypeReminder() == Constante.TYPE_REMINDER_CONFIRM_INSCRIP){
			
			empresaParametroDto.setContexto(Constante.CONTEXT_PARAM_TIME_CONFIRM_INSCRIP);
			
			//Se obtiene el tiempo en minutos
			timeParametro=getParameter(empresaParametroDto, Constante.CONTEXT_PARAM_TIME_CONFIRM_INSCRIP_DEFAULT);
			
			//Se obtiene el numero de veces
			empresaParametroDto.setContexto(Constante.CONTEXT_PARAM_NUM_CONFIRM_INSCRIP);
			numParametro=getParameter(empresaParametroDto, Constante.CONTEXT_PARAM_NUM_CONFIRM_INSCRIP_DEFAULT);
			//log4j.debug("sendReminder() -> "+Constante.CONTEXT_PARAM_TIME_CONFIRM_INSCRIP+"="+timeParametro+
					   // " "+Constante.CONTEXT_PARAM_NUM_CONFIRM_INSCRIP+"="+numParametro);
			//Mensaje para el log
			sbMensaje.append("Se envia recordatorio de Confirmación de Inscripción a usuario:");
			
		//publicación	
		}else if(schedulerDto.getTypeReminder() == Constante.TYPE_REMINDER_PUBLICATION){
			
			empresaParametroDto.setContexto(Constante.CONTEXT_PARAM_TIME_PUBLICATION);
			
			//Se obtiene el tiempo en minutos
			timeParametro=getParameter(empresaParametroDto, Constante.CONTEXT_PARAM_TIME_PUBLI_DEFAULT);
			
			//Se obtiene el numero de veces
			empresaParametroDto.setContexto(Constante.CONTEXT_PARAM_NUM_PUBLICATION);
			numParametro=getParameter(empresaParametroDto, Constante.CONTEXT_PARAM_NUM_PUBLI_DEFAULT);
			//log4j.debug("sendReminder() -> "+Constante.CONTEXT_PARAM_TIME_PUBLICATION+"="+timeParametro+
					   // " "+Constante.CONTEXT_PARAM_NUM_PUBLICATION+"="+numParametro);
			//Mensaje para el log
			sbMensaje.append("Se envia recordatorio Publicacion de CV a usuario:");
		}
		
		//Se obtiene lista de Persona en NotificacionDto's
		log4j.debug("<sendReminder> tipo:"+schedulerDto.getTypeReminder() +", numeroVeces:"+numParametro);
		List<NotificacionDto> lsNotificacionDto=personaDao.getReminder(numParametro,
														schedulerDto.getTypeReminder());
		log4j.debug("<sendReminder> lsNotificacionDto.size="+(lsNotificacionDto != null ? 
													lsNotificacionDto.size():"es null"));
		//Si existen resultados se procesan los recordatorios
		if(lsNotificacionDto != null && lsNotificacionDto.size() > 0){
			Date dateFinal = null;
			Iterator<NotificacionDto> itNotificacionDto= lsNotificacionDto.iterator();
			while(itNotificacionDto.hasNext()){
				NotificacionDto notificacionDto=itNotificacionDto.next();
				log4j.debug("sendReminder() -> timeParametro="+timeParametro+
				    " numParametro="+numParametro);
				log4j.debug("sendReminder() -> getFechaCreacion="+notificacionDto.getFechaCreacion()+
						" getIdPersona="+notificacionDto.getIdPersona()+
						" getEmail="+notificacionDto.getEmail());
				
				log4j.debug("sendReminder() -> getFechaDelTipo="+notificacionDto.getFechaDelTipo()+
							" getNumeroDelTipo="+notificacionDto.getNumeroDelTipo());
				
				//Se obtiene una fecha nueva de la adicion del tiempo parametrizado por el numero de eventos de 
				//envio de correo de confirmación de Inscripcion
				//Confirmarción de Inscripcion
				
				//si es nula la fecha significa que se salto el primer paso de inscripcion
				if(notificacionDto.getFechaDelTipo() == null) {
					notificacionDto.setFechaDelTipo(DateUtily.getToday());
				}
				
				dateFinal=DateUtily.addMinutes(notificacionDto.getFechaDelTipo(), 
						(notificacionDto.getNumeroDelTipo().shortValue() *(timeParametro)));
				
			
				log4j.debug("sendReminder() -> dateActual="+dateActual);
				log4j.debug("sendReminder() -> dateFinal="+dateFinal);
				
				
				//Se manda el mensaje
				if(dateActual.getTime() >= dateFinal.getTime()){	
					log4j.debug("sendReminder() -> se cumple -->  getFechaActual >= dateFinal");
					
					try {
						//Json de comentario notificación	
						jsonNotProgamada = new JSONObject();
						jsonNotProgamada.put("idPivote", ""+notificacionDto.getIdPersona());						
						jsonNotProgamada.put("comentario", notificacionDto.getEmail());
						
						//Confirmarción de Inscripcion
						if(schedulerDto.getTypeReminder() == Constante.TYPE_REMINDER_CONFIRM_INSCRIP){							
							jsonNotProgamada.put("claveEvento",UtilsNotification.CLAVE_EVENTO_CONF_INSC);
//							log4j.debug("sendReminder() -> Se manda la notificacion:"+UtilsNotification.CLAVE_EVENTO_CONF_INSC);							
						//publicación	
						}else if(schedulerDto.getTypeReminder() == Constante.TYPE_REMINDER_PUBLICATION){
							jsonNotProgamada.put("claveEvento",UtilsNotification.CLAVE_EVENTO_RECORDAR_PUBLICACION);
//							log4j.debug("sendReminder() -> Se manda la notificacion:"+UtilsNotification.CLAVE_EVENTO_RECORDAR_PUBLICACION);							
						}
						log4j.debug("<sendReminder> claveEvento:" + jsonNotProgamada.get("claveEvento") );
						
						log4j.debug("<sendReminder> Se crea nueva notificacion_programada: \n" + jsonNotProgamada );
						resp=notificacionProgramadaService.create(new NotificacionProgramadaDto(
															(String)jsonNotProgamada.get("claveEvento"),
															jsonNotProgamada.toString(),null));
						
						log4j.debug("<sendReminder> resp \n <== "+resp);
						
						//Si respuesta NO es Exitosa [] se escribe ERROR EN LOG
						if(!resp.equals(Mensaje.SERVICE_MSG_OK_JSON)){
							log4j.error("<sendReminder> Error al generar notificacion_programada para idPersona:"+
									 notificacionDto.getIdPersona()+"; Respuesta:"+resp);
						}
					 } catch (Exception e) {
						String error=ExceptionUtils.getStackTrace(e);

						log4j.error("<sendReminder> Excepcion al generar notificacion_programada para idPersona:"+
								 notificacionDto.getIdPersona()+" error:"+error, e);
						 e.printStackTrace();
						 throw new SystemTCEException("Excepcion al generar notificacion_programada para idPersona:"+
								 notificacionDto.getIdPersona()+" error:"+error);
					}
					
					//Se incrementa el contador y se actualiza en la bd
					notificacionDto.setFechaActual(dateActual);
					log4j.debug("<sendReminder> Se incrementa el contador y se actualiza en la bd");
					personaDao.updateNumeroReminder(notificacionDto, schedulerDto.getTypeReminder());
					
					//Mensaje final al log
					sbMensaje=sbMensaje.delete(sbMensaje.indexOf(":")+1, sbMensaje.length());
					log4j.info(sbMensaje.append(notificacionDto.getIdPersona()).toString());
					
					//En duda si esta acción se da de alta en la tabla control_proceso
				}
			}
		}

		return null;
	}
	
	/**
	 * 
	 * @param empresaParametroDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getParameter(EmpresaParametroDto empresaParametroDto, int parametroDefault) throws Exception{
		Object object=empresaParametroService.get(empresaParametroDto,false);
		if(object instanceof EmpresaParametroDto ){
			return parametroDefault;
		}else{
			return Integer.parseInt(((List<EmpresaParametroDto>)object).get(0).getValor());
		}	
	}

}
