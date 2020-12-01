package net.tce.dao.impl;


import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.tce.dao.CandidatoDao;
import net.tce.dto.CandidatoDto;
import net.tce.model.Candidato;


/**
 * 
 * @author tce
 *
 */
@Repository("candidatoDao")
public class CandidatoDaoImpl extends PersistenceGenericDaoImpl<Candidato, Object> 
implements CandidatoDao {

	StringBuilder sb;
	Logger log4j = Logger.getLogger( this.getClass());

	
	/**
	* Regresa el solicitante(idEmpresa o idPersona) si existe un HandCheck
	* @param idPosicion, el id de la posicion
	* @param idEmpresa, el id de la empresa candidato
	* @param idPersona, el id de la persona candidato
	* @return un objeto CandidatoDto con el id del solicitante
	*/
	@Transactional(readOnly=true)
	public CandidatoDto getHandCheck(String idPosicion, String idEmpresa, String idPersona ){
		sb=new StringBuilder("select new net.tce.dto.CandidatoDto(").
		append("ca.posicion.persona.idPersona, ca.posicion.empresa.idEmpresa,ca.handshake) ").
		append(" from Candidato as ca where ca.posicion.idPosicion = :idPosicion ");
		
		if(idPersona != null){
			sb.append(" and ca.persona.idPersona = :idPersona ");
		}
		if(idEmpresa != null){
			sb.append(" and ca.empresa.idEmpresa = :idEmpresa ");
		}
		log4j.debug("%$&/ idPersona="+idPersona+
				" idEmpresa="+idEmpresa+
				" idPosicion="+idPosicion);
		Query qr= (Query) getSession().createQuery(sb.toString()).
				 setLong("idPosicion",Long.valueOf(idPosicion));

		if(idPersona != null){
			qr.setLong("idPersona",Long.valueOf(idPersona));
		}
		if(idEmpresa != null){
			qr.setLong("idEmpresa",Long.valueOf(idEmpresa));
		}
		return (CandidatoDto) qr.uniqueResult();
	}

	/**
	* Recupera información del candidato con el idpersona
	* @param idPersona, Identificador de la persona
	* @return CandidatoDto candidatoDto
	* @author osy
	*/
	@Transactional(readOnly=true)
	public Candidato getApplicantInfo(CandidatoDto candidatoDto){
		
		String whereCondition = null;
		String whereField = null;
		Long whereValue = null;
		if(candidatoDto.getIdPersona() != null){
			whereCondition = "where candidato.persona.idPersona=:idPersona ";
			whereField = "idPersona";
			whereValue = candidatoDto.getIdPersona();
		}
		if(candidatoDto.getIdEmpresa() != null){
			whereCondition = "where candidato.empresa.idEmpresa=:idEmpresa ";
			whereField = "idEmpresa";
			whereValue = candidatoDto.getIdEmpresa();
		}
		
		return(Candidato) getSession().createQuery(
			"from Candidato as candidato "
			.concat(whereCondition))
			.setLong(whereField, whereValue).uniqueResult();
	}
	
	/**
	* Recupera una lista de candidatos dependiendo del tipo de relacion entre la empresa y la persona
	* @param idPersona
	* @param idEmpresa
	* @param idRol
	* @return lista de objetos CandidatoDto 
	*/
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Candidato> getCanditatosEmpresa(Long idPersona, Long idEmpresa,
			Long idRol){
		return (List<Candidato>) getSession().createQuery(
				new StringBuilder("select CANDIDATO from Candidato as CANDIDATO ")
				.append("inner join CANDIDATO.empresa.relacionEmpresaPersonas as REMPPER ")
				.append("where REMPPER.empresa.idEmpresa= :idEmpresa ")
				.append("and REMPPER.persona.idPersona= :idPersona ")
				.append("and REMPPER.rol.idRol= :idRol ").toString())
				.setLong("idPersona", idPersona)
				.setLong("idEmpresa", idEmpresa)
				.setLong("idRol", idRol).list();
	}

	/**
	* Obtiene informacion de la posicion relacionada al candidato
	* @param idPosicion, el id de la posicion
	* @param idCandidato, el id del candidato
	* @return un objeto CandidatoDto 
	*/
	@Transactional(readOnly=true)
	public CandidatoDto getHandCheck(Long idCandidato) {
		return (CandidatoDto) getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CandidatoDto(").
				append("PERSONAPOS.idPersona,CANDIDATO.persona.idPersona,").
				append("(select REP.persona.idPersona from RelacionEmpresaPersona as REP ").
				append(" where REP.empresa.idEmpresa=CANDIDATO.empresa.idEmpresa  and REP.representante=true),").
				append(" POSICION.nombre,EMPRESA.nombre, PERSONAPOS.nombre || ' ' || ").
				append(" PERSONAPOS.apellidoPaterno || ' ' || PERSONAPOS.apellidoMaterno)").
				append(" from Candidato as CANDIDATO ").
				append("inner join CANDIDATO.posicion as POSICION ").
				append("left outer join POSICION.empresa EMPRESA ").
				append("inner join POSICION.persona as PERSONAPOS ").
				append("WHERE  CANDIDATO.idCandidato= :idCandidato  ").toString()).
				setLong("idCandidato", idCandidato).uniqueResult();
	}

	/**
	* Se modifica el estatus operativo
	* @param idCandidato, el id del candidato
	* @return
	*/
	public int updateHandShake(Long idCandidato, Long idEstatusOperativo,String textoReinvitar) {
		sb=new StringBuilder("update Candidato set ").
						append("estatusOperativo.idEstatusOperativo = :idEstatusOperativo ");
		//si hay reinvitacion				
		if(textoReinvitar != null){
			sb.append(", detalleEstatus= :detalleEstatus  ");
		}
		
		return getSession().createQuery(
				sb.append(" where  idCandidato = :idCandidato ").toString()).
				setLong("idCandidato", idCandidato).
				setLong("idEstatusOperativo", idEstatusOperativo).
				executeUpdate();
	}
	
	/**
	 * Se modifica el campo de inactivo para una persona o empresa
	 * @param idPersona, el id de la persona
	 * @param idEmpresa, el id de la empresa
	 * @param inactivo, el estatus de inactivo
	 * @return
	 */
	public int updateEstatusCandidato(Long idPersona, Long idEmpresa, Long idEstatusCandidato ){
		
		sb=new StringBuilder("update Candidato set estatusCandidato.idEstatusCandidato = :idEstatusCandidato  where ");
		//Se inactiva para persona o empresa
		if(idPersona != null){
			sb.append(" persona.idPersona = :idPersona ");
		}else{
			sb.append(" empresa.idEmpresa  = :idEmpresa ");
		}
		
		Query qr= getSession().createQuery(sb.toString()).
				  setLong("idEstatusCandidato", idEstatusCandidato);
		if(idPersona != null){
			qr.setLong("idPersona", idPersona);
		}else{
			qr.setLong("idEmpresa", idEmpresa);
		}
		return qr.executeUpdate();	
	}
	
	/**
	 * Se cuenta las veces que la persona o empresa es candidato
	 * @param idPersona, el id de la persona
	 * @param idEmpresa, el id de la empresa
	 * @return 
	 */
	@Transactional(readOnly=true)
	public int count(Long idPersona, Long idEmpresa){
		log4j.debug("%& idPersona="+idPersona+" idEmpresa="+idEmpresa );
		
		sb=new StringBuilder("select count(*) from Candidato as CANDIDATO where ");
		//Es persona o empresa
		if(idPersona != null){
			sb.append(" CANDIDATO.persona.idPersona = :idPersona ");
		}else{
			sb.append(" CANDIDATO.empresa.idEmpresa = :idEmpresa ");
		}
		log4j.debug("%& sb= "+sb.toString() );
		Query qr= getSession().createQuery(sb.toString());
		if(idPersona != null){
			qr.setLong("idPersona", idPersona);
		}else{
			qr.setLong("idEmpresa", idEmpresa);
		}
		
		Long resp=(Long)qr.uniqueResult();
		log4j.debug("%& resp="+resp);
		if(resp != null){
			return resp.intValue();
		}else{
			return 0;
		}
		
	}

	/**
	 * 
	 */
	public Long getEstatusOperativo(Long idCandidato) {
		return (Long) getSession().createQuery(
				new StringBuilder("select CANDIDATO.estatusOperativo.idEstatusOperativo ")
				.append("from Candidato as CANDIDATO ")
				.append("where CANDIDATO.idCandidato = :idCandidato ").toString())
				.setLong("idCandidato", idCandidato).uniqueResult();
	}

	/**
	 * Se regresa una lista de objetos candidatos tomando en cuenta 
	 * su regiastro en la tabla AreaPersona
	 * @param idPersona, el id de la persona
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<Candidato> getCanditatosAreaPersona(Long idPersona, Long idArea) {
		return (List<Candidato>) getSession().createQuery(
				new StringBuilder("select DISTINCT C ")
				.append("from AreaPersona as AREA_PERS  ")
				.append("inner join AREA_PERS.persona as PERS ")
				.append("inner join PERS.candidatos as C ")
				.append("inner join C.posicion as POS ")
				.append("inner join POS.perfilPosicions as PERPOS ")
				.append("inner join PERPOS.perfil as PERF ")
				.append("inner join PERF.areaPerfils as AREA_PERF ")
				.append("inner join AREA_PERF.area as AREA ")				
				.append("where PERS.idPersona = :idPersona ")
				.append("and AREA.idArea= :idArea").toString())
				.setLong("idPersona", idPersona)
				.setLong("idArea", idArea).list();
	}

	/**
	 * Se obtiene un objeto candidato dado el idPersona y idPosicion
	 * @param idPersona, es el identificador de la persona
	 * @param idPosicion, es el identificador de la posicion
	 * @return  
	 */
	@Override
	public Candidato get(long idPosicion, long idPersona) {
		return (Candidato) getSession().createQuery(
				new StringBuilder("select C ")
				.append(" from Candidato as C ")
				.append(" inner join C.posicion as PS ")
				.append(" inner join C.persona  as PR ")
				.append(" where PS.idPosicion= :idPosicion ")
				.append(" and PR.idPersona= :idPersona ").toString())
				.setLong("idPosicion", idPosicion)
				.setLong("idPersona", idPersona)
				.uniqueResult();
	}

	/**
	 * Se obtiene el valor maximo del orden de los candidatos en una posición
	 * @param idPosicion, el id de la posicion
	 * @return el máximo valor del orden
	 */
	@Override
	public Short getMaxOrder(Long idPosicion) {
		return (Short) getSession().createQuery(
				new StringBuilder("select max(C.orden) ")
				.append("from Candidato as C ")
				.append("inner join C.posicion as PS ")
				.append("where PS.idPosicion = :idPosicion ").toString())
				.setLong("idPosicion", idPosicion).uniqueResult();
	}

	/**
	 * Se modifica el Estatus Operativo del candidato correspondiente
	 * su regiastro en la tabla AreaPerfil
	 * @param idCandidato, el id del candidato
	 * @param idEstatusOperativo, el id del Estatus Operativo
	 * @return int
	 */
	@Override
	public int updateEstatusOperativo(Long idCandidato, Long idEstatusOperativo) {
		return getSession().createQuery(new StringBuilder("update Candidato set ").
				append("estatusOperativo.idEstatusOperativo = :idEstatusOperativo ").
				append(" where  idCandidato = :idCandidato ").toString()).
				setLong("idCandidato", idCandidato).
				setLong("idEstatusOperativo", idEstatusOperativo).
				executeUpdate();
	}

	/**
	 * Se obtiene una lista de los postulantes de la posición correspondiente
	 * @param idPosicion, el id de la posicion
	 * @return List<ModeloRscPosFaseDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CandidatoDto> get(Long idPosicion) {
		return (List<CandidatoDto>) getSession().createQuery(
				new StringBuilder("select  DISTINCT new net.tce.dto.CandidatoDto(  ")
				.append("REP.idRelacionEmpresaPersona,PCAN.idPosibleCandidato,CAN.idCandidato)")
				.append("from Monitor as MON ")
				.append("inner join MON.modeloRscPosFase as MRSCPF ")
				.append("inner join MON.trackingMonitors TM ")
				.append("inner join TM.trackingPostulante as TP ")
				.append("inner join TM.relacionEmpresaPersona as REP ")
				.append("inner join MRSCPF.modeloRscPos as MRSCP ")
				.append("inner join MRSCP.perfilPosicion as PP ")				
				.append("inner join PP.posicion as POS ")
				.append("left outer join TP.candidato CAN ")
				.append("left outer join TP.posibleCandidato PCAN ")
				.append("where  POS.idPosicion = :idPosicion ")				
				.append("order by REP.idRelacionEmpresaPersona ").toString())
				.setLong("idPosicion", idPosicion).list();
	}
}
