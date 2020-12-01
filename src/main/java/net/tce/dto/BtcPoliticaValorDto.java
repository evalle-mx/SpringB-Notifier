package net.tce.dto;

public class BtcPoliticaValorDto {
	
	private String idBitacoraPoliticaValor;
	private String idBitacoraPosicion;
	private String idTipoGenero;
	private String idTipoDispViajar;
	private String idPolitica;
	private String idPoliticaValor;
	private String idPoliticaMValor;
	private String idGradoAcademico;
	private String idEstadoCivil;
	private String idIdioma;
	private String idEstatusEscolar;
	private String idDominio;
	private String ponderacion;
	private String descripcion;
	private String valor;
	private String valorMin;
	private String valorMax;
	private String escolaridadMax;
	
	public BtcPoliticaValorDto(){}
	
	public BtcPoliticaValorDto(String idPolitica,String idPoliticaValor){
		this.idPolitica=idPolitica;
		this.idPoliticaValor=idPoliticaValor;
	}
	
	public BtcPoliticaValorDto(String idPolitica,String idPoliticaValor,String idPoliticaMValor){
		this.idPolitica=idPolitica;
		this.idPoliticaValor=idPoliticaValor;
		this.idPoliticaMValor=idPoliticaMValor;
	}

	public String getIdBitacoraPoliticaValor() {
		return idBitacoraPoliticaValor;
	}
	public void setIdBitacoraPoliticaValor(String idBitacoraPoliticaValor) {
		this.idBitacoraPoliticaValor = idBitacoraPoliticaValor;
	}
	public String getIdTipoGenero() {
		return idTipoGenero;
	}
	public void setIdTipoGenero(String idTipoGenero) {
		this.idTipoGenero = idTipoGenero;
	}
	public String getIdTipoDispViajar() {
		return idTipoDispViajar;
	}
	public void setIdTipoDispViajar(String idTipoDispViajar) {
		this.idTipoDispViajar = idTipoDispViajar;
	}
	public String getIdPolitica() {
		return idPolitica;
	}
	public void setIdPolitica(String idPolitica) {
		this.idPolitica = idPolitica;
	}
	public String getIdGradoAcademico() {
		return idGradoAcademico;
	}
	public void setIdGradoAcademico(String idGradoAcademico) {
		this.idGradoAcademico = idGradoAcademico;
	}
	public String getIdEstadoCivil() {
		return idEstadoCivil;
	}
	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}
	public String getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}
	public String getIdEstatusEscolar() {
		return idEstatusEscolar;
	}
	public void setIdEstatusEscolar(String idEstatusEscolar) {
		this.idEstatusEscolar = idEstatusEscolar;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getPonderacion() {
		return ponderacion;
	}
	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getValorMin() {
		return valorMin;
	}
	public void setValorMin(String valorMin) {
		this.valorMin = valorMin;
	}
	public String getValorMax() {
		return valorMax;
	}
	public void setValorMax(String valorMax) {
		this.valorMax = valorMax;
	}
	public String getIdBitacoraPosicion() {
		return idBitacoraPosicion;
	}
	public void setIdBitacoraPosicion(String idBitacoraPosicion) {
		this.idBitacoraPosicion = idBitacoraPosicion;
	}

	public String getIdPoliticaValor() {
		return idPoliticaValor;
	}

	public void setIdPoliticaValor(String idPoliticaValor) {
		this.idPoliticaValor = idPoliticaValor;
	}

	public String getEscolaridadMax() {
		return escolaridadMax;
	}

	public void setEscolaridadMax(String escolaridadMax) {
		this.escolaridadMax = escolaridadMax;
	}

	public String getIdPoliticaMValor() {
		return idPoliticaMValor;
	}

	public void setIdPoliticaMValor(String idPoliticaMValor) {
		this.idPoliticaMValor = idPoliticaMValor;
	}
	
	
	
	
}
