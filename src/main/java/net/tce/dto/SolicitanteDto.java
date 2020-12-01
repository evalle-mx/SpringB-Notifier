package net.tce.dto;

public class SolicitanteDto {

	private String solicitante;
	private String tipoSujeto;
	private String idSujeto;
	private Boolean hayHandcheck;
	
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setTipoSujeto(String tipoSujeto) {
		this.tipoSujeto = tipoSujeto;
	}
	public String getTipoSujeto() {
		return tipoSujeto;
	}
	public void setIdSujeto(String idSujeto) {
		this.idSujeto = idSujeto;
	}
	public String getIdSujeto() {
		return idSujeto;
	}
	public Boolean getHayHandcheck() {
		return hayHandcheck;
	}
	public void setHayHandcheck(Boolean hayHandcheck) {
		this.hayHandcheck = hayHandcheck;
	}
	
	
	
}
