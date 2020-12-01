package net.tce.dao.impl;

import java.util.List;

import net.tce.dao.IdiomaDao;
import net.tce.dto.IdiomaDto;
import net.tce.model.Idioma;

import org.springframework.stereotype.Repository;

@Repository("idiomaDao")
public class IdiomaDaoImpl extends PersistenceGenericDaoImpl<Idioma, Object> 
implements IdiomaDao {

	@SuppressWarnings("unchecked")
	public List<IdiomaDto> getByPersona(Long idPersona){
		List<IdiomaDto> lsIdiomaDto=(List<IdiomaDto>)getSession().
				createQuery(new StringBuilder().
				append("select new net.tce.dto.IdiomaDto(").				
				append("pid.idPersonaIdioma, pid.persona.idPersona,").
				append("pid.idioma.idIdioma, pid.idioma.descripcion, pid.dominio.idDominio, pid.dominio.descripcion ").
				append(") from PersonaIdioma as pid ").
				append(" where pid.persona.idPersona=:idPersona ").
				append(" order by pid.idPersonaIdioma ")
				.toString()).
				setLong("idPersona", idPersona).list();
		if(lsIdiomaDto != null && lsIdiomaDto.size() > 0){
			return lsIdiomaDto;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<IdiomaDto> getByPosicion(Long idPosicion, Long idPoliticaIdioma){
		List<IdiomaDto> lsIdiomaDto=(List<IdiomaDto>)getSession().
				createQuery(new StringBuilder().
				append("select new net.tce.dto.IdiomaDto(").
				append("pmv.idPoliticaMValor, pv.posicion.idPosicion, pv.idPoliticaValor, ").
				append("pmv.dominio.idDominio, pmv.idioma.idIdioma, pmv.ponderacion, pv.politica.idPolitica ").
				append(") FROM PoliticaMValor pmv, PoliticaValor pv ").
				append(" WHERE pmv.politicaValor.idPoliticaValor = pv.idPoliticaValor ").
				append(" AND pv.posicion.idPosicion=:idPosicion ").
				append(" AND pv.politica.idPolitica=:idPoliticaIdioma ").
				append(" ORDER BY pmv.idPoliticaMValor ")
				.toString()).
				setLong("idPosicion", idPosicion).setLong("idPoliticaIdioma", idPoliticaIdioma).list();
		if(lsIdiomaDto != null && lsIdiomaDto.size() > 0){
			return lsIdiomaDto;
		}else{
			return null;
		}
	}
}
