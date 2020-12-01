package net.tce.dao.impl;

import java.util.List;

import net.tce.dao.AreaPerfilDao;
import net.tce.model.AreaPerfil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("areaPerfilDao")
public class AreaPerfilDaoImpl extends PersistenceGenericDaoImpl< AreaPerfil, Object> 
implements AreaPerfilDao {

	

	/**
     * Remueve todos los objetos
	 * @param none
	 */
	@Transactional
	public void deleteAllRecordsByPerfil(Long idPerfil){
		String hql = String.format("delete from %s","AreaPerfil where perfil.idPerfil = :idPerfil");
		Query query = this.getSession().createQuery(hql).setLong("idPerfil", idPerfil);	
		query.executeUpdate();
	}

	/**
	 * Se obtiene una lista de objetos de area_perfil
	 * @param idPerfiles, ids de perfil
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AreaPerfil> get(String idPerfiles) {
		return  this.getSession().createQuery(
				new StringBuilder("select AP ")
				.append(" from AreaPerfil as AP ")
				.append(" inner join AP.perfil as P ")
				.append(" where P.idPerfil in (")
				.append(idPerfiles)
				.append(") order by P.idPerfil ").toString()).list();
	}	
	
	
}
