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
 * BitacoraPerfilTextoNgram generated by hbm2java
 */
@Entity
@Table(name = "bitacora_perfil_texto_ngram")
@DynamicUpdate
public class BitacoraPerfilTextoNgram implements java.io.Serializable {

	private long idBitacoraPerfilTextoNgram;
	private BitacoraPosicion bitacoraPosicion;
	private String texto;
	private Short ponderacion;
	private long idPerfilTextoNgram;

	public BitacoraPerfilTextoNgram() {
	}

	public BitacoraPerfilTextoNgram(long idBitacoraPerfilTextoNgram,
			long idPerfilTextoNgram) {
		this.idBitacoraPerfilTextoNgram = idBitacoraPerfilTextoNgram;
		this.idPerfilTextoNgram = idPerfilTextoNgram;
	}

	public BitacoraPerfilTextoNgram(long idBitacoraPerfilTextoNgram,
			BitacoraPosicion bitacoraPosicion, String texto, Short ponderacion,
			long idPerfilTextoNgram) {
		this.idBitacoraPerfilTextoNgram = idBitacoraPerfilTextoNgram;
		this.bitacoraPosicion = bitacoraPosicion;
		this.texto = texto;
		this.ponderacion = ponderacion;
		this.idPerfilTextoNgram = idPerfilTextoNgram;
	}


	@Id
	@Column(name = "id_bitacora_perfil_texto_ngram", unique = true, nullable = false)
	@SequenceGenerator(name="seq_btc_perfil_texto_ngram", sequenceName=Constante.SECUENCIA_BD_BTC_PERFIL_TEXTO_NGRAM, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_btc_perfil_texto_ngram")
	public long getIdBitacoraPerfilTextoNgram() {
		return this.idBitacoraPerfilTextoNgram;
	}

	public void setIdBitacoraPerfilTextoNgram(long idBitacoraPerfilTextoNgram) {
		this.idBitacoraPerfilTextoNgram = idBitacoraPerfilTextoNgram;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bitacora_posicion")
	public BitacoraPosicion getBitacoraPosicion() {
		return this.bitacoraPosicion;
	}

	public void setBitacoraPosicion(BitacoraPosicion bitacoraPosicion) {
		this.bitacoraPosicion = bitacoraPosicion;
	}

	@Column(name = "texto", length = 2500)
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Column(name = "ponderacion")
	public Short getPonderacion() {
		return this.ponderacion;
	}

	public void setPonderacion(Short ponderacion) {
		this.ponderacion = ponderacion;
	}
	
	@Column(name = "id_perfil_texto_ngram", nullable = false)
	public long getIdPerfilTextoNgram() {
		return this.idPerfilTextoNgram;
	}

	public void setIdPerfilTextoNgram(long idPerfilTextoNgram) {
		this.idPerfilTextoNgram = idPerfilTextoNgram;
	}
}