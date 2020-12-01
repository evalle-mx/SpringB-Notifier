package net.tce.dao;


import java.util.List;
import net.tce.model.Empresa;

/**
 * @author 
 */
public interface EmpresaDao extends PersistenceGenericDao<Empresa, Object>{
	
	Empresa getEmpresaByIdClassDoc(String idTextoClasificacion);
	List<Empresa> getEnterpriseUnclassified();
	List<Empresa> getCompanyByPersona(Long idPersona);
}
