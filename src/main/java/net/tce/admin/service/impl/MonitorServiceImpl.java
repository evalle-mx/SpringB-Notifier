package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import net.tce.admin.service.AdminService;
import net.tce.admin.service.BitacoraTrackingService;
import net.tce.admin.service.MonitorService;
import net.tce.admin.service.NotificacionProgramadaService;
import net.tce.admin.service.TrackingMonitorService;
import net.tce.admin.service.TrackingPostulanteService;
import net.tce.app.exception.SystemTCEException;
import net.tce.dao.CandidatoDao;
import net.tce.dao.ModeloRscPosDao;
import net.tce.dao.ModeloRscPosFaseDao;
import net.tce.dao.MonitorDao;
import net.tce.dao.PosicionDao;
import net.tce.dao.TrackingMonitorDao;
import net.tce.dto.BtcTrackingDto;
import net.tce.dto.BtcTrackingMonPosDto;
import net.tce.dto.CandidatoDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.MonitorDto;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.dto.NotificacionProgramadaDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.dto.TrackingPostulanteDto;
import net.tce.model.Monitor;
import net.tce.model.Posicion;
import net.tce.model.RelacionEmpresaPersona;
import net.tce.model.Rol;
import net.tce.model.ModeloRscPosFase;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsNotification;
import net.tce.util.UtilsTCE;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Transactional
@Service("monitorService")
public class MonitorServiceImpl implements MonitorService{
	final Logger log4j = Logger.getLogger( this.getClass());
	Long idPersonaMonitor;
	Long idPosicion;
	Posicion posicion;
	JSONObject jsonObject = null;
	Rol rol;
	long idRelacionEmpresaPersona;
	long idREPPrincipal;
	List<MensajeDto> lsMensajeDto;
	String resp;
	Monitor monitor;
	TrackingMonitorDto trackingMonitorDto;
	TrackingMonitorDto trackingMonitorDtoLcl;
	TrackingPostulanteDto trackingPostulanteDtoLcl;
	ModeloRscPosFaseDto modeloRscPosFaseDto;
	RelacionEmpresaPersona relacionEmpresaPersona;
	List<ModeloRscPosFaseDto> lsModeloRscPosFaseDto;
	List<CandidatoDto> lsCandidatos;
	CandidatoDto candidato;
	Iterator<CandidatoDto> itCandidatos;
	Iterator<ModeloRscPosFaseDto> itModeloRscPosFaseDto;
	List<ModeloRscPosFase> lsModeloRscPosFase;
	Iterator<ModeloRscPosFase> itModeloRscPosFase;
	List<ModeloRscPosFaseDto> lsTrackingMonitorCanidatosEsquemaDto;
	ModeloRscPosFaseDto tracMonCanEsqDto;
	Long numTracksExistentes;
	boolean principal;
	ModeloRscPosFase modeloRscPosFase;
	ModeloRscPosFase modeloRscPosFaseLca;
	StringBuilder sbNotfText;
	BtcTrackingDto btcTrackingDto;
	BtcTrackingMonPosDto btcTrackingMonPosDto;
	Map<Long, Long> mapBtcMonitor;
	ModeloRscPosFaseDto trackingMonitorCanidatosEsquemaDto;
	Iterator<ModeloRscPosFaseDto> itTrackingMonitorCanidatosEsquemaDto;
	Iterator<TrackingMonitorDto> itMonitoresDto;
	Long idModeloRscPos ;
	Long idModeloRscPosFase;
	Long idModeloRscPosFasePostu;
	Long count;
	
	@Autowired 
	private NotificacionProgramadaService notificacionProgramadaService;
	
	@Autowired 
	private AdminService adminService;
	
	@Autowired 
	private TrackingMonitorService  trackingMonitorService;
	
	@Autowired 
	private TrackingPostulanteService trackingPostulanteService;
	
	@Autowired
	private BitacoraTrackingService bitacoraTrackingService;
	
	@Autowired
	private MonitorDao monitorDao;
	
	@Autowired
	private ModeloRscPosDao modeloRscPosDao;
	
	@Autowired
	private TrackingMonitorDao trackingMonitorDao;
	
	@Autowired
	private PosicionDao posicionDao;
	
	@Autowired
	private CandidatoDao candidatoDao;
	
	@Autowired
	private ModeloRscPosFaseDao modeloRscPosFaseDao;
	
