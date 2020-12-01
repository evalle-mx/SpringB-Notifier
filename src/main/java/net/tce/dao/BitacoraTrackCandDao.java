package net.tce.dao;

import net.tce.model.BitacoraTrackCand;

public interface BitacoraTrackCandDao extends PersistenceGenericDao<BitacoraTrackCand, Object>{

	//int updateCandidateByPC_TEB_TMB(long idCandidato, String idBitacoraTrackCand);	
	//List<Long> get(long idPosibleCandidato,long idTipoEventoBitacora, long idTipoModuloBitacora);

	int updateCandidateByPC_TEB_TMB(long idCandidato, long idPosibleCandidato,
										long idTipoEventoBitacora, long idTipoModuloBitacora);	
}
