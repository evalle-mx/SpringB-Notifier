package net.tce.dto;

public class EmpresaInterfaseDto {

	private String idEmpresa;
	private String contexto;
	private String propiedad;
	private String atributo;
	private String valor;
	
	public EmpresaInterfaseDto(){}
	
	public EmpresaInterfaseDto(String idEmpresa, String valor){
		this.idEmpresa=idEmpresa;
		this.valor=valor;
	}
	
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
	public String getContexto() {
		return contexto;
	}
	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}
	public String getPropiedad() {
		return propiedad;
	}
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	public String getAtributo() {
		return atributo;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getValor() {
		return valor;
	}
}
