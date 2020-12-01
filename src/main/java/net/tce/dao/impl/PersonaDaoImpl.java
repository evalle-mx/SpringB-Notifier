package net.tce.dao.impl;

import java.util.List;
import javax.inject.Inject;
import net.tce.dao.PersonaDao;
import net.tce.dto.CurriculumDto;
import net.tce.dto.NotificacionDto;
import net.tce.model.Persona;
import net.tce.util.Constante;
import net.tce.util.DBInterpreter;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("personaDao")
public class PersonaDaoImpl extends PersistenceGenericDaoImpl<Persona, Object> 
implements PersonaDao {
//	Logger log4j = Logger.getLogger( this.getClass());
	static final Logger log4jQz = Logger.getLogger("QUARTZ");


	@Inject
	DBInterpreter dbInterpreter;
	
	/**
	 * Se obtiene un objeto persona con el id del documento de clasificacion de la BD operativa
	 * @param Long idTextoClasificacion 
	 * @return objeto persona
	 */
	public Persona getPersonaByIdClassDoc(String idTextoClasificacion){
		return (Persona) this.getSession().createQuery(
				new StringBuilder("from Persona as P").
				append(" inner join P.documentoClasificacions DC ").
				append(" where DC.idTextoClasificacion = :idTextoClasificacion").toString()).
				setString("idTextoClasificacion",new String(idTextoClasificacion)).uniqueResult();
	}
	
	/**
	 * Se obtiene una lista de personas sin clasificar
	 * @return lista objetos persona
	 */
	@SuppressWarnings("unchecked")
	public List<Persona> getPersonasSinClasificar() {
		return this.getSession().createQuery(
				new StringBuilder("select PERSONA from ").
				append("Persona as PERSONA where PERSONA.clasificado=").
				append(Constante.CANDIDATO_NO_CLASIFICADO).
				append(" and PERSONA.estatusInscripcion=").
				append(Constante.ESTATUS_INSCRIPCION_PUBLICADO).
				append(" order by PERSONA.idPersona").toString()).list();
	}
	
	/**
	 * 
	 */
	public Object getDtoByIdExterno(String claveInterna, Long idEmpresa){
		Object objHql =  getSession().createQuery(
			new StringBuilder("select new net.tce.dto.ComunDto(")
		.append( dbInterpreter.fnToChar("PERSONA.idPersona",0))
		.append( ", DC.idTextoClasificacion, PERSONA.nombre ) ")
		.append("from Persona as PERSONA ")
		.append("inner join PERSONA.relacionEmpresaPersonas as REP ")
		.append("inner join PERSONA.documentoClasificacions as DC ")
		.append("inner join REP.empresa as EMPRESA ")
		.append("where REP.claveInterna = :claveInterna ")
		.append("and EMPRESA.idEmpresa = :idEmpresa ").toString())
		.setString("claveInterna", claveInterna)
		.setLong("idEmpresa", idEmpresa).uniqueResult();
		this.getSession().flush();//TODO verificar uso de IdEmpresa
		return objHql;
	}

	/**
	 * Genera lista con personas por tipo de recordatorio (1. ConfInscrip, 2.Publicacion)
	 */
	@SuppressWarnings("unchecked")
	public List<NotificacionDto> getReminder(int numero, byte typeReminder){
		StringBuilder sb=new StringBuilder("SELECT new net.tce.dto.NotificacionDto(").
		append("P.idPersona, P.fechaCreacion, P.email");
		
		//Confirmarción de Inscripcion
		if(typeReminder == Constante.TYPE_REMINDER_CONFIRM_INSCRIP){
			sb.append(", P.fechaConfirmarInscripcion, P.numeroConfirmarInscripcion) ");
			
		//publicación	
		}else if(typeReminder == Constante.TYPE_REMINDER_PUBLICATION){
			sb.append(", P.fechaDebePublicar, P.numeroDebePublicar) ");
		}
		
		sb.append(" FROM Persona as P WHERE P.estatusInscripcion=");
		
		//Confirmarción de Inscripcion
		if(typeReminder == Constante.TYPE_REMINDER_CONFIRM_INSCRIP){
			sb.append(Constante.ESTATUS_INSCRIPCION_CREADO).
			append(" AND  P.fechaModificacion is null ");
			
		//publicación	
		}else if(typeReminder == Constante.TYPE_REMINDER_PUBLICATION){
			sb.append(Constante.ESTATUS_INSCRIPCION_ACTIVO);
		}
		
		//Para el número de veces a infinito
		if(numero > -1){
			//Confirmacion de Inscripcion
			if(typeReminder == Constante.TYPE_REMINDER_CONFIRM_INSCRIP){
				sb.append(" AND P.numeroConfirmarInscripcion < ").append(numero);
			
			//publicacion	
			}else if(typeReminder == Constante.TYPE_REMINDER_PUBLICATION){
				sb.append(" AND P.numeroDebePublicar < ").append(numero);
			}			
		}		
		sb.append(" ORDER BY P.idPersona");
		return (List<NotificacionDto>)this.getSession().createQuery(sb.toString()).list();
	}
	
	/**
	 * Se actualiza la propiedad numeroConfirmarInscripcion
	 * @param idPersona, identificadir unico de la persona
	 * @param numeroConfirmarInscripcion
	 * @return
	 */
	public int updateNumeroReminder(NotificacionDto notificacionDto, byte typeReminder){
		
		log4jQz.info("<updateNumeroReminder> numeroDelTipo="+ notificacionDto.getNumeroDelTipo()+
					" fechaActual="+notificacionDto.getFechaActual()+
					" idPersona="+notificacionDto.getIdPersona() );
		StringBuilder sb=new StringBuilder("update Persona set ");
		
		//Confirmarción de Inscripcion
		if(typeReminder == Constante.TYPE_REMINDER_CONFIRM_INSCRIP){
			sb.append(" numeroConfirmarInscripcion = :numeroDelTipo ");
		
		//publicación	
		}else if(typeReminder == Constante.TYPE_REMINDER_PUBLICATION){
			sb.append(" numeroDebePublicar = :numeroDelTipo ");
		}
		
		//cuando el NumeroConfirmarInscripcion = 0 se setea la fecha actual a la FechaConfirmarInscripcion
		if(notificacionDto.getNumeroDelTipo().shortValue() == 0){
			//Confirmarción de Inscripcion
			if(typeReminder == Constante.TYPE_REMINDER_CONFIRM_INSCRIP){
				sb.append(", fechaConfirmarInscripcion = :fechaActual ");	
				
			//publicación	
			}else if(typeReminder == Constante.TYPE_REMINDER_PUBLICATION){
				sb.append(", fechaDebePublicar = :fechaActual ");
			}			
		}
		sb.append(" where  idPersona = :idPersona ");		
		Query query=getSession().createQuery(sb.toString());

		//Toma la fecha actual 
		if(notificacionDto.getNumeroDelTipo().shortValue() == 0){
			query.setTimestamp("fechaActual", notificacionDto.getFechaActual());		
		}
		
		//Confirmarción de Inscripcion
		query.setShort("numeroDelTipo", ((short)(notificacionDto.getNumeroDelTipo().shortValue() +(short)1))).	
		setLong("idPersona", notificacionDto.getIdPersona());
		return query.executeUpdate();			
	}
	
	/**
	 * Se cambia el estatus sePreClasifica
	 * @param sePreClasifica
	 * @param idEmpresa
	 * @return
	 */
	public int updateSePreClasifica(boolean sePreClasifica, Long idPersona) {
		log4j.debug("<updateSePreClasifica> sePreClasifica="+sePreClasifica+
				" idPersona="+idPersona);
		
		return getSession().createQuery(
				new StringBuilder("update Persona set sePreClasifica = :sePreClasifica ")
				.append(" where  idPersona = :idPersona ").toString()).
				setLong("idPersona", idPersona).
				setBoolean("sePreClasifica", Boolean.valueOf(sePreClasifica)).
				executeUpdate();
	}
	
	/**
	 * Obtiene una lista de curriculums si la direccion de correo existe
	 * @param email, direccion de correo
	 * @return lista de objetos CurriculumDto
	 */
	@SuppressWarnings("unchecked")
	public List<CurriculumDto> findEmail(String email){
		return (List<CurriculumDto>)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CurriculumDto(")
				.append("PERSONA.idPersona,REP.empresa.idEmpresa, REP.claveInterna,'') ")
				.append("from Persona as PERSONA left outer join PERSONA.relacionEmpresaPersonas as REP ")
				.append("where PERSONA.email= :email)").toString())
				.setString("email", email).list();
	}
	
	/**
	 * Modifica el campo token_inicio de Persona
	 * @param tokenInicio
	 * @param idPersona
	 * @return 
	 */
	public int updateTokenInicio(String tokenInicio, Long idPersona){
		return getSession().createQuery(
				new StringBuilder("update Persona set tokenInicio = :tokenInicio ")
				.append(" where  idPersona = :idPersona ").toString()).
				setString("tokenInicio", tokenInicio).
				setLong("idPersona", idPersona).
				executeUpdate();
	}
	
	/**
	 * Obtiene parcialmente info de persona
	 * @param idPersona
	 * @return 
	 */
	public CurriculumDto getPersonaNombre(Long idPersona) {
		return (CurriculumDto)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CurriculumDto(")
				.append("PERSONA.nombre, PERSONA.apellidoPaterno,PERSONA.apellidoMaterno)")
				.append("from Persona as PERSONA where PERSONA.idPersona = :idPersona)").toString())
				.setLong("idPersona", idPersona).uniqueResult();
	}
}
