package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Conf generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "CONF")
public class Conf implements java.io.Serializable {

	private long idConf;
	private TipoConf tipoConf;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Set<EmpresaParametro> empresaParametros = new HashSet<EmpresaParametro>(
			0);
	private Set<EmpresaConf> empresaConfs = new HashSet<EmpresaConf>(0);

	public Conf() {
	}

	public Conf(long idConf, Date fechaCreacion) {
		this.idConf = idConf;
		this.fechaCreacion = fechaCreacion;
	}

	public Conf(long idConf, TipoConf tipoConf, Date fechaCreacion,
			Date fechaModificacion, Set<EmpresaParametro> empresaParametros,
			Set<EmpresaConf> empresaConfs) {
		this.idConf = idConf;
		this.tipoConf = tipoConf;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.empresaParametros = empresaParametros;
		this.empresaConfs = empresaConfs;
	}

	@Id
	@Column(name = "ID_CONF", unique = true, nullable = false)
	public long getIdConf() {
		return this.idConf;
	}

	public void setIdConf(long idConf) {
		this.idConf = idConf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_CONF")
	public TipoConf getTipoConf() {
		return this.tipoConf;
	}

	public void setTipoConf(TipoConf tipoConf) {
		this.tipoConf = tipoConf;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "conf")
	public Set<EmpresaParametro> getEmpresaParametros() {
		return this.empresaParametros;
	}

	public void setEmpresaParametros(Set<EmpresaParametro> empresaParametros) {
		this.empresaParametros = empresaParametros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "conf")
	public Set<EmpresaConf> getEmpresaConfs() {
		return this.empresaConfs;
	}

	public void setEmpresaConfs(Set<EmpresaConf> empresaConfs) {
		this.empresaConfs = empresaConfs;
	}

}