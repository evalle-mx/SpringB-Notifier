package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Concepto generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "CONCEPTO")
public class Concepto implements java.io.Serializable {

	private long idConcepto;
	private Seccion seccion;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<PoliticaValor> politicaValors = new HashSet<PoliticaValor>(0);

	public Concepto() {
	}

	public Concepto(long idConcepto, Seccion seccion, String descripcion) {
		this.idConcepto = idConcepto;
		this.seccion = seccion;
		this.descripcion = descripcion;
	}

	public Concepto(long idConcepto, Seccion seccion, String descripcion,
			String significado, boolean estatusRegistro,
			Set<PoliticaValor> politicaValors) {
		this.idConcepto = idConcepto;
		this.seccion = seccion;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.politicaValors = politicaValors;
	}

	@Id
	@Column(name = "ID_CONCEPTO", unique = true, nullable = false)
	public long getIdConcepto() {
		return this.idConcepto;
	}

	public void setIdConcepto(long idConcepto) {
		this.idConcepto = idConcepto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SECCION", nullable = false)
	public Seccion getSeccion() {
		return this.seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "concepto")
	public Set<PoliticaValor> getPoliticaValors() {
		return this.politicaValors;
	}

	public void setPoliticaValors(Set<PoliticaValor> politicaValors) {
		this.politicaValors = politicaValors;
	}

}
