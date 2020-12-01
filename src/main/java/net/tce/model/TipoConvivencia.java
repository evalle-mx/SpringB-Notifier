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
 * TipoConvivencia generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "TIPO_CONVIVENCIA")
public class TipoConvivencia implements java.io.Serializable {

	private long idTipoConvivencia;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<Persona> personas = new HashSet<Persona>(0);

	public TipoConvivencia() {
	}

	public TipoConvivencia(long idTipoConvivencia, String descripcion,
			boolean estatusRegistro) {
		this.idTipoConvivencia = idTipoConvivencia;
		this.descripcion = descripcion;
		this.estatusRegistro = estatusRegistro;
	}

	public TipoConvivencia(long idTipoConvivencia, String descripcion,
			String significado, boolean estatusRegistro, Set<Persona> personas) {
		this.idTipoConvivencia = idTipoConvivencia;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.personas = personas;
	}

	@Id
	@Column(name = "ID_TIPO_CONVIVENCIA", unique = true, nullable = false)
	public long getIdTipoConvivencia() {
		return this.idTipoConvivencia;
	}

	public void setIdTipoConvivencia(long idTipoConvivencia) {
		this.idTipoConvivencia = idTipoConvivencia;
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

	@Column(name = "ESTATUS_REGISTRO")
	public boolean isEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoConvivencia")
	public Set<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}

}