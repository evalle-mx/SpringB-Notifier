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
 * BitacoraTrackMon generated by hbm2java
 */
@Entity
@Table(name = "bitacora_track_mon")
public class BitacoraTrackMon implements java.io.Serializable {

	private long idBitacoraTrackMon;
	private Long idTrackingMonitor;
	private Long idTrackingPostulante;
	private Long idRelacionEmpresaPersona;
	private Long idEstadoTracking;
	private Long idContenido;
	private String comentario;
	private Date fechaInicio;
	private Date fechaFin;
	private Boolean enGrupo;
	private Boolean notifEnviada;
	private Long idMonitor;
	private Set<BitacoraTrack> bitacoraTracks = new HashSet<BitacoraTrack>(0);
	private Set<BitacoraTrackRecdrio> bitacoraTrackRecdrios = new HashSet<BitacoraTrackRecdrio>(0);
	private Set<BitacoraTrackCont> bitacoraTrackConts = new HashSet<BitacoraTrackCont>(0);

	public BitacoraTrackMon() {
	}

	public BitacoraTrackMon(long idBitacoraTrackMon) {
		this.idBitacoraTrackMon = idBitacoraTrackMon;
	}

	public BitacoraTrackMon(long idBitacoraTrackMon, Long idTrackingMonitor, Long idTrackingPostulante,
			Long idRelacionEmpresaPersona, Long idEstadoTracking, Long idContenido, String comentario, Date fechaInicio,
			Date fechaFin, Boolean enGrupo, Boolean notifEnviada, Long idMonitor, Set<BitacoraTrack> bitacoraTracks,
			Set<BitacoraTrackRecdrio> bitacoraTrackRecdrios, Set<BitacoraTrackCont> bitacoraTrackConts) {
		this.idBitacoraTrackMon = idBitacoraTrackMon;
		this.idTrackingMonitor = idTrackingMonitor;
		this.idTrackingPostulante = idTrackingPostulante;
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
		this.idEstadoTracking = idEstadoTracking;
		this.idContenido = idContenido;
		this.comentario = comentario;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.enGrupo = enGrupo;
		this.notifEnviada = notifEnviada;
		this.idMonitor = idMonitor;
		this.bitacoraTracks = bitacoraTracks;
		this.bitacoraTrackRecdrios = bitacoraTrackRecdrios;
		this.bitacoraTrackConts = bitacoraTrackConts;
	}
	
	@Id
	@SequenceGenerator(name="seq_bitacora_track_mon", sequenceName=Constante.SECUENCIA_BD_BTC_TRACK_MON, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_bitacora_track_mon")
	@Column(name = "id_bitacora_track_mon", unique = true, nullable = false)
	public long getIdBitacoraTrackMon() {
		return this.idBitacoraTrackMon;
	}

	public void setIdBitacoraTrackMon(long idBitacoraTrackMon) {
		this.idBitacoraTrackMon = idBitacoraTrackMon;
	}

	@Column(name = "id_tracking_monitor")
	public Long getIdTrackingMonitor() {
		return this.idTrackingMonitor;
	}

	public void setIdTrackingMonitor(Long idTrackingMonitor) {
		this.idTrackingMonitor = idTrackingMonitor;
	}

	@Column(name = "id_tracking_postulante")
	public Long getIdTrackingPostulante() {
		return this.idTrackingPostulante;
	}

	public void setIdTrackingPostulante(Long idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}

	@Column(name = "id_relacion_empresa_persona")
	public Long getIdRelacionEmpresaPersona() {
		return this.idRelacionEmpresaPersona;
	}

	public void setIdRelacionEmpresaPersona(Long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}

	@Column(name = "id_estado_tracking")
	public Long getIdEstadoTracking() {
		return this.idEstadoTracking;
	}

	public void setIdEstadoTracking(Long idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}

	@Column(name = "id_contenido")
	public Long getIdContenido() {
		return this.idContenido;
	}

	public void setIdContenido(Long idContenido) {
		this.idContenido = idContenido;
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

	@Column(name = "en_grupo")
	public Boolean getEnGrupo() {
		return this.enGrupo;
	}

	public void setEnGrupo(Boolean enGrupo) {
		this.enGrupo = enGrupo;
	}

	@Column(name = "notif_enviada")
	public Boolean getNotifEnviada() {
		return this.notifEnviada;
	}

	public void setNotifEnviada(Boolean notifEnviada) {
		this.notifEnviada = notifEnviada;
	}

	@Column(name = "id_monitor")
	public Long getIdMonitor() {
		return this.idMonitor;
	}

	public void setIdMonitor(Long idMonitor) {
		this.idMonitor = idMonitor;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bitacoraTrackMon")
	public Set<BitacoraTrack> getBitacoraTracks() {
		return this.bitacoraTracks;
	}

	public void setBitacoraTracks(Set<BitacoraTrack> bitacoraTracks) {
		this.bitacoraTracks = bitacoraTracks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bitacoraTrackMon")
	public Set<BitacoraTrackRecdrio> getBitacoraTrackRecdrios() {
		return this.bitacoraTrackRecdrios;
	}

	public void setBitacoraTrackRecdrios(Set<BitacoraTrackRecdrio> bitacoraTrackRecdrios) {
		this.bitacoraTrackRecdrios = bitacoraTrackRecdrios;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bitacoraTrackMon")
	public Set<BitacoraTrackCont> getBitacoraTrackConts() {
		return this.bitacoraTrackConts;
	}

	public void setBitacoraTrackConts(Set<BitacoraTrackCont> bitacoraTrackConts) {
		this.bitacoraTrackConts = bitacoraTrackConts;
	}

}
