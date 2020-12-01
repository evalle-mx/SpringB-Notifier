package net.tce.dao;


import java.util.List;
import net.tce.dto.ModeloRscDto;
import net.tce.model.ModeloRsc;


public interface ModeloRscDao extends PersistenceGenericDao<ModeloRsc, Object>{
	
	List<ModeloRscDto> getByModeloRsc(Long idModeloRsc);

}
