package net.tce.dao.impl;

import net.tce.dao.BitacoraDomicilioDao;
import net.tce.model.BitacoraDomicilio;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("bitacoraDomicilioDao")
public class BitacoraDomicilioDaoImpl extends PersistenceGenericDaoImpl<BitacoraDomicilio, Object> 
implements BitacoraDomicilioDao{
	Logger log4j = Logger.getLogger( this.getClass());

}
