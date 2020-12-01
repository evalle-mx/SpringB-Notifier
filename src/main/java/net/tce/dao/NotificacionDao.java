package net.tce.dao;

import java.util.List;
import net.tce.dto.NotificationDto;
import net.tce.model.Notificacion;

public interface NotificacionDao extends PersistenceGenericDao<Notificacion, Object>{

	List<NotificationDto> get(long idPersonaReceptor);
	List<NotificationDto> getPeopleCandidateOfPosition(Long idPosicion);
	List<NotificationDto> getPeopleByRelationType(Long idEmpresa, Long idRol);
	List<NotificationDto> getPositionPeopleOfPosition(Long idPosicion);
	List<NotificationDto> getPositionPeopleOfCandidate(Long idPersona);
	List<NotificationDto> getPeople(Long idPersona);
	List<NotificationDto> getPeoplePositionOfCandidateEnterprise(Long idEmpresa) ;
	List<NotificationDto> getPeopleCandidateOfPositionPerson(Long idPersona, boolean empresaNulo);
	List<NotificationDto> getPersonOfCandidate(Long idCandidate);
	List<NotificationDto> getPersonOfPosibleCandidate(Long idPosibleCandidate);
	
}
