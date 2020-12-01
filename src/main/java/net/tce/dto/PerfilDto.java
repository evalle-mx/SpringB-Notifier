package net.tce.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import net.tce.dto.PerfilTextoNgramDto;


public class PerfilDto {

	private Long idPerfil;
	private long idPersonaPerfil;
	private long idEmpresaPerfil;
	private Date fechaCreacionPersonaPerfil;
	private Date fechaCreacionEmpresaPerfil;
	private double ponderacion;
	private double iap;
	private double iapBruto;
	private double experienciaLaboralBruto;
	private double escolaridadBruto;
	private double empresaBruto;
	private double experienciaLaboral;
	private double escolaridad;
	private double habilidadBruto;
	private long idTipoPerfil;
	private String nombre;
	private String descripcion;
	private String objetivo;
	private Set<PerfilTextoNgramDto> perfilTextoNgrams = new HashSet<PerfilTextoNgramDto>(0);
	
	public PerfilDto(){}
	
	public PerfilDto(Long idPerfil){
		this.idPerfil=idPerfil.longValue();
	}
	
	public PerfilDto(long idPerfil,BigDecimal ponderacion,BigDecimal iapAcademicoBruto, 
			BigDecimal iapLaboralBruto,long idPersonaPerfil, Date fechaCreacion){
		this.experienciaLaboralBruto=iapLaboralBruto.doubleValue();
		this.escolaridadBruto=iapAcademicoBruto.doubleValue();
		this.idPerfil=idPerfil;
		this.ponderacion=ponderacion.doubleValue();
		this.idPersonaPerfil=idPersonaPerfil;
		this.fechaCreacionPersonaPerfil=fechaCreacion;
	}
	public PerfilDto(long idPerfil,BigDecimal ponderacion,BigDecimal empresaBruto,
					 long idEmpresaPerfil, Date fechaCreacion){
		this.empresaBruto=empresaBruto.doubleValue();
		this.idPerfil=idPerfil;
		this.ponderacion=ponderacion.doubleValue();
		this.idEmpresaPerfil=idEmpresaPerfil;
		this.fechaCreacionEmpresaPerfil=fechaCreacion;
	}
	
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	

	public void setIap(double iap) {
		this.iap = iap;
	}
	public double getIap() {
		return iap;
	}
	public void setPonderacion(double ponderacion) {
		this.ponderacion = ponderacion;
	}
	public double getPonderacion() {
		return ponderacion;
	}
	
	public void setFechaCreacionPersonaPerfil(Date fechaCreacionPersonaPerfil) {
		this.fechaCreacionPersonaPerfil = fechaCreacionPersonaPerfil;
	}
	public Date getFechaCreacionPersonaPerfil() {
		return fechaCreacionPersonaPerfil;
	}
	

	public long getIdTipoPerfil() {
		return idTipoPerfil;
	}
	public void setIdTipoPerfil(long idTipoPerfil) {
		this.idTipoPerfil = idTipoPerfil;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public Set<PerfilTextoNgramDto> getPerfilTextoNgrams() {
		return perfilTextoNgrams;
	}
	public void setPerfilTextoNgrams(Set<PerfilTextoNgramDto> perfilTextoNgrams) {
		this.perfilTextoNgrams = perfilTextoNgrams;
	}
	public double getExperienciaLaboralBruto() {
		return experienciaLaboralBruto;
	}
	public void setExperienciaLaboralBruto(double experienciaLaboralBruto) {
		this.experienciaLaboralBruto = experienciaLaboralBruto;
	}
	public double getEscolaridadBruto() {
		return escolaridadBruto;
	}
	public void setEscolaridadBruto(double escolaridadBruto) {
		this.escolaridadBruto = escolaridadBruto;
	}
	public double getHabilidadBruto() {
		return habilidadBruto;
	}
	public void setHabilidadBruto(double habilidadBruto) {
		this.habilidadBruto = habilidadBruto;
	}
	public double getIapBruto() {
		return iapBruto;
	}
	public void setIapBruto(double iapBruto) {
		this.iapBruto = iapBruto;
	}

	public long getIdPersonaPerfil() {
		return idPersonaPerfil;
	}

	public void setIdPersonaPerfil(long idPersonaPerfil) {
		this.idPersonaPerfil = idPersonaPerfil;
	}

	public double getExperienciaLaboral() {
		return experienciaLaboral;
	}

	public void setExperienciaLaboral(double experienciaLaboral) {
		this.experienciaLaboral = experienciaLaboral;
	}

	public double getEscolaridad() {
		return escolaridad;
	}

	public void setEscolaridad(double escolaridad) {
		this.escolaridad = escolaridad;
	}

	public double getEmpresaBruto() {
		return empresaBruto;
	}

	public void setEmpresaBruto(double empresaBruto) {
		this.empresaBruto = empresaBruto;
	}

	public long getIdEmpresaPerfil() {
		return idEmpresaPerfil;
	}

	public void setIdEmpresaPerfil(long idEmpresaPerfil) {
		this.idEmpresaPerfil = idEmpresaPerfil;
	}

	public Date getFechaCreacionEmpresaPerfil() {
		return fechaCreacionEmpresaPerfil;
	}

	public void setFechaCreacionEmpresaPerfil(Date fechaCreacionEmpresaPerfil) {
		this.fechaCreacionEmpresaPerfil = fechaCreacionEmpresaPerfil;
	}
	
	
}
