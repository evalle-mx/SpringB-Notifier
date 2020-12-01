package net.tce.dao;

import java.util.List;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.model.ModeloRscPosFase;

public interface ModeloRscPosFaseDao extends PersistenceGenericDao<ModeloRscPosFase, Object> {
	void deleteByidModeloRscPos(Long idModeloRscPos);
	List<ModeloRscPosFase> getMonitoresByPosicion(Long idPosicion);
	List<ModeloRscPosFaseDto> getModeloRscPosFase(String idsModeloRscPosFase);
	ModeloRscPosFase readByMonitor(Long idMonitor);
	ModeloRscPosFase readByPosOrdAct(Long idPosicion,Short orden, Short actividad, boolean esMonitor);
	List<ModeloRscPosFase> getByIdModeloRscPos(Long idModeloRscPos);
}
