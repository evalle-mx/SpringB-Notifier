package net.tce.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import net.tce.admin.service.CurriculumEmpresaService;
import net.tce.admin.service.EmpresaParametroService;
import net.tce.admin.service.SolrService;
import net.tce.dao.ApplicantDao;
import net.tce.dao.CandidatoDao;
import net.tce.dao.EmpresaDao;
import net.tce.dao.RelacionEmpresaPersonaDao;
import net.tce.dto.CandidatoDto;
import net.tce.dto.CurriculumEmpresaDto;
import net.tce.dto.EmpresaParametroDto;
import net.tce.dto.MensajeDto;
import net.tce.model.Candidato;
import net.tce.model.Empresa;
import net.tce.model.EstatusCandidato;
import net.tce.model.EstatusInscripcion;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.ParametersTCE;
import net.tce.util.UtilsTCE;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.tce.assembler.CurriculumAssembler;

/**
 * Clase donde se aplica las politicas de negocio del servicio CurriculumManagement
 * @author evalle
 *
 */
@Transactional
@Service("curriculumEmpresaService")
public class CurriculumEmpresaServiceImpl implements CurriculumEmpresaService
{
	Logger log4j = Logger.getLogger( this.getClass());
	
	@Autowired
	private EmpresaDao empresaDao;
	
	@Autowired
	private CandidatoDao candidatoDao;
	
	@Autowired
	private ApplicantDao applicantDao;
	
	@Autowired
	private RelacionEmpresaPersonaDao relacionEmpresaPersonaDao;
	
	@Autowired
	private EmpresaParametroService empresaParametroService;
	
	@Autowired
	private SolrService solrService;
	
	@Inject
	private CurriculumAssembler curriculumAssembler;
		
	/**
	 * Valida y publica un curriculum empresarial
	 * @author Osy  
	 * @param CurriculumEmpresaDto curriculumEmpresaDto Información de la empresa  
	 * @return List<VacancyDto> Exito : Mensaje Vacío o Error : 
	 * Informacion de la posición y atributos que fallaron cuya posición se publico o se intento publicar 
	 * @throws Exception 
	 */	
	@SuppressWarnings("unchecked")
	public Object setEnterpriseResumePublication(CurriculumEmpresaDto curriculumEmpresaDto) throws Exception{
		log4j.debug("<setEnterpriseResumePublication> Inicio...");
		
		//HashMap<String,String> mapRequiredAttributes = new HashMap<String, String>();
		List<CurriculumEmpresaDto> lsCurriculumEmpresaDtoOut=new ArrayList<CurriculumEmpresaDto>();
		//HashMap<String,String> mapFailedAttributes = new HashMap<String, String>();
		CandidatoDto candidatoDto = new CandidatoDto();
		Long estatusInscripcionOut ;
		
		Empresa empresa = empresaDao.read(Long.valueOf(curriculumEmpresaDto.getIdEmpresa()));
		//si existe la persona
		if(empresa != null){
			// Obtiene la lista de atributos requeridos para publicar un cv empresarial
			EmpresaParametroDto empresaParametroDto=new EmpresaParametroDto();
			empresaParametroDto.setIdEmpresaConf(curriculumEmpresaDto.getIdEmpresaConf());
			empresaParametroDto.setIdTipoParametro(Constante.TIPO_PARAMETRO_ATRIBUTO_REQUERIDO_EMPRESA);
			log4j.debug("<setEnterpriseResumePublication> Antes empresaParametroService.get...");
			Object object=empresaParametroService.get(empresaParametroDto,true);
			if(object instanceof EmpresaParametroDto ){
				log4j.error("No existen criterios en la tabla EMPRESA_PARAMETRO para publicar curriculum empresarial");
				return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR));
			}else{
				List<EmpresaParametroDto> lsEmpresaParametroDto = (List<EmpresaParametroDto>)object;
				log4j.debug("<setEnterpriseResumePublication> lsEmpresaParametroDto.size :" +  lsEmpresaParametroDto.size());
				CurriculumEmpresaDto empresaValidacionDto = curriculumAssembler.getEmpresaDto(empresa);
//				EmpresaValidacionDto empresaValidacionDto = new EmpresaValidacionDto();
//				assignEnterpriseInfo(empresa, empresaValidacionDto);
				
				lsCurriculumEmpresaDtoOut = ParametersTCE.mainResumeEmpresaDtoValidations(empresaValidacionDto, lsEmpresaParametroDto);
				
				if(lsCurriculumEmpresaDtoOut!=null && lsCurriculumEmpresaDtoOut.size()>0){
					log4j.debug("errores encontrados: "+ lsCurriculumEmpresaDtoOut.size() );
					if(empresa.getEstatusInscripcion().
							getIdEstatusInscripcion() == Constante.ESTATUS_INSCRIPCION_PUBLICADO){
						estatusInscripcionOut = (long) Constante.ESTATUS_INSCRIPCION_ACTIVO;
					}else{
						estatusInscripcionOut = empresa.getEstatusInscripcion().getIdEstatusInscripcion();
					}
				}else{
					log4j.debug("lsCurriculumEmpresaDtoOut es null" + lsCurriculumEmpresaDtoOut );
					estatusInscripcionOut = (long) Constante.ESTATUS_INSCRIPCION_PUBLICADO;
				}
				
				EstatusInscripcion estatusInscripcion = new EstatusInscripcion();
				empresa.setEstatusInscripcion(estatusInscripcion);
				empresa.getEstatusInscripcion().setIdEstatusInscripcion(estatusInscripcionOut);
				empresa.setFechaModificacion(DateUtily.getToday());	
				empresa.setClasificado(false);
				empresaDao.update(empresa);
				
				// Si la empresa es actualmente un candidato, se registra el estatus correspondiente, es decir
				// estatus_candidato = 8 (Por calcular) y bandera de modificado a true
				log4j.debug("<setEnterpriseResumePublication> Antes getApplicantInfo");
				candidatoDto.setIdEmpresa(Long.valueOf(curriculumEmpresaDto.getIdEmpresa()));
				Candidato candidato = candidatoDao.getApplicantInfo(candidatoDto);
				log4j.debug("<setEnterpriseResumePublication> Despues getApplicantInfo");
				if(candidato != null){
					log4j.debug("<setEnterpriseResumePublication> candidatoDto.getIdCandidato :" + candidato.getIdCandidato());
					EstatusCandidato estatusCandidato = new EstatusCandidato();
					estatusCandidato.setIdEstatusCandidato(Constante.ESTATUS_CANDIDATO_POR_CALCULAR);
					candidato.setEstatusCandidato(estatusCandidato);	
					candidato.setFechaModificacion(DateUtily.getToday());
					candidato.setModificado(true);
					applicantDao.update(candidato);
				}else{
					log4j.debug("<setEnterpriseResumePublication> No hay información de candidato para esta empresa");				
				}
				
				if(estatusInscripcionOut == Long.valueOf(Constante.ESTATUS_INSCRIPCION_PUBLICADO)){
					//Escribir/actualizar documento en solr
					solrService.threadwriteInSolr(empresa); //writeInSolrPublication(empresa);
					//*/				
				}
			}
		}else{
			return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
					Mensaje.SERVICE_CODE_002,Mensaje.SERVICE_TYPE_FATAL,
					Mensaje.MSG_ERROR));
		}
		
		log4j.debug("<setEnterpriseResumePublication> Fin...");
		return lsCurriculumEmpresaDtoOut;
	}
	
	
	

}
