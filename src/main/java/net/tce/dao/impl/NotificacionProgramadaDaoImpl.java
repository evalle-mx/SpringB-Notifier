package net.tce.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import net.tce.dao.NotificacionProgramadaDao;
import net.tce.model.NotificacionProgramada;
import net.tce.util.Constante;

@Repository("notificacionProgramadaDao")
public class NotificacionProgramadaDaoImpl extends PersistenceGenericDaoImpl<NotificacionProgramada, Object> 
implements NotificacionProgramadaDao {

	
	/**
	* 
	* @param Lista de Notificaciones programadas (con registro relacionado tipo_evento activo = true)
	* @return 
	*/
	@SuppressWarnings("unchecked")
	public List<NotificacionProgramada> get(boolean enviada,boolean tipoEventoActivo ) {
		return (List<NotificacionProgramada>) this.getSession().createQuery(
				new StringBuilder("SELECT NP ").
				append("FROM NotificacionProgramada AS NP ").
				append("INNER JOIN NP.tipoEvento AS TEV ").
				append(" WHERE NP.enviada = :enviada ").
				append(" AND TEV.activo = :activo ").
				append(" ORDER BY NP.idNotificacionProgramada ").toString()).
				setBoolean("enviada", enviada).
				setBoolean("activo", tipoEventoActivo).list();
	}

	/**
	 * Se modifica el estatus enviada dando idNotificacionProgramada
	 * @param enviada
	 * @param idNotificacionProgramada
	 * @return int
	 */
	public int updateEnviado(boolean enviada,  long idNotificacionProgramada) {
		log4j.debug("<updateEnviado> actualizando notificacionProgramada ID: "+idNotificacionProgramada + " enviada: "+enviada);
		return getSession().createQuery(
				new StringBuilder("UPDATE NotificacionProgramada SET enviada = :enviada ")
				.append(" WHERE  idNotificacionProgramada = :idNotificacionProgramada ").toString()).
				setBoolean("enviada", enviada).
				setLong("idNotificacionProgramada", idNotificacionProgramada).
				executeUpdate();
	}
	

}
