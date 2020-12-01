package net.tce.dto;

public class BtcTrackCandDto extends ComunDto{
	
	private Long idCandidato;
	private Long idPosibleCandidato;
	private Long idEstadoTracking;
	private Long idEstatusOperativo;
	private Long idPerfilPosicion;
	private Long idRelacionEmpresaPersona;
	private Long idTipoPosibleCandidato;
	private String comentario;
	private Boolean confirmado;
	private String tokenAdd;
	
	public Long getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(Long idCandidato) {
		this.idCandidato = idCandidato;
	}
	public Long getIdPosibleCandidato() {
		return idPosibleCandidato;
	}
	public void setIdPosibleCandidato(Long idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}
	public Long getIdEstadoTracking() {
		return idEstadoTracking;
	}
	public void setIdEstadoTracking(Long idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}
	public Long getIdEstatusOperativo() {
		return idEstatusOperativo;
	}
	public void setIdEstatusOperativo(Long idEstatusOperativo) {
		this.idEstatusOperativo = idEstatusOperativo;
	}
	public Long getIdPerfilPosicion() {
		return idPerfilPosicion;
	}
	public void setIdPerfilPosicion(Long idPerfilPosicion) {
		this.idPerfilPosicion = idPerfilPosicion;
	}
	public Long getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(Long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	public Long getIdTipoPosibleCandidato() {
		return idTipoPosibleCandidato;
	}
	public void setIdTipoPosibleCandidato(Long idTipoPosibleCandidato) {
		this.idTipoPosibleCandidato = idTipoPosibleCandidato;
	}
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Boolean getConfirmado() {
		return confirmado;
	}
	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}
	public String getTokenAdd() {
		return tokenAdd;
	}
	public void setTokenAdd(String tokenAdd) {
		this.tokenAdd = tokenAdd;
	}
	
	
}
