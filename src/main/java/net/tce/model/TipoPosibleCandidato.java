package net.tce.model;

// Generated May 16, 2018 7:05:17 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TipoPosibleCandidato generated by hbm2java
 */
@Entity
@Table(name = "tipo_posible_candidato")
public class TipoPosibleCandidato implements java.io.Serializable {

	private long idTipoPosibleCandidato;
	private String descripcion;
	private Boolean estatusRegistro;
	private Set<PosibleCandidato> posibleCandidatos = new HashSet<PosibleCandidato>(
			0);

	public TipoPosibleCandidato() {
	}

	public TipoPosibleCandidato(long idTipoPosibleCandidato) {
		this.idTipoPosibleCandidato = idTipoPosibleCandidato;
	}

	public TipoPosibleCandidato(long idTipoPosibleCandidato,
			String descripcion, Boolean estatusRegistro,
			Set<PosibleCandidato> posibleCandidatos) {
		this.idTipoPosibleCandidato = idTipoPosibleCandidato;
		this.descripcion = descripcion;
		this.estatusRegistro = estatusRegistro;
		this.posibleCandidatos = posibleCandidatos;
	}

	@Id
	@Column(name = "id_tipo_posible_candidato", unique = true, nullable = false)
	public long getIdTipoPosibleCandidato() {
		return this.idTipoPosibleCandidato;
	}

	public void setIdTipoPosibleCandidato(long idTipoPosibleCandidato) {
		this.idTipoPosibleCandidato = idTipoPosibleCandidato;
	}

	@Column(name = "descripcion", length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "estatus_registro", nullable = false)
	public Boolean getEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(Boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoPosibleCandidato")
	public Set<PosibleCandidato> getPosibleCandidatos() {
		return this.posibleCandidatos;
	}

	public void setPosibleCandidatos(Set<PosibleCandidato> posibleCandidatos) {
		this.posibleCandidatos = posibleCandidatos;
	}

}
