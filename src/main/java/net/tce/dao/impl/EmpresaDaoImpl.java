package net.tce.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.tce.dao.EmpresaDao;
import net.tce.model.Empresa;
import net.tce.util.Constante;

@Repository("empresaDao")
public class EmpresaDaoImpl extends PersistenceGenericDaoImpl<Empresa, Object> 
	implements EmpresaDao {
	Logger log4j = Logger.getLogger( this.getClass());
	
	
	/**
	 * Se obtiene un objeto empresa con el id del documento de clasificacion de la BD operativa
	 * @param Long idTextoClasificacion 
	 * @return objeto empresa
	 */
	public Empresa getEmpresaByIdClassDoc(String idTextoClasificacion){
		return (Empresa) this.getSession().
				createQuery(new StringBuilder("from Empresa as EMP ").
				append(" inner join EMP.documentoClasificacions DC ").
				append(" where DC.idTextoClasificacion = :idTextoClasificacion").toString()).
				setString("idTextoClasificacion",new String(idTextoClasificacion)).
				uniqueResult();
	}
	
	/**
	 * Se obtiene una lista de empresas sin clasificar
	 * @return lista objetos empresa
	 */
	@SuppressWarnings("unchecked")
	public List<Empresa> getEnterpriseUnclassified() {
		return (List<Empresa>) this.getSession().createQuery(
				new StringBuilder("select EMPRESA from  Empresa as  EMPRESA ").
				append(" where EMPRESA.clasificado=").
				append(Constante.CANDIDATO_NO_CLASIFICADO).
				append(" and EMPRESA.estatusInscripcion=").
				append(Constante.ESTATUS_INSCRIPCION_PUBLICADO).
				append(" order by EMPRESA.idEmpresa").toString()).list();
	}
	
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Empresa> getCompanyByPersona(Long idPersona) {
		List<Long> lsRelas = new ArrayList<Long>();
		lsRelas.add(new Long(1));lsRelas.add(new Long(9));
		return this.getSession().createQuery(
				new StringBuilder("select DISTINCT EMPRESA from RelacionEmpresaPersona as REP ").
				append("inner join REP.empresa as EMPRESA ").
				append(" where REP.persona.idPersona=:idPersona ").
				//append(" and REP.rol.idRol in (1,9) " )
				append(" and REP.rol.idRol in (:rols) " )
				.append(" and EMPRESA.idEmpresa != 0 " )
				.toString()).
				//setLong("idPersona", idPersona).list();
				setLong("idPersona", idPersona).setParameterList("rols", lsRelas).list();
	}
	
}
