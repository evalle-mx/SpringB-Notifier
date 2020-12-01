package net.tce.dao.impl;


import java.util.List;
import org.springframework.stereotype.Repository;
import net.tce.dao.PosibleCandidatoDao;
import net.tce.dto.PosibleCandidatoDto;
import net.tce.model.PosibleCandidato;
import net.tce.util.Constante;

@Repository("posibleCandidatoDao")
public class PosibleCandidatoDaoImpl extends PersistenceGenericDaoImpl<PosibleCandidato, Object> 
implements PosibleCandidatoDao {

	
	/**
	 * Obtiene una lista de objetos PosibleCandidatoDto dado el idPersona
	 * @param idPersona, Identificador de la persona
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosibleCandidatoDto> getAreas(long idPersona) {
		return (List<PosibleCandidatoDto>) getSession().createQuery(
				new StringBuilder("select new net.tce.dto.PosibleCandidatoDto")
				.append(" (PC.idPosibleCandidato,PC.confirmado, PS.idPosicion, A.idArea) ")
				.append(" from PosibleCandidato as PC ")
				.append(" inner join PC.relacionEmpresaPersona as REP ")
				.append(" inner join REP.persona  as PR ")
				.append(" inner join PC.perfilPosicion as PP ")
				.append(" inner join PP.posicion as PS ")
				.append(" inner join PP.perfil as PF ")
				.append(" inner join PF.areaPerfils as APF ")
				.append(" inner join APF.area as A ")
				.append(" inner join PC.estatusOperativo as EO ")
				.append(" where PR.idPersona = :idPersona ")
				.append(" and EO.idEstatusOperativo != ")
				.append(Constante.ESTATUS_CANDIDATO_OPERATIVO_DESCARTADO)
				.append(" order by A.idArea").toString())
				.setLong("idPersona", idPersona).list();
	}
	
	

	/**
	 * Se obtiene el passwordInisystem dado el idPosibleCandidato
	 * @param idPosibleCandidato, Identificador del posibleCandidato
	 * @return el passwordInisystem
	 */
	@Override
	public String getPswIniSystem(long idPosibleCandidato) {
		return (String) getSession().createQuery(
				new StringBuilder("select HP.pswIniSystem ")
				.append(" from PosibleCandidato as PC ")
				.append(" inner join PC.relacionEmpresaPersona REP ")
				.append(" inner join REP.persona P ")
				.append(" inner join P.historicoPasswords HP ")
				.append(" where PC.idPosibleCandidato= :idPosibleCandidato ").toString())
				.setLong("idPosibleCandidato", idPosibleCandidato).uniqueResult();
	}

	/**
	 * Se obtiene el idPersona dado el idPosibleCandidato
	 * @param idPosibleCandidato, Identificador del posibleCandidato
	 * @return el identificador de la persona
	 */
	@Override
	public Long getIdPersona(long idPosibleCandidato) {
		return (Long) getSession().createQuery(
				new StringBuilder("select P.idPersona")
				.append(" from PosibleCandidato as PC ")
				.append(" inner join PC.relacionEmpresaPersona REP ")
				.append(" inner join REP.persona P ")
				.append(" where PC.idPosibleCandidato= :idPosibleCandidato ").toString())
				.setLong("idPosibleCandidato", idPosibleCandidato).uniqueResult();
	}

	/**
	 * Se obtiene el idPosibleCandidato dado el idPersona y idPosicion
	 * @param idPersona, Identificador de la persona
	 * @param idPosicion, Identificador de la posicion
	 * @return el identificador del PosibleCandidato
	 */
	@Override
	public Long getIdPosibleCandidato(long idPersona, long idPosicion) {
		return (Long) getSession().createQuery(
				new StringBuilder("select PC.idPosibleCandidato ")
				.append(" from PosibleCandidato as PC ")
				.append(" inner join PC.relacionEmpresaPersona REP ")
				.append(" inner join REP.persona P ")
				.append(" inner join PC.perfilPosicion PP ")
				.append(" inner join PP.posicion POS ")
				.append(" where P.idPersona= :idPersona ")
				.append(" and POS.idPosicion= :idPosicion ").toString())
				.setLong("idPersona", idPersona)
				.setLong("idPosicion", idPosicion).uniqueResult();
	}

	/**
	 * Actualiza el campo modificado, dado el idPosibleCandidato
	 * @param idPosibleCandidato, Identificador del posibleCandidato
	 * @param confirmado, true= modificado o false=no modificado
	 * @return 
	 */
	@Override
	public int updateConfirmado(long idPosibleCandidato, boolean confirmado) {
		return getSession().createQuery(
				new StringBuilder("update PosibleCandidato set confirmado = :confirmado ")
				.append(" where  idPosibleCandidato = :idPosibleCandidato ").toString()).
				setLong("idPosibleCandidato", idPosibleCandidato).
				setBoolean("confirmado", confirmado).
				executeUpdate();
	}

	/**
	 * Se pone el token en el registro correspondiente
	 * @param tokenAdd
	 * @param idPosibleCandidato
	 * @return
	 */
	@Override
	public int updateTokenAdd(String tokenAdd, Long idPosibleCandidato) {
		return getSession().createQuery(
				new StringBuilder("update PosibleCandidato set tokenAdd = :tokenAdd ")
				.append(" where  idPosibleCandidato = :idPosibleCandidato ").toString()).
				setString("tokenAdd", tokenAdd).
				setLong("idPosibleCandidato", idPosibleCandidato).
				executeUpdate();
	}
}
