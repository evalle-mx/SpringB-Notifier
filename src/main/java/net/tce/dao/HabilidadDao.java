package net.tce.dao;

import java.util.List;

import net.tce.dto.PerfilNgramDto;
import net.tce.model.Habilidad;

public interface HabilidadDao extends PersistenceGenericDao<Habilidad, Object>{

	 List<PerfilNgramDto> getText(long idPersona);
	 List<PerfilNgramDto> getTextSimiPos(long idHabilidadPos);
}
