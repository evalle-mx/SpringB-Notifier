package net.tce.model;

// Generated Jan 31, 2018 5:59:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TipoOperacionBitacora generated by hbm2java
 */
@Entity
@Table(name = "tipo_operacion_bitacora")
public class TipoOperacionBitacora implements java.io.Serializable {

	private long idTipoOperacionBitacora;
	private String descripcion;
	private String nombre;
	private boolean estatusRegistro;
	private Set<BitacoraPosicion> bitacoraPosicions = new HashSet<BitacoraPosicion>(
			0);

	public TipoOperacionBitacora() {
	}

	public TipoOperacionBitacora(long idTipoOperacionBitacora, String nombre,
			boolean estatusRegistro) {
		this.idTipoOperacionBitacora = idTipoOperacionBitacora;
		this.nombre = nombre;
		this.estatusRegistro = estatusRegistro;
	}

	public TipoOperacionBitacora(long idTipoOperacionBitacora,
			String descripcion, String nombre, boolean estatusRegistro,
			Set<BitacoraPosicion> bitacoraPosicions) {
		this.idTipoOperacionBitacora = idTipoOperacionBitacora;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.estatusRegistro = estatusRegistro;
		this.bitacoraPosicions = bitacoraPosicions;
	}

	@Id
	@Column(name = "id_tipo_operacion_bitacora", unique = true, nullable = false)
	public long getIdTipoOperacionBitacora() {
		return this.idTipoOperacionBitacora;
	}

	public void setIdTipoOperacionBitacora(long idTipoOperacionBitacora) {
		this.idTipoOperacionBitacora = idTipoOperacionBitacora;
	}

	@Column(name = "descripcion", length = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "estatus_registro", nullable = false)
	public boolean isEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoOperacionBitacora")
	public Set<BitacoraPosicion> getBitacoraPosicions() {
		return this.bitacoraPosicions;
	}

	public void setBitacoraPosicions(Set<BitacoraPosicion> bitacoraPosicions) {
		this.bitacoraPosicions = bitacoraPosicions;
	}

}
