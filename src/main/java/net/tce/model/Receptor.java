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
 * Receptor generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "RECEPTOR")
public class Receptor implements java.io.Serializable {

	private long idReceptor;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<TipoEventoReceptor> tipoEventoReceptors = new HashSet<TipoEventoReceptor>(
			0);

	public Receptor() {
	}

	public Receptor(long idReceptor, String descripcion, boolean estatusRegistro) {
		this.idReceptor = idReceptor;
		this.descripcion = descripcion;
		this.estatusRegistro = estatusRegistro;
	}

	public Receptor(long idReceptor, String descripcion, String significado,
			boolean estatusRegistro, Set<TipoEventoReceptor> tipoEventoReceptors) {
		this.idReceptor = idReceptor;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.tipoEventoReceptors = tipoEventoReceptors;
	}

	@Id
	@Column(name = "ID_RECEPTOR", unique = true, nullable = false)
	public long getIdReceptor() {
		return this.idReceptor;
	}

	public void setIdReceptor(long idReceptor) {
		this.idReceptor = idReceptor;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receptor")
	public Set<TipoEventoReceptor> getTipoEventoReceptors() {
		return this.tipoEventoReceptors;
	}

	public void setTipoEventoReceptors(
			Set<TipoEventoReceptor> tipoEventoReceptors) {
		this.tipoEventoReceptors = tipoEventoReceptors;
	}

}