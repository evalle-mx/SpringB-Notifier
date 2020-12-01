package net.tce.dto;

public class SettlementDescDto {


	private String country;
	private String admin_area_level_1;
	private String locality;
	private String sublocality;
	private String neighborhood;
	private String postal_code;

	public SettlementDescDto(){
	}
	public SettlementDescDto(String country, String admin_area_level_1, String locality, String sublocality, String neighborhood, String postal_code){
		this.country = country;
		this.admin_area_level_1 = admin_area_level_1;
		this.locality = locality;
		this.sublocality = sublocality;
		this.neighborhood = neighborhood;
		if(postal_code != null && !postal_code.equals(""))
		{
			this.postal_code = postal_code;
		}
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return country;
	}
	public void setAdmin_area_level_1(String admin_area_level_1) {
		this.admin_area_level_1 = admin_area_level_1;
	}
	public String getAdmin_area_level_1() {
		return admin_area_level_1;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getLocality() {
		return locality;
	}
	public void setSublocality(String sublocality) {
		this.sublocality = sublocality;
	}
	public String getSublocality() {
		return sublocality;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setPostal_code(String postal_code) {
		if(!postal_code.equals("") && postal_code != null)
		{
		 this.postal_code = postal_code;
		}
	}
	public String getPostal_code() {
		return postal_code;
	}

}
