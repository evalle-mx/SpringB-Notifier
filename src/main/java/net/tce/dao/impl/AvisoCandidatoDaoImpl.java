package net.tce.dao.impl;

import java.util.List;

import net.tce.dao.AvisoCandidatoDao;
import net.tce.dto.AvisoDto;
import net.tce.model.AvisoCandidato;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("avisoCandidatoDao")
public class AvisoCandidatoDaoImpl extends PersistenceGenericDaoImpl<AvisoCandidato, Object> 
implements AvisoCandidatoDao {
	
	

	/**
	* Obtiene una lista de objetos avisodto correspondientes al candidato
	* @param idCandidato, el id del candidato
	* @param search, 
	* @return la lista
	*/
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<AvisoDto> get(Long idCandidato, boolean search) {
		StringBuilder cad=new StringBuilder("select new net.tce.dto.AvisoDto(");
		
		if(search){
			cad.append("AVISO.idAviso, AVISO.claveAviso, AVISO_CANDIDATO.idAvisoCandidato,'true'");
		}else{
			cad.append("AVISO.texto");
		}
		
		return (List<AvisoDto>) getSession().createQuery(
				cad.append(") from Aviso as AVISO inner join AVISO.avisoCandidatos as AVISO_CANDIDATO ").
				append("WHERE  AVISO_CANDIDATO.candidato.idCandidato= :idCandidato  ").toString()).
				setLong("idCandidato", idCandidato).list();
	}
}
