package net.tce.model;

// Generated Dec 20, 2017 6:46:39 PM by Hibernate Tools 3.4.0.CR1

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
 * BitacoraCompetenciaPerfil generated by hbm2java
 */
@Entity
@Table(name = "bitacora_competencia_perfil")
@DynamicUpdate
public class BitacoraCompetenciaPerfil implements java.io.Serializable {

	private long idBitacoraCompetenciaPerfil;
	private Competencia competencia;
	private BitacoraPosicion bitacoraPosicion;
	private Boolean seleccionado;
	private long idCompetenciaPerfil;

	public BitacoraCompetenciaPerfil() {
	}

	public BitacoraCompetenciaPerfil(long idBitacoraCompetenciaPerfil,
			BitacoraPosicion bitacoraPosicion, long idCompetenciaPerfil) {
		this.idBitacoraCompetenciaPerfil = idBitacoraCompetenciaPerfil;
		this.bitacoraPosicion = bitacoraPosicion;
		this.idCompetenciaPerfil = idCompetenciaPerfil;
	}

	public BitacoraCompetenciaPerfil(long idBitacoraCompetenciaPerfil,
			Competencia competencia, BitacoraPosicion bitacoraPosicion,
			Boolean seleccionado, long idCompetenciaPerfil) {
		this.idBitacoraCompetenciaPerfil = idBitacoraCompetenciaPerfil;
		this.competencia = competencia;
		this.bitacoraPosicion = bitacoraPosicion;
		this.seleccionado = seleccionado;
		this.idCompetenciaPerfil = idCompetenciaPerfil;
	}

	@Id
	@SequenceGenerator(name="seq_btc_competencia_perfil", sequenceName=Constante.SECUENCIA_BD_BTC_COMPETENCIA_PERFIL, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_btc_competencia_perfil")
	@Column(name = "id_bitacora_competencia_perfil", unique = true, nullable = false)
	public long getIdBitacoraCompetenciaPerfil() {
		return this.idBitacoraCompetenciaPerfil;
	}

	public void setIdBitacoraCompetenciaPerfil(long idBitacoraCompetenciaPerfil) {
		this.idBitacoraCompetenciaPerfil = idBitacoraCompetenciaPerfil;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_competencia")
	public Competencia getCompetencia() {
		return this.competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bitacora_posicion")
	public BitacoraPosicion getBitacoraPosicion() {
		return this.bitacoraPosicion;
	}

	public void setBitacoraPosicion(BitacoraPosicion bitacoraPosicion) {
		this.bitacoraPosicion = bitacoraPosicion;
	}

	@Column(name = "seleccionado")
	public boolean isSeleccionado() {
		return this.seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	@Column(name = "id_competencia_perfil", nullable = false)
	public long getIdCompetenciaPerfil() {
		return this.idCompetenciaPerfil;
	}

	public void setIdCompetenciaPerfil(long idCompetenciaPerfil) {
		this.idCompetenciaPerfil = idCompetenciaPerfil;
	}
}
