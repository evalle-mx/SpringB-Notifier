package net.tce.dto;

import java.util.List;
import java.util.ArrayList;


public class PerfilValidacionDto {

	private Long idPerfil;
	private double ponderacion;
	private long idTipoPerfil;
	private List<PerfilTextoNgramValidacionDto> perfilTextoNgrams = new ArrayList<PerfilTextoNgramValidacionDto>(0);
	
	public PerfilValidacionDto(){}
	
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	
	public void setPonderacion(double ponderacion) {
		this.ponderacion = ponderacion;
	}
	public double getPonderacion() {
		return ponderacion;
	}
	
	public long getIdTipoPerfil() {
		return idTipoPerfil;
	}
	public void setIdTipoPerfil(long idTipoPerfil) {
		this.idTipoPerfil = idTipoPerfil;
	}

	public List<PerfilTextoNgramValidacionDto> getPerfilTextoNgrams() {
		return perfilTextoNgrams;
	}

	public void setPerfilTextoNgrams(List<PerfilTextoNgramValidacionDto> perfilTextoNgrams) {
		this.perfilTextoNgrams = perfilTextoNgrams;
	}

	
}
