package net.tce.dto;

import java.util.HashSet;
import java.util.Set;


public class TipoContactoValidacionDto {
	private Long idTipoContacto;
	private Set<ContactoValidacionDto> contactos = new HashSet<ContactoValidacionDto>(0);

	public Long getIdTipoContacto() {
		return this.idTipoContacto;
	}

	public void setIdTipoContacto(Long idTipoContacto) {
		this.idTipoContacto = idTipoContacto;
	}

	public Set<ContactoValidacionDto> getContactos() {
		return this.contactos;
	}

	public void setContactos(Set<ContactoValidacionDto> contactos) {
		this.contactos = contactos;
	}
}
