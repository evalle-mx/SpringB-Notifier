package net.tce.dao;

import net.tce.model.TipoEvento;

public interface TipoEventoDao extends PersistenceGenericDao<TipoEvento, Object>{

	 Long getIdTipoEvento(String claveEvento);
}
