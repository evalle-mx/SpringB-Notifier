package net.tce.model;
// Generated Mar 2, 2020 12:52:25 PM by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.tce.util.Constante;

/**
 * BitacoraTrackPost generated by hbm2java
 */
@Entity
@Table(name = "bitacora_track_post")
public class BitacoraTrackPost implements java.io.Serializable {

	private long idBitacoraTrackPost;
	private Long idTrackingPostulante;
	private Long idCandidato;
	private Long idPosibleCandidato;
	private Long idEstadoTracking;
	private String comentario;
	private Date fechaInicio;
	private Date fechaFin;
	private Boolean notifEnviada;
	private Long idModeloRscPosFase;
	private Set<BitacoraTrack> bitacoraTracks = new HashSet<BitacoraTrack>(0);
	private Set<BitacoraTrackCont> bitacoraTrackConts = new HashSet<BitacoraTrackCont>(0);
	private Set<BitacoraTrackRecdrio> bitacoraTrackRecdrios = new HashSet<BitacoraTrackRecdrio>(0);

	public BitacoraTrackPost() {
	}

	public BitacoraTrackPost(long idBitacoraTrackPost) {
		this.idBitacoraTrackPost = idBitacoraTrackPost;
	}

	public BitacoraTrackPost(long idBitacoraTrackPost, Long idTrackingPostulante, Long idCandidato,
			Long idPosibleCandidato, Long idEstadoTracking, String comentario, Date fechaInicio, Date fechaFin,
			Boolean notifEnviada, Long idModeloRscPosFase, Set<BitacoraTrack> bitacoraTracks,
			Set<BitacoraTrackCont> bitacoraTrackConts, Set<BitacoraTrackRecdrio> bitacoraTrackRecdrios) {
		this.idBitacoraTrackPost = idBitacoraTrackPost;
		this.idTrackingPostulante = idTrackingPostulante;
		this.idCandidato = idCandidato;
		this.idPosibleCandidato = idPosibleCandidato;
		this.idEstadoTracking = idEstadoTracking;
		this.comentario = comentario;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.notifEnviada = notifEnviada;
		this.idModeloRscPosFase = idModeloRscPosFase;
		this.bitacoraTracks = bitacoraTracks;
		this.bitacoraTrackConts = bitacoraTrackConts;
		this.bitacoraTrackRecdrios = bitacoraTrackRecdrios;
	}

	@Id
	@SequenceGenerator(name="seq_bitacora_track_post", sequenceName=Constante.SECUENCIA_BD_BTC_TRACK_POST, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_bitacora_track_post")
	@Column(name = "id_bitacora_track_post", unique = true, nullable = false)
	public long getIdBitacoraTrackPost() {
		return this.idBitacoraTrackPost;
	}

	public void setIdBitacoraTrackPost(long idBitacoraTrackPost) {
		this.idBitacoraTrackPost = idBitacoraTrackPost;
	}

	@Column(name = "id_tracking_postulante")
	public Long getIdTrackingPostulante() {
		return this.idTrackingPostulante;
	}

	public void setIdTrackingPostulante(Long idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}

	@Column(name = "id_candidato")
	public Long getIdCandidato() {
		return this.idCandidato;
	}

	public void setIdCandidato(Long idCandidato) {
		this.idCandidato = idCandidato;
	}

	@Column(name = "id_posible_candidato")
	public Long getIdPosibleCandidato() {
		return this.idPosibleCandidato;
	}

	public void setIdPosibleCandidato(Long idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}

	@Column(name = "id_estado_tracking")
	public Long getIdEstadoTracking() {
		return this.idEstadoTracking;
	}

	public void setIdEstadoTracking(Long idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}

	@Column(name = "comentario", length = 250)
	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicio", length = 29)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_fin", length = 29)
	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Column(name = "notif_enviada")
	public Boolean getNotifEnviada() {
		return this.notifEnviada;
	}

	public void setNotifEnviada(Boolean notifEnviada) {
		this.notifEnviada = notifEnviada;
	}

	@Column(name = "id_modelo_rsc_pos_fase")
	public Long getIdModeloRscPosFase() {
		return this.idModeloRscPosFase;
	}

	public void setIdModeloRscPosFase(Long idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bitacoraTrackPost")
	public Set<BitacoraTrack> getBitacoraTracks() {
		return this.bitacoraTracks;
	}

	public void setBitacoraTracks(Set<BitacoraTrack> bitacoraTracks) {
		this.bitacoraTracks = bitacoraTracks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bitacoraTrackPost")
	public Set<BitacoraTrackRecdrio> getBitacoraTrackRecdrios() {
		return this.bitacoraTrackRecdrios;
	}

	public void setBitacoraTrackRecdrios(Set<BitacoraTrackRecdrio> bitacoraTrackRecdrios) {
		this.bitacoraTrackRecdrios = bitacoraTrackRecdrios;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bitacoraTrackPost")
	public Set<BitacoraTrackCont> getBitacoraTrackConts() {
		return this.bitacoraTrackConts;
	}

	public void setBitacoraTrackConts(Set<BitacoraTrackCont> bitacoraTrackConts) {
		this.bitacoraTrackConts = bitacoraTrackConts;
	}

}