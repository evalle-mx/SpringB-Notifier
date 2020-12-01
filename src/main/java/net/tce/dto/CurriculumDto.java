package net.tce.dto;

import java.util.List;

/**
 * 
 * @author Goyo
 *
 */
public class CurriculumDto extends ComunDto {
	
	private String idPersona;
	private String idExterno;
	private String idPosicion;
	private String idEmpresa;
	private String email;
	private String password;
	private String passwordIniSistema;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String anioNacimiento;
	private String mesNacimiento;
	private String diaNacimiento;
	private String idTipoGenero;
	private String idEstadoCivil;
    private String idPeriodoEstadoCivil;
	private String permisoTrabajo;
	private String idPais;
	private String numeroHijos;
	private String idTipoEstatusPadres;
	private String idTipoConvivencia;
	private String idTipoVivienda;
    private String numeroDependientesEconomicos;
    private String cambioDomicilio;
	private String idTipoDispViajar;
	private String idAmbitoGeografico;
	private String disponibilidadHorario;
	private String idTipoJornada;
	private String salarioMin;
	private String salarioMax;
	private String antiguedadDomicilio;
	private String idEstatusInscripcion;
	private String idTipoContrato;
	private String idTipoPersona;
	private String idTipoPrestacion;
	private String fechaModificacion;
	private String fechaCreacion;
	private String diasExperienciaLaboral;
	private String idGradoAcademicoMax;
	private String idEstatusEscolarMax;
	private String tituloMax;
	private String ultimoPuesto;
	private String idRelacionEmpresaPersona;

	
	//CvS masivo
	private String stPais;
	private String edad;
	private String claveInterna;
	
	private List<LocationInfoDto> localizacion;
	private List<WorkExperienceDto> experienciaLaboral;
	private List<AcademicBackgroundDto> escolaridad;
	
	private List<AreaPersonaDto> areaPersona;
	private String idAreaPrincipal = null;
	
	private List<ContactInfoDto> contacto;
	private List<PersonSkillDto> habilidad;
	
	private String tokenSocial;
	private String tokenSecret;
	
	private boolean alMenosUnaEscOExp;
	

	public CurriculumDto(){}
	public CurriculumDto(String idPersona,String idEstatusInscripcion){
		this.idPersona=idPersona;
		this.idEstatusInscripcion=idEstatusInscripcion;
	}

	public CurriculumDto(String nombre,String apellidoPaterno,String apellidoMaterno){
		this.nombre=nombre;
		this.apellidoPaterno=apellidoPaterno;
		this.apellidoMaterno=apellidoMaterno;
	}
	public CurriculumDto(Long idPersona,Long idEmpresaExterno,String claveInterna,String dummy){
		this.idPersona=idPersona.toString();
		this.idEmpresa=idEmpresa != null ? idEmpresa.toString():null;
		this.claveInterna=claveInterna;
	}
	
	public CurriculumDto(Long idPersona, Long idEstatusInscripcion, String nombre,String apellidoPaterno,String apellidoMaterno){
		this.idPersona=idPersona==null? null: String.valueOf(idPersona);
		this.idEstatusInscripcion=idEstatusInscripcion == null ? null:String.valueOf(idEstatusInscripcion);
		this.nombre=nombre;
		this.apellidoPaterno=apellidoPaterno;
		this.apellidoMaterno=apellidoMaterno;
	}	
	public CurriculumDto(Long idPersona,Long idTipoGenero, Boolean permisoTrabajo, Long idPais,
						Boolean cambioDomicilio,Long idTipoDispViajar,Long idAmbitoGeografico,
						Boolean disponibilidadHorario, Long idTipoJornada, Long idTipoContrato,
						Long idTipoPrestacion){
		this.idPersona=idPersona == null ? null:String.valueOf(idPersona);
		this.idTipoGenero=idTipoGenero == null ? null:String.valueOf(idTipoGenero);
		this.permisoTrabajo=permisoTrabajo == null ? null:(String.valueOf(permisoTrabajo ? "1":"0"));
		this.idPais=idPais == null ? null:String.valueOf(idPais);
		this.cambioDomicilio=cambioDomicilio == null ? null:(String.valueOf(cambioDomicilio ? "1":"0"));
		this.idTipoDispViajar=idTipoDispViajar == null ? null:String.valueOf(idTipoDispViajar);
		this.idAmbitoGeografico=idAmbitoGeografico == 0 ? null:String.valueOf(idAmbitoGeografico);
		this.disponibilidadHorario=disponibilidadHorario == null ? null:(String.valueOf(disponibilidadHorario ? "1":"0"));
		this.idTipoJornada=idTipoJornada == null ? null:String.valueOf(idTipoJornada);
		this.idTipoContrato=idTipoContrato == null ? null:String.valueOf(idTipoContrato);
		this.idTipoPrestacion=idTipoPrestacion == null ? null:String.valueOf(idTipoPrestacion);
	}
	
	
	
