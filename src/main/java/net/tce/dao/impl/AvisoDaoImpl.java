package net.tce.dao.impl;


import org.springframework.stereotype.Repository;
import net.tce.dao.AvisoDao;
import net.tce.model.Aviso;

@Repository("avisoDao")
public class AvisoDaoImpl extends PersistenceGenericDaoImpl<Aviso, Object> 
implements AvisoDao {
	
	
}
