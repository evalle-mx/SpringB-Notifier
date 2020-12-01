package net.tce.dto;

public class RelacionEmpresaPersonaDto {

	private String idRelacionEmpresaPersona;
	private String idRol;
	private String idEmpresa;
	private String idPrivilegio;
	private String idPersona;
	private String idPosicion;
	private String fechaInicio;
	private String fechaFin;
	private Long count;
	
	
	public RelacionEmpresaPersonaDto(){}
	
	public RelacionEmpresaPersonaDto(Long idEmpresa,Long count){
		this.idEmpresa=idEmpresa.toString();
		this.count=count;
	}
	
	public String getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(String idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getIdPrivilegio() {
		return idPrivilegio;
	}
	public void setIdPrivilegio(String idPrivilegio) {
		this.idPrivilegio = idPrivilegio;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	public String getIdRol() {
		return idRol;
	}

	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	
	
}
