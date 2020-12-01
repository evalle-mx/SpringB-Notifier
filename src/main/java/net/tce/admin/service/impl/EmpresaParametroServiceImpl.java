package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import net.tce.admin.service.EmpresaInterfaseService;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.cache.EmpresaConfCache;
import net.tce.cache.ParametrosCache;
import net.tce.dao.EmpresaParametroDao;
import net.tce.dto.EmpresaConfDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.model.Conf;
import net.tce.model.EmpresaParametro;
import net.tce.model.TipoParametro;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import net.tce.util.Validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.amazonaws.util.json.JSONArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Clase donde se aplica las politicas de negocio del servicio de enterprise
 * @author Goyo
 *
 */
@Transactional
@Service("empresaParametroService")
public class EmpresaParametroServiceImpl implements EmpresaParametroService {
	static Logger log4j = Logger.getLogger( EmpresaParametroServiceImpl.class );
	
//	StringBuilder keyCache;
	
	@Inject
	private ConversionService converter;
	
	@Autowired
	private EmpresaInterfaseService empresaInterfaseService;
	
	@Autowired
	private  EmpresaParametroDao empresaParametroDao;


	@Inject
	Gson gson;
	
	

	@Override
	public Object getExt(EmpresaParametroDto empresaParametroDto)
			throws Exception {

		return get(empresaParametroDto, (empresaParametroDto.getUsarCache() == null ? true:(
				empresaParametroDto.getUsarCache().equals("1") ? true:false)) );
	}
	
