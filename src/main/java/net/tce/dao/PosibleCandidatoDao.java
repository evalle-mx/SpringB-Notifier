package net.tce.dao;


import java.util.List;
import net.tce.dto.PosibleCandidatoDto;
import net.tce.model.PosibleCandidato;

public interface PosibleCandidatoDao extends PersistenceGenericDao<PosibleCandidato, Object>{

	int updateTokenAdd(String tokenAdd, Long idCandidato);
	List<PosibleCandidatoDto> getAreas(long idPersona);
	int updateConfirmado(long idPosibleCandidato,boolean confirmado);
	String getPswIniSystem(long idPosibleCandidato);
	Long getIdPersona(long idPosibleCandidato);
	Long getIdPosibleCandidato(long idPersona, long idPosicion);
}
