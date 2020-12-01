package net.tce.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import net.tce.dao.BitacoraTrackDao;
import net.tce.model.BitacoraTrack;

@Repository("bitacoraTrackDao")
public class BitacoraTrackDaoImpl extends PersistenceGenericDaoImpl<BitacoraTrack, Object> 
implements BitacoraTrackDao {
	Logger log4j = Logger.getLogger( this.getClass());
}
