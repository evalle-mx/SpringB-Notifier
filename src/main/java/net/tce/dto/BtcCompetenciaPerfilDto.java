package net.tce.dto;

public class BtcCompetenciaPerfilDto extends ComunDto {
	
	private String idBitacoraCompetenciaPerfil;
	private String idBitacoraPosicion;
	private String idCompetenciaPerfil;
	private String idCompetencia;
	private String seleccionado;
	
	public String getIdBitacoraCompetenciaPerfil() {
		return this.idBitacoraCompetenciaPerfil;
	}

	public void setIdBitacoraCompetenciaPerfil(String idBitacoraCompetenciaPerfil) {
		this.idBitacoraCompetenciaPerfil = idBitacoraCompetenciaPerfil;
	}
	public String getSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(String seleccionado) {
		this.seleccionado = seleccionado;
	}
	public String getIdCompetencia() {
		return idCompetencia;
	}
	public void setIdCompetencia(String idCompetencia) {
		this.idCompetencia = idCompetencia;
	}
	public String getIdBitacoraPosicion() {
		return idBitacoraPosicion;
	}
	public void setIdBitacoraPosicion(String idBitacoraPosicion) {
		this.idBitacoraPosicion = idBitacoraPosicion;
	}

	public String getIdCompetenciaPerfil() {
		return idCompetenciaPerfil;
	}

	public void setIdCompetenciaPerfil(String idCompetenciaPerfil) {
		this.idCompetenciaPerfil = idCompetenciaPerfil;
	}
	
	

}
