package net.tce.dao;

import java.util.List;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.model.TrackingMonitor;

public interface TrackingMonitorDao  extends PersistenceGenericDao<TrackingMonitor, Object>{

	List<ModeloRscPosFaseDto> get(Long idModeloRscPos, Long idPosicion);
	List<TrackingMonitorDto>  getAllByPosicion(Long idPosicion);
	TrackingMonitorDto getByPostulante(Long idTrackingPostulante);
	Long countByPosREPPost( Long idCandidato, Long idEstadoTracking) ;
	Long countByMRSCPos( Long idModeloRscPos) ;
	List<Long> getActUpByPosRepOrder(Long idPosicion,Short orden, Short actividad, Long idRelacionEmpresaPersona);
	List<Long> getOrderUpByPosRepOrder(Long idPosicion, Short orden,Long idRelacionEmpresaPersona);
}
