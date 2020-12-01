package net.tce.dto;

import java.util.List;

public class RolDto extends ComunDto{

	private String idPersona;
	private String idRol;
	private String descripcion;
	private String rolInicial;
	private String vistaInicial;
		
	private List<PermisoDto> rolPermisos;
	
	public RolDto(){}
	public RolDto(long idRol, String descripcion,
			boolean rolInicial) {
		this.idRol = Long.toString(idRol);
		this.descripcion = descripcion;
		this.setRolInicial(Boolean.toString(rolInicial));
	}

	public RolDto(long idRol, String descripcion, String vistaInicial,
			boolean rolInicial) {
		this.idRol = Long.toString(idRol);
		this.descripcion = descripcion;
		this.vistaInicial=vistaInicial;
		this.setRolInicial(Boolean.toString(rolInicial));
	}
	
	public String getIdRol() {
		return idRol;
	}
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getRolInicial() {
		return rolInicial;
	}
	public void setRolInicial(String rolInicial) {
		this.rolInicial = rolInicial;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public String getVistaInicial() {
		return vistaInicial;
	}
	public void setVistaInicial(String vistaInicial) {
		this.vistaInicial = vistaInicial;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public List<PermisoDto> getRolPermisos() {
		return rolPermisos;
	}
	public void setRolPermisos(List<PermisoDto> rolPermisos) {
		this.rolPermisos = rolPermisos;
	}
	
	
}
