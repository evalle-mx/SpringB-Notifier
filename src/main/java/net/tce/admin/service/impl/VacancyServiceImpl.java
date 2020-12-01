package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import net.tce.admin.service.BitacoraPosicionService;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.admin.service.VacancyService;
import net.tce.assembler.VacancyAssembler;
import net.tce.dao.EmpresaConfDao;
import net.tce.dao.ModeloRscPosDao;
import net.tce.dao.ModeloRscPosFaseDao;
import net.tce.dao.PosicionDao;
import net.tce.dao.RelacionEmpresaPersonaDao;
import net.tce.dto.BtcCompetenciaPerfilDto;
import net.tce.dto.BtcDomicilioDto;
import net.tce.dto.BtcPerfilTextoNgramDto;
import net.tce.dto.BtcPoliticaValorDto;
import net.tce.dto.BtcPosicionDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.ModeloRscPosDto;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.dto.VacancyDto;
import net.tce.model.CompetenciaPerfil;
import net.tce.model.Domicilio;
import net.tce.model.EstatusPosicion;
import net.tce.model.Mes;
import net.tce.model.Perfil;
import net.tce.model.PerfilPosicion;
import net.tce.model.PerfilTextoNgram;
import net.tce.model.PoliticaMValor;
import net.tce.model.PoliticaValor;
import net.tce.model.Posicion;
import net.tce.model.RelacionEmpresaPersona;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.ParametersTCE;
import net.tce.util.UtilsTCE;
import net.tce.util.ValidadorPublicacion;

/**
 * Clase donde se aplica las politicas de negocio del servicio vacancy
 * @author 
 *
 */
@Transactional
@Service("vacancyService")
public class VacancyServiceImpl implements VacancyService{
	Logger log4j = Logger.getLogger( this.getClass());
	
	 EmpresaParametroDto empresaParametroDto;
	List<ModeloRscPosDto> lsModeloRscPos;
	Long idPosicion;
	Long count;
	List<Long> lsIdModeloRscPosFase;
	Iterator<Long> itIdModeloRscPosFase;
	List<ModeloRscPosFaseDto>  lsModeloRscPosFaseDto;
	Posicion posicion;
	
	@Autowired
	private PosicionDao posicionDao;
	

	@Autowired
	private RelacionEmpresaPersonaDao relacionEmpresaPersonaDao;
	
	@Autowired
	EmpresaConfDao empresaConfDao;
	
	@Autowired
	private ModeloRscPosDao modeloRscPosDao;

	@Autowired
	private ModeloRscPosFaseDao modeloRscPosFaseDao;

	@Autowired
	private EmpresaParametroService empresaParametroService;	
	
	@Autowired
	private BitacoraPosicionService bitacoraPosicionService;
	
	@Inject
	private ConversionService converter;
	
	@Inject
	private VacancyAssembler vacancyAssembler;
	
	@Inject
	Gson gson;
	
	
	private List<EmpresaParametroDto> lsEmpresaParametroPublicacionDto = null;
	
	
	
	/**
	 * 
	 * @param vacancyDto
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	public Object createComplete(VacancyDto vacancyDto){
		log4j.info("<createComplete> Dummy................. ");
		VacancyDto responseDto = new VacancyDto(true);
		log4j.debug("<createComplete> obteniendo idEMpresa externo");
		String idEmpresaTxt = vacancyDto.getIdEmpresa();
		
		if(idEmpresaTxt!=null && UtilsTCE.isValidId(idEmpresaTxt)){
			try {
				Long idEmpresaCreate = Long.valueOf(idEmpresaTxt);
				log4j.debug("<createComplete> Valida si existe la empresa");
				Empresa empresa = empresaDao.read(idEmpresaCreate);
				
				if(empresa!=null){
					Object object= new EmpresaParametroDto();
					log4j.debug("<createComplete> Proceso de valicación contra empresa Parametro");
//						List<VacancyDto> lsVacancyDtoOut= null;
					if(lsEmpresaParametroPublicacionDto==null){
						EmpresaParametroDto empresaParametroDto=new EmpresaParametroDto();
						empresaParametroDto.setIdEmpresaConf(Constante.ID_EMPRESA_CONF_MASIVO);	//2
						empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_ATRIBUTO_REQUERIDO_POSICION);
						object=empresaParametroService.get(empresaParametroDto,true);
					}else{
						object=lsEmpresaParametroPublicacionDto;
					}
					if(object instanceof EmpresaParametroDto ){
						log4j.error("<createComplete> No existen criterios en la tabla EMPRESA_PARAMETRO para publicar la posicion");
						responseDto.setStatus("ERROR");
						responseDto.setType(Mensaje.SERVICE_TYPE_ERROR);
						responseDto.setCode(Mensaje.SERVICE_CODE_002);
						responseDto.setMessage(Mensaje.MSG_ERROR);
						return responseDto;
					}else{
						
						List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
						log4j.debug("<createComplete> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
						lsEmpresaParametroPublicacionDto = lsEmpresaParametroDto;
						// >>>>>>>>   II. Realiza calculos en base a DTO  <<<<<<<<   
						Iterator<EmpresaParametroDto> itParametro = lsEmpresaParametroDto.iterator();
						while(itParametro.hasNext()){
							EmpresaParametroDto empParametroDto = itParametro.next();
							if(empParametroDto.getFuncion()!=null && !empParametroDto.getFuncion().trim().equals("")){
								log4j.debug("<createComplete>\n valor: " + empParametroDto.getValor() 
										+ "\n funcion: "+empParametroDto.getFuncion());
								Class<?>[] argumentTypes = { VacancyDto.class};
								Object[] arguments = { vacancyDto };
//									Object resp=
										UtilsTCE.executeReflexion(new ParametersTCE(),empParametroDto.getFuncion(),null, argumentTypes,arguments);
								//if(resp instanceof VacancyDto){	 vacancyDto = (VacancyDto)resp;  }
							}
						}
						log4j.debug("<createComplete> VALIDAR CONTRA EMPRESA PARAMETRO's ");
						// >>>>>>>>   III. VALIDAR CONTRA EMPRESA PARAMETRO's  <<<<<<<<  
						List<VacancyDto> lsPositionsDtoOut = 
								//ParametersTCE.mainResumePosicionDtoValidations(vacancyDto, lsEmpresaParametroDto); /* Fix:Evalle: Elementos anidados
								ValidadorPublicacion.mainResumePosicionDtoValidations(vacancyDto, lsEmpresaParametroDto);
						
						if(lsPositionsDtoOut!=null && !lsPositionsDtoOut.isEmpty()){
							// Hay errores, se devuelve lista de errores 
							log4j.debug("<createComplete> errores encontrados: "+ lsPositionsDtoOut.size() );
							return lsPositionsDtoOut;
						}else{
							//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
							log4j.debug("VacancyDto enviado a Persistencia: " + vacancyDto );
							Object objResponse = saveOrReplaceVacancyExterno(vacancyDto, empresa);
							log4j.debug("<createComplete> despues de saveOrReplaceVacancyExterno ");
							
							//Regresar mensaje satisfactorio o de error en Save
							if(objResponse instanceof VacancyDto){
								//Es error y se envia a caller
								responseDto = (VacancyDto)objResponse;
								log4j.debug("<createComplete> responseDto"+ responseDto );
							}else if(objResponse instanceof Posicion){
								//es Pojo y se envia a Solr
								log4j.debug("<createComplete> es Pojo y se envia a Solr");
								Posicion posicionDB = (Posicion)objResponse;
								//enviar a Solr
								solrService.threadwriteInSolr(posicionDB);							
								responseDto.setStatus("OK");
								responseDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);							
							}
							//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
						}
					}
				}else{
					log4j.error("<createComplete> No existe la empresa solicitada, se envia error de respuesta");
					responseDto.setStatus("ERROR");
					responseDto.setType(Mensaje.SERVICE_TYPE_ERROR);
					responseDto.setCode(Mensaje.SERVICE_CODE_002);
					responseDto.setMessage(Mensaje.MSG_ERROR_EMPTY);
				}
				
			} catch (Exception e) {
				log4j.error("<createComplete> Error al crear la posición:", e);
				responseDto.setCode(Mensaje.SERVICE_CODE_000);
				responseDto.setType(Mensaje.SERVICE_TYPE_FATAL);
				responseDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
				e.printStackTrace();
				return responseDto;
			}
		}else{
			//Error de falta de parametros
			log4j.error("<createComplete> La posición no tiene asignada una empresa");
			responseDto.setStatus("ERROR");
			responseDto.setCode(Mensaje.SERVICE_CODE_000);
			responseDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			responseDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
		}
		
		log4j.info("<createComplete> Fin...");
		return responseDto;
	}*/
	