	@Inject
	Gson gson;
	
	
	/**
	 * Se crea un nuevo Monitor
	 * @param monitorDto
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional(rollbackFor={SystemTCEException.class})
	public String create(MonitorDto monitorDto) throws Exception {
		
		log4j.debug("<create> -> getIdEmpresaConf="+monitorDto.getIdEmpresaConf()+
				" getIdPersona="+monitorDto.getIdPersona()+
				" getIdPosicion="+monitorDto.getIdPosicion()+
				" getMonitores="+(monitorDto.getMonitores() == null ? null:
					monitorDto.getMonitores()));
		
		//Se aplican los filtros desde el AppTransactionalStructured
		
		
		//Se obtiene el IdRelacionEmpresaPersona del monitor
		idREPPrincipal=adminService.getRelacionEmpresaPersona(monitorDto.getIdPersona(), 
															monitorDto.getIdEmpresaConf()).
															getIdRelacionEmpresaPersona();
		
		//Se obtienen los monitores
		itMonitoresDto=monitorDto.getMonitores().iterator();
		while(itMonitoresDto.hasNext()){
			trackingMonitorDto=itMonitoresDto.next();
			log4j.debug("<create> getIdMonitor_IdPersona="+trackingMonitorDto.getIdPersona()+
					" idModeloRscPos="+trackingMonitorDto.getIdModeloRscPos()+
					" idModeloRscPosFase="+trackingMonitorDto.getIdModeloRscPosFase()+
					" idBitacoraTrack="+trackingMonitorDto.getIdBitacoraTrack()+
					" getTracks="+(trackingMonitorDto.getTracks() == null ?
								  null:trackingMonitorDto.getTracks().size()));
			
			//Se convierte a boolean
			principal=(Integer.valueOf(trackingMonitorDto.getPrincipal()).
												intValue()  == 0 ? false:true);
			
			//idPosicion
			idPosicion=Long.parseLong(monitorDto.getIdPosicion());
			
			log4j.debug("<create> -> principal="+principal);
			
			//Se obtiene el IdRelacionEmpresaPersona del monitor
			idRelacionEmpresaPersona=adminService.getRelacionEmpresaPersona(trackingMonitorDto.getIdPersona(), 
																			monitorDto.getIdEmpresaConf()).
																			getIdRelacionEmpresaPersona();
			//si no existe IdModeloRscPos se obtiene
			if(trackingMonitorDto.getIdModeloRscPos() == null) {
				idModeloRscPosFase=Long.parseLong(trackingMonitorDto.getIdModeloRscPosFase());
				idModeloRscPos=modeloRscPosDao.readByFase(idModeloRscPosFase);
			}else {
				idModeloRscPos=Long.valueOf(trackingMonitorDto.getIdModeloRscPos());
			}
			
			log4j.debug("<create> -> idModeloRscPos="+idModeloRscPos);
						
			//se obtiene el rol
			rol=modeloRscPosDao.getRol(idModeloRscPos,idRelacionEmpresaPersona);
			
			//Se analiza si el rol del monitor y el rol del modeloRscPos son diferentes
			if(rol == null){

				//No son los mismos roles
				monitorDto.setMessages(UtilsTCE.getJsonMessageGson(
											monitorDto.getMessages(), 
						  					new MensajeDto(null,null,
						  					Mensaje.SERVICE_CODE_006,
						  					Mensaje.SERVICE_TYPE_ERROR,
						  					"El rol del monitor debe ser igual al rol del ModeloRSCPos")));
				continue;	
			}
			
			
			//solo para la creación de un monitor cuando ya existen fases
			if(trackingMonitorDto.getIdModeloRscPosFase() == null) {
			
				//si es principal, no puede haber dos principales
				if(principal){
					
					//se investiga si existe monitor principal
					idPersonaMonitor= monitorDao.getMonitorPrincipal(idPosicion);
					
					log4j.debug("<create> -> idPersonaMonitor="+idPersonaMonitor);
					
					//verificar si hay monitor
					if(idPersonaMonitor != null &&
					   idPersonaMonitor.doubleValue() > 0){
						
						//Se verifica si ya existe el monitor principal, pero la misma persona.
						if(Long.parseLong(trackingMonitorDto.getIdPersona()) == 
						   idPersonaMonitor.longValue()){							
							//Es el mismo, ir al otro monitor
							log4j.warn("Ya existe el monitor principal:"+trackingMonitorDto.getIdPersona()+
									   " , para la posición: "+monitorDto.getIdPosicion());
							continue;	
						}else{
							//ya existe monitor principal, pero es otra persona
							monitorDto.setMessages(UtilsTCE.getJsonMessageGson(
											monitorDto.getMessages(), 
						  					new MensajeDto("idPersona",
						  					trackingMonitorDto.getIdPersona(),
						  					Mensaje.SERVICE_CODE_004,
						  					Mensaje.SERVICE_TYPE_INFORMATION,
						  					"Ya existe un monitor principal, " +
						  					"por consiguiente a la persona se le " +
						  					"puso como monitor secundario")));	
						   principal=false;
						}					
					}					
				}
					
				
				log4j.debug("<create> -> idRelacionEmpresaPersona="+idRelacionEmpresaPersona+
						" principal="+principal);		
				
				//Se analiza si ya hay registros del monitor para este IdModeloRscPos
				numTracksExistentes=monitorDao.countTracks(Long.parseLong(trackingMonitorDto.
															getIdModeloRscPos()), 
															idRelacionEmpresaPersona);
				
				log4j.debug(" numTracksExistentes="+numTracksExistentes);	
				
				//si ya hay datos en la tabla
				if(numTracksExistentes != null && numTracksExistentes > 0){
					
					//Ya existen ModeloRscPosFase para este idModeloRscPos 
					//y idRelacionEmpresaPersona, mandar mensaje
					monitorDto.setMessages(UtilsTCE.getJsonMessageGson(
												monitorDto.getMessages(), 
							  					new MensajeDto("idModeloRscPos",
							  					idModeloRscPos.toString(),
							  					Mensaje.SERVICE_CODE_016,
							  					Mensaje.SERVICE_TYPE_ERROR,
							  					Mensaje.MSG_ERROR_DUPLICATE)));
					continue;						
				}
			}
			
			//Se analiza si hay fases en forma particular
			/*if(trackingMonitorDto.getTracks() == null || 
			   trackingMonitorDto.getTracks().size() ==0){*/
				
