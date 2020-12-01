package net.tce.dto;

import java.util.Date;

public class RecordatorioDto extends ComunDto {

	private String idRecordatorio;
	private Long idRelacionEmpresaPersona;
	private String descripcion;
	private String activo;
	private String vigente;
	private Long idMonitor;
	private Long idPersona;
	private Long idPosicion;
	private Long idTrackingPostulante;
	private Long idTrackingMonitor;
	private String nombreMonitor;
	private String nombrePosicion;
	private String nombreFase;
	private Date fechaInicio;
	private Date fechaFinal;
	private	Boolean enGrupo;
	
	
	public RecordatorioDto(){}
	
	/**
	 * Constructor para RecordatorioDaoImpl.getByDateMonitor()
	 * @param idRecordatorio
	 * @param idMonitor
	 * @param idTrackingPostulante
	 * @param idPersona
	 * @param nombrePosicion
	 * @param nombreFase
	 * @param fechaInicio
	 * @param nombreMonitor
	 */
	public RecordatorioDto(Long idRecordatorio, Long idMonitor, Long idTrackingMonitor,
						   Long idTrackingPostulante,Long idPersona,Long idPosicion,
						   String nombrePosicion, String nombreFase,Date fechaInicio,
						   Date fechaFin, String nombreMonitor,Long idRelacionEmpresaPersona){
		this.idRecordatorio=idRecordatorio.toString();
		this.idMonitor=idMonitor;
		this.idTrackingPostulante=idTrackingPostulante;
		this.idPersona=idPersona;
		this.nombreMonitor=nombreMonitor;
		this.idPosicion=idPosicion;
		this.nombrePosicion=nombrePosicion;
		this.nombreFase=nombreFase;
		this.fechaInicio=fechaInicio;
		this.fechaFinal=fechaFin;
		this.setIdRelacionEmpresaPersona(idRelacionEmpresaPersona);
		this.idTrackingMonitor=idTrackingMonitor;
	}

	/**
	 * Constructor para RecordatorioDaoImpl.getByDatePostulante()
	 * @param idRecordatorio
	 * @param idPersona
	 * @param nombrePosicion
	 * @param nombreFase
	 * @param fechaInicio
	 * @param nombreMonitor
	 */
	public RecordatorioDto(Long idRecordatorio,  Long idPersona,Long idPosicion,
							String nombrePosicion,String nombreFase,Date fechaInicio,  
					 		Boolean enGrupo,String nombreMonitor,Long idRelacionEmpresaPersona ){
		this.idRecordatorio=idRecordatorio.toString();
		this.idPersona=idPersona;
		this.nombreMonitor=nombreMonitor;
		this.idPosicion=idPosicion;
		this.nombrePosicion=nombrePosicion;
		this.nombreFase=nombreFase;
		this.fechaInicio=fechaInicio;
		this.enGrupo=enGrupo;
		this.setIdRelacionEmpresaPersona(idRelacionEmpresaPersona);
	}

	
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getIdRecordatorio() {
		return idRecordatorio;
	}
	public void setIdRecordatorio(String idRecordatorio) {
		this.idRecordatorio = idRecordatorio;
	}
	public String getVigente() {
		return vigente;
	}
	public void setVigente(String vigente) {
		this.vigente = vigente;
	}

	public Long getIdMonitor() {
		return idMonitor;
	}

	public void setIdMonitor(Long idMonitor) {
		this.idMonitor = idMonitor;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombreMonitor() {
		return nombreMonitor;
	}

	public void setNombreMonitor(String nombreMonitor) {
		this.nombreMonitor = nombreMonitor;
	}

	public String getNombrePosicion() {
		return nombrePosicion;
	}

	public void setNombrePosicion(String nombrePosicion) {
		this.nombrePosicion = nombrePosicion;
	}

	public String getNombreFase() {
		return nombreFase;
	}

	public void setNombreFase(String nombreFase) {
		this.nombreFase = nombreFase;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Long getIdTrackingPostulante() {
		return idTrackingPostulante;
	}


	public void setIdTrackingPostulante(Long idTrackingPostulante) {
		this.idTrackingPostulante = idTrackingPostulante;
	}

	public Boolean getEnGrupo() {
		return enGrupo;
	}

	public void setEnGrupo(Boolean enGrupo) {
		this.enGrupo = enGrupo;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Long getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}

	public void setIdRelacionEmpresaPersona(Long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}

	public Long getIdTrackingMonitor() {
		return idTrackingMonitor;
	}

	public void setIdTrackingMonitor(Long idTrackingMonitor) {
		this.idTrackingMonitor = idTrackingMonitor;
	}

	public Long getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}

	
}
