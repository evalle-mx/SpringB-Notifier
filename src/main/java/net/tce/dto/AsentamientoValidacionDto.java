package net.tce.dto;

import java.util.HashSet;
import java.util.Set;


import net.tce.dto.DomicilioValidacionDto;

public class AsentamientoValidacionDto {
	private Long idAsentamiento;
	private Set<DomicilioValidacionDto> domicilios = new HashSet<DomicilioValidacionDto>(0);
	

	public AsentamientoValidacionDto() {
	}

	public Long getIdAsentamiento() {
		return this.idAsentamiento;
	}

	public void setIdAsentamiento(Long idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}

	public Set<DomicilioValidacionDto> getDomicilios() {
		return this.domicilios;
	}

	public void setDomicilios(Set<DomicilioValidacionDto> domicilios) {
		this.domicilios = domicilios;
	}
	
}