				if(trackingMonitorDto.getIdModeloRscPosFase() != null) {
					lsModeloRscPosFaseDto=new ArrayList<ModeloRscPosFaseDto>();
					modeloRscPosFaseDto=new ModeloRscPosFaseDto();

					log4j.debug("<create> -> idModeloRscPosFase="+idModeloRscPosFase);
					
					//se obtiene el objeto modeloRscPosFase
					modeloRscPosFase=modeloRscPosFaseDao.read(idModeloRscPosFase);
					
					//Obtener el ModeloRscPosFase para el monitor
					modeloRscPosFaseDto.setIdModeloRscPosFase(idModeloRscPosFase);
					modeloRscPosFaseDto.setNombre(modeloRscPosFase.getNombre());					
					
					lsModeloRscPosFaseDto.add(modeloRscPosFaseDto);
					log4j.debug("crear registro en monitor para una fase");
					
				}else {
					//En este caso se toman solo las fases activas del ModeloRscPosFase
					//Obtener el ModeloRscPosFase para el monitor
					lsModeloRscPosFaseDto=modeloRscPosDao.getIdsModeloRscPosFases(idModeloRscPos,true);
					log4j.debug("crear registro/s en monitor para una o más fases");
				}
			
				log4j.debug("<create> -> solo activas lsModeloRscPosFaseDto="+(
							lsModeloRscPosFaseDto == null ? null:lsModeloRscPosFaseDto.size())+
							" principal="+principal);
				
