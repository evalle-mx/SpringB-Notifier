package net.tce.dao.impl;

import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import net.tce.dao.PerfilTextoNgramDao;
import net.tce.dto.PerfilNgramDto;
import net.tce.model.PerfilTextoNgram;
import net.tce.util.UtilsTCE;

/**
 * 
 * @author tce
 *
 */
@Repository("perfilTextoNgramDao")
public class PerfilTextoNgramDaoImpl extends PersistenceGenericDaoImpl<PerfilTextoNgram, Object> 
implements PerfilTextoNgramDao {
	Logger log4j = Logger.getLogger( this.getClass());
	

	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<PerfilNgramDto> getText(long idPerfil){
		log4j.debug("<getText> idPerfil: " + idPerfil );
		List<PerfilNgramDto> lsPerfilTextoNgramDto=(List<PerfilNgramDto>)getSession().
				createQuery(new StringBuilder().
				append("select new net.tce.dto.PerfilNgramDto(").
				append("pn.texto, pn.ponderacion").
				append(") from PerfilTextoNgram as pn ").
				append(" where pn.perfil.idPerfil=:idPerfil").
				append(" order by pn.idPerfilTextoNgram ").toString()).
				setLong("idPerfil", idPerfil).list();
		log4j.debug("<getText> lsPerfilTextoNgramDto: " + lsPerfilTextoNgramDto );
		if(lsPerfilTextoNgramDto != null && lsPerfilTextoNgramDto.size() > 0){
			Iterator<PerfilNgramDto> itPerfilTextoNgramDto=lsPerfilTextoNgramDto.iterator();
			while(itPerfilTextoNgramDto.hasNext()){
				PerfilNgramDto perfilNgramDto=itPerfilTextoNgramDto.next();
				perfilNgramDto.setDescripcion(UtilsTCE.filterGramaText(perfilNgramDto.getDescripcion()));
			}
			
			return lsPerfilTextoNgramDto;
		}else{
			log4j.debug("<getText> lsPerfilTextoNgramDto: es Null");
			return null;
		}
	}
	
	/**
     * Borra el registro de la empresa en la tabla dada
     * @param filtros, es un objeto map que contiene el idEmpresa y el nombre de la tabla
     */
	public void deleteByPerfil(Long id) {
		log4j.debug("<deleteByPerfil> id= "+id);
		this.getSession().createQuery(new StringBuilder("delete from ").
				append("PerfilTextoNgram").
				append(" where  perfil.idPerfil = :idPerfil ").toString()).
				setLong("idPerfil",(Long)id).executeUpdate();
	}
	
}
