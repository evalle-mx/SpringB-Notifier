package net.tce.dto;

public class DataConfDto {

	private boolean required;
	private String restriction;
	private Long minLength;
	private Long maxLength;
	private Long minValue;
	private Long maxValue;
	private String precisionScale;
	private String valueSet;
	private String pattern;
	
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getRestriction() {
		return restriction;
	}
	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}
	public Long getMinLength() {
		return minLength;
	}
	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}
	public Long getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}
	public String getPrecisionScale() {
		return precisionScale;
	}
	public void setPrecisionScale(String precisionScale) {
		this.precisionScale = precisionScale;
	}
	public String getValueSet() {
		return valueSet;
	}
	public void setValueSet(String valueSet) {
		this.valueSet = valueSet;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getPattern() {
		return pattern;
	}
	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}
	public Long getMinValue() {
		return minValue;
	}
	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}
	public Long getMaxValue() {
		return maxValue;
	}
	
}
