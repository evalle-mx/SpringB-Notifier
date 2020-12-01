package net.tce.dto;

public class PersonaPaisValidacionDto {
	private Long idPersonaPais;
	private PaisDto pais;

	public PersonaPaisValidacionDto() {
	}

	public Long getIdPersonaPais() {
		return this.idPersonaPais;
	}

	public void setIdPersonaPais(Long idPersonaPais) {
		this.idPersonaPais = idPersonaPais;
	}

	public PaisDto getPais() {
		return this.pais;
	}

	public void setPais(PaisDto pais) {
		this.pais = pais;
	}

}
