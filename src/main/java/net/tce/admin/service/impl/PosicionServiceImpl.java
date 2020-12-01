package net.tce.admin.service.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.tce.admin.service.PosicionService;
import net.tce.assembler.SearchAssembler;
import net.tce.dao.PosicionDao;
import net.tce.dto.MensajeDto;
import net.tce.dto.PosicionDto;
import net.tce.model.Posicion;
import net.tce.util.Constante;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * 
 * @author tce
 *
 */
@Transactional
@Service("posicionService")
public class PosicionServiceImpl implements PosicionService{
	Logger log4j = Logger.getLogger( this.getClass());

	@Autowired
	private PosicionDao posicionDao;
	
	@Inject
	private SearchAssembler searchAssembler;

	/**
	 * Se modifica la  Posicion
	 * @param posicionDto
	 * @return
	 */
	public String update(PosicionDto posicionDto) {
		log4j.debug("$//%%  posicionDto="+posicionDto.getIdPosicion()+
				    " getIdEstatusPosicion="+posicionDto.getIdEstatusPosicion());
		posicionDto=filtros(posicionDto,Constante.F_UPDATE);
		if(posicionDto.getCode() == null){
			Posicion posicion=posicionDao.read(posicionDto.getIdPosicion());
			log4j.debug("$//%%  posicion="+posicion+
					   " getIdEstatusPosicion="+posicionDto.getIdEstatusPosicion());
			if(posicion != null){
					log4j.debug("$//%% posicionDao.update"+
								" getIdEstatusPosicion="+posicionDto.getIdEstatusPosicion());
					posicionDao.merge(searchAssembler.getPosicion(posicion, posicionDto));
					//Si no hay politicas erroneas
					if(posicionDto.getMessages() == null){
						posicionDto.setMessages(Mensaje.SERVICE_MSG_OK_JSON);
					}
			}else{
				posicionDto.setMessages(UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
										Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
										Mensaje.MSG_ERROR)));
			}
		}
		return posicionDto.getMessages();
	}

	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente
	 * @param posicionDto
	 * @param funcion
	 */
	private PosicionDto filtros(PosicionDto posicionDto, int funcion){
		 boolean error=false;
		 if(posicionDto != null){
			 
			 if(funcion == Constante.F_UPDATE){
				 if(posicionDto.getIdPosicion() == null){
					 error=true;
				 }
			 }
			 
		 }else{
			 error=true;
		 }
		 if(error){
			 posicionDto=new PosicionDto();
			 posicionDto.setMessage(Mensaje.MSG_ERROR);
			 posicionDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 posicionDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		 return posicionDto;
	}
}
