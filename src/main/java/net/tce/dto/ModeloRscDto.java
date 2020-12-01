package net.tce.dto;

import java.util.Date;
import java.util.List;

public class ModeloRscDto extends ComunDto {

	private String idTracking;

	//Comun
	private String nombre;
	private String descripcion;
	private String orden;
	private String actividad;
	private String dias;
	private String activo;	
	
	//Plantilla_rol
	private String idModeloRsc;
	private String idRol;
	private String principal;

	
	//Tracking_plantilla
	private String idModeloRscFase;
	
	//Tracking_esquema (Posicion)
	private String idModeloRscPosFase;
	private String idModeloRscPos;
	private String idPerfilPosicion;
	private String idModeloRscPosFaseRel;
	private String nombrePosicion;
	private String subirArchivo;
	private String bajarArchivo;
	private String editarComentario;
	private String fechaInicio;
	private String fechaFin;
	private String monitor;

	
	private List<ModeloRscDto> tracking; //Estados
		
	private String idPersona;
	private String idRelacionEmpresaPersona;
	private String idPosicion;
	private String idPerfil;
	private String idCandidato;
	private String idContratante;
	
	
	public ModeloRscDto() {
	}
	/**
	 * Constructor para get en Posicion.read
	 * @param idEmpConf
	 * @param idPosicion
	 */
	public ModeloRscDto(String idEmpConf, String idPosicion) {
		this.setIdEmpresaConf(idEmpConf);
		this.idPosicion=idPosicion;
	}
	/**
	 * Constructor para PLANTILLA_ROL
	 * @param idModeloRsc
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param activo
	 */
	public ModeloRscDto(Long idModeloRsc, String nombre, String descripcion, Boolean activo, Long idRol) {		
		this.idModeloRsc=idModeloRsc!=null?String.valueOf(idModeloRsc):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?"1":"0":"1";
		this.idRol=idRol!=null?String.valueOf(idRol):null;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para PLANTILLA_ROL <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	/**
	 * Constructor ModeloRscDaoImpl.getByModeloRsc
	 * @param idModeloRscFase
	 * @param idModeloRscFaseRel
	 * @param idModeloRsc
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 */
	public ModeloRscDto(Long idModeloRscFase, Long idModeloRsc, 
			Short orden,Short actividad, String nombre, String descripcion) {
		this.idModeloRscFase=idModeloRscFase!=null?String.valueOf(idModeloRscFase):null;
		this.idModeloRsc=idModeloRsc!=null?String.valueOf(idModeloRsc):null;
		this.orden=orden!=null?String.valueOf(orden):null;
		this.actividad=actividad!=null?String.valueOf(actividad):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
	}
	
	
	/**
	 * Constructor para ESQUEMA_PERFIL
	 * @param idModeloRsc
	 * @param idRol
	 * @param nombre
	 * @param descripcion
	 * @param activo
	 */
	public ModeloRscDto(Long idModeloRscPos, Long idPerfilPosicion, Long idPosicion, Long idRol, 
												String nombre, String descripcion, Boolean activo) {		
		this.idModeloRscPos=idModeloRscPos!=null?String.valueOf(idModeloRscPos):null;
		this.idPerfilPosicion=idPerfilPosicion!=null?String.valueOf(idPerfilPosicion):null;
		this.idPosicion=idPosicion!=null?String.valueOf(idPosicion):null;
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?"1":"0":"1";
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para ESQUEMA_PERFIL <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	/**
	 * Constructor para TRACKING_ESQUEMA (Estados-TrackPosicion)
	 * @param idModeloRscPosFase
	 * @param idModeloRscFase
	 * @param idEsquemaPerfil
	 * @param idModeloRscPosFaseRel
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 * @param subirArchivo
	 * @param bajarArchivo
	 * @param editarComentario
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public ModeloRscDto(Long idModeloRscPosFase, Long idModeloRscPosFaseRel, Long idModeloRscPos, Short orden, String nombre, String descripcion, 
			Boolean subirArchivo, Boolean bajarArchivo, Boolean editarComentario, Date fechaInicio, Date fechaFin){
		
		this.idModeloRscPosFase=idModeloRscPosFase!=null?String.valueOf(idModeloRscPosFase):null;
		this.idModeloRscPosFaseRel=idModeloRscPosFaseRel!=null?String.valueOf(idModeloRscPosFaseRel):null;
		this.idModeloRscPos=idModeloRscPos!=null?String.valueOf(idModeloRscPos):null;
		
		this.orden=orden!=null?String.valueOf(orden):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		
		this.subirArchivo=subirArchivo!=null?subirArchivo.booleanValue()?"1":"0":"1";
		this.bajarArchivo=bajarArchivo!=null?bajarArchivo.booleanValue()?"1":"0":"1";
		this.editarComentario=editarComentario!=null?editarComentario.booleanValue()?"1":"0":"1";
		
		this.fechaInicio=fechaInicio!=null?String.valueOf(fechaInicio):null;
		this.fechaFin=fechaFin!=null?String.valueOf(fechaFin):null;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para TRACKING_ESQUEMA <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	
	/**
	 * Constructor para TRACKING_ESQUEMA2 (Estados-TrackPosicion) con fecha en String
	 * @param idModeloRscPosFase
	 * @param idModeloRscFase
	 * @param idEsquemaPerfil
	 * @param idModeloRscPosFaseRel
	 * @param orden
	 * @param nombre
	 * @param descripcion
	 * @param subirArchivo
	 * @param bajarArchivo
	 * @param editarComentario
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public ModeloRscDto(Long idModeloRscPosFase, Long idModeloRscPosFaseRel, Long idModeloRscPos, Short orden, String nombre, String descripcion, 
			Boolean subirArchivo, Boolean bajarArchivo, Boolean editarComentario, String fechaInicio, String fechaFin){
		
		this.idModeloRscPosFase=idModeloRscPosFase!=null?String.valueOf(idModeloRscPosFase):null;
		this.idModeloRscPosFaseRel=idModeloRscPosFaseRel!=null?String.valueOf(idModeloRscPosFaseRel):null;
		this.idModeloRscPos=idModeloRscPos!=null?String.valueOf(idModeloRscPos):null;
		
		this.orden=orden!=null?String.valueOf(orden):null;
		this.nombre=nombre;
		this.descripcion=descripcion;
		
		this.subirArchivo=subirArchivo!=null?subirArchivo.booleanValue()?"1":"0":"1";
		this.bajarArchivo=bajarArchivo!=null?bajarArchivo.booleanValue()?"1":"0":"1";
		this.editarComentario=editarComentario!=null?editarComentario.booleanValue()?"1":"0":"1";
		
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
//		this.setStatus("\n\n >>>>>>>>>>>   Constructor para TRACKING_ESQUEMA <<<<<<<<<<<<<<<<<<<<<<\n\n");
	}
	
	/*public TrackingDto(Long idTracking, Long orden, Long idTrackRef, Long idPerPos, Long idRol, 
			Boolean pUpload, Boolean pDownload, String fechaInicio, String fechaFin, Boolean pComentario,
			String descripcion, Boolean activo) {
		this.idTracking=idTracking!=null?String.valueOf(idTracking):null;
		this.orden=orden!=null?String.valueOf(orden):null;
		this.idTrackReferencia=idTrackRef!=null?String.valueOf(idTrackRef):null;
		this.idPerfilPosicion=idPerPos!=null?String.valueOf(idPerPos):null;
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.subirArchivo=pUpload!=null?pUpload.booleanValue()?"1":"0":"0";
		this.bajarArchivo=pDownload!=null?pDownload.booleanValue()?"1":"0":"0";
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.editarComentario=pComentario!=null?pComentario.booleanValue()?"1":"0":"0";
		this.descripcion=descripcion;
		this.activo=activo!=null?activo.booleanValue()?"1":"0":"1";
	}*/
	
	public String getIdTracking() {
		return idTracking;
	}
	public void setIdTracking(String idTracking) {
		this.idTracking = idTracking;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getIdModeloRsc() {
		return idModeloRsc;
	}
	public void setIdModeloRsc(String idModeloRsc) {
		this.idModeloRsc = idModeloRsc;
	}
	public String getIdRol() {
		return idRol;
	}
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	public String getIdModeloRscFase() {
		return idModeloRscFase;
	}
	public void setIdModeloRscFase(String idModeloRscFase) {
		this.idModeloRscFase = idModeloRscFase;
	}
//	public String getIdModeloRscFaseRel() {
//		return idModeloRscFaseRel;
//	}
//	public void setIdModeloRscFaseRel(String idModeloRscFaseRel) {
//		this.idModeloRscFaseRel = idModeloRscFaseRel;
//	}
	public String getIdModeloRscPosFase() {
		return idModeloRscPosFase;
	}
	public void setIdModeloRscPosFase(String idModeloRscPosFase) {
		this.idModeloRscPosFase = idModeloRscPosFase;
	}
	public String getIdModeloRscPos() {
		return idModeloRscPos;
	}
	public void setIdModeloRscPos(String idModeloRscPos) {
		this.idModeloRscPos = idModeloRscPos;
	}
	public String getIdPerfilPosicion() {
		return idPerfilPosicion;
	}
	public void setIdPerfilPosicion(String idPerfilPosicion) {
		this.idPerfilPosicion = idPerfilPosicion;
	}
	public String getIdModeloRscPosFaseRel() {
		return idModeloRscPosFaseRel;
	}
	public void setIdModeloRscPosFaseRel(String idModeloRscPosFaseRel) {
		this.idModeloRscPosFaseRel = idModeloRscPosFaseRel;
	}
	public String getSubirArchivo() {
		return subirArchivo;
	}
	public void setSubirArchivo(String subirArchivo) {
		this.subirArchivo = subirArchivo;
	}
	public String getBajarArchivo() {
		return bajarArchivo;
	}
	public void setBajarArchivo(String bajarArchivo) {
		this.bajarArchivo = bajarArchivo;
	}
	public String getEditarComentario() {
		return editarComentario;
	}
	public void setEditarComentario(String editarComentario) {
		this.editarComentario = editarComentario;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(String idCandidato) {
		this.idCandidato = idCandidato;
	}
	public String getIdContratante() {
		return idContratante;
	}
	public void setIdContratante(String idContratante) {
		this.idContratante = idContratante;
	}
	public List<ModeloRscDto> getTracking() {
		return tracking;
	}
	public void setTracking(List<ModeloRscDto> tracking) {
		this.tracking = tracking;
	}
	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
	public String getNombrePosicion() {
		return nombrePosicion;
	}
	public void setNombrePosicion(String nombrePosicion) {
		this.nombrePosicion = nombrePosicion;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	public String getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(String idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	
	
}
