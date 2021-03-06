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
 * PropositoPolitica generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "PROPOSITO_POLITICA")
public class PropositoPolitica implements java.io.Serializable {

	private long idPropositoPolitica;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<Politica> politicas = new HashSet<Politica>(0);

	public PropositoPolitica() {
	}

	public PropositoPolitica(long idPropositoPolitica, String descripcion,
			boolean estatusRegistro) {
		this.idPropositoPolitica = idPropositoPolitica;
		this.descripcion = descripcion;
		this.estatusRegistro = estatusRegistro;
	}

	public PropositoPolitica(long idPropositoPolitica, String descripcion,
			String significado, boolean estatusRegistro, Set<Politica> politicas) {
		this.idPropositoPolitica = idPropositoPolitica;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.politicas = politicas;
	}

	@Id
	@Column(name = "ID_PROPOSITO_POLITICA", unique = true, nullable = false)
	public long getIdPropositoPolitica() {
		return this.idPropositoPolitica;
	}

	public void setIdPropositoPolitica(long idPropositoPolitica) {
		this.idPropositoPolitica = idPropositoPolitica;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "propositoPolitica")
	public Set<Politica> getPoliticas() {
		return this.politicas;
	}

	public void setPoliticas(Set<Politica> politicas) {
		this.politicas = politicas;
	}

}
