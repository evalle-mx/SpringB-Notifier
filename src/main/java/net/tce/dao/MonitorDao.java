package net.tce.dao;

import java.util.List;

import net.tce.dto.TrackingMonitorDto;
import net.tce.model.Monitor;

public interface MonitorDao extends PersistenceGenericDao<Monitor, Object>{

	Long countTracks(Long idModeloRscPos, Long idRelacionEmpresaPersona);
	Long getMonitorPrincipal( Long idPosicion);
	List<TrackingMonitorDto> getMonitorPersona(Long idModeloRscPos);
	void deleteByidModeloRscPos(Long idModeloRscPos);
	Long countMonitor(Long idPosicion, Long idPersona );
	List<Monitor>  getAllMonitors( Long idPosicion);
}
