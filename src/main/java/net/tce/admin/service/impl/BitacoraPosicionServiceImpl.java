package net.tce.admin.service.impl;

import java.util.Iterator;
import javax.inject.Inject;
import net.tce.admin.service.BitacoraPosicionService;
import net.tce.dao.BitacoraDomicilioDao;
import net.tce.dao.BitacoraPerfilTextoNgramDao;
import net.tce.dao.BitacoraPoliticaValorDao;
import net.tce.dao.BitacoraPosicionDao;
import net.tce.dto.BtcCompetenciaPerfilDto;
import net.tce.dto.BtcPerfilTextoNgramDto;
import net.tce.dto.BtcPoliticaValorDto;
import net.tce.dto.BtcPosicionDto;
import net.tce.dto.MensajeDto;
import net.tce.model.BitacoraDomicilio;
import net.tce.model.BitacoraPerfilTextoNgram;
import net.tce.model.BitacoraPoliticaValor;
import net.tce.model.BitacoraPosicion;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Clase donde se aplica los eventos de la bitacora de la posicion
 * @author DothrDeveloper
 *
 */
@Transactional
@Service("bitacoraPosicionService")
public class BitacoraPosicionServiceImpl implements BitacoraPosicionService{
	Logger log4j = Logger.getLogger( this.getClass());
	
	BitacoraPosicion bitacoraPosicion;
	
	@Autowired
	private BitacoraPosicionDao bitacoraPosicionDao;
	
	@Autowired
	private BitacoraDomicilioDao bitacoraDomicilioDao;
		
	@Autowired
	private BitacoraPerfilTextoNgramDao bitacoraPerfilTextoNgramDao;
	
	@Autowired
	private BitacoraPoliticaValorDao bitacoraPoliticaValorDao;
	
	@Inject
	private ConversionService converter;

