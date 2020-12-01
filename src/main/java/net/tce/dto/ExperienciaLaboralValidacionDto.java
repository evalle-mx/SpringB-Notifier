package net.tce.dto;

import java.util.ArrayList;
import java.util.List;


public class ExperienciaLaboralValidacionDto {
	private Long idExperienciaLaboral;
	private TipoPrestacionValidacionDto tipoPrestacion;
	private NivelJerarquicoValidacionDto nivelJerarquico;
	private TipoContratoValidacionDto tipoContrato;
	private PersonaValidacionDto persona;
	private PaisDto pais;
	private TipoJornadaValidacionDto tipoJornada;
	private Long idTextoNgram;	
	private MesValidacionDto mesByMesInicio;	
	private MesValidacionDto mesByMesFin;		
	private String texto;	
	private String nombreEmpresa;
	private String puesto;
	private String motivoSeparacion;
	private Short anioInicio;
	private Byte diaInicio;
	private Short anioFin;
	private Byte diaFin;
	private Boolean trabajoActual;
	private Boolean referencias;
	private Boolean genteACargo;
	private String complementoDireccion;
	private List<ReferenciaValidacionDto> referencias_1 = new ArrayList<ReferenciaValidacionDto>(0);

	public ExperienciaLaboralValidacionDto() {
	}

	public Long getIdExperienciaLaboral() {
		return this.idExperienciaLaboral;
	}

	public void setIdExperienciaLaboral(Long idExperienciaLaboral) {
		this.idExperienciaLaboral = idExperienciaLaboral;
	}

	public TipoPrestacionValidacionDto getTipoPrestacion() {
		return this.tipoPrestacion;
	}

	public void setTipoPrestacion(TipoPrestacionValidacionDto tipoPrestacion) {
		this.tipoPrestacion = tipoPrestacion;
	}

	public NivelJerarquicoValidacionDto getNivelJerarquico() {
		return this.nivelJerarquico;
	}

	public void setNivelJerarquico(NivelJerarquicoValidacionDto nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}

	public TipoContratoValidacionDto getTipoContrato() {
		return this.tipoContrato;
	}

	public void setTipoContrato(TipoContratoValidacionDto tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public PersonaValidacionDto getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaValidacionDto persona) {
		this.persona = persona;
	}

	public PaisDto getPais() {
		return this.pais;
	}

	public void setPais(PaisDto pais) {
		this.pais = pais;
	}

	public TipoJornadaValidacionDto getTipoJornada() {
		return this.tipoJornada;
	}

	public void setTipoJornada(TipoJornadaValidacionDto tipoJornada) {
		this.tipoJornada = tipoJornada;
	}

	public Long getIdTextoNgram() {
		return idTextoNgram;
	}

	public void setIdTextoNgram(Long idTextoNgram) {
		this.idTextoNgram = idTextoNgram;
	}

	public String getNombreEmpresa() {
		return this.nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getPuesto() {
		return this.puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getMotivoSeparacion() {
		return this.motivoSeparacion;
	}

	public void setMotivoSeparacion(String motivoSeparacion) {
		this.motivoSeparacion = motivoSeparacion;
	}

	public Short getAnioInicio() {
		return this.anioInicio;
	}

	public void setAnioInicio(Short anioInicio) {
		this.anioInicio = anioInicio;
	}

	public Byte getDiaInicio() {
		return this.diaInicio;
	}

	public void setDiaInicio(Byte diaInicio) {
		this.diaInicio = diaInicio;
	}

	public Short getAnioFin() {
		return this.anioFin;
	}

	public void setAnioFin(Short anioFin) {
		this.anioFin = anioFin;
	}

	public Byte getDiaFin() {
		return this.diaFin;
	}

	public void setDiaFin(Byte diaFin) {
		this.diaFin = diaFin;
	}

	public Boolean getTrabajoActual() {
		return this.trabajoActual;
	}

	public void setTrabajoActual(Boolean trabajoActual) {
		this.trabajoActual = trabajoActual;
	}

	public Boolean getReferencias() {
		return this.referencias;
	}

	public void setReferencias(Boolean referencias) {
		this.referencias = referencias;
	}

	public Boolean getGenteACargo() {
		return this.genteACargo;
	}

	public void setGenteACargo(Boolean genteACargo) {
		this.genteACargo = genteACargo;
	}

	public String getComplementoDireccion() {
		return this.complementoDireccion;
	}

	public void setComplementoDireccion(String complementoDireccion) {
		this.complementoDireccion = complementoDireccion;
	}

	public List<ReferenciaValidacionDto> getReferencias_1() {
		return this.referencias_1;
	}

	public void setReferencias_1(List<ReferenciaValidacionDto> referencias_1) {
		this.referencias_1 = referencias_1;
	}


	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	public MesValidacionDto getMesByMesInicio() {
		return mesByMesInicio;
	}

	public void setMesByMesInicio(MesValidacionDto mesByMesInicio) {
		this.mesByMesInicio = mesByMesInicio;
	}

	public MesValidacionDto getMesByMesFin() {
		return mesByMesFin;
	}

	public void setMesByMesFin(MesValidacionDto mesByMesFin) {
		this.mesByMesFin = mesByMesFin;
	}
}
