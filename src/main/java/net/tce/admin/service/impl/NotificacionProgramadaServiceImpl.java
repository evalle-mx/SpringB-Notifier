package net.tce.admin.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.tce.admin.service.NotificacionProgramadaService;
import net.tce.dao.NotificacionProgramadaDao;
import net.tce.dao.RelacionEmpresaPersonaDao;
import net.tce.dao.TipoEventoDao;
import net.tce.dto.AdminDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.NotificacionProgramadaDto;
import net.tce.dto.NotificationDto;
import net.tce.dto.PosicionDto;
import net.tce.model.NotificacionProgramada;
import net.tce.model.TipoEvento;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsNotification;
import net.tce.util.UtilsTCE;

@Transactional
@Service("notificacionProgramadaService")
public class NotificacionProgramadaServiceImpl implements NotificacionProgramadaService {
	Logger log4j = Logger.getLogger(this.getClass());
	NotificacionProgramada notificacionProgramada;
	TipoEvento tipoEvento;
	NotificationDto notificationDto;
	
	@Autowired
	private TipoEventoDao tipoEventoDao;
	
	@Autowired
	private NotificacionProgramadaDao notificacionProgramadaDao;
	
	@Autowired
	private RelacionEmpresaPersonaDao relacionEmpresaPersonaDao;
	
	@Inject
	Gson gson;
	
	private JSONObject jsonNotifProg;

