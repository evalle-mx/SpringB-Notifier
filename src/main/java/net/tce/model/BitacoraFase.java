package net.tce.model;
// Generated Jan 24, 2020 6:06:30 PM by Hibernate Tools 3.5.0.Final

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
 * BitacoraFase generated by hbm2java
 */
@Entity
@Table(name = "bitacora_fase")
public class BitacoraFase implements java.io.Serializable {

	private long idBitacoraFase;
	private Short idModeloRsc;
	private Long idModeloRscPos;
	private Long idModeloRscFase;
	private Long idModeloRscPosFase;
	private Long idModeloRscPosFaseRel;
	private String nombre;
	private String descripcion;
	private Short dias;
	private Boolean activo;
	private Date fechaInicio;
	private Date fechaFin;
	private Boolean subirArchivo;
	private Boolean bajarArchivo;
	private Boolean editarComenteario;
	private Short orden;
	private Short actividad;
	private Set<BitacoraTrack> bitacoraTracks = new HashSet<BitacoraTrack>(0);

	public BitacoraFase() {
	}

	public BitacoraFase(long idBitacoraFase) {
		this.idBitacoraFase = idBitacoraFase;
	}

	public BitacoraFase(long idBitacoraFase, Short idModeloRsc, Long idModeloRscPos, Long idModeloRscFase,
			Long idModeloRscPosFase, Long idModeloRscPosFaseRel, String nombre, String descripcion, Short dias,
			Boolean activo, Date fechaInicio, Date fechaFin, Boolean subirArchivo, Boolean bajarArchivo,
			Boolean editarComenteario, Short orden, Short actividad, Set<BitacoraTrack> bitacoraTracks) {
		this.idBitacoraFase = idBitacoraFase;
		this.idModeloRsc = idModeloRsc;
		this.idModeloRscPos = idModeloRscPos;
		this.idModeloRscFase = idModeloRscFase;
		this.idModeloRscPosFase = idModeloRscPosFase;
		this.idModeloRscPosFaseRel = idModeloRscPosFaseRel;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.dias = dias;
		this.activo = activo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.subirArchivo = subirArchivo;
		this.bajarArchivo = bajarArchivo;
		this.editarComenteario = editarComenteario;
		this.orden = orden;
		this.actividad = actividad;
		this.bitacoraTracks = bitacoraTracks;
	}

	@Id
	@SequenceGenerator(name="seq_bitacora_fase", sequenceName=Constante.SECUENCIA_BD_BTC_FASE, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_bitacora_fase")
	@Column(name = "id_bitacora_fase", unique = true, nullable = false)
	public long getIdBitacoraFase() {
		return this.idBitacoraFase;
	}

	public void setIdBitacoraFase(long idBitacoraFase) {
		this.idBitacoraFase = idBitacoraFase;
	}

	@Column(name = "id_modelo_rsc")
	public Short getIdModeloRsc() {
		return this.idModeloRsc;
	}

	public void setIdModeloRsc(Short idModeloRsc) {
		this.idModeloRsc = idModeloRsc;
	}

	@Column(name = "id_modelo_rsc_pos")
	public Long getIdModeloRscPos() {
		return this.idModeloRscPos;
	}

	public void setIdModeloRscPos(Long idModeloRscPos) {
		this.idModeloRscPos = idModeloRscPos;
	}

	@Column(name = "id_modelo_rsc_fase")
	public Long getIdModeloRscFase() {
		return this.idModeloRscFase;
	}

	public void setIdModeloRscFase(Long idModeloRscFase) {
		this.idModeloRscFase = idModeloRscFase;
	}

	@Column(name = "id_modelo_rsc_pos_fase")
	public Long getIdModeloRscPosFase() {
		return this.idModeloRscPosFase;
	}

	public void setIdModeloRscPosFase(Long idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}

	@Column(name = "id_modelo_rsc_pos_fase_rel")
	public Long getIdModeloRscPosFaseRel() {
		return this.idModeloRscPosFaseRel;
	}

	public void setIdModeloRscPosFaseRel(Long idModeloRscPosFaseRel) {
		this.idModeloRscPosFaseRel = idModeloRscPosFaseRel;
	}

	@Column(name = "nombre", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "dias")
	public Short getDias() {
		return this.dias;
	}

	public void setDias(Short dias) {
		this.dias = dias;
	}

	@Column(name = "activo")
	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
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

	@Column(name = "subir_archivo")
	public Boolean getSubirArchivo() {
		return this.subirArchivo;
	}

	public void setSubirArchivo(Boolean subirArchivo) {
		this.subirArchivo = subirArchivo;
	}

	@Column(name = "bajar_archivo")
	public Boolean getBajarArchivo() {
		return this.bajarArchivo;
	}

	public void setBajarArchivo(Boolean bajarArchivo) {
		this.bajarArchivo = bajarArchivo;
	}

	@Column(name = "editar_comenteario")
	public Boolean getEditarComenteario() {
		return this.editarComenteario;
	}

	public void setEditarComenteario(Boolean editarComenteario) {
		this.editarComenteario = editarComenteario;
	}

	@Column(name = "orden")
	public Short getOrden() {
		return this.orden;
	}

	public void setOrden(Short orden) {
		this.orden = orden;
	}

	@Column(name = "actividad")
	public Short getActividad() {
		return this.actividad;
	}

	public void setActividad(Short actividad) {
		this.actividad = actividad;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bitacoraFase")
	public Set<BitacoraTrack> getBitacoraTracks() {
		return this.bitacoraTracks;
	}

	public void setBitacoraTracks(Set<BitacoraTrack> bitacoraTracks) {
		this.bitacoraTracks = bitacoraTracks;
	}

}
