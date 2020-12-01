package net.tce.dao.impl;

import org.springframework.stereotype.Repository;
import net.tce.dao.BitacoraMonitorDao;
import net.tce.model.BitacoraMonitor;

@Repository("bitacoraMonitorDao")
public class BitacoraMonitorDaoImpl extends PersistenceGenericDaoImpl<BitacoraMonitor, Object> 
implements BitacoraMonitorDao {

}
