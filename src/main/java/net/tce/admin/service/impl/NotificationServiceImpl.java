package net.tce.admin.service.impl;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import net.tce.admin.service.EmpresaInterfaseService;
import net.tce.admin.service.JavaMailManagerService;
import net.tce.admin.service.NotificationService;
import net.tce.app.exception.SystemTCEException;
import net.tce.dao.CandidatoDao;
import net.tce.dao.PosibleCandidatoDao;
import net.tce.dao.TipoEventoDao;
import net.tce.dao.NotificacionDao;
import net.tce.dao.PersonaDao;
import net.tce.dto.CandidatoDto;
import net.tce.dto.CorreoTceDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.MensajeDto;
import net.tce.dto.NotificationDto;
import net.tce.model.Notificacion;
import net.tce.model.Persona;
import net.tce.model.TipoEvento;
import net.tce.model.TipoEventoMecanismo;
import net.tce.model.TipoEventoReceptor;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsNotification;
import net.tce.util.UtilsSeguridad;
import net.tce.util.UtilsTCE;
import net.tce.util.Validador;

/**
 * Clase donde se aplica las politicas de negocio del servicio de notificaciones
 *
 */
@Transactional
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService{

	private  @Value("${uri_appui}")	String uri_appui;
	private  @Value("${files_url_repository}") String file_uri;
	private  @Value("${path_files}")String path_files;	
	private  @Value("${files_repository_local}")boolean files_repository_local;
	private  @Value("${mail_path}") String mail_path;
	private  @Value("${mail_path_selex1_html}")String selex1_html;
	

	StringBuilder stPlantilla;
	StringBuilder stCadena;
	StringBuilder stIdToken;
	//String idToken;
	List<NotificationDto> lsNotificationDto;
	
	@Autowired
	private CandidatoDao candidatoDao;
	
	@Autowired
	private JavaMailManagerService javaMailManagerService;
	
	@Autowired
	private TipoEventoDao tipoEventoDao;

	@Autowired
	private PersonaDao personaDao;
	

	@Autowired
	private NotificacionDao notificacionDao;
	
	@Autowired
	private PosibleCandidatoDao posibleCandidatoDao;
	
	@Autowired
	private EmpresaInterfaseService empresaInterfaseService;
	
	
	Logger log4j = Logger.getLogger( this.getClass());
	
	/**
	 * Crea una notificacion basado en la parametrización correspondiente de tipo evento, receptores y mecanismos
	 * @param NotificationDto notificationDto
	 * @return Object result
	 * @throws Exception 
	 */		
	public String create(NotificationDto notificationDto) throws Exception {
		//log4j.debug("<create> Inicio Notificacion \n " + notificationDto.getClaveEvento());
		log4j.debug("<create> Inicio.  NotificacionDto: \n " + notificationDto);
		String result = Mensaje.SERVICE_MSG_OK_JSON; //Mensaje por defecto a menos que ocurra un error
		//Long idNotificacion;
		
		notificationDto = filtros(notificationDto, Constante.F_CREATE);
		if(notificationDto.getCode() == null){
			
			// Busca información del tipo de evento por CLAVE [tipoEvento.clave = '']
			TipoEvento tipoEvento = null;
			List<TipoEvento> lsTipoEvento = new ArrayList<TipoEvento>();
			HashMap<String, Object> currFilters  = new HashMap<String, Object>();
			currFilters.put("claveEvento",notificationDto.getClaveEvento());
			lsTipoEvento = tipoEventoDao.getByFilters(currFilters);
			if(!lsTipoEvento.isEmpty()){
				tipoEvento = lsTipoEvento.get(0);
			}
			if(tipoEvento != null && tipoEvento.getTipoEventoMecanismos().size()>0){
				//Existe el tipo_evento con CLAVE_EVENTO solicitado
				boolean resp=complementataNotificacion(notificationDto);
				
				if(resp){
					// El evento existe
					log4j.debug("<create> IdEmisor=" +notificationDto.getIdEmisor()+
							" TipoEmisor="+notificationDto.getTipoEmisor()+
							" IdPersonaReceptor="+notificationDto.getIdPersonaReceptor()+
							" IdPivote="+notificationDto.getIdPivote()+
							" NombreEmisor="+notificationDto.getNombreEmisor()+
							" NombreEmpresa="+notificationDto.getNombreEmpresa()+
							" NombrePosicion="+notificationDto.getNombrePosicion()+
							" IdCandidato="+notificationDto.getIdCandidato()+
							" ClaveEvento="+tipoEvento.getClaveEvento()+
							//" getMensajeLayout="+tipoEvento.getTexto()+
							" Comentario="+notificationDto.getComentario()+
							" HostAddress:"+notificationDto.getHostAddress()+
							" HostName:"+notificationDto.getHostName());
	
					// Obtiene los mecanismos asociados al evento, y los almacena en dos listas para mecanismos inmediatos y no inmediatos, 
					// Los valores de cada lista se encuentran separados por comas
//					log4j.debug("<create> EventoMecanismos().size :" + tipoEvento.getTipoEventoMecanismos().size());
					StringBuilder resultBuilderNotImmediate = new StringBuilder();
					StringBuilder resultBuilderImmediate = new StringBuilder();
					String listaMecanismoPasivo = null; 
					String listaMecanismoInmediato = null; 
					Iterator<TipoEventoMecanismo> itLsTipoEventoMecanismo = tipoEvento.getTipoEventoMecanismos().iterator();
					while(itLsTipoEventoMecanismo.hasNext()){
						TipoEventoMecanismo tipoEventoMecanismo = itLsTipoEventoMecanismo.next();
						log4j.debug("<create> IdMecanismo :" + tipoEventoMecanismo.getMecanismo().getIdMecanismo());
						if(tipoEventoMecanismo.getMecanismo().isInmediato()){
							resultBuilderImmediate.append(tipoEventoMecanismo.getMecanismo().getIdMecanismo());
							resultBuilderImmediate.append(",");
						}else{
							resultBuilderNotImmediate.append(tipoEventoMecanismo.getMecanismo().getIdMecanismo());
							resultBuilderNotImmediate.append(",");
						}
					}
					listaMecanismoPasivo = resultBuilderNotImmediate.length() > 0 ? resultBuilderNotImmediate.substring(0, resultBuilderNotImmediate.length() - 1): "";
					listaMecanismoInmediato = resultBuilderImmediate.length() > 0 ? resultBuilderImmediate.substring(0, resultBuilderImmediate.length() - 1): "";
					log4j.debug("<create> listaMecanismoPasivo :" + listaMecanismoPasivo);
					log4j.debug("<create> listaMecanismoInmediato :" + listaMecanismoInmediato);
					
					// Obtiene los receptores asociados al evento
					log4j.debug("<create> Obteniendo Receptores del evento");
					log4j.debug("<create> tipoEventoReceptors().size :" + tipoEvento.getTipoEventoReceptors().size());
					
					if(tipoEvento.getTipoEventoReceptors().size() > 0) {
						/*  ----- */ 
						Iterator<TipoEventoReceptor> itLsTipoEventoReceptor = tipoEvento.getTipoEventoReceptors().iterator();
						while(itLsTipoEventoReceptor.hasNext()){
							TipoEventoReceptor tipoEventoReceptor = itLsTipoEventoReceptor.next();
							log4j.debug("<create> IdReceptor :" + tipoEventoReceptor.getReceptor().getIdReceptor());
							notificationDto.setIdReceptor(tipoEventoReceptor.getReceptor().getIdReceptor());
							// valida que los insumos del receptor sean correctos.
							//Este metodo es necesario solo si el servicio es expuesto.
							if(!filtersByReceiver(notificationDto)){
								/* no debe de pasar esto*/
								log4j.error("<create> No se pueden recuperar las personas asociadas al receptor de la notificación, insumos insuficientes o incorrectos");			
							}else{
								// Por cada receptor, obtiene la lista de personas correspondiente e inserta la notificación
								getlistByReceiver(notificationDto);
								Notificacion notificacion;
								Persona persona;
								log4j.debug("<create> lsNotificationDto: " +lsNotificationDto +
										(notificationDto.getHostAddress()!=null?"\n HostAddress="+notificationDto.getHostAddress():"" )+
										(notificationDto.getHostName()!=null?" HostName="+notificationDto.getHostName():"") );
								
								//Si hay notificaciones finales
								if(lsNotificationDto != null && lsNotificationDto.size() > 0){
									Iterator<NotificationDto> itNotificationDto = lsNotificationDto.iterator();
									while(itNotificationDto.hasNext()){
										NotificationDto notificationDtoOut = itNotificationDto.next();
										
										// Asignaciones generales de la notificación (aplican para todos los receptores y notificaciones)
										notificacion = new Notificacion();
										notificacion.setPrioridad(tipoEvento.getPrioridad());
										notificacion.setTipoEvento(tipoEvento);
										notificacion.setIdEmisor(Long.valueOf(notificationDto.getIdEmisor()));
										notificacion.setTipoEmisor(Byte.parseByte(notificationDto.getTipoEmisor()));
										persona=new Persona();
										persona.setIdPersona(Long.valueOf(notificationDtoOut.getIdPersonaReceptor()));
										notificacion.setPersona(persona);		
										notificacion.setListaMecanismoInmediato(listaMecanismoInmediato);
										notificacion.setListaMecanismoPasivo(listaMecanismoPasivo);
										notificacion.setVista(false);
										notificacion.setFechaCreacion(DateUtily.getToday());
										
										// Se debe generar y enviar la notificación con los mecanismo inmediatos
										notificationDtoOut.setClaveEvento(notificacion.getTipoEvento().getClaveEvento());
										notificationDtoOut.setListaMecanismoInmediato(notificacion.getListaMecanismoInmediato());
										
										//sets
										if(notificationDto.getIdCandidato() != null){
											notificationDtoOut.setIdCandidato(notificationDto.getIdCandidato());
										}
										notificationDtoOut.setIdEmisor(notificationDto.getIdEmisor());
										notificationDtoOut.setTipoEmisor(notificationDto.getTipoEmisor());
										notificationDtoOut.setIdPivote(notificationDto.getIdPivote());
										notificationDtoOut.setNombreEmisor(notificationDto.getNombreEmisor());
										notificationDtoOut.setNombreEmpresa(notificationDto.getNombreEmpresa());
										notificationDtoOut.setComentario(notificationDto.getComentario());
										notificationDtoOut.setComentario2(notificationDto.getComentario2());
										if(notificationDto.getNombrePosicion() != null){
											notificationDtoOut.setNombrePosicion(notificationDto.getNombrePosicion());
										}
									
										notificationDtoOut.setHostAddress(notificationDto.getHostAddress());
										notificationDtoOut.setHostName(notificationDto.getHostName());
										notificationDtoOut.setFecha(notificationDto.getFecha());
										if(notificationDto.getPassword() != null){
											notificationDtoOut.setPassword(notificationDto.getPassword());
										}
										notificationDtoOut.setNombreCandidato(notificationDto.getNombreCandidato());
										notificationDtoOut.setNombreFase(notificationDto.getNombreFase());
										notificationDtoOut.setNombreMonitor(notificationDto.getNombreMonitor());
										notificationDtoOut.setRol(notificationDto.getRol());
										notificationDtoOut.setHoraInicial(notificationDto.getHoraInicial());
										notificationDtoOut.setDiaInicial(notificationDto.getDiaInicial());
										notificationDtoOut.setHoraFinal(notificationDto.getHoraFinal());
										notificationDtoOut.setDiaFinal(notificationDto.getDiaFinal());
										notificationDtoOut.setNombre(notificationDto.getNombre());
																	
										log4j.debug("<create> claveEvento="+notificacion.getTipoEvento().getClaveEvento()+
												" idNotificacion="+notificationDtoOut.getIdNotificacion()+
												" getNombrePosicion="+notificationDtoOut.getNombrePosicion());
										
										//se edita el mensaje de la notificacion
										notificacion.setTexto(replaceTags(tipoEvento.getTexto(), notificationDtoOut));
										notificationDtoOut.setAsuntoEmail(notificacion.getTexto());
										
										//Se persiste el objeto notificacion
										log4j.debug("<create> se CREA nuevo registro de Notificacion ");
										notificationDtoOut.setIdNotificacion(String.valueOf(
															(Long) notificacionDao.create(notificacion)));
									
										//Se aplica los mecanismos inmediatos
										generateImmediateMechanisms(notificationDtoOut);
									}
								
								}else{
									//No se pudo completar la notificacion
									result =UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
																		Mensaje.SERVICE_CODE_002,
																		Mensaje.SERVICE_TYPE_WARNING,
																		Mensaje.MSG_ERROR_EMPTY));
								}
								
							}
						}
						/*  ----- */
					}
					else {
						log4j.error("<create> No existe tipo de receptor/destinatario para el tipo de evento [tipo_evento_receptor] ", new NullPointerException("tipoEvento.tipo_evento_receptor == 0 "));
						result =UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
															Mensaje.SERVICE_CODE_002,
															Mensaje.SERVICE_TYPE_FATAL,
															Mensaje.MSG_ERROR_EMPTY+": [notificacion.tipoEvento.receptor]"));
					}
					
				}else{
					//No se pudo completar la notificacion
					log4j.error("<create> No se pudo complementar la notificacion");
					result =UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
														Mensaje.SERVICE_CODE_002,
														Mensaje.SERVICE_TYPE_FATAL,
														Mensaje.MSG_ERROR));
				}
			}else{
				// Tipo evento no existe o mecanismos esta vacio, envía error
				if(tipoEvento == null) {
					log4j.error("<create> No existe el TIPO_EVENTO con clave [notificacion.tipoEvento]= "+notificationDto.getClaveEvento(),
							new NullPointerException("tipoEvento is null "));
					result =UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,
							Mensaje.SERVICE_TYPE_FATAL,
							Mensaje.MSG_ERROR_EMPTY+": [notificacion.tipoEvento]"));
				}
				else {
					log4j.error("<create> No existen MECANISMOS relacionados TIPO_EVENTO con clave= "+notificationDto.getClaveEvento(),
							new NullPointerException("tipoEvento.getTipoEventoMecanismos == 0 "));
					result =UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_002,
							Mensaje.SERVICE_TYPE_FATAL,
							Mensaje.MSG_ERROR_EMPTY+": [notificacion.tipoEvento.mecanismos]"));
				}				
			}
		}else{
			result = UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
												notificationDto.getCode(),
												notificationDto.getType(),
												notificationDto.getMessage()));
		}
		log4j.debug("<create> Fin de notificacion...");
		return result;
	}	

