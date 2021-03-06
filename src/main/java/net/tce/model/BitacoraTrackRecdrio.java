package net.tce.model;
// Generated Mar 18, 2020 8:40:32 PM by Hibernate Tools 3.5.0.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.tce.util.Constante;

/**
 * BitacoraTrackRecdrio generated by hbm2java
 */
@Entity
@Table(name = "bitacora_track_recdrio")
public class BitacoraTrackRecdrio implements java.io.Serializable {

	private long idBitacoraTrackRecdrio;
	private BitacoraTrackMon bitacoraTrackMon;
	private BitacoraTrackPost bitacoraTrackPost;
	private Long idRecordatorio;
	private Long idTipoRecordatorio;
	private Boolean activo;
	private Date fecha;
	private Short cifra;
	private Boolean seAplico;
	private String descripcion;

	public BitacoraTrackRecdrio() {
	}

	public BitacoraTrackRecdrio(long idBitacoraTrackRecdrio) {
		this.idBitacoraTrackRecdrio = idBitacoraTrackRecdrio;
	}

	public BitacoraTrackRecdrio(long idBitacoraTrackRecdrio, BitacoraTrackMon bitacoraTrackMon,
			BitacoraTrackPost bitacoraTrackPost, Long idRecordatorio, Long idTipoRecordatorio, Boolean activo,
			Date fecha, Short cifra, Boolean seAplico, String descripcion) {
		this.idBitacoraTrackRecdrio = idBitacoraTrackRecdrio;
		this.bitacoraTrackMon = bitacoraTrackMon;
		this.bitacoraTrackPost = bitacoraTrackPost;
		this.idRecordatorio = idRecordatorio;
		this.idTipoRecordatorio = idTipoRecordatorio;
		this.activo = activo;
		this.fecha = fecha;
		this.cifra = cifra;
		this.seAplico = seAplico;
		this.descripcion = descripcion;
	}

	@Id
	@SequenceGenerator(name="seq_bitacora_track_recdrio", sequenceName=Constante.SECUENCIA_BD_BTC_TRACK_RECDRIO, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_bitacora_track_recdrio")
	@Column(name = "id_bitacora_track_recdrio", unique = true, nullable = false)
	public long getIdBitacoraTrackRecdrio() {
		return this.idBitacoraTrackRecdrio;
	}

	public void setIdBitacoraTrackRecdrio(long idBitacoraTrackRecdrio) {
		this.idBitacoraTrackRecdrio = idBitacoraTrackRecdrio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bitacora_track_mon")
	public BitacoraTrackMon getBitacoraTrackMon() {
		return this.bitacoraTrackMon;
	}

	public void setBitacoraTrackMon(BitacoraTrackMon bitacoraTrackMon) {
		this.bitacoraTrackMon = bitacoraTrackMon;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bitacora_track_post")
	public BitacoraTrackPost getBitacoraTrackPost() {
		return this.bitacoraTrackPost;
	}

	public void setBitacoraTrackPost(BitacoraTrackPost bitacoraTrackPost) {
		this.bitacoraTrackPost = bitacoraTrackPost;
	}

	@Column(name = "id_recordatorio")
	public Long getIdRecordatorio() {
		return this.idRecordatorio;
	}

	public void setIdRecordatorio(Long idRecordatorio) {
		this.idRecordatorio = idRecordatorio;
	}

	@Column(name = "id_tipo_recordatorio")
	public Long getIdTipoRecordatorio() {
		return this.idTipoRecordatorio;
	}

	public void setIdTipoRecordatorio(Long idTipoRecordatorio) {
		this.idTipoRecordatorio = idTipoRecordatorio;
	}

	@Column(name = "activo")
	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha", length = 29)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "cifra")
	public Short getCifra() {
		return this.cifra;
	}

	public void setCifra(Short cifra) {
		this.cifra = cifra;
	}

	@Column(name = "se_aplico")
	public Boolean getSeAplico() {
		return this.seAplico;
	}

	public void setSeAplico(Boolean seAplico) {
		this.seAplico = seAplico;
	}

	@Column(name = "descripcion", length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
