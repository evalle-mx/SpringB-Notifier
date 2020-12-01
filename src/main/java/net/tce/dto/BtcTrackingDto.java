package net.tce.dto;

import java.util.Date;

public class BtcTrackingDto extends ComunDto{
	
	//obligatorios
	private Long idBitacoraTrackRel;
	private Long idPosicion;
	private Long idRelacionEmpresaPersona;
	private Short idTipoEventoBitacora;
	private Short idTipoModuloBitacora;
	private Date fecha;
	private Boolean porSistema;
	private String observacion;

	//ModeloRsc,ModeloRscFase, ModeloRscPos, ModeloRscPosFase
	private BtcModeloRscDto btcModeloRscDto;
	
	//monitor, TrackingMonitor y TrackingPostulante
	private BtcTrackingMonPosDto btcTrackingMonPosDto;
	
	//precandidatos, candidatos
	private BtcTrackCandDto btcTrackCandDto;
	
	//contenido
	private BtcTrackMonPosContDto btcTrackMonPosContDto;

	/*private Long idEstadoTracking;
	private long idModeloRscPosFaseRol;
	private long idTrackingPostulante;
	private long idContenido;
	private long idTrackingMonitor;
	private long idPerfilPosicion;*/
	
	public Long getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}
	public Long getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(Long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	public Short getIdTipoEventoBitacora() {
		return idTipoEventoBitacora;
	}
	public void setIdTipoEventoBitacora(Short idTipoEventoBitacora) {
		this.idTipoEventoBitacora = idTipoEventoBitacora;
	}
	public Short getIdTipoModuloBitacora() {
		return idTipoModuloBitacora;
	}
	public void setIdTipoModuloBitacora(Short idTipoModuloBitacora) {
		this.idTipoModuloBitacora = idTipoModuloBitacora;
	}
	
	/*public Long getIdBitacoraModelorsc() {
		return idBitacoraModelorsc;
	}
	public void setIdBitacoraModelorsc(Long idBitacoraModelorsc) {
		this.idBitacoraModelorsc = idBitacoraModelorsc;
	}
	public Long getIdBitacoraFase() {
		return idBitacoraFase;
	}
	public void setIdBitacoraFase(Long idBitacoraFase) {
		this.idBitacoraFase = idBitacoraFase;
	}
	public Long getIdBitacoraTrackCand() {
		return idBitacoraTrackCand;
	}
	public void setIdBitacoraTrackCand(Long idBitacoraTrackCand) {
		this.idBitacoraTrackCand = idBitacoraTrackCand;
	}
	public Long getIdBitacoraTrackMon() {
		return idBitacoraTrackMon;
	}
	public void setIdBitacoraTrackMon(Long idBitacoraTrackMon) {
		this.idBitacoraTrackMon = idBitacoraTrackMon;
	}
	public Long getIdBitacoraTrackPost() {
		return idBitacoraTrackPost;
	}
	public void setIdBitacoraTrackPost(Long idBitacoraTrackPost) {
		this.idBitacoraTrackPost = idBitacoraTrackPost;
	}*/
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BtcModeloRscDto getBtcModeloRscDto() {
		return btcModeloRscDto;
	}
	public void setBtcModeloRscDto(BtcModeloRscDto btcModeloRscDto) {
		this.btcModeloRscDto = btcModeloRscDto;
	}
	public Long getIdBitacoraTrackRel() {
		return idBitacoraTrackRel;
	}
	public void setIdBitacoraTrackRel(Long idBitacoraTrackRel) {
		this.idBitacoraTrackRel = idBitacoraTrackRel;
	}
	public Boolean getPorSistema() {
		return porSistema;
	}
	public void setPorSistema(Boolean porSistema) {
		this.porSistema = porSistema;
	}
	public BtcTrackingMonPosDto getBtcTrackingMonPosDto() {
		return btcTrackingMonPosDto;
	}
	public void setBtcTrackingMonPosDto(BtcTrackingMonPosDto btcTrackingMonPosDto) {
		this.btcTrackingMonPosDto = btcTrackingMonPosDto;
	}
	public BtcTrackCandDto getBtcTrackCandDto() {
		return btcTrackCandDto;
	}
	public void setBtcTrackCandDto(BtcTrackCandDto btcTrackCandDto) {
		this.btcTrackCandDto = btcTrackCandDto;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public BtcTrackMonPosContDto getBtcTrackMonPosContDto() {
		return btcTrackMonPosContDto;
	}
	public void setBtcTrackMonPosContDto(BtcTrackMonPosContDto btcTrackMonPosContDto) {
		this.btcTrackMonPosContDto = btcTrackMonPosContDto;
	}
	
}
