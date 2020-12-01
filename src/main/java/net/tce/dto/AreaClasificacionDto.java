package net.tce.dto;

public class AreaClasificacionDto {
	
	private String idArea;
	private String descripcion;
	private String idAreaRel;
	private String nivel;
	
	public AreaClasificacionDto() {
	}
	
	public AreaClasificacionDto(String idArea, String descripcion, String idAreaRel) {
		this.idArea=idArea;
		this.descripcion=descripcion;
		this.idAreaRel=idAreaRel;
	}
	/*  para futuras mejoras en el HQL */
	public AreaClasificacionDto(String idArea, String descripcion, String idAreaRel, String nivel) {
		this.idArea=idArea;
		this.descripcion=descripcion;
		this.idAreaRel=idAreaRel;
		this.nivel=nivel;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdAreaRel() {
		return idAreaRel;
	}

	public void setIdAreaRel(String idAreaRel) {
		this.idAreaRel = idAreaRel;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String toJson(){
		return "{"
				.concat(this.idArea!=null?"\"id\":\"".concat(this.idArea).concat("\","):"")
				.concat(this.descripcion!=null?"\"nombre\":\"".concat(this.descripcion).concat("\""):"")
				.concat("}");
	}
	
}