				//hay ModeloRscPosFase
				if(lsModeloRscPosFaseDto != null && lsModeloRscPosFaseDto.size() > 0){
					
					mapBtcMonitor = new HashMap<Long, Long>();
					
					sbNotfText= new StringBuilder();
					sbNotfText.append("<table align=\"center\"  border=0 cellspacing=0 cellpadding=0 >");
					sbNotfText.append("<tr><th>Nombre de la Fase</th><th>Per&iacute;odo</th></tr>");
					
					itModeloRscPosFaseDto=lsModeloRscPosFaseDto.iterator();
					while(itModeloRscPosFaseDto.hasNext()){
						
						modeloRscPosFaseDto=itModeloRscPosFaseDto.next();
						monitor=new Monitor();
						monitor.setPrincipal(principal);
						
						relacionEmpresaPersona=new RelacionEmpresaPersona();
						relacionEmpresaPersona.setIdRelacionEmpresaPersona(idRelacionEmpresaPersona);
						monitor.setRelacionEmpresaPersona(relacionEmpresaPersona);
						
						modeloRscPosFaseLca=new ModeloRscPosFase();
						modeloRscPosFaseLca.setIdModeloRscPosFase(modeloRscPosFaseDto.getIdModeloRscPosFase());
						monitor.setModeloRscPosFase(modeloRscPosFaseLca);
												
						//Se crea el monitor
						modeloRscPosFaseDto.setIdMonitor((long)monitorDao.create(monitor));						
						
						log4j.debug(" se insert monitor:"+modeloRscPosFaseDto.getIdMonitor());
						
						//bitacora tracking monitor
						btcTrackingDto=new BtcTrackingDto();
						btcTrackingMonPosDto=new BtcTrackingMonPosDto();
						btcTrackingMonPosDto.setIdMonitor(modeloRscPosFaseDto.getIdMonitor());
						btcTrackingMonPosDto.setIdModeloRscPosFase(modeloRscPosFaseDto.getIdModeloRscPosFase());
						btcTrackingMonPosDto.setPrincipal(trackingMonitorDto.getPrincipal());
						btcTrackingMonPosDto.setIdRelacionEmpresaPersona(idRelacionEmpresaPersona);
						
						btcTrackingDto.setBtcTrackingMonPosDto(btcTrackingMonPosDto);
						btcTrackingDto.setIdPosicion(idPosicion);
						btcTrackingDto.setIdRelacionEmpresaPersona(idREPPrincipal);
						btcTrackingDto.setIdTipoModuloBitacora(Constante.TIPO_MOD_BTC_MONITOR);
						btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_CREATE);
						btcTrackingDto.setIdBitacoraTrackRel(trackingMonitorDto.getIdBitacoraTrack());
						btcTrackingDto.setPorSistema(trackingMonitorDto.getIdBitacoraTrack() != null ? true:false);
						
						//se crea
						resp=bitacoraTrackingService.create(btcTrackingDto);
						log4j.debug("<create> bitacoraTracking monitor -> resp: "+ resp);
						lsMensajeDto=gson.fromJson(resp, new TypeToken<List<MensajeDto>>(){}.getType());
						
						//para bitacora tracking monitor
						mapBtcMonitor.put(modeloRscPosFaseDto.getIdMonitor(),
										  Long.parseLong(lsMensajeDto.get(0).getValue()));
						
						//para notificacion
						sbNotfText.append("<tr>");
						sbNotfText.append("<td>").append(modeloRscPosFaseDto.getNombre()).append("&nbsp;&nbsp;</td>");
						sbNotfText.append("<td>").append(modeloRscPosFaseDto.getFechaInicio() == null ? "--":
						DateUtily.date2String(modeloRscPosFaseDto.getFechaInicio(),Constante.DATE_FORMAT_JAVA))
						.append(" a ").
						append(modeloRscPosFaseDto.getFechaFin() == null ? "--":
							DateUtily.date2String(modeloRscPosFaseDto.getFechaFin(),Constante.DATE_FORMAT_JAVA))
						.append("</td>");
						sbNotfText.append("</tr>");						
					}		
					sbNotfText.append("</table>");					
					
