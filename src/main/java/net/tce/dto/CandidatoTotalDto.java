package net.tce.dto;

public class CandidatoTotalDto extends ComunDto{
	
	private String total;
	private CandidatoDetalleDto aceptados;
	private CandidatoDetalleDto rechazados;
	private Boolean esKO;
	private Boolean entroPrimeravez;
	private Boolean entroPrimeravezDemo;
	

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public CandidatoDetalleDto getAceptados() {
		return aceptados;
	}

	public void setAceptados(CandidatoDetalleDto aceptados) {
		this.aceptados = aceptados;
	}

	public CandidatoDetalleDto getRechazados() {
		return rechazados;
	}

	public void setRechazados(CandidatoDetalleDto rechazados) {
		this.rechazados = rechazados;
	}

	public Boolean getEsKO() {
		return esKO;
	}

	public void setEsKO(Boolean esKO) {
		this.esKO = esKO;
	}

	public Boolean getEntroPrimeravez() {
		return entroPrimeravez;
	}

	public void setEntroPrimeravez(Boolean entroPrimeravez) {
		this.entroPrimeravez = entroPrimeravez;
	}



	public Boolean getEntroPrimeravezDemo() {
		return entroPrimeravezDemo;
	}

	public void setEntroPrimeravezDemo(Boolean entroPrimeravezDemo) {
		this.entroPrimeravezDemo = entroPrimeravezDemo;
	}


}
