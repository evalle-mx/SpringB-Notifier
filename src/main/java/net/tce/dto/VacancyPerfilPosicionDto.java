package net.tce.dto;


import java.util.List;


public class VacancyPerfilPosicionDto extends ComunDto {

	private String ponderacion;
	
    /* Datos de perfil */
	private String idPerfil;
	private String nombre;
	private String descripcion;
	private String objetivo;
	
//	private Set<VacancyPerfilTextoNgramDto> textos = new HashSet<VacancyPerfilTextoNgramDto>(0);
	private List<VacancyPerfilTextoNgramDto> textos; 
	
	public VacancyPerfilPosicionDto() {
	}

	public VacancyPerfilPosicionDto(String idPerfil) {
		this.idPerfil = idPerfil;
	}


	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}
		
	public String getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public List<VacancyPerfilTextoNgramDto> getTextos() {
		return textos;
	}

	public void setTextos(List<VacancyPerfilTextoNgramDto> textos) {
		this.textos = textos;
	}

//	public Set<VacancyPerfilTextoNgramDto> getTextos() {
//		return textos;
//	}
//
//	public void setTextos(Set<VacancyPerfilTextoNgramDto> textos) {
//		this.textos = textos;
//	}
	
}
