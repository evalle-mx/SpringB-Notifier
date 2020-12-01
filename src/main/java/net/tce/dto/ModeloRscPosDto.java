package net.tce.dto;

public class ModeloRscPosDto {

	private Long idModeloRscPos;
	private Long contador;
	
	public ModeloRscPosDto(){}
	
	public ModeloRscPosDto(Long contador,Long idModeloRscPos){
		this.idModeloRscPos=idModeloRscPos;
		this.contador=contador;
	}
	
	public Long getIdModeloRscPos() {
		return idModeloRscPos;
	}
	public void setIdModeloRscPos(Long idModeloRscPos) {
		this.idModeloRscPos = idModeloRscPos;
	}
	public Long getContador() {
		return contador;
	}
	public void setContador(Long contador) {
		this.contador = contador;
	}
	
	
}
