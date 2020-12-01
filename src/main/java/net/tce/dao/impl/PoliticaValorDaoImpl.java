package net.tce.dao.impl;

import java.util.List;

import javax.inject.Inject;

import net.tce.dao.PoliticaValorDao;
import net.tce.model.PoliticaValor;
import net.tce.util.DBInterpreter;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("politicaValor")
public class PoliticaValorDaoImpl extends PersistenceGenericDaoImpl<PoliticaValor, Object> 
implements PoliticaValorDao {

	@Inject
	DBInterpreter dbInterpreter;

	/**
	 * 
	 */
	/*@SuppressWarnings("unchecked")
	public List<PoliticaValorDto> get2(Long  idPerfil) {
		//List<PoliticaValor> lsPoliticaValor=(List<PoliticaValor>) getSession().createQuery(
		List<PoliticaValorDto> lsPoliticaValor=(List<PoliticaValorDto>) getSession().createQuery(" select new net.tce.dto.PoliticaValorDto(".
											concat("p.tipoValor as valor, pv.valor as tipoValor, pv.orden as orden ").	
											concat(" ) ").
											concat(" from PoliticaValor as pv inner join  pv.politica as p ").
											concat(" left outer join  pv.concepto as c ").
											concat(" where pv.perfil.idPerfil=:idPerfil ").
											concat(" and pv.politica.estatusRegistro=1 ").
											concat(" order by pv.seccion.idSeccion, pv.concepto.idConcepto desc, pv.orden")).
											setLong("idPerfil", idPerfil).list();
		
		if(lsPoliticaValor != null && lsPoliticaValor.size() > 0){
			return lsPoliticaValor;
		}else{
			return null;
		}
	}*/
	
	/**
	* Recupera una lista de objetos PoliticaValor dado el idPerfil u idPosicion u idEmpresa
	* @param idPerfil, el id de perfil
	* @param idPosicion, el id de posicion
	* @param idEmpresa, el id de empresa
	* @return una lista de objetos correspondientes
	*/
	@SuppressWarnings("unchecked")
	public List<PoliticaValor> get(Long  idPerfil, Long idPosicion, Long idEmpresa) {
		StringBuffer sb=new StringBuffer(); 
		sb.append("from PoliticaValor as pv inner join fetch pv.politica ").
		append(" left outer join fetch pv.concepto ").
		//append(" where pv.politica.estatusRegistro=1 ");
		append(" where pv.politica.estatusRegistro=").append(dbInterpreter.fnBoolean("1"));
		
		if(idPerfil != null)
			sb.append(" and pv.perfil.idPerfil=:idPerfil ");
		else if(idPosicion != null)
			sb.append(" and pv.posicion.idPosicion=:idPosicion ");
		else if(idEmpresa != null)
			sb.append(" and pv.empresa.idEmpresa=:idEmpresa ");
		sb.append(" order by pv.seccion.idSeccion, pv.concepto.idConcepto desc, pv.orden");
		
		Query query=getSession().createQuery(sb.toString());
		if(idPerfil != null)
			query.setLong("idPerfil", idPerfil);
		else if(idPosicion != null)
			query.setLong("idPosicion", idPosicion);		
		else if(idEmpresa != null)
			query.setLong("idEmpresa", idEmpresa);
		
		List<PoliticaValor> lsPoliticaValor=query.list();
		if(lsPoliticaValor != null && lsPoliticaValor.size() > 0){
			return lsPoliticaValor;
		}else{
			return null;
		}
	}
	
	/**
	* Recupera una lista de objetos PoliticaValor dado el idPerfil y/u idPosicion  y/u idEmpresa.
	* @param idPerfil, el id de perfil
	* @param idPosicion, el id de posicion
	* @param idEmpresa, el id de empresa
	* @return una lista de objetos correspondientes
	*/
	@SuppressWarnings("unchecked")
	public List<PoliticaValor> getUnionParametrosEntrada(Long  idPerfil, Long idPosicion, Long idEmpresa) {
		StringBuffer sb=new StringBuffer("from PoliticaValor as pv where"); 
		String orPerfil=" ";
		String orPosicion=" ";
		String orEmpresa=" ";
		//La formación del query(combinacion or) depende de los parametros
		if(idPerfil != null && idPosicion != null && idEmpresa == null){
			orPosicion="or";
		}else if(idPerfil != null && idPosicion == null && idEmpresa != null){
			orEmpresa="or";
		}else if(idPerfil == null && idPosicion != null && idEmpresa != null){
			orEmpresa="or";
		}else if(idPerfil != null && idPosicion != null && idEmpresa != null){
			orPosicion="or";
			orEmpresa="or";
		}
		
		if(idPerfil != null){
			sb.append(orPerfil).
			append(" pv.idPoliticaValor in(select pv1.idPoliticaValor from PoliticaValor ").
			append(" as pv1 inner join  pv1.politica left outer join pv1.concepto ").
			//append(" where pv1.politica.estatusRegistro=1 ").
			append(" where pv1.politica.estatusRegistro=").append(dbInterpreter.fnBoolean("1")).
			append(" and pv1.perfil.idPerfil=:idPerfil )");
		}
		if(idPosicion != null){
			sb.append(orPosicion).
			append(" pv.idPoliticaValor in(select pv2.idPoliticaValor from PoliticaValor ").
			append(" as pv2 inner join  pv2.politica left outer join pv2.concepto ").
			//append(" where pv2.politica.estatusRegistro=1 ").
			append(" where pv2.politica.estatusRegistro=").append(dbInterpreter.fnBoolean("1")).
			append(" and pv2.posicion.idPosicion=:idPosicion)");
		}	
		if(idEmpresa != null){
			sb.append(orEmpresa).
			append(" pv.idPoliticaValor in(select pv3.idPoliticaValor from PoliticaValor ").
			append(" as pv3 inner join  pv3.politica left outer join pv3.concepto ").
			//append(" where pv3.politica.estatusRegistro=1 ").
			append(" where pv3.politica.estatusRegistro=").append(dbInterpreter.fnBoolean("1")).
			append(" and pv3.empresa.idEmpresa=:idEmpresa )");
		}
		sb.append(" order by pv.empresa.idEmpresa,pv.posicion.idPosicion,pv.perfil.idPerfil,").
		append("pv.seccion.idSeccion, pv.concepto.idConcepto desc, pv.orden");
		
		Query query=getSession().createQuery(sb.toString());
		if(idPerfil != null)
			query.setLong("idPerfil", idPerfil);
		if(idPosicion != null)
			query.setLong("idPosicion", idPosicion);		
		if(idEmpresa != null)
			query.setLong("idEmpresa", idEmpresa);
		
		List<PoliticaValor> lsPoliticaValor=query.list();
		if(lsPoliticaValor != null && lsPoliticaValor.size() > 0){
			return lsPoliticaValor;
		}else{
			return null;
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<PoliticaValor> getByPolicy(Long  idPerfil, Long idPosicion, Long idEmpresa, String policy) {
		StringBuffer sb=new StringBuffer(); 
		sb.append("from PoliticaValor as pv inner join fetch pv.politica ")
		//append(" where pv.politica.estatusRegistro=1 ");
		.append(" where pv.politica.estatusRegistro=").append(dbInterpreter.fnBoolean("1"));
		
		if(idPerfil != null)
			sb.append(" and pv.perfil.idPerfil=:idPerfil ");
		else if(idPosicion != null)
			sb.append(" and pv.posicion.idPosicion=:idPosicion ");
		else if(idEmpresa != null)
			sb.append(" and pv.empresa.idEmpresa=:idEmpresa ");
		
		if(policy != null){
			sb.append(" and pv.politica.clavePolitica=:policy ");
		}
		
		sb.append(" order by pv.seccion.idSeccion, pv.orden");
		
		Query query=getSession().createQuery(sb.toString());
		if(idPerfil != null)
			query.setLong("idPerfil", idPerfil);
		else if(idPosicion != null)
			query.setLong("idPosicion", idPosicion);		
		else if(idEmpresa != null)
			query.setLong("idEmpresa", idEmpresa);

		if(policy != null)
			query.setString("policy", policy);
		
		return query.list();
		/*
		List<PoliticaValor> lsPoliticaValor=query.list();
		if(lsPoliticaValor != null && lsPoliticaValor.size() > 0){
			return lsPoliticaValor;
		}else{
			return null;
		}
		*/
	}

}