					//Se analiza si existen candidatos asignados al monitor principal
					//para idModeloRscPos
					if(trackingMonitorDto.getIdModeloRscPos() != null) {
						
						//se investiga si hay almenos un registro del idModeloRscPosFase(activo=true) 
						//del idModeloRscPos en la tabla tracking_monitor
						count=trackingMonitorDao.countByMRSCPos(idModeloRscPos);
						
						log4j.debug("<create> -> hay registro count="+count);
						
						//no hay
						if(count.longValue() == 0) {
							lsTrackingMonitorCanidatosEsquemaDto= new ArrayList<ModeloRscPosFaseDto>();
							
							//se obtiene una lista de candidatos de la posicion
							lsCandidatos=candidatoDao.get(idPosicion);
							
							//se obtiene una lista de las fases del modeloRscPos activos
							lsModeloRscPosFase=modeloRscPosFaseDao.getByIdModeloRscPos(idModeloRscPos);
							
							log4j.debug("<create> -> lista de candidatos="+lsCandidatos.size()+
									" fases(ModeloRscPos)="+lsModeloRscPosFase.size());							
							
							//se obtiene iterator de candidatos
							itCandidatos=lsCandidatos.iterator();
							
							//Se recorren los candidatos
							while(itCandidatos.hasNext()) {								
								candidato=itCandidatos.next();
								
								itModeloRscPosFase=lsModeloRscPosFase.iterator();
								
								//Se recorren las fases
								//Por cada candidato se asigna la fase
								while(itModeloRscPosFase.hasNext()) {
									modeloRscPosFase=itModeloRscPosFase.next();
									tracMonCanEsqDto=new ModeloRscPosFaseDto();
									tracMonCanEsqDto.setIdRelacionEmpresaPersona(candidato.getIdRelacionEmpresaPersona());
									tracMonCanEsqDto.setIdCandidato(candidato.getIdCandidato());
									tracMonCanEsqDto.setIdPosibleCandidato(candidato.getIdPosibleCandidato());
									tracMonCanEsqDto.setIdModeloRscPosFase(modeloRscPosFase.getIdModeloRscPosFase());
									
									if(modeloRscPosFase.getModeloRscPosFase() != null) {
										tracMonCanEsqDto.setIdModeloRscPosFasePost(modeloRscPosFase.
																getModeloRscPosFase().getIdModeloRscPosFase());
									}
									lsTrackingMonitorCanidatosEsquemaDto.add(tracMonCanEsqDto);									
								}
							}
						}else {
							lsTrackingMonitorCanidatosEsquemaDto= trackingMonitorDao.get(idModeloRscPos,idPosicion);
						}
					}else {						
						//para idModeloRscPosFase												
						//se obtienen los candidatos  de la posicion
						lsCandidatos=candidatoDao.get(idPosicion);
						
						log4j.debug("<create> -> lsCandidatos="+lsCandidatos.size()+
									" orden="+modeloRscPosFase.getOrden()+
									" getActividad="+modeloRscPosFase.getActividad()+
									" idPosicion="+idPosicion);
						
						if(lsCandidatos.size() > 0) {
							
							lsTrackingMonitorCanidatosEsquemaDto= new ArrayList<ModeloRscPosFaseDto>();
							
							//se obtiene iterator de candidatos
							itCandidatos=lsCandidatos.iterator();
							
							//Se recorren los candidatos
							while(itCandidatos.hasNext()) {
								
								candidato=itCandidatos.next();
								log4j.debug("<create> -> candidato -> getIdRelacionEmpresaPersona="+
											candidato.getIdRelacionEmpresaPersona()+
											" idCandidato="+candidato.getIdCandidato()+
											" idPosibleCandidato="+candidato.getIdPosibleCandidato());
								
								tracMonCanEsqDto=new ModeloRscPosFaseDto();
								tracMonCanEsqDto.setIdRelacionEmpresaPersona(candidato.getIdRelacionEmpresaPersona());
								tracMonCanEsqDto.setIdCandidato(candidato.getIdCandidato());
								tracMonCanEsqDto.setIdPosibleCandidato(candidato.getIdPosibleCandidato());
								
								//se asigna el IdModeloRscPosFase del monitor
								tracMonCanEsqDto.setIdModeloRscPosFase(idModeloRscPosFase);
								
								//se obtiene idModeloRscPosFase del postulante
								idModeloRscPosFasePostu=modeloRscPosFaseDao.readByPosOrdAct(idPosicion, 
																	modeloRscPosFase.getOrden(), 
																	modeloRscPosFase.getActividad(), 
																	false).getIdModeloRscPosFase();
								
								log4j.debug("<create> -> idModeloRscPosFasePostulante="+idModeloRscPosFasePostu);
								
								tracMonCanEsqDto.setIdModeloRscPosFasePost(idModeloRscPosFasePostu);
								lsTrackingMonitorCanidatosEsquemaDto.add(tracMonCanEsqDto);
							}
						}
					}
					
					log4j.debug("candidatos lsTrackingMonitorEsquemaDto="+(
						lsTrackingMonitorCanidatosEsquemaDto == null ? null:
						lsTrackingMonitorCanidatosEsquemaDto.size()));
					
