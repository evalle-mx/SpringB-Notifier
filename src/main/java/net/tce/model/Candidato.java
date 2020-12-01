package net.tce.model;

// Generated Mar 27, 2018 3:29:15 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import net.tce.util.Constante;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Candidato generated by hbm2java
 */
@Entity
@Table(name = "candidato")
@DynamicUpdate
public class Candidato implements java.io.Serializable {

	private long idCandidato;
	private Politica politica;
	private Empresa empresa;
	private EstatusOperativo estatusOperativo;
	private EstatusCandidato estatusCandidato;
	private Persona persona;
	private Posicion posicion;
	private Short rangoGeografico;
	private BigDecimal distanciaReal;
	private BigDecimal iap;
	private BigDecimal iapBruto;
	private Short iapCodigo;
	private Short iasCodigo;
	private Short ipgCodigo;
	private BigDecimal demograficoBruto;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Integer rankingArea;
	private Boolean handshake;
	private BigDecimal calificacion;
	private String detalleEstatus;
	private Boolean iasEnCola;
	private Boolean ipgEnCola;
	private Boolean modificado;
	private Boolean confirmado;
	private Short orden;
	private String comentario;
	private Set<TrackingPostulante> trackingPostulantes = new HashSet<TrackingPostulante>(
			0);
	private Set<AvisoCandidato> avisoCandidatos = new HashSet<AvisoCandidato>(0);
	private Set<CandidatoRechazo> candidatoRechazos = new HashSet<CandidatoRechazo>(
			0);

	public Candidato() {
	}

	public Candidato(long idCandidato, EstatusOperativo estatusOperativo,
			EstatusCandidato estatusCandidato, Posicion posicion,
			Date fechaCreacion) {
		this.idCandidato = idCandidato;
		this.estatusOperativo = estatusOperativo;
		this.estatusCandidato = estatusCandidato;
		this.posicion = posicion;
		this.fechaCreacion = fechaCreacion;
	}

	public Candidato(long idCandidato, Politica politica, Empresa empresa,
			EstatusOperativo estatusOperativo,
			EstatusCandidato estatusCandidato, Persona persona,
			Posicion posicion, Short rangoGeografico, BigDecimal distanciaReal,
			BigDecimal iap, BigDecimal iapBruto, Short iapCodigo,
			Short iasCodigo, Short ipgCodigo, BigDecimal demograficoBruto,
			Date fechaCreacion, Date fechaModificacion, Integer rankingArea,
			Boolean handshake, BigDecimal calificacion, String detalleEstatus,
			Boolean iasEnCola, Boolean ipgEnCola, Boolean modificado,
			Boolean confirmado, Short orden, String comentario,
			Set<TrackingPostulante> trackingPostulantes,
			Set<AvisoCandidato> avisoCandidatos,
			Set<CandidatoRechazo> candidatoRechazos) {
		this.idCandidato = idCandidato;
		this.politica = politica;
		this.empresa = empresa;
		this.estatusOperativo = estatusOperativo;
		this.estatusCandidato = estatusCandidato;
		this.persona = persona;
		this.posicion = posicion;
		this.rangoGeografico = rangoGeografico;
		this.distanciaReal = distanciaReal;
		this.iap = iap;
		this.iapBruto = iapBruto;
		this.iapCodigo = iapCodigo;
		this.iasCodigo = iasCodigo;
		this.ipgCodigo = ipgCodigo;
		this.demograficoBruto = demograficoBruto;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.rankingArea = rankingArea;
		this.handshake = handshake;
		this.calificacion = calificacion;
		this.detalleEstatus = detalleEstatus;
		this.iasEnCola = iasEnCola;
		this.ipgEnCola = ipgEnCola;
		this.modificado = modificado;
		this.confirmado = confirmado;
		this.orden = orden;
		this.comentario = comentario;
		this.trackingPostulantes = trackingPostulantes;
		this.avisoCandidatos = avisoCandidatos;
		this.candidatoRechazos = candidatoRechazos;
	}
	
