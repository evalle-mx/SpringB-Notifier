package net.tce.dto;

public class ContactPhoneDto  extends ComunDto {
	private String prefijo;
	private String codigoPais;
	private String codigoArea;
	private String numero;
	private String adicional;
	private String idContacto;
	
	
	
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}
	public String getCodigoPais() {
		return codigoPais;
	}
	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}
	public String getCodigoArea() {
		return codigoArea;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNumero() {
		return numero;
	}
	public void setAdicional(String adicional) {
		this.adicional = adicional;
	}
	public String getAdicional() {
		return adicional;
	}
	public void setIdContacto(String idContacto) {
		this.idContacto = idContacto;
	}
	public String getIdContacto() {
		return idContacto;
	}
	
}