					if(lsTrackingMonitorCanidatosEsquemaDto != null && 
						lsTrackingMonitorCanidatosEsquemaDto.size() > 0){
						
						//Dado a que hay candidatos asignados al monitor principal,
						//se tienen que asignar al nuevo monitor
						//Se escribe en la tabla  TrackingMonitor y TrackingPostulante
						seAsignaCandidatosMonitor(monitorDto);						
					}						
				}else{
					//no existen ModeloRscPosFase activos o no existe el idModeloRscPos
					//mandar mensaje
					monitorDto.setMessages(UtilsTCE.getJsonMessageGson(
												monitorDto.getMessages(), 
							  					new MensajeDto("idModeloRscPos",
							  					idModeloRscPos.toString(),
							  					Mensaje.SERVICE_CODE_002,
							  					Mensaje.SERVICE_TYPE_ERROR,
							  					"El ModeloRscPos no existe o no hay Fases"+
							  					" activas para asignar Monitor")));
					continue;
				}																
			/*}else{
				//solo para fases especificados desde origen
				//Se iteran los fases
				itTracks=trackingMonitorDto.getTracks().iterator();
				while(itTracks.hasNext()){						
					modeloRscPosFaseDto=itTracks.next();
					
					monitor=new Monitor();
					monitor.setPrincipal(principal);
					
					relacionEmpresaPersona=new RelacionEmpresaPersona();
					relacionEmpresaPersona.setIdRelacionEmpresaPersona(idRelacionEmpresaPersona);
					monitor.setRelacionEmpresaPersona(relacionEmpresaPersona);
					
					modeloRscPosFase=new ModeloRscPosFase();
					modeloRscPosFase.setIdModeloRscPosFase(modeloRscPosFaseDto.getIdModeloRscPosFase());
					monitor.setModeloRscPosFase(modeloRscPosFase);
					
					//Se crea el monitor
					monitorDao.create(monitor);						
				}
			}	*/
			

			//Se le manda un mensaje a la persona_monitor
			//Se manta la notificación
			
			posicion=posicionDao.read(idPosicion);
			
			log4j.debug(" se notifica a la persona:"+trackingMonitorDto.getIdPersona()+
					    " para la posicion="+posicion.getNombre());
			
			jsonObject = new JSONObject();
			jsonObject.put("idPivote", trackingMonitorDto.getIdPersona());						
			jsonObject.put("claveEvento",UtilsNotification.CLAVE_EVENTO_ADICION_MONITOR);
			jsonObject.put("nombrePosicion",posicion.getNombre());
			jsonObject.put("rol",rol.getDescripcion());
			jsonObject.put("comentario",sbNotfText.toString());
			
			//Se pone la notificación
			notificacionProgramadaService.create(new NotificacionProgramadaDto(
												(String)jsonObject.get("claveEvento"),
												jsonObject.toString(),null));
		}		
		
		//si no hay mensajes
		if(monitorDto.getMessages() == null){
			monitorDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
		}
		log4j.debug(" getMessages="+monitorDto.getMessages());

		return monitorDto.getMessages();
	}
	
	/**
	 * La info del monitor principal se pone al monitor nuevo
	 * @param lsTrackingMonitorEsquemaDto, info del monitor principal
	 * @param lsModeloRscPosFaseDto, info del monitor nuevo y ModeloRscPosFase
	 * @throws Exception 
	 */
	private void seAsignaCandidatosMonitor(MonitorDto monitorDto) throws Exception{	
		
		//iteraor
		itTrackingMonitorCanidatosEsquemaDto=lsTrackingMonitorCanidatosEsquemaDto.iterator();
		
		//Se recorre la lista 
		while(itTrackingMonitorCanidatosEsquemaDto.hasNext()){
			trackingMonitorCanidatosEsquemaDto=itTrackingMonitorCanidatosEsquemaDto.next();
			
			//Se obtiene el idMonitor, de la lista
			itModeloRscPosFaseDto=lsModeloRscPosFaseDto.iterator();
			
			while(itModeloRscPosFaseDto.hasNext()){
				modeloRscPosFaseDto=itModeloRscPosFaseDto.next();
				log4j.debug("<seAsignaCandidatosMonitor>   idMonitor="+modeloRscPosFaseDto.getIdMonitor()+
					    " idTrackingPostulante="+trackingMonitorCanidatosEsquemaDto.getIdTrackingPostulante()+
					    " idCandidato="+trackingMonitorCanidatosEsquemaDto.getIdCandidato()+
					    " idPosibleCandidato="+trackingMonitorCanidatosEsquemaDto.getIdPosibleCandidato()+
					    " idModeloRscPosFasePostulante="+trackingMonitorCanidatosEsquemaDto.getIdModeloRscPosFasePost()+
					    " idModeloRscPosFasemonitor="+trackingMonitorCanidatosEsquemaDto.getIdModeloRscPosFase()+
					    " idRelacionEmpresaPersona="+trackingMonitorCanidatosEsquemaDto.getIdRelacionEmpresaPersona()+
					    " idEstadoTracking="+trackingMonitorCanidatosEsquemaDto.getIdEstadoTracking()+
					    " Comentario="+trackingMonitorCanidatosEsquemaDto.getComentario()+
					    " FechaInicio="+trackingMonitorCanidatosEsquemaDto.getFechaInicio()+
					    " FechaFin="+trackingMonitorCanidatosEsquemaDto.getFechaFin()+
					    " Orden="+modeloRscPosFaseDto.getOrden()+
					    " Actividad="+modeloRscPosFaseDto.getActividad());	
				
				log4j.debug("<seAsignaCandidatosMonitor>  IdModeloRscPosFase_MonNuevo="+
							modeloRscPosFaseDto.getIdModeloRscPosFase()+
						" IdModeloRscPosFase_MonCand="+ trackingMonitorCanidatosEsquemaDto.getIdModeloRscPosFase());
				
				//Si coinciden IdModeloRscPosFase
				if(modeloRscPosFaseDto.getIdModeloRscPosFase().longValue() == 
				   trackingMonitorCanidatosEsquemaDto.getIdModeloRscPosFase().longValue()){
					
					log4j.debug("<seAsignaCandidatosMonitor>  hacen macht");
					trackingMonitorDtoLcl=new TrackingMonitorDto();				
					
					/** Se crea un nuevo trackingPostulante */
					if(trackingMonitorCanidatosEsquemaDto.getIdTrackingPostulante() != null ||
					   trackingMonitorCanidatosEsquemaDto.getIdModeloRscPosFasePost() != null){
						
						trackingPostulanteDtoLcl=new TrackingPostulanteDto();
						trackingPostulanteDtoLcl.setIdEmpresaConf(monitorDto.getIdEmpresaConf());		
						trackingPostulanteDtoLcl.setIdPersona(monitorDto.getIdPersona());
						trackingPostulanteDtoLcl.setIdPosicion(idPosicion.toString());
						trackingPostulanteDtoLcl.setIdModeloRscPosFase(trackingMonitorCanidatosEsquemaDto.
																		getIdModeloRscPosFasePost().toString());
						trackingPostulanteDtoLcl.setIdBitacoraTrack(mapBtcMonitor.get(
														modeloRscPosFaseDto.getIdMonitor()));
						
						if(trackingMonitorCanidatosEsquemaDto.getIdCandidato() != null) {
							trackingPostulanteDtoLcl.setIdCandidato(trackingMonitorCanidatosEsquemaDto.
																			getIdCandidato().toString());
						}
						if(trackingMonitorCanidatosEsquemaDto.getIdPosibleCandidato() != null) {
							trackingPostulanteDtoLcl.setIdPosibleCandidato(trackingMonitorCanidatosEsquemaDto.
																		getIdPosibleCandidato().toString());
						}
						trackingPostulanteDtoLcl.setIdRelacionEmpresaPersona(trackingMonitorCanidatosEsquemaDto.
																			getIdRelacionEmpresaPersona().toString());
						
						trackingPostulanteDtoLcl.setIdEstadoTracking(Constante.EDO_TRACK_NO_CUMPLIDO.toString());
						
						//se crea trackingPostulante
						resp=trackingPostulanteService.create(trackingPostulanteDtoLcl);
						log4j.debug("<seAsignaCandidatosMonitor> trackingPostulante -> resp=");	
						lsMensajeDto=gson.fromJson(resp, new TypeToken<List<MensajeDto>>(){}.getType());
						log4j.debug("<seAsignaCandidatosMonitor>  lsMensajeDto="+
								(lsMensajeDto != null ? lsMensajeDto.size():null));	
						if(lsMensajeDto.get(0).getName() != null) {
							trackingMonitorDtoLcl.setIdTrackingPostulante(lsMensajeDto.get(0).getValue());
						}else {
							//Error
							throw new SystemTCEException(resp);
						}
					}
					
					/** Se crea trackingMonitor */
					trackingMonitorDtoLcl.setIdEmpresaConf(monitorDto.getIdEmpresaConf());		
					trackingMonitorDtoLcl.setIdPersona(monitorDto.getIdPersona());
					trackingMonitorDtoLcl.setIdMonitor(modeloRscPosFaseDto.getIdMonitor().toString());
					trackingMonitorDtoLcl.setIdRelacionEmpresaPersona(trackingMonitorCanidatosEsquemaDto.
																	getIdRelacionEmpresaPersona().toString());					
									
					//mapa donde se guarda key: idMonitor, value: idBitacoraTracking
					trackingMonitorDtoLcl.setIdBitacoraTrack(mapBtcMonitor.get(modeloRscPosFaseDto.getIdMonitor()));									
					
					//se crea
					trackingMonitorService.create(trackingMonitorDtoLcl);									
					break;
				}			
			}			
		}
	}

	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto trackingPostulanteDto
	 * @param trackingPostulanteDto
	 * @param funcion es el tipo de evento
	 * @throws Exception
	 */
	/*private void filtros(MonitorDto monitorDto, int funcion) throws Exception{
		boolean error=false;
		if(monitorDto != null){
			 if(funcion == Constante.F_CREATE ){
				 
				 if(monitorDto.getMonitores() == null || 
					monitorDto.getMonitores().size() == 0){
					 log4j.debug("<filtros> Monitores [] es requerido ");
					 error=true;
				 }else{
					//Se revisa si hay almenos un monitor y almenos un candidato
					 Iterator<TrackingMonitorDto> itMonitor=monitorDto.
							 getMonitores().iterator();
					 while(itMonitor.hasNext()){
						 TrackingMonitorDto monitor=itMonitor.next();
						
						 if(monitor.getIdPersona() == null  ){ //Persona a Asignar como monitor
								 error=true;
								 log4j.debug("<filtros> IdPersona es requerido ");
								 break;	
						 }
						 log4j.debug("<filtros> -> 1 error="+error);
						 
						 if(monitor.getIdModeloRscPos()  ==  null){
							  error=true;
							  log4j.debug("<filtros> idModeloRscPos es requerido ");
							 	break;
						 }						
						 log4j.debug("<filtros> -> 2 error="+error+
								 " getPrincipal="+monitor.getPrincipal());
						 if(monitor.getPrincipal()  ==  null ||
							(Integer.valueOf(monitor.getPrincipal()).intValue()  != 0 &&
							 Integer.valueOf(monitor.getPrincipal()).intValue()  != 1)){
							    error=true;
							    log4j.debug("<filtros> principal es requerido ");
							    log4j.debug("<filtros> -> 3 error="+error);
							 	break;
						 }else{
							 //si el monitor es principal entonces la lista fases debe ser nula
							 if(Integer.valueOf(monitor.getPrincipal()).intValue()  == 1
								&& monitor.getTracks() != null){
								  error=true;
								  log4j.debug("<filtros> la lista de Fases para principal debe ser nula ");
								 log4j.debug("<filtros> -> 4 error="+error);
								break;
							 }
						 }
						 log4j.debug("<filtros> -> 5 error="+error);
					 }
				 }
				 log4j.debug("<filtros> -> 6 error="+error);
			 }
			 if(funcion == Constante.F_GET ){
				 if(monitorDto.getMonitores() == null || 
							monitorDto.getMonitores().size() == 0){
							 log4j.debug("<filtros> Monitores [] es requerido ");
							 error=true;
				}else{
					 Iterator<TrackingMonitorDto> itMonitor=monitorDto.
							 getMonitores().iterator();
					 while(itMonitor.hasNext()){
						 TrackingMonitorDto monitor=itMonitor.next();
						
						 if(monitor.getIdMonitor() == null  ){ //Persona a Asignar como monitor
								 error=true;
								 log4j.debug("<filtros> idMonitor es requerido ");
								 break;	
						 }
					 }
				 }
			 }
			 //create, update y dataConf
			 if(!error && (monitorDto.getIdEmpresaConf() == null || 
				monitorDto.getIdPersona() == null ||
				monitorDto.getIdPosicion() == null)){
				 	error=true;
			 }
			
		}else{
			error=true;
		}
		 if(error){
			 monitorDto.setMessage(Mensaje.MSG_ERROR);
			 monitorDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 monitorDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
	}*/
}
