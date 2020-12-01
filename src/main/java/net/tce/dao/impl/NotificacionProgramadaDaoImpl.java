package net.tce.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import net.tce.dao.NotificacionProgramadaDao;
import net.tce.dto.NotificacionProgramadaDto;
import net.tce.model.NotificacionProgramada;
//import net.tce.util.Constante;

@Repository("notificacionProgramadaDao")
public class NotificacionProgramadaDaoImpl extends PersistenceGenericDaoImpl<NotificacionProgramada, Object> 
implements NotificacionProgramadaDao {
	
	Logger log4j = Logger.getLogger( this.getClass());

	StringBuilder sb;
	
	/**
	* 
	* @param Lista de Notificaciones programadas (con registro relacionado tipo_evento activo = true)
	* @return 
	*/
	@SuppressWarnings("unchecked")
	public List<NotificacionProgramada> getModel(boolean enviada,boolean tipoEventoActivo ) {
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
	 * Lista de Notificaciones programadas por Parametros (prioridad, intentos, enviada)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<NotificacionProgramada> getModel(Long prioridad, Long intentos, Boolean enviada) {
		sb = new StringBuilder("SELECT NP ")						
				.append("FROM NotificacionProgramada AS NP ")
				.append("INNER JOIN NP.tipoEvento AS TE ")
				.append("WHERE NP.idNotificacionProgramada is not null ");
						
				if(prioridad!=null){
					sb.append(" AND TE.prioridad = :prioridad ");
				}
				if(intentos!=null){
					sb.append(" AND NP.intentos = :intentos ");
				}
				if(enviada!=null){
					sb.append(" AND NP.enviada = :enviada ");
				}
				sb.append(" ORDER BY NP.idNotificacionProgramada ");
				
				log4j.debug("<get> HQL: " + sb.toString() );
				
				Query qr = this.getSession().createQuery(
						sb.toString());
				
				if(prioridad!=null){
					qr.setLong("prioridad", prioridad);
				}
				if(intentos!=null){
					qr.setLong("intentos", intentos);
				}
				if(enviada!=null){
					qr.setBoolean("enviada", enviada);
				}
		return (List<NotificacionProgramada>)qr.list();
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

	/**
	 * Lista de Notificaciones programadas en DTO por Parametros (prioridad, intentos, enviada)
	 */
	@Override
	public List<NotificacionProgramadaDto> get(Long prioridad, Long intentos, Boolean enviada) {
		log4j.debug("<get>");
//		List<NotificacionProgramadaDto> listDto = new ArrayList<NotificacionProgramadaDto>();
		sb = new StringBuilder("SELECT new net.tce.dto.NotificacionProgramadaDto(")
		.append("NP.idNotificacionProgramada, TE.idTipoEvento, ")			
		.append("TE.claveEvento, TE.prioridad, TE.texto, NP.fechaCreacion, ")
		.append("NP.json, NP.intentos,  NP.enviada ) ")
				
		.append("FROM NotificacionProgramada AS NP ")
		.append("INNER JOIN NP.tipoEvento AS TE ")
		.append("WHERE NP.idNotificacionProgramada is not null \n");
				
		if(prioridad!=null){
			sb.append(" AND TE.prioridad = :prioridad ");
		}
		if(intentos!=null){
			sb.append(" AND NP.intentos = :intentos ");
		}
		if(enviada!=null){
			sb.append(" AND NP.enviada = :enviada ");
		}
		sb.append(" ORDER BY NP.idNotificacionProgramada ");
		
		log4j.debug("<get> HQL: " + sb.toString() );
		
		Query qr = this.getSession().createQuery(
				sb.toString());
		
		if(prioridad!=null){
			qr.setLong("prioridad", prioridad);
		}
		if(intentos!=null){
			qr.setLong("intentos", intentos);
		}
		if(enviada!=null){
			qr.setBoolean("enviada", enviada);
		}
		
		return (List<NotificacionProgramadaDto>)qr.list();
	}	

}
