package net.tce.dao;

import java.util.List;
import net.tce.model.NotificacionProgramada;

public interface NotificacionProgramadaDao extends PersistenceGenericDao<NotificacionProgramada, Object>{

	List<NotificacionProgramada> get(boolean enviada,boolean tipoEventoActivo );
	int updateEnviado(boolean enviada,long idNotificacionProgramada) ;
}
