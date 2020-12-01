package net.tce.dao;

import java.util.List;

import net.tce.dto.MunicipioAdyacenciaDto;
import net.tce.model.MunicipioAdyacencia;

public interface MunicipioAdyacenciaDao extends PersistenceGenericDao<MunicipioAdyacencia, Object>{

	List<MunicipioAdyacenciaDto> getAdjacency(Long idMunicipio, long rango);
}
