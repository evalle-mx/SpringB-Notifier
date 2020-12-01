package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AreaEquivalente generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "AREA_EQUIVALENTE")
public class AreaEquivalente implements java.io.Serializable {

	private long idAreaEquivalente;
	private Area area;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;

	public AreaEquivalente() {
	}

	public AreaEquivalente(long idAreaEquivalente, Area area, String descripcion) {
		this.idAreaEquivalente = idAreaEquivalente;
		this.area = area;
		this.descripcion = descripcion;
	}

	public AreaEquivalente(long idAreaEquivalente, Area area,
			String descripcion, String significado, boolean estatusRegistro) {
		this.idAreaEquivalente = idAreaEquivalente;
		this.area = area;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
	}

	@Id
	@Column(name = "ID_AREA_EQUIVALENTE", unique = true, nullable = false)
	public long getIdAreaEquivalente() {
		return this.idAreaEquivalente;
	}

	public void setIdAreaEquivalente(long idAreaEquivalente) {
		this.idAreaEquivalente = idAreaEquivalente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AREA", nullable = false)
	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
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

}