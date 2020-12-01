package net.tce.dao.impl;

import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Repository;
import net.tce.dao.HabilidadDao;
import net.tce.dto.PerfilNgramDto;
import net.tce.model.Habilidad;
import net.tce.util.UtilsTCE;

@Repository("habilidadDao")
public class HabilidadDaoImpl extends PersistenceGenericDaoImpl<Habilidad, Object> 
implements HabilidadDao{

	
	
	/**
	 * Obtiene una lista de las habilidades de la persona
	 * @param idPersona
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PerfilNgramDto> getText(long idPersona){
		List<PerfilNgramDto> lsPerfilTextoNgramDto=(List<PerfilNgramDto>)getSession().
				createQuery(new StringBuilder().
				append("select new net.tce.dto.PerfilNgramDto(").
				append("HABILIDAD.descripcion, DOMINIO.ponderacion").
				append(") from Habilidad as HABILIDAD  ").
				append("inner join HABILIDAD.persona as PERSONA ").
				append("inner join HABILIDAD.dominio as DOMINIO  ").
				append(" where PERSONA.idPersona =:idPersona").
				append(" order by HABILIDAD.idHabilidad ").toString()).
				setLong("idPersona", idPersona).list();

		if(lsPerfilTextoNgramDto != null && lsPerfilTextoNgramDto.size() > 0){
			Iterator<PerfilNgramDto> itPerfilTextoNgramDto=lsPerfilTextoNgramDto.iterator();
			while(itPerfilTextoNgramDto.hasNext()){
				PerfilNgramDto perfilNgramDto=itPerfilTextoNgramDto.next();
				perfilNgramDto.setDescripcion(UtilsTCE.filterGramaText(perfilNgramDto.getDescripcion()));
			}
			
			return lsPerfilTextoNgramDto;
		}else{
			return null;
		}
	}
	
	
	/**
	 * Obtiene una lista de las habilidades similares a la dada, para la posicion
	 * @param idHabilidadPos, id de la habilidadPos
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<PerfilNgramDto> getTextSimiPos(long idHabilidadPos){
		List<PerfilNgramDto> lsPerfilTextoNgramDto=(List<PerfilNgramDto>)getSession().
				createQuery(new StringBuilder().
				append("select new net.tce.dto.PerfilNgramDto(").
				append("HABILIDADPOS.idHabilidadpos, HABILIDADPOS.descripcion").
				append(") from Habilidadpos as HABILIDADPOS ").
				append("inner join HABILIDADPOS.habilidadpos as HABILIDADPOS_SIMI ").
				append(" where HABILIDADPOS_SIMI.idHabilidadpos =:idHabilidadPos").
				append(" order by HABILIDADPOS.idHabilidadpos ").toString()).
				setLong("idHabilidadPos", idHabilidadPos).list();

		if(lsPerfilTextoNgramDto != null && lsPerfilTextoNgramDto.size() > 0){
			Iterator<PerfilNgramDto> itPerfilTextoNgramDto=lsPerfilTextoNgramDto.iterator();
			while(itPerfilTextoNgramDto.hasNext()){
				PerfilNgramDto perfilNgramDto=itPerfilTextoNgramDto.next();
				perfilNgramDto.setDescripcion(UtilsTCE.filterGramaText(perfilNgramDto.getDescripcion()));
			}
			
			return lsPerfilTextoNgramDto;
		}else{
			return null;
		}
	}
}
