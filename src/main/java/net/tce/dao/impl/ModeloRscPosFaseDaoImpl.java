package net.tce.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import net.tce.dao.ModeloRscPosFaseDao;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.model.ModeloRscPosFase;

@Repository("modeloRscPosFaseDao")
public class ModeloRscPosFaseDaoImpl extends PersistenceGenericDaoImpl<ModeloRscPosFase, Object> 
implements ModeloRscPosFaseDao {

	/**
	 * Se borran los registros en la tabla ModeloRscPosFase,dado el idModeloRscPos
	 * @param idModeloRscPos, el identificador del ModeloRscPos
	 */
	@Override
	public void deleteByidModeloRscPos(Long idModeloRscPos) {
		log4j.debug("idModeloRscPos: "+ idModeloRscPos );
		this.getSession().createQuery(new StringBuilder("DELETE FROM ModeloRscPosFase ").
				append(" WHERE  modeloRscPosFase.idModeloRscPos = :idModeloRscPos ").toString()).
				setLong("idModeloRscPos",(Long)idModeloRscPos).executeUpdate();
	}
	
	/**
	 * Se obtiene una lista de objetos  ModeloRscPosFase dada la posicion
	 * @param idPosicion, identificador de la posicion
	 * @return lista de objetos  ModeloRscPosFase
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeloRscPosFase> getMonitoresByPosicion(Long idPosicion) {
		return (List<ModeloRscPosFase>)getSession().createQuery(
				new StringBuilder("SELECT MRPF ")
				.append("from ModeloRscPosFase as MRPF ")
				.append("inner join MRPF.modeloRscPos as MRSCP  ")
				.append("inner join MRSCP.perfilPosicion as PP  ")
				.append("inner join PP.posicion as POS ")
				.append("where POS.idPosicion =:idPosicion ")
				.append("and MRPF.activo = true ")
				.append("and MRSCP.monitor= true ")
				.append("order by MRSCP.idModeloRscPos, MRPF.orden, MRPF.actividad ").toString())
				.setLong("idPosicion", idPosicion).list();
	}

	/**
	 * Se obtiene una lista de objetos  ModeloRscPosDto
	 * @param idsModeloRscPosFase, es un string con idsModeloRscPosFase concatenados
	 * @return List<ModeloRscPosDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeloRscPosFaseDto> getModeloRscPosFase(String idsModeloRscPosFase) {
		return (List<ModeloRscPosFaseDto>)getSession().createQuery(
				new StringBuilder("SELECT new net.tce.dto.ModeloRscPosFaseDto( ")
				.append("count(*),MRPF1.idModeloRscPosFase )")
				.append("FROM ModeloRscPos as MRP ")
				.append("inner join MRP.modeloRscPosFases as MRPF ")
				.append("inner join MRPF.modeloRscPosFase as MRPF1 ")
				.append("where  MRPF1.idModeloRscPosFase in(").append(idsModeloRscPosFase)
				.append(") and MRP.monitor = true ")
				.append("and MRP.activo = true ")
				.append("and MRPF.activo = true ")
				.append("group by MRPF1.idModeloRscPosFase ")
				.append("order by MRPF1.idModeloRscPosFase ").toString()).list();
	}

	/**
	 * Se obtiene un objeto ModeloRscPosFase dado el idMonitor
	 * @param idMonitor, el identificador del Monitor
	 * @return ModeloRscPosFase
	 */
	@Override
	public ModeloRscPosFase readByMonitor(Long idMonitor) {
		return (ModeloRscPosFase) getSession().createQuery(
				new StringBuilder("select MRSCF ")
				.append("FROM Monitor as MON ")
				.append("inner join MON.modeloRscPosFase as MRSCF ")
				.append("where MON.idMonitor =:idMonitor ").toString()).
				setLong("idMonitor", idMonitor).uniqueResult();
	}

	/**
	 * Se obtiene un objeto ModeloRscPosFase dado los parametros
	 * @param idPosicion, el identificador de la posicion
	 * @param orden, es el numero de  orden de la fase
	 * @param actividad, es el número de actividad de la fase
	 * @param esMonitor, si es true el ModeloRscPos es Monitor
	 * 					 si es false el ModeloRscPos es candidato
	 * @return ModeloRscPosFase
	 */
	@Override
	public ModeloRscPosFase readByPosOrdAct(Long idPosicion, Short orden, Short actividad, boolean esMonitor) {
		return (ModeloRscPosFase) getSession().createQuery(
				new StringBuilder("select MRSCPF ")
				.append("from ModeloRscPos as MRSCP  ")
				.append("inner join MRSCP.modeloRscPosFases as MRSCPF ")
				.append("inner join MRSCP.perfilPosicion as PP ")
				.append("inner join PP.posicion as POS ")
				.append("where  POS.idPosicion = :idPosicion ")
				.append("and MRSCP.monitor = :esMonitor ")
				.append("and MRSCPF.orden = :orden ")
				.append("and MRSCPF.actividad = :actividad ").toString()).
				setLong("idPosicion", idPosicion).
				setBoolean("esMonitor", esMonitor).
				setShort("actividad", actividad).
				setShort("orden", orden).uniqueResult();
	}

	/**
	 * Se obtiene una lista de objetos  ModeloRscPosFase dado el ModeloRscPos
	 * @param idModeloRscPos, el identificador de la ModeloRscPos
	 * @return List<ModeloRscPosDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ModeloRscPosFase> getByIdModeloRscPos(Long idModeloRscPos) {
		return (List<ModeloRscPosFase>)getSession().createQuery(
				new StringBuilder("SELECT MRSCPF ")
				.append("from ModeloRscPos as MRSCP  ")
				.append("inner join MRSCP.modeloRscPosFases as MRSCPF ")
				.append("inner join MRSCPF.monitors MON ")
				.append("where  MRSCP.monitor=true ")
				.append("and MRSCPF.activo=true ")
				.append("and MRSCP.idModeloRscPos = :idModeloRscPos ")
				.append("order by MRSCPF.orden, MRSCPF.actividad").toString())
				.setLong("idModeloRscPos", idModeloRscPos).list();
	}
}
