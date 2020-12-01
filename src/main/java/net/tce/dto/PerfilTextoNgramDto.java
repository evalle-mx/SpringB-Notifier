package net.tce.dto;

public class PerfilTextoNgramDto extends ComunDto{


	private String idPerfilTextoNgram;
	private String idPerfil;
	private String idTextoNgram;
	private String texto;
	private String ponderacion;
	private String orden;
	
	public PerfilTextoNgramDto(String texto,String ponderacion,String orden) {
		this.texto=texto;
		this.ponderacion=ponderacion;
		this.orden=orden;
	}
	
	public PerfilTextoNgramDto() {
	}
	
	public String getIdPerfilTextoNgram() {
		return idPerfilTextoNgram;
	}
	public void setIdPerfilTextoNgram(String idPerfilTextoNgram) {
		this.idPerfilTextoNgram = idPerfilTextoNgram;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
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

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
}
