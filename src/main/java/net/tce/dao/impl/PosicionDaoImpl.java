package net.tce.dao.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.tce.dao.PosicionDao;
import net.tce.dto.PosicionDto;
import net.tce.model.Posicion;
import net.tce.util.Constante;
import net.tce.util.DBInterpreter;
import net.tce.util.DateUtily;

@Repository("posicionDao")
public class PosicionDaoImpl extends PersistenceGenericDaoImpl<Posicion, Object> 
implements PosicionDao {

	Logger log4j = Logger.getLogger( this.getClass());
	StringBuilder sb;
	
	@Inject
	DBInterpreter dbInterpreter;
	
	/**
	 * Obtiene lista de Posiciones, en base al perfil 
	 * @param idPerfil
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Posicion> getByPerfil(Long idPerfil) {
		log4j.debug("<getPartial> Inicio...");
		sb = new StringBuilder("SELECT POS from Posicion AS POS ")
		.append(" INNER JOIN POS.perfilPosicions AS PEPOS ")
		.append(" WHERE PEPOS.perfil.idPerfil = :idPerfil");
		List<Posicion> lsPosicion = null;
		lsPosicion = this.getSession().createQuery(
				sb.toString()).setLong("idPerfil", idPerfil).list();
		return lsPosicion;
	}

	/**
	 * Regresa una lista de objetos PosicionDto concurrentes para la busqueda sistematica
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<PosicionDto>  getPosicionConcurrente(){
		return (List<PosicionDto>) this.getSession().createQuery(
				new StringBuilder("select  new net.tce.dto.PosicionDto( POSICION.idPosicion,").
				append(" POSICION.fechaUltimaBusqueda,POSICION.fechaCaducidad,").
				append(" POSICION.periodoBusqueda, POSICION.modificado) ").
				append(" from Posicion as POSICION ").
				append(" inner join POSICION.empresa as EMPRESA ").
				append(" inner join EMPRESA.tipoSuscriptor as TIPOSUSC ").
				append(" inner join POSICION.estatusPosicion as ESTATUSPOS ").
				append(" where POSICION.fechaProgramacion <= :fechaActual ").
				append(" and TIPOSUSC.idTipoSuscriptor=").
				append(Constante.TIPO_SUSCRIPTOR_PREMIUM).
				append(" and ESTATUSPOS.idEstatusPosicion=").
				append(Constante.ESTATUS_POSICION_PUBLICADA).
				append(" and ESTATUSPOS.valorActivo= ").
				append( dbInterpreter.fnBoolean("1")).
				append(" and POSICION.concurrente= ").
				append( dbInterpreter.fnBoolean("1")).
				append(" order by POSICION.idPosicion").toString()).
				setTimestamp("fechaActual", DateUtily.getToday()).list();
	}
	
	/**
	 * Se actualiza la fecha de modificacion
	 * @param idPersona, id de persona
	 * @param fechaModificacion, fecha de moficación
	 * @return
	 */
	public int updateFechaUltimaBusqueda(Long idPosicion,Date fechaUltimaBusqueda) {
		return getSession().createQuery(
				new StringBuilder("update Posicion set fechaUltimaBusqueda = :fechaUltimaBusqueda ")
				.append(" where  idPosicion = :idPosicion ").toString()).
				setLong("idPosicion", idPosicion).
				setTimestamp("fechaUltimaBusqueda", fechaUltimaBusqueda).
				executeUpdate();
	}

	/**
	 * Se cuenta las veces que la persona o empresa ha creado una posicion
	 * @param idPersona, el id de la persona
	 * @param idEmpresa, el id de la empresa
	 * @param personaConEmpresa, 	true	si la persona pertenece a una empresa
	 * 								false	si la persona no pertenece a una empresa
	 * @return 
	 */
	@Transactional(readOnly=true)
	public int count(Long idPersona, Long idEmpresa, boolean personaConEmpresa) {
		log4j.debug("%& count ->idPersona="+idPersona+" idEmpresa="+idEmpresa );
		
		sb=new StringBuilder("select count(*) from Posicion as POSICION where ");
		//Es persona o empresa
		if(idPersona != null){
			sb.append(" POSICION.persona.idPersona = :idPersona ")
			.append(" and POSICION.empresa.idEmpresa is ").
			append(personaConEmpresa ? " not ":" ").append(" null ");
		}else{
			sb.append(" POSICION.empresa.idEmpresa = :idEmpresa ");
		}
		sb.append(" and POSICION.estatusPosicion.idEstatusPosicion <> ").
		append(Constante.ESTATUS_POSICION_DESACTIVADA);
		
		log4j.debug("%& sb= "+sb.toString() );
		Query qr= getSession().createQuery(sb.toString());
		if(idPersona != null){
			qr.setLong("idPersona", idPersona);
		}else{
			qr.setLong("idEmpresa", idEmpresa);
		}
		
		Long resp=(Long)qr.uniqueResult();
		log4j.debug("%& posiciones asociadas a la persona -> resp="+resp);
		if(resp != null){
			return resp.intValue();
		}else{
			return 0;
		}
	}

	/**
	 * Se cambia el estatus de registro de la persona correspondiente
	 * @param idPersona
	 * @param idEstatusPosicion
	 * @param empresaNulo, 	true 	si el valor de la empresa es nulo
	 * 						false	si el valor de la empresa no es nulo
	 * @return
	 */
	public int updateEstatusPosicion(Long idPersona,Long idEstatusPosicion, boolean empresaNulo) {
		sb=new StringBuilder("update Posicion set estatusPosicion.idEstatusPosicion = :idEstatusPosicion ").
					  append(" where  persona.idPersona = :idPersona ");
		//se filtra la empresa
		sb.append(" and empresa.idEmpresa is ").
		append(empresaNulo ? "":" not ").append(" null ");
		
		return getSession().createQuery(sb.toString()).
							setLong("idPersona", idPersona).
							setLong("idEstatusPosicion", idEstatusPosicion).
							executeUpdate();		
	}

	/**
	 * Se obtiene una Posicion dependiendo de su clave externa
	 * @param cveExterna
	 * @return
	 */
	@Transactional(readOnly=true)
	public Posicion getByCveExterna(String cveExterna){
		log4j.debug("<getByCveExterna> clave Externa (Posicion.claveInterna)=" + cveExterna);
		return  (Posicion) this.getSession().createQuery(
				new StringBuilder("from Posicion where claveInterna = :cveExterna").toString()).
				setString("cveExterna", cveExterna).uniqueResult();
	}
	
	/**
	 * Se obtiene una lista de posiciones sin clasificar
	 * @return lista objetos posicion
	 */
	@SuppressWarnings("unchecked")
	public List<Posicion> getPositionUnclassified(){
		return (List<Posicion>) this.getSession().createQuery(
				new StringBuilder("select distinct POSICION from Posicion as  POSICION  ").
				append(" inner join POSICION.perfilPosicions as PERFILPOSICION ").
				append(" inner join PERFILPOSICION.perfil as PERFIL ").
				append(" where PERFIL.clasificado =").
				append(Constante.CANDIDATO_NO_CLASIFICADO).
				append(" and POSICION.estatusPosicion=").
				append(Constante.ESTATUS_POSICION_PUBLICADA).
				append(" order by POSICION.idPosicion").toString()).list();
	}
	
	
	/**
	 * Se actualiza el estatus de modificado
	 * @param idPersona, id de persona
	 * @param modificado, fue modificado -> true, de lo contrario -> false
	 */
	public int updateModificado(Long idPosicion, Boolean modificado) {
		return getSession().createQuery(
				new StringBuilder("update Posicion set modificado = :modificado ")
				.append(" where  idPosicion = :idPosicion ").toString()).
				setLong("idPosicion", idPosicion).
				setBoolean("modificado", modificado).
				executeUpdate();
	}

	/**
	 * Se obtiene el id de la posicion correspondiente al monitor
	 * @param idTrackingMonitor, identificador rackingPostulante
	 * @return Long
	 */
	@Override
	public Long getByMonitor(Long idMonitor) {
		return (Long)getSession().createQuery(
				new StringBuilder("select DISTINCT POS.idPosicion ").
				append("from ModeloRscPos MRP ").
				append("inner join MRP.modeloRscPosFases MRPF ").
				append("inner join MRPF.monitors MON ").
				append("inner join MON.trackingMonitors TMON ").
				append("inner join MRP.perfilPosicion PP ").
				append("inner join PP.posicion POS ").
				append("where MON.idMonitor= :idMonitor ").toString()).
				setLong("idMonitor", idMonitor).
				uniqueResult();
	}

}
