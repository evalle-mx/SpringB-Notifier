package net.tce.admin.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.tce.admin.service.AdminService;
import net.tce.admin.service.BitacoraTrackingService;
import net.tce.admin.service.EmpresaInterfaseService;
import net.tce.admin.service.TrackingMonitorService;
import net.tce.app.exception.SystemTCEException;
import net.tce.dao.ModeloRscPosFaseDao;
import net.tce.dao.PosicionDao;
import net.tce.dao.TrackingMonitorDao;
import net.tce.dto.BtcTrackingDto;
import net.tce.dto.BtcTrackingMonPosDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.model.EstadoTracking;
import net.tce.model.ModeloRscPos;
import net.tce.model.ModeloRscPosFase;
import net.tce.model.Posicion;
import net.tce.model.TrackingMonitor;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import net.tce.util.Validador;

@Transactional
@Service("trackingMonitorService")
public class TrackingMonitorServiceImpl implements TrackingMonitorService {

	final Logger log4j = Logger.getLogger( this.getClass());
	boolean todoCorrecto;
	Date fechaIni,fechaFin;
	String sMensajeError;
	long idRepMonitor;
	TrackingMonitor trackingMonitor;
	ModeloRscPosFase modeloRscPosFase;
	Long idTrackingMonitor;	
	BtcTrackingDto btcTrackingDto;
	BtcTrackingMonPosDto btcTrackingMonPosDto;
	String resp;
	String idEmpresaConf;
	TrackingMonitorDto trackingMonitorDtoeDataConf;
	EstadoTracking estadoTracking;
	ModeloRscPos modeloRscPos;
	List<Long> lsIdEstadoTracking;
	Long idEstadoTracking;
	Posicion posicion;
	Long idRepPos;
	
	@Autowired
	private EmpresaInterfaseService empresaInterfaseService;
	
	@Autowired
	private BitacoraTrackingService bitacoraTrackingService;
	
	@Autowired 
	private AdminService adminService;
	
	
	@Autowired
	private PosicionDao posicionDao;
	
	@Autowired
	private TrackingMonitorDao trackingMonitorDao;
	
	@Autowired
	private ModeloRscPosFaseDao modeloRscPosFaseDao;
	
