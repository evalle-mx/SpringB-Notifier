package net.tce.dao;

import net.tce.model.Perfil;

public interface PerfilDao   extends PersistenceGenericDao<Perfil, Object>{

	Perfil getPerfilByIdClassDoc(String idTextoClasificacion);	

}


