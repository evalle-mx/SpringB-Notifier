package net.tce.dao;

import net.tce.model.EmpresaConf;


public interface EmpresaConfDao extends PersistenceGenericDao<EmpresaConf, Object>{

	Long getEmpresa(String idEmpresaConf);
}
