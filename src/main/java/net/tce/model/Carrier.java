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
 * Carrier generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "CARRIER")
public class Carrier implements java.io.Serializable {

	private long idCarrier;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<ContactoTelefono> contactoTelefonos = new HashSet<ContactoTelefono>(
			0);

	public Carrier() {
	}

	public Carrier(long idCarrier, boolean estatusRegistro) {
		this.idCarrier = idCarrier;
		this.estatusRegistro = estatusRegistro;
	}

	public Carrier(long idCarrier, String descripcion, String significado,
			boolean estatusRegistro, Set<ContactoTelefono> contactoTelefonos) {
		this.idCarrier = idCarrier;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.contactoTelefonos = contactoTelefonos;
	}

	@Id
	@Column(name = "ID_CARRIER", unique = true, nullable = false)
	public long getIdCarrier() {
		return this.idCarrier;
	}

	public void setIdCarrier(long idCarrier) {
		this.idCarrier = idCarrier;
	}

	@Column(name = "DESCRIPCION", length = 100)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "carrier")
	public Set<ContactoTelefono> getContactoTelefonos() {
		return this.contactoTelefonos;
	}

	public void setContactoTelefonos(Set<ContactoTelefono> contactoTelefonos) {
		this.contactoTelefonos = contactoTelefonos;
	}

}