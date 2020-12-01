package net.tce.model;

// Generated Feb 2, 2015 3:37:31 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;

import net.tce.util.Constante;

/**
 * Domicilio generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "DOMICILIO", uniqueConstraints = @UniqueConstraint(columnNames = "id_posicion"))
@DynamicUpdate
public class Domicilio implements java.io.Serializable {

	private long idDomicilio;
	private TipoDomicilio tipoDomicilio;
	private Empresa empresa;
	private TipoPrecision tipoPrecision;
	private Municipio municipio;
	private CodigoProceso codigoProceso;
	private Persona persona;
	private Posicion posicion;
	private Asentamiento asentamiento;
	private String calle;
	private String numeroInterior;
	private String numeroExterior;
	private Boolean direccionFacturacion;
	private BigDecimal googleLatitude;
	private BigDecimal googleLongitude;
	private String direccionNoCatalogada;
	private String descripcion;


	public Domicilio() {
	}

	public Domicilio(long idDomicilio) {
		this.idDomicilio = idDomicilio;
		
	}

	public Domicilio(long idDomicilio, TipoDomicilio tipoDomicilio,
			Empresa empresa, TipoPrecision tipoPrecision, Municipio municipio,
			CodigoProceso codigoProceso, Persona persona, Posicion posicion,
			Asentamiento asentamiento, String calle, String numeroInterior,
			String numeroExterior, Boolean direccionFacturacion,
			BigDecimal googleLatitude, BigDecimal googleLongitude,
			String direccionNoCatalogada, String descripcion) {
		this.idDomicilio = idDomicilio;
		this.tipoDomicilio = tipoDomicilio;
		this.empresa = empresa;
		this.tipoPrecision = tipoPrecision;
		this.municipio = municipio;
		this.codigoProceso = codigoProceso;
		this.persona = persona;
		this.posicion = posicion;
		this.asentamiento = asentamiento;
		this.calle = calle;
		this.numeroInterior = numeroInterior;
		this.numeroExterior = numeroExterior;
		this.direccionFacturacion = direccionFacturacion;
		this.googleLatitude = googleLatitude;
		this.googleLongitude = googleLongitude;
		this.direccionNoCatalogada = direccionNoCatalogada;
		this.descripcion = descripcion;
	}

	@Id
	@SequenceGenerator(name="seq_Domicilio", sequenceName=Constante.SECUENCIA_BD_DOMICILIO, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_Domicilio")
	@Column(name = "ID_DOMICILIO", unique = true, nullable = false, precision = 10, scale = 0)
	public long getIdDomicilio() {
		return this.idDomicilio;
	}

	public void setIdDomicilio(long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_DOMICILIO")
	public TipoDomicilio getTipoDomicilio() {
		return this.tipoDomicilio;
	}

	public void setTipoDomicilio(TipoDomicilio tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPRESA")
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_PRECISION")
	public TipoPrecision getTipoPrecision() {
		return this.tipoPrecision;
	}

	public void setTipoPrecision(TipoPrecision tipoPrecision) {
		this.tipoPrecision = tipoPrecision;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MUNICIPIO")
	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CODIGO_PROCESO")
	public CodigoProceso getCodigoProceso() {
		return this.codigoProceso;
	}

	public void setCodigoProceso(CodigoProceso codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERSONA")
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_POSICION", unique = true)
	public Posicion getPosicion() {
		return this.posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ASENTAMIENTO")
	public Asentamiento getAsentamiento() {
		return this.asentamiento;
	}

	public void setAsentamiento(Asentamiento asentamiento) {
		this.asentamiento = asentamiento;
	}

	@Column(name = "CALLE", length = 250)
	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	@Column(name = "NUMERO_INTERIOR", length = 10)
	public String getNumeroInterior() {
		return this.numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	@Column(name = "NUMERO_EXTERIOR", length = 10)
	public String getNumeroExterior() {
		return this.numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	@Column(name = "DIRECCION_FACTURACION")
	public Boolean getDireccionFacturacion() {
		return this.direccionFacturacion;
	}

	public void setDireccionFacturacion(Boolean direccionFacturacion) {
		this.direccionFacturacion = direccionFacturacion;
	}

	@Column(name = "GOOGLE_LATITUDE", precision = 131089, scale = 0)
	public BigDecimal getGoogleLatitude() {
		return this.googleLatitude;
	}

	public void setGoogleLatitude(BigDecimal googleLatitude) {
		this.googleLatitude = googleLatitude;
	}

	@Column(name = "GOOGLE_LONGITUDE", precision = 131089, scale = 0)
	public BigDecimal getGoogleLongitude() {
		return this.googleLongitude;
	}

	public void setGoogleLongitude(BigDecimal googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	@Column(name = "DIRECCION_NO_CATALOGADA", length = 1500)
	public String getDireccionNoCatalogada() {
		return this.direccionNoCatalogada;
	}

	public void setDireccionNoCatalogada(String direccionNoCatalogada) {
		this.direccionNoCatalogada = direccionNoCatalogada;
	}

	@Column(name = "DESCRIPCION", length = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}