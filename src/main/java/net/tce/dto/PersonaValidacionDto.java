package net.tce.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonaValidacionDto {
	private String idPersona;	
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombrePublico;
	private String email;
	private String anioNacimiento;
	private String mesNacimiento;
	private String diaNacimiento;
	private String salarioMin;
	private String salarioMax;
	private String permisoTrabajo;
	private String numeroHijos;
	private String numeroDependientesEconomicos;
	private String antiguedadDomicilio;
	private String cambioDomicilio;
	private String disponibilidadHorario;
	private String diasExperienciaLaboral;	

	private String idTipoPersona;
	private String idEstatusInscripcion;
	private String idTipoPrestacion;
	private String idTipoConvivencia;
	private String idTipoVivienda;
	private String idPeriodoEstadoCivil;
	private String idAmbitoGeografico;
	private String idEstadoCivil;
	private String idTipoEstatusPadres;
	private String idTipoDispViajar;
	private String idTipoContrato;
	private String idTipoJornada;
	private String idGradoAcademicoMax;
	private String idEstatusEscolarMax;
	private String idTipoGenero;
	
	private List<LocationInfoDto> domicilio = new ArrayList<LocationInfoDto>(); //ok TODO probar con null
	private List<WorkExperienceDto> experienciaLaboral = new ArrayList<WorkExperienceDto>();//ok
	private List<AcademicBackgroundDto> escolaridad = new ArrayList<AcademicBackgroundDto>();
	private List<ContactInfoDto> contacto = new ArrayList<ContactInfoDto>();
	
	private List<PersonSkillDto> personaHabilidad = new ArrayList<PersonSkillDto>();
//	private List<PersonaPaisValidacionDto> personaPaises = new ArrayList<PersonaPaisValidacionDto>(0);
//	private List<PreferenciaValidacionDto> preferencias = new ArrayList<PreferenciaValidacionDto>(0);
//	private List<ContenidoValidacionDto> contenidos = new ArrayList<ContenidoValidacionDto>(0);

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
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

	public String getNombrePublico() {
		return nombrePublico;
	}

	public void setNombrePublico(String nombrePublico) {
		this.nombrePublico = nombrePublico;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(String anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public String getMesNacimiento() {
		return mesNacimiento;
	}

	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	public String getDiaNacimiento() {
		return diaNacimiento;
	}

	public void setDiaNacimiento(String diaNacimiento) {
		this.diaNacimiento = diaNacimiento;
	}

	public String getSalarioMin() {
		return salarioMin;
	}

	public void setSalarioMin(String salarioMin) {
		this.salarioMin = salarioMin;
	}

	public String getSalarioMax() {
		return salarioMax;
	}

	public void setSalarioMax(String salarioMax) {
		this.salarioMax = salarioMax;
	}

	public String getPermisoTrabajo() {
		return permisoTrabajo;
	}

	public void setPermisoTrabajo(String permisoTrabajo) {
		this.permisoTrabajo = permisoTrabajo;
	}

	public String getNumeroHijos() {
		return numeroHijos;
	}

	public void setNumeroHijos(String numeroHijos) {
		this.numeroHijos = numeroHijos;
	}

	public String getNumeroDependientesEconomicos() {
		return numeroDependientesEconomicos;
	}

	public void setNumeroDependientesEconomicos(String numeroDependientesEconomicos) {
		this.numeroDependientesEconomicos = numeroDependientesEconomicos;
	}

	public String getAntiguedadDomicilio() {
		return antiguedadDomicilio;
	}

	public void setAntiguedadDomicilio(String antiguedadDomicilio) {
		this.antiguedadDomicilio = antiguedadDomicilio;
	}

	public String getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getDisponibilidadHorario() {
		return disponibilidadHorario;
	}

	public void setDisponibilidadHorario(String disponibilidadHorario) {
		this.disponibilidadHorario = disponibilidadHorario;
	}

	public String getDiasExperienciaLaboral() {
		return diasExperienciaLaboral;
	}

	public void setDiasExperienciaLaboral(String diasExperienciaLaboral) {
		this.diasExperienciaLaboral = diasExperienciaLaboral;
	}

	public String getIdTipoPersona() {
		return idTipoPersona;
	}

	public void setIdTipoPersona(String idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public String getIdEstatusInscripcion() {
		return idEstatusInscripcion;
	}

	public void setIdEstatusInscripcion(String idEstatusInscripcion) {
		this.idEstatusInscripcion = idEstatusInscripcion;
	}

	public String getIdTipoPrestacion() {
		return idTipoPrestacion;
	}

	public void setIdTipoPrestacion(String idTipoPrestacion) {
		this.idTipoPrestacion = idTipoPrestacion;
	}

	public String getIdTipoConvivencia() {
		return idTipoConvivencia;
	}

	public void setIdTipoConvivencia(String idTipoConvivencia) {
		this.idTipoConvivencia = idTipoConvivencia;
	}

	public String getIdTipoVivienda() {
		return idTipoVivienda;
	}

	public void setIdTipoVivienda(String idTipoVivienda) {
		this.idTipoVivienda = idTipoVivienda;
	}

	public String getIdPeriodoEstadoCivil() {
		return idPeriodoEstadoCivil;
	}

	public void setIdPeriodoEstadoCivil(String idPeriodoEstadoCivil) {
		this.idPeriodoEstadoCivil = idPeriodoEstadoCivil;
	}

	public String getIdAmbitoGeografico() {
		return idAmbitoGeografico;
	}

	public void setIdAmbitoGeografico(String idAmbitoGeografico) {
		this.idAmbitoGeografico = idAmbitoGeografico;
	}

	public String getIdEstadoCivil() {
		return idEstadoCivil;
	}

	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	public String getIdTipoEstatusPadres() {
		return idTipoEstatusPadres;
	}

	public void setIdTipoEstatusPadres(String idTipoEstatusPadres) {
		this.idTipoEstatusPadres = idTipoEstatusPadres;
	}

	public String getIdTipoDispViajar() {
		return idTipoDispViajar;
	}

	public void setIdTipoDispViajar(String idTipoDispViajar) {
		this.idTipoDispViajar = idTipoDispViajar;
	}

	public String getIdTipoContrato() {
		return idTipoContrato;
	}

	public void setIdTipoContrato(String idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public String getIdTipoJornada() {
		return idTipoJornada;
	}

	public void setIdTipoJornada(String idTipoJornada) {
		this.idTipoJornada = idTipoJornada;
	}

	public String getIdGradoAcademicoMax() {
		return idGradoAcademicoMax;
	}

	public void setIdGradoAcademicoMax(String idGradoAcademicoMax) {
		this.idGradoAcademicoMax = idGradoAcademicoMax;
	}

	public String getIdEstatusEscolarMax() {
		return idEstatusEscolarMax;
	}

	public void setIdEstatusEscolarMax(String idEstatusEscolarMax) {
		this.idEstatusEscolarMax = idEstatusEscolarMax;
	}

	public String getIdTipoGenero() {
		return idTipoGenero;
	}

	public void setIdTipoGenero(String idTipoGenero) {
		this.idTipoGenero = idTipoGenero;
	}

	public List<LocationInfoDto> getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(List<LocationInfoDto> domicilio) {
		this.domicilio = domicilio;
	}

	public List<WorkExperienceDto> getExperienciaLaboral() {
		return experienciaLaboral;
	}

	public void setExperienciaLaboral(List<WorkExperienceDto> experienciaLaboral) {
		this.experienciaLaboral = experienciaLaboral;
	}

	public List<AcademicBackgroundDto> getEscolaridad() {
		return escolaridad;
	}

	public void setEscolaridad(List<AcademicBackgroundDto> escolaridad) {
		this.escolaridad = escolaridad;
	}

	public List<ContactInfoDto> getContacto() {
		return contacto;
	}

	public void setContacto(List<ContactInfoDto> contacto) {
		this.contacto = contacto;
	}

	public List<PersonSkillDto> getPersonaHabilidad() {
		return personaHabilidad;
	}

	public void setPersonaHabilidad(List<PersonSkillDto> personaHabilidad) {
		this.personaHabilidad = personaHabilidad;
	}
	
}