	@Inject
	private ConversionService converter;
	
	
	/**
	 * Se crea  un trackingmonitor
	 */
	@Override
	public String create(TrackingMonitorDto trackingMonitorDto) throws Exception {
		log4j.debug("<create>  idTrackingPostulante="+trackingMonitorDto.getIdTrackingPostulante()+
				" idEmpresaConf="+trackingMonitorDto.getIdEmpresaConf()+
				" idPersona="+trackingMonitorDto.getIdPersona()+
				" idMonitor="+trackingMonitorDto.getIdMonitor()+
				" idRelacionEmpresaPersona="+trackingMonitorDto.getIdRelacionEmpresaPersona()+
				" comentario="+trackingMonitorDto.getComentario()+
				" fechaInicio="+trackingMonitorDto.getFechaInicio()+
				" fechaFin="+trackingMonitorDto.getFechaFin()+
				" idBitacoraTrack="+trackingMonitorDto.getIdBitacoraTrack());
		
		filtros(trackingMonitorDto, Constante.F_CREATE);
		log4j.debug("<create> getCode="+trackingMonitorDto.getCode());
		if(trackingMonitorDto.getCode()==null &&
		   trackingMonitorDto.getMessages() == null){
			todoCorrecto=true;
			fechaIni=null;
			fechaFin=null;
			sMensajeError=null;
			
			idRepPos=Long.parseLong(trackingMonitorDto.getIdRelacionEmpresaPersona());
						
			//hay fechas
			if(trackingMonitorDto.getFechaInicio() != null &&
			   trackingMonitorDto.getFechaFin() != null) {

				log4j.debug("<create> en analisis de fechas");
				
				// se verifica si la fechaInicio tiene formato  correcto
				if(!DateUtily.isValidDate(trackingMonitorDto.getFechaInicio(), 
													Constante.DATE_FORMAT_JAVA)) {
					todoCorrecto=false;
					log4j.debug("<create> falla formato getFechaInicio="+trackingMonitorDto.getFechaInicio());
					sMensajeError="La fecha inicial de la Agenda debe tener formato correcto";
					
				}else {
					fechaIni=DateUtily.string2Date(trackingMonitorDto.getFechaInicio(), 
																Constante.DATE_FORMAT_JAVA);
					
					// se verifica si la fechaFin tiene formato  correcto
					if(!DateUtily.isValidDate(trackingMonitorDto.getFechaFin(), 
														Constante.DATE_FORMAT_JAVA)) {
						todoCorrecto=false;
						log4j.debug("<create> falla formato getFechaFin="+trackingMonitorDto.getFechaFin());
						sMensajeError="La fecha final de la Agenda debe tener formato correcto";
						
					}else {
						fechaFin=DateUtily.string2Date(trackingMonitorDto.getFechaFin(), 
																Constante.DATE_FORMAT_JAVA);
					}
				}
				log4j.debug("<create>1  todoCorrecto="+todoCorrecto);
				
				//Comparación de fecha
				if(todoCorrecto) {
						
					//la fecha Inicial debe ser menor a la fecha Final
					if(todoCorrecto && DateUtily.compareDt1Dt2(fechaIni, fechaFin) == 1) {
						todoCorrecto=false;
						sMensajeError="La fecha Inicial de la Agenda debe ser menor a la fecha Final";
					}
				}
			}else if(trackingMonitorDto.getFechaInicio() == null &&
					   trackingMonitorDto.getFechaFin() != null){
				todoCorrecto=false;
				sMensajeError="Fecha Inicial debe tener valor si Fecha Final lo tiene";	
				
			}else if(trackingMonitorDto.getFechaInicio() != null &&
					   trackingMonitorDto.getFechaFin() == null){
				todoCorrecto=false;
				sMensajeError="Fecha Final debe tener valor si Fecha Inicial lo tiene";	
			}
			log4j.debug("<create>2  todoCorrecto="+todoCorrecto);
			
			//sale, seguimos
			if(todoCorrecto) {
				estadoTracking=new EstadoTracking();
				
				//se obtiene IdRelacionEmpresaPersona
				idRepMonitor=Long.parseLong(trackingMonitorDto.getIdRelacionEmpresaPersona());
				log4j.debug("<create>  idRepMonitor="+idRepMonitor);
				
				//setters
				trackingMonitor=converter.convert(trackingMonitorDto, TrackingMonitor.class);
				log4j.debug("<create>  trackingMonitor="+trackingMonitor);
								
				//Se obtiene modeloRscPosFase para el estatus oculto y EstadoTracking
				modeloRscPosFase=modeloRscPosFaseDao.readByMonitor(Long.parseLong(
													trackingMonitorDto.getIdMonitor()));
				log4j.debug("<create>  modeloRscPosFase="+modeloRscPosFase);
				
				//Se obtiene modeloRscPos
				modeloRscPos=modeloRscPosFase.getModeloRscPos();
				log4j.debug("<create>  modeloRscPos="+modeloRscPos);
				
				//Se obtiene Posicion
				posicion=modeloRscPos.getPerfilPosicion().getPosicion();
				log4j.debug("<create>  posicion="+posicion);
								
				//se analiza si es el primer tracking
				if((modeloRscPosFase.getOrden() == 
						Constante.NUM_ORDEN_INICIAL.shortValue()  && 
						modeloRscPosFase.getActividad() == 
				   		Constante.NUM_ACTIVIDAD_INICIAL.shortValue() ) ) {
					trackingMonitor.setOculto(true);
					estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_EN_CURSO);
						
				}else {
					trackingMonitor.setOculto(false);
					
					//se investiga el estatus del track posterior
					
					// verificar si hay actividad posterior para el mismo orden						
					lsIdEstadoTracking= trackingMonitorDao.getActUpByPosRepOrder(posicion.getIdPosicion(),
																			modeloRscPosFase.getOrden(),
																			modeloRscPosFase.getActividad(),
																			idRepPos);
					log4j.debug("<create> lsIdEstadoTracking(actividad posterior)="+ 
															lsIdEstadoTracking.size());
				
					//se verifica si hay actividad posterior
					if(lsIdEstadoTracking.size() > 0) {
						idEstadoTracking=lsIdEstadoTracking.get(0);
					}else {
						//si no hay actividad posterior, entonces se verifica si hay orden posterior
						lsIdEstadoTracking=trackingMonitorDao.getOrderUpByPosRepOrder(
																			posicion.getIdPosicion(),
																			modeloRscPosFase.getOrden(),
																			idRepPos);
						
						log4j.debug("<create> lsIdEstadoTracking(orden posterior)="+
											lsIdEstadoTracking.size());
						
						//se verifica si hay orden posterior
						if(lsIdEstadoTracking.size() > 0) {
							idEstadoTracking=	lsIdEstadoTracking.get(0);
						}
					}
					log4j.debug("<create> del track posterior idEstadoTracking="+idEstadoTracking);
					
					//Si hay track posterior 
					if(idEstadoTracking != null) {
						
						//Para el estatus 'No cumplido'
						if(idEstadoTracking.longValue() == Constante.EDO_TRACK_NO_CUMPLIDO.longValue()) {
							estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_NO_CUMPLIDO);
						}else {
							//Para los estatus: 'En curso', 'Cumplido' y 'Rechazado'							
							trackingMonitor.setComentario(new StringBuilder(Constante.REASIG_TRACK_MON_ROL).
													append(modeloRscPos.getRol().getDescripcion()).toString());
							estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_CUMPLIDO);
						}
					}else {
						estadoTracking.setIdEstadoTracking(Constante.EDO_TRACK_NO_CUMPLIDO);
					}
				}
				trackingMonitor.setEstadoTracking(estadoTracking);
				
