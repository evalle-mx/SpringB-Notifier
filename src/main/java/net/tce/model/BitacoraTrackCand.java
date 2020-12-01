package net.tce.model;
// Generated Jan 15, 2020 8:17:29 PM by Hibernate Tools 3.5.0.Final

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

import net.tce.util.Constante;

/**
 * BitacoraTrackCand generated by hbm2java
 */
@Entity
@Table(name = "bitacora_track_cand")
public class BitacoraTrackCand implements java.io.Serializable {

	private long idBitacoraTrackCand;
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
	private Set<BitacoraTrack> bitacoraTracks = new HashSet<BitacoraTrack>(0);

	public BitacoraTrackCand() {
	}

	public BitacoraTrackCand(long idBitacoraTrackCand) {
		this.idBitacoraTrackCand = idBitacoraTrackCand;
	}

	public BitacoraTrackCand(long idBitacoraTrackCand, Long idCandidato, Long idPosibleCandidato, Long idEstadoTracking,
			Long idEstatusOperativo, Long idPerfilPosicion, Long idRelacionEmpresaPersona, Long idTipoPosibleCandidato,
			String comentario, Boolean confirmado, String tokenAdd, Set<BitacoraTrack> bitacoraTracks) {
		this.idBitacoraTrackCand = idBitacoraTrackCand;
		this.idCandidato = idCandidato;
		this.idPosibleCandidato = idPosibleCandidato;
		this.idEstadoTracking = idEstadoTracking;
		this.idEstatusOperativo = idEstatusOperativo;
		this.idPerfilPosicion = idPerfilPosicion;
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
		this.idTipoPosibleCandidato = idTipoPosibleCandidato;
		this.comentario = comentario;
		this.confirmado = confirmado;
		this.tokenAdd = tokenAdd;
		this.bitacoraTracks = bitacoraTracks;
	}

	@Id
	@SequenceGenerator(name="seq_bitacora_track_cand", sequenceName=Constante.SECUENCIA_BD_BTC_TRACK_CAND, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_bitacora_track_cand")
	@Column(name = "id_bitacora_track_cand", unique = true, nullable = false)
	public long getIdBitacoraTrackCand() {
		return this.idBitacoraTrackCand;
	}

	public void setIdBitacoraTrackCand(long idBitacoraTrackCand) {
		this.idBitacoraTrackCand = idBitacoraTrackCand;
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

	@Column(name = "id_estatus_operativo")
	public Long getIdEstatusOperativo() {
		return this.idEstatusOperativo;
	}

	public void setIdEstatusOperativo(Long idEstatusOperativo) {
		this.idEstatusOperativo = idEstatusOperativo;
	}

	@Column(name = "id_perfil_posicion")
	public Long getIdPerfilPosicion() {
		return this.idPerfilPosicion;
	}

	public void setIdPerfilPosicion(Long idPerfilPosicion) {
		this.idPerfilPosicion = idPerfilPosicion;
	}

	@Column(name = "id_relacion_empresa_persona")
	public Long getIdRelacionEmpresaPersona() {
		return this.idRelacionEmpresaPersona;
	}

	public void setIdRelacionEmpresaPersona(Long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}

	@Column(name = "id_tipo_posible_candidato")
	public Long getIdTipoPosibleCandidato() {
		return this.idTipoPosibleCandidato;
	}

	public void setIdTipoPosibleCandidato(Long idTipoPosibleCandidato) {
		this.idTipoPosibleCandidato = idTipoPosibleCandidato;
	}

	@Column(name = "comentario", length = 250)
	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Column(name = "confirmado")
	public Boolean getConfirmado() {
		return this.confirmado;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	@Column(name = "token_add", length = 100)
	public String getTokenAdd() {
		return this.tokenAdd;
	}

	public void setTokenAdd(String tokenAdd) {
		this.tokenAdd = tokenAdd;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bitacoraTrackCand")
	public Set<BitacoraTrack> getBitacoraTracks() {
		return this.bitacoraTracks;
	}

	public void setBitacoraTracks(Set<BitacoraTrack> bitacoraTracks) {
		this.bitacoraTracks = bitacoraTracks;
	}

}