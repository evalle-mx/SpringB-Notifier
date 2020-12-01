package net.tce.dto;

import java.util.Date;
import java.util.List;

public class TrackingMonitorDto extends MonitorDto {
	
	private String idTrackingMonitor;
	private String idEstadoTracking;
	private String idTrackingPostulante;
	private String comentario;
	private String postulante;
	private Boolean bEnGrupo;
	private String enGrupo;
	private String notificacionEnviada;
	private Boolean oculto;
	private Long idRelEmpPerMonitor;
	private List<CandidatoTrackingDto> candidatos;
	private List<ModeloRscPosFaseDto> tracks;
	
	public TrackingMonitorDto(){}
	
	
	
	public TrackingMonitorDto(Long idPersona, boolean principal){
		setIdPersona(String.valueOf(idPersona) );
		setPrincipal(principal ? "1":"0");
	}
	
	/**
	 * Constructor para TrackingMonitorDaoImpl.getByPostulante()
	 * @param enGrupo
	 * @param nombrePostulante
	 */
	public TrackingMonitorDto(Boolean enGrupo, String nombrePostulante) {
		this.bEnGrupo=enGrupo;
		this.setNombre(nombrePostulante);
	}
	
	/**
	 * Constructor para TrackingMonitorDaoImpl.getAllByPosicion()
	 * @param idEstadoTracking
	 * @param orden
	 * @param actividad
	 * @param idTrackingPostulante
	 */
	public TrackingMonitorDto( Long idEstadoTracking,Short orden,Short actividad,Long idTrackingPostulante,
			String comentario, Date fechaInicio, Date fechaFin, Long idMonitor) {
		this.idEstadoTracking=String.valueOf(idEstadoTracking);
		this.setOrden(String.valueOf(orden));
		this.setActividad(String.valueOf(actividad));
		this.idTrackingPostulante=idTrackingPostulante.toString();
		this.comentario=comentario;
		this.setFechaInicio(fechaInicio!=null?String.valueOf(fechaInicio):null);
		this.setFechaFin(fechaFin!=null?String.valueOf(fechaFin):null);
		this.setIdMonitor(idMonitor.toString());
	}
	
	
	
	public List<CandidatoTrackingDto> getCandidatos() {
		return candidatos;
	}
	public void setCandidatos(List<CandidatoTrackingDto> candidatos) {
		this.candidatos = candidatos;
	}
	
	public List<ModeloRscPosFaseDto> getTracks() {
		return tracks;
	}
	public void setTracks(List<ModeloRscPosFaseDto> tracks) {
		this.tracks = tracks;
	}

	public String getIdTrackingMonitor() {
		return idTrackingMonitor;
	}

	public void setIdTrackingMonitor(String idTrackingMonitor) {
		this.idTrackingMonitor = idTrackingMonitor;
	}

	public String getIdEstadoTracking() {
		return idEstadoTracking;
	}

	public void setIdEstadoTracking(String idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}

	public String getIdTrackingPostulante() {
		return idTrackingPostulante;
	}

	public void setIdTrackingPostulante(String idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Boolean getbEnGrupo() {
		return bEnGrupo;
	}

	public void setbEnGrupo(Boolean bEnGrupo) {
		this.bEnGrupo = bEnGrupo;
	}

	public String getEnGrupo() {
		return enGrupo;
	}

	public void setEnGrupo(String enGrupo) {
		this.enGrupo = enGrupo;
	}

	public String getNotificacionEnviada() {
		return notificacionEnviada;
	}

	public void setNotificacionEnviada(String notificacionEnviada) {
		this.notificacionEnviada = notificacionEnviada;
	}

	public String getPostulante() {
		return postulante;
	}

	public void setPostulante(String postulante) {
		this.postulante = postulante;
	}



	public Boolean getOculto() {
		return oculto;
	}



	public void setOculto(Boolean oculto) {
		this.oculto = oculto;
	}



	public Long getIdRelEmpPerMonitor() {
		return idRelEmpPerMonitor;
	}



	public void setIdRelEmpPerMonitor(Long idRelEmpPerMonitor) {
		this.idRelEmpPerMonitor = idRelEmpPerMonitor;
	}
	
	
}
