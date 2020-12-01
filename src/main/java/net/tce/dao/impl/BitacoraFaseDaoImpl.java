package net.tce.dao.impl;

import org.springframework.stereotype.Repository;
import net.tce.dao.BitacoraFaseDao;
import net.tce.model.BitacoraFase;

@Repository("bitacoraFaseDao")
public class BitacoraFaseDaoImpl extends PersistenceGenericDaoImpl<BitacoraFase, Object> 
implements BitacoraFaseDao {

}
