package net.tce.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CandidatoRechazoDto extends ComunDto{
	
	private String idMotivoRechazo;
	private String otro;
	private String idCandidato;
	
	public CandidatoRechazoDto() {
	}
	
	public String getIdMotivoRechazo() {
		return idMotivoRechazo;
	}

	public void setIdMotivoRechazo(String idMotivoRechazo) {
		this.idMotivoRechazo = idMotivoRechazo;
	}

	public String getOtro() {
		return otro;
	}

	public void setOtro(String otro) {
		this.otro = otro;
	}

	public String getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(String idCandidato) {
		this.idCandidato = idCandidato;
	}
	
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}