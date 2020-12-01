package net.tce.dao;

import java.util.List;

import net.tce.dto.PerfilPosicionDto;
import net.tce.model.PerfilPosicion;

public interface PerfilPosicionDao extends PersistenceGenericDao<PerfilPosicion, Object>{

	PerfilPosicion getInterno(Long idPosicion);
	List<PerfilPosicionDto> get(Long idPosicion);
}
