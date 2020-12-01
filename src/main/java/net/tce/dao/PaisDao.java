package net.tce.dao;

import java.util.List;

import net.tce.dto.PaisDto;
import net.tce.model.Pais;



public interface PaisDao extends PersistenceGenericDao<Pais, Object>{
	List<PaisDto> getPais(String nombre)throws Exception;
}
