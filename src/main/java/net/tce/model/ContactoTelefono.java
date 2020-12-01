package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * ContactoTelefono generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "CONTACTO_TELEFONO")
@DynamicUpdate
public class ContactoTelefono implements java.io.Serializable {

	private long idContacto;
	private Carrier carrier;
	private Contacto contacto;
	private String prefijo;
	private String codigoPais;
	private String codigoArea;
	private String numero;
	private String adicional;

	public ContactoTelefono() {
	}

	public ContactoTelefono(Contacto contacto) {
		this.contacto = contacto;
	}

	public ContactoTelefono(Carrier carrier, Contacto contacto, String prefijo,
			String codigoPais, String codigoArea, String numero,
			String adicional) {
		this.carrier = carrier;
		this.contacto = contacto;
		this.prefijo = prefijo;
		this.codigoPais = codigoPais;
		this.codigoArea = codigoArea;
		this.numero = numero;
		this.adicional = adicional;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "contacto"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID_CONTACTO", unique = true, nullable = false)
	public long getIdContacto() {
		return this.idContacto;
	}

	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CARRIER")
	public Carrier getCarrier() {
		return this.carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Contacto getContacto() {
		return this.contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	@Column(name = "PREFIJO", length = 3)
	public String getPrefijo() {
		return this.prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	@Column(name = "CODIGO_PAIS", length = 3)
	public String getCodigoPais() {
		return this.codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	@Column(name = "CODIGO_AREA", length = 3)
	public String getCodigoArea() {
		return this.codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	@Column(name = "NUMERO", length = 10)
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "ADICIONAL", length = 10)
	public String getAdicional() {
		return this.adicional;
	}

	public void setAdicional(String adicional) {
		this.adicional = adicional;
	}

}
