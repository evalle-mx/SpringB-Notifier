package net.tce.admin.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.tce.admin.service.PersonCertService;
import net.tce.dao.CertificacionDao;
import net.tce.dto.CertificacionDto;
import net.tce.dto.MensajeDto;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

@Transactional
@Service("personCertService")
public class PersonCertServiceImpl implements PersonCertService {
	
	private static Logger log4j = Logger.getLogger( PersonCertServiceImpl.class );
	
	@Autowired
	private CertificacionDao certificacionDao;	
	
	@Override
	public Object get(CertificacionDto certifDto) {
//		log4j.debug("<get> ");
		certifDto = filtros(certifDto, Constante.F_GET);
		
		if(certifDto.getCode()== null ){
			
			Long idPersona = Long.parseLong(certifDto.getIdPersona());
//			log4j.debug("<get> Obtener por persona: " + idPersona );
			Object ls;
			ls = certificacionDao.getByPersona(idPersona);
			if(ls!=null){
//				log4j.debug("<get> LS: "+ls);
				return ls;
			}
			else{
//				log4j.debug("<get> No hay Certificaciones");
				certifDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_003,Mensaje.SERVICE_TYPE_WARNING,
						Mensaje.MSG_WARNING)));
			}
		}else{
			log4j.error("<get> Error en filtros "+ certifDto.getCode());
			certifDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					certifDto.getCode(), certifDto.getType(), certifDto.getMessage())));
		}
		return certifDto.getMessages();
	}
	
	
	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente
	 * @param certifDto
	 * @param funcion
	 * @return
	 */
	private CertificacionDto filtros(CertificacionDto certifDto, int funcion) {
		 boolean error=false;
		 if(certifDto != null){			 
			 if(certifDto.getIdEmpresaConf() == null ){
				 log4j.debug("<filtros> Error, IdEmpresaConf es requerido ");
				 error = true;
			 }
			 else {
				//Para create y get
				 if(funcion ==  Constante.F_CREATE  ||	funcion == Constante.F_GET){
						 certifDto.setIdCertificacion(null);
				 }
				 if(funcion == Constante.F_GET && certifDto.getIdPersona()==null){
					 log4j.debug("<filtros> Error, IdPersona es requerido ");
					 error = true;
				 }
				 
				 //Para delete y update
				 if(!error && (funcion == Constante.F_UPDATE ||
					funcion == Constante.F_DELETE )){			 
					 if(certifDto.getIdCertificacion() == null){
						 log4j.debug("<filtros> Error, IdCertificacion es requerido ");
						 error=true;
					 }
				 }
			 }
		 }else{
			 log4j.debug("<filtros> certifDto es null ");
			 error=true;
		 }
		 if(error){
			 certifDto=new CertificacionDto();
			 certifDto.setMessage(Mensaje.MSG_ERROR);
			 certifDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 certifDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
//		 else{
//			 //create y update
//			 if(funcion != Constante.F_DATACONF){
//				 try {
//						//Se aplican filtros Dataconf
//					 	certifDto=filtrosDataConf(certifDto);
//					} catch (Exception e) {
//						certifDto=new CertificacionDto();
//						certifDto.setMessage(Mensaje.MSG_ERROR_SISTEMA);
//						certifDto.setType(Mensaje.SERVICE_TYPE_FATAL);
//						certifDto.setCode(Mensaje.SERVICE_CODE_000);
//						e.printStackTrace();
//					}
//			 }
//		 }
		return certifDto;
	}

}
