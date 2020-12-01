package net.tce.dto;

public class DocumentoClasificacionDto extends ComunDto {
	
	private String idDocumentoClasificacion;
	private String idArea;
	private String idTipoDocumento;
	
	private String idPersona;
	private String idEmpresa;
	private String idPosicion;
	private String idPerfil;
	
	private String idTextoClasificacion;
	private String estatusClasificacion;
	private String versionModelo;
	private String textoClasificacion;
	private String categoria;
	private String categoriasAnalisis;
	
	private String aplicaModelo;	//1|0 (true/false)
	
	public DocumentoClasificacionDto() {
	}

	public String getIdDocumentoClasificacion() {
		return idDocumentoClasificacion;
	}

	public void setIdDocumentoClasificacion(String idDocumentoClasificacion) {
		this.idDocumentoClasificacion = idDocumentoClasificacion;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	

	public String getEstatusClasificacion() {
		return estatusClasificacion;
	}

	public void setEstatusClasificacion(String estatusClasificacion) {
		this.estatusClasificacion = estatusClasificacion;
	}

	public String getVersionModelo() {
		return versionModelo;
	}

	public void setVersionModelo(String versionModelo) {
		this.versionModelo = versionModelo;
	}

	public String getTextoClasificacion() {
		return textoClasificacion;
	}

	public void setTextoClasificacion(String textoClasificacion) {
		this.textoClasificacion = textoClasificacion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoriasAnalisis() {
		return categoriasAnalisis;
	}

	public void setCategoriasAnalisis(String categoriasAnalisis) {
		this.categoriasAnalisis = categoriasAnalisis;
	}

	public String getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getAplicaModelo() {
		return aplicaModelo;
	}

	public void setAplicaModelo(String aplicaModelo) {
		this.aplicaModelo = aplicaModelo;
	}

	public String getIdTextoClasificacion() {
		return idTextoClasificacion;
	}

	public void setIdTextoClasificacion(String idTextoClasificacion) {
		this.idTextoClasificacion = idTextoClasificacion;
	}
}
