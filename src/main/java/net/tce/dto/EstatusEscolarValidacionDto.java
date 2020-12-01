package net.tce.dto;

import java.util.ArrayList;
import java.util.List;

public class EstatusEscolarValidacionDto {
	private Long idEstatusEscolar;
	private String descripcion;
	private Byte nivel;
	private Byte preponderancia;
	private Boolean estatusRegistro;
	private List<EscolaridadValidacionDto> escolaridads = new ArrayList<EscolaridadValidacionDto>(0);
	private List<PersonaValidacionDto> personas = new ArrayList<PersonaValidacionDto>(0);

	public EstatusEscolarValidacionDto() {
	}

	public Long getIdEstatusEscolar() {
		return this.idEstatusEscolar;
	}

	public void setIdEstatusEscolar(Long idEstatusEscolar) {
		this.idEstatusEscolar = idEstatusEscolar;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Byte getNivel() {
		return this.nivel;
	}

	public void setNivel(Byte nivel) {
		this.nivel = nivel;
	}

	public Byte getPreponderancia() {
		return this.preponderancia;
	}

	public void setPreponderancia(Byte preponderancia) {
		this.preponderancia = preponderancia;
	}

	public Boolean isEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(Boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	public List<EscolaridadValidacionDto> getEscolaridads() {
		return this.escolaridads;
	}

	public void setEscolaridads(List<EscolaridadValidacionDto> escolaridads) {
		this.escolaridads = escolaridads;
	}

	public List<PersonaValidacionDto> getPersonas() {
		return this.personas;
	}

	public void setPersonas(List<PersonaValidacionDto> personas) {
		this.personas = personas;
	}



}
