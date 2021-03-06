package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;

import net.tce.util.Constante;

/**
 * Contacto generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "CONTACTO")
@DynamicUpdate
public class Contacto implements java.io.Serializable {

	private long idContacto;
	private Empresa empresa;
	private TipoContacto tipoContacto;
	private Persona persona;
	private String contacto;
	private boolean estaVerificado;
	private ContactoTelefono contactoTelefono;

	public Contacto() {
	}

	public Contacto(long idContacto) {
		this.idContacto = idContacto;
	}

	public Contacto(long idContacto, Empresa empresa,
			TipoContacto tipoContacto, Persona persona, String contacto,
			boolean estaVerificado, ContactoTelefono contactoTelefono) {
		this.idContacto = idContacto;
		this.empresa = empresa;
		this.tipoContacto = tipoContacto;
		this.persona = persona;
		this.contacto = contacto;
		this.estaVerificado = estaVerificado;
		this.contactoTelefono = contactoTelefono;
	}

	@Id
	@SequenceGenerator(name="seq_Contact", sequenceName=Constante.SECUENCIA_BD_CONTACT, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_Contact")
	@Column(name = "ID_CONTACTO", unique = true, nullable = false)
	public long getIdContacto() {
		return this.idContacto;
	}

	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPRESA")
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_CONTACTO")
	public TipoContacto getTipoContacto() {
		return this.tipoContacto;
	}

	public void setTipoContacto(TipoContacto tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERSONA")
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Column(name = "CONTACTO", length = 50)
	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	@Column(name = "ESTA_VERIFICADO")
	public boolean isEstaVerificado() {
		return this.estaVerificado;
	}

	public void setEstaVerificado(boolean estaVerificado) {
		this.estaVerificado = estaVerificado;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "contacto")
	@Cascade({CascadeType.DELETE})
	public ContactoTelefono getContactoTelefono() {
		return this.contactoTelefono;
	}

	public void setContactoTelefono(ContactoTelefono contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}

}
