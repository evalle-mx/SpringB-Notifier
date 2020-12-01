package net.tce.dao.impl;

import net.tce.dao.TrackingPostulanteDao;
import net.tce.model.TrackingPostulante;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("trackingPostulanteDao")
public class TrackingPostulanteDaoImpl extends PersistenceGenericDaoImpl<TrackingPostulante, Object> 
implements TrackingPostulanteDao {
	

	/**
	 * Se modifica idCandidato
	 * @param idCandidato, el identificador del candidato
	 * @param idPosibleCandidato, el identificador posibleCandidato
	 * @return 
	 */
	@Override
	public int updateCandidate(long idCandidato, long idPosibleCandidato) {
		return  getSession().createSQLQuery(
				new StringBuilder("UPDATE tracking_postulante SET id_candidato=").
				append(idCandidato).append(" WHERE id_posible_candidato =").
				append(idPosibleCandidato).toString()).executeUpdate();  
	}

	/**
	 * Se obtiene el numero de registros referente al candidato dado
	 * @param idCandidato, el identificador del candidato
	 * @return 
	 */
	@Override
	public Long countRecordsCandidates(Long idCandidato) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select count(*) ")
				.append(" from TrackingPostulante as TEP ")
				.append(" inner join TEP.candidato as C ")
				.append(" where C.idCandidato = :idCandidato ").toString())
				.setLong("idCandidato", idCandidato).uniqueResult();
	}

	/**
	 * Se obtiene el numero de registros referente al posible candidato dado
	 * @param idPosibleCandidato, el identificador del posible candidato
	 * @return 
	 */
	@Override
	public Long countRecordsPossibleCandidates(Long idPosibleCandidato) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select count(*) ")
				.append(" from TrackingPostulante as TEP ")
				.append(" inner join TEP.posibleCandidato as PS ")
				.append(" where PS.idPosibleCandidato = :idPosibleCandidato ").toString())
				.setLong("idPosibleCandidato", idPosibleCandidato).uniqueResult();
	}

	/**
	 * Se modifica idEstadoTracking del tracking postulante
	 * @param idTrackingPostulante, el identificador del tracking postulante
	 * @param idEstadoTracking, el identificador del estado tracking
	 * @return int
	 */
	@Override
	public int updateEdoTracking(Long idTrackingPostulante, Long idEstadoTracking) {
		
		return  getSession().createQuery(new StringBuilder(
				"UPDATE TrackingPostulante SET estadoTracking.idEstadoTracking=").
				append(idEstadoTracking.toString()).append(" WHERE idTrackingPostulante =").
				append(idTrackingPostulante).toString()).executeUpdate();  
	}
	
	/**
	 * Se obtiene una lista de estatus tracking de los ordenes(tracking Postulante) posteriores 
	 * a la dada para el candidato correspondiente
	 * @param idPosicion, el identificador de la posición
	 * @param orden, es el numero de orden de la fase
	 * @param idRelacionEmpresaPersona, el identificador de la RelacionEmpresaPersona del candidato
	 * @return List<Long>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getOrderUpByPosRepOrder(Long idPosicion, Short orden,Long idRelacionEmpresaPersona) {
		return (List<Long>) getSession().createQuery(
				new StringBuilder("SELECT EDOT.idEstadoTracking ")
				.append("from ModeloRscPos MRP ")
				.append("inner join MRP.modeloRscPosFases MRPF ")
				.append("inner join MRPF.monitors MON ")
				.append("inner join MON.trackingMonitors TMON ")
				.append("inner join TMON.trackingPostulante TPOS  ")
				.append("inner join TMON.relacionEmpresaPersona REPPOST ")
				.append("inner join TPOS.estadoTracking EDOT ")	
				.append("inner join MRP.perfilPosicion PP  ")
				.append("inner join PP.posicion POS ")
				.append("where POS.idPosicion = :idPosicion ")
				.append("and REPPOST.idRelacionEmpresaPersona = :idREP ")
				.append("and MRPF.orden > :orden ")
				.append("order by MRPF.orden, MRPF.actividad ").toString()).
				setLong("idPosicion", idPosicion).
				setShort("orden", orden).
				setLong("idREP", idRelacionEmpresaPersona).list();
	}

	/**
	 * Se obtiene una lista de estatus tracking de las actividades(tracking Postulante)
	 *  posteriores a la dada para el candidato correspondiente
	 * @param idPosicion, el identificador de la posición
	 * @param orden, es el numero de orden de la fase
	 * @param actividad, es el numero de actividad de la fase
	 * @param idRelacionEmpresaPersona, el identificador de la RelacionEmpresaPersona del candidato
	 * @return List<Long>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getActUpByPosRepOrder(Long idPosicion,Short orden, Short actividad, 
																Long idRelacionEmpresaPersona) {
		return (List<Long>) getSession().createQuery(
				new StringBuilder("SELECT EDOT.idEstadoTracking ")
				.append("from ModeloRscPos MRP   ")
				.append("inner join MRP.perfilPosicion PP  ")
				.append("inner join PP.posicion POS ")
				.append("inner join MRP.modeloRscPosFases MRPF ")
				.append("inner join MRPF.monitors MON ")
				.append("inner join MON.trackingMonitors TMON ")
				.append("inner join TMON.trackingPostulante TPOS ")
				.append("inner join TMON.relacionEmpresaPersona REPPOST ")
				.append("inner join TPOS.estadoTracking EDOT ")				
				.append("where POS.idPosicion = :idPosicion ")
				.append("and REPPOST.idRelacionEmpresaPersona = :idREP ")
				.append("and MRPF.orden = :orden ")
				.append("and MRPF.actividad > :actividad ")
				.append("order by MRPF.actividad").toString()).
				setLong("idPosicion", idPosicion).
				setLong("idREP", idRelacionEmpresaPersona).
				setShort("actividad", actividad).
				setShort("orden", orden).list();
	}
}
