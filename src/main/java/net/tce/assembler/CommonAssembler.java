package net.tce.assembler;

import java.math.BigDecimal;

import javax.inject.Inject;

import com.google.gson.Gson;

import net.tce.dto.DomicilioDto;
import net.tce.dto.LocationInfoDto;
import net.tce.model.Asentamiento;
import net.tce.model.CodigoProceso;
import net.tce.model.Domicilio;
import net.tce.model.TipoDomicilio;

/**
 * Se crea esta clase para contener los assembler de Domicilio y otros empleados de manera común
 * y así concentrar el codigo, evitando duplicidad
 * @author tce
 *
 */
public  class CommonAssembler {
	
	protected boolean huboModificacion;
	
	@Inject
	Gson gson;
	
	/**
	 * Metodo para obtener el valor de un Objeto en cadena (String.valueOf), y en caso de ser null, obtiene null 
	 * @param value
	 * @return
	 */
	protected static String stValue(Object value){
		String resp = null;
		
		if(value!=null){
			resp = String.valueOf(value);
		}
		return resp;
	}
	
	/**
	 * Procesa la respuesta en modo Rest (String)
	 * @param object
	 * @return
	 */
	public String restResponse(Object object){
		return (object instanceof String) ? (String)object: gson.toJson(object);
	}
	
	/**
	 * Hace match del objeto locationInfoDto al objeto domicilio
	 * @param domicilio
	 * @param locationInfoDto
	 * @return
	 */
	public Domicilio getDomicilio(Domicilio domicilio,LocationInfoDto locationInfoDto){
		huboModificacion=false;
		if(locationInfoDto.getIdTipoDomicilio() != null){
			if(domicilio.getTipoDomicilio() == null ||
			   (domicilio.getTipoDomicilio() != null && 
			   !locationInfoDto.getIdTipoDomicilio().equals(String.valueOf(
			   domicilio.getTipoDomicilio().getIdTipoDomicilio())))){
					domicilio.setTipoDomicilio(new TipoDomicilio());
					domicilio.getTipoDomicilio().setIdTipoDomicilio(Long.valueOf(
															locationInfoDto.getIdTipoDomicilio()));
					huboModificacion=true;
			}
		}
		if(locationInfoDto.getIdAsentamiento() != null){
			if((domicilio.getAsentamiento() == null)||
			   (domicilio.getAsentamiento() != null && 
			   !locationInfoDto.getIdAsentamiento().equals(String.valueOf(
			   domicilio.getAsentamiento().getIdAsentamiento())))){
				domicilio.setDireccionNoCatalogada(null);
				domicilio.setAsentamiento(new Asentamiento());
				domicilio.getAsentamiento().setIdAsentamiento(Long.valueOf(
														locationInfoDto.getIdAsentamiento()));
				huboModificacion=true;
			}

		}
		if(locationInfoDto.getIdCodigoProceso() != null){
			if((domicilio.getCodigoProceso() == null) || 
			   (domicilio.getCodigoProceso() != null && 
			   !locationInfoDto.getIdCodigoProceso().equals(String.valueOf(
			   domicilio.getCodigoProceso().getIdCodigoProceso())))){
				domicilio.setCodigoProceso(new CodigoProceso());
				domicilio.getCodigoProceso().setIdCodigoProceso(Long.valueOf(
																locationInfoDto.getIdCodigoProceso()));
				huboModificacion=true;
			}
		}
		if(locationInfoDto.getCalle() != null &&
		   !locationInfoDto.getCalle().equals(domicilio.getCalle())){
			domicilio.setCalle(locationInfoDto.getCalle());
			huboModificacion=true;
		}
		if(locationInfoDto.getNumeroInterior() != null &&
		   !locationInfoDto.getNumeroInterior().equals(domicilio.getNumeroInterior())){
			domicilio.setNumeroInterior(locationInfoDto.getNumeroInterior());
			huboModificacion=true;
		}
		if(locationInfoDto.getNumeroExterior() != null &&
		   !locationInfoDto.getNumeroExterior().equals(domicilio.getNumeroExterior())){
			domicilio.setNumeroExterior(locationInfoDto.getNumeroExterior());
			huboModificacion=true;
		}
		if(locationInfoDto.getDireccionFacturacion() != null){
			boolean boResp=Long.valueOf(locationInfoDto.getDireccionFacturacion()) == 0 ? false:true;
			if(domicilio.getDireccionFacturacion() == null){
				huboModificacion=true;
			}else{
				if(domicilio.getDireccionFacturacion().booleanValue() != boResp)
					huboModificacion=true;
			}
			if(huboModificacion)
				domicilio.setDireccionFacturacion(boResp);
		}
		if(locationInfoDto.getGoogleLatitude() != null &&
		   !locationInfoDto.getGoogleLatitude().equals(domicilio.getGoogleLatitude())){
			domicilio.setGoogleLatitude(new BigDecimal(locationInfoDto.getGoogleLatitude()));
			huboModificacion=true;
		}
		if(locationInfoDto.getGoogleLongitude() != null &&
		   !locationInfoDto.getGoogleLongitude().equals(domicilio.getGoogleLongitude())){
			domicilio.setGoogleLongitude(new BigDecimal(locationInfoDto.getGoogleLongitude()));
			huboModificacion=true;
		}
		if(locationInfoDto.getDireccionNoCatalogada() != null &&
		   !locationInfoDto.getDireccionNoCatalogada().equals(domicilio.getDireccionNoCatalogada())){
			domicilio.setDireccionNoCatalogada(locationInfoDto.getDireccionNoCatalogada());
			domicilio.setAsentamiento(null);
			huboModificacion=true;
		}
		
		
		return domicilio;
	}
	
