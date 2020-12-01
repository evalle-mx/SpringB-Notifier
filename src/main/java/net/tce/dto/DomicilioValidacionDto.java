package net.tce.dto;

import java.math.BigDecimal;
import java.util.Date;
import net.tce.dto.AsentamientoValidacionDto;
import net.tce.dto.PersonaValidacionDto;
import net.tce.dto.TipoDomicilioValidacionDto;

public class DomicilioValidacionDto {
	private Long idDomicilio;
	private TipoDomicilioValidacionDto tipoDomicilio;
	private PersonaValidacionDto persona;
	private AsentamientoValidacionDto asentamiento;
	private String calle;
	private String numeroInterior;
	private String numeroExterior;
	private Boolean direccionFacturacion;
	private BigDecimal googleLatitude;
	private BigDecimal googleLongitude;
	private String direccionNoCatalogada;
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaModificacion;

	public DomicilioValidacionDto() {
	}


	public Long getIdDomicilio() {
		return this.idDomicilio;
	}

	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public TipoDomicilioValidacionDto getTipoDomicilio() {
		return this.tipoDomicilio;
	}

	public void setTipoDomicilio(TipoDomicilioValidacionDto tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}


	public PersonaValidacionDto getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaValidacionDto persona) {
		this.persona = persona;
	}

	public AsentamientoValidacionDto getAsentamiento() {
		return this.asentamiento;
	}

	public void setAsentamiento(AsentamientoValidacionDto asentamiento) {
		this.asentamiento = asentamiento;
	}

	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumeroInterior() {
		return this.numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getNumeroExterior() {
		return this.numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public Boolean getDireccionFacturacion() {
		return this.direccionFacturacion;
	}

	public void setDireccionFacturacion(Boolean direccionFacturacion) {
		this.direccionFacturacion = direccionFacturacion;
	}

	public BigDecimal getGoogleLatitude() {
		return this.googleLatitude;
	}

	public void setGoogleLatitude(BigDecimal googleLatitude) {
		this.googleLatitude = googleLatitude;
	}

	public BigDecimal getGoogleLongitude() {
		return this.googleLongitude;
	}

	public void setGoogleLongitude(BigDecimal googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	public String getDireccionNoCatalogada() {
		return this.direccionNoCatalogada;
	}

	public void setDireccionNoCatalogada(String direccionNoCatalogada) {
		this.direccionNoCatalogada = direccionNoCatalogada;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}
