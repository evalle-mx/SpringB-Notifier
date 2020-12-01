package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tce.admin.service.BitacoraTrackingService;
import net.tce.admin.service.ModeloRscPosService;
import net.tce.assembler.TrackingAssembler;
import net.tce.dao.ModeloRscPosDao;
import net.tce.dao.PerfilPosicionDao;
import net.tce.dao.RelacionEmpresaPersonaDao;
import net.tce.dao.EmpresaConfDao;
import net.tce.dao.ModeloRscDao;
import net.tce.dao.ModeloRscPosFaseDao;
import net.tce.dto.BtcModeloRscDto;
import net.tce.dto.BtcTrackingDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.MonitorDto;
import net.tce.dto.ModeloRscDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.model.ModeloRscPos;
import net.tce.model.ModeloRscPosFase;
import net.tce.model.PerfilPosicion;
import net.tce.model.RelacionEmpresaPersona;
import net.tce.model.Rol;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("modeloRscPosService")
public class ModeloRscPosServiceImpl implements ModeloRscPosService {

	Logger log4j = Logger.getLogger( this.getClass());
	Boolean bMonitor, bPrincipal;
	Long idPerfilPosicion;
	MonitorDto monitorDto;
	TrackingMonitorDto trackingMonitorDto;
	List<TrackingMonitorDto> lsTrackingMonitorDto;
	Iterator<TrackingMonitorDto> itTrackingMonitorDto;
	List<ModeloRscPosFase> lsModeloRscPosFase;
	Iterator<ModeloRscPosFase> itModeloRscPosFase;
	ModeloRscPosFase modeloRscPosFase;
	String resp;
	ModeloRscPos modeloRscPos ;
	Long idModeloRsc;
	PerfilPosicion perPosicion;
	Long idModeloRscPos;
	Rol rol;
	ArrayList<String> orderby;
	HashMap<String, Object> currFilters;
	BtcTrackingDto btcTrackingDto;
	BtcModeloRscDto btcModeloRscDto;
	List<MensajeDto> lsMensajeDto;
	List<ModeloRscDto> lsModeloRscDto;
	long idRelacionEmpresaPersona;
	Long idPosicion;
	
	@Autowired
	private ModeloRscDao modeloRscDao;
	
	@Autowired
	private PerfilPosicionDao perfilPosicionDao;
	
	@Autowired
	private ModeloRscPosDao modeloRscPosDao;
	
	@Autowired
	private ModeloRscPosFaseDao modeloRscPosFaseDao;
	
	@Autowired
	private BitacoraTrackingService bitacoraTrackingService;
	
	@Autowired
	private EmpresaConfDao empresaConfDao;
	
	@Autowired
	private RelacionEmpresaPersonaDao relacionEmpresaPersonaDao;
	
	@Inject
	private TrackingAssembler trackingAssembler;
	
	@Inject
	Gson gson;

