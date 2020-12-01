package net.tce.dto;

public class SettlementIdsDto {

	private Long country;
	private Long admin_area_level_1;
	private Long locality;
	private Long sublocality;
	private Long city;
	private Long neighborhood;
	private String postal_code;

		
	public SettlementIdsDto(){
	}
	public SettlementIdsDto(Long country, Long admin_area_level_1, Long locality, Long sublocality, Long City, Long neighborhood, String postal_code){
		this.country = country;
		this.admin_area_level_1 = admin_area_level_1;
		this.locality = locality;
		this.sublocality = sublocality;
		this.neighborhood = neighborhood;
		this.postal_code = postal_code;
	}

	
	public void setCountry(Long country) {
		this.country = country;
	}
	public Long getCountry() {
		return country;
	}
	public void setAdmin_area_level_1(Long admin_area_level_1) {
		this.admin_area_level_1 = admin_area_level_1;
	}
	public Long getAdmin_area_level_1() {
		return admin_area_level_1;
	}
	public void setLocality(Long locality) {
		this.locality = locality;
	}
	public Long getLocality() {
		return locality;
	}
	public void setSublocality(Long sublocality) {
		this.sublocality = sublocality;
	}
	public Long getSublocality() {
		return sublocality;
	}
	public void setCity(Long city) {
		this.city = city;
	}
	public Long getCity() {
		return city;
	}
	public void setNeighborhood(Long neighborhood) {
		this.neighborhood = neighborhood;
	}
	public Long getNeighborhood() {
		return neighborhood;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getPostal_code() {
		return postal_code;
	}

}
