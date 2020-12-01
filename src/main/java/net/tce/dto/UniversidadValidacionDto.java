package net.tce.dto;

import java.util.ArrayList;
import java.util.List;

public class UniversidadValidacionDto {
	private Long idUniversidad;
	private List<EscolaridadValidacionDto> escolaridads = new ArrayList<EscolaridadValidacionDto>(0);

	public UniversidadValidacionDto() {
	}

	public Long getIdUniversidad() {
		return this.idUniversidad;
	}

	public void setIdUniversidad(Long idUniversidad) {
		this.idUniversidad = idUniversidad;
	}

	public List<EscolaridadValidacionDto> getEscolaridads() {
		return this.escolaridads;
	}

	public void setEscolaridads(List<EscolaridadValidacionDto> escolaridads) {
		this.escolaridads = escolaridads;
	}

}
