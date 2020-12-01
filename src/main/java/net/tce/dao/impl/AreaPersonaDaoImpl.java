package net.tce.dao.impl;

import java.util.List;
import net.tce.dao.AreaPersonaDao;
import net.tce.model.AreaPersona;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("areaPersonaDao")
public class AreaPersonaDaoImpl extends PersistenceGenericDaoImpl<AreaPersona, Object> 
implements AreaPersonaDao {

	

	/**
     * Remueve todos los objetos
	 *@param none
	 */
	@Transactional
	public void deleteAllRecordsByPerson(Long idPersona){
		String hql = String.format("delete from %s","AreaPersona where persona.idPersona = :idPersona");
		Query query = this.getSession().createQuery(hql).setLong("idPersona", idPersona);	
		query.executeUpdate();
	}

	/**
	 * Se obtiene una lista de objetos de area_persona
	 *  @param idPersonas, id's de persoan
	 */
	@SuppressWarnings("unchecked")
	public List<AreaPersona> get(String idPersonas) {
		return  this.getSession().createQuery(
			new StringBuilder("select AP ")
			.append(" from AreaPersona as AP ")
			.append(" inner join AP.persona as PER ")
			.append(" inner join AP.area as AR ")
			.append(" where PER.idPersona in (")
			.append(idPersonas)
			.append(") order by PER.idPersona, AP.principal desc ").toString()).list();
	}

	/**
	 * Se obtiene una lista de objetos de area_persona dados idPersona y idArea
	 * @param idPersona, identificador de persona
	 * @param idArea, identificador de area
	 */
	@Override
	public AreaPersona read(Long idPersona, Long idArea) {
		return (AreaPersona) this.getSession().createQuery(
				new StringBuilder("select AP ")
				.append(" from AreaPersona as AP ")
				.append(" inner join AP.persona as PER ")
				.append(" inner join AP.area as AR ")
				.append(" where PER.idPersona = :idPersona")
				.append(" and AP.confirmada = false ")
				.append(" and AR.idArea = :idArea ").toString())
				.setLong("idPersona", idPersona)
				.setLong("idArea", idArea)
				.uniqueResult();
	}
		

	/**
     * numero de objetos Area_Persona con estatus confirmada=true correspondiente al idPersona  
	 *@param idPersona, identificador de la persona
	 */
	@Override
	public Long countConfirmado(Long idPersona) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select count(*) ")
				.append(" from AreaPersona as AP ")
				.append(" inner join AP.persona as PER ")
				.append(" where PER.idPersona = :idPersona ")
				.append(" and AP.confirmada = true ").toString())
				.setLong("idPersona", idPersona).uniqueResult();
	}
	

}