	/**
	 * Crea un registro de ModeloRscPos y simultaneamente crea registros en ModeloRscPosFases
	 * @param modeloRscDto
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String create(ModeloRscDto modeloRscDto) throws Exception {
		log4j.debug("<create> getIdEmpresaConf: " + modeloRscDto.getIdEmpresaConf() +
				" getIdPersona="+modeloRscDto.getIdPersona()+
				" getIdPosicion="+modeloRscDto.getIdPosicion()+
				" getIdRol="+modeloRscDto.getIdRol()+
				" getMonitor="+modeloRscDto.getMonitor()+
				" getPrincipal="+modeloRscDto.getPrincipal()+
				" getIdModeloRsc="+ modeloRscDto.getIdModeloRsc()+
				" getIdModeloRscPos="+modeloRscDto.getIdModeloRscPos()+
				" getNombre="+modeloRscDto.getNombre()+
				" getDescripcion="+modeloRscDto.getDescripcion());
		
		//Se aplican los filtros desde el AppTransactionalStructured
		
		//Se inicializan
		bPrincipal=null;
		bMonitor=null;
		
		idRelacionEmpresaPersona=getRelacionEmpresaPersona(
									modeloRscDto.getIdPersona(), 
									modeloRscDto.getIdEmpresaConf()).
									getIdRelacionEmpresaPersona();
		
		log4j.debug("<create> idRelacionEmpresaPersona: " + idRelacionEmpresaPersona);
		
		//Se verifica si es modeloRSC principal para la posición correspondiente
		if(modeloRscDto.getIdModeloRsc() != null){
			idPosicion=Long.parseLong(modeloRscDto.getIdPosicion());
						
			//Se investiga si ya existe un modelo principal para la posición correspondiente			
			idModeloRscPos=modeloRscPosDao.readModeloRscPosPrincipal(idPosicion);
			log4j.debug("existe ModeloRSC Principal? -> idModeloRscPos: "+ idModeloRscPos);
			
			//Ya existe modeloRscPos
			if(idModeloRscPos != null){
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
													new MensajeDto("idModeloRscPos",
													String.valueOf(idModeloRscPos),
													Mensaje.SERVICE_CODE_016,
													Mensaje.SERVICE_TYPE_ERROR,
													"Ya existe un ModeloRsc Principal para esta Posición")));
			}else{
				
				idModeloRsc = new Long(modeloRscDto.getIdModeloRsc());
				
				//Se obtienen las fases
				lsModeloRscDto= modeloRscDao.getByModeloRsc(idModeloRsc);
				
				log4j.debug("lsModeloRscDto: "+ (lsModeloRscDto != null ? lsModeloRscDto.size():null));
				
				//Se verifica si hay fases
				if(lsModeloRscDto != null && lsModeloRscDto.size()>0){
					
					//1.se crea ModeloRscPos
					createModeloRscPos(modeloRscDto);
					
					//2.Iterar el listado de fases para crear ModeloRscPosFase
					Iterator<ModeloRscDto> itModeloRscDto = lsModeloRscDto.iterator();
					//ModeloRscPosFase modeloRscPosFase;
					while(itModeloRscDto.hasNext()){
						modeloRscPosFase = trackingAssembler.getModeloRscPosFase(
															null, itModeloRscDto.next());
						modeloRscPos.setIdModeloRscPos(idModeloRscPos.longValue());
						modeloRscPosFase.setModeloRscPos(modeloRscPos);
						modeloRscPosFase.setActivo(true);
						
						//Se crean registros en la Bitacora Tracking Fase
						createModeloRscPosFase();
					}
					
					//3. Enviar mensaje SUCCESS 
					log4j.debug("<create> Se insertaron los elementos, agregando mensaje SUCCESS");
					modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
																new MensajeDto("idModeloRscPos",
																String.valueOf(idModeloRscPos),
																Mensaje.SERVICE_CODE_004,Mensaje.
																SERVICE_TYPE_INFORMATION,null)));
				}else{
					//NO hay fases en el modelo origen, por tanto no es valida
					log4j.fatal("<create> No hay fases en idModeloRsc= "+idModeloRsc );
					modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
																	new MensajeDto("idModeloRsc",
																	String.valueOf(idModeloRsc),
																	Mensaje.SERVICE_CODE_003,
																	Mensaje.SERVICE_TYPE_ERROR,
																	"No existe ModeloRSC o no tiene Fases")));
				}				
			}
		 }
		
		//Para modeloRsc Rol Posicion
		if(modeloRscDto.getMessages() == null && 
		   modeloRscDto.getIdModeloRscPos() != null){

			//se obtiene el idPosicion con el idModeloRscPos Principal
			idPosicion=modeloRscPosDao.getIdPosicion(Long.parseLong(
											modeloRscDto.getIdModeloRscPos()));
			
			log4j.debug("idPosicion: "+idPosicion);
			
			//existe ModeloRSC Posición Principal
			if(idPosicion != null){	
				
				//se pone el idPosicion
				modeloRscDto.setIdPosicion(idPosicion.toString());
				
				
				if(modeloRscDto.getMonitor() != null){
					bMonitor=new Boolean(modeloRscDto.getMonitor().equals(
														Constante.BOL_FALSE_VAL)?false:true);
				}
				if(modeloRscDto.getPrincipal() != null){
					bPrincipal=new Boolean(modeloRscDto.getPrincipal().equals(
														Constante.BOL_FALSE_VAL)?false:true);
				}
				
				log4j.debug("bMonitor: "+bMonitor+" bPrincipal="+bPrincipal);
				
				//Se verifica que no haya ModeloRsc Monitor Rol Principal
				if((bMonitor != null && bMonitor.booleanValue()) &&
				   (bPrincipal != null && bPrincipal.booleanValue())){
					
					idModeloRscPos=modeloRscPosDao.readIdModeloRscPosRol(idPosicion,bMonitor,bPrincipal);
					log4j.debug("Existe ModeloRSC Rol Principal? -> idModeloRscPos: "+ idModeloRscPos);
	
					//Ya existe modeloRscPos
					if(idModeloRscPos != null){
						modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
													new MensajeDto("idModeloRscPos",
													String.valueOf(idModeloRscPos),
													Mensaje.SERVICE_CODE_016,
													Mensaje.SERVICE_TYPE_ERROR,
													"Ya existe un ModeloRsc Monitor Rol Principal para esta Posición")));
					}
				}
				
				//ModeloRsc Rol Postulante
				if(modeloRscDto.getMessages() == null){
					
					//Se verifica si hay ModeloRsc Rol Postulante
					if((bMonitor != null && bMonitor.booleanValue() == false) &&
					   (bPrincipal == null)){
						idModeloRscPos=modeloRscPosDao.readIdModeloRscPosRol(idPosicion,bMonitor, bPrincipal);
						log4j.debug("Existe ModeloRSC Postulante Rol?  -> idModeloRscPos: "+ idModeloRscPos);
						
						//Ya existe modeloRscPos
						if(idModeloRscPos != null){
						modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
													new MensajeDto("idModeloRscPos",
													String.valueOf(idModeloRscPos),
													Mensaje.SERVICE_CODE_016,
													Mensaje.SERVICE_TYPE_ERROR,
													"Ya existe un ModeloRsc Postulante Rol para esta Posición")));
						}
					}
				}
				//no hay mensajes
				//Se persiste en ModeloRscPos y ModeloRscPosFases
				if(modeloRscDto.getMessages() == null){	
					
					//se investiga si existe el ModeloRscPos Rol
					idModeloRscPos=modeloRscPosDao.getByPosicion(idPosicion, 
													Long.parseLong(modeloRscDto.getIdRol()), true);
					
					log4j.debug("ModeloRscPos Rol-> ya existe idModeloRscPos: "+idModeloRscPos);

					//no existe ModeloRscPos Rol
					if(idModeloRscPos == null){	
						
						
						//Se asigna el idModeloRscPos del modelorsc principal
						idModeloRscPos = new Long(modeloRscDto.getIdModeloRscPos());		
		
						
						// Se obtienen las fases del ModeloRsc Principal
						currFilters  = new HashMap<String, Object>();
						orderby=new ArrayList<String>();
						currFilters.put("modeloRscPos.idModeloRscPos",idModeloRscPos);
						orderby.add(0,"orden");
						orderby.add(1,"actividad");
						currFilters.put(Constante.SQL_ORDERBY,orderby );
						lsModeloRscPosFase= modeloRscPosFaseDao.getByFilters(currFilters);
						
						log4j.debug("<create> ModeloRSC Principal -> lsModeloRscPosFase: "+
						(lsModeloRscPosFase != null ? lsModeloRscPosFase.size():null)+
						" idModeloRscPos="+idModeloRscPos );
		
						//Existen fases
						if(lsModeloRscPosFase != null &&
						   lsModeloRscPosFase.size() > 0){
							
							//1. Se crea ModeloRscPos
							createModeloRscPos(modeloRscDto);
							
							//2. se crean las fases del nuevo ModeloRscPos Rol
							itModeloRscPosFase=lsModeloRscPosFase.iterator();
							while(itModeloRscPosFase.hasNext()){						
								modeloRscPosFase=trackingAssembler.getModeloRscPosFase(
																	itModeloRscPosFase.next());								
								modeloRscPos=new ModeloRscPos();
								modeloRscPos.setIdModeloRscPos(idModeloRscPos);
								modeloRscPosFase.setModeloRscPos(modeloRscPos);
								modeloRscPosFase.setActivo(false);
								createModeloRscPosFase();
							}		
							
							//todo ok
							modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
																new MensajeDto("idModeloRscPos",
																String.valueOf(idModeloRscPos),
																Mensaje.SERVICE_CODE_004,
																Mensaje.SERVICE_TYPE_INFORMATION,null)));
													
						}else{
							//No existen fases
							log4j.fatal("<create> No hay fases en idModeloRscPos "+idModeloRscPos );
							modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
																			new MensajeDto("idModeloRscPos",
																			String.valueOf(idModeloRscPos),
																			Mensaje.SERVICE_CODE_003,
																			Mensaje.SERVICE_TYPE_ERROR,
																			"No hay Fases para el ModeloRSC Posición Principal")));					
						}	
					}else{
						modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
												new MensajeDto("idModeloRscPos",
												String.valueOf(idModeloRscPos),
												Mensaje.SERVICE_CODE_016,
												Mensaje.SERVICE_TYPE_ERROR,
												"Ya existe el ModeloRsc Rol para esta Posición")));
					}
				}			
			}else{
				//No existen fases
				log4j.fatal("<create> No hay fases en idModeloRscPos "+idModeloRscPos );
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
																new MensajeDto("idModeloRscPos",
																String.valueOf(idModeloRscPos),
																Mensaje.SERVICE_CODE_003,
																Mensaje.SERVICE_TYPE_ERROR,
																"No existe ModeloRSC Posición Principal")));	
			}
		}		
		return modeloRscDto.getMessages();
	}

	/**
	 * Se crean registros en ModeloRscPos y en la Bitacora Tracking 
	 * @param modeloRscDto
	 * @throws Exception 
	 */
	private void createModeloRscPos(ModeloRscDto modeloRscDto) throws Exception{
		perPosicion = new PerfilPosicion();
		
		//0.Si no existe idPerfilPosicion, se obtiene de idPerfil interno
		if(modeloRscDto.getIdPerfilPosicion() == null){
			log4j.debug("<createModeloRscPos> No existe idPerfilPosicion, se busca con idPosicion" );
			perPosicion.setIdPerfilPosicion(perfilPosicionDao.getInterno(idPosicion).getIdPerfilPosicion());												
		}else{
			log4j.debug("<createModeloRscPos> existe idPerfilPosicion" );
			idPerfilPosicion = new Long(modeloRscDto.getIdPerfilPosicion());						
			perPosicion.setIdPerfilPosicion( idPerfilPosicion);
		}
		
		//1.Crear modeloRscPos (Con IdPerfilPosicion)
		modeloRscPos= new ModeloRscPos();					
		modeloRscPos.setPerfilPosicion(perPosicion);
		if(modeloRscDto.getIdRol() != null){
			rol=new Rol();
			rol.setIdRol(Long.parseLong(modeloRscDto.getIdRol()));
			modeloRscPos.setRol(rol);
		}
		modeloRscPos.setNombre(modeloRscDto.getNombre());
		modeloRscPos.setDescripcion(modeloRscDto.getDescripcion());
		modeloRscPos.setMonitor(bMonitor);
		modeloRscPos.setPrincipal(bPrincipal);
		modeloRscPos.setActivo(true);
		
		idModeloRscPos = (Long)modeloRscPosDao.create(modeloRscPos);
		
		log4j.debug("<createModeloRscPos> idModeloRscPos: "+ idModeloRscPos);
		
		//se crea un registro en la bitacora
		btcTrackingDto=new BtcTrackingDto();
		btcModeloRscDto=new BtcModeloRscDto();
		btcModeloRscDto.setIdModeloRscPos(idModeloRscPos);
		btcModeloRscDto.setIdPerfilPosicion(perPosicion.getIdPerfilPosicion());
		btcModeloRscDto.setIdRol(rol != null ? rol.getIdRol():null);		
		btcModeloRscDto.setNombre(modeloRscPos.getNombre());
		btcModeloRscDto.setDescripcion(modeloRscPos.getDescripcion());		
		btcModeloRscDto.setMonitor(bMonitor != null ? (bMonitor ? Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL):null);
		btcModeloRscDto.setPrincipal(bPrincipal != null ? (bPrincipal ? Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL):null);
		btcModeloRscDto.setActivo(Constante.BOL_TRUE_VAL);
		
		btcTrackingDto.setBtcModeloRscDto(btcModeloRscDto);	
		btcTrackingDto.setIdPosicion(idPosicion);
		btcTrackingDto.setIdRelacionEmpresaPersona(idRelacionEmpresaPersona);
		btcTrackingDto.setIdTipoModuloBitacora(Constante.TIPO_MOD_BTC_MODRSC_POS);
		btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_CREATE);
		btcTrackingDto.setPorSistema(false);
		