				//se persiste
				idTrackingMonitor=(long)trackingMonitorDao.create(trackingMonitor);
				
				log4j.debug("<create>  se crea idTrackingMonitor="+idTrackingMonitor);
				
				
				
				//create respuesta
				trackingMonitorDto.setMessages(UtilsTCE.getJsonMessageGson(null,new 
														MensajeDto("idTrackingMonitor",
														String.valueOf(idTrackingMonitor),
														Mensaje.SERVICE_CODE_004,
														Mensaje.SERVICE_TYPE_INFORMATION,
														null)));				
				/**  BitacoraTracking */			
				//bitacora tracking monitor
				btcTrackingDto=new BtcTrackingDto();
				btcTrackingMonPosDto=new BtcTrackingMonPosDto();
				btcTrackingMonPosDto.setIdMonitor(trackingMonitor.getMonitor().getIdMonitor());
				btcTrackingMonPosDto.setIdTrackingMonitor(idTrackingMonitor);
				btcTrackingMonPosDto.setIdTrackingPostulante(trackingMonitor.getTrackingPostulante() != null ?
															 trackingMonitor.getTrackingPostulante().
															 getIdTrackingPostulante():null);
				btcTrackingMonPosDto.setIdEstadoTracking(trackingMonitor.getEstadoTracking().
																		getIdEstadoTracking());
				btcTrackingMonPosDto.setIdRelacionEmpresaPersona(idRepMonitor);
				btcTrackingMonPosDto.setEnGrupo(trackingMonitor.isEnGrupo() ? 
														Constante.BOL_TRUE_VAL:
														Constante.BOL_FALSE_VAL);
				btcTrackingMonPosDto.setNotifEnviada(trackingMonitor.isNotificacionEnviada() ?						 
														Constante.BOL_TRUE_VAL:
														Constante.BOL_FALSE_VAL);
				btcTrackingMonPosDto.setComentario(trackingMonitor.getComentario());
												
