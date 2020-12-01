package net.tce.dao;

import java.util.List;

import net.tce.dto.AvisoDto;
import net.tce.model.AvisoCandidato;

public interface AvisoCandidatoDao extends PersistenceGenericDao<AvisoCandidato, Object>{

	List<AvisoDto> get(Long idCandidato, boolean search);
}
