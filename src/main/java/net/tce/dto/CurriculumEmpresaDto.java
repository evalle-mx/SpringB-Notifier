package net.tce.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 
 * @author Netto
 *
 */
public class CurriculumEmpresaDto extends ComunDto {
	
	//empresa (model)
	private String idEmpresa;
	private String idEstatusInscripcion;
	private String razonSocial;
	private String nombre;
	private String rfc;
	
	private String anioInicio;
	private String mesInicio;
	private String diaInicio;
	private String fechaInicio;
	private String clientes;
	private String socios;
	
	private String numeroEmpleados;
	private String descripcion;		//Empresa.texto
	private String estaVerificado;   //

//	private List<LocationInfoDto> ubicacion;
	private List<LocationInfoDto> localizacion;
	private List<ContactInfoDto> contacto;
	private List<RelacionEmpresaPersonaDto> relaciones;
	private List<Long> tiposRelacion;
	
	private String fechaModificacion;
	
	//RelacionEmpresaPersona
	private String idRol;
	private String idPersona;
	private String idPrivilegio;
	private String idPosicion;
	private String relacionFechaFin;
	
	private String atributoValor;
	private String atributoDescripcion;
	// Bandera para poder dar de baja la empresa en automático si es posible hacerlo 
	// Condición : Si es el único administrador y no hay asociados
	private Boolean bajaEmpresaVerificado;
	// Bandera y atributo para poder delegar la administración a alguien más si es posible hacerlo
	// Condición : Si es el único admin y hay asociados
	private String idPersonaDelegada;
	// Id de la persona que está ejecutando el servicio correspondiente, se usa para poder hacer validaciones respecto a su relación con la empresa
	private String idPersonaEjecutor;
	private Boolean privilegioAdm;
	private String historico;
	
	

	
	private String operacionRelacion;
	
	private Boolean representante;

	public CurriculumEmpresaDto(){}	
	
	public CurriculumEmpresaDto(String idEmpresa, String idEstatusInscripcion, String razonSocial, String nombre, 
			String anioInicio, String mesInicio, String diaInicio){
		this.idEmpresa=idEmpresa;
		this.idEstatusInscripcion=idEstatusInscripcion;
		this.razonSocial=razonSocial;
		this.nombre=nombre;
		this.anioInicio=anioInicio;
		this.mesInicio=anioInicio;
		this.diaInicio=diaInicio;
	}
	
	public CurriculumEmpresaDto(String idEmpresa, String idEstatusInscripcion, String razonSocial, String nombre,
			String rfc, String anioInicio, String mesInicio, String diaInicio, String fechaInicio,
			 String descripcion, String numeroEmpleados, 
			String idRol, String idPrivilegio, String idPosicion, String fechaFinRelacion, String estaVerificado ){
		this.idEmpresa=idEmpresa;
		this.idEstatusInscripcion=idEstatusInscripcion;
		this.razonSocial=razonSocial;
		this.nombre=nombre;
		this.rfc = rfc;
		this.anioInicio=anioInicio;
		this.mesInicio=mesInicio;
		this.diaInicio=diaInicio;
		this.fechaInicio = fechaInicio;
		this.descripcion=descripcion;
		this.numeroEmpleados=numeroEmpleados;
		
		this.idRol=idRol;
		this.idPrivilegio=idPrivilegio;
		this.idPosicion=idPosicion;
		this.relacionFechaFin=fechaFinRelacion;
		if(estaVerificado.equals("0")){
			this.estaVerificado="false";
		}else if(estaVerificado.equals("1")){
			this.estaVerificado="true";
		}else {
			this.estaVerificado=estaVerificado;
		}
		
	}

