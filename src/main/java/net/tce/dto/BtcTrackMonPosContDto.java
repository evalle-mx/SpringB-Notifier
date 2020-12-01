package net.tce.dto;

import java.util.Date;

public class BtcTrackMonPosContDto extends ComunDto{
	
	private Long idContenido;
	private Long idTipoContenidoArchivo;
	private String descripcion;
	private String rutaAbsoluta;
	private Date fechaCarga;
	private Long idTrackingMonitor;
	private Long idTrackingPostulante;
	
	public Long getIdContenido() {
		return idContenido;
	}
	public void setIdContenido(Long idContenido) {
		this.idContenido = idContenido;
	}
	public String getRutaAbsoluta() {
		return rutaAbsoluta;
	}
	public void setRutaAbsoluta(String rutaAbsoluta) {
		this.rutaAbsoluta = rutaAbsoluta;
	}
	public Date getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getIdTipoContenidoArchivo() {
		return idTipoContenidoArchivo;
	}
	public void setIdTipoContenidoArchivo(Long idTipoContenidoArchivo) {
		this.idTipoContenidoArchivo = idTipoContenidoArchivo;
	}
	public Long getIdTrackingMonitor() {
		return idTrackingMonitor;
	}
	public void setIdTrackingMonitor(Long idTrackingMonitor) {
		this.idTrackingMonitor = idTrackingMonitor;
	}
	public Long getIdTrackingPostulante() {
		return idTrackingPostulante;
	}
	public void setIdTrackingPostulante(Long idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}
	
}
