package net.tce.dto;

public class ContenidoValidacionDto {
	private Long idContenido;
	private String descripcion;

	public ContenidoValidacionDto() {
	}

	public Long getIdContenido() {
		return this.idContenido;
	}

	public void setIdContenido(Long idContenido) {
		this.idContenido = idContenido;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
