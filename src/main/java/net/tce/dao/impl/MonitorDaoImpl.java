package net.tce.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import net.tce.dao.MonitorDao;
import net.tce.dto.TrackingMonitorDto;
import net.tce.model.Monitor;

@Repository("monitorDao")
public class MonitorDaoImpl extends PersistenceGenericDaoImpl<Monitor, Object> 
implements MonitorDao {
	Logger log4j = Logger.getLogger( this.getClass());
	
	/**
	 * Se obtiene el numero de tracks referente al idModeloRscPos y persona
	 * @param idModeloRscPos, el identificador del ModeloRscPos
	 * @param idRelacionEmpresaPersona, el identificador del RelacionEmpresaPersona
	 * @return numero de tracks
	 */
	@Override
	public Long countTracks(Long idModeloRscPos, Long idRelacionEmpresaPersona) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select count(*) ")
				.append(" from ModeloRscPos as MRSCP ")
				.append(" inner join  MRSCP.modeloRscPosFases MRSCPF ")
				.append(" inner join  MRSCPF.monitors MON ")
				.append(" inner join  MON.relacionEmpresaPersona REP ")
				.append(" where MRSCP.idModeloRscPos = :idModeloRscPos  ")
				.append(" and REP.idRelacionEmpresaPersona= :idRelacionEmpresaPersona ").toString())
				.setLong("idRelacionEmpresaPersona", idRelacionEmpresaPersona)
				.setLong("idModeloRscPos", idModeloRscPos).uniqueResult();
	}


	/**
	 * Se obtiene el numero de fases del monitor principal
	 * @param idModeloRscPos, el identificador del ModeloRscPos
	 * @param idPosicion, el identificador de la posicion
	 * @return numero de tracks
	 */
	@Override
	public Long getMonitorPrincipal( Long idPosicion) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select DISTINCT PER.idPersona ")
				.append("from Monitor as MON ")
				.append("inner join MON.modeloRscPosFase as MRSCPF ")
				.append("inner join MON.relacionEmpresaPersona AS REP ")
				.append("inner join REP.persona as PER ")
				.append("inner join MRSCPF.modeloRscPos as MRSCP ")
				.append("inner join MRSCP.perfilPosicion as PP ")
				.append("inner join PP.posicion as POS ")
				.append("where MON.principal=true ")
				.append("and POS.idPosicion = :idPosicion ").toString())
				.setLong("idPosicion", idPosicion).uniqueResult();
	}


	/**
	 * Se obtiene una lista de monitores dependiendo el idModeloRscPos
	 * @param idModeloRscPos, el identificador del ModeloRscPos
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingMonitorDto> getMonitorPersona(
			Long idModeloRscPos) {
		return (List<TrackingMonitorDto>) this.getSession().createQuery(
				new StringBuilder("select DISTINCT new net.tce.dto.TrackingMonitorDto( ")
				.append("PER.idPersona,MON.principal )")
				.append("from Monitor as MON ")
				.append("inner join MON.modeloRscPosFase as MRSCF  ")
				.append("inner join MON.relacionEmpresaPersona AS REP ")
				.append("inner join REP.persona as PER ")
				.append("inner join MRSCF.modeloRscPos as MRSC  ")
				.append("where MRSC.idModeloRscPos = :idModeloRscPos ").toString())
				.setLong("idModeloRscPos", idModeloRscPos).list();
	}


	/**
	 * Se borran los registros en la tabla Monitor,dado el idModeloRscPos
	 * @param idModeloRscPos, el identificador del ModeloRscPos
	 */
	@Override
	public void deleteByidModeloRscPos(Long idModeloRscPos) {
		this.getSession().createQuery(
				new StringBuilder("DELETE FROM Monitor as MON ").
				append("where MON.idMonitor in(").
				append("select MON1.idMonitor ").
				append("from Monitor as MON1  ").
				append("inner join MON1.modeloRscPosFase as MRSCF ").
				append("inner join MRSCF.modeloRscPos as MRSC ").
				append("WHERE MRSC.idModeloRscPos = :idModeloRscPos )").toString()).
				setLong("idModeloRscPos",(Long)idModeloRscPos).executeUpdate();
	}

	/**
	 * Se obtiene el numero de registros monitor dependiendo la posicion y la persona
	 * @param idPosicion, el identificador de la posicion
	 * @param idPersona, el identificador de la persona posible monitor
	 * @return Long
	 */
	@Override
	public Long countMonitor(Long idPosicion, Long idPersona) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select count(*) ")
				.append("from ModeloRscPos as MRSCP ")
				.append("inner join  MRSCP.perfilPosicion as PP ")
				.append("inner join  PP.posicion as POS ")
				.append("inner join  MRSCP.modeloRscPosFases as MRSCPF  ")
				.append("inner join  MRSCPF.monitors as MNT ")
				.append("inner join  MNT.relacionEmpresaPersona as REP ")
				.append("inner join  REP.persona as P ")
				.append(" where POS.idPosicion =:idPosicion ")
				.append(" and P.idPersona =:idPersona ").toString())
				.setLong("idPosicion", idPosicion)
				.setLong("idPersona", idPersona).uniqueResult();
	}

	/**
	 * Se obtiene todos los registros monitor de la posición
	 * @param idPosicion, el identificador de la posicion
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Monitor> getAllMonitors(Long idPosicion) {
		return (List<Monitor>) this.getSession().createQuery(
				new StringBuilder("select MON ")
				.append("from Monitor as MON ")
				.append("inner join MON.modeloRscPosFase as MRSCPF ")
				.append("inner join MON.relacionEmpresaPersona AS REP ")
				.append("inner join REP.persona as PER ")
				.append("inner join MRSCPF.modeloRscPos as MRSCP ")
				.append("inner join MRSCP.perfilPosicion as PP ")
				.append("inner join PP.posicion as POS ")
				.append("where POS.idPosicion =:idPosicion ")
				.append("order by REP.idRelacionEmpresaPersona, MON.idMonitor ").toString())
				.setLong("idPosicion", idPosicion).list();
	}

}
