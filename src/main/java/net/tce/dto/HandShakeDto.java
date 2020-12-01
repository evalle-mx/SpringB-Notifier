package net.tce.dto;

import java.util.List;


public class HandShakeDto extends ComunDto{

	private String idCandidato;
	private String claveEvento;;
	private List<CandidatoRechazoDto> lsMotivoRechazo;
	private String textoReinvitar;
	private String idEstatusOperativo;
	
	public HandShakeDto() {
		
	}
	
	public String getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(String idCandidato) {
		this.idCandidato = idCandidato;
	}
	public String getClaveEvento() {
		return claveEvento;
	}
	public void setClaveEvento(String claveEvento) {
		this.claveEvento = claveEvento;
	}
	
	public String getTextoReinvitar() {
		return textoReinvitar;
	}
	public void setTextoReinvitar(String textoReinvitar) {
		this.textoReinvitar = textoReinvitar;
	}

	public List<CandidatoRechazoDto> getLsMotivoRechazo() {
		return lsMotivoRechazo;
	}

	public void setLsMotivoRechazo(List<CandidatoRechazoDto> lsMotivoRechazo) {
		this.lsMotivoRechazo = lsMotivoRechazo;
	}

	public String getIdEstatusOperativo() {
		return idEstatusOperativo;
	}

	public void setIdEstatusOperativo(String idEstatusOperativo) {
		this.idEstatusOperativo = idEstatusOperativo;
	}

	
}
