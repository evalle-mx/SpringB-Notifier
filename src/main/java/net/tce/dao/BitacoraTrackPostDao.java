package net.tce.dao;


import net.tce.model.BitacoraTrackPost;

public interface BitacoraTrackPostDao extends PersistenceGenericDao<BitacoraTrackPost, Object>{
	//int updateCandidateByPC_TEB_TMB(long idCandidato, String idBitacoraTrackPost);	
	//List<Long> get(long idPosibleCandidato,long idTipoEventoBitacora, long idTipoModuloBitacora);
	int updateCandidateByPC_TEB_TMB(long idCandidato, long idPosibleCandidato,
								long idTipoEventoBitacora, long idTipoModuloBitacora);
}
