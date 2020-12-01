package net.tce.dto;

import java.util.ArrayList;
import java.util.List;


public class GradoAcademicoValidacionDto {
	private Long idGradoAcademico;
	private String descripcion;
	private byte nivel;
	private short dominio;
	private int degradacion;
	private int duracion;
	private boolean estatusRegistro;
	private List<PersonaValidacionDto> personas = new ArrayList<PersonaValidacionDto>(0);
	private List<EscolaridadValidacionDto> escolaridads = new ArrayList<EscolaridadValidacionDto>(0);

	public GradoAcademicoValidacionDto() {
	}

	public Long getIdGradoAcademico() {
		return this.idGradoAcademico;
	}

	public void setIdGradoAcademico(Long idGradoAcademico) {
		this.idGradoAcademico = idGradoAcademico;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte getNivel() {
		return this.nivel;
	}

	public void setNivel(byte nivel) {
		this.nivel = nivel;
	}

	public short getDominio() {
		return this.dominio;
	}

	public void setDominio(short dominio) {
		this.dominio = dominio;
	}

	public int getDegradacion() {
		return this.degradacion;
	}

	public void setDegradacion(int degradacion) {
		this.degradacion = degradacion;
	}

	public int getDuracion() {
		return this.duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public boolean isEstatusRegistro() {
		return this.estatusRegistro;
	}

	public void setEstatusRegistro(boolean estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	public List<PersonaValidacionDto> getPersonas() {
		return this.personas;
	}

	public void setPersonas(List<PersonaValidacionDto> personas) {
		this.personas = personas;
	}

	public List<EscolaridadValidacionDto> getEscolaridads() {
		return this.escolaridads;
	}

	public void setEscolaridads(List<EscolaridadValidacionDto> escolaridads) {
		this.escolaridads = escolaridads;
	}


}
