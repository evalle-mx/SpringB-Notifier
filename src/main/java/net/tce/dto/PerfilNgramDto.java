package net.tce.dto;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

public class PerfilNgramDto implements Cloneable{
	private String descripcion;
	private float ponderacion;
	private long idHabilidad;
	private List<PerfilNgramDto> lsHabilidadSimilar;
	Logger log4j = Logger.getLogger( this.getClass());

	public PerfilNgramDto(){}
	
	public PerfilNgramDto(long idHabilidad,String descripcion){
		this.descripcion=descripcion;
		this.idHabilidad=idHabilidad;
	}
	
	public PerfilNgramDto(String descripcion,short ponderacion){
		this.descripcion=descripcion;
		this.ponderacion=ponderacion;
	}
	
	public PerfilNgramDto(String descripcion,BigDecimal ponderacion){
		this.descripcion=descripcion;
		this.ponderacion=ponderacion.floatValue();
		
	}
	
	public PerfilNgramDto(String descripcion,BigDecimal ponderacion,List<PerfilNgramDto> lsHabilidadSimilar){
		this.descripcion=descripcion;
		this.ponderacion=ponderacion.floatValue();
		this.lsHabilidadSimilar=lsHabilidadSimilar;
		
	}
	
	/**
	 * Se emplea para clonar el objeto
	 */
	 public Object clone(){
	        Object obj=null;
	        try{
	            obj=super.clone();
	        }catch(CloneNotSupportedException ex){
	        	log4j.error(" NO SE PUEDE DUPLICAR EL OBJETO");
	        }
	        return obj;
	    }	
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setPonderacion(float ponderacion) {
		this.ponderacion = ponderacion;
	}
	public float getPonderacion() {
		return ponderacion;
	}

	
	
	public long getIdHabilidad() {
		return idHabilidad;
	}

	public void setIdHabilidad(long idHabilidad) {
		this.idHabilidad = idHabilidad;
	}

	public List<PerfilNgramDto> getLsHabilidadSimilar() {
		return lsHabilidadSimilar;
	}

	public void setLsHabilidadSimilar(List<PerfilNgramDto> lsHabilidadSimilar) {
		this.lsHabilidadSimilar = lsHabilidadSimilar;
	}


}
