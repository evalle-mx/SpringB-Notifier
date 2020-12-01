package net.tce.dto;

import java.util.HashMap;
import java.util.List;


public class AreaFuncionPerfilDto {
	private List<PerfilNgramDto> lsFuncionPerfilDto;
	private HashMap<String,String> hmFormulaArea;
	
	public AreaFuncionPerfilDto(){}
	
	public AreaFuncionPerfilDto(List<PerfilNgramDto> lsFuncionPerfilDto,HashMap<String,String> hmFormulaArea){
		this.lsFuncionPerfilDto=lsFuncionPerfilDto;
		this.hmFormulaArea=hmFormulaArea;
	}
	
	public void setLsFuncionPerfilDto(List<PerfilNgramDto> lsFuncionPerfilDto) {
		this.lsFuncionPerfilDto = lsFuncionPerfilDto;
	}

	public List<PerfilNgramDto> getLsFuncionPerfilDto() {
		return lsFuncionPerfilDto;
	}

	public void setHmFormulaArea(HashMap<String,String> hmFormulaArea) {
		this.hmFormulaArea = hmFormulaArea;
	}

	public HashMap<String,String> getHmFormulaArea() {
		return hmFormulaArea;
	}
}
