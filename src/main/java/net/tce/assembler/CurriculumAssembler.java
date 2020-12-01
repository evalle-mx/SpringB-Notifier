package net.tce.assembler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import net.tce.dto.AcademicBackgroundDto;
import net.tce.dto.ContactInfoDto;
import net.tce.dto.CurriculumDto;
import net.tce.dto.CurriculumEmpresaDto;
import net.tce.dto.LocationInfoDto;
import net.tce.dto.PersonSkillDto;
import net.tce.dto.RelacionEmpresaPersonaDto;
import net.tce.dto.WorkExperienceDto;
import net.tce.model.AmbitoGeografico;
import net.tce.model.Contacto;
import net.tce.model.Domicilio;
import net.tce.model.Empresa;
import net.tce.model.Escolaridad;
import net.tce.model.EstadoCivil;
import net.tce.model.EstatusEscolar;
import net.tce.model.EstatusInscripcion;
import net.tce.model.ExperienciaLaboral;
import net.tce.model.GradoAcademico;
import net.tce.model.Habilidad;
import net.tce.model.Mes;
import net.tce.model.NivelJerarquico;
import net.tce.model.Pais;
import net.tce.model.PeriodoEstadoCivil;
import net.tce.model.Persona;
import net.tce.model.RelacionEmpresaPersona;
import net.tce.model.TipoContrato;
import net.tce.model.TipoConvivencia;
import net.tce.model.TipoDispViajar;
import net.tce.model.TipoEstatusPadres;
import net.tce.model.TipoGenero;
import net.tce.model.TipoJornada;
import net.tce.model.TipoPrestacion;
import net.tce.model.TipoVivienda;
import net.tce.util.Constante;
import net.tce.util.DateUtily;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

/**
 * Contiene convertidores propios del proceso de Curriculum
 * @author tce
 *
 */
