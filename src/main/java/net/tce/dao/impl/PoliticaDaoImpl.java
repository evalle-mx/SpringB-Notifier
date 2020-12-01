package net.tce.dao.impl;

import net.tce.dao.PoliticaDao;
import net.tce.model.Politica;
import org.springframework.stereotype.Repository;

@Repository("politicaDao")
public class PoliticaDaoImpl extends PersistenceGenericDaoImpl<Politica, Object> 
implements PoliticaDao {



}
