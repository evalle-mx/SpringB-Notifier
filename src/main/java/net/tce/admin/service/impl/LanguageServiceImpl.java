package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.tce.admin.service.CurriculumService;
import net.tce.admin.service.LanguageService;
import net.tce.dao.IdiomaDao;
import net.tce.dao.PersonaDao;
import net.tce.dao.PosicionDao;
import net.tce.dto.IdiomaDto;
import net.tce.dto.MensajeDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("languageService")
public class LanguageServiceImpl implements LanguageService {

	private static Logger log4j = Logger.getLogger( LanguageServiceImpl.class );
	
	@Autowired
	IdiomaDao idiomaDao;
	
	@Autowired
	PersonaDao personaDao;
	
	@Autowired
	PosicionDao posicionDao;
	
	@Autowired
	private CurriculumService curriculumService;
	

	
	
	
	@Override
	public Object get(IdiomaDto idiomaDto){
		log4j.debug("<get>");
		log4j.debug("<get> dto: " + idiomaDto );
		idiomaDto=filtros(idiomaDto,Constante.F_GET);
		
		if(idiomaDto.getCode() == null){
			Long idEntidad;
			Object ls;
			if(idiomaDto.getIdPersona()!=null){
				idEntidad = Long.parseLong(idiomaDto.getIdPersona());
				ls = idiomaDao.getByPersona(idEntidad);
				if(ls!=null){
					return ls;
				}
				else{
					log4j.debug("<get> No hay resultados");
					idiomaDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
							Mensaje.SERVICE_CODE_003,Mensaje.SERVICE_TYPE_WARNING,
							Mensaje.MSG_WARNING)));
				}
			}
			else{//NO determinado
				idiomaDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)));
			}
		}else{
			idiomaDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					idiomaDto.getCode(),idiomaDto.getType(),idiomaDto.getMessage())));
		}
		
		return idiomaDto.getMessages();
	}
	
	/**
	 * Método interno que evalua parametros requeridos
	 * @param idiomaDto
	 * @param funcion
	 * @return
	 */
	private IdiomaDto filtros(IdiomaDto idiomaDto, int funcion){
		boolean error=false;
		if(idiomaDto != null){			
			if(idiomaDto.getIdEmpresaConf()==null){
				error=true;
				log4j.debug("<filtros> IdEmpresaConf es null");
			}else{
				if(funcion == Constante.F_CREATE){
					if(idiomaDto.getIdPersona()== null && idiomaDto.getIdPosicion()==null){
						error=true;
						log4j.debug("<filtros> IdPersona/IdPosicion es null");
					}
				 }
				else if(funcion == Constante.F_UPDATE){
					if(idiomaDto.getIdPersonaIdioma()==null && idiomaDto.getIdPosicionIdioma()==null){
						error=true;
						log4j.debug("<filtros> IdPersonaIdioma/IdPosicionIdioma es null");
					}
					if(idiomaDto.getIdDominio()==null && idiomaDto.getIdIdioma()==null){
						error=true;
						log4j.debug("<filtros> idDominio/idIdioma es null");
					}
				}
				else if(funcion==Constante.F_DELETE){
					if(idiomaDto.getIdPersonaIdioma()==null && idiomaDto.getIdPosicionIdioma()==null){
						error=true;
						log4j.debug("<filtros> IdPersonaIdioma/IdPosicionIdioma es null");
					}
				}
				else if(funcion==Constante.F_GET){ //Unicamente interno
					if(idiomaDto.getIdPersona()== null && idiomaDto.getIdPosicion()==null){
						error=true;
						log4j.debug("<filtros> IdPersona/IdPosicion es null");
					}
				}
			}
		}else{
			log4j.debug("<filtros> idiomaDto es null");
			error=true;
		}
		//Procedimiento conforme a bandera de Error
		if(error){
			idiomaDto=new IdiomaDto();
			idiomaDto.setMessage(Mensaje.MSG_ERROR);
			idiomaDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			idiomaDto.setCode(Mensaje.SERVICE_CODE_006);
		}
//		else{
//			 //create y update
//			 if(funcion != Constante.F_DATACONF ){
//				 try {
//					 //Se aplican filtros Dataconf
//					 idiomaDto=filtrosDataConf(idiomaDto);
//					} catch (Exception e) {
//						idiomaDto=new IdiomaDto();
//						idiomaDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
//						idiomaDto.setType(Mensaje.SERVICE_TYPE_FATAL);
//						idiomaDto.setCode(Mensaje.SERVICE_CODE_000);
//						log4j.fatal("Error en filtros DataConf ", e);
//					}
//			 }
//		}
		return idiomaDto;
	}
}