//	/** TODO reutilizarlo cuando se desarrolle la vista de notificaciones
//	 * Obtiene una lista de notificaciones del receptor correspondiente
//	 * @param NotificationDto notificationDto
//	 * @return Object
//	 * @throws Exception 
//	 */
//	public Object getDD(NotificationDto notificationDto) throws Exception {
//		notificationDto = filtros(notificationDto, Constante.F_GET);
//		if(notificationDto.getCode() == null && notificationDto.getMessages() == null){
//			List<NotificationDto> lsNotificationDto =notificacionDao.get(Long.parseLong(
//													 notificationDto.getIdPersonaReceptor()));
//			if(lsNotificationDto != null && lsNotificationDto.size() > 0){
//				return lsNotificationDto;
//			}else{
//				return Mensaje.SERVICE_MSG_OK_JSON;
//			}
//		}else{
//			if(notificationDto.getMessages() == null)
//				notificationDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
//											notificationDto.getCode(),notificationDto.getType(),
//											notificationDto.getMessage())));
//			else
//				notificationDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
//													Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
//													Mensaje.MSG_ERROR)));
//		}
//		return notificationDto.getMessages();
//	}
	
	
	/**
	 * Genera y envía las notificaciones con mecanismos inmediatos, por ejemplo : email
	 * @param NotificationDto notificationDto
	 * @return Object result
	 * @throws SystemTCEException 
	 * @throws IOException 
	 */		
	public Object generateImmediateMechanisms(NotificationDto notificationDto) throws SystemTCEException {
		log4j.debug("<generateImmediateMechanisms> ");
		log4j.debug("<generateImmediateMechanisms> IdNotificacion :" + notificationDto.getIdNotificacion() +
				"\n mecanismoInmediato :" + notificationDto.getListaMecanismoInmediato() +
				"\n IdPersonaReceptor :" + notificationDto.getIdPersonaReceptor() +
				"\n NombreReceptor :" + notificationDto.getNombreReceptor() +
				(notificationDto.getTokenInicio()!=null?"\n TokenInicio :" + notificationDto.getTokenInicio():"") +
				(notificationDto.getTokenInicio()!=null?"\n Vista :" + notificationDto.getVista():""));

		String result = null;
		// La notificación no debe haberse visto
		if(UtilsTCE.nvl(notificationDto.getVista(),"0").equals("1")?true:false ||
			notificationDto.getListaMecanismoInmediato() == null){
			log4j.debug("<generateImmediateMechanisms> La notificación ya ha sido vista, o no cuenta con mecanismos inmediatos por procesar");
		}else{
			log4j.debug("<generateImmediateMechanisms> Procesando mecanismos inmediatos");
			StringTokenizer st = new StringTokenizer(notificationDto.getListaMecanismoInmediato(),",");
			while(st.hasMoreTokens()){
				Long idMecanismo = Long.valueOf(st.nextToken());
				log4j.debug("<generateImmediateMechanisms> Mecanismo inmediato a procesar :" + idMecanismo);
				//correo
				if(idMecanismo == 5) {	// UtilsNotification.MECANISMO_CORREO.longValue()){  /* OSEASE que aqui si se vale HardCodear Octavio???   */
					genBodyAndSendMail(notificationDto);
				}
				else {
					log4j.fatal("<OCTAVIO NO PROGRAMO PARA OTROS CASOS ¿SINSENTIDO? ");
				}
			}
		}
		log4j.debug("<generateImmediateMechanisms> Fin...result="+result);
		return result;
	}	
	
	
	/**
	 * Obtiene la o las personas que deben recibir la notificación a partir del tipo de receptor
	 * @param NotificationDto notificationDto
	 * @return List<PersonaValidacionDto>
	 * @author osy
	 */			
	@Transactional( readOnly = true , isolation=Isolation.READ_COMMITTED)
	private void getlistByReceiver(NotificationDto notificationDto){
		log4j.debug("<getlistByReceiver> Inicio...getIdReceptor :" + notificationDto.getIdReceptor()+
				" getIdPersonaReceptor="+notificationDto.getIdPersonaReceptor()+
				" getIdEmisor="+notificationDto.getIdEmisor());
		if(notificationDto.getIdReceptor().longValue() == 
		   UtilsNotification.RECEPTOR_RESPONSABLES_POSICION.longValue()){
			lsNotificationDto=notificacionDao.getPositionPeopleOfCandidate( //EL DAO deberia ser Candidato, pues accede a Tabla Candidato
												Long.valueOf(notificationDto.getIdEmisor()));
		}else if(notificationDto.getIdReceptor().longValue() == 
			     UtilsNotification.RECEPTOR_CANDIDATOS_POSICION.longValue()){
			lsNotificationDto=notificacionDao.getPeopleCandidateOfPosition( //EL DAO deberia ser Candidato, pues accede a Tabla Candidato
												Long.valueOf(notificationDto.getIdEmisor()));
		}else if(notificationDto.getIdReceptor().longValue() == 
				 UtilsNotification.RECEPTOR_ENVIADO_CREACION.longValue()){
			
			 //se invita a un candidato al proceso de contratación
			 if( notificationDto.getClaveEvento().equals(UtilsNotification.
					 						CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_1)){
				 lsNotificationDto=notificacionDao.getPersonOfCandidate( //EL DAO deberia ser Candidato, pues accede a Tabla Candidato
						 						Long.valueOf(notificationDto.getIdPersonaReceptor()));
			 }else if(notificationDto.getClaveEvento().equals(UtilsNotification.
					 						CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_3) ||
					  notificationDto.getClaveEvento().equals(UtilsNotification.
							  CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_2_1) ||
					  notificationDto.getClaveEvento().equals(UtilsNotification.
							  CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_2_2)){
				 lsNotificationDto=notificacionDao.getPersonOfPosibleCandidate(  //EL DAO deberia ser Candidato, pues accede a Tabla POSIBLECandidato
						 						Long.valueOf(notificationDto.getIdPersonaReceptor()));
			 }else{
				 
				lsNotificationDto=notificacionDao.getPeople(Long.valueOf( //El query no tiene sentido, pues obtiene solo una persona por el ID TODO modificar a readbyID
									notificationDto.getIdPersonaReceptor())); 
			 }
			
		}else if(notificationDto.getIdReceptor().longValue() == 
				 UtilsNotification.RECEPTOR_ADMINISTRADORES_EMPRESA.longValue()){
			lsNotificationDto=notificacionDao.getPeopleByRelationType(Long.valueOf(notificationDto.getIdEmisor()),
																	  Long.valueOf(Constante.ROL_ADMINISTRADOR));
		}else if(notificationDto.getIdReceptor().longValue() == 
				 UtilsNotification.RECEPTOR_RESPONSABLES_POSICIONES_POR_EMPRESA.longValue()){
			//hacer pruebas
			lsNotificationDto=notificacionDao.getPeoplePositionOfCandidateEnterprise(Long.valueOf(notificationDto.getIdEmisor()));
		}else if(notificationDto.getIdReceptor().longValue() == 
				 UtilsNotification.RECEPTOR_RESPONSABLES_POSICIONES_POR_PERSONA.longValue()){
			lsNotificationDto=notificacionDao.getPositionPeopleOfCandidate(Long.valueOf(notificationDto.getIdEmisor()));
		}else if(notificationDto.getIdReceptor().longValue() == 
				 UtilsNotification.RECEPTOR_CANDIDATOS_POR_POSICION_PERSONA.longValue()){
			//La persona pertenece a una empresa
			/*if(notificationDto.getAuxiliar() == 0){
			lsNotificationDto=notificacionDao.getPeopleCandidateOfPositionPerson(Long.valueOf(notificationDto.getIdEmisor()),
																				 false);
			//La persona no pertenece a una empresa
			}else if(notificationDto.getAuxiliar() == 1){*/
				lsNotificationDto=notificacionDao.getPeopleCandidateOfPositionPerson(Long.valueOf(notificationDto.getIdEmisor()),
																				 true);
			//}
			
		}
	}

	/**
	 * Se aplican los filtros a los parámetros de entrada requeridos por receptor
	 * @param notificacionDto, objeto  que contiene las propiedades para aplicarle los filtros
	 * @return objeto principal que puede ser un mensaje u objeto original
	 */
	private boolean filtersByReceiver(NotificationDto notificationDto) {
		log4j.debug("<filtersByReceiver> Inicio...getIdReceptor :" + notificationDto.getIdReceptor());
		boolean success = true;
		 
		if(notificationDto.getIdReceptor().longValue() == UtilsNotification.RECEPTOR_RESPONSABLES_POSICION.longValue() ||
			notificationDto.getIdReceptor().longValue() == UtilsNotification.RECEPTOR_CANDIDATOS_POSICION.longValue()){
			// Para el receptor RECEPTOR_RESPONSABLES_POSICION,RECEPTOR_CANDIDATOS_POSICION  se requiere:
			// idPosicion
			if(notificationDto.getIdEmisor() == null || !notificationDto.getTipoEmisor().equals(
													UtilsNotification.TIPO_EMISOR_POSICION.toString()) ){
				log4j.debug("<filtersByReceiver> Se requiere la posición");
				success = false;
			}
		}
		/*if(notificationDto.getIdReceptor() == UtilsNotification.RECEPTOR_ADMINISTRADORES_EMPRESA.longValue()){
		}else if(
			notificationDto.getIdReceptor().equals(String.valueOf(UtilsNotification.RECEPTOR_ADMINISTRADORES_EMPRESA)) ||
			notificationDto.getIdReceptor().equals(String.valueOf(UtilsNotification.RECEPTOR_RESPONSABLES_POSICIONES_POR_EMPRESA))
		  ){
		}else */if(
			notificationDto.getIdReceptor().longValue() == UtilsNotification.RECEPTOR_ADMINISTRADORES_EMPRESA.longValue() ||
			notificationDto.getIdReceptor().longValue() == UtilsNotification.RECEPTOR_RESPONSABLES_POSICIONES_POR_EMPRESA.longValue()
		  ){
			// Para el receptor RECEPTOR_ADMINISTRADORAS_EMPRESA, se requiere:
			// idEmpresa
			if(notificationDto.getIdEmisor() == null || !notificationDto.getTipoEmisor().equals(
													UtilsNotification.TIPO_EMISOR_EMPRESA.toString()) ){
				log4j.debug("<filtersByReceiver> Se requiere la empresa");
				success = false;
			}
		}else if(notificationDto.getIdReceptor().longValue() == UtilsNotification.RECEPTOR_ENVIADO_CREACION.longValue()){
			// Para el receptor RECEPTOR_ENVIADO_CREACION, se requiere:
			// idPersonaReceptor, enviado desde la creación de la notificación
			if(notificationDto.getIdPersonaReceptor() == null ){
				log4j.debug("<filtersByReceiver> Se requiere el identificador de receptor");
				success = false;
			}
		}else if(
			notificationDto.getIdReceptor().longValue() == UtilsNotification.
										RECEPTOR_RESPONSABLES_POSICIONES_POR_PERSONA.longValue()){
			// Para el receptor RECEPTOR_ADMINISTRADORAS_PERSONA, se requiere:
			// idPersona
			if(notificationDto.getIdEmisor() == null || !notificationDto.getTipoEmisor().equals(
															UtilsNotification.TIPO_EMISOR_PERSONA.toString()) ){
				log4j.debug("<filtersByReceiver> Se requiere la persona");
				success = false;
			}
		}
		return success;
	}

	/**
	 * Se selecciona plantilla HTML para generar un correo en base del tipo de evento y se envia a clase de envio de correo
	 * @param notificationDto
	 * @throws SystemTCEException 
	 * @throws IOException 
	 */
	private void genBodyAndSendMail(NotificationDto notificationDto) throws SystemTCEException {
		log4j.debug("<genBodyAndSendMail> ");		
		/* Mensaje de AGENDA de FASE para Monitor Principal */
		if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_AGENDA_FMP)){
			try {
				//Se trae de un archivo local
				stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
				notificationDto.setContenido(new StringBuilder("<h2>Invitaci&oacute;n para participar en la fase de \"").
								append(UtilsNotification.TAG_17).append("\" </h2>").
								append("<h3><p style=\"text-align: justify;\">Estimado/a ").
								append(UtilsNotification.TAG_1).
								append(", este es un ").
								append(UtilsNotification.TAG_9).
								append(" usted tiene una cita").
								append(UtilsNotification.TAG_23).
								append(" con: ").append(UtilsNotification.TAG_22).
								append(", el día ").append(UtilsNotification.TAG_21).
								append(" en el horario de ").append(UtilsNotification.TAG_20).append(" .<br>").
								append("Para revisar su tracking en esta vacante, ingrese al siguiente enlace ").
								append(":&nbsp;&nbsp;").
								append("<a href=\"").append(UtilsNotification.TAG_2).append("\">").
								append("dothr.net</a></p> ").	
								append("<p style=\"text-align: justify;\"><br>").
								append(" Muchas gracias por su atenci&oacute;n.</p></p></h3>").
								toString());
			} catch (IOException e) {
				throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_AGENDA_FM)){
			try {
				//Se trae de un archivo local
				stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
				notificationDto.setContenido(new StringBuilder("<h2>Fase \"").
								append(UtilsNotification.TAG_17).append("\" </h2>").
								append("<h3><p style=\"text-align: justify;\">Este es un aviso para recordarle ").
								append(" que usted tiene que realizar la actividad a partir del día ").
								append(UtilsNotification.TAG_21).
								append(" en el horario de ").append(UtilsNotification.TAG_20).
								append(" hasta el día ").
								append(UtilsNotification.TAG_25).
								append(" en el horario de ").append(UtilsNotification.TAG_24).
								append(" .<br>").
								append("Para revisar su tracking, ingrese al siguiente enlace ").
								append(":&nbsp;&nbsp;").
								append("<a href=\"").append(UtilsNotification.TAG_2).append("\">").
								append("dothr.net</a></p> ").	
								append("<p style=\"text-align: justify;\"><br>").
								append(" Muchas gracias por su atenci&oacute;n.</p></p></h3>").
								toString());
			} catch (IOException e) {
				throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.
												CLAVE_EVENTO_MONITOR_NO_PRINCIPAL_FASE_RECHAZO)){
					try {
						//Se trae de un archivo local
						stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
						notificationDto.setContenido(new StringBuilder("<h2>El candidato ").append(UtilsNotification.TAG_16).
										append(", ha sido rechazado en la fase: \"").append(UtilsNotification.TAG_17).
										append("\" por el monitor:").append(UtilsNotification.TAG_18).
										append(".</h2>").
										append("<br>").
										append("<h3>Para dar seguimiento al proceso por favor ").
										append("accesar al siguiente enlace:&nbsp;&nbsp;").
										append("<a href=\"").append(UtilsNotification.TAG_2).append("\">").
										append("<div class=\"confirm\">dothr.net</div></a></p><br> ").	
										append("<p style=\"text-align: justify;\"><br>").
										append(" Muchas gracias por su atenci&oacute;n.</p></h3>").
										toString());
					} catch (IOException e) {
						throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
					}	
		
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.
													CLAVE_EVENTO_ADICION_MONITOR)) {
			try {
			stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
			notificationDto.setContenido(new StringBuilder("<h2>Adici&oacute;n al Proceso de Contrataci&oacute;n</h2>").
				append(" <h3><p style=\"text-align: left;\">Estimado/a ").
				append(UtilsNotification.TAG_1).append(":</p>").
				append("<p style=\"text-align: justify;\">Usted ha sido invitado/a para participar ").
				append("como monitor(").append(UtilsNotification.TAG_19).
				append(") en el proceso de postulaci&oacute;n de la vacante: ").
				append(UtilsNotification.TAG_7).append(". Las fases donde participa son:</p></h3>").
				append("<h4>").append(UtilsNotification.TAG_9).append("</h4>").				
				append("<h3><p style=\"text-align: justify;\"> ").
				append("Para definir su agenda en cada fase y dar seguimiento al proceso, ").
				append("por favor accesar a ").
				append("<a href=\"").append(UtilsNotification.TAG_2).append("\">").
				append("dothr.net</a></p><br> ").
				append("Muchas gracias por su atenci&oacute;n.</h3>").toString());
			} catch (IOException e) {
			throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.
													CLAVE_EVENTO_DESVINCULAR_MONITOR)) {
			try {
			stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
			notificationDto.setContenido(new StringBuilder("<h2>Desvinculaci&oacute;n al Proceso de Contrataci&oacute;n</h2>").
			append(" <h3><p style=\"text-align: left;\">Estimado/a ").
			append(UtilsNotification.TAG_1).append(":</p>").
			append("<p style=\"text-align: justify;\">Usted ha sido desvinculado/a en una o mas fases, ").
			append("como monitor(").append(UtilsNotification.TAG_19).
			append(") en el proceso de postulaci&oacute;n de la vacante: ").
			append(UtilsNotification.TAG_7).append(". Las fases son:</p></h3>").
			append("<h4>").append(UtilsNotification.TAG_9).append("</h4>").				
			append("<h3><p style=\"text-align: justify;\"> ").
			append("Para comprobar esto por favor accesar a ").
			append("<a href=\"").append(UtilsNotification.TAG_2).append("\">").
			append("dothr.net</a></p><br> ").
			append("Muchas gracias por su atenci&oacute;n.</h3>").toString());
			} catch (IOException e) {
			throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.
												CLAVE_EVENTO_SUSTITUCION_MONITOR)) {
			try {
			stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
			notificationDto.setContenido(new StringBuilder("<h2>Desvinculaci&oacute;n al Proceso de Contrataci&oacute;n</h2>").
			append(" <h3><p style=\"text-align: left;\">Estimado/a ").
			append(UtilsNotification.TAG_1).append(":</p>").
			append("<p style=\"text-align: justify;\">Usted ha sido desvinculado/a ").
			append("como monitor(").append(UtilsNotification.TAG_19).
			append(") en el proceso de postulaci&oacute;n de la vacante: ").
			append(UtilsNotification.TAG_7).append(". ").
			append("Para comprobar esto por favor accesar a ").
			append("<a href=\"").append(UtilsNotification.TAG_2).append("\">").
			append("dothr.net</a></p><br> ").
			append("Muchas gracias por su atenci&oacute;n.</h3>").toString());
			} catch (IOException e) {
			throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.
									CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_1) ||
				 notificationDto.getClaveEvento().equals(UtilsNotification.
						 			CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_2_1)) {
			try {
				stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
				notificationDto.setContenido(new StringBuilder("<h2><br>Adicion al Proceso de Contrataci&oacute;n</h2>").
						append(" <h3><p style=\"text-align: left;\">Estimado/a ").
						append(UtilsNotification.TAG_1).append(":</p>").
						append("<p style=\"text-align: justify;\">Usted ha sido invitado para participar ").
						append(" en el proceso de postulaci&oacute;n de la vacante: ").
						append(UtilsNotification.TAG_7).append(".<br>").
						append("Para seguir con el proceso, es requerido  ").
						append("accesar al siguiente enlace:&nbsp;&nbsp;").
						append("<a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append("<div class=\"confirm\">dothr.net</div></a></p><br> ").	
						append("<p style=\"text-align: justify;\"><br>").
						append(" Muchas gracias por tu atenci&oacute;n.</p></h3>").toString());
			} catch (IOException e) {
				throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.
										CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_2_2)){
			try {
				stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
				notificationDto.setContenido(new StringBuilder("<h2><br>Adicion al Proceso de Contrataci&oacute;n</h2>").
						append(" <h3><p style=\"text-align: left;\">Estimado/a ").
						append(UtilsNotification.TAG_1).append(":</p>").
						append("<p style=\"text-align: justify;\">Usted ha sido invitado para participar ").
						append(" en el proceso de postulaci&oacute;n de la vacante: ").
						append(UtilsNotification.TAG_7).append(".<br>").
						append("Para seguir con el proceso es necesario que actualice ").
						append(" y publique sus datos en el sistema. ").
						append("Por favor accese al siguiente enlace:&nbsp;&nbsp;").
						append("<a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append("<div class=\"confirm\">dothr.net</div></a></p><br> ").	
						append("<p style=\"text-align: justify;\"><br>").
						append(" Muchas gracias por tu atenci&oacute;n.</p></h3>").toString());
			} catch (IOException e) {
				throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_3)) {
			try {
				stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
				notificationDto.setContenido(new StringBuilder("<h2>Inicio de Proceso de Contrataci&oacute;n</h2>").
				append(" <h3><p style=\"text-align: left;\">Estimado ").
				append(UtilsNotification.TAG_1).append(":</p>").
				append("<p style=\"text-align: justify;\">Usted ha sido invitado para participar ").
				append(" en el proceso de postulaci&oacute;n de la vacante: ").
				append(UtilsNotification.TAG_7).append(".<br>").
				append("Para continuar con el proceso, es requerido que realice la captura ").
				append("de su perfil en nuestro sistema, accesando y confirmando su inscripción ").
				append("por medio del enlace siguiente:&nbsp;&nbsp;").
				append("<a href=\"").append(UtilsNotification.TAG_2).append("\">dothr.net</a><br> ").
				append("En el recuadro de abajo se encuentran sus credenciales.").append("</p></h3>").				
				append(" <div  style=\"width: 400px; background: #F3F4F5; margin: 25px auto 0; padding: 30px 10px;\">").
				append(" <p style=\"width: 100%; font-size: 18px; text-align: left; font-weight: 400; margin: 0;\"").
				append(" align=\"center\">Usuario:<span style=\"font-weight: 300; color: #55667a; margin: 0; padding-left: 2em;\">").
				append(UtilsNotification.TAG_14).append("</span></p>").
				append(" <p style=\"width: 100%; font-size: 18px; text-align: left; font-weight: 400; margin: 0; \" align=\"center\">").			
				append(" Contraseña:&nbsp;<span style=\"font-weight: 300; color: #55667a; margin: 0; \">").
				append(UtilsNotification.TAG_15).append("</span></p></div>").
				append(" <h3>Muchas gracias por tu atenci&oacute;n. </h3>").toString());
			} catch (IOException e) {
				throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
			
		}
		/* Confirmacion de inscripcion/ reinicio contrasenia */
		else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_CONF_INSC) || 
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_REINICIO_PWD)){
			try {
				//Se trae de un archivo local
				stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
				notificationDto.setContenido(new StringBuilder("<h1>Gracias por registrarse con nosotros</h1>").
								append("<h3>Para continuar con el proceso dé click en la siguiente liga:</h3>").
								append("<a href=\"").append(UtilsNotification.TAG_2).
								append("\"><div class=\"confirm\">Confirmar su Inscripción</div></a>").
								append("<h2>Inmediatamente después podrá dar de alta su CV para aplicar a la posición vacante publicada.</h2>").
								toString());
			} catch (IOException e) {
				throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_PUBLICA_CV)){
			try {
				//Se trae de un archivo local
				stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
				notificationDto.setContenido(new StringBuilder("<br>").
						append("<h1>Gracias por registrarse con nosotros y publicar su CV</h1>").
						append("<h3>Saludos Cordiales.</h3>").
						toString());
				
			} catch (IOException e) {
				throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_RECORDAR_PUBLICACION)){
			try {
				//Se trae de un archivo local
				stPlantilla=new StringBuilder(UtilsTCE.readFile(mail_path +selex1_html,StandardCharsets.UTF_8));
				notificationDto.setContenido(new StringBuilder("<h1>Proceso Incompleto</h1><br>").
								append("<h2>Hemos detectado que recientemente capturaste tu perfil en nuestro sitio ").
								append("(p&aacute;gina web), sin embargo el proceso quedo inconcluso.</h2> ").
								append("<h2>Agradeceremos ingreses nuevamente  en la ").
								append("<a href=\"").append(UtilsNotification.TAG_2).append("\">siguiente liga</a>").
								append(" y selecciones la opcion publicar. </h2><br>").
								append(" <h2>Muchas gracias por tu atenci&oacute;n. </h2>").
								toString());
			} catch (IOException e) {
				throw new SystemTCEException(" Error al leer el archivo html:"+mail_path +selex1_html,e);
			}
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_INVITACION)){
			stPlantilla.append("<p>Estimado ").append(UtilsNotification.TAG_1).append(":<br>").
						append("Ha sido invitado para participar en el proceso de selección de la vacante ").
						append("<b><a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append(UtilsNotification.TAG_7).append("</a></b>.<br>").
						append("La convocatoria fue realizada por el contratante ").
						append("<nombreEmisor>");
			//Nombre empresa
			if(notificationDto.getNombreEmpresa() != null){
				stPlantilla.append(" , quien trabaja en la empresa ").
							append(UtilsNotification.TAG_6);
			}
			stPlantilla.append(".</p>");
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_REINVITACION)){
			stPlantilla.append("<p>Estimado ").append(UtilsNotification.TAG_1).append(":<br>").
						append("El contratante ").append("<nombreEmisor>");
			//Nombre empresa
			if(notificationDto.getNombreEmpresa() != null){
				stPlantilla.append(" , quien trabaja en la empresa ").
							append(UtilsNotification.TAG_6 ).append(", ");
			}				
			stPlantilla.append(" te ha invitado de nuevo para participar en el proceso de selección de la vacante ").
						append("<b><a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append(UtilsNotification.TAG_7).append("</a></b>.<br>").
						append("Te ha enviado el siguiente mensaje: ").
						append("\"").append(UtilsNotification.TAG_9).append("\"");
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_INVITACION_OK)){
			stPlantilla.append("<p>Estimado ").
						append(UtilsNotification.TAG_1).append(":<br>").
						append("El candidato ").append("<nombreEmisor>").
						append(", ha aceptado su invitación.").
						append(" Por favor da click en la siguiente liga:").
						append("<b><a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append(" Ver Candidato</a></b>.</p>");
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_INVITACION_NOT_OK)){
			stPlantilla.append("<p>Estimado ").append(UtilsNotification.TAG_1).append(":<br>").
						append("El candidato ").append(UtilsNotification.TAG_8).
						append(", no aceptó su invitación.").
						append(" Para ver el detalle, por favor da click en la siguiente liga: ").
						append("<b><a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append("Motivo de rechazo</a></b>.</p>");
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_CV_PERSONA_INACTIVA)){
			stPlantilla.append("<p>Estimado ").append(UtilsNotification.TAG_1).append(":<br>").
						append("El candidato ").append(UtilsNotification.TAG_8).
						append(", ha inactivado su cuenta.").
						append(" Para ver el detalle, por favor da click en la siguiente liga: ").
						append("<b><a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append("Ver Candidato</a></b>.</p>");
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_CV_PERSONA_INACTIVA_POSICION)){
			stPlantilla.append("<p>Estimado ").append(UtilsNotification.TAG_1).append(":<br>").
						append("La posición: ").append(UtilsNotification.TAG_7).
						append(", ha sido inactivada.").
						append(" Para ver el detalle, por favor da click en la siguiente liga: ").
						append("<b><a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append("Ver Candidato</a></b>.</p>");
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_RECHAZO)){
			stPlantilla.append("<p>Estimado ").append(UtilsNotification.TAG_1).append(":<br>").
						append("El contratante ").append("<nombreEmisor>").
						append(", lo ha rechazado para la posición: ").append(UtilsNotification.TAG_7).
						append(". Para ver el detalle, por favor da click en la siguiente liga: ").
						append("<b><a href=\"").append(UtilsNotification.TAG_2).append("\">").
						append("Ver Candidato</a></b>.</p>");
		}
		
		/* Plantilla por DEFECTO (TODO cambiar a template.html) */
		else {
			/* ERROR_FATAL de la aplicacion para enviar a Administradores */
//			cif(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_ERROR_FATAL)){
				//Genera plantilla en HardCode
				stPlantilla=new StringBuilder().
							append("<h3>Host:</h3>").
							append("<h4><ul>").
							append("<li>IP: ").append(UtilsNotification.TAG_12).append("</li>"). //<hostAddress>
							append("<li>Nombre: ").append(UtilsNotification.TAG_11).append("</li>"). //<hostName>
							append("<li>Fecha: ").append(UtilsNotification.TAG_13).append("</li>"). //<fecha>
							append("</ul></h4>").
							append("<br><h3>Detalle:</h3>").
							append("<h4>").
							append(UtilsNotification.TAG_9). //<comentario>
							append("</h4>");
				
//			}
		}
		
		log4j.debug("<genBodyAndSendMail> claveEvento="+notificationDto.getClaveEvento()+
				" email="+notificationDto.getEmail()+
				" asuntoEmail="+notificationDto.getAsuntoEmail()+
				" comentario="+notificationDto.getComentario()+
				" hostAddress="+notificationDto.getHostAddress()+
				" hostName:"+notificationDto.getHostName()
//				+" notificationDto="+notificationDto
				);
		/* Validacion que se haya elegido plantilla */
		if(stPlantilla==null) {
			log4j.error("<genBodyAndSendMail> No se definio una plantilla de correo ", new SystemTCEException("No se definio una plantilla de correo"));
		}
		else {
			//Se sustituyen las etiquetas en el contenido
			if(notificationDto.getContenido() != null){
				notificationDto.setContenido(replaceTags(notificationDto.getContenido(),
														 notificationDto));  //TODO lo hace dos veces??
			}
			
			/*Se sustituyen las etiquetas en  stPlantilla y se manda el correo (V0)
			javaMailManagerService.threadMail(new CorreoTceDto(null,
											notificationDto.getEmail(),
											notificationDto.getAsuntoEmail(),
											replaceTags(stPlantilla.toString(), 
														notificationDto),
											null ));*/
			/*Se sustituyen las etiquetas en  stPlantilla y se manda el correo (V1 gmail)
			javaMailManagerService.sendMail(new CorreoTceDto(null,
											notificationDto.getEmail(),
											notificationDto.getAsuntoEmail(),
											replaceTags(stPlantilla.toString(), 
														notificationDto),
											notificationDto.getClaveEvento(),
											null )); /*/
			/* Se sustituyen las etiquetas en  stPlantilla y se manda el correo (V2 SES) */
			javaMailManagerService.sendMailBySES(new CorreoTceDto(null,
					notificationDto.getEmail(),
					notificationDto.getAsuntoEmail(),
					replaceTags(stPlantilla.toString(), 
								notificationDto),
					notificationDto.getClaveEvento(),
					null ));
		}
	}
	
	/**
	 * Usando EXPRESIONES REGULARES, Se sustituyen dinamicamente las etiquetas en el texto [mensaje]
	 * @param mensaje, es el string donde estan las etiquetas a reemplazar
	 * @param notificationDto, objeto con informacion de la notificacion
	 * @return
	 */
	private  String replaceTags(String mensaje, NotificationDto notificationDto){
		Matcher matcher = Pattern.compile("<(\\w+|\\w+:\\w+)>").matcher(mensaje);
		while(matcher.find()){
			//TODO validarlo primero contra una lista para reducir tiempo de procesamiento
			mensaje=mensaje.replace(matcher.group(), traslateTag(matcher.group(), notificationDto));
		}
		return mensaje;
	}
	
	/**
	 * Reemplaza la etiqueta con el valor correspondiente en el Dto: 
	 * etiqueta a reemplazar [i.e. &lt;nombre&gt; por dto.getNombre()]
	 * @param stTag
	 * @return
	 */
	private String traslateTag(String stTag, NotificationDto notificationDto){
//		log4j.debug("<traslateTag> stTag="+stTag );
		if(stTag.equals(UtilsNotification.TAG_1)){ //<nombreReceptor> Nombre persona
			stTag=notificationDto.getNombreReceptor();
			//link en el correo para ingresar al cliente
		} 
		else if(stTag.equals(UtilsNotification.TAG_2)){ /* <ligaCorreo> Casos: Correo confirmacion, restablecer, publicacion, recordatorio */
			//Se crea el token
			if(notificationDto.getClaveEvento().equals(
				UtilsNotification.CLAVE_EVENTO_REINICIO_PWD)){
				stTag=createToken(notificationDto, "#/reiniciar/");
			}else if(notificationDto.getClaveEvento().equals(
					  UtilsNotification.CLAVE_EVENTO_RECORDAR_PUBLICACION) ||
					 notificationDto.getClaveEvento().equals(
							  UtilsNotification.CLAVE_EVENTO_ADICION_MONITOR) ||
					 notificationDto.getClaveEvento().equals(
							  UtilsNotification.CLAVE_EVENTO_MONITOR_NO_PRINCIPAL_FASE_RECHAZO) ||
					 notificationDto.getClaveEvento().equals(
							 UtilsNotification.CLAVE_EVENTO_AGENDA_FMP) ||
					 notificationDto.getClaveEvento().equals(
					 		UtilsNotification.CLAVE_EVENTO_AGENDA_FM)	||
					 notificationDto.getClaveEvento().equals(
						 		UtilsNotification.CLAVE_EVENTO_DESVINCULAR_MONITOR)
					){
				stTag=uri_appui+"#/login/";
			}else if(notificationDto.getClaveEvento().equals(
						UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_1) ||
					notificationDto.getClaveEvento().equals(
						UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_3) ||
					notificationDto.getClaveEvento().equals(
						UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_2_1) ||
					notificationDto.getClaveEvento().equals(
						UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_2_2)){
				stTag=createToken(notificationDto, "#/confirmAddVacancy/");				
			}else if(notificationDto.getClaveEvento().equals(
					  UtilsNotification.CLAVE_EVENTO_CONF_INSC)){	
				stTag=createToken(notificationDto, "#/validar/");
			}
			
		}
		/* link de la pagina login */
		else if(stTag.equals(UtilsNotification.TAG_3)){
			stTag=uri_appui;
		//link del logo de dothr
		}else if(stTag.equals(UtilsNotification.TAG_4)){
			if(files_repository_local){
				stTag=new StringBuilder(file_uri).append("logo.png").toString();
			}else{
				stTag=new StringBuilder(file_uri).append(path_files).append("logo.png").toString();
			}
		//Nombre del emisor
		}else if(stTag.equals("<nombreEmisor>")){
			if(notificationDto.getNombreEmisor() == null){
				CurriculumDto curriculumDto=personaDao.getPersonaNombre(Long.valueOf(notificationDto.getIdEmisor()));
				stTag= new StringBuilder(curriculumDto.getNombre() == null ? "":curriculumDto.getNombre()+" ")
						.append(curriculumDto.getApellidoPaterno() == null ? "":curriculumDto.getApellidoPaterno()+" ")
						.append(curriculumDto.getApellidoMaterno() == null? "":curriculumDto.getApellidoMaterno()).toString();
				notificationDto.setNombreEmisor(stTag);
			}else{
				stTag=notificationDto.getNombreEmisor();
			}
			//Nombre de la empresa del emisor
		}else if(stTag.equals(UtilsNotification.TAG_6)){
			stTag=notificationDto.getNombreEmpresa() == null ? " persona "+notificationDto.getNombreEmisor():
															" empresa "+notificationDto.getNombreEmpresa();
		//Nombre de la posicion	
		}else if(stTag.equals(UtilsNotification.TAG_7)){
			log4j.debug("<listTags> NombrePosicion: "+notificationDto.getNombrePosicion() );
			stTag=notificationDto.getNombrePosicion();
		//id del candidato
		}else if(stTag.equals(UtilsNotification.TAG_8)){
			stTag=notificationDto.getIdCandidato().toString();
		}else if(stTag.equals(UtilsNotification.TAG_9)){
			stTag=notificationDto.getComentario() == null ? 
							"No hay texto":
							notificationDto.getComentario() ;
		}else if(stTag.equals(UtilsNotification.TAG_10)){
			stTag=notificationDto.getContenido();
			log4j.debug("<traslateTag> " + stTag );
		}else if(stTag.equals(UtilsNotification.TAG_11)){
			stTag=notificationDto.getHostName();
		}else if(stTag.equals(UtilsNotification.TAG_12)){
			stTag=notificationDto.getHostAddress();
		}else if(stTag.equals(UtilsNotification.TAG_13)){
			stTag=notificationDto.getFecha();
		}else if(stTag.equals(UtilsNotification.TAG_14)){
			stTag=notificationDto.getEmail();
		}else if(stTag.equals(UtilsNotification.TAG_15)){
			stTag=notificationDto.getPassword();
		}else if(stTag.equals(UtilsNotification.TAG_16)){
			stTag=notificationDto.getNombreCandidato();
		}else if(stTag.equals(UtilsNotification.TAG_17)){
			stTag=notificationDto.getNombreFase();
		}else if(stTag.equals(UtilsNotification.TAG_18)){
			stTag=notificationDto.getNombreMonitor();
		}else if(stTag.equals(UtilsNotification.TAG_19)){
			stTag=notificationDto.getRol();
		}else if(stTag.equals(UtilsNotification.TAG_20)){
			stTag=notificationDto.getHoraInicial();
		}else if(stTag.equals(UtilsNotification.TAG_21)){
			stTag=notificationDto.getDiaInicial();
		}else if(stTag.equals(UtilsNotification.TAG_22)){
			stTag=notificationDto.getNombre();
		}else if(stTag.equals(UtilsNotification.TAG_23)){
			stTag=notificationDto.getComentario2();
		}else if(stTag.equals(UtilsNotification.TAG_24)){
			stTag=notificationDto.getHoraFinal();
		}else if(stTag.equals(UtilsNotification.TAG_25)){
			stTag=notificationDto.getDiaFinal();
		}
//		log4j.debug("<traslateTag> salida stTag="+stTag);
		return stTag;
	}
	
	/**
	 * Crea el enlace http para reemplazar en la plantilla de Correo Bienvenida / Restablecer
	 * @param ngRoute : #/validar/  |  #/reiniciar/
	 * @param notificationDto
	 */
	private String createToken(NotificationDto notificationDto, String ngRoute){
		
		//si no hay clave
	 	if(notificationDto.getTokenInicio() == null){
	 		try {
	 			stIdToken= new StringBuilder();
	 			
	 			//se crea token para uri de invitación a candidato de sumarse a la posición
	 			if(notificationDto.getClaveEvento().equals(
	 				   UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_1)){
	 				stIdToken.append(UtilsNotification.NUM_EVENTO_POSTULADO_PROCESO_CONTRATACION_1.toString()).
	 						  append(UtilsNotification.CLAVE_DIVISOR_2).
	 						  append(notificationDto.getIdCandidato().toString());
	 				
	 			//se crea token para uri de invitación a posible_candidato_b
	 			//de sumarse a la posición
	 			}else if(notificationDto.getClaveEvento().equals(
		 				   UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_3)){
	 				stIdToken.append(UtilsNotification.NUM_EVENTO_ADICION_PROCESO_CONTRATACION_3.toString()).
	 						  append(UtilsNotification.CLAVE_DIVISOR_2).
	 						  append(notificationDto.getIdPosibleCandidato().toString());
	 			}else if( notificationDto.getClaveEvento().equals(
	 					UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_2_1) ){
	 				stIdToken.append(UtilsNotification.NUM_EVENTO_POSTULADO_PROCESO_CONTRATACION_2_1.toString()).
									  append(UtilsNotification.CLAVE_DIVISOR_2).
									  append(notificationDto.getIdPosibleCandidato().toString());
	 			}else if( notificationDto.getClaveEvento().equals(
	 					UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_2_2) ){	 			
	 				stIdToken.append(UtilsNotification.NUM_EVENTO_ADICION_PROCESO_CONTRATACION_2_2.toString()).
									  append(UtilsNotification.CLAVE_DIVISOR_2).
									  append(notificationDto.getIdPosibleCandidato().toString());
	 			}
	 			/* Si es confirmacion (id_estatusInscripcion 1) */
	 			else if( notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_CONF_INSC) ){
	 				stIdToken.append(UtilsNotification.CLAVE_DIVISOR_1).
			  		  		  append(notificationDto.getIdPersonaReceptor().toString());
	 			}else {	
	 				
					//Se completa stIdToken
					stIdToken.append(UtilsNotification.CLAVE_DIVISOR_1).
					  		  append(notificationDto.getIdNotificacion());
					
	 			}
	 			log4j.debug("createToken() -> idToken_cad="+stIdToken);
	 				
	 			//Se pone el token encryptado
	 			notificationDto.setTokenInicio(UtilsSeguridad.encrypt(stIdToken.toString()));
		 		
		 		log4j.debug("createToken() -> final idToken_encrypt="+notificationDto.getTokenInicio());
		 		
		 	} catch (Exception e) {
				log4j.error("Error al reemplazar el tag:"+UtilsNotification.TAG_2);
				e.printStackTrace();
			}
	 	}
	 	//se actualiza el token en la tabla correspondiente
	 	if(notificationDto.getClaveEvento().equals(
		   UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_1) ||
		   notificationDto.getClaveEvento().equals(
		   UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_2_1) ||
		   notificationDto.getClaveEvento().equals(
		   UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_2_2)){
	 		//Aqui no se guarda el token
	 	}else if(notificationDto.getClaveEvento().equals(
				   UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_3)){
	 		posibleCandidatoDao.updateTokenAdd(notificationDto.getTokenInicio(),
							   				   notificationDto.getIdPosibleCandidato());
	 	}else{
	 		personaDao.updateTokenInicio(notificationDto.getTokenInicio(),
					Long.valueOf(notificationDto.getIdPersonaReceptor()));
	 	}
	 	
	 	log4j.debug("createToken() -> ngRoute="+ngRoute+ 
	 			    " notificationDto.getEmail="+notificationDto.getEmail());
		
		//La clave encriptada se compone: (idPersona o idCandidato) + : + idNotificacion
		//return new StringBuilder(URI_APPTCEUI) .append("#/validar/").
		return new StringBuilder(uri_appui) .append(ngRoute).
				append(notificationDto.getTokenInicio()).append("/").
				//se adiciona el correo
				append(notificationDto.getEmail()).toString();				
	}				
	
	/**
	 * Se complementa la notificacion dependiendo del tipo de evento, en cuanto: tipoemiso, emisor y receptor
	 * @param notificationDto
	 */
	private boolean complementataNotificacion(NotificationDto notificationDto){
		boolean regreso=true;
		//inscripcion: confirmacion de la inscripcion 
		if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_CONF_INSC) ||
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_PUBLICA_CV) ||
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_RECORDAR_PUBLICACION)  ||
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_ERROR_FATAL) ||
		   //se inicia el proceso de contratacion
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_3) ||
		   //se invita a un candidato al proceso de contratación
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_1) ||
		   //se informa al precandidato_2 postulado que es parte del proceso de contratación
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_POSTULADO_PROCESO_CONTRATACION_2_1) ||
		   //se informa la adicion del precandidato_2_1 en el proceso de contratación
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_ADICION_PROCESO_CONTRATACION_2_2) ||
		   // Reiniciar: Restablecer el password
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_REINICIO_PWD) ||
		   // se informa de la adicion del monitor
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_ADICION_MONITOR) ||
		   // se informa del rechazo de fase del monitor correspondiente
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_MONITOR_NO_PRINCIPAL_FASE_RECHAZO) ||
			// se informa cuando se creo una agenda para fase monitor_postulado
		   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_AGENDA_FMP) ||
			// se informa cuando se creo una agenda para fase monitor
			notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_AGENDA_FM) ||
			// se informa de la desvinculacion del monitor
			 notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_DESVINCULAR_MONITOR) ||
			// se informa de la sustitucion del monitor
		    notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_SUSTITUCION_MONITOR) 
		   ){
				notificationDto.setTipoEmisor(UtilsNotification.TIPO_EMISOR_SISTEMA.toString());
				notificationDto.setIdEmisor(UtilsNotification.EMISOR_SISTEMA.toString());
				notificationDto.setIdPersonaReceptor(notificationDto.getIdPivote());
					
		//Handshake
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_INVITACION) ||
				notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_REINVITACION) ||
				notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_INVITACION_OK) ||
				notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_INVITACION_NOT_OK) ||
				notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_RECHAZO)){
			
			//El pivote es el idCandidato
			CandidatoDto candidatoDto =candidatoDao.getHandCheck(Long.valueOf(notificationDto.getIdPivote()));
			if(candidatoDto != null){
				if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_INVITACION) ||
				   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_REINVITACION) ||
				   notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_HK_RECHAZO)){
					//acciones del contratante
					completaHandShake(candidatoDto,notificationDto, true);
				}else{
					//acciones de candidato
					completaHandShake(candidatoDto,notificationDto, false);
				}
			}else{
				regreso=false;
			}
		//Se modifica o se inactiva cv
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_MODIFICA_CV_PERSONA)
				|| notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_CV_PERSONA_INACTIVA)){
			notificationDto.setTipoEmisor(UtilsNotification.TIPO_EMISOR_PERSONA.toString());
			notificationDto.setIdEmisor(notificationDto.getIdPivote());
		//Se modifica empresa
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_MODIFICA_CV_EMPRESA)){
			notificationDto.setTipoEmisor(UtilsNotification.TIPO_EMISOR_EMPRESA.toString());
	    	notificationDto.setIdEmisor(notificationDto.getIdPivote());
	    //se inactiva cv, si la persona es 
		}else if(notificationDto.getClaveEvento().equals(UtilsNotification.CLAVE_EVENTO_CV_PERSONA_INACTIVA_POSICION)){
			notificationDto.setTipoEmisor(UtilsNotification.TIPO_EMISOR_PERSONA.toString());
			notificationDto.setIdEmisor(notificationDto.getIdPivote());
		}
		//NUEVO caso por Defecto
		else {
			notificationDto.setTipoEmisor(UtilsNotification.TIPO_EMISOR_SISTEMA.toString()); // 1-Per,2-Emp, 3-Pos, 4-Sys
			notificationDto.setIdEmisor(UtilsNotification.EMISOR_SISTEMA.toString()); //ID persona Emisor (0 es sistema)
			notificationDto.setIdPersonaReceptor(notificationDto.getIdPivote());  //ID persona destinatario
		}
		
		return regreso;
	}
	
	/**
	 * Se complementa la notificacion para el contexto de Handshake
	 * @param candidatoDto
	 * @param notificationDto
	 * @param candidatoReceptor
	 */
	private void completaHandShake(CandidatoDto candidatoDto,NotificationDto notificationDto, boolean candidatoReceptor){
		notificationDto.setTipoEmisor(UtilsNotification.TIPO_EMISOR_PERSONA.toString());
		notificationDto.setIdCandidato(Long.valueOf(notificationDto.getIdPivote()));
		notificationDto.setNombrePosicion(candidatoDto.getNombrePosicion());
		notificationDto.setNombreEmpresa(candidatoDto.getNombreEmpresa());
		
		//si el candidato es el receptor
		if(candidatoReceptor){
			notificationDto.setIdPersonaReceptor(candidatoDto.getIdPersona() == null ? 
							candidatoDto.getIdRepresentante().toString():
							candidatoDto.getIdPersona().toString());
			notificationDto.setIdEmisor(candidatoDto.getIdPosicionPersona().toString());
			notificationDto.setNombreEmisor(candidatoDto.getNombrePosicionPersona());
		//si el candidato es el emisor
		}else{
			notificationDto.setIdPersonaReceptor(candidatoDto.getIdPosicionPersona().toString());
			notificationDto.setIdEmisor(candidatoDto.getIdPersona() == null ? 
							candidatoDto.getIdRepresentante().toString():
							candidatoDto.getIdPersona().toString());
		}
	}
	
	
	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto notificacionDto
	 * @param notificacionDto, objeto  que contiene las propiedades para aplicarle los filtros
	 * @return objeto principal que puede ser un mensaje u objeto original
	 * @throws Exception 
	 */
	private NotificationDto filtros(NotificationDto notificationDto, int funcion) throws Exception {
		 boolean error=false;
		 if(notificationDto != null){
			
			 //La propiedad IdPosicion para la función create, debe ser nula
			 if(!error && funcion == Constante.F_CREATE){
				 // Es requerida la clave de evento
				 if(notificationDto.getClaveEvento() == null ){		
					 log4j.debug("<filtros> Es requerido clave de evento");
					error=true;
				 }
				 if(notificationDto.getIdPivote() == null){		
							 log4j.debug("<filtros> Es requerido IdPivote");
							error=true;
				}
			 //Ya que el metodo_servicio es expuesto, se valida el idconf
			 }else if(!error && funcion == Constante.F_GET){
				 if(notificationDto.getIdEmpresaConf() == null || 
					notificationDto.getIdPersonaReceptor() == null ){
					 log4j.debug("<filtros> Es requerid idPersona");
					 error=true;
				 }
			 }else if(!error && funcion == Constante.F_DATACONF){
				 if(notificationDto.getIdEmpresaConf() == null){
					 log4j.debug("<filtros> Es requerido idConf");
					 error=true;
				 }
			 }			
//			 log4j.debug(" filtros funcion"+funcion+" error="+error);
		 }else{
			 log4j.debug("<filtros> Dto es Null");
			 error=true;
		 }
		 
		 if(error){
			 notificationDto=new NotificationDto();
			 notificationDto.setMessage(Mensaje.MSG_ERROR);
			 notificationDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 notificationDto.setCode(Mensaje.SERVICE_CODE_006);
		 }else{
			//create y update
			 if(funcion != Constante.F_DATACONF && funcion != Constante.F_CREATE ){
				//Se aplican filtros Dataconf
				notificationDto=filtrosDataConf(notificationDto);
			 }
		 }
		return notificationDto;
	}

	/**
	 * Regresa un objeto dataConf referida al servicio notificationDto
	 * @param notificationDto, objeto data-conf correspondiente
	 * @return NotificationDto
	 * @throws Exception 
	 */
	private NotificationDto dataConf(NotificationDto notificationDto) throws Exception{
		//Se obtiene el objeto-Dataconf
		notificationDto=filtros(notificationDto,Constante.F_DATACONF);
		if(notificationDto.getCode() == null){
			//Se obtiene los data-info
			notificationDto=(NotificationDto)empresaInterfaseService.dataConf(
														notificationDto.getIdEmpresaConf(),
														"Notificacion",new NotificationDto());
			if(notificationDto == null){
				notificationDto=new NotificationDto();
				notificationDto.setMessage(Mensaje.SERVICE_MSG_ERROR_DATACONF);
				notificationDto.setType(Mensaje.SERVICE_TYPE_FATAL);
				notificationDto.setCode(Mensaje.SERVICE_CODE_002);
			}
		}
		
		return notificationDto;
	}
	
	/**
	 * 
	 * @param notificationDto
	 * @return
	 * @throws Exception 
	 */
	private NotificationDto filtrosDataConf(NotificationDto notificationDto) throws Exception{
		 log4j.debug(" getIdConf="+notificationDto.getIdEmpresaConf());
		NotificationDto notificationDataconf=new NotificationDto ();
		notificationDataconf.setIdEmpresaConf(notificationDto.getIdEmpresaConf());
		notificationDataconf=dataConf(notificationDto);
		//si no hay error
		if(notificationDataconf.getCode() == null){
			notificationDto=(NotificationDto)Validador.filtrosDataConf(
							notificationDataconf,notificationDto);
			
		}else{
			notificationDto=notificationDataconf;
		}
		
		return notificationDto;
	}
	
}