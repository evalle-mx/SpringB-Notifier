package net.tce.dto;

import java.util.Date;

public class ModeloRscPosFaseDto {

	private Long idMonitor;
	private Long idModeloRscPosFase;
	private Long idModeloRscPosFasePost;
	private Long idModeloRscPosFaseRel;
	private Long idTrackingPostulante;
	private Long idPosibleCandidato;
	private Long idCandidato;
	private Long idRelacionEmpresaPersona;
	private Long idEstadoTracking;
	private String comentario;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private Short orden;
	private Short actividad;
	private Long contador;
	
	private BtcTrackingMonPosDto btcTrackingMonPosDto;

	public ModeloRscPosFaseDto(){}
	
	public ModeloRscPosFaseDto(Long idModeloRscPosFase){
		this.idModeloRscPosFase=idModeloRscPosFase;
	}
	
	public ModeloRscPosFaseDto(Long contador, long idModeloRscPosFase){
		this.setContador(contador);
		this.idModeloRscPosFase=idModeloRscPosFase;
	}
	public ModeloRscPosFaseDto(Long idModeloRscPosFase, Short orden){
		this.idModeloRscPosFase=idModeloRscPosFase;
		this.orden=orden;
	}
	
	/**
	 * Constructor para CandidatoDao.get()
	 * @param idRelacionEmpresaPersona
	 * @param idPosibleCandidato
	 * @param idCandidato
	 */
	public ModeloRscPosFaseDto(Long idRelacionEmpresaPersona, Long idPosibleCandidato,Long idCandidato){
		this.idRelacionEmpresaPersona=idRelacionEmpresaPersona;
		this.idPosibleCandidato=idPosibleCandidato;
		this.idCandidato=idCandidato;
	}
	
	/**
	 *  Constructor para modeloRscPosDao.getIdsModeloRscPosFases()
	 * @param idModeloRscPosFase
	 * @param nombre
	 * @param fechaInicio
	 * @param fechaFin
	 * @param orden
	 * @param actividad
	 */
	public ModeloRscPosFaseDto(Long idModeloRscPosFase,String nombre, Date fechaInicio,Date fechaFin,
																			Short orden, Short actividad){
		this.idModeloRscPosFase=idModeloRscPosFase;
		this.nombre=nombre;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.orden=orden;
		this.actividad=actividad;
	}
	
	/**
	 *  Constructor para modeloRscPosDao.readModeloRscPosFase()
	 * @param idModeloRscPosFase
	 * @param nombre
	 * @param fechaInicio
	 * @param fechaFin
	 * @param orden
	 * @param actividad
	 */
	public ModeloRscPosFaseDto(Long idModeloRscPosFase,Long idModeloRscPosFaseRel,String nombre, 
									Date fechaInicio,Date fechaFin,Short orden, Short actividad){
		this.idModeloRscPosFase=idModeloRscPosFase;
		this.idModeloRscPosFaseRel=idModeloRscPosFaseRel;
		this.nombre=nombre;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.orden=orden;
		this.actividad=actividad;
	}
	
	/**
	 * Constructor para trackingMonitorDaoImpl.get()
	 * @param idModeloRscPosFase
	 * @param idTrackingPostulante
	 * @param idRelacionEmpresaPersona
	 * @param idEstadoTracking
	 * @param comentario
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public ModeloRscPosFaseDto(Long idModeloRscPosFase,Long idTrackingPostulante,Long idRelacionEmpresaPersona,
			Long idEstadoTracking,String comentario,Date fechaInicio,Date fechaFin, Long idPosibleCandidato,
			Long idCandidato, Long idModeloRscPosFasePost){
		this.idModeloRscPosFase=idModeloRscPosFase;
		this.idTrackingPostulante=idTrackingPostulante;
		this.idRelacionEmpresaPersona=idRelacionEmpresaPersona;
		this.idEstadoTracking=idEstadoTracking;
		this.comentario=comentario;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.idPosibleCandidato=idPosibleCandidato;
		this.idCandidato=idCandidato;
		this.idModeloRscPosFasePost=idModeloRscPosFasePost;
	}
	
	
	
	public Long getIdTrackingPostulante() {
		return idTrackingPostulante;
	}
	public void setIdTrackingPostulante(Long idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}
	public Long getIdModeloRscPosFase() {
		return idModeloRscPosFase;
	}
	public void setIdModeloRscPosFase(Long idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}
	
	public Long getIdEstadoTracking() {
		return idEstadoTracking;
	}

	public void setIdEstadoTracking(Long idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}
	
	public Long getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(Long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Long getIdMonitor() {
		return idMonitor;
	}
	public void setIdMonitor(Long idMonitor) {
		this.idMonitor = idMonitor;
	}

	public Short getOrden() {
		return orden;
	}

	public void setOrden(Short orden) {
		this.orden = orden;
	}

	public Long getContador() {
		return contador;
	}

	public void setContador(Long contador) {
		this.contador = contador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Short getActividad() {
		return actividad;
	}

	public void setActividad(Short actividad) {
		this.actividad = actividad;
	}

	public BtcTrackingMonPosDto getBtcTrackingMonPosDto() {
		return btcTrackingMonPosDto;
	}

	public void setBtcTrackingMonPosDto(BtcTrackingMonPosDto btcTrackingMonPosDto) {
		this.btcTrackingMonPosDto = btcTrackingMonPosDto;
	}

	public Long getIdPosibleCandidato() {
		return idPosibleCandidato;
	}

	public void setIdPosibleCandidato(Long idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}

	public Long getIdCandidato() {
		return idCandidato;
	}

	public void setIdCandidato(Long idCandidato) {
		this.idCandidato = idCandidato;
	}

	public Long getIdModeloRscPosFasePost() {
		return idModeloRscPosFasePost;
	}

	public void setIdModeloRscPosFasePost(Long idModeloRscPosFasePost) {
		this.idModeloRscPosFasePost = idModeloRscPosFasePost;
	}

	public Long getIdModeloRscPosFaseRel() {
		return idModeloRscPosFaseRel;
	}

	public void setIdModeloRscPosFaseRel(Long idModeloRscPosFaseRel) {
		this.idModeloRscPosFaseRel = idModeloRscPosFaseRel;
	}

	
	
}
