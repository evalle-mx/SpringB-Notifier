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
 * TipoJornada generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "TIPO_JORNADA")
public class TipoJornada implements java.io.Serializable {

	private long idTipoJornada;
	private String descripcion;
	private String significado;
	private float ponderacion;
	private boolean estatusRegistro;
	private Set<BitacoraPosicion> bitacoraPosicions = new HashSet<BitacoraPosicion>(
			0);
	private Set<Persona> personas = new HashSet<Persona>(0);
	private Set<ExperienciaLaboral> experienciaLaborals = new HashSet<ExperienciaLaboral>(
			0);
	private Set<Posicion> posicions = new HashSet<Posicion>(0);

	public TipoJornada() {
	}

	public TipoJornada(long idTipoJornada, String descripcion, float ponderacion) {
		this.idTipoJornada = idTipoJornada;
		this.descripcion = descripcion;
		this.ponderacion = ponderacion;
	}

	public TipoJornada(long idTipoJornada, String descripcion,
			String significado, float ponderacion, boolean estatusRegistro,
			Set<BitacoraPosicion> bitacoraPosicions, Set<Persona> personas,
			Set<ExperienciaLaboral> experienciaLaborals, Set<Posicion> posicions) {
		this.idTipoJornada = idTipoJornada;
		this.descripcion = descripcion;
		this.significado = significado;
		this.ponderacion = ponderacion;
		this.estatusRegistro = estatusRegistro;
		this.bitacoraPosicions = bitacoraPosicions;
		this.personas = personas;
		this.experienciaLaborals = experienciaLaborals;
		this.posicions = posicions;
	}

	@Id
	@Column(name = "ID_TIPO_JORNADA", unique = true, nullable = false)
	public long getIdTipoJornada() {
		return this.idTipoJornada;
	}

	public void setIdTipoJornada(long idTipoJornada) {
		this.idTipoJornada = idTipoJornada;
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

	@Column(name = "PONDERACION", nullable = false, precision = 8, scale = 8)
	public float getPonderacion() {
		return this.ponderacion;
	}

	public void setPonderacion(float ponderacion) {
		this.ponderacion = ponderacion;
	}

	@Column(name = "ESTATUS_REGISTRO")
	public boolean isEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoJornada")
	public Set<BitacoraPosicion> getBitacoraPosicions() {
		return this.bitacoraPosicions;
	}

	public void setBitacoraPosicions(Set<BitacoraPosicion> bitacoraPosicions) {
		this.bitacoraPosicions = bitacoraPosicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoJornada")
	public Set<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoJornada")
	public Set<ExperienciaLaboral> getExperienciaLaborals() {
		return this.experienciaLaborals;
	}

	public void setExperienciaLaborals(
			Set<ExperienciaLaboral> experienciaLaborals) {
		this.experienciaLaborals = experienciaLaborals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoJornada")
	public Set<Posicion> getPosicions() {
		return this.posicions;
	}

	public void setPosicions(Set<Posicion> posicions) {
		this.posicions = posicions;
	}

}
