package net.tce.dao.impl;

import net.tce.dao.PerfilDao;
import net.tce.model.Perfil;
import org.springframework.stereotype.Repository;

@Repository("perfilDao")
public class PerfilDaoImpl extends PersistenceGenericDaoImpl<Perfil, Object> 
	implements PerfilDao {


	
	/**
	 * Se obtiene un objeto perfil con el id del documento de clasificacion de la BD operativa
	 * @param Long idTextoClasificacion 
	 * @return objeto perfil
	 */
	public Perfil getPerfilByIdClassDoc(String idTextoClasificacion){
		return (Perfil) this.getSession().createQuery(
				new StringBuilder("from Perfil as P ").
				append(" inner join P.documentoClasificacions DC ").
				append(" where DC.idTextoClasificacion = :idTextoClasificacion").toString()).
				setString("idTextoClasificacion",new String(idTextoClasificacion)).uniqueResult();
	}
	

}
