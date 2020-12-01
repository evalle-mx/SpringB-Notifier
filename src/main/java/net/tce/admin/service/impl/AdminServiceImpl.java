package net.tce.admin.service.impl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import net.tce.admin.service.AdminService;
import net.tce.admin.service.NotificacionProgramadaService;
import net.tce.admin.service.RestJsonService;
import net.tce.dao.ControlProcesoDao;
import net.tce.dao.EmpresaConfDao;
import net.tce.dao.PerfilPosicionDao;
import net.tce.dao.RelacionEmpresaPersonaDao;
import net.tce.dto.AdminDto;
import net.tce.dto.ControlProcesoDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.NotificacionProgramadaDto;
import net.tce.model.PerfilPosicion;
import net.tce.model.RelacionEmpresaPersona;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsNotification;
import net.tce.util.UtilsTCE;



/**
 * Clase donde se aplica las politicas de negocio del servicio AdminService
 * @author DhrDeveloper
 *
 */
@Transactional
@Service("adminService")
public class AdminServiceImpl implements AdminService{
	
	 Logger log4j = Logger.getLogger( this.getClass());	

	 StringBuffer sb;
	 List<ControlProcesoDto> lsControlProcesoDto;
	 ControlProcesoDto controlProcesoDto;
	 Byte tipoProceso;
	 AdminDto admindto;
	 String resp;
	 JSONObject jsonObject = null;

	 @Autowired 
	 private NotificacionProgramadaService notificacionProgramadaService;
	 
	 @Autowired
	 private RelacionEmpresaPersonaDao relacionEmpresaPersonaDao;

	 @Autowired
	 private ControlProcesoDao controlProcesoDao;
	
	 @Autowired
	 private RestJsonService restJsonService;
	 
	 
	 @Autowired
	 private PerfilPosicionDao perfilPosicionDao;
	 
