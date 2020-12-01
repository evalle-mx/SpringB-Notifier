package net.tce.dao.impl;


import org.springframework.stereotype.Repository;
import net.tce.dao.BitacoraTrackCandDao;
import net.tce.model.BitacoraTrackCand;

@Repository("bitacoraTrackCandDao")
public class BitacoraTrackCandDaoImpl extends PersistenceGenericDaoImpl<BitacoraTrackCand, Object> 
implements BitacoraTrackCandDao {

	
	/*@Override
	public int updateCandidateByPC_TEB_TMB(long idCandidato, String idBitacoraTrackCand) {
		return  getSession().createQuery(
				new StringBuilder("UPDATE BitacoraTrackCand SET idCandidato= :idCandidato ").
				append(" where idBitacoraTrackCand in (").append(idBitacoraTrackCand).
				append(")").toString()).
				setLong("idCandidato", idCandidato).
				executeUpdate();  
	}*/

	/*@SuppressWarnings("unchecked")
	@Override
	public List<Long> get(long idPosibleCandidato, long idTipoEventoBitacora, long idTipoModuloBitacora) {
		return (List<Long>) this.getSession().createQuery(
				new StringBuilder("SELECT BTC.idBitacoraTrackCand  ").
				append("FROM BitacoraTrack as BT  ").
				append("inner join BT.bitacoraTrackCand as BTC ").
				append("inner join BT.tipoEventoBitacora as TEB ").
				append("inner join BT.tipoModuloBitacora as TMB ").
				append("WHERE TEB.idTipoEventoBitacora = :idTipoEventoBitacora  ").
				append("and TMB.idTipoModuloBitacora = :idTipoModuloBitacora ").
				append("and BTC.idPosibleCandidato = :idPosibleCandidato ").toString()).
				setLong("idPosibleCandidato", idPosibleCandidato).
				setLong("idTipoEventoBitacora", idTipoEventoBitacora).
				setLong("idTipoModuloBitacora", idTipoModuloBitacora).list();
	}*/

	@Override
	public int updateCandidateByPC_TEB_TMB(long idCandidato, long idPosibleCandidato, 
											long idTipoEventoBitacora,long idTipoModuloBitacora) {
		return  getSession().createQuery(
				new StringBuilder("UPDATE BitacoraTrackCand SET idCandidato= :idCandidato ").
				append(" where idBitacoraTrackCand in (").
					append("SELECT BTC.idBitacoraTrackCand ").
					append("FROM BitacoraTrack as BT  ").
					append("inner join BT.bitacoraTrackCand as BTC ").
					append("inner join BT.tipoEventoBitacora as TEB ").
					append("inner join BT.tipoModuloBitacora as TMB ").
					append("WHERE TEB.idTipoEventoBitacora = :idTipoEventoBitacora  ").
					append("and TMB.idTipoModuloBitacora = :idTipoModuloBitacora ").
					append("and BTC.idPosibleCandidato = :idPosibleCandidato ").
				append(")").toString()).
				setLong("idCandidato", idCandidato).
				setLong("idPosibleCandidato", idPosibleCandidato).
				setLong("idTipoEventoBitacora", idTipoEventoBitacora).
				setLong("idTipoModuloBitacora", idTipoModuloBitacora).
				executeUpdate();  
	}
	
	

}
