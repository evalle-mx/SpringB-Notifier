package net.tce.dao;


import java.util.List;

import net.tce.model.EmpresaParametro;

public interface EmpresaParametroDao extends PersistenceGenericDao<EmpresaParametro, Object>{
	
	List<EmpresaParametro> get(Long idConf, String contexto, Long idTipoParametro);
	
}
