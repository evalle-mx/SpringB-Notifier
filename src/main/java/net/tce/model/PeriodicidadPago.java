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
 * PeriodicidadPago generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "PERIODICIDAD_PAGO")
public class PeriodicidadPago implements java.io.Serializable {

	private long idPeriodicidadPago;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<Posicion> posicions = new HashSet<Posicion>(0);
	private Set<BitacoraPosicion> bitacoraPosicions = new HashSet<BitacoraPosicion>(
			0);

	public PeriodicidadPago() {
	}

	public PeriodicidadPago(long idPeriodicidadPago) {
		this.idPeriodicidadPago = idPeriodicidadPago;
	}

	public PeriodicidadPago(long idPeriodicidadPago, String descripcion,
			String significado, boolean estatusRegistro,
			Set<Posicion> posicions, Set<BitacoraPosicion> bitacoraPosicions) {
		this.idPeriodicidadPago = idPeriodicidadPago;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.posicions = posicions;
		this.bitacoraPosicions = bitacoraPosicions;
	}

	@Id
	@Column(name = "id_periodicidad_pago", unique = true, nullable = false)
	public long getIdPeriodicidadPago() {
		return this.idPeriodicidadPago;
	}

	public void setIdPeriodicidadPago(long idPeriodicidadPago) {
		this.idPeriodicidadPago = idPeriodicidadPago;
	}

	@Column(name = "descripcion", length = 100)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "periodicidadPago")
	public Set<Posicion> getPosicions() {
		return this.posicions;
	}

	public void setPosicions(Set<Posicion> posicions) {
		this.posicions = posicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "periodicidadPago")
	public Set<BitacoraPosicion> getBitacoraPosicions() {
		return this.bitacoraPosicions;
	}

	public void setBitacoraPosicions(Set<BitacoraPosicion> bitacoraPosicions) {
		this.bitacoraPosicions = bitacoraPosicions;
	}

}
