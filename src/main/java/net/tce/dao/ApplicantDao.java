package net.tce.dao;

import java.util.List;
import net.tce.model.Candidato;
import net.tce.dto.CandidatoDto;
import net.tce.dto.PerfilDto;

public interface ApplicantDao extends PersistenceGenericDao<Candidato, Object>{
	//CandidatoDto getAdjacency(Long idMunicipio1,Long idMunicipio2);		
	List<CandidatoDto> getPeople(long idPosicion, long id_empresa);
	List<CandidatoDto> getPeopleCandidate(long idPosicion, long idEmpresa);
	List<CandidatoDto> getEnterprise(long idPosicion);
	CandidatoDto getPositionInfo(long idPosicion);
	//List<CandidatoDto> getMaxSchooling(long idPersona);
	List<PerfilDto> getProfilesPeople(long idPosicion, long idPersona);
	List<PerfilDto> getProfilesEnterprise(long idPosicion, long idEmpresa);
	List<CandidatoDto> getCanditatosPersonas(Long idPosicion);
	List<CandidatoDto> getCanditatosEmpresa(Long idPosicion);
}