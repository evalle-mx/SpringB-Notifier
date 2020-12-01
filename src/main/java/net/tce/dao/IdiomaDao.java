package net.tce.dao;

import java.util.List;

import net.tce.dto.IdiomaDto;
import net.tce.model.Idioma;

public interface IdiomaDao extends PersistenceGenericDao<Idioma, Object>{
	
	List<IdiomaDto> getByPersona(Long idPersona);
	List<IdiomaDto> getByPosicion(Long idPosicion, Long idPoliticaIdioma);
}
