package net.tce.dao.impl;

import java.util.List;

import net.tce.dao.MunicipioAdyacenciaDao;
import net.tce.dto.MunicipioAdyacenciaDto;
import net.tce.model.MunicipioAdyacencia;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("municipioAdyacenciaDao")
public class MunicipioAdyacenciaDaoImpl extends PersistenceGenericDaoImpl<MunicipioAdyacencia, Object> 
implements MunicipioAdyacenciaDao{
	Logger log4j = Logger.getLogger( this.getClass());
	
	

	/**
	* Recupera una lista de DTOs usando varios filtros.
	* @param filtros, un mapa con los filtros a aplicar
	* @return una lista de objetos correspondientes
	*/
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<MunicipioAdyacenciaDto> getAdjacency(Long idMunicipio, long rango){
		log4j.debug("<getAdjacency> idMunicipio="+idMunicipio+" rango="+rango);
		return (List<MunicipioAdyacenciaDto>) getSession().createQuery(
				new StringBuilder("select new net.tce.dto.MunicipioAdyacenciaDto( ").
		        append(" ADYACENCIA.municipioByIdMunicipio.idMunicipio,").
		        append(" ADYACENCIA.municipioByIdMunicipioAdyacente.idMunicipio) ").
		        append(" from MunicipioAdyacencia as ADYACENCIA ").
		        append(" where ADYACENCIA.municipioByIdMunicipio.idMunicipio  = :idMunicipio ").
		        append(" and ADYACENCIA.rango = :rango ").
		        append(" order by ADYACENCIA.municipioByIdMunicipioAdyacente.idMunicipio ").toString()).
		        setLong("idMunicipio", idMunicipio).
		        setLong("rango", rango).list();   
	}
}
