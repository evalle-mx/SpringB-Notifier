package net.tce.dao.impl;

import org.springframework.stereotype.Repository;
import net.tce.dao.TipoEventoDao;
import net.tce.model.TipoEvento;

@Repository("tipoEventoDao")
public class TipoEventoDaoImpl extends PersistenceGenericDaoImpl<TipoEvento, Object> 
	implements TipoEventoDao {
	
	public Long getIdTipoEvento(String claveEvento) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select TP.idTipoEvento  ")
				.append(" from TipoEvento as TP  ")
				.append(" where TP.claveEvento = :claveEvento ").toString())
				.setString("claveEvento", claveEvento).uniqueResult();
	}
	
}
