package net.tce.dto;

import java.util.Date;

public class BtcRecdrioDto  extends ComunDto{
	
	private Long idBitacoraTrackRecdrio;
	private Long idBitacoraTrackPost;
	private Long idBitacoraTrackMon;
	private Long idRecordatorio;
	private Short idTipoRecordatorio;
	private String activo;
	private Date fecha;
	private Short cifra;
	private String seAplico;
	private String descripcion;
	
	public Long getIdBitacoraTrackRecdrio() {
		return idBitacoraTrackRecdrio;
	}
	public void setIdBitacoraTrackRecdrio(Long idBitacoraTrackRecdrio) {
		this.idBitacoraTrackRecdrio = idBitacoraTrackRecdrio;
	}
	public Long getIdBitacoraTrackPost() {
		return idBitacoraTrackPost;
	}
	public void setIdBitacoraTrackPost(Long idBitacoraTrackPost) {
		this.idBitacoraTrackPost = idBitacoraTrackPost;
	}
	public Long getIdBitacoraTrackMon() {
		return idBitacoraTrackMon;
	}
	public void setIdBitacoraTrackMon(Long idBitacoraTrackMon) {
		this.idBitacoraTrackMon = idBitacoraTrackMon;
	}
	public Long getIdRecordatorio() {
		return idRecordatorio;
	}
	public void setIdRecordatorio(Long idRecordatorio) {
		this.idRecordatorio = idRecordatorio;
	}
	public Short getIdTipoRecordatorio() {
		return idTipoRecordatorio;
	}
	public void setIdTipoRecordatorio(Short idTipoRecordatorio) {
		this.idTipoRecordatorio = idTipoRecordatorio;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Short getCifra() {
		return cifra;
	}
	public void setCifra(Short cifra) {
		this.cifra = cifra;
	}
	public String getSeAplico() {
		return seAplico;
	}
	public void setSeAplico(String seAplico) {
		this.seAplico = seAplico;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
