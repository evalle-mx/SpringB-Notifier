package net.tce.dto;

public class VacancyPerfilTextoNgramDto extends ComunDto{

	private String idPerfilTextoNgram;
	private String idTextoNgram;
	private String texto;
	private String ponderacion;
	
	public VacancyPerfilTextoNgramDto() {
	}
	
	public String getIdPerfilTextoNgram() {
		return idPerfilTextoNgram;
	}
	public void setIdPerfilTextoNgram(String idPerfilTextoNgram) {
		this.idPerfilTextoNgram = idPerfilTextoNgram;
	}
	public String getIdTextoNgram() {
		return idTextoNgram;
	}
	public void setIdTextoNgram(String idTextoNgram) {
		this.idTextoNgram = idTextoNgram;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}
}