	/**
	 * Metodo interno encargado de procesar los datos para realizar la persistencia
	 * si esta ya existe o son datos nuevos  <br>
	 * [Los integridad de datos en el DTO debe haberse validado previamente] <br>
	 * <strong>Unicamente para Carga Masiva/Completa </strong>
	 * @param vacancyDto
	 * @param empresa
	 * @return
	 */
	/*private Object saveOrReplaceVacancyExterno(VacancyDto vacancyDto, Empresa empresa){
		log4j.debug("<saveOrReplaceVacancyExterno> Inicio");	
		//1] obtener/Validar el Identificador externo de los datos
		String idExterno = vacancyDto.getClaveInterna();
		if(idExterno==null || idExterno.trim().equals("")){
			log4j.error("<saveOrReplaceVacancyExterno> El Identificador es invalido "+idExterno);
			vacancyDto = new VacancyDto();
			vacancyDto.setCode(Mensaje.SERVICE_CODE_003);
			vacancyDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			vacancyDto.setMessage(Mensaje.MSG_ERROR);
			return vacancyDto;
		}
		try{
			Long idPosicion = null;
			log4j.debug("<saveOrReplaceVacancyExterno> idExterno: " + idExterno );			
			//2] buscar el registro con identificador externo
			log4j.debug("<saveOrReplaceVacancyExterno> Busqueda de persona con ID: "+idExterno);
			Posicion posicionBase = posicionDao.getByCveExterna(idExterno);
			log4j.debug("posicionBase: " + posicionBase );
			posicionDao.flush();
			
			//3]Se crean/cargan objetos asociados
			// POJO para persona Asignada/Creadora de Posición
			RelacionEmpresaPersona relacionZero = null;
			// POJOS que se emplean para TextosNgram, se buscan para Update, se crean para Nueva
			Persona personaAsignada = null;
			PerfilPosicion perfilPosicion = null;
			Perfil perfil = null;
			
			if(posicionBase==null){		//No encontrado (CREATE)
				//3.1] Al ser nuevo, se conservan como null para ser creados posteriormente
				log4j.debug("<saveOrReplaceVacancyExterno> Posicion es null, se generara nueva posicion ###  (CREATE)  ###" );
				posicionBase = new Posicion();
				
			}else{ 	//Encontrado, (UPDATE)
				//3.2] al Existir datos, se busca PersonaAsignada, Perfil y PerfilPosicion correspondientes
				log4j.debug("<saveOrReplaceVacancyExterno> idPosicion encontrado en query  ###  (UPDATE)  ### " + posicionBase.getIdPosicion() );
				//3.2.1] personaAsignada:
				if(posicionBase.getPersona()!=null){
					personaAsignada = posicionBase.getPersona();
				}
				//3.2.2] PerfilPosicion & Perfil
				idPosicion = posicionBase.getIdPosicion();
				List<PerfilPosicion> lsPerfilPosicion = new ArrayList<PerfilPosicion>();
				HashMap<String, Object> currFilters  = new HashMap<String, Object>();
				currFilters.put("posicion.idPosicion", posicionBase.getIdPosicion() );
				lsPerfilPosicion=(List<PerfilPosicion>) perfilPosicionDao.getByFilters(currFilters);
				
				if(lsPerfilPosicion != null && lsPerfilPosicion.size() > 0){
					Iterator<PerfilPosicion> itPerfilPosicion = lsPerfilPosicion.iterator();
					while(itPerfilPosicion.hasNext()){
					PerfilPosicion perfilPosicionTmp = itPerfilPosicion.next();
						if(perfilPosicionTmp.getPerfil() != null && 
								perfilPosicionTmp.getPerfil().getTipoPerfil().getIdTipoPerfil() == Constante.TIPO_PERFIL_INTERNO){
							perfilPosicion = perfilPosicionTmp;
						}
					}				
				}
			}//<-Fin de else (update)
			
			
			// 3.3] Si la persona Asignada es null, se obtienen personas asociadas a empresa, y obtener id de persona asignable 						
			if(personaAsignada==null){
				log4j.debug("<saveOrReplaceVacancyExterno> se obtienen personas asociadas a empresa, y obtener id de persona asignable");
				List<RelacionEmpresaPersona> lsRelacionEmpresaPersona = null;
//				
				lsRelacionEmpresaPersona = relacionEmpresaPersonaDao.getAsociados(empresa.getIdEmpresa());	//getByFilters(currFilters);
				
				if(lsRelacionEmpresaPersona== null || lsRelacionEmpresaPersona.isEmpty()){
					//3.3.1] No hay relaciones de personas con empresa (Regresar error)
					log4j.error("<saveOrReplaceVacancyExterno> No existen asociados validos para publicar Posicion [lsRelacionEmpresaPersona es null o Empty]");
					vacancyDto = new VacancyDto();
					vacancyDto.setCode(Mensaje.SERVICE_CODE_003);
					vacancyDto.setType(Mensaje.SERVICE_TYPE_FATAL);
					vacancyDto.setMessage(Mensaje.MSG_ERROR_EMPTY);
					return vacancyDto;
				}else{
					log4j.debug("<saveOrReplaceVacancyExterno> Se encontraron "+lsRelacionEmpresaPersona.size() + " asociados ");
					//3.3.2] Seleccionar una relacion para asignarle la posicion
					relacionZero = lsRelacionEmpresaPersona.get(0);
					personaAsignada = relacionZero.getPersona();
					log4j.debug("<saveOrReplaceVacancyExterno> Se asigna persona "+ personaAsignada.getIdPersona() +" a posicion");
				}
			}
			//4] Se crea nuevo Pojo para evitar datos innecesarios, incorrectos o duplicados en la persistencia 
			Posicion posicionUpd = vacancyAssembler.getPosicion(new Posicion(), vacancyDto, true);
			posicionUpd.setPersona(personaAsignada);
			EstatusPosicion estatusPosicion = new EstatusPosicion();
			estatusPosicion.setIdEstatusPosicion(Constante.ESTATUS_POSICION_CREADA.longValue());
			posicionUpd.setEstatusPosicion(estatusPosicion);
			// 5] PERSISTENCIA DE LOS DATOS [creacion/actualizacion de posicion] 			
			if(idPosicion==null){		//Es CREATE				
				//5.1] Creación de Posicion
				posicionUpd.setFechaCreacion(DateUtily.getToday());
				log4j.debug("posicionUpd.fechaCreacion " + posicionUpd.getFechaCreacion() );
				Long idPosicionCreated = (Long) posicionDao.create(posicionUpd);
				idPosicion = idPosicionCreated;
				// Se crea la posición con todos los datos de primer nivel
				vacancyDto.setIdPosicion(String.valueOf(idPosicionCreated));
				posicionUpd.setIdPosicion(idPosicion);
				
			}else{  //es UPDATE
				//5.2] Obtener/eliminar datos previos de Posicion [domicilios, perfilTextoNgram (texto-FUnciones)]
				//5.2.1]Agregar los datos requeridos en BD para un Update de Posicion
				posicionUpd.setIdPosicion(idPosicion);
				posicionUpd.setFechaCreacion(posicionBase.getFechaCreacion()!=null?posicionBase.getFechaCreacion():DateUtily.getToday());
				posicionUpd.setFechaModificacion(DateUtily.getToday());
				vacancyDto.setIdPosicion(String.valueOf(idPosicion));
				
				//5.2.2] Obtener perfilPosicion y Perfil ya existentes
				perfilPosicion = perfilPosicionDao.getInterno(idPosicion);				
				log4j.debug("perfilPosicion: " + perfilPosicion );
				if(perfilPosicion!=null){
					//Obtener perfil 
					perfil = perfilPosicion.getPerfil();
					log4j.debug("perfil: " + perfil );					
				}
				//5.2.3]Eliminar datos reemplazables
				domicilioDao.deleteByPosicion(idPosicion);
				if(perfil!= null){
					//BOrrar los TextNgram de este perfil
					perfilTextoNgramDao.deleteByPerfil(perfil.getIdPerfil());
				}
				//5.2.4] Persistir la Posición
				posicionDao.merge(posicionUpd);
				log4j.info("<createComplete> Fin merge Posicion");
			}
			posicionDao.flush();
			
			// 6] Persistir datos Satelitales [Domicilios, texto-FUnciones (perfilTextoNgram)]
			
			Iterator<LocationInfoDto> itDomiciliosDto = vacancyDto.getLocalizacion().iterator();
			Set<Domicilio> domiciliosPosicion = posicionUpd.getDomicilios();
			while(itDomiciliosDto.hasNext()){
				LocationInfoDto infoDto = itDomiciliosDto.next();
				Domicilio domicilio = vacancyAssembler.getDomicilio(new Domicilio(), infoDto);
				domicilio.setPosicion(posicionUpd);
				Object idDom = domicilioDao.create(domicilio);
				if(idDom instanceof Long){
					log4j.debug("Domicilio creado ("+idDom+")");
				}
				domiciliosPosicion.add(domicilio);
			}
			posicionUpd.setDomicilios(domiciliosPosicion); 
			//Se guardan para enviar el Pojo completo de regreso
				//6.2]Insertar PerfilTextoNgram's
			// Crea también un registro en perfilPosicion y perfil de tipo interno con la finalidad 
			// de poder asignarle posteriomente textos nGrams 				
			log4j.debug("#=#=#=#= >>>> vacancyDto.getPerfilesInternos()  " + vacancyDto.getPerfiles() ); //vacancyDto.getPerfilesInternos() );
			if(vacancyDto.getPerfiles()!=null){ //getPerfilesInternos()!=null){
				//6.2.1] Se iteran perfiles internos (por definicion, solo debe ser un perfil interno 
				Iterator<VacancyPerfilPosicionDto> itPerfilPosicionInternoDto = vacancyDto.getPerfiles().iterator(); //getPerfilesInternos().iterator();
				while(itPerfilPosicionInternoDto.hasNext()){
					VacancyPerfilPosicionDto perPosDto = itPerfilPosicionInternoDto.next();
					log4j.debug("VacancyPerfilPosicionDto: " + perPosDto );
					//6.2.2] Si no existe perfil (nuevaPosicion) se crean nuevos registros de perfil y perfilPosicion
					if(perfil==null){
						log4j.debug("<createComplete> A) Creando perfil interno");
						PerfilDto perfilDto = new PerfilDto();
						perfilDto.setIdTipoPerfil(Constante.TIPO_PERFIL_INTERNO);
						perfilDto.setNombre(Constante.NOMBRE_PERFIL_INTERNO);
						perfilDto.setIdEmpresaPerfil(empresa.getIdEmpresa());
						perfilDto.setDescripcion( perPosDto.getDescripcion() );						
						perfil = converter.convert(perfilDto, Perfil.class);
						String idPerfilCreated = ((Long) perfilDao.create(perfil)).toString();
						log4j.info("<create> id perfil interno :" + idPerfilCreated);
						log4j.debug("<createComplete> B) Creando Relacion perfil-Posicion");
						PerfilPosicionDto perfilPosicionDto = new PerfilPosicionDto();
						perfilPosicionDto.setIdPosicion(String.valueOf(posicionUpd.getIdPosicion()));
						PerfilDto perfildto = new PerfilDto();
						perfildto.setIdPerfil(Long.valueOf(idPerfilCreated));
						perfilPosicionDto.setPerfil(perfildto);
							
						perfilPosicionDto.setIdPerfilPosicion(null);
						perfilPosicionDto.setPonderacion("0.5");
						perfilPosicionDto.setIdPosicion(String.valueOf(idPosicion));
						perfilPosicion = converter.convert(perfilPosicionDto, PerfilPosicion.class);
						Object idPerfilPosicionObj = perfilPosicionDao.create(perfilPosicion);
						log4j.info("<create> id PerfilPosicion :" + idPerfilPosicionObj);
					}
					//6.2.3] Se insertan los textos relacionados con Perfil-Interno (Siempre se crean, pues fueron depurados en paso anterior)
					//Set<VacancyPerfilTextoNgramDto> textosNgram = perPosDto.getTextos();
					List<VacancyPerfilTextoNgramDto> textosNgram = perPosDto.getTextos();
					 Iterator<VacancyPerfilTextoNgramDto> itTextoNgram = textosNgram.iterator();
					 while(itTextoNgram.hasNext()){
						 VacancyPerfilTextoNgramDto textoNgramDto = itTextoNgram.next();
						 PerfilTextoNgram perfilTextoNgram = new PerfilTextoNgram();
						 perfilTextoNgram.setPerfil(perfil);
						 perfilTextoNgram.setTexto(textoNgramDto.getTexto());
						 perfilTextoNgram.setPonderacion(Short.valueOf(textoNgramDto.getPonderacion()));
						 String idPerfilTextoNgramCloned = perfilTextoNgramDao.create(perfilTextoNgram).toString();
						 log4j.info("<create> Despues create PerfilTextoNgram :" + idPerfilTextoNgramCloned);
					 }
				}
			}
			
			//6.3] Crear/Actualizar  politicas-Valor 
			//Se tiene que ser referencia al servicio que esta en appTrnsactionalStructured
			updatePolicies(vacancyDto);
			//6.4] Si todo salio bien, se regresa el Pojo para publicar en Solr 
			return posicionUpd;
			
		}catch (Exception e){
			log4j.error("<saveOrReplaceVacancyExterno> error: ", e);
			vacancyDto.setCode(Mensaje.SERVICE_CODE_012);
			vacancyDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			vacancyDto.setMessage(Mensaje.MSG_ERROR);
		}
		log4j.debug("<saveOrReplaceVacancyExterno> fin " + vacancyDto.getClaveInterna());
		return vacancyDto;
	}*/
	
