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
 * TipoParametro generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "TIPO_PARAMETRO")
public class TipoParametro implements java.io.Serializable {

	private long idTipoParametro;
	private String descripcion;
	private String significado;
	private boolean estatusRegistro;
	private Set<EmpresaParametro> empresaParametros = new HashSet<EmpresaParametro>(
			0);

	public TipoParametro() {
	}

	public TipoParametro(long idTipoParametro, String descripcion,
			boolean estatusRegistro) {
		this.idTipoParametro = idTipoParametro;
		this.descripcion = descripcion;
		this.estatusRegistro = estatusRegistro;
	}

	public TipoParametro(long idTipoParametro, String descripcion,
			String significado, boolean estatusRegistro,
			Set<EmpresaParametro> empresaParametros) {
		this.idTipoParametro = idTipoParametro;
		this.descripcion = descripcion;
		this.significado = significado;
		this.estatusRegistro = estatusRegistro;
		this.empresaParametros = empresaParametros;
	}

	@Id
	@Column(name = "ID_TIPO_PARAMETRO", unique = true, nullable = false)
	public long getIdTipoParametro() {
		return this.idTipoParametro;
	}

	public void setIdTipoParametro(long idTipoParametro) {
		this.idTipoParametro = idTipoParametro;
	}

	@Column(name = "DESCRIPCION", nullable = false, length = 250)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoParametro")
	public Set<EmpresaParametro> getEmpresaParametros() {
		return this.empresaParametros;
	}

	public void setEmpresaParametros(Set<EmpresaParametro> empresaParametros) {
		this.empresaParametros = empresaParametros;
	}

}
