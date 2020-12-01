package net.tce.assembler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import net.tce.dao.EmpresaDao;
import net.tce.dto.LocationInfoDto;
import net.tce.dto.PerfilTextoNgramDto;
import net.tce.dto.PoliticaEscolaridadDto;
import net.tce.dto.VacancyDto;
import net.tce.dto.VacancyPerfilPosicionDto;
import net.tce.model.AreaPerfil;
import net.tce.model.Domicilio;
import net.tce.model.Empresa;
import net.tce.model.EstadoCivil;
import net.tce.model.EstatusEscolar;
import net.tce.model.GradoAcademico;
import net.tce.model.Mes;
import net.tce.model.PerfilPosicion;
import net.tce.model.PerfilTextoNgram;
import net.tce.model.PeriodicidadPago;
import net.tce.model.PoliticaMValor;
import net.tce.model.PoliticaValor;
import net.tce.model.Posicion;
import net.tce.model.EstatusPosicion;
import net.tce.model.AmbitoGeografico;
import net.tce.model.NivelJerarquico;
import net.tce.model.TipoContrato;
import net.tce.model.TipoGenero;
import net.tce.model.TipoJornada;
import net.tce.model.TipoPrestacion;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;

/**
 * Contiene convertidores propios del proceso de Vacancy/Posicion
 * @author tce
 *
 */
public class VacancyAssembler extends CommonAssembler {
	
	@Autowired
	private EmpresaDao empresaDao;
	
	@Inject
	private ConversionService converter;
	
	Logger log4j = Logger.getLogger( this.getClass());
	
	/**
	 * Obtiene un vacancyDto en base a un JSon, o en su defecto uno nuevo con mensaje de error
	 * @param json
	 * @return
	 */
	public VacancyDto getVacancyDtoFromJson(String json){
		log4j.debug("<getVacancyDtoFromJson> Json " + json );
		VacancyDto vacancyDto = null;
		try{
			vacancyDto = gson.fromJson(json, VacancyDto.class);
		}catch(Exception e){
			log4j.error("Excepción de Gson: ", e);
			vacancyDto = new VacancyDto();
			vacancyDto.setCode(Mensaje.SERVICE_CODE_003);
			vacancyDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			vacancyDto.setMessage(Mensaje.MSG_ERROR);
		}
		
		return vacancyDto;
	}
	
