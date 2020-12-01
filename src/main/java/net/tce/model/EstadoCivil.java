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
 * EstadoCivil generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "ESTADO_CIVIL")
public class EstadoCivil implements java.io.Serializable {

	private long idEstadoCivil;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<PoliticaMValor> politicaMValors = new HashSet<PoliticaMValor>(0);
	private Set<Persona> personas = new HashSet<Persona>(0);
	private Set<BitacoraPoliticaValor> bitacoraPoliticaValors = new HashSet<BitacoraPoliticaValor>(
			0);

	public EstadoCivil() {
	}

	public EstadoCivil(long idEstadoCivil, String descripcion) {
		this.idEstadoCivil = idEstadoCivil;
		this.descripcion = descripcion;
	}

	public EstadoCivil(long idEstadoCivil, String descripcion,
			String significado, boolean estatusRegistro,
			Set<PoliticaMValor> politicaMValors, Set<Persona> personas,
			Set<BitacoraPoliticaValor> bitacoraPoliticaValors) {
		this.idEstadoCivil = idEstadoCivil;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.politicaMValors = politicaMValors;
		this.personas = personas;
		this.bitacoraPoliticaValors = bitacoraPoliticaValors;
	}

	@Id
	@Column(name = "id_estado_civil", unique = true, nullable = false)
	public long getIdEstadoCivil() {
		return this.idEstadoCivil;
	}

	public void setIdEstadoCivil(long idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoCivil")
	public Set<PoliticaMValor> getPoliticaMValors() {
		return this.politicaMValors;
	}

	public void setPoliticaMValors(Set<PoliticaMValor> politicaMValors) {
		this.politicaMValors = politicaMValors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoCivil")
	public Set<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoCivil")
	public Set<BitacoraPoliticaValor> getBitacoraPoliticaValors() {
		return this.bitacoraPoliticaValors;
	}

	public void setBitacoraPoliticaValors(
			Set<BitacoraPoliticaValor> bitacoraPoliticaValors) {
		this.bitacoraPoliticaValors = bitacoraPoliticaValors;
	}
}
