package net.tce.task.service;


import net.tce.dto.SchedulerDto;

public interface SearchCandidatesService {	
  String searchCandidates(SchedulerDto schedulerDto) throws Exception;
}