package net.tce.dao;


import java.util.Date;
import java.util.List;

import net.tce.dto.RecordatorioDto;
import net.tce.model.Recordatorio;

public interface RecordatorioDao extends PersistenceGenericDao<Recordatorio, Object>{
	
	List<RecordatorioDto> getByDateMonitor(Date fecha);
	int update(long idRecordatorio, boolean seAplico);
	List<RecordatorioDto> getByDatePostulante(Date fecha);
}
