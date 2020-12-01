package net.tce.task.service;

import net.tce.dto.SchedulerDto;

public interface ReminderService {
	String sendReminder(SchedulerDto schedulerDto)throws Exception;
}