	 @Autowired
	 private EmpresaConfDao empresaConfDao;
	 
	 
	 @Inject
	 Gson gson;
	
	
	/**
	 * Ultima fecha del proceso de sincronización de documentos
	 */
	@SuppressWarnings("unchecked")
	public Object lastDateFinalSyncDocs(ControlProcesoDto cpDto) {
		log4j.debug("<synchronizeDocs> getTipoSync:"+ cpDto.getTipoSync()+
				" getIdPersona="+cpDto.getIdPersona()+
				" getIdEmpresaConf="+cpDto.getIdEmpresaConf());

		
		if(cpDto != null && 
			(cpDto.getIdEmpresaConf() != null &&
			 cpDto.getIdPersona() != null &&
			 cpDto.getTipoSync() != null &&
			 UtilsTCE.isNumeric(cpDto.getTipoSync()))){
		
				if(cpDto.getTipoSync().equals(Constante.TIPO_SYNC_DOCS_SOLR)){
					return lastDatefinalTask(cpDto,Constante.TIPO_PROCESO_SYNC_DOCS_SOLR);
				}else if(cpDto.getTipoSync() .equals(Constante.TIPO_SYNC_DOCS_CONFIR)){
					return lastDatefinalTask(cpDto,Constante.TIPO_PROCESO_SYNC_DOCS_CONFIR);
				}else if(cpDto.getTipoSync() .equals(Constante.TIPO_SYNC_DOCS_TODOS)){
					Object resp;
					controlProcesoDto=new ControlProcesoDto();
					
					//Para sincronizacion de docs solr
					resp=lastDatefinalTask(cpDto,Constante.TIPO_PROCESO_SYNC_DOCS_SOLR);					
					if(resp instanceof String){
						return resp;
					}else{
						controlProcesoDto.setFechaSynSolr(((List<ControlProcesoDto>)
																resp).get(0).getFecha());
					}
					
					//Para sincronizacion de docs confirmados
					resp=lastDatefinalTask(cpDto,Constante.TIPO_PROCESO_SYNC_DOCS_CONFIR);
					if(resp instanceof String){
						return resp;
					}else{
						controlProcesoDto.setFechaSynConfir(((List<ControlProcesoDto>)
																resp).get(0).getFecha());
					}
					lsControlProcesoDto=new ArrayList<ControlProcesoDto>();
					lsControlProcesoDto.add(controlProcesoDto);					
					return lsControlProcesoDto;
				}else{
					return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
														Mensaje.SERVICE_CODE_006, 
														Mensaje.SERVICE_TYPE_ERROR, 
														Mensaje.MSG_ERROR));
				}
				
		}else{
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
												Mensaje.SERVICE_CODE_006, 
												Mensaje.SERVICE_TYPE_ERROR, 
												Mensaje.MSG_ERROR));
		}
	}
	
	
	/**
	 * Ultima fecha del proceso de remodelado
	 */
	public Object lastDateFinalRemodel(ControlProcesoDto controlProcesoDto) {
			return lastDatefinalTask(controlProcesoDto,Constante.TIPO_PROCESO_REMODEL_CLASS);			
	}
	
	/**
	 * Ultima fecha del proceso de Reload Core Solr
	 */
	public Object lastDateFinalReloadCoreSolr(ControlProcesoDto controlProcesoDto) {
			return lastDatefinalTask(controlProcesoDto,Constante.TIPO_PROCESO_RELOAD_CORE_SOLR);			
	}
	
	/**
	 * Ultima fecha del proceso de reclasificacion de docs
	 */
	public Object lastDateFinalReclassDocs(ControlProcesoDto controlProcesoDto) {
			return lastDatefinalTask(controlProcesoDto,Constante.TIPO_PROCESO_RECLASS_DOCS);			
	}
	
	
	
	/**
	 * Regresa la última fecha de la llamada al proceso correspondiente
	 * @param controlProcesoDto, objeto que tiene 
	 * @param tipoProceso, tipo de proceso
	 * @return
	 */
	private Object lastDatefinalTask(ControlProcesoDto controlProcesoDto,Byte tipoProceso){
		log4j.debug("lastDatefinalTask() --> getIdEmpresaConf="+controlProcesoDto.getIdEmpresaConf()+
				" getIdPersona="+controlProcesoDto.getIdPersona());
		if(controlProcesoDto.getIdEmpresaConf() != null && controlProcesoDto.getIdPersona() != null){
			//Ojo -> cuando éste servicio es invocado por el quartz, el "demonio" manda : idEmpresaConf=-1 e idPersona=-1	
			controlProcesoDto=controlProcesoDao.lastDateFinal(tipoProceso.longValue());		
			lsControlProcesoDto=new ArrayList<ControlProcesoDto>();
			if(controlProcesoDto.getFecha() == null){
				controlProcesoDto.setFecha("-1");			
			}
			log4j.debug("lastDateFinal() --> tipoProceso="+tipoProceso+
					   " controlProcesoDto="+controlProcesoDto.getFecha());
			
			lsControlProcesoDto.add(controlProcesoDto);
			return lsControlProcesoDto;
		}else{
			log4j.error("ERROR: lastDatefinalTask() -->  IdEmpresaConf nulo o/y  IdPersona nulo. Para el tipoProceso="+tipoProceso);
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
												Mensaje.SERVICE_CODE_001,
												Mensaje.SERVICE_TYPE_FATAL,
												Mensaje.MSG_ERROR_SISTEMA));	
		}	
	}
	
	/**
	 * Usa el servico admin en  transactional
	 */
	@SuppressWarnings("finally")
	public String notificacionFatal(MensajeDto mensajeDto){
		log4j.debug("enviarCorreo() -> getMessage="+mensajeDto.getMessage());
		
		try {
			jsonObject = new JSONObject();
			jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_ERROR_FATAL);
			jsonObject.put("comentario",mensajeDto.getMessage());
			jsonObject.put("nombreEmisor","AppNotifierStructured");
			InetAddress address = InetAddress.getLocalHost();
			jsonObject.put("hostName",address.getHostName());
			jsonObject.put("hostAddress",address.getHostAddress());
			jsonObject.put("fecha",DateUtily.date2String(DateUtily.getToday(),
											Constante.DATE_FORMAT_JAVA_ZONE));
			
			//Se pone la notificación
			resp=notificacionProgramadaService.create(new NotificacionProgramadaDto(
												(String)jsonObject.get("claveEvento"),
												jsonObject.toString(),null));
			
			
			log4j.debug("enviarCorreo() -> resp:"+resp);
			 if(!resp.equals(Mensaje.SERVICE_MSG_OK_JSON)){
				 return resp;
			 }
		} catch (Exception e) {
			log4j.error("Error al enviar correo a los Administradores por Error Fatal: ",e);			
			e.printStackTrace();
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
												Mensaje.SERVICE_CODE_001,
												Mensaje.SERVICE_TYPE_FATAL,
												Mensaje.MSG_ERROR_SISTEMA));
		}finally{
			if(!mensajeDto.getCode().equals(Mensaje.SERVICE_CODE_200)){
				mensajeDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
			}
			return UtilsTCE.getJsonMessageGson(null,mensajeDto); 
		}		
	}

	
	/**
	 * Se obtiene un objeto PerfilPosicion
	 * @param IdPosicion, identificador de la posicion
	 * @return PerfilPosicion
	 */
	public PerfilPosicion getPerfilPosicion(String IdPosicion) {
		return perfilPosicionDao.getInterno(Long.parseLong(IdPosicion));
	}
	
	/**
	 * Se obtiene un objeto RelacionEmpresaPersona
	 * @param idPersona, identificador de la persona
	 * @param idEmpresaConf, identificador de la empresaconf
	 * @return RelacionEmpresaPersona
	 * @throws Exception 
	 */
	public RelacionEmpresaPersona getRelacionEmpresaPersona(String idPersona, 
			String idEmpresaConf) throws Exception {
		HashMap<String, Object> currFilters  = new HashMap<String, Object>();
		currFilters.put("persona.idPersona",Long.parseLong(idPersona));
		currFilters.put("empresa.idEmpresa",empresaConfDao.getEmpresa(idEmpresaConf));
		return (RelacionEmpresaPersona)relacionEmpresaPersonaDao.
												getByFilters(currFilters).get(0);
	}
}
