package net.tce.dto;

public class PoliticaValorDto {
	private Integer orden;
	private String valor;
	private String tipoValor;

	private String idPoliticaValor;
	private String idSeccion;
	private String idPolitica;
	private String idEmpresa;
	private String idPerfil;
	private String idConcepto;
	private String idArea;
	private String idPosicion;
	private String valorMinRango1;
	private String valorMaxRango1;
	private String ponderacionRango1;
	private String valorMinRango2;
	private String valorMaxRango2;
	private String ponderacionRango2;
	private String valorMinRango3;
	private String valorMaxRango3;
	private String ponderacionRango3;
	private String fechaCreacion;
	private String fechaModificacion;
	
	public PoliticaValorDto(){}
	
	public PoliticaValorDto(String valor,String tipoValor,Integer orden){
		this.orden=orden;
		this.valor=valor;
		this.tipoValor=tipoValor;
	}
	
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	public String getTipoValor() {
		return tipoValor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getValor() {
		return valor;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public Integer getOrden() {
		return orden;
	}
	public String getIdPoliticaValor() {
		return idPoliticaValor;
	}

	public void setIdPoliticaValor(String idPoliticaValor) {
		this.idPoliticaValor = idPoliticaValor;
	}

	public String getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(String idSeccion) {
		this.idSeccion = idSeccion;
	}

	public String getIdPolitica() {
		return idPolitica;
	}

	public void setIdPolitica(String idPolitica) {
		this.idPolitica = idPolitica;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(String idConcepto) {
		this.idConcepto = idConcepto;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getValorMinRango1() {
		return valorMinRango1;
	}

	public void setValorMinRango1(String valorMinRango1) {
		this.valorMinRango1 = valorMinRango1;
	}

	public String getValorMaxRango1() {
		return valorMaxRango1;
	}

	public void setValorMaxRango1(String valorMaxRango1) {
		this.valorMaxRango1 = valorMaxRango1;
	}

	public String getPonderacionRango1() {
		return ponderacionRango1;
	}

	public void setPonderacionRango1(String ponderacionRango1) {
		this.ponderacionRango1 = ponderacionRango1;
	}

	public String getValorMinRango2() {
		return valorMinRango2;
	}

	public void setValorMinRango2(String valorMinRango2) {
		this.valorMinRango2 = valorMinRango2;
	}

	public String getValorMaxRango2() {
		return valorMaxRango2;
	}

	public void setValorMaxRango2(String valorMaxRango2) {
		this.valorMaxRango2 = valorMaxRango2;
	}

	public String getPonderacionRango2() {
		return ponderacionRango2;
	}

	public void setPonderacionRango2(String ponderacionRango2) {
		this.ponderacionRango2 = ponderacionRango2;
	}

	public String getValorMinRango3() {
		return valorMinRango3;
	}

	public void setValorMinRango3(String valorMinRango3) {
		this.valorMinRango3 = valorMinRango3;
	}

	public String getValorMaxRango3() {
		return valorMaxRango3;
	}

	public void setValorMaxRango3(String valorMaxRango3) {
		this.valorMaxRango3 = valorMaxRango3;
	}

	public String getPonderacionRango3() {
		return ponderacionRango3;
	}

	public void setPonderacionRango3(String ponderacionRango3) {
		this.ponderacionRango3 = ponderacionRango3;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}