	/**
	 * Se obtiene una lista de objetos EmpresaParametroDto dependiendo del idConf y idTipoParametro
	 * @param empresaParametroDto
	 * @return
	 * @throws Exception 
	 */
	public synchronized Object get(EmpresaParametroDto empresaParametroDto, boolean usarCache) throws Exception  {
		log4j.debug("<get>\n IdTipoParametro="+empresaParametroDto.getIdTipoParametro()+
				"\n IdConf="+empresaParametroDto.getIdConf() +
				"\n IdEmpresaConf="+empresaParametroDto.getIdEmpresaConf());
		
		empresaParametroDto=filtros(empresaParametroDto, Constante.F_GET);
		
		if(empresaParametroDto.getCode() == null){
			StringBuilder keyCache;
			
			if(empresaParametroDto.getIdConf()==null){
				//si idEmpresaConf no es tipo null, se obtiene el idConf
				if(!empresaParametroDto.getIdEmpresaConf().equals(Constante.IDCONF_NULL)){
					EmpresaConfDto empresaConfDto=EmpresaConfCache.get(Long.valueOf(
															empresaParametroDto.getIdEmpresaConf()));
					//log4j.debug("%&%& empresaConfDto="+empresaConfDto);
					//Se pone el idConf
					if(empresaConfDto != null){
						empresaParametroDto.setIdConf(empresaConfDto.getIdConf().toString());
					}else{
						log4j.error("El idEmpresaconf:" + empresaParametroDto.getIdConf() + ", no esta registrado.");
						 empresaParametroDto=new EmpresaParametroDto();
						 empresaParametroDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
						 empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
						 empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
						 return empresaParametroDto;
					}
				}else{
					empresaParametroDto.setIdConf(Constante.IDCONF_NULL);
				}
			}

			//keyCache= idConf + idTipoParametro
			keyCache=new StringBuilder(empresaParametroDto.getIdConf()).
					append(empresaParametroDto.getIdTipoParametro());
			
			List<EmpresaParametroDto> lsParametrosDto =null;
			//Todo bien
			//if(empresaParametroDto.getCode() == null){
				//Si se usa el cache, se busca primeramente en el cache de Parametros
			if(usarCache){
				lsParametrosDto =(List<EmpresaParametroDto>)ParametrosCache.get(keyCache.toString());
			}
				//La lista no está en Cache o se solicita busqueda en BD
			if(lsParametrosDto == null){
				log4j.debug("<get> " + (usarCache?"NO hay lista en cache: "+keyCache.toString()+", ":"")+ "se realiza la busqueda a BD");
				HashMap<String, Object> defOpFilters = new HashMap<String, Object>();
				List<String> orderby=new ArrayList<String>();
					//solo si idconf no es nulo
				if(!empresaParametroDto.getIdConf().toUpperCase().equals(Constante.IDCONF_NULL)){
					defOpFilters.put("conf.idConf",Long.valueOf(empresaParametroDto.getIdConf()));
				}
				defOpFilters.put("tipoParametro.idTipoParametro",Long.valueOf(empresaParametroDto.getIdTipoParametro()));
				
				if(empresaParametroDto.getContexto()!=null){
					defOpFilters.put("contexto", empresaParametroDto.getContexto() );
				}
				
				orderby.add(0,"orden");		
				defOpFilters.put(Constante.SQL_ORDERBY,orderby );
				log4j.debug("<get> Se buscan registros ByFilters: " + defOpFilters );
				
				List<EmpresaParametro> lsEmpParametro=empresaParametroDao.getByFilters(defOpFilters);
				lsParametrosDto = new ArrayList<EmpresaParametroDto>();
				
				
				if(lsEmpParametro != null && lsEmpParametro.size() > 0){
					
					Iterator<EmpresaParametro> itEmpParametro=lsEmpParametro.iterator();
					
					while(itEmpParametro.hasNext()){
						EmpresaParametroDto temporalDto = converter.convert(itEmpParametro.next(),EmpresaParametroDto.class );
//						temporalDto.setIdConf(null);
//						temporalDto.setIdEmpresaParametro(null);
//						temporalDto.setIdTipoParametro(null);
//						temporalDto.setDescripcion(null);
						lsParametrosDto.add(temporalDto);
					}
						//Si se usa el cache, se guarda la lista
					if(usarCache){
						ParametrosCache.put(keyCache.toString(), lsParametrosDto);
					}
				}else{
					log4j.error("No hay informacion en la tabla Empresa_Parametro para idConf=" + 
							empresaParametroDto.getIdConf()+" & idTipoParametro="+ empresaParametroDto.getIdTipoParametro() 
							+" Se envia arreglo (lista) vacio: []");
//						 empresaParametroDto=new EmpresaParametroDto();
//						 empresaParametroDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
//						 empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
//						 empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
//						 return empresaParametroDto;
				}
			}
			else if(usarCache) { log4j.debug("<get> Existe lista en Cache ["+keyCache.toString()+"]");}
			log4j.debug("<get> lsParametrosDto: " + lsParametrosDto == null ? null:lsParametrosDto.size());
			return lsParametrosDto;
		}
		else{
			log4j.debug("<get> Error en filtros: "+empresaParametroDto.getMessages());
		}
		return empresaParametroDto;
	}
	/*public synchronized Object get(EmpresaParametroDto empresaParametroDto, boolean usarCache) {
		log4j.debug("<get> IdTipoParametro="+empresaParametroDto.getIdTipoParametro()+
				" IdEmpresaConf="+empresaParametroDto.getIdEmpresaConf());
		empresaParametroDto=filtros(empresaParametroDto, Constante.F_GET);
		if(empresaParametroDto.getCode() == null){
			List<EmpresaParametroDto> lista =null;
			//si idEmpresaConf no es tipo null, se obtiene el idConf
			if(!empresaParametroDto.getIdEmpresaConf().equals(Constante.IDCONF_NULL)){
				EmpresaConfDto empresaConfDto=EmpresaConfCache.get(Long.valueOf(empresaParametroDto.getIdEmpresaConf()));
				//log4j.debug("%&%& empresaConfDto="+empresaConfDto);
				//Se pone el idConf
				if(empresaConfDto != null){
					empresaParametroDto.setIdConf(empresaConfDto.getIdConf().toString());
				}else{
					log4j.error(new StringBuilder("El idEmpresaconf:").
							append(empresaParametroDto.getIdConf()).
							append(", no esta registrado.").toString());
					 empresaParametroDto=new EmpresaParametroDto();
					 empresaParametroDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
					 empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
					 empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
				}
			}else{
				empresaParametroDto.setIdConf(Constante.IDCONF_NULL);
			}
			//Todo bien
			if(empresaParametroDto.getCode() == null){
				//Si se usa el cache
				if(usarCache && empresaParametroDto.getIdConf() != null){
					//se busca en el cache de Parametros
					//llave es : idConf + idTipoParametro
					keyCache=new StringBuilder(empresaParametroDto.getIdConf()).
								append(empresaParametroDto.getIdTipoParametro());
					lista =(List<EmpresaParametroDto>)ParametrosCache.get(keyCache.toString());
				}
				
				if(lista == null){
					log4j.debug("<get> " + (usarCache?"NO hay lista en cache: "+keyCache.toString()+", ":"")+ "se realiza la busqueda a BD");
					HashMap<String, Object> defOpFilters = new HashMap<String, Object>();
					List<String> orderby=new ArrayList<String>();
					//solo si idconf no es nulo
					if(!empresaParametroDto.getIdConf().toUpperCase().equals(Constante.IDCONF_NULL)){
						defOpFilters.put("conf.idConf",Long.valueOf(empresaParametroDto.getIdConf()));
					}
					defOpFilters.put("tipoParametro.idTipoParametro",Long.valueOf(empresaParametroDto.getIdTipoParametro()));
					orderby.add(0,"orden");		
					defOpFilters.put(Constante.SQL_ORDERBY,orderby );
					log4j.debug("<get> Obtener de BD con getByFilters: " + defOpFilters );
					List<EmpresaParametro> lsEmpParametro=empresaParametroDao.getByFilters(defOpFilters);
					if(lsEmpParametro != null && lsEmpParametro.size() > 0){
						lista = new ArrayList<EmpresaParametroDto>();
						Iterator<EmpresaParametro> itEmpParametro=lsEmpParametro.iterator();
						while(itEmpParametro.hasNext()){
							EmpresaParametroDto temporalDto = converter.convert(itEmpParametro.next(),EmpresaParametroDto.class );
							temporalDto.setIdConf(null);
							temporalDto.setIdEmpresaParametro(null);
							temporalDto.setIdTipoParametro(null);
							//temporalDto.setDescripcion(null);
							
							//log4j.debug("%&%& getValor="+temporalDto.getValor());
							lista.add(temporalDto);
						}
						//Si se usa el cache
						if(usarCache){
							//se guarda en cache de Parametros
							ParametrosCache.put(keyCache.toString(),lista);
						}
					}else{
						 log4j.error(new StringBuilder("No hay informacion en la tabla Empresa_Parametro para idConf=").
								 append(empresaParametroDto.getIdConf()).append(" e idTipoParametro=").
								 append(empresaParametroDto.getIdTipoParametro()).toString() );
						 empresaParametroDto=new EmpresaParametroDto();
						 empresaParametroDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
						 empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
						 empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
					}
				}
				else if(usarCache) { log4j.debug("<get> Existe lista en Cache ["+keyCache.toString()+"]");}
				//Si tambien se busca por contexto
				if(empresaParametroDto.getCode() == null ){
					if(empresaParametroDto.getContexto() != null){
						log4j.debug("<get> se filtra por contexto: " + empresaParametroDto.getContexto() ); 
						boolean hayContexto=false;
						lista2 = new ArrayList<EmpresaParametroDto>();
						Iterator<EmpresaParametroDto> itEmpresaParametroDto= lista.iterator();
						while(itEmpresaParametroDto.hasNext()){
							EmpresaParametroDto empresaParametroDto2=itEmpresaParametroDto.next();
							log4j.debug("<get> obj.contexto: " + empresaParametroDto2.getContexto());
							if(empresaParametroDto2.getContexto().equals(empresaParametroDto.getContexto())){
								log4j.debug("<get> Se agrega a segunda Lista ");
								lista2.add(empresaParametroDto2);
								hayContexto=true;
								break;
							}
						}
						//Se encuentra el contexto
						if(hayContexto){
							return lista2;
						}else{
							 log4j.error(new StringBuilder("No hay informacion en la tabla Empresa_Parametro para el contexto=").
									 	 append(empresaParametroDto.getContexto()).toString());
							 empresaParametroDto=new EmpresaParametroDto();
							 empresaParametroDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
							 empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
							 empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
						}
					}else{
						return lista;
					}
				}
			}
		}
		return empresaParametroDto;
	}//*/
	
	
	@SuppressWarnings("unchecked")
	public synchronized Object reloadCache(EmpresaParametroDto empresaParametroDto) throws Exception {
		log4j.debug("<reloadCache> ");
		log4j.debug("<reloadCache>\n IdTipoParametro="+empresaParametroDto.getIdTipoParametro()+
				"\n IdConf="+empresaParametroDto.getIdConf() +
				"\n IdEmpresaConf="+empresaParametroDto.getIdEmpresaConf());
		
		empresaParametroDto=filtros(empresaParametroDto, Constante.F_GET);
		if(empresaParametroDto.getCode() == null){
			List<EmpresaParametroDto> lsParametrosDto =null;
			String idConf = empresaParametroDto.getIdConf();
			
			if(idConf==null){
				if(!empresaParametroDto.getIdEmpresaConf().equals(Constante.IDCONF_NULL)){
					EmpresaConfDto empresaConfDto=EmpresaConfCache.get(Long.valueOf(empresaParametroDto.getIdEmpresaConf()));
					if(empresaConfDto != null){
						idConf = empresaConfDto.getIdConf().toString();
					}else{
						log4j.error("El idEmpresaconf:" + empresaParametroDto.getIdConf() + ", no esta registrado.");
						 empresaParametroDto=new EmpresaParametroDto();
						 empresaParametroDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
						 empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
						 empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
						 return empresaParametroDto;
					}
				}else{
					idConf = Constante.IDCONF_NULL;
				}
			}
			
			
			String keyCache=idConf+empresaParametroDto.getIdTipoParametro();
			log4j.debug("<reloadCache> solicitando nueva información al servicio get...");
			Object getResponse = get(empresaParametroDto, false);
			if(getResponse instanceof List<?>){
				lsParametrosDto = (List<EmpresaParametroDto>)getResponse;
				ParametrosCache.put(keyCache.toString(), lsParametrosDto);
				
				empresaParametroDto=new EmpresaParametroDto();
				empresaParametroDto.setMessage(Mensaje.MSG_OK_UPDATEDCACHE+" para "+keyCache);
				empresaParametroDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);
				empresaParametroDto.setCode(Mensaje.SERVICE_CODE_005);
			}else //if(getResponse instanceof EmpresaParametroDto)
				{
				return getResponse;
			}
			 
			
			
		}else{
			log4j.debug("<reloadCache> Inconsistencia detectada en filtros");
			empresaParametroDto=new EmpresaParametroDto();
			empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
			empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			empresaParametroDto.setCode(Mensaje.SERVICE_CODE_006);
		}

