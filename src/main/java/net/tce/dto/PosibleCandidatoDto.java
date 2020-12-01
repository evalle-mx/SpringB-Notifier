package net.tce.dto;

public class PosibleCandidatoDto extends ComunDto {

	private String idPersona;
	private String tipoPostulante;
	private int caso;
	private long idPosibleCandidato;
	private long idPosicion;
	private long idArea;
	private boolean confirmado;
	
    public PosibleCandidatoDto(){}
	
	public PosibleCandidatoDto(String idEmpresaConf,String idPersona,String tipoPostulante,int caso){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.tipoPostulante=tipoPostulante;
		this.caso=caso;
	}	
	public PosibleCandidatoDto(long idPosibleCandidato,boolean confirmado, long idPosicion,long idArea){
		this.idPosibleCandidato=idPosibleCandidato;
		this.idPosicion=idPosicion;
		this.idArea=idArea;
		this.confirmado=confirmado;
	}
	
	public long getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(long idPosicion) {
		this.idPosicion = idPosicion;
	}
	public long getIdArea() {
		return idArea;
	}
	public void setIdArea(long idArea) {
		this.idArea = idArea;
	}	
	public long getIdPosibleCandidato() {
		return idPosibleCandidato;
	}
	public void setIdPosibleCandidato(long idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getTipoPostulante() {
		return tipoPostulante;
	}

	public void setTipoPostulante(String tipoPostulante) {
		this.tipoPostulante = tipoPostulante;
	}

	public int getCaso() {
		return caso;
	}

	public void setCaso(int caso) {
		this.caso = caso;
	}

}
