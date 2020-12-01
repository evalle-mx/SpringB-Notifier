package net.tce.dao;

import java.util.List;
import net.tce.dto.CandidatoDto;
import net.tce.dto.ModeloRscPosFaseDto;
import net.tce.model.Candidato;

public interface CandidatoDao extends PersistenceGenericDao<Candidato, Object>{
	CandidatoDto getHandCheck(String idPosicion, String idEmpresa, String idPersona);
	Candidato getApplicantInfo(CandidatoDto candidatoDto);
	List<Candidato> getCanditatosEmpresa(Long idPersona, Long idEmpresa,Long idRol);
	List<Candidato> getCanditatosAreaPersona(Long idPersona, Long idArea);
	CandidatoDto getHandCheck(Long idCandidato);
	int updateHandShake(Long idCandidato, Long idEstatusOperativo,String textoReinvitar);
	int updateEstatusCandidato(Long idPersona, Long idEmpresa, Long estatusCandidato );
	int count(Long idPersona, Long idEmpresa);
	Long getEstatusOperativo(Long idCandidato);
	Candidato get(long idPosicion,long idPersona);
	public Short getMaxOrder(Long idPosicion);
	int updateEstatusOperativo(Long idCandidato, Long idEstatusOperativo);	
	List<CandidatoDto> get(Long idPosicion) ;
}