	public List<ContactInfoDto> getContacto() {
		return contacto;
	}
	public void setContacto(List<ContactInfoDto> contacto) {
		this.contacto = contacto;
	}
	
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
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
	
	public void setPermisoTrabajo(String permisoTrabajo) {
		this.permisoTrabajo = permisoTrabajo;
	}
	public String getPermisoTrabajo() {
		return permisoTrabajo;
	}

	public void setAnioNacimiento(String anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public String getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	public String getMesNacimiento() {
		return mesNacimiento;
	}

	public void setDiaNacimiento(String diaNacimiento) {
		this.diaNacimiento = diaNacimiento;
	}

	public String getDiaNacimiento() {
		return diaNacimiento;
	}
	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}
	public String getIdEstadoCivil() {
		return idEstadoCivil;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setIdPeriodoEstadoCivil(String idPeriodoEstadoCivil) {
		this.idPeriodoEstadoCivil = idPeriodoEstadoCivil;
	}
	public String getIdPeriodoEstadoCivil() {
		return idPeriodoEstadoCivil;
	}
	
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getIdPais() {
		return idPais;
	}
	public void setNumeroHijos(String numeroHijos) {
		this.numeroHijos = numeroHijos;
	}
	public String getNumeroHijos() {
		return numeroHijos;
	}
	
	public void setNumeroDependientesEconomicos(
			String numeroDependientesEconomicos) {
		this.numeroDependientesEconomicos = numeroDependientesEconomicos;
	}
	public String getNumeroDependientesEconomicos() {
		return numeroDependientesEconomicos;
	}
	
	public void setIdTipoVivienda(String idTipoVivienda) {
		this.idTipoVivienda = idTipoVivienda;
	}
	public String getIdTipoVivienda() {
		return idTipoVivienda;
	}
	public void setIdTipoEstatusPadres(String idTipoEstatusPadres) {
		this.idTipoEstatusPadres = idTipoEstatusPadres;
	}
	public String getIdTipoEstatusPadres() {
		return idTipoEstatusPadres;
	}
	public void setIdTipoConvivencia(String idTipoConvivencia) {
		this.idTipoConvivencia = idTipoConvivencia;
	}
	public String getIdTipoConvivencia() {
		return idTipoConvivencia;
	}
	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}
	public String getCambioDomicilio() {
		return cambioDomicilio;
	}
	
	public void setDisponibilidadHorario(String disponibilidadHorario) {
		this.disponibilidadHorario = disponibilidadHorario;
	}
	public String getDisponibilidadHorario() {
		return disponibilidadHorario;
	}
	public void setIdTipoDispViajar(String idTipoDispViajar) {
		this.idTipoDispViajar = idTipoDispViajar;
	}
	public String getIdTipoDispViajar() {
		return idTipoDispViajar;
	}
	public void setIdAmbitoGeografico(String idAmbitoGeografico) {
		this.idAmbitoGeografico = idAmbitoGeografico;
	}
	public String getIdAmbitoGeografico() {
		return idAmbitoGeografico;
	}
	public void setIdTipoJornada(String idTipoJornada) {
		this.idTipoJornada = idTipoJornada;
	}
	public String getIdTipoJornada() {
		return idTipoJornada;
	}
	public void setAntiguedadDomicilio(String antiguedadDomicilio) {
		this.antiguedadDomicilio = antiguedadDomicilio;
	}
	public String getAntiguedadDomicilio() {
		return antiguedadDomicilio;
	}
	public void setIdTipoContrato(String idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}
	public String getIdTipoContrato() {
		return idTipoContrato;
	}
	public void setIdTipoPersona(String idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}
	public String getIdTipoPersona() {
		return idTipoPersona;
	}
	public void setIdTipoPrestacion(String idTipoPrestacion) {
		this.idTipoPrestacion = idTipoPrestacion;
	}
	public String getIdTipoPrestacion() {
		return idTipoPrestacion;
	}
	public void setSalarioMin(String salarioMin) {
		this.salarioMin = salarioMin;
	}
	public String getSalarioMin() {
		return salarioMin;
	}
	public void setSalarioMax(String salarioMax) {
		this.salarioMax = salarioMax;
	}
	public String getSalarioMax() {
		return salarioMax;
	}

