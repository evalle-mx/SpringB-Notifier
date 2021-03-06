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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import net.tce.util.Constante;

/**
 * Habilidad generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "HABILIDAD")
@DynamicUpdate
public class Habilidad implements java.io.Serializable {

	private long idHabilidad;
	private Persona persona;
	private Dominio dominio;
	private String descripcion;

	public Habilidad() {
	}

	public Habilidad(long idHabilidad, String descripcion) {
		this.idHabilidad = idHabilidad;
		this.descripcion = descripcion;
	}

	public Habilidad(long idHabilidad, Persona persona, Dominio dominio,
			String descripcion) {
		this.idHabilidad = idHabilidad;
		this.persona = persona;
		this.dominio = dominio;
		this.descripcion = descripcion;
	}

	@Id
	@SequenceGenerator(name="seq_habilidad", sequenceName=Constante.SECUENCIA_BD_HABILIDAD, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_habilidad")
	@Column(name = "ID_HABILIDAD", unique = true, nullable = false)
	public long getIdHabilidad() {
		return this.idHabilidad;
	}

	public void setIdHabilidad(long idHabilidad) {
		this.idHabilidad = idHabilidad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERSONA")
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOMINIO")
	public Dominio getDominio() {
		return this.dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	@Column(name = "DESCRIPCION", length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
