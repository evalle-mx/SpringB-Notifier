package net.tce.dao;

import java.util.List;

import net.tce.dto.PerfilNgramDto;
import net.tce.model.PerfilTextoNgram;

public interface PerfilTextoNgramDao extends PersistenceGenericDao<PerfilTextoNgram, Object>{

	List<PerfilNgramDto> getText(long idPerfil);
	void deleteByPerfil(Long id);
}
