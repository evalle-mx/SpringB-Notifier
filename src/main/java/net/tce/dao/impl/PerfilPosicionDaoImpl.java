package net.tce.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import net.tce.dao.PerfilPosicionDao;
import net.tce.dto.PerfilPosicionDto;
import net.tce.model.PerfilPosicion;
import net.tce.util.Constante;

@Repository("perfilPosicionDao")
public class PerfilPosicionDaoImpl extends PersistenceGenericDaoImpl<PerfilPosicion, Object> 
implements PerfilPosicionDao {

	
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public PerfilPosicion getInterno(Long idPosicion){
		PerfilPosicion perfilPosicion = null;
		
		List<PerfilPosicion> lsPerfilPosicion =
				(List<PerfilPosicion>)getSession().
				createQuery(new StringBuilder().
				append(" from PerfilPosicion as pp where pp.posicion.idPosicion=:idPosicion ").
				append(" and pp.perfil.tipoPerfil.idTipoPerfil=:tipoInterno").
				//append(" order by pn.orden ")
				toString()).
				setLong("idPosicion", idPosicion).
				setLong("tipoInterno", Constante.TIPO_PERFIL_INTERNO).
				list();
		if(lsPerfilPosicion!=null && !lsPerfilPosicion.isEmpty()){
			perfilPosicion = lsPerfilPosicion.get(0); 
		}
		return perfilPosicion;
	}

	/**
	 * Se obtiene una lista de objetos PerfilPosicion dado el idPosicion
	 * @param idPosicion, el id de la posicion
	 * @return la lista
	 */
	@SuppressWarnings("unchecked")
	public List<PerfilPosicionDto> get(Long idPosicion){
		return (List<PerfilPosicionDto>)getSession().
		createQuery(new StringBuilder(" select new net.tce.dto.PerfilPosicionDto(").
				append(" pp.perfil.idPerfil, pp.ponderacion) ").
				append(" from PerfilPosicion as pp where pp.posicion.idPosicion=:idPosicion ").toString()).
				setLong("idPosicion", idPosicion).list();
	}
}
