package net.tce.dto;


public class ContactoValidacionDto {
	private Long idContacto;
	private TipoContactoValidacionDto tipoContacto;
	private ReferenciaValidacionDto referencia;
	private PersonaValidacionDto persona;
	private String contacto;
	private ContactoTelefonoValidacionDto contactoTelefono;

	public Long getIdContacto() {
		return this.idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

	public TipoContactoValidacionDto getTipoContacto() {
		return this.tipoContacto;
	}

	public void setTipoContacto(TipoContactoValidacionDto tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public ReferenciaValidacionDto getReferencia() {
		return this.referencia;
	}

	public void setReferencia(ReferenciaValidacionDto referencia) {
		this.referencia = referencia;
	}

	public PersonaValidacionDto getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaValidacionDto persona) {
		this.persona = persona;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public ContactoTelefonoValidacionDto getContactoTelefono() {
		return this.contactoTelefono;
	}

	public void setContactoTelefono(ContactoTelefonoValidacionDto contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}
}
