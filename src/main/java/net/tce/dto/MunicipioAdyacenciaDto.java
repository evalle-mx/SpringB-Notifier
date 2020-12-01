package net.tce.dto;


public class MunicipioAdyacenciaDto {

	private Long idMunicipio;
	private Long idMunicipioAdyacente;
	
	
	public MunicipioAdyacenciaDto(){}
	
	public MunicipioAdyacenciaDto(Long idMunicipio, Long idMunicipioAdyacente){
				this.setIdMunicipio(idMunicipio);
				this.idMunicipioAdyacente = idMunicipioAdyacente;

	}
	
	public void setIdMunicipioAdyacente(Long idMunicipioAdyacente) {
		this.idMunicipioAdyacente = idMunicipioAdyacente;
	}
	public Long getIdMunicipioAdyacente() {
		return idMunicipioAdyacente;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
}
