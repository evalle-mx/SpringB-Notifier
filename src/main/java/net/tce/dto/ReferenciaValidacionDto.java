package net.tce.dto;

import java.util.HashSet;
import java.util.Set;

public class ReferenciaValidacionDto {

	private Long idReferencia;
	private TipoReferenciaValidacionDto tipoReferencia;
	private String nombre;
	private String apellido;
	private Set<ContactoValidacionDto> contactos = new HashSet<ContactoValidacionDto>(0);

	public Long getIdReferencia() {
		return this.idReferencia;
	}

	public void setIdReferencia(Long idReferencia) {
		this.idReferencia = idReferencia;
	}

	public TipoReferenciaValidacionDto getTipoReferencia() {
		return this.tipoReferencia;
	}

	public void setTipoReferencia(TipoReferenciaValidacionDto tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Set<ContactoValidacionDto> getContactos() {
		return this.contactos;
	}

	public void setContactos(Set<ContactoValidacionDto> contactos) {
		this.contactos = contactos;
	}

}
