package net.tce.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.tce.dao.CertificacionDao;
import net.tce.dto.CertificacionDto;
import net.tce.model.Certificacion;

@Repository("certificacionDao")
public class CertificacionDaoImpl extends PersistenceGenericDaoImpl<Certificacion, Object> 
implements CertificacionDao {
	static Logger log4j = Logger.getLogger( CertificacionDaoImpl.class );

	@SuppressWarnings("unchecked")
	public List<CertificacionDto> getByPersona(Long idPersona){
		log4j.debug("idPersona " + idPersona);
		try{
			List<CertificacionDto> lsCertDto=(List<CertificacionDto>)getSession().
					createQuery(new StringBuilder().
					append("select new net.tce.dto.CertificacionDto(").
					append("CERT.idCertificacion, CERT.persona.idPersona, ").
					append("GRAD.idGradoAcademico, GRAD.descripcion, ").
					append("CERT.titulo, CERT.institucion ").
					append(") from Certificacion as CERT ").
					append(" left outer join CERT.gradoAcademico as GRAD ").
					append(" where CERT.persona.idPersona = :idPersona ").		
					toString()).
					setLong("idPersona", idPersona).
					list();
					log4j.debug("lsCertDto.size() " + lsCertDto.size());
			
			if(lsCertDto != null && lsCertDto.size() > 0){
//				log4j.debug("regresando lsCertDto " + lsCertDto);
				return lsCertDto;
			}else{
				return null;
			}
		}catch(Exception e){
			log4j.fatal("ERROR ", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CertificacionDto> getByPosicion(Long idPosicion, Long idPoliticaCertif){
		List<CertificacionDto> lsCertifDto=(List<CertificacionDto>)getSession().
				createQuery(new StringBuilder().
				
				append("select new net.tce.dto.CertificacionDto(").
				append("pmv.idPoliticaMValor, pv.posicion.idPosicion, pv.idPoliticaValor, ").
				append("pmv.gradoAcademico.idGradoAcademico, pmv.gradoAcademico.descripcion, pmv.descripcion ").
				append(") FROM PoliticaMValor pmv, PoliticaValor pv ").
				append(" WHERE pmv.politicaValor.idPoliticaValor = pv.idPoliticaValor ").
				append(" AND pv.posicion.idPosicion=:idPosicion ").
				append(" AND pv.politica.idPolitica=:idPoliticaCertif ").
				append(" ORDER BY pmv.idPoliticaMValor ")
				.toString()).
				setLong("idPosicion", idPosicion).setLong("idPoliticaCertif", idPoliticaCertif).list();
		if(lsCertifDto != null && lsCertifDto.size() > 0){
			return lsCertifDto;
		}else{
			return null;
		}
	}
}
