package net.tce.dao;

import java.util.List;

import net.tce.dto.NotificacionProgramadaDto;
import net.tce.model.NotificacionProgramada;

public interface NotificacionProgramadaDao extends PersistenceGenericDao<NotificacionProgramada, Object>{
	
	List<NotificacionProgramadaDto> get(Long prioridad, Long intentos, Boolean enviada);

	List<NotificacionProgramada> getModel(boolean enviada,boolean tipoEventoActivo );
	
	List<NotificacionProgramada> getModel(Long prioridad, Long intentos, Boolean enviada );
	
	int updateEnviado(boolean enviada,long idNotificacionProgramada) ;
}
