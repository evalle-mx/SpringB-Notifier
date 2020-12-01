package net.tce.dao.impl;

import net.tce.dao.AreaEmpresaDao;
import net.tce.model.AreaEmpresa;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("areaEmpresaDao")
public class AreaEmpresaDaoImpl extends PersistenceGenericDaoImpl< AreaEmpresa, Object> 
implements AreaEmpresaDao {

	

	/**
     * Remueve todos los objetos
	 *@param none
	 */
	@Transactional
	public void deleteAllRecordsByEmpresa(Long idEmpresa){
		String hql = String.format("delete from %s","AreaEmpresa where empresa.idEmpresa = :idEmpresa");
		Query query = this.getSession().createQuery(hql).setLong("idEmpresa", idEmpresa);	
		query.executeUpdate();
	}	
}
