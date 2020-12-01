package net.tce.admin.service;

import net.tce.dto.TrackingPostulanteDto;

public interface TrackingPostulanteService {
	String create(TrackingPostulanteDto trackingPostulanteDto)throws Exception ;
	String createAll(TrackingPostulanteDto trackingPostulanteDto)throws Exception ;
	String confirm(TrackingPostulanteDto trackingPostulanteDto)throws Exception;
	//String delete(TrackingPostulanteDto trackingPostulanteDto)throws Exception;
	//String rollBack(TrackingPostulanteDto trackingPostulanteDto)throws Exception;
}
