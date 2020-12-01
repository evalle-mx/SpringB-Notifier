package net.tce.dto;

public class PersonalInfoDto {
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String email;
	private Long anioNacimiento;
	private Long mesNacimiento;
	private Long diaNacimiento;
	private Long sexo;
	private Long idEstadoCivil;
    private Long idPeriodoEstadoCivil;
	private Long permisoTrabajo;
	
	
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
	
	public Long getSexo() {
		return sexo;
	}
	public void setSexo(Long sexo) {
		this.sexo = sexo;
	}
	
	
	public void setPermisoTrabajo(Long permisoTrabajo) {
		this.permisoTrabajo = permisoTrabajo;
	}
	public Long getPermisoTrabajo() {
		return permisoTrabajo;
	}

	public void setAnioNacimiento(Long anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public Long getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setMesNacimiento(Long mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	public Long getMesNacimiento() {
		return mesNacimiento;
	}

	public void setDiaNacimiento(Long diaNacimiento) {
		this.diaNacimiento = diaNacimiento;
	}

	public Long getDiaNacimiento() {
		return diaNacimiento;
	}
	public void setIdEstadoCivil(Long idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}
	public Long getIdEstadoCivil() {
		return idEstadoCivil;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setIdPeriodoEstadoCivil(Long idPeriodoEstadoCivil) {
		this.idPeriodoEstadoCivil = idPeriodoEstadoCivil;
	}
	public Long getIdPeriodoEstadoCivil() {
		return idPeriodoEstadoCivil;
	}
	
}
