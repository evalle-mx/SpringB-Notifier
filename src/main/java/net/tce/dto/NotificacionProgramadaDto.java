package net.tce.dto;

public class NotificacionProgramadaDto extends ComunDto{

	private String json;
	private String descripcion;
	private String claveEvento;

	public NotificacionProgramadaDto(){}
	
	public NotificacionProgramadaDto(String claveEvento,String json,String descripcion){
		this.claveEvento=claveEvento;
		this.json=json;
		this.descripcion=descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getClaveEvento() {
		return claveEvento;
	}
	public void setClaveEvento(String claveEvento) {
		this.claveEvento = claveEvento;
	}
	
	
}