	/**
	 * Carga los valores de una posicion y relaciones en un objeto DTO
	 * <br><i>replíca el procedimiento de Vacancy.Read</i>
	 * @param posicion
	 * @return
	 * @throws Exception
	 */
	public VacancyDto getVacancyDto(Posicion posicion) throws Exception {
		
		VacancyDto vacancyDto = null;
		
		if(posicion!=null){
			String idPerfilInterno = null;		
			log4j.info("<getVacancyDto> Antes converter posicion.getDomicilios().size() :" + posicion.getDomicilios().size());
			
			vacancyDto=converter.convert(posicion, VacancyDto.class);
			
			log4j.info("<getVacancyDto> Despues converter");
			if (posicion.getDomicilios().size() > 0){
				log4j.info("<getVacancyDto> getDomicilios="+posicion.getDomicilios().size());
				List<LocationInfoDto> lsLocationDto = new ArrayList<LocationInfoDto>();
				Iterator<Domicilio> itDomicilio = posicion.getDomicilios().iterator();
				while(itDomicilio.hasNext()){
					lsLocationDto.add(converter.convert(itDomicilio.next(), LocationInfoDto.class));
				}
				vacancyDto.setLocalizacion(lsLocationDto);
			}
			
//			log4j.debug("posicion.getPerfilPosicions().size() " + posicion.getPerfilPosicions().size() );
			/* Separa el detalle de perfiles internos y los que no lo son */
			if (posicion.getPerfilPosicions().size() > 0){
				log4j.info("<getVacancyDto> Existen getPerfilPosicions :" + posicion.getPerfilPosicions().size());
				List<VacancyPerfilPosicionDto> lsPerfilInternosPosicionDto = new ArrayList<VacancyPerfilPosicionDto>();
				List<VacancyPerfilPosicionDto> lsPerfilOtrosPosicionDto = new ArrayList<VacancyPerfilPosicionDto>();
				idPerfilInterno = null;
				Set<AreaPerfil> areaPerfils;
				
				
				Iterator<PerfilPosicion> itPerfilPosition = posicion.getPerfilPosicions().iterator();
				while(itPerfilPosition.hasNext()){
					log4j.info("<getVacancyDto> iterando perfilPosicions");
					PerfilPosicion perfilPosicion = itPerfilPosition.next();
					
					areaPerfils=perfilPosicion.getPerfil().getAreaPerfils();
					
					log4j.info("<getVacancyDto> areaPerfils="+areaPerfils);

					//Se obtiene el area
					if(perfilPosicion.getPerfil().getAreaPerfils() != null){
						
						//Solo cuando hay un perfil
						if(areaPerfils.size() == 1){
							Iterator<AreaPerfil> itAreaPerfil=areaPerfils.iterator();
							if(itAreaPerfil.hasNext()){
								vacancyDto.setIdArea(String.valueOf(itAreaPerfil.next().getArea().getIdArea()));
								log4j.info("<getVacancyDto> getIdArea="+vacancyDto.getIdArea());
							}
						}						
					}
					
					VacancyPerfilPosicionDto perfilInternoPosicionDto = new VacancyPerfilPosicionDto();
					VacancyPerfilPosicionDto perfilOtroPosicionDto = new VacancyPerfilPosicionDto();

					if(perfilPosicion.getPerfil().getTipoPerfil().getIdTipoPerfil() == Constante.TIPO_PERFIL_INTERNO){
						log4j.info("<getVacancyDto> Conformando perfilInternoPosicions");
						log4j.info("<getVacancyDto> Antes convert perfilPosicion");
						perfilInternoPosicionDto = converter.convert(perfilPosicion, VacancyPerfilPosicionDto.class);
						log4j.info("<getVacancyDto> Despues convert perfilPosicion");
						idPerfilInterno = perfilInternoPosicionDto.getIdPerfil();
						lsPerfilInternosPosicionDto.add(perfilInternoPosicionDto);
					}else{
						log4j.info("<getVacancyDto> Conformando perfilOtrosPosicions");
						log4j.info("<getVacancyDto> Antes convert perfilPosicion");
						perfilOtroPosicionDto = converter.convert(perfilPosicion, VacancyPerfilPosicionDto.class);
						log4j.info("<getVacancyDto> Despues convert perfilPosicion");
						lsPerfilOtrosPosicionDto.add(perfilOtroPosicionDto);
					}
				}
				vacancyDto.setPerfiles(lsPerfilInternosPosicionDto);
				vacancyDto.setPerfilExterno(new HashSet<VacancyPerfilPosicionDto>(lsPerfilOtrosPosicionDto));
			}
			
			/* Políticas de negocio de la posición */
			if (posicion.getPoliticaValors().size() > 0){
				log4j.info("<getVacancyDto> Existen politicaValors :" + posicion.getPoliticaValors().size());
				Iterator<PoliticaValor> itPoliticaValor = posicion.getPoliticaValors().iterator();
				while(itPoliticaValor.hasNext()){
					PoliticaValor politicaValor = itPoliticaValor.next();
					log4j.info("<getVacancyDto> ClavePolitica :" + politicaValor.getPolitica().getClavePolitica());

					/* POLITICA_VALOR_EXP_LABORAL_KO */
					if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_EXP_LABORAL_KO)){
						log4j.info("<getVacancyDto> Asignando " + Constante.POLITICA_VALOR_EXP_LABORAL_KO + "...");
						vacancyDto.setExperienciaLaboralMinima(politicaValor.getValor());
					}
					// POLITICA_VALOR_FORM_ACADEMICA_KO_MI
					// POLITICA_VALOR_FORM_ACADEMICA_KO_MAX
					if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MIN) ||
						politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_FORM_ACADEMICA_KO_MAX)){
						log4j.info("<getVacancyDto> Asignando " + politicaValor.getPolitica().getClavePolitica());
						
						if(politicaValor.getPoliticaMValors().size() > 0){
							PoliticaMValor politicaMValor=politicaValor.getPoliticaMValors().iterator().next();
							if(politicaMValor.getGradoAcademico() != null){
								vacancyDto.setIdGradoAcademicoMin(String.valueOf(politicaMValor.getGradoAcademico().getIdGradoAcademico()));
							}
							if(politicaMValor.getEstatusEscolar() != null){
								vacancyDto.setIdEstatusEscolarMin(String.valueOf(politicaMValor.getEstatusEscolar().getIdEstatusEscolar()));										
							}
						}
					}
					
					
					/* POLITICA_VALOR_RANGO_EDAD_KO */
					if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_RANGO_EDAD_KO)){
						log4j.info("<getVacancyDto> Asignando " + Constante.POLITICA_VALOR_RANGO_EDAD_KO + "...");
						vacancyDto.setEdadMinima(politicaValor.getValorMinRango1());
						vacancyDto.setEdadMaxima(politicaValor.getValorMaxRango1());
					}
					/* POLITICA_VALOR_SEXO_KO */
					if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_SEXO_KO)){
						log4j.info("<getVacancyDto> Asignando " + Constante.POLITICA_VALOR_SEXO_KO + "...");
						vacancyDto.setIdTipoGeneroRequerido(politicaValor.getValor());
					}
					/* POLITICA_VALOR_EDO_CIVIL_KO */
					if(politicaValor.getPolitica().getClavePolitica().equals(Constante.POLITICA_VALOR_EDO_CIVIL_KO)){
						log4j.info("<getVacancyDto> Asignando " + Constante.POLITICA_VALOR_EDO_CIVIL_KO + "...");
						if(politicaValor.getPoliticaMValors().size() > 0){
							PoliticaMValor politicaMValor=politicaValor.getPoliticaMValors().iterator().next();
							if(politicaMValor.getEstadoCivil() != null){
								vacancyDto.setIdEstadoCivilRequerido(String.valueOf(politicaMValor.getEstadoCivil().getIdEstadoCivil()));
							}
						}
						
					}
				}
			}
			
			log4j.info("<getVacancyDto> Asignando perfil interno :" + idPerfilInterno);
			vacancyDto.setIdPerfil(idPerfilInterno);
		}
		return vacancyDto;
	}
	
	

	/**
	 * Hace match del objeto vacancyDto al objeto posicion
	 * @param perfilTextoNgram
	 * @param perfilTextoNgramDto
	 * @return el nuevo objeto
	 * @throws ParseException 
	 */
	public PerfilTextoNgram getPerfilTextoNgram(PerfilTextoNgram perfilTextoNgram, PerfilTextoNgramDto perfilTextoNgramDto, 
												boolean seModificoAlgo) throws Exception{		
		seModificoAlgo = false;
		
		if(perfilTextoNgramDto.getTexto() != null && 
		   !perfilTextoNgramDto.getTexto().equals(perfilTextoNgram.getTexto())){
				perfilTextoNgram.setTexto(perfilTextoNgramDto.getTexto());
				seModificoAlgo=true;
		}

		if(perfilTextoNgramDto.getPonderacion() != null && 
		   !perfilTextoNgramDto.getPonderacion().equals(perfilTextoNgram.getPonderacion())){
				perfilTextoNgram.setPonderacion(Short.valueOf(perfilTextoNgramDto.getPonderacion()));
				seModificoAlgo=true;
		}
		return perfilTextoNgram;
	}


	/**
	 * Hace match del objeto vacancyDto al objeto PoliticaValor
	 * @param PoliticaValor
	 * @param VacancyDto
	 * @return PoliticaValor 
	 * @throws ParseException 
	 */
	public PoliticaValor getPoliticaValor(PoliticaValor politicaValor, VacancyDto vacancyDto, boolean seModificoAlgo, String policy) throws Exception{		
		seModificoAlgo = false;
		
		if(policy.equals(Constante.POLITICA_VALOR_EXP_LABORAL_KO)){
			/* ExperienciaLaboralMinima */
			if(vacancyDto.getExperienciaLaboralMinima() != null && 
					!vacancyDto.getExperienciaLaboralMinima().equals(politicaValor.getValor())){
				politicaValor.setValor(vacancyDto.getExperienciaLaboralMinima());
				seModificoAlgo=true;
			}
		}
		if(policy.equals(Constante.POLITICA_VALOR_RANGO_EDAD_KO)){
			/* EdadMinima */
			if(vacancyDto.getEdadMinima() != null && 
					!vacancyDto.getEdadMinima().equals(politicaValor.getValorMinRango1())){
				politicaValor.setValorMinRango1(vacancyDto.getEdadMinima());
				seModificoAlgo=true;
			}
	
			/* EdadMaxima */
			if(vacancyDto.getEdadMaxima() != null && 
					!vacancyDto.getEdadMaxima().equals(politicaValor.getValorMaxRango1())){
				politicaValor.setValorMaxRango1(vacancyDto.getEdadMaxima());
				seModificoAlgo=true;
			}
		}
		
		//Si se modifico algo se persiste nueva fecha
		if(seModificoAlgo)
			politicaValor.setFechaModificacion(DateUtily.getToday());

		log4j.info("<getPoliticaValor> politicaValor.getIdPoliticaValor :" + politicaValor.getIdPoliticaValor());
		log4j.info("<getPoliticaValor> politicaValor.getIdPolitica :" + politicaValor.getPolitica().getIdPolitica());
		log4j.info("<getPoliticaValor> politicaValor.getFechaCreacion :" + politicaValor.getFechaCreacion());
		log4j.info("<getPoliticaValor> politicaValor.getValorMinRango1 :" + politicaValor.getValorMinRango1());
		log4j.info("<getPoliticaValor> politicaValor.getValorMaxRango1 :" + politicaValor.getValorMaxRango1());
		return politicaValor;
	}

	/**
	 * Hace match del objeto vacancyDto al objeto PoliticaEscolaridad
	 * @param PoliticaEscolaridad
	 * @param VacancyDto
	 * @return PoliticaEscolaridad
	 * @throws ParseException 
	 */
	/*public PoliticaEscolaridad getPoliticaEscolaridad(PoliticaEscolaridad politicaEscolaridad, VacancyDto vacancyDto, boolean seModificoAlgo, String modo) throws Exception{		
		seModificoAlgo = false;
		
		if (modo.equals("MIN")){
			//idGradoAcademicoMin 
			if(vacancyDto.getIdGradoAcademicoMin() != null){
				if(politicaEscolaridad.getGradoAcademico() == null ||
				   (politicaEscolaridad.getGradoAcademico() != null && 
				   !vacancyDto.getIdGradoAcademicoMin().equals(String.valueOf(
				    politicaEscolaridad.getGradoAcademico().getIdGradoAcademico())))){
						politicaEscolaridad.setGradoAcademico(new GradoAcademico());
						politicaEscolaridad.getGradoAcademico().setIdGradoAcademico(
																Long.valueOf(vacancyDto.getIdGradoAcademicoMin())
																);
							seModificoAlgo=true;
				}
			}		
			//idEstatusEscolarMin 
			if(vacancyDto.getIdEstatusEscolarMin() != null){
				if(politicaEscolaridad.getEstatusEscolar() == null ||
				   (politicaEscolaridad.getEstatusEscolar() != null && 
				   !vacancyDto.getIdEstatusEscolarMin().equals(String.valueOf(
				    politicaEscolaridad.getEstatusEscolar().getIdEstatusEscolar())))){
						politicaEscolaridad.setEstatusEscolar(new EstatusEscolar());
						politicaEscolaridad.getEstatusEscolar().setIdEstatusEscolar(
																Long.valueOf(vacancyDto.getIdEstatusEscolarMin())
																);
							seModificoAlgo=true;
				}
			}
		}
		if (modo.equals("MAX")){
			// idGradoAcademicoMax 
			if(vacancyDto.getIdGradoAcademicoMax() != null){
				if(politicaEscolaridad.getGradoAcademico() == null ||
				   (politicaEscolaridad.getGradoAcademico() != null && 
				   !vacancyDto.getIdGradoAcademicoMax().equals(String.valueOf(
				    politicaEscolaridad.getGradoAcademico().getIdGradoAcademico())))){
						politicaEscolaridad.setGradoAcademico(new GradoAcademico());
						politicaEscolaridad.getGradoAcademico().setIdGradoAcademico(
																Long.valueOf(vacancyDto.getIdGradoAcademicoMax())
																);
							seModificoAlgo=true;
				}
			}		
			// idEstatusEscolarMax
			if(vacancyDto.getIdEstatusEscolarMax() != null){
				if(politicaEscolaridad.getEstatusEscolar() == null ||
				   (politicaEscolaridad.getEstatusEscolar() != null && 
				   !vacancyDto.getIdEstatusEscolarMax().equals(String.valueOf(
				    politicaEscolaridad.getEstatusEscolar().getIdEstatusEscolar())))){
						politicaEscolaridad.setEstatusEscolar(new EstatusEscolar());
						politicaEscolaridad.getEstatusEscolar().setIdEstatusEscolar(
																Long.valueOf(vacancyDto.getIdEstatusEscolarMax())
																);
							seModificoAlgo=true;
				}
			}		
		}
		//Si se modifico algo se persiste nueva fecha
		if(seModificoAlgo)
			politicaEscolaridad.getPoliticaValor().setFechaModificacion(DateUtily.getToday());

		log4j.info("<getPoliticaEscolaridad> politicaEscolaridad.politicaEscolaridad :" + politicaEscolaridad.getIdPoliticaEscolaridad());
		log4j.info("<getPoliticaEscolaridad> politicaEscolaridad.getIdGradoAcademico :" + politicaEscolaridad.getGradoAcademico().getIdGradoAcademico());
		log4j.info("<getPoliticaEscolaridad> politicaEscolaridad.getIdEstatusEscolar :" + politicaEscolaridad.getEstatusEscolar().getIdEstatusEscolar());
		
		return politicaEscolaridad;
	}*/
	
	/**
	 * Hace match del objeto vacancyDto al objeto PoliticaEscolaridad
	 * @param PoliticaEscolaridad
	 * @param VacancyDto
	 * @return PoliticaEscolaridad
	 * @throws ParseException 
	 */
	/*public PoliticaMGenero getPoliticaMGenero(PoliticaMGenero politicaMGenero, VacancyDto vacancyDto, boolean seModificoAlgo) throws Exception{		
		seModificoAlgo = false;

		// idGradoAcademicoMin 
		if(vacancyDto.getIdTipoGeneroRequerido() != null){
			if(politicaMGenero.getTipoGenero() == null ||
			   (politicaMGenero.getTipoGenero() != null && 
			   !vacancyDto.getIdTipoGeneroRequerido().equals(String.valueOf(
					politicaMGenero.getTipoGenero().getIdTipoGenero())))){
					politicaMGenero.setTipoGenero(new TipoGenero());
					politicaMGenero.getTipoGenero().setIdTipoGenero(
															Long.valueOf(vacancyDto.getIdTipoGeneroRequerido())
															);
						seModificoAlgo=true;
			}
		}				

		//Si se modifico algo se persiste nueva fecha
		if(seModificoAlgo)
			politicaMGenero.getPoliticaValor().setFechaModificacion(DateUtily.getToday());
		
		return politicaMGenero;
	}*/

	/**
	 * Hace match del objeto vacancyDto al objeto PoliticaMEstadoCivil
	 * @param PoliticaMEstadoCivil
	 * @param VacancyDto
	 * @return PoliticaMEstadoCivil
	 * @throws ParseException 
	 */