	public CurriculumEmpresaDto(String atributoValor, String atributoDescripcion) {
		this.atributoValor = atributoValor;
		this.atributoDescripcion = atributoDescripcion;
	}
	
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getIdEstatusInscripcion() {
		return idEstatusInscripcion;
	}
	public void setIdEstatusInscripcion(String idEstatusInscripcion) {
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
	public String getAnioInicio() {
		return anioInicio;
	}
	public void setAnioInicio(String anioInicio) {
		this.anioInicio = anioInicio;
	}
	public String getMesInicio() {
		return mesInicio;
	}
	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}
	public String getDiaInicio() {
		return diaInicio;
	}
	public void setDiaInicio(String diaInicio) {
		this.diaInicio = diaInicio;
	}
	public String getNumeroEmpleados() {
		return numeroEmpleados;
	}
	public void setNumeroEmpleados(String numeroEmpleados) {
		this.numeroEmpleados = numeroEmpleados;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
//	public List<LocationInfoDto> getUbicacion() {
//		return ubicacion;
//	}
//	public void setUbicacion(List<LocationInfoDto> ubicacion) {
//		this.ubicacion = ubicacion;
//	}
	public List<ContactInfoDto> getContacto() {
		return contacto;
	}
	public void setContacto(List<ContactInfoDto> contacto) {
		this.contacto = contacto;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdRol() {
		return idRol;
	}
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
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
	public String getIdPrivilegio() {
		return idPrivilegio;
	}
	public void setIdPrivilegio(String idPrivilegio) {
		this.idPrivilegio = idPrivilegio;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getRelacionFechaFin() {
		return relacionFechaFin;
	}

	public void setRelacionFechaFin(String relacionFechaFin) {
		this.relacionFechaFin = relacionFechaFin;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getEstaVerificado() {
		return estaVerificado;
	}

	public void setEstaVerificado(String estaVerificado) {
		this.estaVerificado = estaVerificado;
	}
	
	public String getAtributoValor() {
		return atributoValor;
	}

	public void setAtributoValor(String atributoValor) {
		this.atributoValor = atributoValor;
	}

	public String getAtributoDescripcion() {
		return atributoDescripcion;
	}

	public void setAtributoDescripcion(String atributoDescripcion) {
		this.atributoDescripcion = atributoDescripcion;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getIdPersonaDelegada() {
		return idPersonaDelegada;
	}

	public void setIdPersonaDelegada(String idPersonaDelegada) {
		this.idPersonaDelegada = idPersonaDelegada;
	}

	public Boolean getBajaEmpresaVerificado() {
		return bajaEmpresaVerificado;
	}

	public void setBajaEmpresaVerificado(Boolean bajaEmpresaVerificado) {
		this.bajaEmpresaVerificado = bajaEmpresaVerificado;
	}
	
	public List<RelacionEmpresaPersonaDto> getRelaciones() {
		return relaciones;
	}

	public void setRelaciones(List<RelacionEmpresaPersonaDto> relaciones) {
		this.relaciones = relaciones;
	}

//	public String getPrivilegioAdm() {
//		return privilegioAdm;
//	}
//
//	public void setPrivilegioAdm(String privilegioAdm) {
//		this.privilegioAdm = privilegioAdm;
//	}

	public String getIdPersonaEjecutor() {
		return idPersonaEjecutor;
	}

	public void setIdPersonaEjecutor(String idPersonaEjecutor) {
		this.idPersonaEjecutor = idPersonaEjecutor;
	}

	public String getOperacionRelacion() {
		return operacionRelacion;
	}

	public void setOperacionRelacion(String operacionRelacion) {
		this.operacionRelacion = operacionRelacion;
	}
	
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}

	public Boolean getRepresentante() {
		return representante;
	}

	public void setRepresentante(Boolean representante) {
		this.representante = representante;
	}

	public List<Long> getTiposRelacion() {
		return tiposRelacion;
	}

	public void setTiposRelacion(List<Long> tiposRelacion) {
		this.tiposRelacion = tiposRelacion;
	}

	public Boolean getPrivilegioAdm() {
		return privilegioAdm;
	}

	public void setPrivilegioAdm(Boolean privilegioAdm) {
		this.privilegioAdm = privilegioAdm;
	}

	public List<LocationInfoDto> getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(List<LocationInfoDto> localizacion) {
		this.localizacion = localizacion;
	}
}
