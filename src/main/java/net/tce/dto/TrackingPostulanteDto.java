package net.tce.dto;

import java.util.List;

public class TrackingPostulanteDto extends ModeloRscDto{

	private String idTrackingPostulante;
	private String idPosibleCandidato;
	private String idEstadoTracking;
	private String comentario;
	private Integer ordenTrackCumplido;
	private List<TrackingMonitorDto> monitores;
	private List<CandidatoTrackingDto> candidatos;
	private List<PosibleCandidatoDto> lsPosibleCandidatoDto;
	
	
	public String getIdTrackingPostulante() {
		return idTrackingPostulante;
	}
	public void setIdTrackingPostulante(String idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}
	public String getIdPosibleCandidato() {
		return idPosibleCandidato;
	}
	public void setIdPosibleCandidato(String idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}
	public String getIdEstadoTracking() {
		return idEstadoTracking;
	}
	public void setIdEstadoTracking(String idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public List<TrackingMonitorDto> getMonitores() {
		return monitores;
	}
	public void setMonitores(List<TrackingMonitorDto> monitores) {
		this.monitores = monitores;
	}
	
	public List<CandidatoTrackingDto> getCandidatos() {
		return candidatos;
	}
	public void setCandidatos(List<CandidatoTrackingDto> candidatos) {
		this.candidatos = candidatos;
	}
	public List<PosibleCandidatoDto> getLsPosibleCandidatoDto() {
		return lsPosibleCandidatoDto;
	}
	public void setLsPosibleCandidatoDto(List<PosibleCandidatoDto> lsPosibleCandidatoDto) {
		this.lsPosibleCandidatoDto = lsPosibleCandidatoDto;
	}
	public Integer getOrdenTrackCumplido() {
		return ordenTrackCumplido;
	}
	public void setOrdenTrackCumplido(Integer ordenTrackCumplido) {
		this.ordenTrackCumplido = ordenTrackCumplido;
	}
	
	
}
