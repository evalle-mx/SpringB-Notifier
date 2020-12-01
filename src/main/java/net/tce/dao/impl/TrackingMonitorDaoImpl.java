package net.tce.dao.impl;

import java.util.List;
import net.tce.dao.TrackingMonitorDao;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.dto.TrackingMonitorDto;
import net.tce.model.TrackingMonitor;

import org.springframework.stereotype.Repository;

@Repository("trackingMonitorDao")
public class TrackingMonitorDaoImpl extends PersistenceGenericDaoImpl<TrackingMonitor, Object> 
implements TrackingMonitorDao {

	/**
	 * Se obtiene una lista de objetos  ModeloRscPosFaseDto enfocada a TrackingMonitor
	 * @param idModeloRscPos, identificador de ModeloRscPos
	 * @param idPosicion, identificador de Posicion
	 * @return lista de objetos  ModeloRscPosFase
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeloRscPosFaseDto> get(Long idModeloRscPos,
			Long idPosicion) {
		return (List<ModeloRscPosFaseDto>)getSession().createQuery(
				new StringBuilder("SELECT new net.tce.dto.ModeloRscPosFaseDto( ")
				.append("MRSCPF.idModeloRscPosFase, TP.idTrackingPostulante,REP.idRelacionEmpresaPersona,")
				.append("ET.idEstadoTracking,TM.comentario,TM.fechaInicio,TM.fechaFin, ")
				.append("PCAN.idPosibleCandidato,CAN.idCandidato,MRSCPF_POST.idModeloRscPosFase) ")
				.append("from Monitor as MON ")
				.append("inner join MON.modeloRscPosFase as MRSCPF ")
				.append("inner join MON.trackingMonitors TM ")
				.append("left outer join TM.trackingPostulante as TP ")
				.append("inner join TM.estadoTracking as ET ")
				.append("inner join TM.relacionEmpresaPersona as REP ")
				.append("inner join MRSCPF.modeloRscPos as MRSCP ")
				.append("inner join MRSCP.perfilPosicion as PP ")
				.append("inner join PP.posicion as POS ")
				.append("left outer join TP.candidato CAN ")
				.append("left outer join TP.posibleCandidato PCAN ")
				.append("left outer join TP.modeloRscPosFase MRSCPF_POST ")
				//.append("where MON.principal=true ")
				.append("where MRSCP.monitor=true ")
				.append("and POS.idPosicion =:idPosicion ")
				.append("and MRSCP.idModeloRscPos =:idModeloRscPos ")
				.append("order by REP.idRelacionEmpresaPersona, MRSCPF.orden,MRSCPF.modeloRscPosFase ").toString())
				.setLong("idPosicion", idPosicion)
				.setLong("idModeloRscPos", idModeloRscPos).list();
	}

	

	
	/**
	 * Se obtiene una lista de objetos  TrackingMonitorDto de todos los TrackingMonitor
	 * @param idPosicion, identificador de Posicion
	 * @return List<TrackingMonitorDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingMonitorDto> getAllByPosicion(Long idPosicion) {
		return (List<TrackingMonitorDto>)getSession().createQuery(
				new StringBuilder("SELECT new net.tce.dto.TrackingMonitorDto(EDOT.idEstadoTracking , ")
				.append(" MRPF.orden , MRPF.actividad,TPOST.idTrackingPostulante,TMON.comentario, ")
				.append(" TMON.fechaInicio, TMON.fechaFin,MON.idMonitor ) ")
				.append("from ModeloRscPos MRP ")
				.append("inner join MRP.modeloRscPosFases MRPF ")
				.append("inner join MRPF.monitors MON  ")
				.append("inner join MON.trackingMonitors TMON  ")
				.append("inner join MRP.perfilPosicion PP ")
				.append("inner join PP.posicion POS ")
				.append("inner join TMON.estadoTracking EDOT ")
				.append("inner join TMON.relacionEmpresaPersona REP_POS ")
				.append("left outer join TMON.trackingPostulante TPOST  ")
				.append("where  POS.idPosicion = :idPosicion ")
				.append("and MRP.activo = true ")
				.append("and MRPF.activo = true  ")
				.append("order by MRPF.orden, MRPF.actividad, REP_POS.idRelacionEmpresaPersona ").toString())
				.setLong("idPosicion", idPosicion).list();
	}

	/**
	 * Se obtiene un objeto  TrackingMonitorDto(postulante) dado el idTrackingPostulante
	 * @param idTrackingPostulante, identificador del monitor
	 * @return TrackingMonitorDto
	 */
	@Override
	public TrackingMonitorDto getByPostulante(Long idTrackingPostulante){
		return (TrackingMonitorDto)getSession().createQuery(
				new StringBuilder("SELECT new net.tce.dto.TrackingMonitorDto(TMON.enGrupo,")
				.append("(COALESCE(P_POST.nombre,'') || ' ' || COALESCE(P_POST.apellidoPaterno,'')  ")
				.append("|| ' ' || COALESCE(P_POST.apellidoMaterno,'')) ) ")
				.append("from TrackingMonitor TMON ")
				.append("inner join TMON.trackingPostulante TPOST ")
				.append("inner join TPOST.candidato CAND ")
				.append("inner join CAND.persona P_POST ")
				.append("where TPOST.idTrackingPostulante = :idTrackingPostulante ").toString())
				.setLong("idTrackingPostulante", idTrackingPostulante).uniqueResult();
	}
	
	/**
	 * Se obtiene el numero de tracks  al estatustracking dado, correspondientes al candidato
	 * @param idCandidato, es el identificador del candidato
	 * @param idEstadoTracking, es el identificador EstadoTracking
	 */
	@Override
	public Long countByPosREPPost( Long idCandidato, Long idEstadoTracking) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("SELECT count(TMON.idTrackingMonitor) ")
				.append("from  ModeloRscPos MRP ")
				.append("inner join MRP.modeloRscPosFases MRPF ")
				.append("inner join MRPF.monitors MON ")
				.append("inner join MON.trackingMonitors TMON ")				
				.append("inner join TMON.estadoTracking EDOT ")
				.append("inner join TMON.relacionEmpresaPersona REP_POST ")
				.append("inner join REP_POST.persona PER ")
				.append("inner join PER.candidatos CAND ")
				.append("where  CAND.idCandidato = :idCandidato ")
				.append("and EDOT.idEstadoTracking = :idEstadoTracking ").toString()).
				setLong("idCandidato", idCandidato).
				setLong("idEstadoTracking", idEstadoTracking).uniqueResult();
	}

	/**
	 * Se obtiene una lista de estatus tracking de los ordenes(tracking monitor) posteriores 
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
				.append("inner join TMON.relacionEmpresaPersona REPPOST ")
				.append("inner join TMON.estadoTracking EDOT ")	
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
	 * Se obtiene una lista de estatus tracking de las actividades(tracking monitor)
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
				.append("inner join TMON.relacionEmpresaPersona REPPOST ")
				.append("inner join TMON.estadoTracking EDOT ")				
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

	/**
	 * Se obtiene el numero de registros en la tabla tracking_monitor dependiendo del ModeloRscPos 
	 * @param idModeloRscPos, el identificador del ModeloRscPos
	 * @return Long
	 */
	@Override
	public Long countByMRSCPos(Long idModeloRscPos) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("SELECT count(TMON.idTrackingMonitor) ")
				.append("from ModeloRscPos as MRSCP ")
				.append("inner join MRSCP.modeloRscPosFases as MRSCPF ")
				.append("inner join MRSCPF.monitors MON ")
				.append("inner join MON.trackingMonitors TMON ")
				.append("where  MRSCP.monitor=true ")
				.append("and MRSCPF.activo=true ")
				.append("and MRSCP.idModeloRscPos = :idModeloRscPos ").toString()).
				setLong("idModeloRscPos", idModeloRscPos).uniqueResult();
	}
}
