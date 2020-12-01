package net.tce.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpresaValidacionDto {
	private Long idEmpresa;	
	private Long idEstatusInscripcion;	
	private String idTextoClasificacion;
	private String razonSocial;
	private String nombre;
	private String texto;
	private Date fechaInicio;
	private Short anioInicio;
	private Byte mesInicio;
	private Byte diaInicio;
	private Date fechaRegistro;
	private String rfc;
	private Long numeroEmpleados;
	private String clientes;
	private String socios;
	
	private List<DomicilioValidacionDto> domicilios = new ArrayList<DomicilioValidacionDto>(0);
	private List<ContactoValidacionDto> contactos = new ArrayList<ContactoValidacionDto>(0);
	private List<ContenidoValidacionDto> contenidos = new ArrayList<ContenidoValidacionDto>(0);
	private List<RelacionEmpresaPersonaValidacionDto> relacionEmpresaPersonas = new ArrayList<RelacionEmpresaPersonaValidacionDto>(0);

	public EmpresaValidacionDto() {
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Long getIdEstatusInscripcion() {
		return idEstatusInscripcion;
	}

	public void setIdEstatusInscripcion(Long idEstatusInscripcion) {
		this.idEstatusInscripcion = idEstatusInscripcion;
	}

	
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Short getAnioInicio() {
		return anioInicio;
	}

	public void setAnioInicio(Short anioInicio) {
		this.anioInicio = anioInicio;
	}

	public Byte getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(Byte mesInicio) {
		this.mesInicio = mesInicio;
	}

	public Byte getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(Byte diaInicio) {
		this.diaInicio = diaInicio;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Long getNumeroEmpleados() {
		return numeroEmpleados;
	}

	public void setNumeroEmpleados(Long numeroEmpleados) {
		this.numeroEmpleados = numeroEmpleados;
	}

	public String getClientes() {
		return clientes;
	}

	public void setClientes(String clientes) {
		this.clientes = clientes;
	}

	public String getSocios() {
		return socios;
	}

	public void setSocios(String socios) {
		this.socios = socios;
	}

	public List<DomicilioValidacionDto> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(List<DomicilioValidacionDto> domicilios) {
		this.domicilios = domicilios;
	}

	public List<ContactoValidacionDto> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactoValidacionDto> contactos) {
		this.contactos = contactos;
	}

	public List<ContenidoValidacionDto> getContenidos() {
		return contenidos;
	}

	public void setContenidos(List<ContenidoValidacionDto> contenidos) {
		this.contenidos = contenidos;
	}

	public List<RelacionEmpresaPersonaValidacionDto> getRelacionEmpresaPersonas() {
		return relacionEmpresaPersonas;
	}

	public void setRelacionEmpresaPersonas(
			List<RelacionEmpresaPersonaValidacionDto> relacionEmpresaPersonas) {
		this.relacionEmpresaPersonas = relacionEmpresaPersonas;
	}

	public String getIdTextoClasificacion() {
		return idTextoClasificacion;
	}

	public void setIdTextoClasificacion(String idTextoClasificacion) {
		this.idTextoClasificacion = idTextoClasificacion;
	}
	
}
