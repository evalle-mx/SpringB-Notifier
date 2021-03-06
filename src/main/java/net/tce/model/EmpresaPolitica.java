package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * EmpresaPolitica generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "EMPRESA_POLITICA", uniqueConstraints = @UniqueConstraint(columnNames = "id_empresa"))
public class EmpresaPolitica implements java.io.Serializable {

	private long idEmpresaPolitica;
	private Empresa empresa;
	private GradoAcademico gradoAcademicoByIdGradoAcademicoMax;
	private GradoAcademico gradoAcademicoByIdGradoAcademicoMin;
	private EstatusEscolar estatusEscolarByIdEstatusEscolarMin;
	private EstatusEscolar estatusEscolarByIdEstatusEscolarMax;
	private Integer edadMin;
	private Integer edadMax;
	private Integer experienciaMin;
	private Integer experienciaMax;

	public EmpresaPolitica() {
	}

	public EmpresaPolitica(long idEmpresaPolitica, Empresa empresa) {
		this.idEmpresaPolitica = idEmpresaPolitica;
		this.empresa = empresa;
	}

	public EmpresaPolitica(long idEmpresaPolitica, Empresa empresa,
			GradoAcademico gradoAcademicoByIdGradoAcademicoMax,
			GradoAcademico gradoAcademicoByIdGradoAcademicoMin,
			EstatusEscolar estatusEscolarByIdEstatusEscolarMin,
			EstatusEscolar estatusEscolarByIdEstatusEscolarMax,
			Integer edadMin, Integer edadMax, Integer experienciaMin,
			Integer experienciaMax) {
		this.idEmpresaPolitica = idEmpresaPolitica;
		this.empresa = empresa;
		this.gradoAcademicoByIdGradoAcademicoMax = gradoAcademicoByIdGradoAcademicoMax;
		this.gradoAcademicoByIdGradoAcademicoMin = gradoAcademicoByIdGradoAcademicoMin;
		this.estatusEscolarByIdEstatusEscolarMin = estatusEscolarByIdEstatusEscolarMin;
		this.estatusEscolarByIdEstatusEscolarMax = estatusEscolarByIdEstatusEscolarMax;
		this.edadMin = edadMin;
		this.edadMax = edadMax;
		this.experienciaMin = experienciaMin;
		this.experienciaMax = experienciaMax;
	}

	@Id
	@Column(name = "ID_EMPRESA_POLITICA", unique = true, nullable = false)
	public long getIdEmpresaPolitica() {
		return this.idEmpresaPolitica;
	}

	public void setIdEmpresaPolitica(long idEmpresaPolitica) {
		this.idEmpresaPolitica = idEmpresaPolitica;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPRESA", unique = true, nullable = false)
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRADO_ACADEMICO_MAX")
	public GradoAcademico getGradoAcademicoByIdGradoAcademicoMax() {
		return this.gradoAcademicoByIdGradoAcademicoMax;
	}

	public void setGradoAcademicoByIdGradoAcademicoMax(
			GradoAcademico gradoAcademicoByIdGradoAcademicoMax) {
		this.gradoAcademicoByIdGradoAcademicoMax = gradoAcademicoByIdGradoAcademicoMax;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRADO_ACADEMICO_MIN")
	public GradoAcademico getGradoAcademicoByIdGradoAcademicoMin() {
		return this.gradoAcademicoByIdGradoAcademicoMin;
	}

	public void setGradoAcademicoByIdGradoAcademicoMin(
			GradoAcademico gradoAcademicoByIdGradoAcademicoMin) {
		this.gradoAcademicoByIdGradoAcademicoMin = gradoAcademicoByIdGradoAcademicoMin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTATUS_ESCOLAR_MIN")
	public EstatusEscolar getEstatusEscolarByIdEstatusEscolarMin() {
		return this.estatusEscolarByIdEstatusEscolarMin;
	}

	public void setEstatusEscolarByIdEstatusEscolarMin(
			EstatusEscolar estatusEscolarByIdEstatusEscolarMin) {
		this.estatusEscolarByIdEstatusEscolarMin = estatusEscolarByIdEstatusEscolarMin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTATUS_ESCOLAR_MAX")
	public EstatusEscolar getEstatusEscolarByIdEstatusEscolarMax() {
		return this.estatusEscolarByIdEstatusEscolarMax;
	}

	public void setEstatusEscolarByIdEstatusEscolarMax(
			EstatusEscolar estatusEscolarByIdEstatusEscolarMax) {
		this.estatusEscolarByIdEstatusEscolarMax = estatusEscolarByIdEstatusEscolarMax;
	}

	@Column(name = "EDAD_MIN")
	public Integer getEdadMin() {
		return this.edadMin;
	}

	public void setEdadMin(Integer edadMin) {
		this.edadMin = edadMin;
	}

	@Column(name = "EDAD_MAX")
	public Integer getEdadMax() {
		return this.edadMax;
	}

	public void setEdadMax(Integer edadMax) {
		this.edadMax = edadMax;
	}

	@Column(name = "EXPERIENCIA_MIN")
	public Integer getExperienciaMin() {
		return this.experienciaMin;
	}

	public void setExperienciaMin(Integer experienciaMin) {
		this.experienciaMin = experienciaMin;
	}

	@Column(name = "EXPERIENCIA_MAX")
	public Integer getExperienciaMax() {
		return this.experienciaMax;
	}

	public void setExperienciaMax(Integer experienciaMax) {
		this.experienciaMax = experienciaMax;
	}

}
