package net.tce.dao.impl;

import org.springframework.stereotype.Repository;

import net.tce.dao.BitacoraTrackContDao;
import net.tce.model.BitacoraTrackCont;

@Repository("bitacoraTrackContDao")
public class BitacoraTrackContDaoImpl extends PersistenceGenericDaoImpl<BitacoraTrackCont, Object> 
implements BitacoraTrackContDao{

}