public class CurriculumAssembler extends CommonAssembler {
	Logger log4j = Logger.getLogger( this.getClass());
	
	
	boolean seModificoAlgo;
	
	
	
	
	/**
	 * Obtiene un CurriculumDto en base a un JSon, o en su defecto uno nuevo con mensaje de error
	 * @param json
	 * @return
	 */
	public CurriculumDto getCurriculumDtoFromJson(String json){
		log4j.debug("<getCurriculumDtoFromJson> Json " + json );
		CurriculumDto curriculumDto = null;
		try{
			curriculumDto = gson.fromJson(json, CurriculumDto.class);
		}catch(Exception e){
			log4j.error("Excepción de Gson: ", e);
			curriculumDto = new CurriculumDto();
			curriculumDto.setCode(Mensaje.SERVICE_CODE_003);
			curriculumDto.setType(Mensaje.SERVICE_TYPE_FATAL);
			curriculumDto.setMessage(Mensaje.MSG_ERROR);
		}
		
		return curriculumDto;
	}
	
	
	public CurriculumDto getCvDto(Persona persona) throws Exception {
		CurriculumDto personaValidaDto = new CurriculumDto();
		/* Asignaciones de atributos simples : informacion de la persona */
		personaValidaDto.setIdPersona(stValue(persona.getIdPersona()));
		personaValidaDto.setNombre(persona.getNombre());
		personaValidaDto.setApellidoPaterno(persona.getApellidoPaterno());
		personaValidaDto.setApellidoMaterno(persona.getApellidoMaterno());
		personaValidaDto.setEmail(persona.getEmail());
		personaValidaDto.setAnioNacimiento(stValue(persona.getAnioNacimiento()));
		personaValidaDto.setMesNacimiento(stValue(persona.getMes()==null?null:persona.getMes().getIdMes()));
		personaValidaDto.setDiaNacimiento(stValue(persona.getDiaNacimiento()));
		personaValidaDto.setSalarioMin(stValue(persona.getSalarioMin()));
		personaValidaDto.setSalarioMax(stValue(persona.getSalarioMax()));
		personaValidaDto.setPermisoTrabajo(stValue(persona.getPermisoTrabajo()));
		personaValidaDto.setNumeroHijos(stValue(persona.getNumeroHijos()));
		personaValidaDto.setNumeroDependientesEconomicos(stValue(persona.getNumeroDependientesEconomicos()));
		personaValidaDto.setAntiguedadDomicilio(stValue(persona.getAntiguedadDomicilio()));
		personaValidaDto.setCambioDomicilio(stValue(persona.getCambioDomicilio()));
		personaValidaDto.setDisponibilidadHorario(stValue(persona.getDisponibilidadHorario()));
		personaValidaDto.setDiasExperienciaLaboral(stValue(persona.getDiasExperienciaLaboral()));
		
		if (persona.getTipoPersona() != null){
			personaValidaDto.setIdTipoPersona(stValue(persona.getTipoPersona().getIdTipoPersona()));
		}
		if (persona.getEstatusInscripcion() != null){
			personaValidaDto.setIdEstatusInscripcion(stValue(persona.getEstatusInscripcion().getIdEstatusInscripcion()));
		}
		if (persona.getTipoPrestacion() != null){
			personaValidaDto.setIdTipoPrestacion(stValue(persona.getTipoPrestacion().getIdTipoPrestacion()));
		}
		if (persona.getTipoConvivencia() != null){
			personaValidaDto.setIdTipoConvivencia(stValue(persona.getTipoConvivencia().getIdTipoConvivencia()));
		}
		if (persona.getTipoVivienda() != null){
			personaValidaDto.setIdTipoVivienda(stValue(persona.getTipoVivienda().getIdTipoVivienda()));
		}
		if (persona.getPeriodoEstadoCivil() != null){
			personaValidaDto.setIdPeriodoEstadoCivil(stValue(persona.getPeriodoEstadoCivil().getIdPeriodoEstadoCivil()));
		}
		if (persona.getAmbitoGeografico() != null){
			personaValidaDto.setIdAmbitoGeografico(stValue(persona.getAmbitoGeografico().getIdAmbitoGeografico()));
		}
		if (persona.getEstadoCivil() != null){
			personaValidaDto.setIdEstadoCivil(stValue(persona.getEstadoCivil().getIdEstadoCivil()));
		} 
		if (persona.getTipoEstatusPadres() != null){
			personaValidaDto.setIdTipoEstatusPadres(stValue(persona.getTipoEstatusPadres().getIdTipoEstatusPadres()));
		}
		if (persona.getTipoDispViajar() != null){
			personaValidaDto.setIdTipoDispViajar(stValue(persona.getTipoDispViajar().getIdTipoDispViajar()));
		}
		if (persona.getTipoContrato() != null){
			personaValidaDto.setIdTipoContrato(stValue(persona.getTipoContrato().getIdTipoContrato()));
		}
		if (persona.getTipoJornada() != null){
			personaValidaDto.setIdTipoJornada(stValue(persona.getTipoJornada().getIdTipoJornada()));
		}
		if (persona.getGradoAcademico() != null){
			personaValidaDto.setIdGradoAcademicoMax(stValue(persona.getGradoAcademico().getIdGradoAcademico()));
		}
		if (persona.getEstatusEscolar() != null){
			personaValidaDto.setIdEstatusEscolarMax(stValue(persona.getEstatusEscolar().getIdEstatusEscolar()));
		}
		if (persona.getTipoGenero() != null){
			personaValidaDto.setIdTipoGenero(stValue(persona.getTipoGenero().getIdTipoGenero()));
		}
		/* Miembros para contactos */
		//Set<Contacto> contactosPersona = persona.getContactos();
		Iterator<Contacto> itContactosPersona = persona.getContactos().iterator(); 
		List<ContactInfoDto> lsContacto = new ArrayList<ContactInfoDto>();
		while(itContactosPersona.hasNext()){
			Contacto contactoPersona = new Contacto();
			contactoPersona = itContactosPersona.next();
			
			/* Asignaciones de : Atributos simples de contactos */	
			ContactInfoDto dto = new ContactInfoDto();
			dto.setIdContacto(stValue(contactoPersona.getIdContacto()));
			dto.setContacto(contactoPersona.getContacto());

			/* Asignaciones de : contactos.tipoContacto */	
			if(contactoPersona.getTipoContacto() != null){
				dto.setIdTipoContacto(stValue(contactoPersona.getTipoContacto().getIdTipoContacto()));
			}
		
			
			/* Asignaciones de : contactos.contactoTelefono */
			if(contactoPersona.getContactoTelefono() != null){			
//				contactoTelefonoValidacionDto.setIdContacto(contactoPersona.getContactoTelefono().getIdContacto());
//				contactoTelefonoValidacionDto.setPrefijo(contactoPersona.getContactoTelefono().getPrefijo());
//				contactoTelefonoValidacionDto.setCodigoPais(contactoPersona.getContactoTelefono().getCodigoPais());
//				contactoTelefonoValidacionDto.setCodigoArea(contactoPersona.getContactoTelefono().getCodigoArea());
//				contactoTelefonoValidacionDto.setNumero(contactoPersona.getContactoTelefono().getNumero());
//				contactoTelefonoValidacionDto.setAdicional(contactoPersona.getContactoTelefono().getAdicional());FI
				log4j.debug("TODO CODIFICAR  ...");
			}
//			contactoValidacionDto.setContactoTelefono(contactoTelefonoValidacionDto);
			
			/* Conformando la lista final de validacion de contactos */
			lsContacto.add(dto);
		}
		personaValidaDto.setContacto(lsContacto);
		
		/* Asignaciones de : Domicilios */
		//Set<Domicilio> domiciliosPersona = persona.getDomicilios();
		Iterator<Domicilio> itDomiciliosPersona = persona.getDomicilios().iterator();
		log4j.debug("persona.getDomicilios()." + persona.getDomicilios().size() );
		List<LocationInfoDto> domicilioDtos = new ArrayList<LocationInfoDto>(0);
		while(itDomiciliosPersona.hasNext()){
			Domicilio domicilioPersona = itDomiciliosPersona.next();
			log4j.debug("domicilioPersona.getGoogleLatitude : " + domicilioPersona.getGoogleLatitude() );
			/* Asignaciones de : Atributos simples de domicilios */	
			LocationInfoDto domicilioDto = new LocationInfoDto();
			domicilioDto.setNumeroExterior( domicilioPersona.getNumeroExterior());
			domicilioDto.setNumeroInterior( domicilioPersona.getNumeroInterior());
			domicilioDto.setDireccionNoCatalogada( domicilioPersona.getDireccionNoCatalogada());
			domicilioDto.setDescripcion( domicilioPersona.getDescripcion());
			domicilioDto.setCalle( domicilioPersona.getCalle() );
			domicilioDto.setGoogleLatitude(stValue(domicilioPersona.getGoogleLatitude()));
			domicilioDto.setGoogleLongitude(stValue(domicilioPersona.getGoogleLongitude()));
			if( domicilioPersona.getTipoDomicilio()!=null){
				domicilioDto.setIdTipoDomicilio( String.valueOf(domicilioPersona.getTipoDomicilio().getIdTipoDomicilio()) );  //);
			}
			if( domicilioPersona.getAsentamiento()!=null){
				domicilioDto.setIdAsentamiento(String.valueOf( domicilioPersona.getAsentamiento().getIdAsentamiento() ) );   //
			}
			domicilioDtos.add(domicilioDto);
		}
		personaValidaDto.setLocalizacion(domicilioDtos);

		/* Asignaciones de : Experiencias laborales */
		List<WorkExperienceDto> lsExperienciasDto = new ArrayList<WorkExperienceDto>();
		//Set<ExperienciaLaboral> experienciaLaboralsPersona = persona.getExperienciaLaborals();
		Iterator<ExperienciaLaboral> itExperienciaLaboralsPersona = persona.getExperienciaLaborals().iterator(); 
		while(itExperienciaLaboralsPersona.hasNext()){
			WorkExperienceDto workExperiencedto = new WorkExperienceDto();
			ExperienciaLaboral experienciaLaboralPersona = itExperienciaLaboralsPersona.next();			
			/* Asignaciones de : Atributos simples de experienciaLaborals*/	
			workExperiencedto.setIdExperienciaLaboral(stValue(experienciaLaboralPersona.getIdExperienciaLaboral()));
//			experienciaLaboralValidacionDto.setIdExperienciaLaboral(experienciaLaboralPersona.getIdExperienciaLaboral());
			workExperiencedto.setNombreEmpresa(experienciaLaboralPersona.getNombreEmpresa());
			workExperiencedto.setPuesto(experienciaLaboralPersona.getPuesto());
			workExperiencedto.setMotivoSeparacion(experienciaLaboralPersona.getMotivoSeparacion());
			workExperiencedto.setAnioInicio(stValue(experienciaLaboralPersona.getAnioInicio()));
			workExperiencedto.setDiaInicio(stValue(experienciaLaboralPersona.getDiaInicio()));
			workExperiencedto.setAnioFin(stValue(experienciaLaboralPersona.getAnioFin()));
			workExperiencedto.setDiaFin(stValue(experienciaLaboralPersona.getDiaFin()));
			workExperiencedto.setTrabajoActual(stValue(experienciaLaboralPersona.getTrabajoActual()));
			workExperiencedto.setReferencias(stValue(experienciaLaboralPersona.getReferencias()));
			workExperiencedto.setGenteACargo(stValue( experienciaLaboralPersona.getGenteACargo()));
			workExperiencedto.setComplementoDireccion(experienciaLaboralPersona.getComplementoDireccion());
			workExperiencedto.setTexto(experienciaLaboralPersona.getTexto());

			/* Asignaciones de : experienciaLaborals.mesInicio */
			if (experienciaLaboralPersona.getMesByMesInicio() != null){
				workExperiencedto.setMesInicio(stValue(experienciaLaboralPersona.getMesByMesInicio().getIdMes()));
			}
			
			/* Asignaciones de : experienciaLaborals.mesFin */
			if (experienciaLaboralPersona.getMesByMesFin() != null){
				workExperiencedto.setMesFin(stValue(experienciaLaboralPersona.getMesByMesFin().getIdMes()));
			}

			/* Asignaciones de : experienciaLaborals.tipoPrestacion */
			if (experienciaLaboralPersona.getTipoPrestacion() != null){
				workExperiencedto.setIdTipoPrestacion( stValue(experienciaLaboralPersona.getTipoPrestacion().getIdTipoPrestacion()) );
			}
			
			/* Asignaciones de : experienciaLaborals.nivelJerarquico */
			if (experienciaLaboralPersona.getNivelJerarquico() != null){
				workExperiencedto.setIdNivelJerarquico( stValue(experienciaLaboralPersona.getNivelJerarquico().getIdNivelJerarquico()) );
			} 
			/* Asignaciones de : experienciaLaborals.tipoContrato */
			if (experienciaLaboralPersona.getTipoContrato() != null){
				workExperiencedto.setIdTipoContrato(stValue(experienciaLaboralPersona.getTipoContrato().getIdTipoContrato()));
			}
			/* Asignaciones de : experienciaLaborals.estado */
			if (experienciaLaboralPersona.getPais() != null){
				workExperiencedto.setIdPais(stValue(experienciaLaboralPersona.getPais().getIdPais()));
			}
			/* Asignaciones de : experienciaLaborals.tipoJornada */
			if (experienciaLaboralPersona.getTipoJornada() != null){
				workExperiencedto.setIdTipoJornada( stValue(experienciaLaboralPersona.getTipoJornada().getIdTipoJornada()) );
			}			
			/* Conformando la lista final de validacion de experiencias laborales */
			lsExperienciasDto.add(workExperiencedto);
		}
		personaValidaDto.setExperienciaLaboral(lsExperienciasDto);

		/* Asignaciones de : Escolaridades */
		//Set<Escolaridad> escolaridadsPersona = persona.getEscolaridads();
		Iterator<Escolaridad> itEscolaridadsPersona =  persona.getEscolaridads().iterator(); 
		List<AcademicBackgroundDto> lsAcademics = new ArrayList<AcademicBackgroundDto>();
		while(itEscolaridadsPersona.hasNext()){
			Escolaridad escolaridadPersona = itEscolaridadsPersona.next();
			AcademicBackgroundDto dto = new AcademicBackgroundDto();
			/* Asignaciones de : Atributos simples de escolaridads */
			dto.setIdEscolaridad(stValue(escolaridadPersona.getIdEscolaridad()));
			dto.setTitulo(escolaridadPersona.getTitulo());
			dto.setNombreInstitucion(escolaridadPersona.getNombreInstitucion());
			dto.setAnioInicio(stValue(escolaridadPersona.getAnioInicio()));
			dto.setDiaInicio(stValue(escolaridadPersona.getDiaInicio()));
			dto.setAnioFin(stValue(escolaridadPersona.getAnioFin()));
			dto.setDiaFin(stValue(escolaridadPersona.getDiaFin()));
			dto.setTexto(escolaridadPersona.getTexto());

			/* Asignaciones de : escolaridads.mesInicio */
			if (escolaridadPersona.getMesByMesInicio() != null){
				dto.setMesInicio(stValue(escolaridadPersona.getMesByMesInicio().getIdMes()));
			}						
			/* Asignaciones de : escolaridads.mesFin */
			if (escolaridadPersona.getMesByMesFin() != null){
				dto.setMesFin(stValue(escolaridadPersona.getMesByMesFin().getIdMes()));
			}
			/* Asignaciones de : escolaridads.gradoAcademico */
			if (escolaridadPersona.getGradoAcademico() != null){
				dto.setIdGradoAcademico(stValue(escolaridadPersona.getGradoAcademico().getIdGradoAcademico()));
				dto.setNivelGradoAcademico(escolaridadPersona.getGradoAcademico().getNivel());
			}
			
			/* Asignaciones de : escolaridads.estatusEscolar */
			if (escolaridadPersona.getEstatusEscolar() != null){
				dto.setIdEstatusEscolar(stValue(escolaridadPersona.getEstatusEscolar().getIdEstatusEscolar()));
				dto.setNivelEstatusAcademico(escolaridadPersona.getEstatusEscolar().getNivel());
			}
			/* Asignaciones de : escolaridads.pais */
			if (escolaridadPersona.getPais() != null){
				dto.setIdPais(stValue(escolaridadPersona.getPais().getIdPais()));
			}
			/* Conformando la lista final de validacion de escolaridads */
			lsAcademics.add(dto);
		}
		personaValidaDto.setEscolaridad(lsAcademics);

		/* Asignaciones de : Habilidades */
		List<PersonSkillDto> lsPersonaHabilidad = new ArrayList<PersonSkillDto>();
		Iterator<Habilidad> itPersonaHabilidadsPersona = persona.getHabilidads().iterator(); 	
		while(itPersonaHabilidadsPersona.hasNext()){
			Habilidad habilidadPersona = itPersonaHabilidadsPersona.next();
			
			/* Asignaciones de : Atributos simples de habilidads */
			PersonSkillDto dto = new PersonSkillDto();
			dto.setIdHabilidad(stValue(habilidadPersona.getIdHabilidad()));
			
			/* Asignaciones de : habilidads.habilidad */
			if (habilidadPersona.getDescripcion() != null){
				dto.setDescripcion(stValue(habilidadPersona.getDescripcion()));
			}
			/* Asignaciones de : habilidads.dominio */
			if (habilidadPersona.getDominio() != null){
				dto.setIdDominio(stValue(habilidadPersona.getDominio().getIdDominio()));
			}
			/* Conformando la lista final de validacion de habilidads */
			lsPersonaHabilidad.add(dto);
		}
		personaValidaDto.setHabilidad(lsPersonaHabilidad);
		return personaValidaDto;
	}
	
