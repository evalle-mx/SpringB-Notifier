package net.tce.dao;

import java.util.List;

import net.tce.dto.CurriculumDto;
import net.tce.dto.NotificacionDto;
import net.tce.model.Persona;

public interface PersonaDao extends PersistenceGenericDao<Persona, Object>{
	
	Persona getPersonaByIdClassDoc(String idTextoClasificacion);	
	List<Persona> getPersonasSinClasificar();
	Object getDtoByIdExterno(String claveInterna, Long idEmpresa);
	List<NotificacionDto> getReminder(int numero,byte typeReminder);
	int updateNumeroReminder(NotificacionDto notificacionDto,byte typeReminder);
	int updateSePreClasifica(boolean sePreClasifica, Long idPersona);
	List<CurriculumDto> findEmail(String email);
	int updateTokenInicio(String tokenInicio, Long idPersona);
	CurriculumDto getPersonaNombre(Long idPersona);

}
