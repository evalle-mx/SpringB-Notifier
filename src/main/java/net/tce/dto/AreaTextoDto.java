package net.tce.dto;

/**
 * 
 * @author Osy
 *
 */
public class AreaTextoDto extends ComunDto{

	private String idAreaTexto;
	private String idArea;
	private String texto;
	private String ponderacion;

	public AreaTextoDto(){}
	
	public String getIdAreaTexto() {
		return idAreaTexto;
	}

	public void setIdAreaTexto(String idAreaTexto) {
		this.idAreaTexto = idAreaTexto;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}

	
	
}
