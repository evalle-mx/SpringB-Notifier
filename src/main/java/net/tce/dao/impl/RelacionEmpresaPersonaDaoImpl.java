package net.tce.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import net.tce.dao.RelacionEmpresaPersonaDao;
import net.tce.model.RelacionEmpresaPersona;
import net.tce.util.Constante;

@Repository("relacionEmpresaPersonaDao")
public class RelacionEmpresaPersonaDaoImpl extends PersistenceGenericDaoImpl<RelacionEmpresaPersona, Object> 
	implements RelacionEmpresaPersonaDao {
	
	/**
	 * Se obtiene una lista de personas con rol de administración
	 */
	@Override
	public RelacionEmpresaPersona getRelacionEmpresaPersona(Long idCandidato, String idEmpresaConf) {
		return (RelacionEmpresaPersona)this.getSession().createQuery(
				new StringBuilder("select REP from RelacionEmpresaPersona as REP  ").
				append(" inner join REP.empresa as EMP ").
				append(" inner join EMP.empresaConfs as EMPC ").
				append(" inner join REP.persona as PER ").
				append(" inner join PER.candidatos as CANTO ").
				append(" where CANTO.idCandidato = :idCandidato ").
				append(" and EMPC.idEmpresaConf = :idEmpresaConf ").toString()).
				setLong("idEmpresaConf",Long.parseLong(idEmpresaConf)).
				setLong("idCandidato", idCandidato).uniqueResult();
	}
	
	
	/**
	 * Se obtiene una lista de personas con rol de administración
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getAdminsPerson() {
		return (List<Long>) this.getSession().createQuery(
				new StringBuilder("select PER.idPersona ").
				append(" from RelacionEmpresaPersona as REP ").
				append(" inner join REP.persona as PER ").
				append(" inner join REP.rol as ROL ").
				append(" where ROL.idRol=").
				append(Constante.ROL_ADMINISTRADOR).
				append(" order by PER.idPersona  ").toString()).list();
	}
	
	
}