		//se crea
		resp=bitacoraTrackingService.create(btcTrackingDto);
		log4j.debug("<createModeloRscPos> resp: "+ resp);
		lsMensajeDto=gson.fromJson(resp, new TypeToken<List<MensajeDto>>(){}.getType());
		log4j.debug("<createModeloRscPos> lsMensajeDto: "+ (lsMensajeDto != null ? lsMensajeDto.size():null));
	}
	
	/**
	 * Se crean registros en ModeloRscPosFase y en la Bitacora Tracking 
	 * @param modeloRscDto
	 * @throws Exception
	 */
	private void createModeloRscPosFase() throws Exception {
				
		//se crea un registro en la bitacora
		btcTrackingDto=new BtcTrackingDto();
		
		//se crea bitacora para modeloRSC y ModeloRSCFase
		btcModeloRscDto=new BtcModeloRscDto();
		btcModeloRscDto.setIdModeloRscPos(modeloRscPosFase.getModeloRscPos().getIdModeloRscPos());		
		btcModeloRscDto.setOrden(String.valueOf(modeloRscPosFase.getOrden()));
		btcModeloRscDto.setActividad(String.valueOf(modeloRscPosFase.getActividad()));
		btcModeloRscDto.setNombre(modeloRscPosFase.getNombre());
		btcModeloRscDto.setDescripcion(modeloRscPosFase.getDescripcion());
		btcModeloRscDto.setActivo(modeloRscPosFase.isActivo() ? 
									Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL);
		btcModeloRscDto.setSubirArchivo(modeloRscPosFase.isSubirArchivo() ? 
									Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL);
		btcModeloRscDto.setBajarArchivo(modeloRscPosFase.isBajarArchivo() ? 
									Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL);
		btcModeloRscDto.setEditarComenteario(modeloRscPosFase.isEditarComentario() ? 
									Constante.BOL_TRUE_VAL:Constante.BOL_FALSE_VAL);
		btcModeloRscDto.setFechaInicio(modeloRscPosFase.getFechaInicio());
		btcModeloRscDto.setFechaFin(modeloRscPosFase.getFechaFin());
		btcModeloRscDto.setIdModeloRscPosFase((long)modeloRscPosFaseDao.create(modeloRscPosFase));		
				
		btcTrackingDto.setBtcModeloRscDto(btcModeloRscDto);		
		btcTrackingDto.setIdRelacionEmpresaPersona(idRelacionEmpresaPersona);
		btcTrackingDto.setIdPosicion(idPosicion);
		btcTrackingDto.setIdTipoModuloBitacora(Constante.TIPO_MOD_BTC_MODRSC_POS_FASE);
		btcTrackingDto.setIdTipoEventoBitacora(Constante.TIPO_EVEN_BTC_CREATE);
		btcTrackingDto.setPorSistema(true);
		btcTrackingDto.setIdBitacoraTrackRel(Long.parseLong(lsMensajeDto.get(0).getValue()));
		log4j.debug("<createModeloRscPos> getIdModeloRscPos:"+ btcModeloRscDto.getIdModeloRscPos());
		//se crea
		bitacoraTrackingService.create(btcTrackingDto);
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
	
	
	/**
	 * Modifica registro de esquema_perfil_posicion y simultaneamente inserta registros en Tracking_esquema
	 * @param modeloRscDto
	 * @return
	 * @throws Exception 
	 */
	/*@Override
	public String update(ModeloRscDto modeloRscDto) throws Exception {
		
		//Se aplican los filtros en el AppTransactionalStructured

		//Esta opción no se esta usando: ModeloRscPosFase (estado)
		if(modeloRscDto.getIdModeloRscPosFase()!=null){
			log4j.debug("<update> es Update de ModeloRscPosFase (Estado) ");
			//Tracking_esquema
			ModeloRscPosFase trackEsquema = modeloRscPosFaseDao.read(Long.parseLong(
															modeloRscDto.getIdModeloRscPosFase()));
			if(trackEsquema!=null){
				trackEsquema = trackingAssembler.getModeloRscPosFase(trackEsquema, modeloRscDto); 
				modeloRscPosFaseDao.update(trackEsquema);//modeloRscDao.update(trackPlant);
				modeloRscDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
			}else{
				log4j.debug("<update> No existe tracking solicitado ");
				//NO existe registro, se agrega Mensaje de ERROR (002->No existe)
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), 
																new MensajeDto(null,null,
																Mensaje.SERVICE_CODE_002,
																Mensaje.SERVICE_TYPE_ERROR,
																Mensaje.MSG_ERROR)));
			}
		}else{
			//Esta opción se esta usando
			//Se borran todos los tracking_estados del idModeloRscPos y se ponen los nuevos
			log4j.debug("<update>  Update opcion 2");
			
			Long idModeloRscPos = new Long(modeloRscDto.getIdModeloRscPos());	
			ModeloRscPos modeloRscPos = modeloRscPosDao.read(idModeloRscPos);
			log4j.debug("<update>  modeloRscPos="+modeloRscPos);
			if(modeloRscPos!=null){
				
				modeloRscPos = trackingAssembler.getModeloRscPos(modeloRscDto, modeloRscPos);
				log4j.debug("<update> Se actualiza el objeto ESQUEMA_PERFIL "+modeloRscPos);
				
				//Se actualiza 
				modeloRscPosDao.update(modeloRscPos);
				
				//actualizar o purgar los Estados del Tracking (en caso de que sea un FULL_UPDATE)
				if(modeloRscDto.getTracking()!=null && modeloRscDto.getTracking().size()>0){
					
					log4j.debug("<update>  isMonitor_1="+modeloRscPos.getMonitor());
					
					//Revisamos si es monitor
					if(modeloRscPos.getMonitor() != null &&
					   modeloRscPos.getMonitor().booleanValue()){
						
						//Traer los monitores referenciados a es idModeloRscPos
						lsTrackingMonitorDto=monitorDao.getMonitorPersona(idModeloRscPos);
						
						log4j.debug("<update>  es Monitor_1 -> lsTrackingMonitorDto="+
									((lsTrackingMonitorDto != null && lsTrackingMonitorDto.size() > 0) ?
									lsTrackingMonitorDto.size():null));
						
						//Borrar los registros en la tabla monitor
						monitorDao.deleteByidModeloRscPos(idModeloRscPos);
						log4j.debug("<update>  es Monitor_2 -> se borran los registros");
					}
					
					//2. BORRAR los registros relacionados
					log4j.debug("<update> Depurando los trackings relacionados a EsquemaPerfilPos");
					modeloRscPosFaseDao.deleteByidModeloRscPos(idModeloRscPos);
					
					Iterator<ModeloRscDto> itEstado = modeloRscDto.getTracking().iterator();
					ModeloRscDto tmpDto;
					Long idModeloRscPosFase;
					while(itEstado.hasNext()){
						tmpDto = itEstado.next();
						ModeloRscPosFase modeloRscPosFase = trackingAssembler.getModeloRscPosFase(null, tmpDto);
						//TODO validar que fechas cumplan con patron de fecha
						modeloRscPosFase.setModeloRscPos(modeloRscPos);
						log4j.debug("<update> nombre: "+ modeloRscPosFase.getNombre() );
						log4j.debug("<update> subirArchivo: "+ modeloRscPosFase.getSubirArchivo() );
						log4j.debug("<update> bajarArchivo: "+ modeloRscPosFase.getBajarArchivo() );
						log4j.debug("<update> editComment: "+ modeloRscPosFase.getEditarComentario() );
						log4j.debug("<update> fechaInicio: "+ modeloRscPosFase.getFechaInicio() );
						log4j.debug("<update> fechaFin: "+ modeloRscPosFase.getFechaFin() );
						
						//relacion con Esquema reflejo
						if(tmpDto.getIdModeloRscPosFaseRel()!=null){
							log4j.debug("<update> Se agrega relacion con otro Estado");
							ModeloRscPosFase trackRel = new ModeloRscPosFase();
							trackRel.setIdModeloRscPosFase(Long.parseLong(tmpDto.getIdModeloRscPosFaseRel()));
							modeloRscPosFase.setModeloRscPosFase(trackRel);
						}
						
						idModeloRscPosFase =(Long) modeloRscPosFaseDao.create(modeloRscPosFase);
						log4j.debug("<update> idModeloRscPosFase: " + idModeloRscPosFase );
					}
					
					log4j.debug("<update>  isMonitor_2="+modeloRscPos.getMonitor());
					
					//Si es monitor se escribe en la tabla 
					if((modeloRscPos.getMonitor() != null &&
						modeloRscPos.getMonitor().booleanValue() )&&
					   (lsTrackingMonitorDto != null && lsTrackingMonitorDto.size() > 0)){						
						itTrackingMonitorDto=lsTrackingMonitorDto.iterator();
						
						//Se crea el monitor
						lsTrackingMonitorDto=new ArrayList<TrackingMonitorDto>();
						
						 //se pone a la persona dueña de la posición 
						 monitorDto=new MonitorDto();
						 monitorDto.setIdEmpresaConf(modeloRscDto.getIdEmpresaConf());
					   	 monitorDto.setIdPersona(modeloRscDto.getIdPersona());
					   	 monitorDto.setIdPosicion(modeloRscDto.getIdPosicion());
						
					    log4j.debug("<update> dueño de la posicio ->  getIdPersona: " + modeloRscDto.getIdPersona()+
					   				" getIdPosicion="+modeloRscDto.getIdPosicion());
						
						while(itTrackingMonitorDto.hasNext()){
							trackingMonitorDto=itTrackingMonitorDto.next();
							trackingMonitorDto.setIdModeloRscPos(
												modeloRscDto.getIdModeloRscPos());
							log4j.debug("<update> monitor -> idPersona:: " + trackingMonitorDto.getIdPersona()+
										" getPrincipal="+trackingMonitorDto.getPrincipal());
							
						    //Se suma el monitor
						    lsTrackingMonitorDto.add(trackingMonitorDto);  
						}
						
						 monitorDto.setMonitores(lsTrackingMonitorDto);
					     
					     //Se llama al servicio de create monitor
					     resp= monitorService.create(monitorDto);
					     
					     log4j.debug("<update> Se crea monitor -> resp="+resp);
					     	 
					     if(!resp.equals(Mensaje.SERVICE_MSG_OK_JSON)){
					    	 modeloRscDto.setMessages(resp);
					     }		
					}
				}
				 log4j.debug("<update> salir-> getMessages="+modeloRscDto.getMessages());
				
				//todo bien
				if(modeloRscDto.getMessages() == null){
					modeloRscDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON); 
				 }
				
			}else{
				log4j.debug("<update> No existe Esquema solicitado ");
				//NO existe registro, se agrega Mensaje de ERROR (002->No existe)
				modeloRscDto.setMessages(UtilsTCE.getJsonMessageGson(modeloRscDto.getMessages(), new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_ERROR,
						Mensaje.MSG_ERROR)));
			}
		}
		
		return modeloRscDto.getMessages();
	}*/	
	
	
	
}
