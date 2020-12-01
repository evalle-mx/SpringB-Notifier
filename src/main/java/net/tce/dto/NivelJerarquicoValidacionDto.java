package net.tce.dto;

import java.util.ArrayList;
import java.util.List;

public class NivelJerarquicoValidacionDto {
	private Long idNivelJerarquico;
	private List<ExperienciaLaboralValidacionDto> experienciaLaborals = new ArrayList<ExperienciaLaboralValidacionDto>(0);

	public NivelJerarquicoValidacionDto() {
	}

	public Long getIdNivelJerarquico() {
		return this.idNivelJerarquico;
	}

	public void setIdNivelJerarquico(Long idNivelJerarquico) {
		this.idNivelJerarquico = idNivelJerarquico;
	}


	public List<ExperienciaLaboralValidacionDto> getExperienciaLaborals() {
		return this.experienciaLaborals;
	}

	public void setExperienciaLaborals(
			List<ExperienciaLaboralValidacionDto> experienciaLaborals) {
		this.experienciaLaborals = experienciaLaborals;
	}
}
