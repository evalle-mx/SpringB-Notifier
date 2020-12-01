package net.tce.dao;


import java.util.List;

import net.tce.model.TrackingPostulante;

public interface TrackingPostulanteDao extends PersistenceGenericDao<TrackingPostulante, Object>{

	int updateCandidate(long idCandidato, long idPosibleCandidato);
	Long countRecordsCandidates(Long idCandidato);
	Long countRecordsPossibleCandidates(Long idPosibleCandidato);
	int updateEdoTracking(Long idTrackingPostulante, Long idEstadoTracking);
	List<Long> getActUpByPosRepOrder(Long idPosicion,Short orden, Short actividad, Long idRelacionEmpresaPersona);
	List<Long> getOrderUpByPosRepOrder(Long idPosicion, Short orden,Long idRelacionEmpresaPersona);
}