	/**
	 * Hace match del objeto curriculumDto al objeto persona
	 * @param persona
	 * @param personalInfoDto
	 * @return el nuevo objeto
	 */
	public Persona getPersona(Persona persona, CurriculumDto curriculumDto,boolean seModificoAlgo){		
		log4j.info("&& seModificoAlgo= "+seModificoAlgo);
		
		if(curriculumDto.getNombre() != null && 
		   !curriculumDto.getNombre().equals(persona.getNombre())){
			persona.setNombre(curriculumDto.getNombre());
			seModificoAlgo=true;
		}
		if(curriculumDto.getApellidoPaterno() != null &&
		  !curriculumDto.getApellidoPaterno().equals(persona.getApellidoPaterno())){
			persona.setApellidoPaterno(curriculumDto.getApellidoPaterno());
			seModificoAlgo=true;
		}
		if(curriculumDto.getApellidoMaterno() != null &&
		  !curriculumDto.getApellidoMaterno().equals(persona.getApellidoMaterno())){
			persona.setApellidoMaterno(curriculumDto.getApellidoMaterno());
			seModificoAlgo=true;
		}
		if(curriculumDto.getEmail() != null &&
		  !curriculumDto.getEmail().equals(persona.getEmail())){
			persona.setEmail(curriculumDto.getEmail());
			seModificoAlgo=true;
		}
		if(curriculumDto.getAnioNacimiento() != null && 
			!curriculumDto.getAnioNacimiento().equals(String.valueOf(persona.getAnioNacimiento()))){
			persona.setAnioNacimiento(Long.valueOf(curriculumDto.getAnioNacimiento()).shortValue());
			seModificoAlgo=true;
		}else{
			seModificoAlgo=false;
		}
		if(curriculumDto.getMesNacimiento() != null){
			if(persona.getMes() == null ||
			   (persona.getMes() != null && 
			   !curriculumDto.getMesNacimiento().equals(String.valueOf(
			   persona.getMes().getIdMes())))){
				persona.setMes(new Mes());
				persona.getMes().setIdMes(Long.valueOf(curriculumDto.getMesNacimiento()).byteValue());
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getDiaNacimiento() != null && 
		  !curriculumDto.getDiaNacimiento().equals(String.valueOf(persona.getDiaNacimiento()))){
			persona.setDiaNacimiento(Long.valueOf(curriculumDto.getDiaNacimiento()).byteValue());
			seModificoAlgo=true;
		}else{
			seModificoAlgo=false;
		}
		if(curriculumDto.getSalarioMin()!= null && 
		  !curriculumDto.getSalarioMin().equals(String.valueOf(persona.getSalarioMin()))){
			persona.setSalarioMin(Long.valueOf(curriculumDto.getSalarioMin()));
			seModificoAlgo=true;
		}else{
			seModificoAlgo=false;
		}
		if(curriculumDto.getSalarioMax()!= null && 
		  !curriculumDto.getSalarioMax().equals(String.valueOf(persona.getSalarioMax()))){
			persona.setSalarioMax(Long.valueOf(curriculumDto.getSalarioMax()));
			seModificoAlgo=true;
		}else{
			seModificoAlgo=false;
		}
		if(curriculumDto.getIdTipoGenero()!= null){		
			if((persona.getTipoGenero() == null) || 
				(persona.getTipoGenero() != null && 
				!curriculumDto.getIdTipoGenero().equals(String.valueOf(persona.getTipoGenero().getIdTipoGenero())))){
					persona.setTipoGenero(new TipoGenero());
					persona.getTipoGenero().setIdTipoGenero(Long.valueOf(curriculumDto.getIdTipoGenero()).longValue());
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getIdEstadoCivil()!= null){
			if(persona.getEstadoCivil() == null ||
			   (persona.getEstadoCivil() != null && 
			   !curriculumDto.getIdEstadoCivil().equals(String.valueOf(
			   persona.getEstadoCivil().getIdEstadoCivil())))){
					persona.setEstadoCivil(new EstadoCivil());	
					persona.getEstadoCivil().setIdEstadoCivil(Long.valueOf(curriculumDto.getIdEstadoCivil()));
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getIdPeriodoEstadoCivil()!= null){
			if(persona.getPeriodoEstadoCivil() == null || 
				(persona.getPeriodoEstadoCivil() != null && 
				!curriculumDto.getIdPeriodoEstadoCivil().equals(String.valueOf(
				persona.getPeriodoEstadoCivil().getIdPeriodoEstadoCivil())))){
					persona.setPeriodoEstadoCivil(new PeriodoEstadoCivil());
					persona.getPeriodoEstadoCivil().setIdPeriodoEstadoCivil(Long.valueOf(curriculumDto.getIdPeriodoEstadoCivil()));
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getPermisoTrabajo() != null){
			boolean boResp=Long.valueOf(curriculumDto.getPermisoTrabajo()).intValue()  == 0 ? false:true;
			if(persona.getPermisoTrabajo() == null){
				seModificoAlgo=true;
			}else{
				if(persona.getPermisoTrabajo().booleanValue() != boResp)
					seModificoAlgo=true;
			}
			if(seModificoAlgo)
				persona.setPermisoTrabajo(boResp);
		}
		
		//familyTenement
		if(curriculumDto.getNumeroHijos()!= null &&
		   !curriculumDto.getNumeroHijos().equals(persona.getNumeroHijos())){
			persona.setNumeroHijos(new Integer(curriculumDto.getNumeroHijos()));
			seModificoAlgo=true;
		}
		if(curriculumDto.getIdTipoEstatusPadres()!= null){
			if(persona.getTipoEstatusPadres() == null || 
				(persona.getTipoEstatusPadres() != null && 
				!curriculumDto.getIdTipoEstatusPadres().equals(String.valueOf(
				persona.getTipoEstatusPadres().getIdTipoEstatusPadres())))){
					persona.setTipoEstatusPadres(new TipoEstatusPadres());
					persona.getTipoEstatusPadres().setIdTipoEstatusPadres(Long.valueOf(curriculumDto.getIdTipoEstatusPadres()));
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getIdTipoConvivencia()!= null){
			if(persona.getTipoConvivencia() == null || 
				(persona.getTipoConvivencia() != null && 
				!curriculumDto.getIdTipoConvivencia().equals(String.valueOf(
				persona.getTipoConvivencia().getIdTipoConvivencia())))){
					persona.setTipoConvivencia(new TipoConvivencia());
					persona.getTipoConvivencia().setIdTipoConvivencia(Long.valueOf(curriculumDto.getIdTipoConvivencia()));
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getIdTipoVivienda()!= null){
			if(persona.getTipoVivienda() == null || 
				(persona.getTipoVivienda() != null && 
				!curriculumDto.getIdTipoVivienda().equals(String.valueOf(
				persona.getTipoVivienda().getIdTipoVivienda())))){
					persona.setTipoVivienda(new TipoVivienda());
					persona.getTipoVivienda().setIdTipoVivienda(Long.valueOf(curriculumDto.getIdTipoVivienda()));
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getNumeroDependientesEconomicos()!= null && 
			!curriculumDto.getNumeroDependientesEconomicos().equals(
			String.valueOf(persona.getNumeroDependientesEconomicos()))){
				persona.setNumeroDependientesEconomicos(new Integer(curriculumDto.getNumeroDependientesEconomicos()));
				seModificoAlgo=true;
		}
		
		//additionalInfo
		
		if(curriculumDto.getCambioDomicilio()!= null){
			boolean boResp=Long.valueOf(curriculumDto.getCambioDomicilio()).intValue()  == 0 ? false:true;
			if(persona.getCambioDomicilio() == null){
				seModificoAlgo=true;
			}else{
				if(persona.getCambioDomicilio().booleanValue() != boResp)
					seModificoAlgo=true;
			}
			if(seModificoAlgo)
				persona.setCambioDomicilio(boResp);
		}
		if(curriculumDto.getIdTipoDispViajar() != null){
			if(persona.getTipoDispViajar() == null || 
				(persona.getTipoDispViajar() != null && 
				!curriculumDto.getIdTipoDispViajar().equals(String.valueOf(
				persona.getTipoDispViajar().getIdTipoDispViajar())))){
					persona.setTipoDispViajar(new TipoDispViajar());
					persona.getTipoDispViajar().setIdTipoDispViajar(Long.valueOf(curriculumDto.getIdTipoDispViajar()));
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getIdAmbitoGeografico()!= null){
			if(persona.getAmbitoGeografico() == null || 
				(persona.getAmbitoGeografico() != null && 
				!curriculumDto.getIdAmbitoGeografico().equals(String.valueOf(
				persona.getAmbitoGeografico().getIdAmbitoGeografico())))){
					persona.setAmbitoGeografico(new AmbitoGeografico());
					persona.getAmbitoGeografico().setIdAmbitoGeografico(Long.valueOf(curriculumDto.getIdAmbitoGeografico()));	
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getDisponibilidadHorario()!= null){
			boolean boResp=Long.valueOf(curriculumDto.getDisponibilidadHorario()).intValue()  == 0 ? false:true;
			if(persona.getDisponibilidadHorario() == null){
				seModificoAlgo=true;
			}else{
				if(persona.getDisponibilidadHorario().booleanValue() != boResp)
					seModificoAlgo=true;
			}
			if(seModificoAlgo)
				persona.setDisponibilidadHorario(boResp);
		}
		if(curriculumDto.getIdTipoJornada()!= null){	
			if(persona.getTipoJornada() == null || 
			  (persona.getTipoJornada() != null && 
			  !curriculumDto.getIdTipoJornada().equals(String.valueOf(
			  persona.getTipoJornada().getIdTipoJornada())))){
					persona.setTipoJornada(new TipoJornada());
					persona.getTipoJornada().setIdTipoJornada(
												Long.valueOf(curriculumDto.getIdTipoJornada()));	
					seModificoAlgo=true;
			}
		}
		if(curriculumDto.getIdEstatusInscripcion()!=null){
			if(persona.getEstatusInscripcion() == null || 
				(persona.getEstatusInscripcion() != null && 
				!curriculumDto.getIdEstatusInscripcion().equals(String.valueOf(
				persona.getEstatusInscripcion().getIdEstatusInscripcion())))){
					EstatusInscripcion estatusInscripcion=new EstatusInscripcion();
					estatusInscripcion.setIdEstatusInscripcion(Long.valueOf(
									   curriculumDto.getIdEstatusInscripcion()).longValue());
					persona.setEstatusInscripcion(estatusInscripcion);
					seModificoAlgo=true;
			}
		}
		
		if(curriculumDto.getIdGradoAcademicoMax()!=null){
			if(persona.getGradoAcademico()==null ||
					(persona.getGradoAcademico()!=null &&
					!curriculumDto.getIdGradoAcademicoMax().equals(stValue(persona.getGradoAcademico().getIdGradoAcademico())))){
				GradoAcademico gradoAcademico = new GradoAcademico();
				gradoAcademico.setIdGradoAcademico(Long.valueOf(curriculumDto.getIdGradoAcademicoMax()));
				persona.setGradoAcademico(gradoAcademico);
				seModificoAlgo = true;
			}
		}
		if(curriculumDto.getIdEstatusEscolarMax()!=null){
			if(persona.getEstatusEscolar()==null ||
					(persona.getEstatusEscolar()!=null &&
						!curriculumDto.getIdEstatusEscolarMax().equals(stValue(persona.getEstatusEscolar().getIdEstatusEscolar())))
					){
				EstatusEscolar estatusEscolar = new EstatusEscolar();
				estatusEscolar.setIdEstatusEscolar(Long.valueOf(curriculumDto.getIdEstatusEscolarMax()));
				persona.setEstatusEscolar(estatusEscolar);
				seModificoAlgo=true;
			}
			
		}
		
		if(curriculumDto.getTituloMax() != null &&
				  !curriculumDto.getTituloMax().equals(persona.getTituloMax())){
					persona.setTituloMax(curriculumDto.getTituloMax());
					seModificoAlgo=true;
		}
		
		if(curriculumDto.getEdad() != null &&
		   !curriculumDto.getEdad().equals(String.valueOf(persona.getEdad()))){
					persona.setEdad(Byte.valueOf(curriculumDto.getEdad()));
					seModificoAlgo=true;
		}
		
		if(curriculumDto.getDiasExperienciaLaboral()!=null){
			if(persona.getDiasExperienciaLaboral()!=null ||
					!curriculumDto.getDiasExperienciaLaboral().equals(stValue(persona.getDiasExperienciaLaboral()))){				
				persona.setDiasExperienciaLaboral(Long.valueOf(curriculumDto.getDiasExperienciaLaboral()));
				seModificoAlgo=true;
			}
		}
	    //Si se modifico algo se persiste nueva fecha
		if(seModificoAlgo)
			persona.setFechaModificacion(DateUtily.getToday());
		return persona;
	}
	
	
	
	/**
	 * Hace match del objeto workExperienceDto al objeto experienciaLaboral
	 * @param experienciaLaboral
	 * @param workExperienceDto
	 * @return
	 */
	public ExperienciaLaboral getExperienciaLaboral(ExperienciaLaboral experienciaLaboral, 
													WorkExperienceDto workExperienceDto){
		if(workExperienceDto.getComplementoDireccion() != null)
			experienciaLaboral.setComplementoDireccion(workExperienceDto.getComplementoDireccion());
		if(workExperienceDto.getTrabajoActual() != null){
			experienciaLaboral.setTrabajoActual(Long.valueOf(workExperienceDto.getTrabajoActual()).intValue()  == 0 ? false:true);
	    	//Si el estatus_escolar es de estudiante entonces fecha final es nula
			if(workExperienceDto.getTrabajoActual().equals(Constante.TRABAJO_ACTUAL)){
				experienciaLaboral.setAnioFin(null);
				experienciaLaboral.setMesByMesFin(null);
				experienciaLaboral.setDiaFin(null);
			}
		}
		if(workExperienceDto.getIdPais() != null){
			experienciaLaboral.setPais(new Pais());
			experienciaLaboral.getPais().setIdPais(Long.valueOf(workExperienceDto.getIdPais()));
		}
		if(workExperienceDto.getAnioInicio()!= null){
			experienciaLaboral.setAnioInicio(Long.valueOf(workExperienceDto.getAnioInicio()).shortValue());
		}
		if(workExperienceDto.getMesInicio()!= null){
			experienciaLaboral.setMesByMesInicio(new Mes());
			experienciaLaboral.getMesByMesInicio().setIdMes(Long.valueOf(workExperienceDto.getMesInicio()).byteValue());
		}
		if(workExperienceDto.getDiaInicio()!= null){
			experienciaLaboral.setDiaInicio(Long.valueOf(workExperienceDto.getDiaInicio()).byteValue());
		}
		
		if(workExperienceDto.getAnioFin()!= null){
			experienciaLaboral.setAnioFin(Long.valueOf(workExperienceDto.getAnioFin()).shortValue());
		}	
		if(workExperienceDto.getMesFin()!= null){
			experienciaLaboral.setMesByMesFin(new Mes());
			experienciaLaboral.getMesByMesFin().setIdMes(Long.valueOf(workExperienceDto.getMesFin()).byteValue());
		}
		if(workExperienceDto.getDiaFin()!= null){
			experienciaLaboral.setDiaFin(Long.valueOf(workExperienceDto.getDiaFin()).byteValue());
		}
	
		if(workExperienceDto.getGenteACargo() != null)
			experienciaLaboral.setGenteACargo(Long.valueOf(workExperienceDto.getGenteACargo()).intValue() == 0 ? false:true );
		if(workExperienceDto.getMotivoSeparacion() != null)	
			experienciaLaboral.setMotivoSeparacion(workExperienceDto.getMotivoSeparacion());
		if(workExperienceDto.getNombreEmpresa() != null)
			experienciaLaboral.setNombreEmpresa(workExperienceDto.getNombreEmpresa());
		if(workExperienceDto.getIdTipoPrestacion() != null){
			experienciaLaboral.setTipoPrestacion(new TipoPrestacion());
			experienciaLaboral.getTipoPrestacion().setIdTipoPrestacion(Long.valueOf(workExperienceDto.getIdTipoPrestacion()));
		}
		if(workExperienceDto.getPuesto() != null)
			experienciaLaboral.setPuesto(workExperienceDto.getPuesto());
		if(workExperienceDto.getReferencias() != null)	
			experienciaLaboral.setReferencias(Long.valueOf(workExperienceDto.getReferencias()).intValue() == 0 ? false:true );
		if(workExperienceDto.getIdTipoContrato() != null){
			experienciaLaboral.setTipoContrato(new TipoContrato());
			experienciaLaboral.getTipoContrato().setIdTipoContrato(Long.valueOf(workExperienceDto.getIdTipoContrato()));
		}
		if(workExperienceDto.getIdNivelJerarquico() != null){
			experienciaLaboral.setNivelJerarquico(new NivelJerarquico());
			experienciaLaboral.getNivelJerarquico().setIdNivelJerarquico(Long.valueOf(workExperienceDto.getIdNivelJerarquico()));
		}
		if(workExperienceDto.getTexto()  != null){
			experienciaLaboral.setTexto(workExperienceDto.getTexto());
		}
		if(workExperienceDto.getIdTipoJornada()!= null){	
			experienciaLaboral.setTipoJornada(new TipoJornada());
			experienciaLaboral.getTipoJornada().setIdTipoJornada( Long.valueOf(workExperienceDto.getIdTipoJornada()) );
		}
		
		 if(workExperienceDto.getTexto()  != null || workExperienceDto.getPuesto() != null){
			 experienciaLaboral.setTextoFiltrado(UtilsTCE.filterGramaText(
			  (workExperienceDto.getTexto() == null ? (experienciaLaboral.getTexto() == null ? " ":
				experienciaLaboral.getTexto().concat(" ")):workExperienceDto.getTexto().concat(" ")).
			    concat(workExperienceDto.getPuesto() == null ? (experienciaLaboral.getPuesto() == null ? " ":
			    experienciaLaboral.getPuesto()):workExperienceDto.getPuesto())));
			}
		return  experienciaLaboral;
	}
	
	
	
	/**
	 * Hace match del objeto academicBackgroundDto al objeto escolaridad
	 * @param escolaridad
	 * @param academicBackgroundDto
	 * @return
	 */
	public Escolaridad getEscolaridad(Escolaridad  escolaridad, AcademicBackgroundDto academicBackgroundDto){
		if(academicBackgroundDto.getIdPais() != null){
			escolaridad.setPais(new Pais());
			escolaridad.getPais().setIdPais(Long.valueOf(academicBackgroundDto.getIdPais()));
		}
		if(academicBackgroundDto.getIdEstatusEscolar()!= null){
			escolaridad.setEstatusEscolar(new EstatusEscolar());
			escolaridad.getEstatusEscolar().setIdEstatusEscolar(Long.valueOf(
											academicBackgroundDto.getIdEstatusEscolar()));
			log4j.debug("assembler -> getIdEstatusEscolar="+academicBackgroundDto.getIdEstatusEscolar());
			//Si el estatus_escolar es de estudiante entonces fecha final es nula
			 if(academicBackgroundDto.getIdEstatusEscolar().equals(Constante.ESTATUS_ESCOLAR_ESTUDIANTE)){
				 log4j.debug("assembler -> es estudiante, la fecha_fin es nula");
				 escolaridad.setAnioFin(null);
				 escolaridad.setMesByMesFin(null);
				 escolaridad.setDiaFin(null);
			 }
			
		}
		if(academicBackgroundDto.getAnioInicio()!= null){
			escolaridad.setAnioInicio(Long.valueOf(academicBackgroundDto.getAnioInicio()).shortValue());
		}
		if(academicBackgroundDto.getMesInicio()!= null){
			escolaridad.setMesByMesInicio(new Mes());
			escolaridad.getMesByMesInicio().setIdMes(Long.valueOf(academicBackgroundDto.getMesInicio()).byteValue());
		}
		if(academicBackgroundDto.getDiaInicio()!= null){
			escolaridad.setDiaInicio(Long.valueOf(academicBackgroundDto.getDiaInicio()).byteValue());
		}
		
		if(academicBackgroundDto.getAnioFin()!= null){
			escolaridad.setAnioFin(Long.valueOf(academicBackgroundDto.getAnioFin()).shortValue());
		}	
		if(academicBackgroundDto.getMesFin()!= null){
			escolaridad.setMesByMesFin(new Mes());
			escolaridad.getMesByMesFin().setIdMes(Long.valueOf(academicBackgroundDto.getMesFin()).byteValue());			
		}
		if(academicBackgroundDto.getDiaFin()!= null){
			escolaridad.setDiaFin(Long.valueOf(academicBackgroundDto.getDiaFin()).byteValue());
		}
		if(academicBackgroundDto.getIdGradoAcademico()!= null){
			escolaridad.setGradoAcademico(new GradoAcademico());
			escolaridad.getGradoAcademico().setIdGradoAcademico(
											Long.valueOf(academicBackgroundDto.getIdGradoAcademico()));
		}
		if(academicBackgroundDto.getNombreInstitucion() != null){
			escolaridad.setNombreInstitucion(academicBackgroundDto.getNombreInstitucion());
		}
		if(academicBackgroundDto.getTitulo() != null){
			escolaridad.setTitulo(academicBackgroundDto.getTitulo());
		}
		if(academicBackgroundDto.getTexto()  != null){
			escolaridad.setTexto(academicBackgroundDto.getTexto());
			escolaridad.setTextoFiltrado(UtilsTCE.filterGramaText(academicBackgroundDto.getTexto()));
		}
		if(academicBackgroundDto.getTexto()  != null || academicBackgroundDto.getTitulo() != null){
			 escolaridad.setTextoFiltrado(UtilsTCE.filterGramaText(
			  (academicBackgroundDto.getTexto() == null ? (escolaridad.getTexto() == null ? " ":
				  escolaridad.getTexto().concat(" ")):academicBackgroundDto.getTexto().concat(" ")).
			    concat(academicBackgroundDto.getTitulo() == null ? (escolaridad.getTitulo() == null ? " ":
			    	escolaridad.getTitulo()):academicBackgroundDto.getTitulo())));
			}
		return escolaridad;
	}
	
	/**
	 * 
	 * @param empresa
	 * @return
	 * @throws Exception
	 */
	public CurriculumEmpresaDto getEmpresaDto(Empresa empresa) throws Exception {
		CurriculumEmpresaDto empresaValidaDto = new CurriculumEmpresaDto();
		/* Asignaciones de atributos simples : informacion de la persona */
		empresaValidaDto.setIdEmpresa(stValue(empresa.getIdEmpresa()));
//		empresaValidaDto.setTextoClasificacion(stValue(empresa.getIdTextoClasificacion()));
		empresaValidaDto.setRazonSocial(empresa.getRazonSocial());
		empresaValidaDto.setNombre(empresa.getNombre());
		empresaValidaDto.setDescripcion(empresa.getTexto());
//		empresaValidaDto.setFechaInicio(empresa.getFechaInicio());
		empresaValidaDto.setAnioInicio(stValue(empresa.getAnioInicio()));
		empresaValidaDto.setDiaInicio(stValue(empresa.getDiaInicio()));
//		empresaValidaDto.setFechaRegistro(empresa.getFechaRegistro());
		empresaValidaDto.setRfc(empresa.getRfc());
		empresaValidaDto.setNumeroEmpleados(stValue(empresa.getNumeroEmpleados()));
		empresaValidaDto.setClientes(empresa.getClientes());
		empresaValidaDto.setSocios(empresa.getSocios());

		/* Asignaciones de objetos */
		if(empresa.getEstatusInscripcion() != null){
			empresaValidaDto.setIdEstatusInscripcion(stValue(empresa.getEstatusInscripcion().getIdEstatusInscripcion()));
		}
		if(empresa.getMes() != null){
			empresaValidaDto.setMesInicio(stValue(empresa.getMes().getIdMes()));
		}
		
		/* Miembros para contactos */
		Set<Contacto> contactosEmpresa = empresa.getContactos();
		Iterator<Contacto> itContactosEmpresa = contactosEmpresa.iterator(); 
		List<ContactInfoDto> lsContacto = new ArrayList<ContactInfoDto>();
		while(itContactosEmpresa.hasNext()){
			Contacto contacto = new Contacto();
			contacto = itContactosEmpresa.next();
			
			/* Asignaciones de : Atributos simples de contactos */	
			ContactInfoDto dto = new ContactInfoDto();
			dto.setIdContacto(stValue(contacto.getIdContacto()));
			dto.setContacto(contacto.getContacto());

			/* Asignaciones de : contactos.tipoContacto */	
			if(contacto.getTipoContacto() != null){
				dto.setIdTipoContacto(stValue(contacto.getTipoContacto().getIdTipoContacto()));
			}
			
			/* Asignaciones de : contactos.contactoTelefono */
			if(contacto.getContactoTelefono() != null){			
//				contactoTelefonoValidacionDto.setIdContacto(contactoPersona.getContactoTelefono().getIdContacto());
//				contactoTelefonoValidacionDto.setPrefijo(contactoPersona.getContactoTelefono().getPrefijo());
//				contactoTelefonoValidacionDto.setCodigoPais(contactoPersona.getContactoTelefono().getCodigoPais());
//				contactoTelefonoValidacionDto.setCodigoArea(contactoPersona.getContactoTelefono().getCodigoArea());
//				contactoTelefonoValidacionDto.setNumero(contactoPersona.getContactoTelefono().getNumero());
//				contactoTelefonoValidacionDto.setAdicional(contactoPersona.getContactoTelefono().getAdicional());FI
				log4j.debug("TODO CODIFICAR  ...");
			}
//			contactoValidacionDto.setContactoTelefono(contactoTelefonoValidacionDto);
			
			/* Conformando la lista final de validacion de contactos */
			lsContacto.add(dto);
		}
		empresaValidaDto.setContacto(lsContacto);
		
		/* Asignaciones de : Domicilios */
		Set<Domicilio> domicilios = empresa.getDomicilios();
		Iterator<Domicilio> itDomicilios = domicilios.iterator();
		List<LocationInfoDto> domicilioDtos = new ArrayList<LocationInfoDto>(0);
		while(itDomicilios.hasNext()){
			Domicilio domicilio = itDomicilios.next();
			/* Asignaciones de : Atributos simples de domicilios */	
			LocationInfoDto domicilioDto = new LocationInfoDto();
			domicilioDto.setNumeroExterior( domicilio.getNumeroExterior());
			domicilioDto.setNumeroInterior( domicilio.getNumeroInterior());
			domicilioDto.setDireccionNoCatalogada( domicilio.getDireccionNoCatalogada());
			domicilioDto.setDescripcion( domicilio.getDescripcion());
			domicilioDto.setCalle( domicilio.getCalle() );
			if( domicilio.getTipoDomicilio()!=null){
				domicilioDto.setIdTipoDomicilio( stValue(domicilio.getTipoDomicilio().getIdTipoDomicilio()) );  //);
			}
			if( domicilio.getAsentamiento()!=null){
				domicilioDto.setIdAsentamiento(stValue( domicilio.getAsentamiento().getIdAsentamiento() ) );   //
			}
			domicilioDtos.add(domicilioDto);
		}
		empresaValidaDto.setLocalizacion(domicilioDtos);
		
		/* Miembros para Contenidos  */
//		Set<Contenido> contenidosEmpresa = empresa.getContenidos();
//		List<ContenidoDto> contenidosValidacionDto = new ArrayList<ContenidoDto>();
//		ContenidoDto contenidoValidacionDto;

//		/* Asignaciones de : Contenidos */
//		Iterator<Contenido> itContenidosEmpresa = contenidosEmpresa.iterator(); 
//		while(itContenidosEmpresa.hasNext()){
//			Contenido contenidoEmpresa = new Contenido();
//			contenidoEmpresa = itContenidosEmpresa.next();
//			
//			/* Asignaciones de : Atributos simples de contenidos */
//			contenidoValidacionDto = new ContenidoDto();
//			contenidoValidacionDto.setIdContenido(contenidoEmpresa.getIdContenido());
//
//			/* Conformando la lista final de validacion de contenidos */			
//			contenidosValidacionDto.add(contenidoValidacionDto);
//		}
//		empresaValidaDto.setContenido(contenidosValidacionDto); 
		
		/* Miembros para RelacionEmpresaPersonas  */
		Set<RelacionEmpresaPersona> relacionEmpresaPersonas = empresa.getRelacionEmpresaPersonas();
		List<RelacionEmpresaPersonaDto> relacionEmpresaPersonaDto = new ArrayList<RelacionEmpresaPersonaDto>();
		RelacionEmpresaPersonaDto relacionEmpresaPersonaValidacionDto;
		/* Asignaciones de : RelacionEmpresaPersonas */
		Iterator<RelacionEmpresaPersona> itRelacionEmpresaPersonas = relacionEmpresaPersonas.iterator(); 
		while(itRelacionEmpresaPersonas.hasNext()){
			RelacionEmpresaPersona relacionEmpresaPersona = new RelacionEmpresaPersona();
			relacionEmpresaPersona = itRelacionEmpresaPersonas.next();
			
			/* Asignaciones de : Atributos simples de relacionEmpresaPersonas */
			relacionEmpresaPersonaValidacionDto = new RelacionEmpresaPersonaDto();
			relacionEmpresaPersonaValidacionDto.setIdRelacionEmpresaPersona(
										stValue(relacionEmpresaPersona.getIdRelacionEmpresaPersona()));

			/* Conformando la lista final de validacion de contenidos */			
			relacionEmpresaPersonaDto.add(relacionEmpresaPersonaValidacionDto);
		}
		empresaValidaDto.setRelaciones(relacionEmpresaPersonaDto);
		
		return empresaValidaDto;
	}
	
}
