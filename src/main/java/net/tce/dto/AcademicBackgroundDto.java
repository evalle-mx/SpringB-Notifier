package net.tce.dto;


public class AcademicBackgroundDto extends ComunDto {

	private String idEscolaridad;
	private String idPersona;
	private String idGradoAcademico;
	private String idEstatusEscolar;
	private String idPais;
	private String idTextoNgram;
	private String nombreInstitucion;
	private String anioInicio;
	private String mesInicio;
	private String diaInicio;
	private String anioFin;
	private String mesFin;
	private String diaFin;
	private String titulo;
	private String texto;
	private String textoFiltrado;
	private Byte nivelGradoAcademico;
	private Byte nivelEstatusAcademico;
	  
	public AcademicBackgroundDto(){}
	public AcademicBackgroundDto(String idEmpresaConf,String idPersona, boolean parcial){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.setParcial(parcial);
	}

	public AcademicBackgroundDto(Long idEscolaridad, Long idGradoAcademico, Long idEstatusEscolar,
								Short anioInicio,Short anioFin, String texto){
		this.idEscolaridad=idEscolaridad == null ? null:String.valueOf(idEscolaridad);
		this.idGradoAcademico= idGradoAcademico == null ? null:String.valueOf(idGradoAcademico);
		this.idEstatusEscolar=idEstatusEscolar == null ? null:String.valueOf(idEstatusEscolar);
		this.anioInicio=anioInicio== null ? null:String.valueOf(anioInicio);
		this.anioFin=anioFin== null ? null:String.valueOf(anioFin);
		this.texto=texto;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	public void setIdEscolaridad(String idEscolaridad) {
		this.idEscolaridad = idEscolaridad;
	}
	public String getIdEscolaridad() {
		return idEscolaridad;
	}
	
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getIdPais() {
		return idPais;
	}
	
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdGradoAcademico(String idGradoAcademico) {
		this.idGradoAcademico = idGradoAcademico;
	}
	public String getIdGradoAcademico() {
		return idGradoAcademico;
	}
	public void setIdEstatusEscolar(String idEstatusEscolar) {
		this.idEstatusEscolar = idEstatusEscolar;
	}
	public String getIdEstatusEscolar() {
		return idEstatusEscolar;
	}
	
	public void setAnioInicio(String anioInicio) {
		this.anioInicio = anioInicio;
	}
	public String getAnioInicio() {
		return anioInicio;
	}
	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}
	public String getMesInicio() {
		return mesInicio;
	}
	public void setDiaInicio(String diaInicio) {
		this.diaInicio = diaInicio;
	}
	public String getDiaInicio() {
		return diaInicio;
	}
	public void setAnioFin(String anioFin) {
		this.anioFin = anioFin;
	}
	public String getAnioFin() {
		return anioFin;
	}
	public void setMesFin(String mesFin) {
		this.mesFin = mesFin;
	}
	public String getMesFin() {
		return mesFin;
	}
	public void setDiaFin(String diaFin) {
		this.diaFin = diaFin;
	}
	public String getDiaFin() {
		return diaFin;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}


	public void setIdTextoNgram(String idTextoNgram) {
		this.idTextoNgram = idTextoNgram;
	}

	public String getIdTextoNgram() {
		return idTextoNgram;
	}

	public String getTextoFiltrado() {
		return textoFiltrado;
	}

	public void setTextoFiltrado(String textoFiltrado) {
		this.textoFiltrado = textoFiltrado;
	}
	public Byte getNivelGradoAcademico() {
		return nivelGradoAcademico;
	}
	public void setNivelGradoAcademico(Byte nivelGradoAcademico) {
		this.nivelGradoAcademico = nivelGradoAcademico;
	}
	public Byte getNivelEstatusAcademico() {
		return nivelEstatusAcademico;
	}
	public void setNivelEstatusAcademico(Byte nivelEstatusAcademico) {
		this.nivelEstatusAcademico = nivelEstatusAcademico;
	}
		
	
}
