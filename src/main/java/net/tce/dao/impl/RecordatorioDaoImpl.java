package net.tce.dao.impl;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import net.tce.dao.RecordatorioDao;
import net.tce.dto.RecordatorioDto;
import net.tce.model.Recordatorio;
import net.tce.util.Constante;
import net.tce.util.DateUtily;

@Repository("recordatorioDao")
public class RecordatorioDaoImpl extends PersistenceGenericDaoImpl<Recordatorio, Object> 
implements RecordatorioDao {
	
	final Logger log4jSN = Logger.getLogger("SCHED_REMINDER");
	StringBuilder sb;

	/**
	 * Se regresa una lista de objetos recordatorioDto, del monitor, dando la fecha correspondiente
	 * @param fecha, fecha hacia atras 
	 * @return List<RecordatorioDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RecordatorioDto> getByDateMonitor(Date fecha) {
		return (List<RecordatorioDto>) getSession().createQuery(
				new StringBuilder("SELECT new net.tce.dto.RecordatorioDto(RCD.idRecordatorio, ")
				.append("MON.idMonitor,TMON.idTrackingMonitor,TPOS.idTrackingPostulante,P_MON.idPersona, ")
				.append("POS.idPosicion,POS.nombre, MRSCF.nombre, TMON.fechaInicio,TMON.fechaFin,")
				.append("(COALESCE(P_MON.nombre,'') || ' ' || COALESCE(P_MON.apellidoPaterno,'') ")
				.append("|| ' ' || COALESCE(P_MON.apellidoMaterno,'')), REP_MON.idRelacionEmpresaPersona ")
				.append(") from Recordatorio RCD ")
				.append("inner join RCD.trackingMonitor TMON ")
				.append("inner join TMON.monitor MON ")
				.append("inner join MON.relacionEmpresaPersona REP_MON ")
				.append("inner join REP_MON.persona P_MON ")
				.append("inner join MON.modeloRscPosFase MRSCF ")
				.append("inner join MRSCF.modeloRscPos MRSC ")
				.append("inner join MRSC.perfilPosicion PPOS ")
				.append("inner join PPOS.posicion POS ")
				.append("left outer join TMON.trackingPostulante TPOS ")
				.append("where RCD.fecha <= '")
				.append(DateUtily.thisDateFormated(
				  		fecha, Constante.DATE_FORMAT_JAVA))
				.append("' and RCD.activo=true ")
				.append("and RCD.seAplico=false ")
				.append("order by RCD.cifra ").toString()).list();
	}

	
	/**
	 * Se modifica el estatus seAplico
	 * @param idRecordatorio, identificador del recordatorio
	 * @param seAplico, estatus
	 * @return List<RecordatorioDto>
	 */
	@Override
	public int update(long idRecordatorio, boolean seAplico) {
		return getSession().createQuery(
				new StringBuilder("update Recordatorio set seAplico = :seAplico ")
				.append(" where  idRecordatorio = :idRecordatorio ").toString()).
				setBoolean("seAplico", seAplico).
				setLong("idRecordatorio", idRecordatorio).
				executeUpdate();
	}


	/**
	 * Se regresa una lista de objetos recordatorioDto, del postulante, dando la fecha correspondiente
	 * @param fecha, fecha hacia atras 
	 * @return List<RecordatorioDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RecordatorioDto> getByDatePostulante(Date fecha) {
		return (List<RecordatorioDto>) getSession().createQuery(
				new StringBuilder("SELECT new net.tce.dto.RecordatorioDto(RCD.idRecordatorio, ")
				.append("P_POST.idPersona,POS.idPosicion,POS.nombre, MRSCF.nombre, TMON.fechaInicio,TMON.enGrupo,")
				.append("(COALESCE(P_MON.nombre,'') || ' ' || COALESCE(P_MON.apellidoPaterno,'') ")
				.append("|| ' ' || COALESCE(P_MON.apellidoMaterno,'')), REP_MON.idRelacionEmpresaPersona ")
				.append(") from Recordatorio RCD ")
				.append("inner join RCD.trackingPostulante  TPOST ")
				.append("inner join TPOST.trackingMonitors TMON ")
				.append("inner join TMON.monitor MON ")
				.append("inner join MON.relacionEmpresaPersona REP_MON ")
				.append("inner join REP_MON.persona P_MON ")
				.append("inner join MON.modeloRscPosFase MRSCF ")
				.append("inner join MRSCF.modeloRscPos MRSC ")
				.append("inner join MRSC.perfilPosicion PPOS ")
				.append("inner join PPOS.posicion POS ")
				.append("inner join TPOST.candidato CAN_POST ")
				.append("inner join CAN_POST.persona P_POST ")
				.append("where RCD.fecha <= '")
				.append(DateUtily.thisDateFormated(
				  		fecha, Constante.DATE_FORMAT_JAVA))
				.append("' and RCD.activo=true ")
				.append("and RCD.seAplico=false ")
				.append("order by RCD.cifra ").toString()).list();
	}
	


}
