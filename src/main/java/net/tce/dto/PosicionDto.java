package net.tce.dto;

import java.util.Date;


public class PosicionDto extends ComunDto{

	private Long idPosicion;
	private Short maxAdjacency;
	private Boolean huboCambioPosicion;
	private long idEstatusPosicion;
	private String detalle; 
	private Date fechaUltimaBusqueda;
	private Date fechaCaducidad;
	private Integer periodoBusqueda;
	
	
	public PosicionDto(){}
	public PosicionDto(Long idPosicion,Date fechaUltimaBusqueda,Date fechaCaducidad,
						Integer periodoBusqueda,Boolean huboCambioPosicion){
		this.idPosicion=idPosicion;
		this.fechaUltimaBusqueda=fechaUltimaBusqueda;
		this.fechaCaducidad=fechaCaducidad;
		this.setPeriodoBusqueda(periodoBusqueda);
		this.huboCambioPosicion=huboCambioPosicion;
	}
	
	public PosicionDto(Long idPosicion,long idEstatusPosicion,String detalle){
		this.idPosicion=idPosicion;
		this.idEstatusPosicion=idEstatusPosicion;
		this.detalle=detalle;
	}
	public PosicionDto(Long idPosicion,Short maxAdjacency,Boolean huboCambioPosicion){
		this.idPosicion=idPosicion;
		this.maxAdjacency=maxAdjacency;
		this.huboCambioPosicion=huboCambioPosicion;
	}
	
	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}
	public Long getIdPosicion() {
		return idPosicion;
	}
	public Short getMaxAdjacency() {
		return maxAdjacency;
	}
	public void setMaxAdjacency(Short maxAdjacency) {
		this.maxAdjacency = maxAdjacency;
	}
	public Boolean isHuboCambioPosicion() {
		return huboCambioPosicion;
	}
	public void setHuboCambioPosicion(Boolean huboCambioPosicion) {
		this.huboCambioPosicion = huboCambioPosicion;
	}
	public long getIdEstatusPosicion() {
		return idEstatusPosicion;
	}
	public void setIdEstatusPosicion(long idEstatusPosicion) {
		this.idEstatusPosicion = idEstatusPosicion;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}


	public Date getFechaUltimaBusqueda() {
		return fechaUltimaBusqueda;
	}


	public void setFechaUltimaBusqueda(Date fechaUltimaBusqueda) {
		this.fechaUltimaBusqueda = fechaUltimaBusqueda;
	}
	public Integer getPeriodoBusqueda() {
		return periodoBusqueda;
	}
	public void setPeriodoBusqueda(Integer periodoBusqueda) {
		this.periodoBusqueda = periodoBusqueda;
	}
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	
}
