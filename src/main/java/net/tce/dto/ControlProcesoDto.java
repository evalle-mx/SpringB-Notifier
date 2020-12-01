package net.tce.dto;

public class ControlProcesoDto extends ComunDto{

	private String idControlProceso;
	private String idTipoProceso;
	private String idEstatusProceso;
	private String fechaInicio;
	private String fechaFin;	
	private String resultado;
	private String mensaje;
	private String tipoSync;
	private String idPersona;
	private String fecha;
	private String fechaSynSolr;
	private String fechaSynConfir;
	
	public ControlProcesoDto(){}
	
	public ControlProcesoDto(String fecha){
		this.fecha=fecha;
	}

	public ControlProcesoDto(String idPersona,String idEmpresaConf){
		this.idPersona=idPersona;
		this.setIdEmpresaConf(idEmpresaConf);
	}
	
	public ControlProcesoDto(String idPersona,String idEmpresaConf,String tipoSync){
		this.idPersona=idPersona;
		this.setIdEmpresaConf(idEmpresaConf);
		this.tipoSync=tipoSync;
	}
	
	public ControlProcesoDto(Long idControlProceso, Long idTipoProceso, Long idEstatusProceso,
				String fechaInicio, String fechaFin, String resultado, String mensaje){
		this.idControlProceso=idControlProceso!=null?String.valueOf(idControlProceso):null;
		this.idEstatusProceso=idEstatusProceso!=null?String.valueOf(idEstatusProceso):null;
		this.idTipoProceso=idTipoProceso!=null?String.valueOf(idTipoProceso):null;
		this.idEstatusProceso=idEstatusProceso!=null?String.valueOf(idEstatusProceso):null;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.resultado=resultado;
		this.mensaje=mensaje;
	}
	

	public String getIdControlProceso() {
		return idControlProceso;
	}

	public void setIdControlProceso(String idControlProceso) {
		this.idControlProceso = idControlProceso;
	}

	public String getIdTipoProceso() {
		return idTipoProceso;
	}

	public void setIdTipoProceso(String idTipoProceso) {
		this.idTipoProceso = idTipoProceso;
	}

	public String getIdEstatusProceso() {
		return idEstatusProceso;
	}

	public void setIdEstatusProceso(String idEstatusProceso) {
		this.idEstatusProceso = idEstatusProceso;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTipoSync() {
		return tipoSync;
	}

	public void setTipoSync(String tipoSync) {
		this.tipoSync = tipoSync;
	}

	public String getFechaSynSolr() {
		return fechaSynSolr;
	}

	public void setFechaSynSolr(String fechaSynSolr) {
		this.fechaSynSolr = fechaSynSolr;
	}

	public String getFechaSynConfir() {
		return fechaSynConfir;
	}

	public void setFechaSynConfir(String fechaSynConfir) {
		this.fechaSynConfir = fechaSynConfir;
	}
	
	
}
