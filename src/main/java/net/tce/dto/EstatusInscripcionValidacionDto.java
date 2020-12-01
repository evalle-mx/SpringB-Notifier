package net.tce.dto;

import java.util.HashSet;
import java.util.Set;


import net.tce.dto.PersonaValidacionDto;

public class EstatusInscripcionValidacionDto {

	private long idEstatusInscripcion;
	private Set<PersonaValidacionDto> personas = new HashSet<PersonaValidacionDto>(0);

	public EstatusInscripcionValidacionDto() {
	}
	
	public EstatusInscripcionValidacionDto(long idEstatusInscripcion) {
		this.idEstatusInscripcion = idEstatusInscripcion;
	}

	public long getIdEstatusInscripcion() {
		return this.idEstatusInscripcion;
	}

	public void setIdEstatusInscripcion(long idEstatusInscripcion) {
		this.idEstatusInscripcion = idEstatusInscripcion;
	}

	public Set<PersonaValidacionDto> getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set<PersonaValidacionDto> personas) {
		this.personas = personas;
	}

}
