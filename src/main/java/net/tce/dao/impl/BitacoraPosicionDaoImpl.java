package net.tce.dao.impl;

import net.tce.dao.BitacoraPosicionDao;
import net.tce.model.BitacoraPosicion;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("bitacoraPosicionDao")
public class BitacoraPosicionDaoImpl extends PersistenceGenericDaoImpl<BitacoraPosicion, Object> 
implements BitacoraPosicionDao {
	Logger log4j = Logger.getLogger( this.getClass());
	
	
}
