package net.tce.dto;

import java.math.BigDecimal;



public class PerfilPosicionDto {

	private String idPerfilPosicion;
	private PerfilDto perfil;
	private String idPosicion;
	private String ponderacion;
	private long   idTipoPerfil;

	public PerfilPosicionDto() {
	}

	public PerfilPosicionDto(String idPerfilPosicion, PerfilDto perfil,
			String idPosicion, String ponderacion) {
		this.idPerfilPosicion = idPerfilPosicion;
		this.perfil = perfil;
		this.idPosicion = idPosicion;
		this.ponderacion = ponderacion;
	}
	
	public PerfilPosicionDto(Long idPerfil, BigDecimal ponderacion) {
		this.ponderacion = ponderacion.toString();
		this.perfil = new  PerfilDto(idPerfil);
	}

	public String getIdPerfilPosicion() {
		return idPerfilPosicion;
	}

	public void setIdPerfilPosicion(String idPerfilPosicion) {
		this.idPerfilPosicion = idPerfilPosicion;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}
		
	public long getIdTipoPerfil() {
		return idTipoPerfil;
	}

	public void setIdTipoPerfil(long idTipoPerfil) {
		this.idTipoPerfil = idTipoPerfil;
	}

	public PerfilDto getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilDto perfil) {
		this.perfil = perfil;
	}

}
