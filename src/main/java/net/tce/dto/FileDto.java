package net.tce.dto;


import javax.activation.DataHandler;

public class FileDto extends ComunDto{

	  private String idContenido;
	  private String idPersona;
	  private String idPosicion;
	  private String idEmpresa;
	  private String tipoArchivo;
	  private DataHandler dhContenido;
	  private String idTipoContenido;
	  private String descripcion;
	  private String url;
	  private String idTrackingMonitor;
	  private String idTrackingPostulante;
	 
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
	public String getTipoArchivo() {
		return tipoArchivo;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	
	public void setIdTipoContenido(String idTipoContenido) {
		this.idTipoContenido = idTipoContenido;
	}
	public String getIdTipoContenido() {
		return idTipoContenido;
	}
	
	public void setDhContenido(DataHandler dhContenido) {
		this.dhContenido = dhContenido;
	}
	public DataHandler getDhContenido() {
		return dhContenido;
	}
	public void setIdContenido(String idContenido) {
		this.idContenido = idContenido;
	}
	public String getIdContenido() {
		return idContenido;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getIdEmpresa() {
		return idEmpresa;
	}
	
	
	
	
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getIdTrackingMonitor() {
		return idTrackingMonitor;
	}
	public void setIdTrackingMonitor(String idTrackingMonitor) {
		this.idTrackingMonitor = idTrackingMonitor;
	}
	public String getIdTrackingPostulante() {
		return idTrackingPostulante;
	}
	public void setIdTrackingPostulante(String idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}
	
}
