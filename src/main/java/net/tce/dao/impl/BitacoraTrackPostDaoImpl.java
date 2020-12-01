package net.tce.dao.impl;

import org.springframework.stereotype.Repository;
import net.tce.dao.BitacoraTrackPostDao;
import net.tce.model.BitacoraTrackPost;

@Repository("bitacoraTrackPostDao")
public class BitacoraTrackPostDaoImpl extends PersistenceGenericDaoImpl<BitacoraTrackPost, Object> 
implements BitacoraTrackPostDao {

	@Override
	public int updateCandidateByPC_TEB_TMB(long idCandidato, long idPosibleCandidato, 
											long idTipoEventoBitacora, long idTipoModuloBitacora) {
		return  getSession().createQuery(
				new StringBuilder("UPDATE BitacoraTrackPost SET idCandidato= :idCandidato ").
				append(" where idBitacoraTrackPost in (").
					append("SELECT BTP.idBitacoraTrackPost ").
					append("FROM BitacoraTrack as BT ").
					append("inner join BT.bitacoraTrackPost as BTP ").
					append("inner join BT.tipoEventoBitacora as TEB ").
					append("inner join BT.tipoModuloBitacora as TMB ").
					append("WHERE TEB.idTipoEventoBitacora = :idTipoEventoBitacora ").
					append("and TMB.idTipoModuloBitacora = :idTipoModuloBitacora ").
					append("and BTP.idPosibleCandidato = :idPosibleCandidato ").
				append(")").toString()).
				setLong("idCandidato", idCandidato).
				setLong("idPosibleCandidato", idPosibleCandidato).
				setLong("idTipoEventoBitacora", idTipoEventoBitacora).
				setLong("idTipoModuloBitacora", idTipoModuloBitacora).
				executeUpdate();  
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<Long> get(long idPosibleCandidato, long idTipoEventoBitacora, long idTipoModuloBitacora) {
		return (List<Long>) this.getSession().createQuery(
				new StringBuilder("SELECT BTP.idBitacoraTrackPost  ").
				append("FROM BitacoraTrack as BT  ").
				append("inner join BT.bitacoraTrackPost as BTP ").
				append("inner join BT.tipoEventoBitacora as TEB ").
				append("inner join BT.tipoModuloBitacora as TMB ").
				append("WHERE TEB.idTipoEventoBitacora = :idTipoEventoBitacora  ").
				append("and TMB.idTipoModuloBitacora = :idTipoModuloBitacora ").
				append("and BTP.idPosibleCandidato = :idPosibleCandidato )").toString()).
				setLong("idPosibleCandidato", idPosibleCandidato).
				setLong("idTipoEventoBitacora", idTipoEventoBitacora).
				setLong("idTipoModuloBitacora", idTipoModuloBitacora).list();
	}*/
}
