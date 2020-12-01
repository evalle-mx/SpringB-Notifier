package net.tce.dao.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.tce.dao.NotificacionDao;
import net.tce.dto.NotificationDto;
import net.tce.model.Notificacion;
import net.tce.util.Constante;

@Repository("notificacionDao")
public class NotificacionDaoImpl extends PersistenceGenericDaoImpl<Notificacion, Object> 
	implements NotificacionDao {
	Logger log4j = Logger.getLogger( this.getClass());
	
	final private String sReceptor= new StringBuilder("select new net.tce.dto.NotificationDto(").
									append("PERSONA.idPersona,PERSONA.email,PERSONA.tokenInicio,").
									append("PERSONA.nombre ||' '|| PERSONA.apellidoPaterno||' '|| ").
									append(" (CASE when PERSONA.apellidoMaterno is null THEN ''").
									append(" else PERSONA.apellidoMaterno end) ").toString();

	/**
	 * Se obtine una lista de las notificaciones del receptor
	 * @param idPersonaReceptor, el id de la persona_receptor
	 * @return List<NotificationDto>
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> get(long idPersonaReceptor) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder("select new net.tce.dto.NotificationDto(").
				append("NOTIFICACION.idNotificacion, NOTIFICACION.idEmisor, ").
				append("NOTIFICACION.tipoEmisor,TIPOEVENTO.claveEvento,NOTIFICACION.texto, ").
				append(" NOTIFICACION.vista, NOTIFICACION.listaMecanismoPasivo )").
				append("from Notificacion as NOTIFICACION ").
				append("inner join NOTIFICACION.tipoEvento as TIPOEVENTO ").
				append("inner join NOTIFICACION.persona as PERSONA ").
				append("where PERSONA.idPersona = :idPersonaReceptor  ").toString()).
				setLong("idPersonaReceptor", idPersonaReceptor).list();
	}

	/**
	 * Se obtiene una lista de  personas que han creado posiciones relacionadas con el candidato dado
	 * @param idPersona, el id de la persona candidato
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> getPositionPeopleOfCandidate(Long idPersona) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(",CANDIDATO.idCandidato) from Posicion as POSICION ").
				append("inner join POSICION.candidatos as CANDIDATO ").
				append("inner join POSICION.persona as PERSONA ").
				append("where CANDIDATO.persona.idPersona =:idPersona ").toString()).
				setLong("idPersona", idPersona).list();
	}

	/**
	 * Se obtiene una lista de  personas que han creado la posición proporcionada, 
	 * en este momento sólo es una, sin embargo en un futuro
	 * con los perfiles públicos/privados podría ser una lista
	 * @param idPosicion, el id de la posicion
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> getPositionPeopleOfPosition(Long idPosicion) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(") from Posicion as POSICION ").
				append("inner join POSICION.persona as PERSONA ").
				append("where POSICION.idPosicion =:idPosicion ").toString()).
				setLong("idPosicion", idPosicion).list();
	}

	/**
	 * Se obtiene una lista de personas candidatas de la posición proporcionada
	 * @param idPosicion, el id de la posicion
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> getPeopleCandidateOfPosition(Long idPosicion) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(" ) from Candidato as CANDIDATO ").
				append(" inner join CANDIDATO.persona as PERSONA ").
				append(" where CANDIDATO.posicion.idPosicion =:idPosicion ").
				append(" and  CANDIDATO.estatusCandidato.idEstatusCandidato =").
				append(Constante.ESTATUS_CANDIDATO_ACEPTADO).toString()).
				setLong("idPosicion", idPosicion).list();
	}

	/**
	 * Se obtiene una lista de personas candidatas de la posición proporcionada
	 * @param idPersona, el id de la idPersona
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> getPeople(Long idPersona) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(") from Persona as PERSONA ").
				append("where PERSONA.idPersona =:idPersona ").toString()).
				setLong("idPersona", idPersona).list();
	}

	/**
	 * Se obtiene una lista de personas asociadas de la empresa y tipo de relación proporcionada
	 * @param idEmpresa, el id de la empresa
	 * @param idRol, el id del tipo de relacion
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> getPeopleByRelationType(Long idEmpresa,
			Long idRol) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(") from RelacionEmpresaPersona as REP ").
				append("inner join REP.persona as PERSONA ").
				append(" where REP.empresa.idEmpresa=:idEmpresa ").
				append(" and REP.rol.idRol=:idRol").toString()).
				setLong("idEmpresa", idEmpresa).
				setLong("idRol", idRol).list();
	}

	/**
	 * Se obtiene una lista de  personas de las posiciones a las cuales ha aplicado la empresa_candidato proporcionada
	 * @param idEmpresa, el id de la empresa_candidato
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> getPeoplePositionOfCandidateEnterprise(Long idEmpresa) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(" ) from Posicion as POSICION ").
				append("inner join POSICION.candidatos as CANDIDATO ").
				append("inner join POSICION.persona as PERSONA ").
				append("where CANDIDATO.empresa.idEmpresa=:idEmpresa").toString()).
				setLong("idEmpresa", idEmpresa).list();
	}

	/**
	 * Se obtiene una lista de personas_candidato de la posicion relacionada a la persona correspondiente
	 * @param idPersona, el id de la persona_posicion
	 * @param empresaNulo, 	true	la persona no pertenece a una empresa
	 * 						false	la persona pertenece a una empresa
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> getPeopleCandidateOfPositionPerson(Long idPersona, boolean empresaNulo) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(" ,POSICION.nombre) from Posicion as POSICION ").
				append("inner join POSICION.candidatos as CANDIDATO ").
				append("inner join CANDIDATO.persona as PERSONA ").
				append("where POSICION.persona.idPersona=:idPersona ").
				append("and  CANDIDATO.estatusCandidato.idEstatusCandidato =").
				append(Constante.ESTATUS_CANDIDATO_ACEPTADO).
				append(" and POSICION.empresa.idEmpresa is ").
				append(empresaNulo ? " ":" not ").append("null").toString()).
				setLong("idPersona", idPersona).list();
	}

	/**
	 * Se obtiene una lista de personas asociadas de la empresa y tipo de relación proporcionada
	 * @param idCandidate, identificador del candidato
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<NotificationDto> getPersonOfCandidate(Long idCandidate) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(",CANDIDATO.idCandidato) from Candidato as CANDIDATO  ").
				append("inner join CANDIDATO.persona as PERSONA ").
				append("where CANDIDATO.idCandidato =:idCandidate ").toString()).
				setLong("idCandidate", idCandidate).list();
	}

	/**
	 * Se obtiene una lista de personas dado el idPosibleCandidate
	 * @param idPosibleCandidato, identificador del posible_candidato
	 * @return lista objetos NotificationDto
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationDto> getPersonOfPosibleCandidate(Long idPosibleCandidato) {
		return (List<NotificationDto>) getSession().createQuery(
				new StringBuilder(sReceptor).
				append(", PC.idPosibleCandidato,PC.confirmado ) from PosibleCandidato as PC  ").
				append("inner join PC.relacionEmpresaPersona REP ").
				append("inner join REP.persona PERSONA ").
				append("where PC.idPosibleCandidato =:idPosibleCandidato ").toString()).
				setLong("idPosibleCandidato", idPosibleCandidato).list();
	}
	
}