		return empresaParametroDto;
	}
	

	/**
	 * Regresa un objeto dataConf referida al servicio EmpresaParametro
	 * @param empresaParametroDto, objeto data-conf 
	 * @return
	 * @throws Exception 
	 */
	private EmpresaParametroDto dataConf(EmpresaParametroDto empresaParametroDto) throws Exception{
		empresaParametroDto=(EmpresaParametroDto)empresaInterfaseService.dataConf(
				            empresaParametroDto.getIdEmpresaConf(),"EmpresaParametro",new EmpresaParametroDto());
		if(empresaParametroDto == null){
			empresaParametroDto=new EmpresaParametroDto();
			empresaParametroDto.setMessage(Mensaje.SERVICE_MSG_ERROR_DATACONF);
			empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
		}
		return empresaParametroDto;
	}

	/**
	 * Se aplica los filtros de Dataconf para los valores de las propiedades del objeto empresaParametroDto
	 * @param empresaParametroDto, objeto a filtrar correspondiente
	 * @return empresaParametroDto
	 * @throws Exception 
	 */
	private EmpresaParametroDto filtrosDataConf(EmpresaParametroDto empresaParametroDto)throws Exception{
		//Se obtiene el objeto-Dataconf
		EmpresaParametroDto empresaParametroDataConf=new EmpresaParametroDto();
		empresaParametroDataConf.setIdEmpresaConf(empresaParametroDto.getIdEmpresaConf());
		empresaParametroDataConf=dataConf(empresaParametroDataConf);
		//si no hay error
		if(empresaParametroDataConf.getCode() == null){
			empresaParametroDto=(EmpresaParametroDto)Validador.filtrosDataConf(
					empresaParametroDataConf,empresaParametroDto);
		}else{
			empresaParametroDto=empresaParametroDataConf;
		}
		return empresaParametroDto;
	}

	
	/**
	 * Se aplican los filtros a las propiedades correspondientes del objeto empresaParametroDto
	 * @param empresaParametroDto, objeto  que contiene las propiedades para aplicarle los filtros
	 * @param funcion,es un numero que indica el metodo CRUDGD
	 * @return objeto principal que puede ser un objeto mensaje u objeto original
	 * @throws Exception 
	 */
	private EmpresaParametroDto filtros(EmpresaParametroDto empresaParametroDto, int funcion) throws Exception {
		 boolean error=false;
		 if(empresaParametroDto == null){ 
			 error=true;
		 }else{
			 if(empresaParametroDto.getIdEmpresaConf() == null){
				 log4j.debug("<filtros> IdEmpresaConf es requerido");
				 error=true;
		 	 }
		 	
			 //get
			 if(funcion == Constante.F_GET){
				 if(empresaParametroDto.getIdTipoParametro() == null){
					 log4j.debug("<filtros> IdTipoParametro es requerido");
					 error=true; 
				 }
			 }
			 //update
			if(funcion == Constante.F_CREATE){
				if(empresaParametroDto.getIdTipoParametro() == null){
					log4j.debug("<filtros> IdTipoParametro es requerido");
					error=true; 
				}
				if(empresaParametroDto.getIdConf() == null){
					log4j.debug("<filtros> IdConf es requerido");
					error=true; 
				}
				if(empresaParametroDto.getValor() == null){
					log4j.debug("<filtros> Valor es requerido");
					error=true;
				}
				log4j.debug("<filtros> UtilsTCE.isValidId(empresaParametroDto.getOrden()): " + UtilsTCE.isNumeric(empresaParametroDto.getOrden()) );
				if(empresaParametroDto.getOrden() == null || !UtilsTCE.isNumeric(empresaParametroDto.getOrden())){
					log4j.debug("<filtros> Orden es requerido");
					error=true;
				}
			} 
			//update
			 if(funcion == Constante.F_UPDATE){
				 if(empresaParametroDto.getIdEmpresaParametro() == null){
					 log4j.debug("<filtros> IdEmpresaParametro es requerido");
					 error=true; 
				 }
			 }
			//delete
			 if(funcion == Constante.F_DELETE){
				 if(empresaParametroDto.getIdEmpresaParametro() == null){
					 log4j.debug("<filtros> IdEmpresaParametro es requerido");
					 error=true; 
				 }
			 }
		 }
		 //Si algun filtro no se cumplio
		 if(error){
			 empresaParametroDto=new EmpresaParametroDto();
			 empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
			 empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 empresaParametroDto.setCode(Mensaje.SERVICE_CODE_006);
		 }else{
			 //get
			 if(funcion != Constante.F_GET && funcion != Constante.F_DELETE ){
				 try {
						//Se aplican filtros Dataconf
					 empresaParametroDto=filtrosDataConf(empresaParametroDto);
					} catch (Exception e) {
						empresaParametroDto=new EmpresaParametroDto();
						empresaParametroDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
						empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
						empresaParametroDto.setCode(Mensaje.SERVICE_CODE_000);
						e.printStackTrace();
					}
			 }
		 }
		return empresaParametroDto;
	}


	@Transactional
	public Object create(EmpresaParametroDto empresaParametroDto) throws Exception {
		log4j.debug("<create> ");
		empresaParametroDto = filtros(empresaParametroDto, Constante.F_CREATE);
		log4j.debug("<create> despues de filtros: " + empresaParametroDto );
		if(empresaParametroDto.getCode() == null && empresaParametroDto.getMessages()==null){
			try{
				log4j.debug("<create> Set de Campos" );
				EmpresaParametro empParametro = new EmpresaParametro();
//				String idEmpresaParametro = empresaParametroDto.getIdEmpresaParametro();

				TipoParametro tipoParam = new TipoParametro();
				tipoParam.setIdTipoParametro(Long.parseLong(empresaParametroDto.getIdTipoParametro()));
				log4j.debug("<create> Set idTIpoParametro" );
				empParametro.setTipoParametro(tipoParam);
				
				Conf conf = new Conf();
				conf.setIdConf(Long.parseLong(empresaParametroDto.getIdConf()));
				log4j.debug("<create> Set idConf" );
				empParametro.setConf(conf);
				
				if(empresaParametroDto.getOrden()!=null){
					log4j.debug("<create> Set orden: " + empresaParametroDto.getOrden());
					empParametro.setOrden(Long.parseLong(empresaParametroDto.getOrden()));
				}
				if(empresaParametroDto.getContexto()!=null){
					log4j.debug("<create> Set contexto:" + empresaParametroDto.getContexto() );
					empParametro.setContexto(empresaParametroDto.getContexto());
				}
				if(empresaParametroDto.getValor()!=null){
					log4j.debug("valor: \n"+ empresaParametroDto.getValor());
					empParametro.setValor(empresaParametroDto.getValor());
				}
				if(empresaParametroDto.getCondicion()!=null){
					log4j.debug("<create> Set condicion: " + empresaParametroDto.getCondicion());
					empParametro.setCondicion(empresaParametroDto.getCondicion());
				}
				if(empresaParametroDto.getDescripcion()!=null){
					log4j.debug("<create> Set descripcion: " + empresaParametroDto.getDescripcion());
					empParametro.setDescripcion(empresaParametroDto.getDescripcion());
				}
				if(empresaParametroDto.getFuncion()!=null){
					log4j.debug("<create> Set funcion: " + empresaParametroDto.getFuncion());
					empParametro.setFuncion(empresaParametroDto.getFuncion());
				}
				log4j.debug("<create> Set fecha creación" );
				empParametro.setFechaCreacion(DateUtily.getToday());

				Object idEmpresaParametro = empresaParametroDao.create(empParametro);
				log4j.debug("<create> Creación correcta, idEmpresaParametro: " + idEmpresaParametro);
				empresaParametroDto=new EmpresaParametroDto();
				empresaParametroDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);
				empresaParametroDto.setCode(Mensaje.SERVICE_CODE_004);
				empresaParametroDto.setIdEmpresaParametro(String.valueOf(idEmpresaParametro));
			}catch (Exception e){
				empresaParametroDto=new EmpresaParametroDto();
				empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
				empresaParametroDto.setType(Mensaje.SERVICE_TYPE_ERROR);
				empresaParametroDto.setCode(Mensaje.SERVICE_CODE_006);
			}
			return empresaParametroDto;
		}
		log4j.debug("<create> Se detecto inconsistencia en filtros");
		//+ empresaParametroDto );
		if(empresaParametroDto.getMessages()!=null){
			return empresaParametroDto.getMessages();
		}
		return empresaParametroDto;
	}

	/**
	 * Realiza la persistencia de nuevos valores
	 * @param empresaParametroDto, objeto que contiene los valores a actualizar
	 * @return objeto principal que puede ser un objeto mensaje u objeto original
	 * @throws Exception 
	 */
	@Transactional
	@Override
	public Object update(EmpresaParametroDto empresaParametroDto) throws Exception {
		log4j.debug("<update> ");
		empresaParametroDto = filtros(empresaParametroDto, Constante.F_UPDATE);
		log4j.debug("<update> despues de filtros: " + empresaParametroDto );
		if(empresaParametroDto.getCode() == null && empresaParametroDto.getMessages()==null){
			try{
				EmpresaParametro empParametro = empresaParametroDao.read(Long.parseLong(empresaParametroDto.getIdEmpresaParametro()));
				if(empParametro!=null){
					boolean hayCambio = false;
					String resultado = Mensaje.SERVICE_MSG_OK_JSON;
					if(empresaParametroDto.getOrden()!=null){
						empParametro.setOrden(Long.parseLong(empresaParametroDto.getOrden()));
						hayCambio = true;
					}
					if(empresaParametroDto.getContexto()!=null){
						empParametro.setContexto(empresaParametroDto.getContexto());
						hayCambio = true;
					}
					if(empresaParametroDto.getValor()!=null){
						empParametro.setValor(empresaParametroDto.getValor());
						hayCambio = true;
					}
					if(empresaParametroDto.getCondicion()!=null){
						empParametro.setCondicion(empresaParametroDto.getCondicion());
						hayCambio = true;
					}
					if(empresaParametroDto.getDescripcion()!=null){
						empParametro.setDescripcion(empresaParametroDto.getDescripcion());
						hayCambio = true;
					}
					if(empresaParametroDto.getFuncion()!=null){
						empParametro.setFuncion(empresaParametroDto.getFuncion());
						hayCambio = true;
					}

					if(empresaParametroDto.getIdTipoParametro()!=null){
						TipoParametro tipoParam = new TipoParametro();
						tipoParam.setIdTipoParametro(Long.parseLong(empresaParametroDto.getIdTipoParametro()));
						empParametro.setTipoParametro(tipoParam);
						hayCambio = true;
					}
					
					if(hayCambio){
						log4j.debug("<update> Se actualiza pojo..");
						empParametro.setFechaModificacion(DateUtily.getToday());
						empresaParametroDao.update(empParametro);
						if(empresaParametroDto.getActualizaCache()!=null && empresaParametroDto.getActualizaCache()){
							log4j.debug("<update> Actualizar cache...");
							EmpresaParametroDto empresaParametroDto2 = new EmpresaParametroDto();
							empresaParametroDto2.setIdEmpresaConf(empresaParametroDto.getIdEmpresaConf());
							empresaParametroDto2.setIdConf(String.valueOf(empParametro.getConf().getIdConf())); 
							empresaParametroDto2.setIdTipoParametro(String.valueOf(empParametro.getTipoParametro().getIdTipoParametro()));
							Object objRsp = reloadCache(empresaParametroDto2);
							if(objRsp instanceof EmpresaParametroDto){
								empresaParametroDto2 = (EmpresaParametroDto)objRsp;
								//Si es diferente de Success (Type I)
								if(!empresaParametroDto2.getType().equals(Mensaje.SERVICE_TYPE_INFORMATION)){
									empresaParametroDto=new EmpresaParametroDto();
									empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
									empresaParametroDto.setType(Mensaje.SERVICE_TYPE_ERROR);
									empresaParametroDto.setCode(Mensaje.SERVICE_CODE_006);
									return empresaParametroDto;
								}
							}
							//SI hay problema reportarlo en resultado
							else{
								empresaParametroDto=new EmpresaParametroDto();
								empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
								empresaParametroDto.setType(Mensaje.SERVICE_TYPE_ERROR);
								empresaParametroDto.setCode(Mensaje.SERVICE_CODE_006);
								return empresaParametroDto;
							}
						}
						else{   log4j.debug("<update> No se actualiza parametro en cache...");  }
					}
					return resultado;
				}else{
					log4j.debug("<update> No existe objeto en BD");
					empresaParametroDto=new EmpresaParametroDto();
					empresaParametroDto.setMessage(Mensaje.MSG_ERROR_EMPTY);
					empresaParametroDto.setType(Mensaje.SERVICE_TYPE_ERROR);
					empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
				}
			}catch (Exception e){
				empresaParametroDto=new EmpresaParametroDto();
				empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
				empresaParametroDto.setType(Mensaje.SERVICE_TYPE_ERROR);
				empresaParametroDto.setCode(Mensaje.SERVICE_CODE_006);
			}
//			empresaParametroDto=new EmpresaParametroDto();
//			empresaParametroDto.setMessage("Metodo no desarrollado (update)");
//			empresaParametroDto.setType(Mensaje.SERVICE_TYPE_WARNING);
//			empresaParametroDto.setCode(Mensaje.SERVICE_CODE_000);
		}
		log4j.debug("<update> " + empresaParametroDto );
		if(empresaParametroDto.getMessages()!=null){
			return empresaParametroDto.getMessages();
		}
		return empresaParametroDto;
	}

	@Transactional
	public Object delete(EmpresaParametroDto empresaParametroDto) throws Exception {
		log4j.debug("<delete> ");
		empresaParametroDto = filtros(empresaParametroDto, Constante.F_DELETE);
		log4j.debug("<delete> despues de filtros: " + empresaParametroDto );
		if(empresaParametroDto.getCode() == null && empresaParametroDto.getMessages()==null){
			try{
				EmpresaParametro empParametro = empresaParametroDao.read(Long.parseLong(empresaParametroDto.getIdEmpresaParametro()));
				if(empParametro!=null){
					String idEmpresaParametro = empresaParametroDto.getIdEmpresaParametro();
					empresaParametroDao.delete(empParametro);
//					empresaParametroDto.setMessages(UtilsTCE.getJsonMessageGson(null, 
//							new MensajeDto("idEmpresaParametro", empresaParametroDto.getIdEmpresaParametro(),
//							Mensaje.SERVICE_CODE_007,Mensaje.SERVICE_TYPE_INFORMATION,null)));
					empresaParametroDto=new EmpresaParametroDto();
//					empresaParametroDto.setMessage(Mensaje.MSG_ERROR_EMPTY);
					empresaParametroDto.setType(Mensaje.SERVICE_TYPE_INFORMATION);
					empresaParametroDto.setCode(Mensaje.SERVICE_CODE_007);
					empresaParametroDto.setIdEmpresaParametro(idEmpresaParametro);
					
				}else{
					log4j.debug("<delete> No existe objeto en BD");
					empresaParametroDto=new EmpresaParametroDto();
					empresaParametroDto.setMessage(Mensaje.MSG_ERROR_EMPTY);
					empresaParametroDto.setType(Mensaje.SERVICE_TYPE_ERROR);
					empresaParametroDto.setCode(Mensaje.SERVICE_CODE_002);
				}
			}catch (Exception e){
				empresaParametroDto=new EmpresaParametroDto();
				empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
				empresaParametroDto.setType(Mensaje.SERVICE_TYPE_ERROR);
				empresaParametroDto.setCode(Mensaje.SERVICE_CODE_006);
			}
		}
		
		return empresaParametroDto;
	}

	@Override
	public Object updateMultiple(String jsonArray) {
			//JSONArray arrayDto) {
		log4j.debug("<updateMultiple> ");
		EmpresaParametroDto empresaParametroDto;
		StringBuilder sbResponse = new StringBuilder("[");
		try{
			JSONArray arrayDto = new JSONArray(jsonArray);
			EmpresaParametroDto empParamDto;
			for(int x=0;x<arrayDto.length();x++){
				log4j.debug("<updateMultiple> "+x+" "+ arrayDto.getJSONObject(x).toString() );
				empParamDto = gson.fromJson(arrayDto.getJSONObject(x).toString(), EmpresaParametroDto.class);
				log4j.debug("<updateMultiple> Conversión exitosa a DTO: " + empParamDto.getIdEmpresaParametro() );
				empParamDto.setActualizaCache(false);//==>Se actualiza de manera Masiva al final de la iteración
				
				Object objRsp = update(empParamDto);
				log4j.debug("<updateMultiple> se obtuvo respuesta: "+objRsp );
				if(objRsp instanceof String){
					if(String.valueOf(objRsp).equals(Mensaje.SERVICE_MSG_OK_JSON)){
						log4j.debug("<updateMultiple> update exitoso, se omite respuesta");
					}else{
						log4j.debug("<updateMultiple> update no Exitoso, agregando cadena");
						sbResponse.append(objRsp).append(x==arrayDto.length()-1?"":",")
						.append("\n");
						
					}
				}
				else if(objRsp instanceof EmpresaParametroDto){
					log4j.debug("<updateMultiple> update no Exitoso, se obtuvo EmpresaParametroDto");
					//empDtoRsp = (EmpresaParametroDto)objRsp;
					sbResponse.append(new GsonBuilder().disableHtmlEscaping().create().toJson(objRsp))
					.append(x==arrayDto.length()-1?"":",")
					.append("\n");
				}
				else{
					log4j.debug("<updateMultiple> update no Exitoso, se obtuvo respuesta no esperada");
					sbResponse.append(objRsp).append(x==arrayDto.length()-1?"":",").append("\n");
				}
			}
			
			// ACTUALIZAR el cache se llamará de manera explicita
			sbResponse.append("]");
			log4j.debug("<updateMultiple> \n\n sbResponse: \n"+sbResponse);
			return sbResponse.toString();
			
		}catch (Exception e){
			log4j.fatal("<updateMultiple> Excepcion: ", e);
			empresaParametroDto=new EmpresaParametroDto();
			empresaParametroDto.setMessage(Mensaje.MSG_ERROR);
			empresaParametroDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			empresaParametroDto.setCode(Mensaje.SERVICE_CODE_000);
		}
		
		return empresaParametroDto;
	}


}