	/**
	 * Se crea una NotificacionProgramada
	 * @param notificacionProgramadaDto
	 * @return String
	 * @throws Exception 
	 */
	@Override
	public String create(NotificacionProgramadaDto notificacionProgramadaDto)
			throws Exception {

	    log4j.debug("<create> \n claveEvento: "+notificacionProgramadaDto.getClaveEvento()+
					"\n json="+notificacionProgramadaDto.getJson());
	    
	    if(notificacionProgramadaDto.getClaveEvento()==null || notificacionProgramadaDto.getJson()==null) {
	    	return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_006, 
					Mensaje.SERVICE_TYPE_ERROR, 
					Mensaje.MSG_ERROR+": [claveEvento, json]"));
	    }else {
		    //TODO generar lista destinatarios por defecto y solo ADICIONAR opcionalmente los de la base
		    //se verifica si es un mensaje de error fatal
		    if(notificacionProgramadaDto.getClaveEvento().equals(
		    				UtilsNotification.CLAVE_EVENTO_ERROR_FATAL)){
		    		    	
		    	List<Long> lsAdminsPerson=relacionEmpresaPersonaDao.getAdminsPerson();
		    	  log4j.debug("create() - error fatal -> lsAdminsPerson="+lsAdminsPerson.size());
		    	
				if(lsAdminsPerson != null && lsAdminsPerson.size() > 0){
					
					Iterator<Long> itAdminsPerson=lsAdminsPerson.iterator();
					while(itAdminsPerson.hasNext()){
						notificationDto=gson.fromJson(notificacionProgramadaDto.getJson(), NotificationDto.class);
						notificationDto.setIdPivote(itAdminsPerson.next().toString());
						notificacionProgramadaDto.setJson(gson.toJson(notificationDto));
						createNP(notificacionProgramadaDto);
					}
				}else {				 
					 //esto no debe pasar
					//Se rsuelve poniendo correos en una variable en el archivo de properties
					//tambien usar esta solucion cuando no hay base de datos(cachar excepcion)
					// y hacer una reingenieria en el servico de notificacionñ
					 log4j.fatal("No hay admin para mandar correos de error fatal");
				}
		    }else {
		    	/* Validar que el json contenga idPivote */
		    	jsonNotifProg = new JSONObject(notificacionProgramadaDto.getJson());
		    	if(!jsonNotifProg.has("idPivote")) {	    	    
		    		return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_006, 
							Mensaje.SERVICE_TYPE_ERROR, 
							Mensaje.SERVICE_MSG_EMPRESA_PARAMETROS_001+": idPivote"));
		    	}else {
		    		//Validar que exista clave de evento (TODO hacerlo dinamico a CLAVE GENERICA)
		    		if(tipoEventoDao.getIdTipoEvento(notificacionProgramadaDto.getClaveEvento()) == null){
		    			log4j.error("<create> No existe tipo de claveEvento " + notificacionProgramadaDto.getClaveEvento() + " en la tabla TipoEvento  ", 
		    					new NullPointerException("where TipoEvento.clave_evento == " + notificacionProgramadaDto.getClaveEvento() + " : null (0) "));
						
		    			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
								Mensaje.SERVICE_CODE_006, 
								Mensaje.SERVICE_TYPE_ERROR, 
								Mensaje.MSG_ERROR_EMPTY+": claveEvento"));
		    		}else {
		    			createNP(notificacionProgramadaDto);
		    		}
		    	}
		    }	    
			return Mensaje.SERVICE_MSG_OK_JSON;			
	    }
	}
	
	/**
	 * 
	 * @param notificacionProgramadaDto
	 */
	private void createNP(NotificacionProgramadaDto notificacionProgramadaDto) {
		
		notificacionProgramada=new NotificacionProgramada();
			tipoEvento=new TipoEvento();
		tipoEvento.setIdTipoEvento(tipoEventoDao.getIdTipoEvento(
									notificacionProgramadaDto.getClaveEvento()));				
		notificacionProgramada.setTipoEvento(tipoEvento);
		notificacionProgramada.setEnviada(false);
		notificacionProgramada.setJson(notificacionProgramadaDto.getJson());
		notificacionProgramada.setFechaCreacion(DateUtily.getToday());
		
		if(notificacionProgramadaDto.getDescripcion() != null){
			notificacionProgramada.setDescripcion(notificacionProgramadaDto.getDescripcion());
		}		
		
		//se crea la notificacion
		notificacionProgramadaDao.create(notificacionProgramada);
	}

	@Override
	public Object get(NotificacionProgramadaDto notificacionProgramadaDto) throws Exception {
		log4j.debug("<get>");
		
		log4j.debug("<get> tipoNotificacion: " + notificacionProgramadaDto.getTipoNotificacion()
				+ (notificacionProgramadaDto.getPrioridad()!=null? " prioridad: " + notificacionProgramadaDto.getPrioridad():"")
				+ (notificacionProgramadaDto.getIntentos()!=null? " intentos: " + notificacionProgramadaDto.getIntentos():"")
				+ (notificacionProgramadaDto.getEnviada()!=null? " enviada: " + notificacionProgramadaDto.getEnviada():"")
		);
		
		if(notificacionProgramadaDto.getTipoNotificacion()!= null && notificacionProgramadaDto.getTipoNotificacion().equals("1")) {
			log4j.debug("<get> Obtener notificaciones PROGRAMADAS");
			Long prioridad = notificacionProgramadaDto.getPrioridad()!=null?Long.parseLong(notificacionProgramadaDto.getPrioridad()):null;
			Long intentos = notificacionProgramadaDto.getIntentos()!=null?Long.parseLong(notificacionProgramadaDto.getIntentos()):null;
			Boolean enviada = notificacionProgramadaDto.getEnviada()!=null?
					(notificacionProgramadaDto.getEnviada().equals("1")?true:false):
						null;
			
			List<NotificacionProgramadaDto> lsNotificaciones = notificacionProgramadaDao.get(prioridad, intentos, enviada);
			return lsNotificaciones;
		
			/*  //Metodo de prueba para replicar Procesos automaticos
			List<net.tce.model.NotificacionProgramada> lsModel = notificacionProgramadaDao.getModel(prioridad, intentos, enviada);
			log4j.debug("# NotificacionProgramada's: " + lsModel.size() );
			log4j.debug("NotificacionProgramada's: " + lsModel );
			return "[]"; */
		}
		else { 
			log4j.debug("<get> Obtener notificaciones [ENVIADAS]");
			//TODO implementar algoritmo para notificaciones enviadas
			return "[]";
		}
		
		
		
//		if(!lsNotificaciones.isEmpty()) {
//			sb = new StringBuilder();
//
//			//Procesa los textos json en vista 
//			Iterator<NotificacionProgramadaDto> itNotificacion = lsNotificaciones.iterator();
//			while(itNotificacion.hasNext()) {
//				dto = itNotificacion.next();
//				jsonString = dto.getJson();
//				JSONObject json = new JSONObject(jsonString);
//				log4j.debug("json: " + json.toString() );
//				dto.setJson(json.toString() );
//			}
//		}
//		return "[]";
	}
	
}
