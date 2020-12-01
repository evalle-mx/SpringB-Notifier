package net.tce.dao;

import net.tce.model.AreaEmpresa;

public interface AreaEmpresaDao extends PersistenceGenericDao<AreaEmpresa, Object>{

	public void deleteAllRecordsByEmpresa(Long idEmpresa);	

}
