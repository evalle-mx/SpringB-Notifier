package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.tce.util.Constante;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;

/**
 * PersonaPerfil generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "PERSONA_PERFIL", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id_perfil", "id_persona" }))
@DynamicUpdate
public class PersonaPerfil implements java.io.Serializable {

	private long idPersonaPerfil;
	private Perfil perfil;
	private Persona persona;
	private BigDecimal iap;
	private BigDecimal iapAcademico;
	private BigDecimal iapLaboral;
	private BigDecimal iapHabilidad;
	private BigDecimal iapBruto;
	private BigDecimal iapAcademicoBruto;
	private BigDecimal iapLaboralBruto;
	private BigDecimal iapHabilidadBruto;
	private Date fechaCreacion;
	private Date fechaModificacion;

	public PersonaPerfil() {
	}

	public PersonaPerfil(long idPersonaPerfil, Perfil perfil, Persona persona,
			Date fechaCreacion) {
		this.idPersonaPerfil = idPersonaPerfil;
		this.perfil = perfil;
		this.persona = persona;
		this.fechaCreacion = fechaCreacion;
	}

	public PersonaPerfil(long idPersonaPerfil, Perfil perfil, Persona persona,
			BigDecimal iap, BigDecimal iapAcademico, BigDecimal iapLaboral,
			BigDecimal iapHabilidad, BigDecimal iapBruto,
			BigDecimal iapAcademicoBruto, BigDecimal iapLaboralBruto,
			BigDecimal iapHabilidadBruto, Date fechaCreacion,
			Date fechaModificacion) {
		this.idPersonaPerfil = idPersonaPerfil;
		this.perfil = perfil;
		this.persona = persona;
		this.iap = iap;
		this.iapAcademico = iapAcademico;
		this.iapLaboral = iapLaboral;
		this.iapHabilidad = iapHabilidad;
		this.iapBruto = iapBruto;
		this.iapAcademicoBruto = iapAcademicoBruto;
		this.iapLaboralBruto = iapLaboralBruto;
		this.iapHabilidadBruto = iapHabilidadBruto;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
	}

	@Id
	@SequenceGenerator(name="seq_personaPerfil", sequenceName=Constante.SECUENCIA_BD_PERSONA_PERFIL, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_personaPerfil")
	@Column(name = "ID_PERSONA_PERFIL", unique = true, nullable = false)
	public long getIdPersonaPerfil() {
		return this.idPersonaPerfil;
	}

	public void setIdPersonaPerfil(long idPersonaPerfil) {
		this.idPersonaPerfil = idPersonaPerfil;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERFIL", nullable = false)
	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERSONA", nullable = false)
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Column(name = "IAP", precision = 131089, scale = 0)
	public BigDecimal getIap() {
		return this.iap;
	}

	public void setIap(BigDecimal iap) {
		this.iap = iap;
	}

	@Column(name = "IAP_ACADEMICO", precision = 131089, scale = 0)
	public BigDecimal getIapAcademico() {
		return this.iapAcademico;
	}

	public void setIapAcademico(BigDecimal iapAcademico) {
		this.iapAcademico = iapAcademico;
	}

	@Column(name = "IAP_LABORAL", precision = 131089, scale = 0)
	public BigDecimal getIapLaboral() {
		return this.iapLaboral;
	}

	public void setIapLaboral(BigDecimal iapLaboral) {
		this.iapLaboral = iapLaboral;
	}

	@Column(name = "IAP_HABILIDAD", precision = 131089, scale = 0)
	public BigDecimal getIapHabilidad() {
		return this.iapHabilidad;
	}

	public void setIapHabilidad(BigDecimal iapHabilidad) {
		this.iapHabilidad = iapHabilidad;
	}

	@Column(name = "IAP_BRUTO", precision = 131089, scale = 0)
	public BigDecimal getIapBruto() {
		return this.iapBruto;
	}

	public void setIapBruto(BigDecimal iapBruto) {
		this.iapBruto = iapBruto;
	}

	@Column(name = "IAP_ACADEMICO_BRUTO", precision = 131089, scale = 0)
	public BigDecimal getIapAcademicoBruto() {
		return this.iapAcademicoBruto;
	}

	public void setIapAcademicoBruto(BigDecimal iapAcademicoBruto) {
		this.iapAcademicoBruto = iapAcademicoBruto;
	}

	@Column(name = "IAP_LABORAL_BRUTO", precision = 131089, scale = 0)
	public BigDecimal getIapLaboralBruto() {
		return this.iapLaboralBruto;
	}

	public void setIapLaboralBruto(BigDecimal iapLaboralBruto) {
		this.iapLaboralBruto = iapLaboralBruto;
	}

	@Column(name = "IAP_HABILIDAD_BRUTO", precision = 131089, scale = 0)
	public BigDecimal getIapHabilidadBruto() {
		return this.iapHabilidadBruto;
	}

	public void setIapHabilidadBruto(BigDecimal iapHabilidadBruto) {
		this.iapHabilidadBruto = iapHabilidadBruto;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CREACION", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_MODIFICACION", length = 29)
	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

}
