package net.tce.admin.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import net.tce.admin.service.EmpresaInterfaseService;
import net.tce.app.exception.SystemTCEException;
import net.tce.cache.DataInfoCache;
import net.tce.cache.EmpresaConfCache;
import net.tce.dao.EmpresaParametroDao;
import net.tce.dto.EmpresaConfDto;
import net.tce.model.EmpresaParametro;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import net.tce.util.Validador;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase donde se aplica las politicas de negocio del servicio empresaInterfase
 * @author Goyo
 *
 */
@Transactional
@Service("empresaInterfaseService")
public class EmpresaInterfaseServiceImpl implements EmpresaInterfaseService{
	Logger log4j = Logger.getLogger( this.getClass());
	EmpresaConfDto empresaConfDto;
	List<EmpresaParametro> lsDataConf;
	Field field;
	Iterator<EmpresaParametro> itDataConf;
	EmpresaParametro empresaParametro;
	//HashMap<String, Object> currFilters;
	//ArrayList<String> orderby;

	@Autowired
	private EmpresaParametroDao empresaParametroDao;
	

	

	/**
	 * Regresa un objeto datainfo
	 * @param idEmpresa, referida al id de la empresa
	 * @param contexto, es nombre de la tabla
	 * @param object, es el objeto datainfo 
	 * @return el objeto datainfo si hay informacion de lo contrario regresa nulo
	 */
	public synchronized  Object dataConf(String idEmpresaConf, String contexto, Object objectDataInfo)  throws Exception {
		Object objectDataInfoResp=null;
		
		//Se revisa si es numero
		if(Pattern.matches(Validador.NUMERICDECPATT,idEmpresaConf)){
			
			//se obtiene el idconf
			empresaConfDto=EmpresaConfCache.get(Long.valueOf(idEmpresaConf));
			
			if(empresaConfDto != null){
				
				//dar un vistazo al cache
				DataInfoCache.viewchmDataInfo();
				
				//Se obtiene del cache
				objectDataInfoResp=DataInfoCache.get(new StringBuilder(objectDataInfo.getClass().getName()).
								   append(empresaConfDto.getIdConf()).toString()); 
				log4j.debug("<dataConf> -> objectDataInfo.name="+objectDataInfo.getClass().getName()+
						" getIdConf="+empresaConfDto.getIdConf()+" objectDataInfoResp:"+objectDataInfoResp);
				
				//No esta en el cache
				if(objectDataInfoResp == null ){
			
					lsDataConf=empresaParametroDao.get(empresaConfDto.getIdConf(), 
														contexto, 
														Constante.PARAMETRO_DATACONF.longValue());
					
					log4j.debug("<dataConf> -> lsDataConf="+
							(lsDataConf != null ? lsDataConf.size():null));
					
					if(lsDataConf != null && lsDataConf.size() > 0){
						field =null;
						itDataConf= lsDataConf.iterator();
						while(itDataConf.hasNext()){
							empresaParametro=itDataConf.next();
							try {
								log4j.debug("<dataConf> field="+empresaParametro.getDescripcion());
								field =UtilsTCE.findUnderlying(objectDataInfo.getClass(),empresaParametro.getDescripcion());
								field.setAccessible(true);
								field.set(objectDataInfo, empresaParametro.getValor());
							} catch (Exception e) {
								log4j.error("Error al obtener dataconf".concat(" , en el dto:").
											concat(objectDataInfo.getClass().getName()).
											concat(" --> ").concat(e.toString()));
								e.printStackTrace();
								 throw new SystemTCEException("Error al obtener dataconf".concat(" , en el dto:").
											concat(objectDataInfo.getClass().getName()).
											concat(" --> ").concat(e.toString()));
							}
						}
						//Se guarda en cache
						//llave es : nombreClase + idConf
						DataInfoCache.set(new StringBuilder(objectDataInfo.getClass().getName()).
											append(empresaConfDto.getIdConf()).toString(), objectDataInfo);
						objectDataInfoResp=objectDataInfo;
						objectDataInfo=null;
					}else{
						log4j.error(new StringBuilder(Mensaje.MSG_ERROR_DATACONF).append(contexto).
									append(" , para la empresa(id)=").append(empresaConfDto.getIdConf()).toString());
						objectDataInfoResp=null;
					}
				}
			}else{
				log4j.error(new StringBuilder("El idEmpresaconf:").append(idEmpresaConf).
							append(", no esta registrado.").toString());
			}
		}
		return objectDataInfoResp;
	}


	
}
