package net.tce.dto;


public class EscolaridadValidacionDto {
	private Long idEscolaridad;
	private AreaValidacionDto area;
	private GradoAcademicoValidacionDto gradoAcademico;
	private PersonaValidacionDto persona;
	private EstatusEscolarValidacionDto estatusEscolar;
	private PaisDto pais;
	private MesValidacionDto mesByMesInicio;	
	private MesValidacionDto mesByMesFin;	
	private String texto;		
	private Long idTextoNgram;
	private String nombreInstitucion;
	private Short anioInicio;
	private Byte diaInicio;
	private Short anioFin;
	private Byte diaFin;

	public EscolaridadValidacionDto() {
	}

	public Long getIdEscolaridad() {
		return this.idEscolaridad;
	}

	public void setIdEscolaridad(Long idEscolaridad) {
		this.idEscolaridad = idEscolaridad;
	}

	public AreaValidacionDto getArea() {
		return this.area;
	}

	public void setArea(AreaValidacionDto area) {
		this.area = area;
	}

	public GradoAcademicoValidacionDto getGradoAcademico() {
		return this.gradoAcademico;
	}

	public void setGradoAcademico(GradoAcademicoValidacionDto gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}

	public PersonaValidacionDto getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaValidacionDto persona) {
		this.persona = persona;
	}

	public EstatusEscolarValidacionDto getEstatusEscolar() {
		return this.estatusEscolar;
	}

	public void setEstatusEscolar(EstatusEscolarValidacionDto estatusEscolar) {
		this.estatusEscolar = estatusEscolar;
	}

	public PaisDto getPais() {
		return this.pais;
	}

	public void setPais(PaisDto pais) {
		this.pais = pais;
	}

	public String getNombreInstitucion() {
		return this.nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
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

	public Long getIdTextoNgram() {
		return idTextoNgram;
	}

	public void setIdTextoNgram(Long idTextoNgram) {
		this.idTextoNgram = idTextoNgram;
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
