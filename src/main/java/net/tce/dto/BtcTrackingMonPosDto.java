package net.tce.dto;

import java.util.Date;
import java.util.List;

public class BtcTrackingMonPosDto extends ComunDto{
	
	private Long idMonitor;
	private Long idModeloRscPosFase;
	private Long idRelacionEmpresaPersona;
	private Long idRelacionEmpresaPersonaS;
	private String principal;
	
	//BitacoraTrackMon y BitacoraTrackPost
	private Long idBitacoraTrackMon;
	private Long idBitacoraTrackPost;
	private Long idTrackingPostulante;
	private Long idTrackingMonitor;
	private Long idEstadoTracking;
	private Long idCandidato;
	private Long idPosibleCandidato;
	private Long idContenido;
	private String comentario;
	private Date fechaInicio;
	private Date fechaFin;
	private String enGrupo;
	private String notifEnviada;
	
	List<BtcRecdrioDto> lsBtcRecdrioDto;

	
	public Long getIdBitacoraTrackMon() {
		return idBitacoraTrackMon;
	}
	public void setIdBitacoraTrackMon(Long idBitacoraTrackMon) {
		this.idBitacoraTrackMon = idBitacoraTrackMon;
	}
	public Long getIdTrackingMonitor() {
		return idTrackingMonitor;
	}
	public void setIdTrackingMonitor(Long idTrackingMonitor) {
		this.idTrackingMonitor = idTrackingMonitor;
	}
	public Long getIdTrackingPostulante() {
		return idTrackingPostulante;
	}
	public void setIdTrackingPostulante(Long idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}
	public Long getIdEstadoTracking() {
		return idEstadoTracking;
	}
	public void setIdEstadoTracking(Long idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}
	public Long getIdContenido() {
		return idContenido;
	}
	public void setIdContenido(Long idContenido) {
		this.idContenido = idContenido;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getEnGrupo() {
		return enGrupo;
	}
	public void setEnGrupo(String enGrupo) {
		this.enGrupo = enGrupo;
	}
	public String getNotifEnviada() {
		return notifEnviada;
	}
	public void setNotifEnviada(String notifEnviada) {
		this.notifEnviada = notifEnviada;
	}
	public Long getIdMonitor() {
		return idMonitor;
	}
	public void setIdMonitor(Long idMonitor) {
		this.idMonitor = idMonitor;
	}
	public Long getIdModeloRscPosFase() {
		return idModeloRscPosFase;
	}
	public void setIdModeloRscPosFase(Long idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}
	public Long getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(Long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public Long getIdRelacionEmpresaPersonaS() {
		return idRelacionEmpresaPersonaS;
	}
	public void setIdRelacionEmpresaPersonaS(Long idRelacionEmpresaPersonaS) {
		this.idRelacionEmpresaPersonaS = idRelacionEmpresaPersonaS;
	}
	public Long getIdBitacoraTrackPost() {
		return idBitacoraTrackPost;
	}
	public void setIdBitacoraTrackPost(Long idBitacoraTrackPost) {
		this.idBitacoraTrackPost = idBitacoraTrackPost;
	}
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
	public List<BtcRecdrioDto> getLsBtcRecdrioDto() {
		return lsBtcRecdrioDto;
	}
	public void setLsBtcRecdrioDto(List<BtcRecdrioDto> lsBtcRecdrioDto) {
		this.lsBtcRecdrioDto = lsBtcRecdrioDto;
	}
	
}