	/**
	 * Procesa multiples cv's en un llamado de Cliente Rest
	 * @param lsCurriculumDto
	 * @return
	 */
	//@Transactional(propagation=Propagation.NOT_SUPPORTED)
	/*public Object createMasive(MasivoDto inDto){
		StringBuilder sb=new StringBuilder("[");
		log4j.debug("<createMasive> Se valido DTO de entrada, se procede a obtener listado de Posiciones...");
		log4j.debug("<createMasive> claveEmpresa: " + inDto.getClaveEmpresa() );
		log4j.debug("<createMasive> posiciones.size: " + inDto.getPosiciones().size());
		Iterator<VacancyDto> itCvs = inDto.getPosiciones().iterator();
		String idExternoTmp ;
		Object object;
		while(itCvs.hasNext()){
			VacancyDto dto = itCvs.next();
			idExternoTmp = dto.getClaveInterna();
			dto.setClaveEmpresa(inDto.getClaveEmpresa());
			if(dto.getIdEmpresa()==null){
				dto.setIdEmpresa(inDto.getClaveEmpresa());
			}
			if(dto.getIdEmpresaConf()==null){
				log4j.debug("Se asigna idConf de MasiveDto "+ inDto.getIdEmpresaConf() );
				dto.setIdEmpresaConf(inDto.getIdEmpresaConf());
			}
			object = createComplete(dto); 
			sb.append("{").
			append("\"claveInterna\":\"").append(idExternoTmp).append("\",").
			append("\"messages\":").append(vacancyAssembler.restResponse(object)).
			append("}");
			if(itCvs.hasNext()){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}*/

	
	
	/**
	 * Modificación de las politicas de la posicion correspondiente
	 * @param VacancyDto
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	/*public VacancyDto updatePolicies(VacancyDto vacancyDto) throws Exception{
		//SE DEBE ADAPTAR A LA NUEVA TABLA POLITICA_M_VALOR...VER APPTYRANSACTIONAL
		
		log4j.debug("<updatePolicies> Inicio...");
		log4j.debug("vacancyDto: " + vacancyDto );
		
		/// Condiciones específicas de las políticas a aplicar 
		if(vacancyDto.getExperienciaLaboralMinima() != null){
			vacancyDto = updatePolicyWorkExperienceKO(vacancyDto, Constante.POLITICA_VALOR_EXP_LABORAL_KO);			
		}
		if(vacancyDto.getIdGradoAcademicoMin() != null || vacancyDto.getIdEstatusEscolarMin() != null){
			log4j.info("<updatePolicies> Actualizando/Insertando política :" + Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN);
			vacancyDto = updatePolicyAcademicKO(vacancyDto, Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN);			
		}
		if(vacancyDto.getIdGradoAcademicoMax() != null || vacancyDto.getIdEstatusEscolarMax() != null){
			log4j.info("<updatePolicies> Actualizando/Insertando política :" + Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX);
			vacancyDto = updatePolicyAcademicKO(vacancyDto, Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX);			
		}
		if(vacancyDto.getIdTipoGeneroRequerido() != null){
			log4j.info("<updatePolicies> Actualizando/Insertando política :" + Constante.POLITICA_VALOR_SEXO_KO);
			vacancyDto = updatePolicyGenderKO(vacancyDto, Constante.POLITICA_VALOR_SEXO_KO);			
		}
		if(vacancyDto.getIdEstadoCivilRequerido() != null){
			log4j.info("<updatePolicies> Actualizando/Insertando política :" + Constante.POLITICA_VALOR_EDO_CIVIL_KO);
			vacancyDto = updatePolicyMaritalStatusKO(vacancyDto, Constante.POLITICA_VALOR_EDO_CIVIL_KO);			
		}
		if(vacancyDto.getEdadMinima() != null || vacancyDto.getEdadMaxima() != null ){
			log4j.info("<updatePolicies> Actualizando/Insertando política :" + Constante.POLITICA_VALOR_RANGO_EDAD_KO);
			vacancyDto = updatePolicyAgeKO(vacancyDto, Constante.POLITICA_VALOR_RANGO_EDAD_KO);			
		}
				
		log4j.info("<updatePolicies> Fin...");		
		return vacancyDto;
	}*/

	/**
	 * Modificación o creación de la politica POLITICA_VALOR_RANGO_EDAD_KO de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	/*public VacancyDto updatePolicyAgeKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyAgeKO> Inicio...");
		// Seccion 5, concepto 16
		boolean seModificoAlgo=false;

		log4j.info("<updatePolicyAgeKO> Actualizando/Insertando política :" + policy);
		// Obtener la politica correspondiente por posicion
		log4j.info("<updatePolicyAgeKO> Antes instanciar lsPoliticaValor");
		List<PoliticaValor> lsPoliticaValor = new ArrayList<PoliticaValor>();
		
		log4j.info("<updatePolicyAgeKO> Antes politicaValorDao.getByPolicy");
		lsPoliticaValor = politicaValorDao.getByPolicy(null, Long.valueOf(vacancyDto.getIdPosicion()), null, policy);			
		log4j.info("<updatePolicyAgeKO> Despues getByPolicy lsPerfilPosicion.size() :" + lsPoliticaValor.size());

		//Los registros de politica se deben eliminar si el valor de entrada existe pero es blanco 
		if( !lsPoliticaValor.isEmpty()  && 
				(vacancyDto.getEdadMinima() != null && vacancyDto.getEdadMinima().equals("")) && 
				(vacancyDto.getEdadMaxima() != null && vacancyDto.getEdadMaxima().equals(""))){
			log4j.info("<updatePolicyAgeKO> Borrando registros de politica");
			politicaValorDao.delete(lsPoliticaValor.get(0));
		}else{
		
			PoliticaValor politicaValor = new PoliticaValor();
			// Actualizar o crear la política correspondiente de acuerdo al atributo actualizado
			if(lsPoliticaValor.size() > 0){
				// Actualiza 
				log4j.info("<updatePolicyAgeKO> Actualizando el registro de politicaValor");
				politicaValor = politicaValorDao.read(lsPoliticaValor.get(0).getIdPoliticaValor());	
				politicaValorDao.update(vacancyAssembler.getPoliticaValor(politicaValor, vacancyDto, seModificoAlgo, policy));
	
			}else{
				
				log4j.info("<updatePolicyAgeKO> Creando el registro de politicaValor");
				// Obtiene la información de la política correspondiente 
				List<Politica> lsPolitica = new ArrayList<Politica>();
				HashMap<String, Object> currFilters  = new HashMap<String, Object>();
				currFilters.put("clavePolitica",policy);
				log4j.info("<updatePolicyAgeKO> Antes politicaDao.getByFilters");
				lsPolitica = politicaDao.getByFilters(currFilters);
				log4j.info("<updatePolicyAgeKO> Despues politicaDao.getByFilters");
				if(lsPolitica.size() > 0){
					politicaValor.setPolitica(lsPolitica.get(0));
					Posicion posicion = new Posicion();
					posicion.setIdPosicion(Long.valueOf(vacancyDto.getIdPosicion()));
					politicaValor.setPosicion(posicion);
					if(vacancyDto.getEdadMinima() != null){
						politicaValor.setValorMinRango1(vacancyDto.getEdadMinima());
					}
					if(vacancyDto.getEdadMaxima() != null){
						politicaValor.setValorMaxRango1(vacancyDto.getEdadMaxima());
					}
					Concepto concepto = new Concepto();
					concepto.setIdConcepto(Constante.CONCEPTO_EDAD);
					politicaValor.setConcepto(concepto);
					politicaValor.setFechaCreacion(DateUtily.getToday());					
					politicaValorDao.create(politicaValor).toString();
				}
			}
		}
		log4j.info("<updatePolicyAgeKO> Fin...");
		return vacancyDto;
	}*/
	
