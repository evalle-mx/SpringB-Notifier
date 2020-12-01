package net.tce.dto;

public class VacancyPoliticaEscolaridadDto extends ComunDto{


	private String idPoliticaEscolaridad;
	private String idGradoAcademico;
	private String idEstatusEscolar;
	private String ponderacion;

	public String getIdPoliticaEscolaridad() {
		return idPoliticaEscolaridad;
	}
	public void setIdPoliticaEscolaridad(String idPoliticaEscolaridad) {
		this.idPoliticaEscolaridad = idPoliticaEscolaridad;
	}
	public String getIdGradoAcademico() {
		return idGradoAcademico;
	}
	public void setIdGradoAcademico(String idGradoAcademico) {
		this.idGradoAcademico = idGradoAcademico;
	}
	public String getIdEstatusEscolar() {
		return idEstatusEscolar;
	}
	public void setIdEstatusEscolar(String idEstatusEscolar) {
		this.idEstatusEscolar = idEstatusEscolar;
	}
	public String getPonderacion() {
		return ponderacion;
	}
	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}
}
