package net.tce.dao.impl;


import org.springframework.stereotype.Repository;
import net.tce.dao.EmpresaConfDao;
import net.tce.model.EmpresaConf;

@Repository("empresaConfDao")
public class EmpresaConfDaoImpl extends PersistenceGenericDaoImpl<EmpresaConf, Object> 
implements EmpresaConfDao {

	/**
	 * Se obtiene el idEmpresa  correspondiente al idEmpresaConf
	 * @param idEmpresaConf
	 */
	public Long getEmpresa(String idEmpresaConf) {
		return (Long) getSession().createQuery(
				new StringBuilder("select  EMPRESA.idEmpresa ")
				.append("from EmpresaConf as EMPRESACONF ")
				.append("inner join EMPRESACONF.empresa  as EMPRESA ")
				.append("where EMPRESACONF.idEmpresaConf = :idEmpresaConf ").toString())
				.setLong("idEmpresaConf", Long.valueOf(idEmpresaConf)).uniqueResult();
	}
	
	
}
