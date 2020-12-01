package net.tce.dto;


public class ContactoTelefonoValidacionDto {
	private Long idContacto;
	private ContactoValidacionDto contacto;
	private String prefijo;
	private String codigoPais;
	private String codigoArea;
	private String numero;
	private String adicional;

	public Long getIdContacto() {
		return this.idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

	public ContactoValidacionDto getContacto() {
		return this.contacto;
	}

	public void setContacto(ContactoValidacionDto contacto) {
		this.contacto = contacto;
	}

	public String getPrefijo() {
		return this.prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public String getCodigoPais() {
		return this.codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public String getCodigoArea() {
		return this.codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAdicional() {
		return this.adicional;
	}

	public void setAdicional(String adicional) {
		this.adicional = adicional;
	}
}