	/**
	 * Modificación o creación de la politica de POLITICA_VALOR_EDO_CIVIL_KO de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	/*public VacancyDto updatePolicyMaritalStatusKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyMaritalStatusKO> Inicio...");
		// Obtener la politica correspondiente por posicion 
		boolean seModificoAlgo=false;
		List<PoliticaValor> lsPoliticaValor = new ArrayList<PoliticaValor>();

		log4j.info("<updatePolicyMaritalStatusKO> Antes politicaValorDao.getByPolicy");
		lsPoliticaValor = politicaValorDao.getByPolicy(null, Long.valueOf(vacancyDto.getIdPosicion()), null, policy);			
		log4j.info("<updatePolicyMaritalStatusKO> Despues getByPolicy lsPoliticaValor.size() :" + lsPoliticaValor.size());
		
		// Los registros de politica se deben eliminar si el valor de entrada existe pero es blanco 
		if((!lsPoliticaValor.isEmpty()  && lsPoliticaValor.get(0).getPoliticaMEstadoCivils().size() > 0) && vacancyDto.getIdEstadoCivilRequerido().equals("")){
			log4j.info("<updatePolicyMaritalStatusKO> Borrando registros de politica");
			politicaValorDao.delete(lsPoliticaValor.get(0));
		}else{
			log4j.info("<updatePolicyMaritalStatusKO> Creando o actualizando los registros de politica");
			PoliticaMEstadoCivil politicaMEstadoCivil = new PoliticaMEstadoCivil();
			/ Actualizar o crear la política correspondiente de acuerdo al atributo actualizado 
			if(lsPoliticaValor.size() > 0){
				if (lsPoliticaValor.get(0).getPoliticaMEstadoCivils().size() > 0){
					// Actualiza registro en PoliticaMEstadoCivil
					Iterator<PoliticaMEstadoCivil> itPoliticaMEstadoCivil = lsPoliticaValor.get(0).getPoliticaMEstadoCivils().iterator();
					log4j.info("<updatePolicyMaritalStatusKO> Actualizando el registro de PoliticaMEstadoCivil");
					politicaMEstadoCivil = politicaMEstadoCivilDao.read(itPoliticaMEstadoCivil.next().getIdPoliticaMEstadoCivil());	
					politicaMEstadoCivilDao.update(vacancyAssembler.getPoliticaMEstadoCivil(politicaMEstadoCivil, vacancyDto, seModificoAlgo));
				}
			}else{
				// Crea registro en PoliticaValor 					
				log4j.info("<updatePolicyMaritalStatusKO> Creando el registro de politicaValor");
				PoliticaValor politicaValor = new PoliticaValor();
				// Obtiene la información de la política correspondiente 
				List<Politica> lsPolitica = new ArrayList<Politica>();
				HashMap<String, Object> currFilters  = new HashMap<String, Object>();
				currFilters.put("clavePolitica",policy);
				log4j.info("<updatePolicyMaritalStatusKO> Antes politicaDao.getByFilters");
				lsPolitica = politicaDao.getByFilters(currFilters);
				log4j.info("<updatePolicyMaritalStatusKO> Despues politicaDao.getByFilters lsPolitica.size() :" + lsPolitica.size());
				if(lsPolitica.size() > 0){
					politicaValor.setPolitica(lsPolitica.get(0));
					Posicion posicion = new Posicion();
					posicion.setIdPosicion(Long.valueOf(vacancyDto.getIdPosicion()));
					politicaValor.setPosicion(posicion);
					politicaValor.setFechaCreacion(DateUtily.getToday());	
					Concepto concepto = new Concepto();
					concepto.setIdConcepto(Constante.CONCEPTO_ESTADO_CIVIL);
					politicaValor.setConcepto(concepto);
					log4j.info("<updatePolicyMaritalStatusKO> Antes crear politicaValorDao");
					String idPoliticaValorCreated = politicaValorDao.create(politicaValor).toString();
					log4j.info("<updatePolicyMaritalStatusKO> Despues crear politicaValorDao idPoliticaValorCreated :" + idPoliticaValorCreated);
					
					// Crea registro en PoliticaMEstadoCivil 		
					politicaMEstadoCivil = new PoliticaMEstadoCivil();
					if(vacancyDto.getIdEstadoCivilRequerido() != null && !vacancyDto.getIdEstadoCivilRequerido().equals("")){ 
						EstadoCivil estadoCivil = new EstadoCivil();
						estadoCivil.setIdEstadoCivil(Long.valueOf(vacancyDto.getIdEstadoCivilRequerido()));
						politicaMEstadoCivil.setEstadoCivil(estadoCivil);
					}
					PoliticaValor politicaValorNew = new PoliticaValor();
					politicaValorNew.setIdPoliticaValor(Long.valueOf(idPoliticaValorCreated));
					politicaMEstadoCivil.setPoliticaValor(politicaValorNew);
	
					log4j.info("<updatePolicyMaritalStatusKO> Antes politicaMEstadoCivilDao.create");
					String idPoliticaMEstadoCivilCreated = politicaMEstadoCivilDao.create(politicaMEstadoCivil).toString();
					log4j.info("<updatePolicyMaritalStatusKO> Despues politicaMEstadoCivilDao.create idPoliticaMEstadoCivilCreated :" + idPoliticaMEstadoCivilCreated);
				}
			}
		}
		log4j.info("<updatePolicyMaritalStatusKO> Fin...");
		return vacancyDto;
	}	*/

	/**
	 * Modificación o creación de la politica de POLITICA_VALOR_EXP_LABORAL_KO de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	/*public VacancyDto updatePolicyWorkExperienceKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyworkExperienceKO> Inicio...");

		boolean seModificoAlgo=false;

		log4j.info("<updatePolicyworkExperienceKO> Actualizando/Insertando política :" + policy);
		// Obtener la politica correspondiente por posicion
		log4j.info("<updatePolicyworkExperienceKO> Antes instanciar lsPoliticaValor");
		List<PoliticaValor> lsPoliticaValor = new ArrayList<PoliticaValor>();
		
		log4j.info("<updatePolicyworkExperienceKO> Antes politicaValorDao.getByPolicy");
		lsPoliticaValor = politicaValorDao.getByPolicy(null, Long.valueOf(vacancyDto.getIdPosicion()), null, policy);			
		log4j.info("<updatePolicyworkExperienceKO> Despues getByPolicy lsPerfilPosicion.size() :" + lsPoliticaValor.size());

		// Los registros de politica se deben eliminar si el valor de entrada existe pero es blanco 
		if((!lsPoliticaValor.isEmpty()  && lsPoliticaValor.get(0).getValor() != null) && vacancyDto.getExperienciaLaboralMinima().equals("")){
			log4j.info("<updatePolicyworkExperienceKO> Borrando registros de politica");
			politicaValorDao.delete(lsPoliticaValor.get(0));
		}else{
		
			PoliticaValor politicaValor = new PoliticaValor();
			// Actualizar o crear la política correspondiente de acuerdo al atributo actualizado
			if(lsPoliticaValor.size() > 0){
				// Actualiza
				log4j.info("<updatePolicyworkExperienceKO> Actualizando el registro de politicaValor");
				politicaValor = politicaValorDao.read(lsPoliticaValor.get(0).getIdPoliticaValor());	
				politicaValorDao.update(vacancyAssembler.getPoliticaValor(politicaValor, vacancyDto, seModificoAlgo, policy));
	
			}else{
				// Crea
				log4j.info("<updatePolicyworkExperienceKO> Creando el registro de politicaValor");
				// Obtiene la información de la política correspondiente 
				List<Politica> lsPolitica = new ArrayList<Politica>();
				HashMap<String, Object> currFilters  = new HashMap<String, Object>();
				currFilters.put("clavePolitica",policy);
				log4j.info("<updatePolicyworkExperienceKO> Antes politicaDao.getByFilters");
				lsPolitica = politicaDao.getByFilters(currFilters);
				log4j.info("<updatePolicyworkExperienceKO> Despues politicaDao.getByFilters");
				if(lsPolitica.size() > 0){
					politicaValor.setPolitica(lsPolitica.get(0));
					Posicion posicion = new Posicion();
					posicion.setIdPosicion(Long.valueOf(vacancyDto.getIdPosicion()));
					politicaValor.setPosicion(posicion);
					politicaValor.setValor(vacancyDto.getExperienciaLaboralMinima());
					politicaValor.setFechaCreacion(DateUtily.getToday());		
					Concepto concepto = new Concepto();
					concepto.setIdConcepto(Constante.CONCEPTO_EXPERIENCIA_LABORAL);
					politicaValor.setConcepto(concepto);					
					politicaValorDao.create(politicaValor).toString();
				}
			}
		}
		log4j.info("<updatePolicyworkExperienceKO> Fin...");
		return vacancyDto;
	}*/
	
	/**
	 * Modificación o creación de la politica de POLITICA_VALOR_SEXO_KO de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	/*public VacancyDto updatePolicyGenderKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyGenderKO> Inicio...");

		// Obtener la politica correspondiente por posicion 
		List<PoliticaValor> lsPoliticaValor = new ArrayList<PoliticaValor>();
		PoliticaValor politicaValor = new PoliticaValor();

		log4j.info("<updatePolicyGenderKO> Antes politicaValorDao.getByPolicy");
		lsPoliticaValor = politicaValorDao.getByPolicy(null, Long.valueOf(vacancyDto.getIdPosicion()), null, policy);			
		log4j.info("<updatePolicyGenderKO> Despues getByPolicy lsPoliticaValor.size() :" + lsPoliticaValor.size());
		politicaValorDao.flush();
		// Los registros de politica se deben eliminar si el valor de entrada existe pero es blanco 
		if((!lsPoliticaValor.isEmpty()) && vacancyDto.getIdTipoGeneroRequerido().equals("")){
			log4j.info("<updatePolicyGenderKO> Borrando registros de politica");
			politicaValorDao.delete(lsPoliticaValor.get(0));
		}else{
			log4j.info("<updatePolicyGenderKO> Creando o actualizando los registros de politica");
			// Actualizar o crear la política correspondiente de acuerdo al atributo actualizado 
			if(lsPoliticaValor.size() > 0){
				// Actualiza el valor único de la política en PoliticaValor
				politicaValor = lsPoliticaValor.get(0);
				politicaValor.setValor(vacancyDto.getIdTipoGeneroRequerido());
				politicaValorDao.update(politicaValor);
				
			}else{
				// Crea registro en PoliticaValor 					
				log4j.info("<updatePolicyGenderKO> Creando el registro de politicaValor");
				//Obtiene la información de la política correspondiente 
				List<Politica> lsPolitica = new ArrayList<Politica>();
				HashMap<String, Object> currFilters  = new HashMap<String, Object>();
				currFilters.put("clavePolitica",policy);
				log4j.info("<updatePolicyGenderKO> Antes politicaDao.getByFilters");
				lsPolitica = politicaDao.getByFilters(currFilters);
				log4j.info("<updatePolicyGenderKO> Despues politicaDao.getByFilters lsPolitica.size() :" + lsPolitica.size());
				if(lsPolitica.size() > 0){
					politicaValor.setPolitica(lsPolitica.get(0));
					Posicion posicion = new Posicion();
					posicion.setIdPosicion(Long.valueOf(vacancyDto.getIdPosicion()));
					politicaValor.setPosicion(posicion);
					politicaValor.setFechaCreacion(DateUtily.getToday());		
					// Crea el valor único de la política en PoliticaValor
					politicaValor.setValor(vacancyDto.getIdTipoGeneroRequerido());
					
					Concepto concepto = new Concepto();
					concepto.setIdConcepto(Constante.CONCEPTO_TIPO_GENERO);
					politicaValor.setConcepto(concepto);					
					
					log4j.info("<updatePolicyGenderKO> Antes crear politicaValorDao");
					String idPoliticaValorCreated = politicaValorDao.create(politicaValor).toString();
					log4j.info("<updatePolicyGenderKO> Despues crear politicaValorDao idPoliticaValorCreated :" + idPoliticaValorCreated);
				}
			}
		}
		log4j.info("<updatePolicyGenderKO> Fin...");
		return vacancyDto;
	}*/
	
