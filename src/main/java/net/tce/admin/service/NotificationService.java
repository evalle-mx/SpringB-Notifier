package net.tce.admin.service;


import net.tce.dto.NotificationDto;

public interface NotificationService {
		
	String create(NotificationDto notificationDto) throws Exception;
//	Object get(NotificationDto notificationDto)throws Exception;
}