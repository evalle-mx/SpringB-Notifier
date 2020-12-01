package net.tce.dao;


import net.tce.model.Domicilio;

public interface DomicilioDao extends PersistenceGenericDao<Domicilio, Object>{
	void deleteByPosicion(Long id);
}
