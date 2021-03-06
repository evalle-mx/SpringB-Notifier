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

import net.tce.util.Constante;

/**
 * AvisoCandidato generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "AVISO_CANDIDATO")
public class AvisoCandidato implements java.io.Serializable {

	private long idAvisoCandidato;
	private Candidato candidato;
	private Aviso aviso;

	public AvisoCandidato() {
	}

	public AvisoCandidato(long idAvisoCandidato) {
		this.idAvisoCandidato = idAvisoCandidato;
	}

	public AvisoCandidato(long idAvisoCandidato, Candidato candidato,
			Aviso aviso) {
		this.idAvisoCandidato = idAvisoCandidato;
		this.candidato = candidato;
		this.aviso = aviso;
	}

	@Id
	@SequenceGenerator(name="seq_aviso_cand", sequenceName=Constante.SECUENCIA_BD_SEQ_AVISO_CAND, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_aviso_cand")
	@Column(name = "ID_AVISO_CANDIDATO", unique = true, nullable = false)
	public long getIdAvisoCandidato() {
		return this.idAvisoCandidato;
	}

	public void setIdAvisoCandidato(long idAvisoCandidato) {
		this.idAvisoCandidato = idAvisoCandidato;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CANDIDATO")
	public Candidato getCandidato() {
		return this.candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AVISO")
	public Aviso getAviso() {
		return this.aviso;
	}

	public void setAviso(Aviso aviso) {
		this.aviso = aviso;
	}

}