	/**
	 * Modificación de las politicas POLITICA_VALOR_FORM_ACADEMICA_KO_MIN y POLITICA_VALOR_FORM_ACADEMICA_KO_MAX de la posicion correspondiente
	 * @param VacancyDto
	 * @param String policy
	 * @return VacancyDto
	 * @author Osy
	 * @throws Exception 
	 */
	/*public VacancyDto updatePolicyAcademicKO(VacancyDto vacancyDto, String policy) throws Exception{
		log4j.info("<updatePolicyAcademicKO> Inicio...");

		// Obtener la politica correspondiente por posicion 
		boolean seModificoAlgo=false;
		List<PoliticaValor> lsPoliticaValor = new ArrayList<PoliticaValor>();
		String modo = null;
		
		if (policy.equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN))
			modo = "MIN";
		else if (policy.equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX))
			modo = "MAX";
			
		
		log4j.info("<updatePolicyAcademicKO> Antes politicaValorDao.getByPolicy");
		lsPoliticaValor = politicaValorDao.getByPolicy(null, Long.valueOf(vacancyDto.getIdPosicion()), null, policy);			
		log4j.info("<updatePolicyAcademicKO> Despues getByPolicy lsPoliticaValor.size() :" + lsPoliticaValor.size());

		// Los registros de politica se deben eliminar si el valor de entrada existe pero es blanco 
		if((lsPoliticaValor.size() > 0  && lsPoliticaValor.get(0).getPoliticaEscolaridads().size() > 0) && 
				((vacancyDto.getIdGradoAcademicoMin() != null && vacancyDto.getIdGradoAcademicoMin().equals("")) || 
				(vacancyDto.getIdGradoAcademicoMax() != null && vacancyDto.getIdGradoAcademicoMax().equals("")))){
			log4j.info("<updatePolicyAcademicKO> Borrando registros de politica valor.");
			politicaValorDao.delete(lsPoliticaValor.get(0));
			log4j.info("<updatePolicyAcademicKO> Después de borrar registros de politica valor");
		}else{
		
			PoliticaEscolaridad politicaEscolaridad = new PoliticaEscolaridad();
			// Actualizar o crear la política correspondiente de acuerdo al atributo actualizado 
			if(lsPoliticaValor.size() > 0){
				if (lsPoliticaValor.get(0).getPoliticaEscolaridads().size() > 0){
					// Actualiza registro en PoliticaEscolaridad
					Iterator<PoliticaEscolaridad> itPoliticaEscolaridad = lsPoliticaValor.get(0).getPoliticaEscolaridads().iterator();
					log4j.info("<updatePolicyAcademicKO> Actualizando el registro de PoliticaEscolaridad");
					politicaEscolaridad = politicaEscolaridadDao.read(itPoliticaEscolaridad.next().getIdPoliticaEscolaridad());	
					politicaEscolaridadDao.update(vacancyAssembler.getPoliticaEscolaridad(politicaEscolaridad, vacancyDto, seModificoAlgo, modo));
				}
			}else{
				// Crea registro en PoliticaValor 					
				log4j.info("<updatePolicyAcademicKO> Creando el registro de politicaValor");
				PoliticaValor politicaValor = new PoliticaValor();
				// Obtiene la información de la política correspondiente
				List<Politica> lsPolitica = new ArrayList<Politica>();
				HashMap<String, Object> currFilters  = new HashMap<String, Object>();
				currFilters.put("clavePolitica",policy);
				log4j.info("<updatePolicyAcademicKO> Antes politicaDao.getByFilters");
				lsPolitica = politicaDao.getByFilters(currFilters);
				log4j.info("<updatePolicyAcademicKO> Despues politicaDao.getByFilters lsPolitica.size :" + lsPolitica.size());
				if(lsPolitica.size() > 0){
					politicaValor.setPolitica(lsPolitica.get(0));
					Posicion posicion = new Posicion();
					posicion.setIdPosicion(Long.valueOf(vacancyDto.getIdPosicion()));
					politicaValor.setPosicion(posicion);
					politicaValor.setFechaCreacion(DateUtily.getToday());					
					Concepto concepto = new Concepto();
					concepto.setIdConcepto(Constante.CONCEPTO_ESCOLARIDAD);
					politicaValor.setConcepto(concepto);					
					log4j.info("<updatePolicyAcademicKO> Antes crear politicaValor");
					String idPoliticaValorCreated = politicaValorDao.create(politicaValor).toString();
					log4j.info("<updatePolicyAcademicKO> Despues crear politicaValor idPoliticaValorCreated :" + idPoliticaValorCreated);
					// Crea registro en PoliticaEscolaridad 			
					politicaEscolaridad = new PoliticaEscolaridad();
					if(vacancyDto.getIdGradoAcademicoMin() != null && !vacancyDto.getIdGradoAcademicoMin().equals("")){ 
						log4j.info("<updatePolicyAcademicKO> Instanciando gradoAcademico minimo");
						GradoAcademico gradoAcademico = new GradoAcademico();
						gradoAcademico.setIdGradoAcademico(Long.valueOf(vacancyDto.getIdGradoAcademicoMin()));
						politicaEscolaridad.setGradoAcademico(gradoAcademico);
					}
					if(vacancyDto.getIdEstatusEscolarMin() != null && !vacancyDto.getIdEstatusEscolarMin().equals("")){ 
						log4j.info("<updatePolicyAcademicKO> Instanciando estatusEscolar minimo");
						EstatusEscolar estatusEscolar = new EstatusEscolar();
						estatusEscolar.setIdEstatusEscolar(Long.valueOf(vacancyDto.getIdEstatusEscolarMin()));
						politicaEscolaridad.setEstatusEscolar(estatusEscolar);
					}
					if(vacancyDto.getIdGradoAcademicoMax() != null && !vacancyDto.getIdGradoAcademicoMax().equals("")){ 
						log4j.info("<updatePolicyAcademicKO> Instanciando gradoAcademico máximo");
						GradoAcademico gradoAcademico = new GradoAcademico();
						gradoAcademico.setIdGradoAcademico(Long.valueOf(vacancyDto.getIdGradoAcademicoMax()));
						politicaEscolaridad.setGradoAcademico(gradoAcademico);
					}
					if(vacancyDto.getIdEstatusEscolarMax() != null && !vacancyDto.getIdEstatusEscolarMax().equals("")){ 
						log4j.info("<updatePolicyAcademicKO> Instanciando estatusEscolar máximo");
						EstatusEscolar estatusEscolar = new EstatusEscolar();
						estatusEscolar.setIdEstatusEscolar(Long.valueOf(vacancyDto.getIdEstatusEscolarMax()));
						politicaEscolaridad.setEstatusEscolar(estatusEscolar);
					}
					PoliticaValor politicaValorNew = new PoliticaValor();
					politicaValorNew.setIdPoliticaValor(Long.valueOf(idPoliticaValorCreated));
					politicaEscolaridad.setPoliticaValor(politicaValorNew);
					log4j.info("<updatePolicyAcademicKO> Antes crear politicaEscolaridadDao");
					String idPoliticaEscolaridadCreated = politicaEscolaridadDao.create(politicaEscolaridad).toString();
					log4j.info("<updatePolicyAcademicKO> Antes crear politicaEscolaridadDao idPoliticaEscolaridadCreated :" + idPoliticaEscolaridadCreated);
				}
			}			
		}		
		log4j.info("<updatePolicyAcademicKO> Fin...");
		return vacancyDto;
	}*/
	
	
	/*  ************************************************   PUBLICATE   ************************************** */
	/**
	 * Valida y publica o programa una posición
	 * @author TCE  
	 * @param VacancyDto vacancyDtoReq Información de la posición  
	 * @return List<VacancyDto> Exito : Mensaje Vacío o Error : 
	 * Informacion de la posición y atributos que fallaron cuya posición se publico o se intento publicar 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<VacancyDto> setVacancyPublication(VacancyDto vacancyDtoReq) throws Exception{
		log4j.debug("<setVacancyPublication> Inicio...getIdPosicion:"+vacancyDtoReq.getIdPosicion()+
				" getIdPersona:"+vacancyDtoReq.getIdPersona()+
				" getIdEmpresaConf:"+vacancyDtoReq.getIdEmpresaConf());	
		List<VacancyDto> lsVacancyDtoOut=new ArrayList<VacancyDto>();
		if(vacancyDtoReq != null && vacancyDtoReq.getIdPosicion() != null &&
		   vacancyDtoReq.getIdPersona() != null	&& 
		   vacancyDtoReq.getIdEmpresaConf() != null){
			 posicion = posicionDao.read(Long.valueOf(vacancyDtoReq.getIdPosicion()));
			if(posicion != null){
				
				/* ******************************************************************  */
				Object object;
				//Se solicitan los empresa parametro para Publicación
				
