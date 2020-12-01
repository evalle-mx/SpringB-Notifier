package net.tce.dao.impl;

import org.springframework.stereotype.Repository;
import net.tce.dao.BitacoraTrackMonDao;
import net.tce.model.BitacoraTrackMon;

@Repository("bitacoraTrackMonDao")
public class BitacoraTrackMonDaoImpl extends PersistenceGenericDaoImpl<BitacoraTrackMon, Object> 
implements BitacoraTrackMonDao {

}
