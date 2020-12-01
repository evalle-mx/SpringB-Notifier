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
 * NivelJerarquico generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "NIVEL_JERARQUICO")
public class NivelJerarquico implements java.io.Serializable {

	private long idNivelJerarquico;
	private String descripcion;
	private String significado;
	private Short ponderacion;
	private boolean estatusRegistro;
	private Set<ExperienciaLaboral> experienciaLaborals = new HashSet<ExperienciaLaboral>(
			0);
	private Set<Posicion> posicions = new HashSet<Posicion>(0);

	public NivelJerarquico() {
	}

	public NivelJerarquico(long idNivelJerarquico, String descripcion,
			boolean estatusRegistro) {
		this.idNivelJerarquico = idNivelJerarquico;
		this.descripcion = descripcion;
		this.estatusRegistro = estatusRegistro;
	}

	public NivelJerarquico(long idNivelJerarquico, String descripcion,
			String significado, Short ponderacion, boolean estatusRegistro,
			Set<ExperienciaLaboral> experienciaLaborals, Set<Posicion> posicions) {
		this.idNivelJerarquico = idNivelJerarquico;
		this.descripcion = descripcion;
		this.significado = significado;
		this.ponderacion = ponderacion;
		this.estatusRegistro = estatusRegistro;
		this.experienciaLaborals = experienciaLaborals;
		this.posicions = posicions;
	}

	@Id
	@Column(name = "ID_NIVEL_JERARQUICO", unique = true, nullable = false)
	public long getIdNivelJerarquico() {
		return this.idNivelJerarquico;
	}

	public void setIdNivelJerarquico(long idNivelJerarquico) {
		this.idNivelJerarquico = idNivelJerarquico;
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

	@Column(name = "PONDERACION")
	public Short getPonderacion() {
		return this.ponderacion;
	}

	public void setPonderacion(Short ponderacion) {
		this.ponderacion = ponderacion;
	}

	@Column(name = "ESTATUS_REGISTRO")
	public boolean isEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivelJerarquico")
	public Set<ExperienciaLaboral> getExperienciaLaborals() {
		return this.experienciaLaborals;
	}

	public void setExperienciaLaborals(
			Set<ExperienciaLaboral> experienciaLaborals) {
		this.experienciaLaborals = experienciaLaborals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "nivelJerarquico")
	public Set<Posicion> getPosicions() {
		return this.posicions;
	}

	public void setPosicions(Set<Posicion> posicions) {
		this.posicions = posicions;
	}

}
