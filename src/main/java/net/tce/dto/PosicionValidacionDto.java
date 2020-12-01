package net.tce.dto;

import java.util.ArrayList;
import java.util.List;

public class PosicionValidacionDto {
	private Long idPosicion;	
	private String nombre;
	private Long idAmbitoGeografico;
	private Long salarioMin;
	private Long salarioMax;
	private Boolean asignada;

	private List<DomicilioValidacionDto> domicilios = new ArrayList<DomicilioValidacionDto>(0);
	private List<PerfilValidacionDto> perfils = new ArrayList<PerfilValidacionDto>(0);

	public PosicionValidacionDto() {
	}

	public Long getIdPosicion() {
		return this.idPosicion;
	}

	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdAmbitoGeografico() {
		return idAmbitoGeografico;
	}

	public void setIdAmbitoGeografico(Long idAmbitoGeografico) {
		this.idAmbitoGeografico = idAmbitoGeografico;
	}

	public List<DomicilioValidacionDto> getDomicilios() {
		return this.domicilios;
	}

	public void setDomicilios(List<DomicilioValidacionDto> domicilios) {
		this.domicilios = domicilios;
	}
	public Long getSalarioMin() {
		return salarioMin;
	}

	public void setSalarioMin(Long salarioMin) {
		this.salarioMin = salarioMin;
	}

	public Long getSalarioMax() {
		return salarioMax;
	}

	public void setSalarioMax(Long salarioMax) {
		this.salarioMax = salarioMax;
	}

	public Boolean getAsignada() {
		return asignada;
	}

	public void setAsignada(Boolean asignada) {
		this.asignada = asignada;
	}

	public List<PerfilValidacionDto> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<PerfilValidacionDto> perfils) {
		this.perfils = perfils;
	}

}
