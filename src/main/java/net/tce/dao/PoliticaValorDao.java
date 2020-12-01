package net.tce.dao;


import java.util.List;
import net.tce.model.PoliticaValor;


public interface PoliticaValorDao extends PersistenceGenericDao<PoliticaValor, Object>{

	 List<PoliticaValor> get(Long idPerfil,Long idPosicion, Long idEmpresa);
	 List<PoliticaValor> getUnionParametrosEntrada(Long  idPerfil, Long idPosicion, Long idEmpresa);
	 List<PoliticaValor> getByPolicy(Long  idPerfil, Long idPosicion, Long idEmpresa, String policy);
	 
}
