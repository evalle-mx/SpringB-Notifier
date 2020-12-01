package net.tce.dao.impl;

import java.util.ArrayList;
import java.util.List;
import net.tce.dao.AreaDao;
import net.tce.model.Area;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("areaDao")
public class AreaDaoImpl extends PersistenceGenericDaoImpl<Area, Object> 
implements AreaDao {

	Logger log4j = Logger.getLogger( this.getClass());
	
	

	/**
     * Obtiene las áreas padre (ancestros) de un área específica, la lista final incluye al área proporcionada
	 *@param none
	 *@author osy
	 */
	@Transactional(readOnly=true)
	public List<Long> getParents(Long idArea){
		List<Long> parentsList = new ArrayList<Long>();
		parentsList.add(idArea);
		return getParentsList(idArea, parentsList);
	}
	
	/**
     * Obtiene de manera recursiva los ancestros de las áreas
	 *@param none
	 *@author osy
	 */
	@Transactional(readOnly=true)
	public List<Long> getParentsList(Long idArea, List<Long> currentList){
		log4j.info("<getParentsList> Inicio idArea :" + idArea);
		
		List<Long> parentsList = currentList;
		parentsList.add(idArea);
		String hql = String.format("select area.idArea from Area where idArea = :idArea");
		Query query = this.getSession().createQuery(hql).setLong("idArea", idArea);
		Long result = (Long) query.uniqueResult();
		if (result != null){
			log4j.info("<getParentsList> result not null current parentsList size :" + parentsList.size());
			log4j.info("<getParentsList> result not null call getParentsList with :" + result);
			return getParentsList(result, parentsList);
		}
		else
			log4j.info("<getParentsList> result null return parentsList size :" + parentsList.size());
			return parentsList;
	}
}
