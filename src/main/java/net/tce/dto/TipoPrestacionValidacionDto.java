package net.tce.dto;

import java.util.ArrayList;
import java.util.List;

public class TipoPrestacionValidacionDto {
	private Long idTipoPrestacion;
	private List<ExperienciaLaboralValidacionDto> experienciaLaborals = new ArrayList<ExperienciaLaboralValidacionDto>(0);
	private List<PersonaValidacionDto> personas = new ArrayList<PersonaValidacionDto>(0);

	public TipoPrestacionValidacionDto() {
	}

	public Long getIdTipoPrestacion() {
		return this.idTipoPrestacion;
	}

	public void setIdTipoPrestacion(Long idTipoPrestacion) {
		this.idTipoPrestacion = idTipoPrestacion;
	}

	public List<ExperienciaLaboralValidacionDto> getExperienciaLaborals() {
		return this.experienciaLaborals;
	}

	public void setExperienciaLaborals(
			List<ExperienciaLaboralValidacionDto> experienciaLaborals) {
		this.experienciaLaborals = experienciaLaborals;
	}

	public List<PersonaValidacionDto> getPersonas() {
		return this.personas;
	}

	public void setPersonas(List<PersonaValidacionDto> personas) {
		this.personas = personas;
	}
}
