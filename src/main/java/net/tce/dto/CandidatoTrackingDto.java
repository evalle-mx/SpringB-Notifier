package net.tce.dto;

import java.util.List;

public class CandidatoTrackingDto extends ComunDto{

	private String idCandidato;
	private String idPostulante;
	private String idPersona;
	private String idModeloRscPos;
	private String idRelacionEmpresaPersona;
	private String idPerfilPosicion;
	private String idPosibleCandidato;
	private String ordenTrackDefaultCumplido;
	private String idTipoPosibleCandidato;
	private String comentario;
	private String email;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String orden;
	private String tipoPostulante;
	private String edad;
	private boolean error;
	private String confirmado;
	private String pwsCad;
	private String activo;
	
	private List<ModeloRscPosFaseDto> lsModeloRscPosFaseDto;
	private Long diasExperienciaLaboral;
	private Long salarioMinPer;
	private Short anioNacimiento;
	private Byte mesNacimiento;
	//private Integer edad;
	private String gradoAcademico;
	private String estatusEscolar;
	private String titulo;
	
	public String getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(String idCandidato) {
		this.idCandidato = idCandidato;
	}
	
	public String getIdModeloRscPos() {
		return idModeloRscPos;
	}
	public void setIdModeloRscPos(String idModeloRscPos) {
		this.idModeloRscPos = idModeloRscPos;
	}
	public String getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(String idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	public String getIdPerfilPosicion() {
		return idPerfilPosicion;
	}
	public void setIdPerfilPosicion(String idPerfilPosicion) {
		this.idPerfilPosicion = idPerfilPosicion;
	}
	public List<ModeloRscPosFaseDto> getLsModeloRscPosFaseDto() {
		return lsModeloRscPosFaseDto;
	}
	public void setLsModeloRscPosFaseDto(List<ModeloRscPosFaseDto> lsModeloRscPosFaseDto) {
		this.lsModeloRscPosFaseDto = lsModeloRscPosFaseDto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getIdPosibleCandidato() {
		return idPosibleCandidato;
	}
	public void setIdPosibleCandidato(String idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getTipoPostulante() {
		return tipoPostulante;
	}
	public void setTipoPostulante(String tipoPostulante) {
		this.tipoPostulante = tipoPostulante;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPostulante() {
		return idPostulante;
	}
	public void setIdPostulante(String idPostulante) {
		this.idPostulante = idPostulante;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getConfirmado() {
		return confirmado;
	}
	public void setConfirmado(String confirmado) {
		this.confirmado = confirmado;
	}
	public String getPwsCad() {
		return pwsCad;
	}
	public void setPwsCad(String pwsCad) {
		this.pwsCad = pwsCad;
	}
	public String getOrdenTrackDefaultCumplido() {
		return ordenTrackDefaultCumplido;
	}
	public void setOrdenTrackDefaultCumplido(String ordenTrackDefaultCumplido) {
		this.ordenTrackDefaultCumplido = ordenTrackDefaultCumplido;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public Long getDiasExperienciaLaboral() {
		return diasExperienciaLaboral;
	}
	public void setDiasExperienciaLaboral(Long diasExperienciaLaboral) {
		this.diasExperienciaLaboral = diasExperienciaLaboral;
	}
	public Long getSalarioMinPer() {
		return salarioMinPer;
	}
	public void setSalarioMinPer(Long salarioMinPer) {
		this.salarioMinPer = salarioMinPer;
	}
	public Short getAnioNacimiento() {
		return anioNacimiento;
	}
	public void setAnioNacimiento(Short anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}
	public Byte getMesNacimiento() {
		return mesNacimiento;
	}
	public void setMesNacimiento(Byte mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}
	public String getGradoAcademico() {
		return gradoAcademico;
	}
	public void setGradoAcademico(String gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}
	public String getEstatusEscolar() {
		return estatusEscolar;
	}
	public void setEstatusEscolar(String estatusEscolar) {
		this.estatusEscolar = estatusEscolar;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getIdTipoPosibleCandidato() {
		return idTipoPosibleCandidato;
	}
	public void setIdTipoPosibleCandidato(String idTipoPosibleCandidato) {
		this.idTipoPosibleCandidato = idTipoPosibleCandidato;
	}
	
}
