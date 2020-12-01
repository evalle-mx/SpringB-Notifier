package net.tce.dao;

import java.util.List;
import net.tce.dto.ModeloRscPosDto;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.model.ModeloRscPosFase;
import net.tce.model.Monitor;
import net.tce.model.Rol;
import net.tce.model.ModeloRscPos;

public interface ModeloRscPosDao extends PersistenceGenericDao<ModeloRscPos, Object>{

	List<ModeloRscPosFaseDto> getIdsModeloRscPosFases(Long idModeloRscPos, Boolean activo);
	ModeloRscPosFaseDto readModeloRscPosFase(Long idModeloRscPosFase);
	List<ModeloRscPosFase> getModeloRscPosFases(Long idModeloRscPos);
	List<Monitor> getMonitor(Long idModeloRscPos, Long idPersona);
	List<ModeloRscPosDto> getModeloRscPos(Long idPosicion,boolean esMonitor,
											boolean relacionEntreEsquemas, 
											Boolean modeloMonitorPrincipal,
											boolean relMonitores,
											Boolean faseActivo);
	Long readModeloRscPosPrincipal(Long idPosicion);
	Long readIdModeloRscPosRol(Long idPosicion, Boolean monitor, Boolean principal);
	Long getIdPosicion(Long idModeloRscPos);
	Long getByPosicion(Long idPosicion, Long idRol, Boolean activo);
	Long countMonitoresByPosicion(Long idPosicion);
	Long countFaseSinDiasByPosicion(Long idPosicion);
	Rol getRol(Long idModeloRscPos, Long idRelacionEmpresaPersona );
	Long getCountMonitor(Long idModeloRscPos, Long idPersona , Boolean principal);
	List<Long> get(Long idPosicion, Boolean monitor, Boolean faseActiva);
	Long readByFase(Long idModeloRscPosFase);

}
