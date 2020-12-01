package net.tce.dao;


import java.util.List;

import net.tce.model.RelacionEmpresaPersona;

/**
 * 
 * @author Goyo
 *
 */
public interface RelacionEmpresaPersonaDao extends PersistenceGenericDao<RelacionEmpresaPersona, Object>{
	RelacionEmpresaPersona getRelacionEmpresaPersona(Long idCandidato, String idEmpresaConf);
	List<Long> getAdminsPerson();
}
