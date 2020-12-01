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
			    	createNP(notificacionProgramadaDto);
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
	
}
