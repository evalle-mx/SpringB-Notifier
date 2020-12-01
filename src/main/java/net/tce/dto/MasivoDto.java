package net.tce.dto;

import java.util.List;

public class MasivoDto extends ComunDto{
	
	private String claveEmpresa;
	private String msvDir;
	private List<CurriculumDto> curriculos;
	private List<VacancyDto> posiciones;
	
	
	
	public String getClaveEmpresa() {
		return claveEmpresa;
	}
	public void setClaveEmpresa(String claveEmpresa) {
		this.claveEmpresa = claveEmpresa;
	}
	public List<CurriculumDto> getCurriculos() {
		return curriculos;
	}
	public void setCurriculos(List<CurriculumDto> curriculos) {
		this.curriculos = curriculos;
	}
	public List<VacancyDto> getPosiciones() {
		return posiciones;
	}
	public void setPosiciones(List<VacancyDto> posiciones) {
		this.posiciones = posiciones;
	}
	public String getMsvDir() {
		return msvDir;
	}
	public void setMsvDir(String msvDir) {
		this.msvDir = msvDir;
	}
	
	
}
