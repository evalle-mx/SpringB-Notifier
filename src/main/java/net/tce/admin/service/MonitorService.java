package net.tce.admin.service;

import net.tce.dto.MonitorDto;

public interface MonitorService {

	String create(MonitorDto monitorDto)throws Exception;
	
}
