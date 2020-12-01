package net.tce.dto;

import java.util.Date;

public class NotificacionProgramadaDto extends ComunDto {

	private String idNotificacionProgramada;
	private String fechaCreacion;
	private String json;
	private String descripcion;
	private String intentos;
	private String enviada;
	//Tabla tipo Evento:
	private String idTipoEvento;
	private String texto;
	private String prioridad;
	private String claveEvento;
	
	private String tipoNotificacion; //Booleano que indica tipo: 0=>notificacion 1=>NotificacioNProgramada (Request)

	public NotificacionProgramadaDto(){}
	
	/* Constructor simple para envio remoto */
	public NotificacionProgramadaDto(String claveEvento,String json,String descripcion){
		this.claveEvento=claveEvento;
		this.json=json;
		this.descripcion=descripcion;
	}

	/* Constructor completo para busqueda en DAO */
	public NotificacionProgramadaDto(Long idNotificacionProgramada, Long idTipoEvento, String claveEvento, 
			Integer prioridad, String texto, Date fechaCreacion, String json, Short intentos, Boolean enviada){
		
		this.idNotificacionProgramada = idNotificacionProgramada == null ? null:String.valueOf(idNotificacionProgramada);
		this.idTipoEvento = idTipoEvento == null ? null:String.valueOf(idTipoEvento);
		this.claveEvento=claveEvento;
		this.prioridad = prioridad == null ? null:String.valueOf(prioridad);
		this.texto= texto;
		this.fechaCreacion= fechaCreacion!=null?String.valueOf(fechaCreacion):null;
		this.json=json;
		this.intentos = intentos == null ? null:String.valueOf(intentos);
		this.enviada=enviada == null ? null:(String.valueOf(enviada ? "1":"0"));
	}

	public String getIdNotificacionProgramada() {
		return idNotificacionProgramada;
	}

	public void setIdNotificacionProgramada(String idNotificacionProgramada) {
		this.idNotificacionProgramada = idNotificacionProgramada;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIntentos() {
		return intentos;
	}

	public void setIntentos(String intentos) {
		this.intentos = intentos;
	}

	public String getEnviada() {
		return enviada;
	}

	public void setEnviada(String enviada) {
		this.enviada = enviada;
	}

	public String getIdTipoEvento() {
		return idTipoEvento;
	}

	public void setIdTipoEvento(String idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getClaveEvento() {
		return claveEvento;
	}

	public void setClaveEvento(String claveEvento) {
		this.claveEvento = claveEvento;
	}

	public String getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(String tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}
	
}
