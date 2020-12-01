package net.tce.dto;

import java.util.HashSet;
import java.util.Set;

import net.tce.dto.PersonaValidacionDto;

public class TipoPersonaValidacionDto {
	private Long idTipoPersona;
	private Set<PersonaValidacionDto> personas = new HashSet<PersonaValidacionDto>(0);

	public TipoPersonaValidacionDto() {
	}

	public Long getIdTipoPersona() {
		return this.idTipoPersona;
	}

	public void setIdTipoPersona(Long idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public Set<PersonaValidacionDto> getPersonas() {
		return this.personas;
	}

	public void setPersonas(Set<PersonaValidacionDto> personas) {
		this.personas = personas;
	}
}
