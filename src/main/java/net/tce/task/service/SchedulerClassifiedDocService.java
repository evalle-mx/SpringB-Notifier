package net.tce.task.service;


import net.tce.dto.SchedulerDto;


public interface SchedulerClassifiedDocService {
	
	 void setNumDocumentsByQueryForModel(SchedulerDto schedulerDto)throws Exception;
	 Boolean runningProcess(SchedulerDto schedulerDto);
	 SchedulerDto beginProcess(SchedulerDto schedulerDto);
	 SchedulerDto endProcess(SchedulerDto schedulerDto);
	 String getCurrentModelVersion(SchedulerDto schedulerDto);
	 String getPreviousModelVersion();
	 String getNewModelVersion();
	 Object synchronizeClassificationDocs(SchedulerDto schedulerDto) throws Exception;
	 Object synchronizeSolrDocs(SchedulerDto schedulerDto) throws Exception;
	 Object synchronizeDocs(SchedulerDto schedulerDto)throws Exception ;
	 Object runReModel(SchedulerDto schedulerDto)throws Exception;
	 Object runReClassification(SchedulerDto schedulerDto)throws Exception;
	 Object runReloadCoreSolr(SchedulerDto schedulerDto) throws Exception ;
	 String  verificarPosibleCandidato(SchedulerDto schedulerDto) throws Exception;

}
