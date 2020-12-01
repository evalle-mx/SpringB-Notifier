package net.tce.admin.service;

import net.tce.dto.ControlProcesoDto;
import net.tce.dto.MensajeDto;
import net.tce.model.PerfilPosicion;
import net.tce.model.RelacionEmpresaPersona;

public interface AdminService {

	Object lastDateFinalSyncDocs(ControlProcesoDto  controlProceso);
	Object lastDateFinalRemodel(ControlProcesoDto controlProcesoDto);
	Object lastDateFinalReloadCoreSolr(ControlProcesoDto controlProcesoDto);
	Object lastDateFinalReclassDocs(ControlProcesoDto controlProcesoDto);
	String notificacionFatal(MensajeDto mensajeDto);
	PerfilPosicion getPerfilPosicion(String IdPosicion);
	RelacionEmpresaPersona getRelacionEmpresaPersona(String idPersona, String idEmpresaConf) throws Exception;
}
