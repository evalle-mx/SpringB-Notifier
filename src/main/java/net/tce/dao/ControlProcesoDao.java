package net.tce.dao;


import net.tce.dto.ControlProcesoDto;
import net.tce.dto.SchedulerDto;
import net.tce.model.ControlProceso;

public interface ControlProcesoDao extends PersistenceGenericDao<ControlProceso, Object>{

	Long numberOpenProcess(SchedulerDto schedulerDto);
	String lastResult(SchedulerDto schedulerDto);
	ControlProcesoDto lastDateFinal(Long  idTipoProceso) ;
	ControlProcesoDto lastControlProceso(Long idTipoProceso, Long idEstatusProceso);

}
