package net.tce.dao.impl;

import java.util.List;
import net.tce.dao.ModeloRscPosDao;
import net.tce.dto.ModeloRscPosDto;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.model.ModeloRscPos;
import net.tce.model.ModeloRscPosFase;
import net.tce.model.Monitor;
import net.tce.model.Rol;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("modeloRscPosDao")
public class ModeloRscPosDaoImpl extends PersistenceGenericDaoImpl<ModeloRscPos, Object> 
implements ModeloRscPosDao{

	Logger log4j = Logger.getLogger( this.getClass());
	StringBuilder sbQuery;
	Query qr;


	/**
	 * Se obtiene una lista de objetos  ModeloRscPosFase
	 * @param idModeloRscPos
	 * @return lista de objetos  ModeloRscPosFase
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<ModeloRscPosFase> getModeloRscPosFases(Long idModeloRscPos) {
		return (List<ModeloRscPosFase>)getSession().createQuery(
				new StringBuilder("SELECT MRSCPF ")
				.append(" from ModeloRscPos as MRSCP   ")
				.append(" inner join  MRSCP.modeloRscPosFases MRSCPF  ")
				.append(" where MRSCP.idModeloRscPos =:idModeloRscPos ")
				.append(" order by MRSCPF.orden, MRSCPF.actividad ").toString())
				.setLong("idModeloRscPos", idModeloRscPos).list();
	}

	/**
	 * Se obtiene una lista de objetos  ModeloRscPosFase dado el idModeloRscPos
	 * @param idModeloRscPos, identificador ModeloRscPos
	 * @return lista de objetos  ModeloRscPosFaseDto solo con los ids
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<ModeloRscPosFaseDto> getIdsModeloRscPosFases(Long idModeloRscPos, Boolean activo) {
		return (List<ModeloRscPosFaseDto>)getSession().createQuery(
				new StringBuilder("SELECT new net.tce.dto.ModeloRscPosFaseDto(MRSCPF.idModeloRscPosFase, ")
				.append("MRSCPF.nombre, MRSCPF.fechaInicio, MRSCPF.fechaFin, MRSCPF.orden,MRSCPF.actividad ) ")
				.append(" from ModeloRscPos as MRSCP ")
				.append(" inner join  MRSCP.modeloRscPosFases MRSCPF ")
				.append(" where MRSCP.idModeloRscPos =:idModeloRscPos ")
				.append(activo == null ? "":"and MRSCPF.activo="+activo.booleanValue())
				.append(" order by MRSCPF.orden, MRSCPF.modeloRscPosFase ").toString())
				.setLong("idModeloRscPos", idModeloRscPos).list();
	}
	
	/**
	 * Se obtiene una lista de objetos  Monitor
	 * @param idModeloRscPos, identificador del ModeloRscPos
	 * @param idPersona, identificador de la persona
	 * @return lista de objetos  Monitor
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Monitor> getMonitor(Long idModeloRscPos, Long idPersona) {
		return (List<Monitor>)getSession().createQuery(
				new StringBuilder("SELECT MNT ")
				.append(" from ModeloRscPos as MRSCP ")
				.append(" inner join  MRSCP.modeloRscPosFases as MRSCPF ")
				.append(" inner join  MRSCPF.monitors as MNT ")
				.append(" inner join  MNT.relacionEmpresaPersona as REP ")
				.append(" inner join  REP.persona as P ")
				.append(" where MRSCP.idModeloRscPos =:idModeloRscPos ")
				.append(" and P.idPersona =:idPersona ")
				.append(" order by MRSCPF.orden, MRSCPF.actividad ").toString())
				.setLong("idModeloRscPos", idModeloRscPos)
				.setLong("idPersona", idPersona).list();
	}

	/**
	 * Se obtiene una lista de objetos  ModeloRscPosDto
	 * @param idPosicion, identificador de la posicion
	 * @param monitor, indica si el esquema es de monitor o no
	 * @param relacionEntreEsquemas, indica si se investiga si hay relacion entre esquemas
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeloRscPosDto> getModeloRscPos(Long idPosicion,
												boolean esMonitor,
												boolean relacionEntreEsquemas,
												Boolean modeloMonitorPrincipal,
												boolean relMonitores,
												Boolean faseActivo) {
		
		log4j.debug("getModeloRscPos() -> idPosicion="+idPosicion+
				" esMonitor="+esMonitor+
				" relacionEntreEsquemas="+relacionEntreEsquemas+
				" modeloMonitorPrincipal="+modeloMonitorPrincipal+
				" relMonitores="+relMonitores);
		
		sbQuery = new StringBuilder("SELECT new net.tce.dto.ModeloRscPosDto(")
		.append("count(*),MRSCP.idModeloRscPos) ")
		.append("from ModeloRscPos as MRSCP ")
		.append("inner join MRSCP.modeloRscPosFases as MRSCPF ")
		.append("inner join MRSCP.perfilPosicion as PP ")
		.append("inner join PP.posicion as POS ");
		
		if(relacionEntreEsquemas){
			sbQuery.append("inner join MRSCPF.modeloRscPosFase MRSCPF1 ");
		}
		
		//solo cuando el esquema pertenece a un monitor
		if(relMonitores){//esMonitor && esMonitorPrincipal != null
			sbQuery.append("inner join MRSCPF.monitors as MON  ");
		}
		 
		sbQuery.append("where POS.idPosicion =:idPosicion ")
		.append("and MRSCP.monitor =:monitor ");
		
		
		//si es modelo monitor principal
		if(modeloMonitorPrincipal != null){
			sbQuery.append(" and MRSCP.principal = ")
			.append(modeloMonitorPrincipal.booleanValue());
		}
		
		//si es modelo monitor principal
		if(faseActivo != null){
			sbQuery.append(" and MRSCPF.activo = ")
			.append(faseActivo.booleanValue());
		}
		
		sbQuery.append(" and MRSCP.activo = true ").
		append(" group by MRSCP.idModeloRscPos");
		
		log4j.debug("getModeloRscPos() -> sbQuery="+sbQuery.toString());
		qr= this.getSession().createQuery(sbQuery.toString())
			.setLong("idPosicion", idPosicion)
			.setBoolean("monitor", esMonitor);
		
		return (List<ModeloRscPosDto>)qr.list();
	}

	/**
	 * Se obtiene el id del ModeloRsc Posicion Principal dado la posición
	 * @param idPosicion, identificador de la posicion
	 */
	@Override
	public Long readModeloRscPosPrincipal(Long idPosicion) {
		return (Long)getSession().createQuery(
				new StringBuilder("SELECT MRSCP.idModeloRscPos ")
				.append("from ModeloRscPos as MRSCP  ")
				.append("inner join MRSCP.perfilPosicion as PP ")
				.append("inner join PP.posicion as POS  ")
				.append("where POS.idPosicion =:idPosicion ")
				.append("and MRSCP.rol is null ")
				.append("and MRSCP.monitor is null ").toString())
				.setLong("idPosicion", idPosicion).uniqueResult();
	}

	/**
	 * Se obtiene el id del ModeloRsc Posicion Rol dado la posición
	 * @param idPosicion, identificador de la posicion
	 * @param monitor, identificador de la posicion
	 * @param principal, identificador de la posicion
	 */
	@Override
	public Long readIdModeloRscPosRol(Long idPosicion, Boolean monitor, Boolean principal) {
		
		sbQuery = new StringBuilder("SELECT MRSCP.idModeloRscPos ")
				.append("from ModeloRscPos as MRSCP  ")
				.append("inner join MRSCP.perfilPosicion as PP ")
				.append("inner join PP.posicion as POS  ")
				.append("inner join MRSCP.modeloRscPosFases as MRSCPF ");
		
		if(principal != null){
			sbQuery.append("inner join MRSCPF.monitors as MON  ");
		}
				
		sbQuery.append("where POS.idPosicion =:idPosicion ")
				.append("and MRSCP.monitor =:monitor ");
		
		if(principal != null){
			sbQuery.append("and MON.principal =:principal ");
		}
		
		qr= this.getSession().createQuery(sbQuery.toString())
				.setLong("idPosicion", idPosicion)
				.setBoolean("monitor", monitor);
			
		//solo cuando el esquema pertenece a un monitor
		if(principal != null){
			qr.setBoolean("principal", principal);
		}
		
		return (Long) qr.uniqueResult();
	}

	/**
	 * Se obtiene la posición dado idModeloRscPos
	 * @param idModeloRscPos, identificador del ModeloRscPos
	 * @return 
	 */
	public Long getIdPosicion(Long idModeloRscPos) {
		return (Long)getSession().createQuery(
				new StringBuilder("select POS.idPosicion ")
				.append("from ModeloRscPos as MRSCP ")
				.append("inner join MRSCP.perfilPosicion as PP ")
				.append("inner join PP.posicion as POS ")
				.append("where MRSCP.idModeloRscPos =:idModeloRscPos ").toString())
				.setLong("idModeloRscPos", idModeloRscPos).uniqueResult();
	}


	/**
	 * Se obtiene idModeloRscPos dado la posicion,el rol y si es activo
	 * @param idPosicion, identificador de la posicion
	 * @param idRol, identificador del rol
	 * @param activo, activo si es true false si no
	 * @return 
	 */
	@Override
	public Long getByPosicion(Long idPosicion, Long idRol,
			Boolean activo) {
		return (Long)getSession().createQuery(
				new StringBuilder("select MRP.idModeloRscPos ")
				.append("FROM ModeloRscPos as MRP ")
				.append("inner join MRP.perfilPosicion as PEPOS ")
				.append("inner join PEPOS.posicion as POS ")
				.append("inner join MRP.rol as ROL ")
				.append("WHERE POS.idPosicion =:idPosicion ")
				.append("AND ROL.idRol =:idRol ")
				.append("AND MRP.activo =:activo ").toString())
				.setLong("idPosicion", idPosicion)
				.setLong("idRol", idRol)
				.setBoolean("activo", activo)
				.uniqueResult();
	}
	/**
	 * Se obtiene el numero de modelosRSC monitor de la posicion dada
	 * @param idPosicion, el identificador del Posicion
	 * @return numero de modelosRSC  	 
	 * */
	@Override
	public Long countMonitoresByPosicion(Long idPosicion) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select count(*) ")
				.append("from ModeloRscPos as MRSCP ")
				.append("inner join MRSCP.perfilPosicion as PP  ")
				.append("inner join PP.posicion as POS ")
				.append("where POS.idPosicion = :idPosicion  ")
				.append("and MRSCP.monitor = true ").toString())
				.setLong("idPosicion", idPosicion).uniqueResult();
	}

	/**
	 * Se obtiene el numero de fases con fecha final y dias del ModelosRSC principal de la posicion dada
	 * @param idPosicion, el identificador del Posicion
	 * @return numero de fases  	 
	 * */
	@Override
	public Long countFaseSinDiasByPosicion(Long idPosicion) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("select count(*) ")
				.append("from ModeloRscPos as MRP ")
				.append("inner join MRP.perfilPosicion as PP  ")
				.append("inner join PP.posicion as POS ")
				.append("inner join MRP.modeloRscPosFases MRPF  ")
				.append("where POS.idPosicion =:idPosicion ")
				.append("and MRP.rol is null  ")
				.append("and MRP.monitor is null ")
				.append("and MRPF.dias is null ")
				.append("and MRPF.fechaFin is null ").toString())
				.setLong("idPosicion", idPosicion).uniqueResult();
	}

	
	/**
	 * Se obtiene el rol del modeloRscPos si coincide con el rol del idRelacionEmpresaPersona dada
	 * @param idModeloRscPos, el identificador del ModeloRscPos
	 * @param idRelacionEmpresaPersona, el identificador de la RelacionEmpresaPersona
	 * @return el rol 	 
	 * */
	@Override
	public Rol getRol(Long idModeloRscPos, Long idRelacionEmpresaPersona) {
		return (Rol) this.getSession().createQuery(
				new StringBuilder("select ROL ")
				.append("from ModeloRscPos as MRSCP ")
				.append("inner join MRSCP.rol as ROL ")
				.append("inner join ROL.relacionEmpresaPersonas REP ")
				.append("where MRSCP.idModeloRscPos =:idModeloRscPos ")
				.append("and REP.idRelacionEmpresaPersona =:idRelacionEmpresaPersona ").toString())
				.setLong("idModeloRscPos", idModeloRscPos)
				.setLong("idRelacionEmpresaPersona", idRelacionEmpresaPersona).uniqueResult();
	}

	/**
	 * Se obtiene un numero de registros monitor dependiendo del ModeloRscPos y idPersona
	 * @param idModeloRscPos, identificador del ModeloRscPos
	 * @param idPersona, identificador de la persona
	 * @param principal, true si es monitor principal, false si no es
	 * @return lista de objetos  Monitor
	 */
	@Override
	public Long getCountMonitor(Long idModeloRscPos, Long idPersona, Boolean principal) {
		return (Long)getSession().createQuery(
				new StringBuilder("SELECT count(*) ")
				.append(" from ModeloRscPos as MRSCP ")
				.append(" inner join  MRSCP.modeloRscPosFases as MRSCPF ")
				.append(" inner join  MRSCPF.monitors as MNT ")
				.append(" inner join  MNT.relacionEmpresaPersona as REP ")
				.append(" inner join  REP.persona as P ")
				.append(" where MRSCP.idModeloRscPos =:idModeloRscPos ")
				.append(" and P.idPersona =:idPersona ")
				.append(" and MNT.principal =:principal ").toString())
				.setLong("idModeloRscPos", idModeloRscPos)
				.setLong("idPersona", idPersona)
				.setBoolean("principal", principal).uniqueResult();
	}

	/**
	 * Se obtiene una lista de idModeloRscPosFase 
	 * @param idPosicion, identificador de la posicion
	 * @param monitor, nos indica si la lista es de monitor(true) o postulante(false)
	 * @param faseActiva, nos indica si la lista es de fase activa o no
	 * @return List<Long>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> get(Long idPosicion ,Boolean monitor, Boolean faseActiva) {
		sbQuery = new StringBuilder("select MRPF.idModeloRscPosFase ")
				.append("FROM ModeloRscPos as MRP ")
				.append("inner join MRP.modeloRscPosFases MRPF ")
				.append("inner join MRP.perfilPosicion as PEPOS ")
				.append("inner join PEPOS.posicion as POS ")
				.append("where POS.idPosicion = :idPosicion ")
				.append("and MRP.activo = true  ");
		
		if(monitor != null){
			sbQuery.append(" and MRP.monitor =").
				append(monitor.booleanValue());
		}
		
		if(faseActiva != null){
			sbQuery.append(" and MRPF.activo =").
				append(faseActiva.booleanValue());
		}
		
		sbQuery.append(" order by MRPF.idModeloRscPosFase ");
		
		return (List<Long>) this.getSession().createQuery(sbQuery.toString())
				.setLong("idPosicion", idPosicion).list();
		
	}

	@Override
	public ModeloRscPosFaseDto readModeloRscPosFase(Long idModeloRscPosFase) {
		return (ModeloRscPosFaseDto)getSession().createQuery(
				new StringBuilder("SELECT new net.tce.dto.ModeloRscPosFaseDto( ")
				.append("MRSCPF.idModeloRscPosFase, MRSCPF.idModeloRscPosFaseRel,MRSCPF.nombre, ")
				.append(" MRSCPF.fechaInicio, MRSCPF.fechaFin, MRSCPF.orden,MRSCPF.actividad ) ")
				.append("from ModeloRscPosFase MRSCPF ")
				.append("where MRSCPF.idModeloRscPosFase =:idModeloRscPosFase ").toString())
				.setLong("idModeloRscPosFase", idModeloRscPosFase).uniqueResult();
	}

	/**
	 * Se obtiene el idModeloRscPos correspondiente a idModeloRscPosFase
	 * @param idModeloRscPos, el identificador del ModeloRscPos
	 * @return Long
	 */
	@Transactional(readOnly=true)
	@Override
	public Long readByFase(Long idModeloRscPosFase) {
		return (Long) this.getSession().createQuery(
				new StringBuilder("SELECT MRP.idModeloRscPos ")
 				.append("from ModeloRscPos MRP ")
				.append("inner join MRP.modeloRscPosFases MRPF  ")
				.append("where MRPF.idModeloRscPosFase= :idModeloRscPosFase ").toString())
				.setLong("idModeloRscPosFase", idModeloRscPosFase).uniqueResult();
	}
	
}
