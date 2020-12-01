package net.tce.dto;

import java.util.List;

public class CandidatoDetalleDto {
	private String cantidad;
	private String descripcion;
	private List<CandidatoDto> listado;
	private CandidatoDetalleDto demografico;
	private List<CandidatoDetalleDto> detalle;
	private CandidatoDetalleDto ias;
	private CandidatoDetalleDto ipg;
	private CandidatoDetalleDto habilidad;;

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CandidatoDetalleDto getDemografico() {
		return demografico;
	}

	public void setDemografico(CandidatoDetalleDto demografico) {
		this.demografico = demografico;
	}

	public List<CandidatoDetalleDto> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<CandidatoDetalleDto> detalle) {
		this.detalle = detalle;
	}
	
	public CandidatoDetalleDto getIas() {
		return ias;
	}

	public void setIas(CandidatoDetalleDto ias) {
		this.ias = ias;
	}

	public CandidatoDetalleDto getIpg() {
		return ipg;
	}

	public void setIpg(CandidatoDetalleDto ipg) {
		this.ipg = ipg;
	}

	public List<CandidatoDto> getListado() {
		return listado;
	}

	public void setListado(List<CandidatoDto> listado) {
		this.listado = listado;
	}

	public CandidatoDetalleDto getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(CandidatoDetalleDto habilidad) {
		this.habilidad = habilidad;
	}
}
