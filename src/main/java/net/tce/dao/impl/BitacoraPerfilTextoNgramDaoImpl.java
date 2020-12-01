package net.tce.dao.impl;

import net.tce.dao.BitacoraPerfilTextoNgramDao;
import net.tce.model.BitacoraPerfilTextoNgram;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("bitacoraPerfilTextoNgramDao")
public class BitacoraPerfilTextoNgramDaoImpl extends PersistenceGenericDaoImpl<BitacoraPerfilTextoNgram, Object> 
implements BitacoraPerfilTextoNgramDao{
	Logger log4j = Logger.getLogger( this.getClass());

	

}
