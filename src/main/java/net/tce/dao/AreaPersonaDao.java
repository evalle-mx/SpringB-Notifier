package net.tce.dao;

import java.util.List;
import net.tce.model.AreaPersona;

public interface AreaPersonaDao extends PersistenceGenericDao<AreaPersona, Object>{

	 void deleteAllRecordsByPerson(Long idPersona);		 
	 List<AreaPersona> get(String idPersonas);
	 AreaPersona read(Long idPersona, Long idArea);
	 Long countConfirmado(Long idPersona);

	 
}