	/**
	 * Se crea un nuevo registro en la bitácora de posicion
	 * @param btcPosicionDto, objeto a persistir
	 * @return una cadena de respuesta
	 * @throws Exception 
	 */
	@Override
	public String create(BtcPosicionDto btcPosicionDto) throws Exception {
		filtros(btcPosicionDto,Constante.F_CREATE);
		log4j.debug("<create> -> getCode="+btcPosicionDto.getCode()+
				" IdRelacionEmpresaPersona="+btcPosicionDto.getIdRelacionEmpresaPersona()+
				" getIdPerfilPosicion="+btcPosicionDto.getIdPerfilPosicion());
		if(btcPosicionDto.getCode() == null){
			
			//Bitacora Posicion
			bitacoraPosicion=converter.convert(btcPosicionDto, BitacoraPosicion.class);
			bitacoraPosicion.setFechaBitacora(DateUtily.getToday());
			btcPosicionDto.setIdBitacoraPosicion(bitacoraPosicionDao.create(bitacoraPosicion).toString());
			log4j.debug("<create> -> getIdBitacoraPosicion="+btcPosicionDto.getIdBitacoraPosicion()+
						" getBtcDomicilioDto="+btcPosicionDto.getBtcDomicilioDto()+
						" getBtcCompetenciaPerfilDtos="+btcPosicionDto.getBtcCompetenciaPerfilDtos()+
						" getBtcPerfilTextoNgramDtos="+btcPosicionDto.getBtcPerfilTextoNgramDtos()+
						" getBtcPoliticaValors="+btcPosicionDto.getBtcPoliticaValors());

			
			//Bitacora Domicilio
			if(btcPosicionDto.getBtcDomicilioDto() != null){
				btcPosicionDto.getBtcDomicilioDto().setIdBitacoraPosicion(btcPosicionDto.getIdBitacoraPosicion());
				bitacoraDomicilioDao.create(converter.convert(btcPosicionDto.getBtcDomicilioDto(), BitacoraDomicilio.class));
			}
			
			//Bitacora CompetenciaPerfil
			if(btcPosicionDto.getBtcCompetenciaPerfilDtos() != null && 
			   btcPosicionDto.getBtcCompetenciaPerfilDtos().size() > 0){
				Iterator<BtcCompetenciaPerfilDto> itBtcCompetenciaPerfilDtos=btcPosicionDto.
														getBtcCompetenciaPerfilDtos().iterator();
				while(itBtcCompetenciaPerfilDtos.hasNext()){
					BtcCompetenciaPerfilDto btcCompetenciaPerfilDto=itBtcCompetenciaPerfilDtos.next();
					btcCompetenciaPerfilDto.setIdBitacoraPosicion(btcPosicionDto.getIdBitacoraPosicion());
					log4j.debug("btcCompetenciaPerfilDto: "+btcCompetenciaPerfilDto);
//					bitacoraCompetenciaPerfilDao.create(converter.convert(btcCompetenciaPerfilDto, 
//																	BitacoraCompetenciaPerfil.class));
				}
			}
			
			//Bitacora Perfil Texto Ngram
			if(btcPosicionDto.getBtcPerfilTextoNgramDtos() != null && 
			   btcPosicionDto.getBtcPerfilTextoNgramDtos().size() > 0){
				Iterator<BtcPerfilTextoNgramDto> itBtcPerfilTextoNgramDtos=btcPosicionDto.
														getBtcPerfilTextoNgramDtos().iterator();
				while(itBtcPerfilTextoNgramDtos.hasNext()){
					BtcPerfilTextoNgramDto btcPerfilTextoNgramDto=itBtcPerfilTextoNgramDtos.next();
					btcPerfilTextoNgramDto.setIdBitacoraPosicion(btcPosicionDto.getIdBitacoraPosicion());
					bitacoraPerfilTextoNgramDao.create(converter.convert(btcPerfilTextoNgramDto, 
																		BitacoraPerfilTextoNgram.class));
				}
			}
			
			//Bitacora PoliticaValor
			if(btcPosicionDto.getBtcPoliticaValors() != null && 
			   btcPosicionDto.getBtcPoliticaValors().size() > 0){
				Iterator<BtcPoliticaValorDto> itBtcPoliticaValorDtos=btcPosicionDto.
														getBtcPoliticaValors().iterator();
				while(itBtcPoliticaValorDtos.hasNext()){
					BtcPoliticaValorDto btcPoliticaValorDto=itBtcPoliticaValorDtos.next();
					btcPoliticaValorDto.setIdBitacoraPosicion(btcPosicionDto.getIdBitacoraPosicion());
					
					//Si en la politica se escogio un indistinto
					//SEXO_KO
					if(btcPoliticaValorDto.getIdTipoGenero() != null &&
					   btcPoliticaValorDto.getIdTipoGenero().equals(Constante.POLITICA_INDISTINTO)){
						btcPoliticaValorDto.setIdTipoGenero(null);
					}
					
					//EDO_CIVIL_KO
					if(btcPoliticaValorDto.getIdEstadoCivil() != null &&
					   btcPoliticaValorDto.getIdEstadoCivil().equals(Constante.POLITICA_INDISTINTO)){
						btcPoliticaValorDto.setIdEstadoCivil(null);
					}
					
					//DISP_VIAJAR_KO
					if(btcPoliticaValorDto.getIdTipoDispViajar() != null &&
					   btcPoliticaValorDto.getIdTipoDispViajar().equals(Constante.POLITICA_INDISTINTO)){
						btcPoliticaValorDto.setIdTipoDispViajar(null);
					}
					
					//FORM_ACADEMICA_KO_MIN, FORM_ACADEMICA_KO_MAX
					if(btcPoliticaValorDto.getIdGradoAcademico() != null &&
					   btcPoliticaValorDto.getIdGradoAcademico().equals(Constante.POLITICA_INDISTINTO)){
						btcPoliticaValorDto.setIdGradoAcademico(null);
					}
					if(btcPoliticaValorDto.getIdEstatusEscolar() != null &&
					   btcPoliticaValorDto.getIdEstatusEscolar().equals(Constante.POLITICA_INDISTINTO)){
						btcPoliticaValorDto.setIdEstatusEscolar(null);
					}
					
					//RANGO_EDAD_KO
					if(btcPoliticaValorDto.getValorMin() != null &&
					   btcPoliticaValorDto.getValorMin().equals(Constante.POLITICA_VACIO)){
						btcPoliticaValorDto.setValorMin(null);
					}
					
					if(btcPoliticaValorDto.getValorMax() != null &&
					   btcPoliticaValorDto.getValorMax().equals(Constante.POLITICA_VACIO)){
						btcPoliticaValorDto.setValorMax(null);
					}
					
					bitacoraPoliticaValorDao.create(converter.convert(btcPoliticaValorDto, 
																	  BitacoraPoliticaValor.class));
				}
			}
			//respuestas
			btcPosicionDto.setMessages(UtilsTCE.getJsonMessageGson(null,new MensajeDto("idBitacoraPosicion",
																		btcPosicionDto.getIdBitacoraPosicion(),
																		Mensaje.SERVICE_CODE_004,
																		Mensaje.SERVICE_TYPE_INFORMATION,null)));	
		}else{
			btcPosicionDto.setMessages(UtilsTCE.getJsonMessageGson(null, 
										new MensajeDto(null,null,
										btcPosicionDto.getCode(),
										btcPosicionDto.getType(),
										btcPosicionDto.getMessage())));
		}
		return btcPosicionDto.getMessages();
	}


	
	/**
	 * Se aplican criterios de negocio para analizar si es viable la ejecucion correspondiente
	 * @param btcPosicionDto, el objeto a anlizar
	 * @param funcion, el criterio para aplicar los filtros
	 * @throws Exception 
	 */
	private void filtros(BtcPosicionDto btcPosicionDto, int funcion) throws Exception {
		 boolean error=false;
		 if(btcPosicionDto != null){
			 if(funcion == Constante.F_CREATE && (
				 btcPosicionDto.getIdRelacionEmpresaPersona() == null ||
				 btcPosicionDto.getIdPerfilPosicion() == null)){
				 error=true;
			 }
			 
			
			 
		 }else{
			 error=true;
		 }
		 
		 if(error){
			 btcPosicionDto.setMessage(Mensaje.MSG_ERROR);
			 btcPosicionDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			 btcPosicionDto.setCode(Mensaje.SERVICE_CODE_006);
		 }
		 
	}
}
