package net.tce.dto;

public class MensajeDto extends EstafetaDto {
	private String name;
	private String value;
	private String messages;
	
	public MensajeDto(){}
	public MensajeDto(String name,String value,String code,String type,String message){
		this.name= name;
		this.value= value;
		this.setCode(code);
		this.setType(type);
		this.setMessage(message);
	}
	public MensajeDto(String name,String value,String messages){
		this.setName(name);
		this.setValue(value);		
		this.messages=messages;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	
}
