package net.tce.dto;


public class PaisDto implements Comparable<Object> {
	private Long idPais;
	private String nombre;
	private float sortWeigh;

	public PaisDto() {
	}
	
	public PaisDto(Long idPais,String nombre) {
		this.idPais = idPais;
		this.nombre = nombre;
	}

	public Long getIdPais() {
		return this.idPais;
	}

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}

	@Override
	public int compareTo(Object o) {
		PaisDto pais=(PaisDto)o;
		if(this.sortWeigh > pais.sortWeigh){
			return -1;
		}else if(this.sortWeigh < pais.sortWeigh){
			return 1;
		}else{
			return 0;
		}
	}
	

	public float getSortWeigh() {
		return sortWeigh;
	}

	public void setSortWeigh(float sortWeigh) {
		this.sortWeigh = sortWeigh;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
