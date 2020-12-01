package net.tce.dao.impl;

import net.tce.dao.AmbitoGeograficoDao;
import net.tce.model.AmbitoGeografico;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("ambitoGeograficoDao")
public class AmbitoGeograficoDaoImpl extends PersistenceGenericDaoImpl<AmbitoGeografico, Object> 
implements AmbitoGeograficoDao{
	Logger log4j = Logger.getLogger( this.getClass());
	
}
