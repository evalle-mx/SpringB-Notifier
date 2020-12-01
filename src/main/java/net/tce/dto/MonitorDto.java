package net.tce.dto;

import java.util.List;

public class MonitorDto extends  ModeloRscDto {

	private String idMonitor;
	
	private List<TrackingMonitorDto> monitores;

	public List<TrackingMonitorDto> getMonitores() {
		return monitores;
	}

	public void setMonitores(List<TrackingMonitorDto> monitores) {
		this.monitores = monitores;
	}

	public String getIdMonitor() {
		return idMonitor;
	}

	public void setIdMonitor(String idMonitor) {
		this.idMonitor = idMonitor;
	}
	
}
