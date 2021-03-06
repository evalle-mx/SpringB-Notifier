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
 * TipoDomicilio generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "TIPO_DOMICILIO")
public class TipoDomicilio implements java.io.Serializable {

	private long idTipoDomicilio;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<Domicilio> domicilios = new HashSet<Domicilio>(0);

	public TipoDomicilio() {
	}

	public TipoDomicilio(long idTipoDomicilio, String descripcion,
			boolean estatusRegistro) {
		this.idTipoDomicilio = idTipoDomicilio;
		this.descripcion = descripcion;
		this.estatusRegistro = estatusRegistro;
	}

	public TipoDomicilio(long idTipoDomicilio, String descripcion,
			String significado, boolean estatusRegistro,
			Set<Domicilio> domicilios) {
		this.idTipoDomicilio = idTipoDomicilio;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.domicilios = domicilios;
	}

	@Id
	@Column(name = "ID_TIPO_DOMICILIO", unique = true, nullable = false)
	public long getIdTipoDomicilio() {
		return this.idTipoDomicilio;
	}

	public void setIdTipoDomicilio(long idTipoDomicilio) {
		this.idTipoDomicilio = idTipoDomicilio;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDomicilio")
	public Set<Domicilio> getDomicilios() {
		return this.domicilios;
	}

	public void setDomicilios(Set<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}

}
