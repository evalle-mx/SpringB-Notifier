package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * MotivoRechazo generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "MOTIVO_RECHAZO")
public class MotivoRechazo implements java.io.Serializable {

	private long idMotivoRechazo;
	private String descripcion;
	private String significado;
	private boolean aplicacion;
	private boolean estatusRegistro;
	private Set<CandidatoRechazo> candidatoRechazos = new HashSet<CandidatoRechazo>(
			0);

	public MotivoRechazo() {
	}

	public MotivoRechazo(long idMotivoRechazo, String descripcion) {
		this.idMotivoRechazo = idMotivoRechazo;
		this.descripcion = descripcion;
	}

	public MotivoRechazo(long idMotivoRechazo, String descripcion,
			String significado, boolean aplicacion, boolean estatusRegistro,
			Set<CandidatoRechazo> candidatoRechazos) {
		this.idMotivoRechazo = idMotivoRechazo;
		this.descripcion = descripcion;
		this.significado = significado;
		this.aplicacion = aplicacion;
		this.estatusRegistro = estatusRegistro;
		this.candidatoRechazos = candidatoRechazos;
	}

	@Id
	@Column(name = "ID_MOTIVO_RECHAZO", unique = true, nullable = false)
	public long getIdMotivoRechazo() {
		return this.idMotivoRechazo;
	}

	public void setIdMotivoRechazo(long idMotivoRechazo) {
		this.idMotivoRechazo = idMotivoRechazo;
	}

	@Column(name = "DESCRIPCION", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "SIGNIFICADO", length = 1500)
	public String getSignificado() {
		return this.significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}

	@Column(name = "APLICACION", nullable = false)
	public boolean isAplicacion() {
		return this.aplicacion;
	}

	public void setAplicacion(boolean aplicacion) {
		this.aplicacion = aplicacion;
	}

	@Column(name = "ESTATUS_REGISTRO", nullable = false)
	public boolean isEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "motivoRechazo")
	public Set<CandidatoRechazo> getCandidatoRechazos() {
		return this.candidatoRechazos;
	}

	public void setCandidatoRechazos(Set<CandidatoRechazo> candidatoRechazos) {
		this.candidatoRechazos = candidatoRechazos;
	}

}