/*	public PoliticaMEstadoCivil getPoliticaMEstadoCivil(PoliticaMEstadoCivil politicaMEstadoCivil, VacancyDto vacancyDto, boolean seModificoAlgo) throws Exception{		
		seModificoAlgo = false;

		// idEstadoCivilRequerido 
		if(vacancyDto.getIdEstadoCivilRequerido() != null){
			if(politicaMEstadoCivil.getEstadoCivil() == null ||
			   (politicaMEstadoCivil.getEstadoCivil() != null && 
			   !vacancyDto.getIdEstadoCivilRequerido().equals(String.valueOf(
					politicaMEstadoCivil.getEstadoCivil().getIdEstadoCivil())))){
					politicaMEstadoCivil.setEstadoCivil(new EstadoCivil());
					politicaMEstadoCivil.getEstadoCivil().setIdEstadoCivil(
															Long.valueOf(vacancyDto.getIdEstadoCivilRequerido())
															);
						seModificoAlgo=true;
			}
		}				

		//Si se modifico algo se persiste nueva fecha
		if(seModificoAlgo)
			politicaMEstadoCivil.getPoliticaValor().setFechaModificacion(DateUtily.getToday());
		
		return politicaMEstadoCivil;
	}*/

	/**
	 * Hace match del objeto PoliticaEscolaridadDto al objeto PoliticaEscolaridad
	 * @param PoliticaEscolaridad
	 * @param PoliticaEscolaridadDto
	 * @return el nuevo objeto
	 * @throws ParseException 
	 */
	/*public PoliticaEscolaridad getPoliticaEscolaridad(PoliticaEscolaridad politicaEscolaridad, PoliticaEscolaridadDto politicaEscolaridadDto, boolean seModificoAlgo) throws Exception{		
		seModificoAlgo = false;

		// idGradoAcademico 
		if(politicaEscolaridadDto.getIdGradoAcademico() != null){
			if(politicaEscolaridad.getGradoAcademico() == null ||
			   (politicaEscolaridad.getGradoAcademico() != null && 
			   !politicaEscolaridadDto.getIdGradoAcademico().equals(String.valueOf(
			    politicaEscolaridad.getGradoAcademico().getIdGradoAcademico())))){
					politicaEscolaridad.setGradoAcademico(new GradoAcademico());
					politicaEscolaridad.getGradoAcademico().setIdGradoAcademico(
															Long.valueOf(politicaEscolaridadDto.getIdGradoAcademico())
															);
						seModificoAlgo=true;
			}
		}		
		// idEstatusEscolar 
		if(politicaEscolaridadDto.getIdEstatusEscolar() != null){
			if(politicaEscolaridad.getEstatusEscolar() == null ||
			   (politicaEscolaridad.getEstatusEscolar() != null && 
			   !politicaEscolaridadDto.getIdEstatusEscolar().equals(String.valueOf(
			    politicaEscolaridad.getEstatusEscolar().getIdEstatusEscolar())))){
					politicaEscolaridad.setEstatusEscolar(new EstatusEscolar());
					politicaEscolaridad.getEstatusEscolar().setIdEstatusEscolar(
															Long.valueOf(politicaEscolaridadDto.getIdEstatusEscolar())
															);
						seModificoAlgo=true;
			}
		}

		if(politicaEscolaridadDto.getPonderacion() != null && 
				!politicaEscolaridadDto.getPonderacion().equals(politicaEscolaridad.getPonderacion())){
			politicaEscolaridad.setPonderacion(Short.valueOf(politicaEscolaridadDto.getPonderacion()));
			seModificoAlgo=true;
		}

		//Si se modifico algo se persiste nueva fecha
		if(seModificoAlgo)
			politicaEscolaridad.getPoliticaValor().setFechaModificacion(DateUtily.getToday());

		return politicaEscolaridad;
	}*/
}
