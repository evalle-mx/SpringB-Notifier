package net.tce.dto;

import java.util.Date;

public class NotificacionDto {
	
	private Long idPersona;
	private String email;
	private Date fechaCreacion;
	private Date fechaActual;
	private Date fechaDelTipo;
	private Short numeroDelTipo;
	
	private String idPivote;
	private String claveEvento;
	private String comentario;



	public NotificacionDto(){}
	
	public NotificacionDto(String idPivote,String claveEvento, String comentario){
		this.setIdPivote(idPivote);
		this.setClaveEvento(claveEvento);
		this.setComentario(comentario);
	}
	
	//Se usa en: PersonaDaoImpl.getReminder()
	public NotificacionDto(Long idPersona,Date fechaCreacion,String email,Date fechaDelTipo,Short numeroDelTipo){
		this.setIdPersona(idPersona);
		this.setFechaCreacion(fechaCreacion);
		this.email=email;
		this.setFechaDelTipo(fechaDelTipo);
		this.setNumeroDelTipo(numeroDelTipo);
	}

	

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	
	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaDelTipo() {
		return fechaDelTipo;
	}

	public void setFechaDelTipo(Date fechaDelTipo) {
		this.fechaDelTipo = fechaDelTipo;
	}

	public Short getNumeroDelTipo() {
		return numeroDelTipo;
	}

	public void setNumeroDelTipo(Short numeroDelTipo) {
		this.numeroDelTipo = numeroDelTipo;
	}

	public String getClaveEvento() {
		return claveEvento;
	}

	public void setClaveEvento(String claveEvento) {
		this.claveEvento = claveEvento;
	}

	public String getIdPivote() {
		return idPivote;
	}

	public void setIdPivote(String idPivote) {
		this.idPivote = idPivote;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	

}
