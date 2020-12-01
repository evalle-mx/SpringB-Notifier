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
 * TipoContrato generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "TIPO_CONTRATO")
public class TipoContrato implements java.io.Serializable {

	private long idTipoContrato;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<BitacoraPosicion> bitacoraPosicions = new HashSet<BitacoraPosicion>(
			0);
	private Set<Posicion> posicions = new HashSet<Posicion>(0);
	private Set<Persona> personas = new HashSet<Persona>(0);
	private Set<ExperienciaLaboral> experienciaLaborals = new HashSet<ExperienciaLaboral>(
			0);

	public TipoContrato() {
	}

	public TipoContrato(long idTipoContrato, String descripcion) {
		this.idTipoContrato = idTipoContrato;
		this.descripcion = descripcion;
	}

	public TipoContrato(long idTipoContrato, String descripcion,
			String significado, boolean estatusRegistro,
			Set<BitacoraPosicion> bitacoraPosicions, Set<Posicion> posicions,
			Set<Persona> personas, Set<ExperienciaLaboral> experienciaLaborals) {
		this.idTipoContrato = idTipoContrato;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.bitacoraPosicions = bitacoraPosicions;
		this.posicions = posicions;
		this.personas = personas;
		this.experienciaLaborals = experienciaLaborals;
	}

	@Id
	@Column(name = "id_tipo_contrato", unique = true, nullable = false)
	public long getIdTipoContrato() {
		return this.idTipoContrato;
	}

	public void setIdTipoContrato(long idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	@Column(name = "descripcion", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "significado", length = 1500)
	public String getSignificado() {
		return this.significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}

	@Column(name = "estatus_registro", nullable = false)
	public boolean isEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoContrato")
	public Set<BitacoraPosicion> getBitacoraPosicions() {
		return this.bitacoraPosicions;
	}

	public void setBitacoraPosicions(Set<BitacoraPosicion> bitacoraPosicions) {
		this.bitacoraPosicions = bitacoraPosicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoContrato")
	public Set<Posicion> getPosicions() {
		return this.posicions;
	}

	public void setPosicions(Set<Posicion> posicions) {
		this.posicions = posicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoContrato")
	public Set<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoContrato")
	public Set<ExperienciaLaboral> getExperienciaLaborals() {
		return this.experienciaLaborals;
	}

	public void setExperienciaLaborals(
			Set<ExperienciaLaboral> experienciaLaborals) {
		this.experienciaLaborals = experienciaLaborals;
	}


}