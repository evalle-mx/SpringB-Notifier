package net.tce.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;
import net.tce.dao.EmpresaParametroDao;
import net.tce.model.EmpresaParametro;

@Repository("empresaParametroDao")
public class EmpresaParametroDaoImpl extends PersistenceGenericDaoImpl<EmpresaParametro, Object> 
	implements EmpresaParametroDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EmpresaParametro> get(Long idConf, String contexto, Long idTipoParametro) {
		return (List<EmpresaParametro>) this.getSession().createQuery(
				new StringBuilder("SELECT EMP  ")
				.append("from EmpresaParametro  EMP ")
				.append("inner join EMP.conf CF  ")
				.append("inner join EMP.tipoParametro TP  ")
				.append("where CF.idConf= :idConf   ")
				.append("and EMP.contexto= :contexto ")
				.append("and TP.idTipoParametro= :idTipoParametro  ")
				.append("order by EMP.descripcion  ").toString()).
				setLong("idConf", idConf).
				setString("contexto", contexto).
				setLong("idTipoParametro", idTipoParametro).list();
	}

	
}