	/**
	 * Hace match del objeto locationInfoDto al objeto domicilio
	 * @param domicilio
	 * @param locationInfoDto
	 * @return
	 */
	public Domicilio getDomicilio(Domicilio domicilio,DomicilioDto domicilioDto){
		huboModificacion = false;
		if(domicilioDto.getIdTipoDomicilio() != null){
			if(domicilio.getTipoDomicilio() == null ||
			   (domicilio.getTipoDomicilio() != null && 
			   !domicilioDto.getIdTipoDomicilio().equals(String.valueOf(
			   domicilio.getTipoDomicilio().getIdTipoDomicilio())))){
					domicilio.setTipoDomicilio(new TipoDomicilio());
					domicilio.getTipoDomicilio().setIdTipoDomicilio(Long.valueOf(
							domicilioDto.getIdTipoDomicilio()));
					huboModificacion=true;
			}
		}
		if(domicilioDto.getIdAsentamiento() != null){
			if((domicilio.getAsentamiento() == null)||
			   (domicilio.getAsentamiento() != null && 
			   !domicilioDto.getIdAsentamiento().equals(String.valueOf(
			   domicilio.getAsentamiento().getIdAsentamiento())))){
				domicilio.setDireccionNoCatalogada(null);
				domicilio.setAsentamiento(new Asentamiento());
				domicilio.getAsentamiento().setIdAsentamiento(Long.valueOf(
						domicilioDto.getIdAsentamiento()));
				huboModificacion=true;
			}

		}
		if(domicilioDto.getIdCodigoProceso() != null){
			if((domicilio.getCodigoProceso() == null) || 
			   (domicilio.getCodigoProceso() != null && 
			   !domicilioDto.getIdCodigoProceso().equals(String.valueOf(
			   domicilio.getCodigoProceso().getIdCodigoProceso())))){
				domicilio.setCodigoProceso(new CodigoProceso());
				domicilio.getCodigoProceso().setIdCodigoProceso(Long.valueOf(
						domicilioDto.getIdCodigoProceso()));
				huboModificacion=true;
			}
		}
		if(domicilioDto.getCalle() != null &&
		   !domicilioDto.getCalle().equals(domicilio.getCalle())){
			domicilio.setCalle(domicilioDto.getCalle());
			huboModificacion=true;
		}
		if(domicilioDto.getNumeroInterior() != null &&
		   !domicilioDto.getNumeroInterior().equals(domicilio.getNumeroInterior())){
			domicilio.setNumeroInterior(domicilioDto.getNumeroInterior());
			huboModificacion=true;
		}
		if(domicilioDto.getNumeroExterior() != null &&
		   !domicilioDto.getNumeroExterior().equals(domicilio.getNumeroExterior())){
			domicilio.setNumeroExterior(domicilioDto.getNumeroExterior());
			huboModificacion=true;
		}
		if(domicilioDto.getDireccionFacturacion() != null){
			boolean boResp=Long.valueOf(domicilioDto.getDireccionFacturacion()) == 0 ? false:true;
			if(domicilio.getDireccionFacturacion() == null){
				huboModificacion=true;
			}else{
				if(domicilio.getDireccionFacturacion().booleanValue() != boResp)
					huboModificacion=true;
			}
			if(huboModificacion)
				domicilio.setDireccionFacturacion(boResp);
		}
		if(domicilioDto.getGoogleLatitude() != null &&
		   !domicilioDto.getGoogleLatitude().equals(domicilio.getGoogleLatitude())){
			domicilio.setGoogleLatitude(new BigDecimal(domicilioDto.getGoogleLatitude()));
			huboModificacion=true;
		}
		if(domicilioDto.getGoogleLongitude() != null &&
		   !domicilioDto.getGoogleLongitude().equals(domicilio.getGoogleLongitude())){
			domicilio.setGoogleLongitude(new BigDecimal(domicilioDto.getGoogleLongitude()));
			huboModificacion=true;
		}
		if(domicilioDto.getDireccionNoCatalogada() != null &&
		   !domicilioDto.getDireccionNoCatalogada().equals(domicilio.getDireccionNoCatalogada())){
			domicilio.setDireccionNoCatalogada(domicilioDto.getDireccionNoCatalogada());
			domicilio.setAsentamiento(null);
			huboModificacion=true;
		}
		
		return domicilio;
	}

}
