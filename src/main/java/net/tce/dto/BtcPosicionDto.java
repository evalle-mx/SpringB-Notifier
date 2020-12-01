package net.tce.dto;

import java.util.List;

public class BtcPosicionDto  extends ComunDto{
	
	private String idBitacoraPosicion;
	private String idRelacionEmpresaPersona;
	private String idRelacionEmpresaPersonaS;
	private String idPerfilPosicion;
	private String idTipoPrestacion;
	private String idPeriodicidadPago;
	private String idAmbitoGeografico;
	private String idTipoContrato;
	private String idTipoJornada;
	private String idAreaPerfil;
	private String idTipoOperacionBitacora;
	private String nombre;
	private String otros;
	private String sueldoNegociable;
	private String esConfidencial;
	private String salarioMin;
	private String salarioMax;
	private String fechaBitacora;
	private BtcDomicilioDto btcDomicilioDto;
	private List<BtcPerfilTextoNgramDto> btcPerfilTextoNgramDtos;
	private List<BtcCompetenciaPerfilDto> btcCompetenciaPerfilDtos;
	private List<BtcPoliticaValorDto> btcPoliticaValors;

	
	
	public String getIdBitacoraPosicion() {
		return idBitacoraPosicion;
	}
	public void setIdBitacoraPosicion(String idBitacoraPosicion) {
		this.idBitacoraPosicion = idBitacoraPosicion;
	}
	public String getIdTipoPrestacion() {
		return idTipoPrestacion;
	}
	public void setIdTipoPrestacion(String idTipoPrestacion) {
		this.idTipoPrestacion = idTipoPrestacion;
	}
	public String getIdPeriodicidadPago() {
		return idPeriodicidadPago;
	}
	public void setIdPeriodicidadPago(String idPeriodicidadPago) {
		this.idPeriodicidadPago = idPeriodicidadPago;
	}
	public String getIdAmbitoGeografico() {
		return idAmbitoGeografico;
	}
	public void setIdAmbitoGeografico(String idAmbitoGeografico) {
		this.idAmbitoGeografico = idAmbitoGeografico;
	}
	public String getIdTipoContrato() {
		return idTipoContrato;
	}
	public void setIdTipoContrato(String idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}
	public String getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(String idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	public String getIdTipoJornada() {
		return idTipoJornada;
	}
	public void setIdTipoJornada(String idTipoJornada) {
		this.idTipoJornada = idTipoJornada;
	}
	public String getIdPerfilPosicion() {
		return idPerfilPosicion;
	}
	public void setIdPerfilPosicion(String idPerfilPosicion) {
		this.idPerfilPosicion = idPerfilPosicion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getOtros() {
		return otros;
	}
	public void setOtros(String otros) {
		this.otros = otros;
	}
	public String getSueldoNegociable() {
		return sueldoNegociable;
	}
	public void setSueldoNegociable(String sueldoNegociable) {
		this.sueldoNegociable = sueldoNegociable;
	}
	public String getEsConfidencial() {
		return esConfidencial;
	}
	public void setEsConfidencial(String esConfidencial) {
		this.esConfidencial = esConfidencial;
	}
	public String getSalarioMin() {
		return salarioMin;
	}
	public void setSalarioMin(String salarioMin) {
		this.salarioMin = salarioMin;
	}
	public String getSalarioMax() {
		return salarioMax;
	}
	public void setSalarioMax(String salarioMax) {
		this.salarioMax = salarioMax;
	}
	public BtcDomicilioDto getBtcDomicilioDto() {
		return btcDomicilioDto;
	}
	public void setBtcDomicilioDto(BtcDomicilioDto btcDomicilioDto) {
		this.btcDomicilioDto = btcDomicilioDto;
	}
	public List<BtcPerfilTextoNgramDto> getBtcPerfilTextoNgramDtos() {
		return btcPerfilTextoNgramDtos;
	}
	public void setBtcPerfilTextoNgramDtos(List<BtcPerfilTextoNgramDto> btcPerfilTextoNgramDtos) {
		this.btcPerfilTextoNgramDtos = btcPerfilTextoNgramDtos;
	}
	public List<BtcCompetenciaPerfilDto> getBtcCompetenciaPerfilDtos() {
		return btcCompetenciaPerfilDtos;
	}
	public void setBtcCompetenciaPerfilDtos(List<BtcCompetenciaPerfilDto> btcCompetenciaPerfilDtos) {
		this.btcCompetenciaPerfilDtos = btcCompetenciaPerfilDtos;
	}
	public List<BtcPoliticaValorDto> getBtcPoliticaValors() {
		return btcPoliticaValors;
	}
	public void setBtcPoliticaValors(List<BtcPoliticaValorDto> btcPoliticaValors) {
		this.btcPoliticaValors = btcPoliticaValors;
	}
	public String getFechaBitacora() {
		return fechaBitacora;
	}
	public void setFechaBitacora(String fechaBitacora) {
		this.fechaBitacora = fechaBitacora;
	}
	public String getIdAreaPerfil() {
		return idAreaPerfil;
	}
	public void setIdAreaPerfil(String idAreaPerfil) {
		this.idAreaPerfil = idAreaPerfil;
	}
	public String getIdTipoOperacionBitacora() {
		return idTipoOperacionBitacora;
	}
	public void setIdTipoOperacionBitacora(String idTipoOperacionBitacora) {
		this.idTipoOperacionBitacora = idTipoOperacionBitacora;
	}
	public String getIdRelacionEmpresaPersonaS() {
		return idRelacionEmpresaPersonaS;
	}
	public void setIdRelacionEmpresaPersonaS(String idRelacionEmpresaPersonaS) {
		this.idRelacionEmpresaPersonaS = idRelacionEmpresaPersonaS;
	}
	
	
}
