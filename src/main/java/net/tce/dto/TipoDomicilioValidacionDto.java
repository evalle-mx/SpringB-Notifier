package net.tce.dto;

import java.util.HashSet;
import java.util.Set;


import net.tce.dto.DomicilioValidacionDto;

public class TipoDomicilioValidacionDto {
	private Long idTipoDomicilio;
	private Set<DomicilioValidacionDto> domicilios = new HashSet<DomicilioValidacionDto>(0);

	public TipoDomicilioValidacionDto() {
	}

	public Long getIdTipoDomicilio() {
		return this.idTipoDomicilio;
	}

	public void setIdTipoDomicilio(Long idTipoDomicilio) {
		this.idTipoDomicilio = idTipoDomicilio;
	}

	public Set<DomicilioValidacionDto> getDomicilios() {
		return this.domicilios;
	}

	public void setDomicilios(Set<DomicilioValidacionDto> domicilios) {
		this.domicilios = domicilios;
	}

}
