package net.tce.dto;


public class SchedulerDto extends ComunDto {
	
	private String idEntidad;
	private String tipoEntidad;
	private Long numDocs;	
	private Long idTipoProceso;	
	private Long idControlProceso;	
	private Long idEstatusProceso;	
	private String currentModel;
	private String newModel;
	private String resultado;
	private String queryForModel;
	private long currentNumDocsByQueryForModel;
	private long numDocsForModel;
	private Short reclassificationAuto;
	private Long idPosicion;
	private String idPersona;
	private Boolean huboCambioPosicion;
	private byte typeReminder;
	private String tipoSync;
	private String idAreasConfirmadas;
	
	public  SchedulerDto(){
		
	}
	public  SchedulerDto(String idEmpresaConf, String idPersona){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
	}
	public  SchedulerDto(String idEmpresaConf){
		this.setIdEmpresaConf(idEmpresaConf);
		
	}
	
	public  SchedulerDto(byte typeReminder){
		this.typeReminder=typeReminder;
		
	}
	
	public  SchedulerDto(Long idTipoProceso){
		this.idTipoProceso=idTipoProceso;
		
	}
	public  SchedulerDto(String idEmpresaConf, String idPersona,String idAreasConfirmadas){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.idAreasConfirmadas=idAreasConfirmadas;
	}
	
	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	
	public String getTipoEntidad() {
		return tipoEntidad;
	}
	public void setTipoEntidad(String tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public Long getNumDocs() {
		return numDocs;
	}
	public void setNumDocs(Long numDocs) {
		this.numDocs = numDocs;
	}		
	public Long getIdTipoProceso() {
		return idTipoProceso;
	}
	public void setIdTipoProceso(Long idTipoProceso) {
		this.idTipoProceso = idTipoProceso;
	}
	public Long getIdControlProceso() {
		return idControlProceso;
	}
	public void setIdControlProceso(Long idControlProceso) {
		this.idControlProceso = idControlProceso;
	}
	public String getCurrentModel() {
		return currentModel;
	}
	public void setCurrentModel(String currentModel) {
		this.currentModel = currentModel;
	}
	public String getNewModel() {
		return newModel;
	}
	public void setNewModel(String newModel) {
		this.newModel = newModel;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getQueryForModel() {
		return queryForModel;
	}
	public void setQueryForModel(String queryForModel) {
		this.queryForModel = queryForModel;
	}
	public long getNumDocsForModel() {
		return numDocsForModel;
	}
	public void setNumDocsForModel(long numDocsForModel) {
		this.numDocsForModel = numDocsForModel;
	}
	public Short getReclassificationAuto() {
		return reclassificationAuto;
	}
	public void setReclassificationAuto(Short reclassificationAuto) {
		this.reclassificationAuto = reclassificationAuto;
	}
	public Long getIdEstatusProceso() {
		return idEstatusProceso;
	}
	public void setIdEstatusProceso(Long idEstatusProceso) {
		this.idEstatusProceso = idEstatusProceso;
	}
	
	public long getCurrentNumDocsByQueryForModel() {
		return currentNumDocsByQueryForModel;
	}
	public void setCurrentNumDocsByQueryForModel(long currentNumDocsByQueryForModel) {
		this.currentNumDocsByQueryForModel = currentNumDocsByQueryForModel;
	}
	public Long getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}

	public Boolean getHuboCambioPosicion() {
		return huboCambioPosicion;
	}

	public void setHuboCambioPosicion(Boolean huboCambioPosicion) {
		this.huboCambioPosicion = huboCambioPosicion;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public byte getTypeReminder() {
		return typeReminder;
	}
	public void setTypeReminder(byte typeReminder) {
		this.typeReminder = typeReminder;
	}
	public String getTipoSync() {
		return tipoSync;
	}
	public void setTipoSync(String tipoSync) {
		this.tipoSync = tipoSync;
	}
	public String getIdAreasConfirmadas() {
		return idAreasConfirmadas;
	}
	public void setIdAreasConfirmadas(String idAreasConfirmadas) {
		this.idAreasConfirmadas = idAreasConfirmadas;
	}
	
	
	
}
