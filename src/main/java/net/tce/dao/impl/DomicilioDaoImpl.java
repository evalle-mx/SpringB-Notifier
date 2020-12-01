package net.tce.dao.impl;


import org.springframework.stereotype.Repository;
import net.tce.dao.DomicilioDao;
import net.tce.model.Domicilio;

@Repository("domicilioDao")
public class DomicilioDaoImpl extends PersistenceGenericDaoImpl<Domicilio, Object> 
implements DomicilioDao {

	
	/**
     * Borra el registro de la empresa en la tabla dada
     * @param filtros, es un objeto map que contiene el idEmpresa y el nombre de la tabla
     */
	public void deleteByPosicion(Long id) {
		log4j.debug("<deleteByPosicion> Posicion id="+id);
		this.getSession().createQuery(new StringBuilder("delete from ").
				append(getType().getName()).
				append(" where  posicion.idPosicion = :idPosicion ").toString()).
				setLong("idPosicion", id).executeUpdate();
	}
}
