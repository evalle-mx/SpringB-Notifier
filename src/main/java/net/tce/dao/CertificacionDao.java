package net.tce.dao;

import java.util.List;

import net.tce.dto.CertificacionDto;
import net.tce.model.Certificacion;

public interface CertificacionDao extends PersistenceGenericDao<Certificacion, Object>{

	List<CertificacionDto> getByPersona(Long idPersona);
	List<CertificacionDto> getByPosicion(Long idPosicion, Long idPoliticaCertif);
}
