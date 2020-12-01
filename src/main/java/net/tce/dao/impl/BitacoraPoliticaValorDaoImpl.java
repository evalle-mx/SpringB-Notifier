package net.tce.dao.impl;

import net.tce.dao.BitacoraPoliticaValorDao;
import net.tce.model.BitacoraPoliticaValor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;


@Repository("bitacoraPoliticaValorDao")
public class BitacoraPoliticaValorDaoImpl extends PersistenceGenericDaoImpl<BitacoraPoliticaValor, Object> 
implements BitacoraPoliticaValorDao{
	Logger log4j = Logger.getLogger( this.getClass());

	

}
