package net.tce.dto;



public class SettlementDto extends ComunDto {

    /* Atributos que soportan la busqueda de direccion por coincidencia */
	private Long idCountry;
	private Long idAdmin_area_level_1;
	private Long idLocality;
	private Long idSublocality;
	private Long idCity;
	private Long idNeighborhood;
	private String postalCode;	
	
	private String country;
	private String admin_area_level_1;
	private String locality;
	private String sublocality;
	private String city;
	private String neighborhood;
	private String street;
	private Float ponderacion;
	/* */

    /* Agregados para soportar el json enviado desde la vista */
	private String tempPais;
	private String tempEstado;
	private String tempMunicipio;
	private String tempAsentamiento;
	private String tempCodigoPostal;
	private String calle;
	private String numeroExterior;
	private String numeroInterior;
	private String idDomicilio;
	private String idPersona;
	private String idEmpresa;
	private String idPosicion;
	private String idCodigoProceso;
	private String googleLatitude;
	private String googleLongitude;
    /* */

	private SettlementDto persistence;
	private SettlementDto discrepancy;

	public SettlementDto(){
	}

	public SettlementDto(Long idCountry, String country,Long idAdmin_area_level_1, String admin_area_level_1, Long idLocality, String locality,Long idNeighborhood, String neighborhood, String postalCode){
		this.postalCode = postalCode;
		this.idNeighborhood = idNeighborhood;
		this.idNeighborhood = idNeighborhood;
		this.idLocality = idLocality;
		this.idAdmin_area_level_1 = idAdmin_area_level_1;
		this.idCountry = idCountry;
		this.neighborhood = neighborhood;
		this.neighborhood = neighborhood;
		this.locality = locality;
		this.admin_area_level_1 = admin_area_level_1;
		this.country = country;
	}

	public SettlementDto(Long idCountry, String country,Long idAdmin_area_level_1, String admin_area_level_1, Long idLocality, String locality,Long idNeighborhood, String neighborhood){
		this.idNeighborhood = idNeighborhood;
		this.idLocality = idLocality;
		this.idAdmin_area_level_1 = idAdmin_area_level_1;
		this.idCountry = idCountry;
		this.neighborhood = neighborhood;
		this.locality = locality;
		this.admin_area_level_1 = admin_area_level_1;
		this.country = country;
	}
	
	public SettlementDto(Long idCountry, String country,Long idAdmin_area_level_1, String admin_area_level_1, Long idLocality, String locality){
		this.idLocality = idLocality;
		this.idAdmin_area_level_1 = idAdmin_area_level_1;
		this.idCountry = idCountry;
		this.locality = locality;
		this.admin_area_level_1 = admin_area_level_1;
		this.country = country;
	}
	
	public SettlementDto(String country, String admin_area_level_1,String locality, String neighborhood,String postalCode, boolean dummy){
		this.neighborhood = neighborhood;
		this.postalCode = postalCode;
		this.locality = locality;
		this.admin_area_level_1 = admin_area_level_1;
		this.country = country;
	}
	
	public SettlementDto(Long idCountry, Long idAdmin_area_level_1,Long idLocality, Long idNeighborhood,String postalCode){
		this.idNeighborhood = idNeighborhood;
		this.postalCode = postalCode;
		this.idLocality = idLocality;
		this.idAdmin_area_level_1 = idAdmin_area_level_1;
		this.idCountry = idCountry;
	}
	
	public SettlementDto(Long idCountry, String country,Long idAdmin_area_level_1, String admin_area_level_1){
		this.idAdmin_area_level_1 = idAdmin_area_level_1;
		this.idCountry = idCountry;
		this.admin_area_level_1 = admin_area_level_1;
		this.country = country;
	}

	public SettlementDto(Long idCountry, String country){
		this.idCountry = idCountry;
		this.country = country;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setIdNeighborhood(Long idNeighborhood) {
		this.idNeighborhood = idNeighborhood;
	}
	public Long getIdNeighborhood() {
		return idNeighborhood;
	}
	
	public void setIdCity(Long idCity) {
		this.idCity = idCity;
	}
	public Long getIdCity() {
		return idCity;
	}
	
	public void setIdLocality(Long idLocality) {
		this.idLocality = idLocality;
	}
	public Long getIdLocality() {
		return idLocality;
	}
	public void setIdSublocality(Long idSublocality) {
		this.idSublocality = idSublocality;
	}
	public Long getIdSublocality() {
		return idSublocality;
	}
	
	public void setIdAdmin_area_level_1(Long idAdmin_area_level_1) {
		this.idAdmin_area_level_1 = idAdmin_area_level_1;
	}
	public Long getIdAdmin_area_level_1() {
		return idAdmin_area_level_1;
	}

	public void setIdCountry(Long idCountry) {
		this.idCountry = idCountry;
	}
	public Long getIdCountry() {
		return idCountry;
	}
	
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getNeighborhood() {
		return neighborhood;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return city;
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
	
	public void setAdmin_area_level_1(String admin_area_level_1) {
		this.admin_area_level_1 = admin_area_level_1;
	}
	public String getAdmin_area_level_1() {
		return admin_area_level_1;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return country;
	}
	
	public void setPersistence(SettlementDto persistence) {
		this.persistence = persistence;
	}
	public SettlementDto getPersistence() {
		return persistence;
	}
	public void setDiscrepancy(SettlementDto discrepancy) {
		this.discrepancy = discrepancy;
	}
	public SettlementDto getDiscrepancy() {
		return discrepancy;
	}

	public void setIdDomicilio(String idDomicilio) {
		this.idDomicilio = idDomicilio;
	}
	public String getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet() {
		return street;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCalle() {
		return calle;
	}
	
	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}
	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setTempCodigoPostal(String tempCodigoPostal) {
		this.tempCodigoPostal = tempCodigoPostal;
	}
	public String getTempCodigoPostal() {
		return tempCodigoPostal;
	}
	
	public void setIdCodigoProceso(String idCodigoProceso) {
		this.idCodigoProceso = idCodigoProceso;
	}
	public String getIdCodigoProceso() {
		return idCodigoProceso;
	}

	public void setGoogleLatitude(String googleLatitude) {
		this.googleLatitude = googleLatitude;
	}
	public String getGoogleLatitude() {
		return googleLatitude;
	}

	public void setGoogleLongitude(String googleLongitude) {
		this.googleLongitude = googleLongitude;
	}
	public String getGoogleLongitude() {
		return googleLongitude;
	}

	public void setTempPais(String tempPais) {
		this.tempPais = tempPais;
	}
	public String getTempPais() {
		return tempPais;
	}

	public void setTempEstado(String tempEstado) {
		this.tempEstado = tempEstado;
	}
	public String getTempEstado() {
		return tempEstado;
	}
	
	public void setTempMunicipio(String tempMunicipio) {
		this.tempMunicipio = tempMunicipio;
	}
	public String getTempMunicipio() {
		return tempMunicipio;
	}

	public void setTempAsentamiento(String tempAsentamiento) {
		this.tempAsentamiento = tempAsentamiento;
	}
	public String getTempAsentamiento() {
		return tempAsentamiento;
	}
	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public Float getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Float ponderacion) {
		this.ponderacion = ponderacion;
	}
	
	
}
