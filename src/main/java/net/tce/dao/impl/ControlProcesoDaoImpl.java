package net.tce.dao.impl;

import net.tce.dao.ControlProcesoDao;
import net.tce.dto.ControlProcesoDto;
import net.tce.dto.SchedulerDto;
import net.tce.model.ControlProceso;
import net.tce.util.Constante;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("controlProcesoDao")
public class ControlProcesoDaoImpl extends PersistenceGenericDaoImpl<ControlProceso, Object> 
implements ControlProcesoDao {

	Logger log4j = Logger.getLogger( this.getClass());
	StringBuilder sb;
	
	
	
	/**
	* Devuelve el numero de procesos abiertos para el tipo de proceso proporcionado
	* @param filtros, un mapa con los filtros a aplicar
	* @return una lista de objetos correspondientes
	* @author osy
	*/
	@Transactional(readOnly=true)
	public Long numberOpenProcess(SchedulerDto schedulerDto){
		log4j.info("<existsOpenProcess> IdTipoProceso :" + schedulerDto.getIdTipoProceso());
		return (Long)this.getSession().createQuery(
				new StringBuilder("select count(*) from ControlProceso as CONTROLPROCESO ").
	    		append(" where CONTROLPROCESO.estatusProceso.idEstatusProceso = ").
	    		append(Constante.PROCESS_STATUS_IN_PROGRESS).
	    		append(" and CONTROLPROCESO.tipoProceso.idTipoProceso = ").
	    		append(schedulerDto.getIdTipoProceso()).toString()).uniqueResult();
	    
	}
	
	/**
	* Devuelve el último resultado de un proceso dado con estatus de cerrado
	* @param SchedulerDto
	* @return String resultado
	* @author osy
	*/
	@Transactional(readOnly=true)
	public String lastResult(SchedulerDto schedulerDto){		
		
		String result = (String) this.getSession().createQuery(
				new StringBuilder("select resultado from ControlProceso as CONTROLPROCESO ").
				append(" where  CONTROLPROCESO.estatusProceso.idEstatusProceso = ").
				append( Constante.PROCESS_STATUS_CLOSED).
				append(" and CONTROLPROCESO.tipoProceso.idTipoProceso = ").
				append(schedulerDto.getIdTipoProceso()).
				append(" and CONTROLPROCESO.fechaFin = (select max(fechaFin) from  ControlProceso as CONTROLPROCESO2 ").
				append(" where  CONTROLPROCESO2.estatusProceso.idEstatusProceso = ").
				append(Constante.PROCESS_STATUS_CLOSED).
				append(" and CONTROLPROCESO2.tipoProceso.idTipoProceso = ").
				append(schedulerDto.getIdTipoProceso()).append(")").
				toString()).uniqueResult();
			
		if(result == null){
			result = "0";	
		}
		
		log4j.info("<lastResult> result :" + result);
		return result;
	}



	/**
	 * Ultima fecha final del proceso correspondiente
	 * @param idTipoProceso, id de tipo de proceso
	 * @return un objeto ControlProcesoDto solo con la fecha
	 */
	public ControlProcesoDto lastDateFinal(Long  idTipoProceso) {
		return (ControlProcesoDto)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.ControlProcesoDto(TO_CHAR(max(CP.fechaFin), ")
				.append(Constante.PSG_TOCHAR_DATE)
				.append(")) from ControlProceso as CP ")
				.append(" where CP.estatusProceso.idEstatusProceso= :idEstatusProceso")
				.append(" and CP.tipoProceso.idTipoProceso= :idTipoProceso").toString())
				.setLong("idEstatusProceso", Constante.PROCESS_STATUS_CLOSED.longValue())
				.setLong("idTipoProceso", idTipoProceso).uniqueResult();
	}
	
	/**
	 * 
	 * @param idTipoProceso
	 * @param idEstatusProceso
	 * @return
	 */
	public ControlProcesoDto lastControlProceso(Long idTipoProceso, Long idEstatusProceso){		
		log4j.debug("<lastControlProceso> idTipoProceso :" + idTipoProceso + ", idEstatusProceso: " + idEstatusProceso);
		
		return (ControlProcesoDto)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.ControlProcesoDto(")
				.append("idControlProceso, tipoProceso.idTipoProceso, estatusProceso.idEstatusProceso, ")
				.append("to_char(fechaInicio, 'Mon DD/YYYY HH24:mm'), to_char(fechaFin, 'Mon DD/YYYY HH24:mm'), resultado, mensaje )")
//				.append("from ControlProceso as CP ")
//				.append(" where CP.estatusProceso.idEstatusProceso= :idEstatusProceso")
//				.append(" and CP.tipoProceso.idTipoProceso= :idTipoProceso").toString())
				.append(" where  CONTROLPROCESO.estatusProceso.idEstatusProceso = :idEstatusProceso")
				.append(" and CONTROLPROCESO.tipoProceso.idTipoProceso = :idTipoProceso")
				.append(" and CONTROLPROCESO.fechaFin = (select max(fechaFin) from  ControlProceso as CONTROLPROCESO2")
				.append(" where CONTROLPROCESO2.estatusProceso.idEstatusProceso = :idEstatusProceso")
				.append(" and CONTROLPROCESO2.tipoProceso.idTipoProceso = :idTipoProceso")
				.append(" and CONTROLPROCESO2.fechaFin < (select max(fechaFin) from  ControlProceso as CONTROLPROCESO3")
				.append(" where CONTROLPROCESO3.estatusProceso.idEstatusProceso = :idEstatusProceso")
				.append(" and CONTROLPROCESO3.tipoProceso.idTipoProceso = :idTipoProceso")
				.append(")) ").toString())
				.setLong("idEstatusProceso", idEstatusProceso)
				.setLong("idTipoProceso", idTipoProceso).uniqueResult();
	}
}
