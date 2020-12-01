package net.tce.dao;

import java.util.Date;
import java.util.List;
import net.tce.dto.PosicionDto;
import net.tce.model.Posicion;

public interface PosicionDao   extends PersistenceGenericDao<Posicion, Object>{

	//List<VacancyDto> getPartial(HashMap <String, Object> htFilters);
	//List<Posicion> getPosicionByFilter(HashMap <String, Object> htFilters);
	int count(Long idPersona, Long idEmpresa, boolean personaConEmpresa);
	int updateEstatusPosicion(Long idEstatusPosicion, Long idPersona, boolean empresaNulo) ;
	Posicion getByCveExterna(String cveExterna);
	List<Posicion> getPositionUnclassified();
	List<PosicionDto>  getPosicionConcurrente();
	int updateFechaUltimaBusqueda(Long idPosicion,Date fechaUltimaBusqueda);
	int updateModificado(Long idPosicion, Boolean modificado) ;
	List<Posicion> getByPerfil(Long idPerfil);
	Long getByMonitor(Long idMonitor);
}