	@Id
	@SequenceGenerator(name="seq_candidato", sequenceName=Constante.SECUENCIA_BD_CANDIDATO, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_candidato")
	@Column(name = "id_candidato", unique = true, nullable = false)
	public long getIdCandidato() {
		return this.idCandidato;
	}

	public void setIdCandidato(long idCandidato) {
		this.idCandidato = idCandidato;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_politica")
	public Politica getPolitica() {
		return this.politica;
	}

	public void setPolitica(Politica politica) {
		this.politica = politica;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa")
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_operativo", nullable = false)
	public EstatusOperativo getEstatusOperativo() {
		return this.estatusOperativo;
	}

	public void setEstatusOperativo(EstatusOperativo estatusOperativo) {
		this.estatusOperativo = estatusOperativo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_candidato", nullable = false)
	public EstatusCandidato getEstatusCandidato() {
		return this.estatusCandidato;
	}

	public void setEstatusCandidato(EstatusCandidato estatusCandidato) {
		this.estatusCandidato = estatusCandidato;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona")
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_posicion", nullable = false)
	public Posicion getPosicion() {
		return this.posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	@Column(name = "rango_geografico")
	public Short getRangoGeografico() {
		return this.rangoGeografico;
	}

	public void setRangoGeografico(Short rangoGeografico) {
		this.rangoGeografico = rangoGeografico;
	}

	@Column(name = "distancia_real", precision = 131089, scale = 0)
	public BigDecimal getDistanciaReal() {
		return this.distanciaReal;
	}

	public void setDistanciaReal(BigDecimal distanciaReal) {
		this.distanciaReal = distanciaReal;
	}

	@Column(name = "iap", precision = 131089, scale = 0)
	public BigDecimal getIap() {
		return this.iap;
	}

	public void setIap(BigDecimal iap) {
		this.iap = iap;
	}

	@Column(name = "iap_bruto", precision = 131089, scale = 0)
	public BigDecimal getIapBruto() {
		return this.iapBruto;
	}

	public void setIapBruto(BigDecimal iapBruto) {
		this.iapBruto = iapBruto;
	}

	@Column(name = "iap_codigo")
	public Short getIapCodigo() {
		return this.iapCodigo;
	}

	public void setIapCodigo(Short iapCodigo) {
		this.iapCodigo = iapCodigo;
	}

	@Column(name = "ias_codigo")
	public Short getIasCodigo() {
		return this.iasCodigo;
	}

	public void setIasCodigo(Short iasCodigo) {
		this.iasCodigo = iasCodigo;
	}

	@Column(name = "ipg_codigo")
	public Short getIpgCodigo() {
		return this.ipgCodigo;
	}

	public void setIpgCodigo(Short ipgCodigo) {
		this.ipgCodigo = ipgCodigo;
	}

	@Column(name = "demografico_bruto", precision = 131089, scale = 0)
	public BigDecimal getDemograficoBruto() {
		return this.demograficoBruto;
	}

	public void setDemograficoBruto(BigDecimal demograficoBruto) {
		this.demograficoBruto = demograficoBruto;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 29)
	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Column(name = "ranking_area")
	public Integer getRankingArea() {
		return this.rankingArea;
	}

	public void setRankingArea(Integer rankingArea) {
		this.rankingArea = rankingArea;
	}

	@Column(name = "handshake")
	public Boolean getHandshake() {
		return this.handshake;
	}

	public void setHandshake(Boolean handshake) {
		this.handshake = handshake;
	}

	@Column(name = "calificacion", precision = 131089, scale = 0)
	public BigDecimal getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(BigDecimal calificacion) {
		this.calificacion = calificacion;
	}

	@Column(name = "detalle_estatus", length = 1500)
	public String getDetalleEstatus() {
		return this.detalleEstatus;
	}

	public void setDetalleEstatus(String detalleEstatus) {
		this.detalleEstatus = detalleEstatus;
	}

	@Column(name = "ias_en_cola")
	public Boolean getIasEnCola() {
		return this.iasEnCola;
	}

	public void setIasEnCola(Boolean iasEnCola) {
		this.iasEnCola = iasEnCola;
	}

	@Column(name = "ipg_en_cola")
	public Boolean getIpgEnCola() {
		return this.ipgEnCola;
	}

	public void setIpgEnCola(Boolean ipgEnCola) {
		this.ipgEnCola = ipgEnCola;
	}

	@Column(name = "modificado")
	public Boolean getModificado() {
		return this.modificado;
	}

	public void setModificado(Boolean modificado) {
		this.modificado = modificado;
	}

	@Column(name = "confirmado")
	public Boolean getConfirmado() {
		return this.confirmado;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	@Column(name = "orden")
	public Short getOrden() {
		return this.orden;
	}

	public void setOrden(Short orden) {
		this.orden = orden;
	}

	@Column(name = "comentario", length = 1500)
	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "candidato")
	public Set<AvisoCandidato> getAvisoCandidatos() {
		return this.avisoCandidatos;
	}

	public void setAvisoCandidatos(Set<AvisoCandidato> avisoCandidatos) {
		this.avisoCandidatos = avisoCandidatos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "candidato")
	public Set<TrackingPostulante> getTrackingPostulantes() {
		return this.trackingPostulantes;
	}

	public void setTrackingPostulantes(
			Set<TrackingPostulante> trackingPostulantes) {
		this.trackingPostulantes = trackingPostulantes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "candidato")
	public Set<CandidatoRechazo> getCandidatoRechazos() {
		return this.candidatoRechazos;
	}

	public void setCandidatoRechazos(Set<CandidatoRechazo> candidatoRechazos) {
		this.candidatoRechazos = candidatoRechazos;
	}

}