package net.tce.dto;

public class PerfilTextoNgramValidacionDto extends ComunDto{

	private Long idPerfilTextoNgram;
	private Long idPerfil;
	private String texto;
	private Short ponderacion;
	private Integer orden;
	
	public PerfilTextoNgramValidacionDto() {
	}
	
	public Long getIdPerfilTextoNgram() {
		return idPerfilTextoNgram;
	}

	public void setIdPerfilTextoNgram(Long idPerfilTextoNgram) {
		this.idPerfilTextoNgram = idPerfilTextoNgram;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	
	public Short getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Short ponderacion) {
		this.ponderacion = ponderacion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