				if(lsEmpresaParametroPublicacionDto==null){
					//No existen en el cache (Global) se solicita al servicio
					EmpresaParametroDto empresaParametroDto=new EmpresaParametroDto();
					empresaParametroDto.setIdEmpresaConf(vacancyDtoReq.getIdEmpresaConf());	//1
					empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_ATRIBUTO_REQUERIDO_POSICION);
					object=empresaParametroService.get(empresaParametroDto,true);
				}else{
					//Ya existe en sesion, se reasigna al objeto de empresaParametro
					object=lsEmpresaParametroPublicacionDto;
				}
				if(object instanceof EmpresaParametroDto ){
					//Si el servicio regreso error de existencia, se envia error a vista
					log4j.error("<setVacancyPublication> No existen criterios en la tabla EMPRESA_PARAMETRO para publicar la posicion");
					//lsVacancyDtoOut=new ArrayList<VacancyDto>();
					lsVacancyDtoOut.add(new VacancyDto(Mensaje.SERVICE_CODE_002,
														Mensaje.SERVICE_TYPE_FATAL,
														Mensaje.MSG_ERROR));
					return lsVacancyDtoOut;
				}else{
					//Existen valores, se debe convertir pojo en Dto					
					VacancyDto vacancyDto = vacancyAssembler.getVacancyDto(posicion);
					
					//Se ponene los datos del entorno tracking al objeto vacancyDto
					getCriteriosModeloRSC(vacancyDto);					
					
					//se procede con la iteración para implementar funciones
					List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
					log4j.debug("<setVacancyPublication> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
					lsEmpresaParametroPublicacionDto = lsEmpresaParametroDto;
					
					log4j.debug("<setVacancyPublication> IMPLEMENTAR FUNCIONES DE EMPRESA PARAMETRO's (con reflect)");
					/* >>>>>>>>   II. Realiza calculos en base a DTO  <<<<<<<<  */ 
					Iterator<EmpresaParametroDto> itParametro = lsEmpresaParametroDto.iterator();
					while(itParametro.hasNext()){
						EmpresaParametroDto empParametroDto = itParametro.next();
						if(empParametroDto.getFuncion()!=null && !empParametroDto.getFuncion().trim().equals("")){
							log4j.debug("<setVacancyPublication>\n valor: " + empParametroDto.getValor() 
									+ "\n funcion: "+empParametroDto.getFuncion());
							Class<?>[] argumentTypes = { VacancyDto.class};
							Object[] arguments = { vacancyDto };
							
							//Se ejecutan las funciones (tabla EMPRESA_PARAMETRO)) 
							UtilsTCE.executeReflexion(new ParametersTCE(),empParametroDto.getFuncion(),
													  null, argumentTypes,arguments);
						}
					}
					
					log4j.debug("<setVacancyPublication> VALIDAR CONTRA EMPRESA PARAMETRO's : ");
					
					/* >>>>>>>>   III. VALIDAR CONTRA EMPRESA PARAMETRO's  <<<<<<<<  */
					lsVacancyDtoOut = ValidadorPublicacion.mainResumePosicionDtoValidations(vacancyDto, lsEmpresaParametroDto);
					
					long estatusPosicionFinal=0;	/* Variable que contendra el estatus de la publicación al final del Proceso */
					
					if(lsVacancyDtoOut!=null && !lsVacancyDtoOut.isEmpty()){
						/* Hubo validaciones que no se cumplieron, se copia la lista de errores, y se procesa el estatus a NoPublicado (Creado o anterior) */
						log4j.debug("<createComplete> errores encontrados: "+ lsVacancyDtoOut.size() );
						//Copia de errores en validador
						//lsVacancyDtoOut = lsPositionsDtoOut; /* Se envia el resultado al final del proceso, pues el estatus de Posicion siempre se actualiza */
						
						//Verifica el estatus original de la posicion, para dejarlo en un estatus de "no Publicado" 
						if(posicion.getEstatusPosicion() != null){
							if(posicion.getEstatusPosicion().getIdEstatusPosicion() == Constante.ESTATUS_POSICION_PUBLICADA.longValue()){
								/* Si el estatus original es Publicado, asigna el valor de estatus a Creada */
								estatusPosicionFinal = Constante.ESTATUS_POSICION_CREADA.longValue();						
							}else{
								/* Asigna el valor de estatus al original de la posicion */
								estatusPosicionFinal = posicion.getEstatusPosicion().getIdEstatusPosicion();						
							}
						}
					}else{
						/* >>>>>>>>   IV VALIDACION SE CUMPLIO CORRECTAMENTE, SE PROCEDE AL PROCESO DE PUBLICACIÓN/PROGRAMACIÓN <<<<<< */
						Mes mes;
					
						log4j.debug("<setVacancyPublication> programando la posicion getAnioProgramacion="+vacancyDto.getAnioProgramacion()+
								" getMesProgramacion="+vacancyDto.getMesProgramacion()+
								" getDiaProgramacion="+vacancyDto.getDiaProgramacion());

						//  A) Existen datos de programacion, se verifica, se crea 
						if(vacancyDto.getAnioProgramacion() != null &&
						   vacancyDto.getMesProgramacion() != null &&
						   vacancyDto.getDiaProgramacion() != null){
							log4j.debug("<setVacancyPublication> Verificando fecha progrmacion");
							/* valida fecha */
							if(DateUtily.isValidDate(
									vacancyDto.getAnioProgramacion()+ "/" +
									vacancyDto.getMesProgramacion()+ "/" +
									vacancyDto.getDiaProgramacion(), 
									"yyyy/MM/dd")){
								//fecha Correcta
								log4j.debug("<setVacancyPublication> Fecha progrmacion correcta, almacenando programación...");
								/*posicion.setAnioProgramacion(Short.valueOf(vacancyDto.getAnioProgramacion()));
								mes = new Mes();
								mes.setIdMes(Byte.valueOf(vacancyDto.getMesProgramacion()));
								posicion.setMesByMesProgramacion(mes);
								posicion.setDiaProgramacion(Byte.valueOf(vacancyDto.getDiaProgramacion()));*/
								posicion.setFechaProgramacion(
										DateUtily.creaFecha(String.valueOf(posicion.getAnioProgramacion()), 
															String.valueOf(posicion.getMesByMesProgramacion().getIdMes()), 
															String.valueOf(posicion.getDiaProgramacion())));
								/* Asigna el valor de estatus a PROGRAMADO, todos las validaciones se cumplieron */
								estatusPosicionFinal = Constante.ESTATUS_POSICION_PUBLICADA.longValue();
						
							}else{
								log4j.debug("<setVacancyPublication> Fecha progrmacion no valida");
								/* Asigna el valor de estatus al original de la posicion y genera detalle de error */
								estatusPosicionFinal = posicion.getEstatusPosicion().getIdEstatusPosicion();					
								//lsVacancyDtoOut=new ArrayList<VacancyDto>();
								lsVacancyDtoOut.add(new VacancyDto(Mensaje.SERVICE_CODE_006_6,
													Mensaje.SERVICE_TYPE_INFORMATION,
													Mensaje.MSG_ERROR));
							}
						}else{
							/* Asigna el valor de estatus a PUBLICADO, todos las validaciones se cumplieron */
							log4j.debug("<setVacancyPublication> Publicando...");
							estatusPosicionFinal = Constante.ESTATUS_POSICION_PUBLICADA.longValue();
							posicion.setFechaProgramacion(null);
						}
						
					//  A) Existen datos de caducidad de la programacion, se verifica, se crea 
						if(vacancyDto.getAnioCaducidad() != null && 
						   vacancyDto.getMesCaducidad() != null && 
						   vacancyDto.getDiaCaducidad() != null){
							log4j.debug("<setVacancyPublication> Verificando fecha caducidad");
							/* valida fecha */
							if(DateUtily.isValidDate(
									vacancyDto.getAnioCaducidad()+ "/" +
									vacancyDto.getMesCaducidad()+ "/" +
									vacancyDto.getDiaCaducidad(), 
									"yyyy/MM/dd")){
								//fecha Correcta
								log4j.debug("<setVacancyPublication> Fecha caducidad correcta , almacenando programación...");
								posicion.setAnioCaducidad(Short.valueOf(vacancyDto.getAnioCaducidad()));
								mes = new Mes();
								mes.setIdMes(Byte.valueOf(vacancyDto.getMesCaducidad()));
								posicion.setMesByMesCaducidad(mes);
								posicion.setDiaCaducidad(Short.valueOf(vacancyDto.getDiaCaducidad()));
								posicion.setFechaCaducidad(DateUtily.creaFecha(String.valueOf(posicion.getAnioCaducidad()), 
															String.valueOf(posicion.getMesByMesCaducidad().getIdMes()), 
															String.valueOf(posicion.getDiaCaducidad())));
								posicion.setConcurrente(true);
								// Asigna el valor de estatus a PROGRAMADO, todos las validaciones se cumplieron 
								estatusPosicionFinal = Constante.ESTATUS_POSICION_PUBLICADA.longValue();
						
							}else{
								log4j.debug("<setVacancyPublication> Fecha caducidad no valida");
								// Asigna el valor de estatus al original de la posicion y genera detalle de error
								estatusPosicionFinal = posicion.getEstatusPosicion().getIdEstatusPosicion();					
								//lsVacancyDtoOut=new ArrayList<VacancyDto>();
								lsVacancyDtoOut.add(new VacancyDto(Mensaje.SERVICE_CODE_006_6,
													Mensaje.SERVICE_TYPE_INFORMATION,
													Mensaje.MSG_ERROR));
							}
						}else{
							/* Asigna el valor de estatus a PUBLICADO, todos las validaciones se cumplieron */
							log4j.debug("<setVacancyPublication> Publicando...");
							estatusPosicionFinal = Constante.ESTATUS_POSICION_PUBLICADA.longValue();					
							posicion.setFechaCaducidad(null);
							//posicion.setConcurrente(false);
						}
						
						log4j.debug("<setVacancyPublication> getFechaProgramacion="+posicion.getFechaProgramacion()+
								" getFechaCaducidad="+posicion.getFechaCaducidad());
						
						//si hay ambas fechas, validarlas
						if(posicion.getFechaProgramacion() != null && posicion.getFechaCaducidad() != null){
							if(DateUtily.compareDt1Dt2(DateUtily.setDate(Integer.parseInt(vacancyDto.getAnioProgramacion()),
							   null,vacancyDto.getMesProgramacion(),
							   vacancyDto.getDiaProgramacion(),"0", "0", "0"),
			 				   DateUtily.setDate(Integer.parseInt(vacancyDto.getAnioCaducidad()),
			 				   null,vacancyDto.getMesCaducidad(),
			 				   vacancyDto.getDiaCaducidad(),"0", "0", "0")) == 1){
								
								// Asigna el valor de estatus al original de la posicion y genera detalle de error 
								estatusPosicionFinal = posicion.getEstatusPosicion().getIdEstatusPosicion();					
								//lsVacancyDtoOut=new ArrayList<VacancyDto>();
								lsVacancyDtoOut.add(new VacancyDto(0L,"anioCaducidad",
																   Mensaje.SERVICE_MSG_PUBLIC_DATES_QUARTZ));
								
							}
						//si hay fecha de caducidad debe haber fecha de programación
						}else if(posicion.getFechaProgramacion() == null && posicion.getFechaCaducidad() != null){	
							posicion.setFechaCaducidad(null);
							
							// Asigna el valor de estatus al original de la posicion y genera detalle de error 
							estatusPosicionFinal = posicion.getEstatusPosicion().getIdEstatusPosicion();					
							lsVacancyDtoOut.add(new VacancyDto(Mensaje.SERVICE_CODE_002,
															Mensaje.SERVICE_TYPE_FATAL, 
															Mensaje.MSG_ERROR));
						}else{
							/* Asigna el valor de estatus a PUBLICADO, todos las validaciones se cumplieron */
							log4j.debug("<setVacancyPublication> Publicando...");
							estatusPosicionFinal = Constante.ESTATUS_POSICION_PUBLICADA.longValue();
						}
					}
					
					log4j.debug("<setVacancyPublication> getNumeroPublicaciones="+posicion.getNumeroPublicaciones()+
																	" estatusPosicionFinal="+estatusPosicionFinal);
					
					//Se crea un nuevo registro en la bitacora de posición (solo si se ha publicado una vez)
					if(posicion.getNumeroPublicaciones().shortValue() == (short)0 ){
						log4j.debug("<setVacancyPublication> Se escribe en bitacora ");
						
						//Posicion
						BtcPosicionDto btcPosicionDto=converter.convert(posicion, BtcPosicionDto.class);
						
						//se obtiene PerfilPosicion, en este caso solo hay un perfil
						//si hubiera mas de un perfil sería dado desde la petición en el json
						PerfilPosicion perfilPosicion=posicion.getPerfilPosicions().iterator().next();
						Perfil perfil=perfilPosicion.getPerfil();
						btcPosicionDto.setIdPerfilPosicion(String.valueOf(perfilPosicion.getIdPerfilPosicion()));
						
						//tipo operacion
						btcPosicionDto.setIdTipoOperacionBitacora(Constante.SECUENCIA_BD_BTC_TIPO_OPER_CREATE);
						
						//Se obtiene el idempresa
						Long idEmpresa=empresaConfDao.getEmpresa(vacancyDtoReq.getIdEmpresaConf());
						log4j.debug("<setVacancyPublication>  idEmpresa="+idEmpresa);
						
						//Se obtiene el IdRelacionEmpresaPersona
						HashMap<String, Object> currFilters  = new HashMap<String, Object>();
						currFilters.put("persona.idPersona",Long.parseLong(vacancyDtoReq.getIdPersona()));
						currFilters.put("empresa.idEmpresa",idEmpresa);
						List<RelacionEmpresaPersona> lsRelacionEmpresaPersona= relacionEmpresaPersonaDao.
																					getByFilters(currFilters);
						btcPosicionDto.setIdRelacionEmpresaPersona(String.valueOf(lsRelacionEmpresaPersona.
																		get(0).getIdRelacionEmpresaPersona()));
						
						log4j.debug("<setVacancyPublication>  getIdRelacionEmpresaPersona="+btcPosicionDto.getIdRelacionEmpresaPersona()+
								" getIdPerfilPosicion="+btcPosicionDto.getIdPerfilPosicion());
						
						//domicilio
						Iterator<Domicilio> itDomicilios=posicion.getDomicilios().iterator();
						if(itDomicilios.hasNext()){
							log4j.debug("<setVacancyPublication>  set domicilio");
							btcPosicionDto.setBtcDomicilioDto(converter.convert(itDomicilios.next(), 
																				BtcDomicilioDto.class));
						}
						
						//perfiltexto ngram
						List<BtcPerfilTextoNgramDto> lsBtcPerfilTextoNgramDtos= new ArrayList<BtcPerfilTextoNgramDto>();
						Iterator<PerfilTextoNgram> itPerfilTextoNgram=perfil.getPerfilTextoNgrams().iterator();
						while(itPerfilTextoNgram.hasNext()){							
							lsBtcPerfilTextoNgramDtos.add(converter.convert(itPerfilTextoNgram.next(), 
																		BtcPerfilTextoNgramDto.class));
						}
						btcPosicionDto.setBtcPerfilTextoNgramDtos(lsBtcPerfilTextoNgramDtos);
						
						//competencias
						List<BtcCompetenciaPerfilDto> lsBtcCompetenciaPerfilDto= new ArrayList<BtcCompetenciaPerfilDto>();
						Iterator<CompetenciaPerfil> itCompetenciaPerfil=perfil.getCompetenciaPerfils().iterator();
						while(itCompetenciaPerfil.hasNext()){
							lsBtcCompetenciaPerfilDto.add(converter.convert(itCompetenciaPerfil.next(), 
																		BtcCompetenciaPerfilDto.class));
						}
						btcPosicionDto.setBtcCompetenciaPerfilDtos(lsBtcCompetenciaPerfilDto);
						
						//politica valor
						if (posicion.getPoliticaValors().size() > 0){
							List<BtcPoliticaValorDto> lsBtcPoliticaValorDto=new ArrayList<BtcPoliticaValorDto>();
							BtcPoliticaValorDto btcPoliticaValorDto;
							log4j.info("<getVacancyDto> Existen politicaValors :" + posicion.getPoliticaValors().size());
							Iterator<PoliticaValor> itPoliticaValor = posicion.getPoliticaValors().iterator();
							while(itPoliticaValor.hasNext()){
								PoliticaValor politicaValor = itPoliticaValor.next();								
								
								//PoliticaMValors
								if(politicaValor.getPoliticaMValors() != null &&
									politicaValor.getPoliticaMValors().size() > 0){
									PoliticaMValor politicaMValor;
									
									log4j.info("<getVacancyDto> Existen PoliticaMValors :" + politicaValor.getPoliticaMValors().size());									
									
									//Se obtienen los elementos
									Iterator<PoliticaMValor> itPoliticaMValor=politicaValor.getPoliticaMValors().iterator();
									
									//Se iteran
									while(itPoliticaMValor.hasNext()){
										politicaMValor=itPoliticaMValor.next();	
										btcPoliticaValorDto=new BtcPoliticaValorDto(String.valueOf(politicaValor.
																					getPolitica().getIdPolitica()),
																					String.valueOf(politicaValor.
																					getIdPoliticaValor()),
																					String.valueOf(politicaMValor.
																					getIdPoliticaMValor()));										
										
										//EDO_CIVIL_KO
										if(politicaMValor.getEstadoCivil() != null)
											btcPoliticaValorDto.setIdEstadoCivil(String.valueOf(politicaMValor.
																			getEstadoCivil().getIdEstadoCivil()));
										
										//DISP_VIAJAR_KO
										if(politicaMValor.getTipoDispViajar() != null)
											btcPoliticaValorDto.setIdTipoDispViajar(String.valueOf(politicaMValor.
																		getTipoDispViajar().getIdTipoDispViajar()));
										
										//SEXO_KO
										if(politicaMValor.getTipoGenero() != null)
											btcPoliticaValorDto.setIdTipoGenero(String.valueOf(politicaMValor.
																				getTipoGenero().getIdTipoGenero()));
											
										//FORM_ACADEMICA_KO_MIN, FORM_ACADEMICA_KO_MAX, CERTIFICACIONES									
										//Si es escolaridad max o min
										if(politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
													equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN) ||
										   politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
													equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX)){
												btcPoliticaValorDto.setEscolaridadMax(
													politicaMValor.getPoliticaValor().getPolitica().getClavePolitica().
													equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX) ? "true":"false");
										}
										
										if(politicaMValor.getGradoAcademico() != null){
											btcPoliticaValorDto.setIdGradoAcademico(String.valueOf(politicaMValor.
																		getGradoAcademico().getIdGradoAcademico()));
											
										}
											
										if(politicaMValor.getEstatusEscolar() != null){
											btcPoliticaValorDto.setIdEstatusEscolar(String.valueOf(politicaMValor.
																		getEstatusEscolar().getIdEstatusEscolar()));
										}
																					
										//CERTIFICACIONES
										if(politicaMValor.getDescripcion() != null){
											btcPoliticaValorDto.setDescripcion(String.valueOf(politicaMValor.getDescripcion()));
											log4j.info("<getVacancyDto> getGradoAcademico="+politicaMValor.getGradoAcademico().getIdGradoAcademico()+
													" getDescripcion:" + politicaMValor.getDescripcion());
										}
											
										//IDIOMA_KO	
										if(politicaMValor.getDominio() != null)
											btcPoliticaValorDto.setIdDominio(String.valueOf(politicaMValor.
																				getDominio().getIdDominio()));
										
										if(politicaMValor.getIdioma() != null)
											btcPoliticaValorDto.setIdIdioma(String.valueOf(politicaMValor.
																					getIdioma().getIdIdioma()));
										
										//Se adiciona la politica
										lsBtcPoliticaValorDto.add(btcPoliticaValorDto);										
									}									
								}else{
									btcPoliticaValorDto=new BtcPoliticaValorDto(String.valueOf(politicaValor.
																				getPolitica().getIdPolitica()),
																				String.valueOf(politicaValor.
																				getIdPoliticaValor()));
									
									//EXP_LABORAL_KO , CAMBIO_DOMICILIO_KO
									if(politicaValor.getValor() != null)
										btcPoliticaValorDto.setValor(politicaValor.getValor());
									
									//RANGO_EDAD_KO
									if(politicaValor.getValorMinRango1() != null)
										btcPoliticaValorDto.setValorMin(politicaValor.getValorMinRango1());
									
									if(politicaValor.getValorMaxRango1() != null)
										btcPoliticaValorDto.setValorMax(politicaValor.getValorMaxRango1());
									
									//Se adiciona la politica
									lsBtcPoliticaValorDto.add(btcPoliticaValorDto);
								}								
								
							}
							btcPosicionDto.setBtcPoliticaValors(lsBtcPoliticaValorDto);
						}						
						
						//Se crea el registro						
						log4j.debug("<setVacancyPublication>  resp:"+	gson.toJson(btcPosicionDto));
						bitacoraPosicionService.create(btcPosicionDto);
						
					}
					
					
					/* >>>>>>>>   V. UNA VEZ DETERMINADO ESTATUS Y MENSAJE DE SALIDA, SE ACTUALIZA POSICIÓN <<<<<< */
					EstatusPosicion estatusPosicion = new EstatusPosicion();
					estatusPosicion.setIdEstatusPosicion(estatusPosicionFinal);
					if(posicion.getEstatusPosicion() != null){
						if(estatusPosicionFinal != posicion.getEstatusPosicion().getIdEstatusPosicion() ){
							posicion.setEstatusPosicion(estatusPosicion);
							posicion.setFechaModificacion(DateUtily.getToday());	
							
							//solo para posición publicada
							if(estatusPosicionFinal == Constante.ESTATUS_POSICION_PUBLICADA.longValue()){
								
								//se adiciona 
								posicion.setNumeroPublicaciones((short)(posicion.getNumeroPublicaciones().
																					shortValue() +(short)1));
								
								//se inicializa el estatus de modificado
								posicion.setModificado(true);
							}
							posicionDao.update(posicion);	
						}				
					}
					//-----QUEDA PENDIENTE ---
					//si despues de la validación, se determina que la posición es publicable, se actualiza estatus y se envia a solr   
					/* if(estatusPosicionFinal == Constante.ESTATUS_POSICION_PUBLICADA.longValue()){
						log4j.debug("<setVacancyPublication> Enviando la escritura del documento en Solr"); 
						//Escribir/actualizar documento en solr
						solrService.threadwriteInSolr(posicion);
						log4j.debug("<setVacancyPublication> Después del envío de la escritura del documento en Solr"); 
					}*/
								
					
				}
			}else{ 
				// No existe posicion con ese Id, se genera mensaje de error 
				//lsVacancyDtoOut=new ArrayList<VacancyDto>();
				lsVacancyDtoOut.add(new VacancyDto(Mensaje.SERVICE_CODE_002,
									Mensaje.SERVICE_TYPE_FATAL, Mensaje.MSG_ERROR));
			}
		}else{ 
			lsVacancyDtoOut.add(new VacancyDto(Mensaje.SERVICE_CODE_006,
								Mensaje.SERVICE_TYPE_FATAL, Mensaje.MSG_ERROR));
		}
		log4j.debug("<setVacancyPublication> Fin... lsVacancyDtoOut="+lsVacancyDtoOut.size());
		return lsVacancyDtoOut;			
	}
	
	/**
	 * Se obtiene información con referente al contexto de esquema_tracking
	 * @param vacancyDto
	 */
	private void getCriteriosModeloRSC(VacancyDto vacancyDto){
		
		log4j.debug("<getCriteriosRacking> getIdPosicion="+
					vacancyDto.getIdPosicion());
		
		idPosicion=Long.parseLong(vacancyDto.getIdPosicion());
		
		//1.Se requiere solo un ModeloRSC Principal
		if(modeloRscPosDao.readModeloRscPosPrincipal(idPosicion) != null){
			vacancyDto.setExisteModeloRscPrincipal(true);
		}else{
			vacancyDto.setExisteModeloRscPrincipal(false);
		}
		
		//2.Se requiere solo un ModeloRSC Postulante
		lsModeloRscPos=modeloRscPosDao.getModeloRscPos(idPosicion,false, false,null,false,null);
		if(lsModeloRscPos != null && 
		   lsModeloRscPos.size() >0){
					vacancyDto.setExisteModeloRscPostulante(true);
		}else{
			vacancyDto.setExisteModeloRscPostulante(false);
		}
		
		//3. Se requiere solo un ModeloRSC Monitor Principal
		lsModeloRscPos=modeloRscPosDao.getModeloRscPos(idPosicion,true,false,true,false,null);

		if(lsModeloRscPos != null && 
		lsModeloRscPos.size() >0){
			vacancyDto.setExisteModeloRscMonitorPrincipal(true);
		}else{
			vacancyDto.setExisteModeloRscMonitorPrincipal(false);
		}
		
		//Se requiere al menos una relación de fase del ModeloRSC Postulante con 
		//los ModelosRSC Monitores
		//Se obtiene una lista de los modelosRSCPos monitor que tienen relación con modelosRSCPos postulante
		lsModeloRscPos=modeloRscPosDao.getModeloRscPos(idPosicion ,true, true,null,false,true);
		
		//se obtiene el numero total de modeosRSCPos de monitores 
		count=modeloRscPosDao.countMonitoresByPosicion(idPosicion);
		
		log4j.debug("<getCriteriosRacking> para HayRelacionModeloRscPostMonts -> num_total_modelos_monitores_= "+count+
				" num_monitores_relacionados_a_postulante:"+(lsModeloRscPos == null ? null:lsModeloRscPos.size())); 
		
		if((lsModeloRscPos != null && 
		   lsModeloRscPos.size() >0) &&
		   count != null){
			
			//el numero debe coincidir
			if(lsModeloRscPos.size() == count){
				vacancyDto.setHayRelacionModeloRscPostMonts(true);
			}else{
				vacancyDto.setHayRelacionModeloRscPostMonts(false);
			}
		}else{
			vacancyDto.setHayRelacionModeloRscPostMonts(false);
		}
						
		//5. Se requiere relacionar al menos un usuario a cada uno de los ModeloRSC Monitores 
		//Se obtiene una lista de los modelosRSCPos monitor secundarios con usuarios
		lsModeloRscPos=modeloRscPosDao.getModeloRscPos(idPosicion ,true, false,null,true,true);
		
		log4j.debug("<getCriteriosRacking> para existeAlmenosUnMonitorPorCadaModeloMonts -> num_total_modelos_monitores_= "+count+
				" num_modelos_monitores_con_usuario:"+(lsModeloRscPos == null ? null:lsModeloRscPos.size())); 
		
		if((lsModeloRscPos != null && 
		   lsModeloRscPos.size() > 0) &&
		   count != null){
					
				//el numero debe coincidir
				if(lsModeloRscPos.size()  == count){
					vacancyDto.setExisteAlmenosUnMonitorPorCadaModeloMonts(true);
				}else{
					vacancyDto.setExisteAlmenosUnMonitorPorCadaModeloMonts(false);
				}
		}else{
			vacancyDto.setExisteAlmenosUnMonitorPorCadaModeloMonts(false);
		}
		
		//6. Se investiga cuantas fases no tienen dias en el ModelosRSC Principal 
		if(vacancyDto.getExisteModeloRscPrincipal()){
			count=modeloRscPosDao.countFaseSinDiasByPosicion(idPosicion);
			
			log4j.debug("<getCriteriosRacking> para AllFasesTienenDiasModeloRscPrincipal -> count_fases_sin_dias= "+count); 
			
			if(count >0){
				vacancyDto.setAllFasesTienenDiasModeloRscPrincipal(false);
			}else{
				vacancyDto.setAllFasesTienenDiasModeloRscPrincipal(true);
			}
		}
		
		//7. Se investiga si todas las fases del modelo postulante tienen relación con las fases de los modelos monitor
		//Se traen todos las fases activas del modelo postulante
		lsIdModeloRscPosFase= modeloRscPosDao.get(idPosicion, false, true);
		log4j.debug("<getCriteriosRacking> para AllFasesPostTienenRelConMonts -> lsIdModeloRscPosFase= "+
					(lsIdModeloRscPosFase == null ? null:lsIdModeloRscPosFase.size())); 
		if(lsIdModeloRscPosFase != null && lsIdModeloRscPosFase.size() > 0){
			itIdModeloRscPosFase=lsIdModeloRscPosFase.iterator();
			StringBuilder sbIds=new StringBuilder();
			while(itIdModeloRscPosFase.hasNext()){				
				sbIds.append(itIdModeloRscPosFase.next()).append(",");				
			}
			
			//Se obtiene una lista de IdModeloRscPosFase activos que estan 
			//relacionados con otras fases de monitores
			lsModeloRscPosFaseDto=modeloRscPosFaseDao.getModeloRscPosFase(sbIds.
										substring(0, sbIds.length() -1).toString());
			
			log4j.debug("<getCriteriosRacking> para AllFasesPostTienenRelConMonts -> lsModeloRscPosFaseDto= "+
					(lsModeloRscPosFaseDto == null ? null:lsModeloRscPosFaseDto.size())); 
			
			//El numero de elementos coinciden entonces todas las fases del postulante tienen 
			//relación con alguna fase de los monitores
			if(lsIdModeloRscPosFase.size() == lsModeloRscPosFaseDto.size()){
				vacancyDto.setAllFasesPostTienenRelConMonts(true);
			}else{
				vacancyDto.setAllFasesPostTienenRelConMonts(false);
			}
		}else{
			vacancyDto.setAllFasesPostTienenRelConMonts(false);
		}
		
		
		log4j.debug("<getCriteriosRacking> existeModeloRscPrincipal= "+vacancyDto.getExisteModeloRscPrincipal()+
				" existeModeloRscPostulante:"+vacancyDto.getExisteModeloRscPostulante()+
				" ExisteModeloRscMonitorPrincipal:"+vacancyDto.getExisteModeloRscMonitorPrincipal()+
				" HayRelacionModeloRscPostMonts:"+vacancyDto.getHayRelacionModeloRscPostMonts()+
				" ExisteAlmenosUnMonitorPorCadaModeloMonts:"+vacancyDto.getExisteAlmenosUnMonitorPorCadaModeloMonts()+
				" AllFasesTienenDiasModeloRscPrincipal="+vacancyDto.getAllFasesTienenDiasModeloRscPrincipal()+
				" AllFasesPostTienenRelConMonts="+vacancyDto.getAllFasesPostTienenRelConMonts());

	}
	
	/**
	 * Obtiene el parametro del número de documentos para generar el modelo (empresa TCE) de la base de datos, 
	 * @param Ninguno
	 * @return QUERY Valor del parametro numDocsForModel
	 */			
	@SuppressWarnings("unchecked")
	private int getParameterCalendar(String contexto, int valorDefault) throws Exception{
		empresaParametroDto=new EmpresaParametroDto();
		empresaParametroDto.setIdEmpresaConf(Constante.IDCONF_NULL);
		empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_APLICACION_GENERAL);
		empresaParametroDto.setContexto(contexto);	
		Object object=empresaParametroService.get(empresaParametroDto,false);
		if(object instanceof EmpresaParametroDto ){
			return valorDefault;
		}else{
			List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
			log4j.debug("<getParameterCalendar> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
			return Integer.parseInt(lsEmpresaParametroDto.get(0).getValor());
		}	
	}
	
	
}