	public String getIdEstatusInscripcion() {
		return idEstatusInscripcion;
	}
	public void setIdEstatusInscripcion(String idEstatusInscripcion) {
		this.idEstatusInscripcion = idEstatusInscripcion;
	}
	public void setIdTipoGenero(String idTipoGenero) {
		this.idTipoGenero = idTipoGenero;
	}
	public String getIdTipoGenero() {
		return idTipoGenero;
	}
	
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setDiasExperienciaLaboral(String diasExperienciaLaboral) {
		this.diasExperienciaLaboral = diasExperienciaLaboral;
	}
	public String getDiasExperienciaLaboral() {
		return diasExperienciaLaboral;
	}
	public void setIdGradoAcademicoMax(String idGradoAcademicoMax) {
		this.idGradoAcademicoMax = idGradoAcademicoMax;
	}
	public String getIdGradoAcademicoMax() {
		return idGradoAcademicoMax;
	}
	public void setIdEstatusEscolarMax(String idEstatusEscolarMax) {
		this.idEstatusEscolarMax = idEstatusEscolarMax;
	}
	public String getIdEstatusEscolarMax() {
		return idEstatusEscolarMax;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setHabilidad(List<PersonSkillDto> habilidad) {
		this.habilidad = habilidad;
	}
	public List<PersonSkillDto> getHabilidad() {
		return habilidad;
	}
	public void setExperienciaLaboral(List<WorkExperienceDto> experienciaLaboral) {
		this.experienciaLaboral = experienciaLaboral;
	}
	public List<WorkExperienceDto> getExperienciaLaboral() {
		return experienciaLaboral;
	}
	public void setEscolaridad(List<AcademicBackgroundDto> escolaridad) {
		this.escolaridad = escolaridad;
	}
	public List<AcademicBackgroundDto> getEscolaridad() {
		return escolaridad;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getIdExterno() {
		return idExterno;
	}
	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
//	public List<GradoAcademico> getCatalogoGradoAcademico() {
//		return catalogoGradoAcademico;
//	}
//	public void setCatalogoGradoAcademico(
//			List<GradoAcademico> catalogoGradoAcademico) {
//		this.catalogoGradoAcademico = catalogoGradoAcademico;
//	}
	public String getStPais() {
		return stPais;
	}
	public void setStPais(String stPais) {
		this.stPais = stPais;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	
	public String getClaveInterna() {
		return claveInterna;
	}
	public void setClaveInterna(String claveInterna) {
		this.claveInterna = claveInterna;
	}
	public List<LocationInfoDto> getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(List<LocationInfoDto> localizacion) {
		this.localizacion = localizacion;
	}
	public String getTokenSocial() {
		return tokenSocial;
	}
	public void setTokenSocial(String tokenSocial) {
		this.tokenSocial = tokenSocial;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	public boolean isAlMenosUnaEscOExp() {
		return alMenosUnaEscOExp;
	}
	public void setAlMenosUnaEscOExp(boolean alMenosUnaEscOExp) {
		this.alMenosUnaEscOExp = alMenosUnaEscOExp;
	}
	public String getTituloMax() {
		return tituloMax;
	}
	public void setTituloMax(String tituloMax) {
		this.tituloMax = tituloMax;
	}


	public List<AreaPersonaDto> getAreaPersona() {
		return areaPersona;
	}

	public void setAreaPersona(List<AreaPersonaDto> areaPersona) {
		this.areaPersona = areaPersona;
	}
	public String getIdAreaPrincipal() {
		return idAreaPrincipal;
	}
	public void setIdAreaPrincipal(String idAreaPrincipal) {
		this.idAreaPrincipal = idAreaPrincipal;
	}
	public String getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}
	public void setIdRelacionEmpresaPersona(String idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}
	public String getPasswordIniSistema() {
		return passwordIniSistema;
	}
	public void setPasswordIniSistema(String passwordIniSistema) {
		this.passwordIniSistema = passwordIniSistema;
	}
	public String getUltimoPuesto() {
		return ultimoPuesto;
	}
	public void setUltimoPuesto(String ultimoPuesto) {
		this.ultimoPuesto = ultimoPuesto;
	}
}
