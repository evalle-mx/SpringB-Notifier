package net.tce.dto;

public class PasswordDto extends ComunDto {
	private String fecha;
	private String idHistoricoPassword;
	private String idPersona;
	private String email;
	private String password;
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFecha() {
		return fecha;
	}
	public void setIdHistoricoPassword(String idHistoricoPassword) {
		this.idHistoricoPassword = idHistoricoPassword;
	}
	public String getIdHistoricoPassword() {
		return idHistoricoPassword;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
	}
}