				btcTrackingDto.setBtcTrackingMonPosDto(btcTrackingMonPosDto);
				btcTrackingDto.setIdBitacoraTrackRel(trackingMonitorDto.getIdBitacoraTrack());
				btcTrackingDto.setIdPosicion(posicionDao.getByMonitor(trackingMonitor.getMonitor().
																					getIdMonitor()));
				btcTrackingDto.setIdRelacionEmpresaPersona(adminService.getRelacionEmpresaPersona(
															trackingMonitorDto.getIdPersona(), 
											   				trackingMonitorDto.getIdEmpresaConf()).
											   				getIdRelacionEmpresaPersona());
				btcTrackingDto.setIdTipoModuloBitacora(Constante.TIPO_MOD_BTC_TRACK_MON);
				btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_CREATE);
				btcTrackingDto.setPorSistema(trackingMonitorDto.getIdBitacoraTrack() != null ?
											true:false);
				
				//se crea
				resp=bitacoraTrackingService.create(btcTrackingDto);
				log4j.debug("<create> bitacoraTrackingMonitor -> resp: "+ resp);			
				
			}else {
				trackingMonitorDto.setMessages(UtilsTCE.getJsonMessageGson(
											null, 
											new MensajeDto(null,null,
											Mensaje.SERVICE_CODE_006,
											Mensaje.SERVICE_TYPE_ERROR,
											sMensajeError)));
			}
		}else{
			log4j.fatal("<update> Existió un error en filtros ");
			if(trackingMonitorDto.getMessages() == null){
				trackingMonitorDto.setMessages(UtilsTCE.getJsonMessageGson(trackingMonitorDto.getMessages(), 
																				new MensajeDto(null,null,
																				Mensaje.SERVICE_CODE_006,
																				Mensaje.SERVICE_TYPE_FATAL,
																				Mensaje.MSG_ERROR)));
			}
		}
		log4j.debug("<update> getMessages="+trackingMonitorDto.getMessages());
		return trackingMonitorDto.getMessages();
	}
	
	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto Dto
	 * @param trackDto
	 * @param funcion, indica el tipo de accion
	 * @throws Exception 
	 */
	private void filtros(TrackingMonitorDto trackDto, int funcion) throws Exception{
		boolean error=false;
		if(trackDto != null){
			 if(trackDto.getIdEmpresaConf() == null || 
				trackDto.getIdPersona() == null){
				 log4j.debug("<filtros> idEmpresaConf o IdPersona son null");
				 error=true;
			 }else{
				 if(funcion == Constante.F_CREATE){
					 if(trackDto.getIdRelacionEmpresaPersona() == null ||
						trackDto.getIdMonitor() == null){
						 log4j.debug("<filtros> idRelacionEmpresaPersona o IdMonitor es Requerido");
						 error=true;
					 	}
				}
			 }
		}else{
			log4j.debug("<filtros> trackDto es null ");
			error=true;
		}
		
		log4j.debug("<filtros>   error="+error);
		 if(error){
			 trackDto.setMessage(Mensaje.MSG_ERROR);
			 trackDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 trackDto.setCode(Mensaje.SERVICE_CODE_006);
		 }else{
			//se aplican filtros Dataconf
				filtrosDataConf(trackDto);	
		 }
	}
	
	/**
	 * Regresa un objeto dataConf referida al servicio trackingMonitorDto
	 * @param trackingMonitorDto, objeto data-conf correspondiente
	 * @return TrackingPostulanteDto
	 * @throws Exception 
	 */
	private TrackingMonitorDto dataConf(TrackingMonitorDto trackingMonitorDto) throws Exception{
		//Se obtiene el objeto-Dataconf
		idEmpresaConf=trackingMonitorDto.getIdEmpresaConf();
		
		//Se obtiene los data-info
		trackingMonitorDto=(TrackingMonitorDto)empresaInterfaseService.dataConf(idEmpresaConf,
																				"TrackingMonitor",
																				new TrackingMonitorDto());
		log4j.debug("<filtros_dataConf> trackingMonitorDto="+trackingMonitorDto);
		if(trackingMonitorDto == null){
			trackingMonitorDto=new TrackingMonitorDto();
			trackingMonitorDto.setMessage(Mensaje.SERVICE_MSG_ERROR_DATACONF);
			trackingMonitorDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			trackingMonitorDto.setCode(Mensaje.SERVICE_CODE_002);
		}
		return trackingMonitorDto;
	}
	
	/**
	 * Se aplica los filtros de Dataconf para los valores de las propiedades del objeto modeloRscDto
	 * @param modeloRscDto, objeto a filtrar correspondiente
	 * @return modeloRscDto
	 * @throws Exception 
	 */
	private void filtrosDataConf(TrackingMonitorDto trackingMonitorDto)throws Exception{
		trackingMonitorDtoeDataConf=new TrackingMonitorDto();
		trackingMonitorDtoeDataConf.setIdEmpresaConf(trackingMonitorDto.getIdEmpresaConf());
		trackingMonitorDtoeDataConf=dataConf(trackingMonitorDtoeDataConf);
		//si no hay error
		if(trackingMonitorDtoeDataConf.getCode() == null){
			//se validan las propiedades del objeto vs los valores (restricciones) de empresaParametro 
			trackingMonitorDto=(TrackingMonitorDto)Validador.filtrosDataConf(
									trackingMonitorDtoeDataConf,trackingMonitorDto);		
		}else{
			trackingMonitorDto=trackingMonitorDtoeDataConf;
		}
	}


}
