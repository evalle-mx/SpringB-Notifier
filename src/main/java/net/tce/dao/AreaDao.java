package net.tce.dao;

import java.util.List;

import net.tce.model.Area;

public interface AreaDao extends PersistenceGenericDao<Area, Object>{

	public List<Long> getParents(Long idArea);	
	
}
