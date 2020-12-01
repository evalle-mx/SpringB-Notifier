package net.tce.dao;

import java.util.List;

import net.tce.model.AreaPerfil;

public interface AreaPerfilDao extends PersistenceGenericDao<AreaPerfil, Object>{

	void deleteAllRecordsByPerfil(Long idPerfil);	
	List<AreaPerfil> get(String idPerfiles);
}
