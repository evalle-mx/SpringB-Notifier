package net.tce.dao.impl;


import java.util.List;
import net.tce.dao.ApplicantDao;
import net.tce.dto.CandidatoDto;
import net.tce.dto.PerfilDto;
import net.tce.model.Candidato;
import net.tce.util.Constante;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("applicantDao")
public class ApplicantDaoImpl extends PersistenceGenericDaoImpl<Candidato, Object> 
implements ApplicantDao{
	Logger log4j = Logger.getLogger( this.getClass());
	StringBuilder sb;
	
	
	
	/**
	* Recupera una lista de DTOs usando varios filtros.
	* @param filtros, un mapa con los filtros a aplicar
	* @return una lista de objetos correspondientes
	*/
	/*@Transactional(readOnly=true)
	public CandidatoDto getAdjacency(Long idMunicipio1,Long idMunicipio2){
		log4j.debug("<getAdjacency> idMunicipio1="+idMunicipio1+" idMunicipio2="+idMunicipio2);
		return (CandidatoDto) getSession().createQuery(new StringBuilder("select new net.tce.dto.CandidatoDto( ").
		        append("ADYACENCIA.idMunicipioAdyacencia, ADYACENCIA.municipioByIdMunicipio.idMunicipio,").
		        append("ADYACENCIA.municipioByIdMunicipioAdyacente.idMunicipio,").
		        append("ADYACENCIA.distancia,ADYACENCIA.rango) ").
		        append("from MunicipioAdyacencia as ADYACENCIA ").
		        append("where ADYACENCIA.municipioByIdMunicipio.idMunicipio  = :idMunicipio1 ").
		        append("and ADYACENCIA.municipioByIdMunicipioAdyacente.idMunicipio = :idMunicipio2 ").toString()).
		        setLong("idMunicipio1", idMunicipio1).
		        setLong("idMunicipio2", idMunicipio2).
		        uniqueResult();   
	}*/

	
	
	/**
	* Recupera una lista de posibles candidatos (personas), dependiendo la posicion
	* @param idPosicion, el id de la posicion
	* @return una lista de objetos correspondientes
	*/
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<CandidatoDto> getPeople(long idPosicion, long idEmpresa){
		return (List<CandidatoDto>)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CandidatoDto(")
				.append("PERSONA.idPersona,PERSONA.salarioMin,")
				.append("PERSONA.salarioMax,trunc(PERSONA.salarioMin+((PERSONA.salarioMax-PERSONA.salarioMin)/2)), ")
				.append("CASE when DOMPER.googleLatitude is null THEN DOMMUNPER.latitude ") 
				.append("else DOMPER.googleLatitude end , ")
				.append("CASE when DOMPER.googleLongitude is null THEN DOMMUNPER.longitude ") 
				.append("else DOMPER.googleLongitude end , ")
				.append("DOMMUNPER.idMunicipio, ")
				.append(" PERSONA.nombre ||' '|| PERSONA.apellidoPaterno||''|| ")
				.append(" (CASE when PERSONA.apellidoMaterno is null THEN  '' ")
				.append("  else PERSONA.apellidoMaterno end),")	
				.append("PERSONA.tipoGenero.idTipoGenero ,GRADOACAD.idGradoAcademico, ")
				.append("ESTATUSESC.idEstatusEscolar,PERSONA.diasExperienciaLaboral, ")
				.append("coalesce(PERSONA.diaNacimiento, 1), ")
				.append("PERSONA.mes.idMes,PERSONA.anioNacimiento,PERSONA.edad, ")
				.append("PERSONA.permisoTrabajo,PERSONA.disponibilidadHorario,PERSONA.cambioDomicilio, ") 
				.append("PERSONA.tipoDispViajar.idTipoDispViajar,PERSONA.estadoCivil.idEstadoCivil, ")
				.append("GRADOACAD.nivel,ESTATUSESC.nivel,PERSONA.tipoJornada.idTipoJornada) ")
				.append("from  AreaPersona as AREA_PERSONA ") 
				.append("inner join AREA_PERSONA.persona as PERSONA ")  
				.append("inner join PERSONA.relacionEmpresaPersonas as REP ") 				
				.append("inner join PERSONA.domicilios as DOMPER ")
				.append("inner join AREA_PERSONA.area as AREA ")
				.append("inner join REP.rol as ROL ")
				.append("inner join REP.empresa as EMPRESA ")
				.append("inner join DOMPER.tipoDomicilio as DOMPER_TIPDOM ")
				.append("inner join PERSONA.estatusInscripcion as PER_EDOINS ")
				.append("left outer join PERSONA.gradoAcademico as GRADOACAD ") 
				.append("left outer join  PERSONA.estatusEscolar as ESTATUSESC ") 
				.append("left outer join DOMPER.asentamiento as DOMASEPER ") 
				.append("left outer join DOMASEPER.municipio DOMMUNPER ") 
				.append("where  (DOMPER_TIPDOM.idTipoDomicilio= ")
				.append(Constante.TIPO_DOMICILIO_PRINCIPAL)
				.append(" or DOMPER_TIPDOM.idTipoDomicilio is null) ")
				.append("and PER_EDOINS.idEstatusInscripcion = ")
				.append(Constante.ESTATUS_INSCRIPCION_PUBLICADO)
				.append(" and AREA.idArea in( ")
				.append("select DISTINCT ap.area.idArea from AreaPerfil as ap, PerfilPosicion as pp ") 
				.append("inner join pp.posicion as p  where ap.perfil.idPerfil=pp.perfil.idPerfil ")
				.append("and ap.confirmada = true and p.idPosicion = :idPosicion) ") 
				.append("and  ROL.idRol in (")
				.append(Constante.ROL_CANDIDATO).append(") ")
				.append("and EMPRESA.idEmpresa = :idEmpresa ")
				.append("and AREA_PERSONA.confirmada = true ")
				.append("and PERSONA.idPersona not in( ")
				.append("select PERSONA1.idPersona from Persona as PERSONA1 ")
				.append("inner join PERSONA1.candidatos as CANDIDATO ")
				.append("inner join CANDIDATO.posicion as POSICION ") 
				.append("where POSICION.idPosicion= :idPosicion ) ")
				.append("ORDER BY PERSONA.idPersona").toString())
			    .setLong("idPosicion", idPosicion)
			    .setLong("idEmpresa", idEmpresa).list();
	}	
	
	
	/**
	* Recupera una lista de candidatos (personas), dependiendo la posicion
	* @param idPosicion, el id de la posicion
	* @param idEmpresa, el id de la empresa
	* @return una lista de objetos correspondientes
	* */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<CandidatoDto> getPeopleCandidate(long idPosicion, long idEmpresa) {
		return (List<CandidatoDto>)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CandidatoDto(")
				.append("CANDIDATO.idCandidato,PERSONA.idPersona,PERSONA.salarioMin,")
				.append("PERSONA.salarioMax,trunc(PERSONA.salarioMin+((PERSONA.salarioMax-PERSONA.salarioMin)/2)),")
				.append("CASE when DOMPER.googleLatitude is null THEN DOMMUNPER.latitude ")
				.append(" else DOMPER.googleLatitude end ,")
				.append("CASE when DOMPER.googleLongitude is null THEN DOMMUNPER.longitude ")
				.append(" else DOMPER.googleLongitude end ,")
				.append("DOMMUNPER.idMunicipio,")
				.append("CANDIDATO.fechaCreacion,EDOCANDIDATO.idEstatusCandidato,")
				.append("EDOOPERATIVO.idEstatusOperativo,CANDIDATO.iap,CANDIDATO.iapBruto,")
				.append("CANDIDATO.iasCodigo,CANDIDATO.ipgCodigo,CANDIDATO.distanciaReal,")
				.append("CANDIDATO.demograficoBruto,CANDIDATO.iapCodigo,CANDIDATO.rangoGeografico,")
				.append("CANDIDATO.handshake,CANDIDATO.modificado,")
				.append(" PERSONA.nombre ||'  '|| PERSONA.apellidoPaterno||''|| ")
				.append(" (CASE when PERSONA.apellidoMaterno is null THEN  '' ")
				.append("  else PERSONA.apellidoMaterno end),")	
				.append(" PER_TGENERO.idTipoGenero ,GRADOACAD.idGradoAcademico,")
				.append(" ESTATUSESC.idEstatusEscolar,PERSONA.diasExperienciaLaboral,")
				.append(" coalesce(PERSONA.diaNacimiento, 1), ")
				.append(" PERSONA.mes.idMes,PERSONA.anioNacimiento,PERSONA.edad,")
				.append(" PERSONA.permisoTrabajo,PERSONA.disponibilidadHorario,PERSONA.cambioDomicilio, ")
				.append(" TIP_DISP_VIAJAR.idTipoDispViajar,EDO_CIVIL.idEstadoCivil,")
				.append(" GRADOACAD.nivel,ESTATUSESC.nivel,PER_TJORNADA.idTipoJornada,")
				.append(" CANDIDATO.orden, CANDIDATO.confirmado,CANDIDATO.comentario) ")
				.append("  from  AreaPersona as AREA_PERSONA  ")
				.append("inner join AREA_PERSONA.persona as PERSONA ")
				.append("inner join PERSONA.relacionEmpresaPersonas as REP ")				
				.append("inner join PERSONA.domicilios as DOMPER ")
				.append("inner join PERSONA.candidatos as CANDIDATO ")
				.append("inner join CANDIDATO.posicion as POSICION ")
				.append("inner join CANDIDATO.estatusCandidato as EDOCANDIDATO ")
				.append("inner join CANDIDATO.estatusOperativo  as EDOOPERATIVO ")
				.append("inner join PERSONA.tipoGenero as PER_TGENERO ")
				.append("inner join PERSONA.tipoDispViajar as TIP_DISP_VIAJAR ")
				.append("inner join PERSONA.estadoCivil as EDO_CIVIL ")
				.append("inner join DOMPER.tipoDomicilio as DOMPER_TDOM ")
				.append("inner join PERSONA.estatusInscripcion as EDO_INS ")
				.append("inner join AREA_PERSONA.area as AREA ")
				.append("inner join REP.rol as ROL ")
				.append("inner join REP.empresa as EMPRESA ")
				.append("left outer join PERSONA.tipoJornada as PER_TJORNADA ")
				.append("left outer join PERSONA.gradoAcademico as GRADOACAD ")
				.append("left outer join  PERSONA.estatusEscolar as ESTATUSESC ")
				.append("left outer join DOMPER.asentamiento as DOMASEPER ")
				.append("left outer join DOMASEPER.municipio DOMMUNPER ")				
				.append("where  POSICION.idPosicion = :idPosicion ")
			    .append("and (DOMPER_TDOM.idTipoDomicilio= ")
			    .append(Constante.TIPO_DOMICILIO_PRINCIPAL)
			    .append(" or DOMPER_TDOM.idTipoDomicilio is null)")
			    .append(" and EDO_INS.idEstatusInscripcion = ")
			    .append(Constante.ESTATUS_INSCRIPCION_PUBLICADO)
			    .append(" and AREA.idArea in(")
			    .append("select DISTINCT ap.area.idArea from AreaPerfil as ap, PerfilPosicion as pp ")
			    .append("inner join pp.posicion as p  where ap.perfil.idPerfil=pp.perfil.idPerfil ")
			    .append("and ap.confirmada = true and p.idPosicion = :idPosicion) ")
			    .append(" and  ROL.idRol in (")
			    .append(Constante.ROL_CANDIDATO).append(") ")
			    .append("and EMPRESA.idEmpresa = :idEmpresa ")
			    .append("and AREA_PERSONA.confirmada = true ")
			    .append("and EDOOPERATIVO.idEstatusOperativo != ")
			    .append(Constante.ESTATUS_CANDIDATO_OPERATIVO_DESCARTADO)
			    .append(" ORDER BY PERSONA.idPersona").toString())
			    .setLong("idPosicion", idPosicion)
			    .setLong("idEmpresa", idEmpresa).list();
	}
	
	/**
	 * Recupera una lista de DTOs de candidatos de empresas, dependiendo la posicion
	 * @param idPosicion, el id de la posicion 
	 * @return lista de objetos candidatos
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<CandidatoDto> getEnterprise(long idPosicion){
		return (List<CandidatoDto>)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CandidatoDto(")
				.append("(select CANDIDATO.idCandidato from Candidato as CANDIDATO ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("EMPRESA.idEmpresa,DOMPER.googleLatitude,DOMPER.googleLongitude, ")
				.append("DOMMUNPER.idMunicipio,count(*), ")
				.append("(select CANDIDATO.estatusCandidato.idEstatusCandidato from  ")
				.append("Candidato as CANDIDATO where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.estatusOperativo.idEstatusOperativo from  ")
				.append("Candidato as CANDIDATO where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.iap from Candidato as CANDIDATO ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.iapBruto from Candidato as CANDIDATO  ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion  ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.distanciaReal from Candidato as CANDIDATO  ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.iapCodigo from Candidato as CANDIDATO  ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion  ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.ipgCodigo from Candidato as CANDIDATO ")
				.append(" where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa),")
				.append("(select CANDIDATO.rangoGeografico from Candidato as CANDIDATO  ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion  ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.handshake from Candidato as CANDIDATO ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion  ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.modificado from Candidato as CANDIDATO ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion  ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("(select CANDIDATO.fechaCreacion from Candidato as CANDIDATO ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.empresa.idEmpresa=EMPRESA.idEmpresa), ")
				.append("EMPRESA.nombre,EMPRESA.texto) from  AreaEmpresa as AREA_EMPRESA  ")
				.append("inner join AREA_EMPRESA.empresa as EMPRESA ")
				.append("left outer join EMPRESA.domicilios as DOMPER ")
				.append("left outer join DOMPER.asentamiento as DOMASEPER ")
				.append("left outer join DOMASEPER.municipio DOMMUNPER ")
				.append("where (DOMPER.tipoDomicilio.idTipoDomicilio= ")
				.append(Constante.TIPO_DOMICILIO_PRINCIPAL)
				.append(" or DOMPER.tipoDomicilio.idTipoDomicilio is null) ")
				.append("and EMPRESA.estatusInscripcion.idEstatusInscripcion = ")
				.append(Constante.ESTATUS_INSCRIPCION_PUBLICADO)
				.append("and AREA_EMPRESA.area.idArea in( ")
				.append("select DISTINCT ap.area.idArea from ")
				.append("AreaPerfil as ap, PerfilPosicion as pp ")
				.append("inner join pp.posicion as p  ")
				.append("where ap.perfil.idPerfil=pp.perfil.idPerfil ")
				.append("and p.idPosicion = :idPosicion) ")
				.append("GROUP BY  EMPRESA.idEmpresa,DOMPER.googleLatitude, ")
				.append("DOMPER.googleLongitude,DOMMUNPER.idMunicipio,")
				.append("EMPRESA.nombre,EMPRESA.texto ")
				.append("ORDER BY EMPRESA.idEmpresa ").toString()
			    ).setLong("idPosicion", idPosicion).list();
	}
	
	
	/**
	* Recupera una lista de PerfilDtos dependiendo del id persona y id posicion.
	* @param idPosicion, id de la posicion
	* @param idPersona, id de la persona
	* @return una lista de objetos correspondientes
	*/
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<PerfilDto> getProfilesPeople(long idPosicion, long idPersona){
		return (List<PerfilDto>)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.PerfilDto(")
				.append("pp2.perfil.idPerfil ,pp2.ponderacion,pp1.iapAcademicoBruto, ")
				.append("pp1.iapLaboralBruto, pp1.idPersonaPerfil,pp1.fechaCreacion ) ")
				.append("from PersonaPerfil as pp1, PerfilPosicion as pp2 ")
				.append("where pp1.perfil.idPerfil=pp2.perfil.idPerfil ")
				.append("and pp2.posicion.idPosicion = :idPosicion ")
				.append("and pp1.persona.idPersona= :idPersona").toString()).
				setLong("idPosicion", idPosicion).
			    setLong("idPersona", idPersona).list();
	}
	
	/**
	* Recupera una lista de PerfilDtos usando varios filtros.
	* @param idPosicion, id de la posicion
	* @param idPersona, id de la persona
	* @return una lista de objetos correspondientes
	*/
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<PerfilDto> getProfilesEnterprise(long idPosicion, long idEmpresa) {
		return (List<PerfilDto>)getSession().createQuery(
				new StringBuilder("select new net.tce.dto.PerfilDto(")
				.append("pp2.perfil.idPerfil ,pp2.ponderacion,ep1.iapBruto,")
				.append("ep1.idEmpresaPerfil,ep1.fechaCreacion ) ")
				.append("from EmpresaPerfil as ep1, PerfilPosicion as pp2 ")
				.append("where ep1.perfil.idPerfil=pp2.perfil.idPerfil ")
				.append("and pp2.posicion.idPosicion = :idPosicion ")
				.append("and ep1.empresa.idEmpresa= :idEmpresa").toString()).
				setLong("idPosicion", idPosicion).
			    setLong("idEmpresa", idEmpresa).list();
	}
	
	/**
	* Regresa informacion de la posicion.
	* @param filtros, un mapa con los filtros a aplicar
	* @return una lista de objetos correspondientes
	*/
	@Transactional(readOnly=true)
	public CandidatoDto getPositionInfo(long idPosicion){
		return (CandidatoDto)getSession().createQuery(
			new StringBuilder("select DISTINCT new net.tce.dto.CandidatoDto(" )
			.append("CASE when POSICION.empresa.idEmpresa is null  THEN REP.empresa.idEmpresa ")
			.append(" else POSICION.empresa.idEmpresa end ")
			.append(",POSICION.idPosicion,POSICION.salarioMin, POSICION.salarioMax,")		
			.append("trunc(POSICION.salarioMin+((POSICION.salarioMax-POSICION.salarioMin)/2)), ")
			.append("DOMPOS.googleLatitude,DOMPOS.googleLongitude,DOMMUNPOS.idMunicipio,")
			.append("POSICION.fechaModificacion,POSICION.ambitoGeografico.idAmbitoGeografico, ")
			.append("POSICION.modificado) from Posicion as POSICION ")
			.append(" left outer join POSICION.persona as PERSONA ")
			.append(" left outer join PERSONA.relacionEmpresaPersonas as REP ")
			.append(" left outer join POSICION.domicilios as DOMPOS ")
			.append("left outer join DOMPOS.asentamiento as DOMASEPOS left outer join DOMASEPOS.municipio DOMMUNPOS ")
		    .append("where (DOMPOS.tipoDomicilio.idTipoDomicilio=1 or DOMPOS.tipoDomicilio.idTipoDomicilio is null) ")
			.append("and POSICION.idPosicion = :idPosicion").toString())
			.setLong("idPosicion", idPosicion).uniqueResult();
	}			
	
	/**
	* Recupera la maxima escolaridad y grado academico para la persona proporcionada
	* @param idPersona, Identificador de la persona
	* @return una lista de objetos correspondientes
	*/
	/*@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<CandidatoDto> getMaxSchooling(long idPersona){
		log4j.debug("<getMaxSchooling> Inicio...");
		
		List<CandidatoDto> lsCandidatoDto=
			(List<CandidatoDto>)getSession().createQuery(
			"select new net.tce.dto.CandidatoDto("
			.concat("escolaridad.persona.idPersona, gradoAcademico.idGradoAcademico, estatusEscolar.idEstatusEscolar) ")
			.concat("from Escolaridad as escolaridad ")
			.concat("inner join escolaridad.gradoAcademico as gradoAcademico ")
			.concat("inner join escolaridad.estatusEscolar as estatusEscolar ")
			.concat("inner join escolaridad.mesByMesInicio as mesByMesInicio ")
			.concat("where escolaridad.persona.idPersona=:idPersona ")
			.concat("and gradoAcademico.nivel is not null ")
			.concat("and escolaridad.anioInicio is not null ")
			.concat("order by gradoAcademico.nivel desc, estatusEscolar.nivel desc, ") 
			.concat("escolaridad.anioInicio desc,  ")
			.concat("nvl(lpad(mesByMesInicio.idMes,2,'0'),1) desc, ") 
			.concat("nvl(lpad(escolaridad.diaInicio,2,'0'),1) desc "))
			.setLong("idPersona", idPersona).list();
		
		
		log4j.debug("<getMaxSchooling> Tamaño lista :" + lsCandidatoDto.size());
				
		log4j.debug("<getMaxSchooling> Fin...");
		return lsCandidatoDto;
	}			*/
	
	/**
	* Recupera una lista de candidados_personas dependiendo la posicion
	* @param idPosicion, Identificador de la posicion
	* @return lista de objetos CandidatoDto 
	*/
	@SuppressWarnings("unchecked")
	public List<CandidatoDto> getCanditatosPersonas(Long idPosicion) {
		log4j.debug("<getCanditatosPersonas> dbInterpreter.hibernate_manager: "+dbInterpreter.hibernate_manager);
		return (List<CandidatoDto>) getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CandidatoDto(")
				.append("ESTATUS_CANDIDATO.idEstatusCandidato,POLITICA.idPolitica, POLITICA.significado,CANDIDATO.idCandidato, ")
				.append("PERSONA.idPersona, CANDIDATO.iapCodigo ,CANDIDATO.ipgCodigo,CANDIDATO.iasCodigo,ESTATUS_OPERATIVO.idEstatusOperativo, ")
				.append("CASE when POSICION.empresa.idEmpresa = REP.empresa.idEmpresa THEN true else false end ,")
				//.append("(PERSONA.nombre || '  ' || PERSONA.apellidoPaterno  || '  ' || PERSONA.apellidoMaterno) ,")
				.append(dbInterpreter.hibernate_manager.equals(Constante.DB_MANAGER_PSG.toUpperCase()) ?
				 "( COALESCE(PERSONA.nombre, '') || '  ' || COALESCE(PERSONA.apellidoPaterno, '')  || '  ' ||  COALESCE(PERSONA.apellidoMaterno, '')  ) ":
				"(PERSONA.nombre || '  ' || PERSONA.apellidoPaterno  || '  ' || PERSONA.apellidoMaterno)").append(", ")
				.append(" CANDIDATO.calificacion, coalesce(PERSONA.diaNacimiento, 1), MES.idMes, PERSONA.anioNacimiento,")	
				.append(" PERSONA.edad, GRADO_ACADEMICO.descripcion, ESTATUS_ESCOLAR.descripcion , PERSONA.diasExperienciaLaboral")	
				.append(" , PERSONA.salarioMin, PERSONA.salarioMax, CANDIDATO.distanciaReal,PERSONA.tituloMax) ")
				.append("from Candidato as CANDIDATO ")
				.append("inner join CANDIDATO.persona as PERSONA ")
				.append("inner join PERSONA.relacionEmpresaPersonas as REP ")
				.append("left outer join PERSONA.mes as MES ")
				.append("inner join PERSONA.gradoAcademico as GRADO_ACADEMICO ")
				.append("inner join PERSONA.estatusEscolar as ESTATUS_ESCOLAR ")
				.append("inner join PERSONA.estatusInscripcion as EDO_INSC ")
				.append("inner join CANDIDATO.estatusCandidato as ESTATUS_CANDIDATO ")
				.append("inner join CANDIDATO.estatusOperativo as ESTATUS_OPERATIVO ")
				.append("inner join CANDIDATO.posicion as POSICION ")
				.append("left outer join CANDIDATO.politica as POLITICA ")
				.append("where POSICION.idPosicion = :idPosicion ")
				.append("and ESTATUS_CANDIDATO.idEstatusCandidato in (")
				.append(Constante.ESTATUS_CANDIDATO_ACEPTADO).append(",")
				.append(Constante.ESTATUS_CANDIDATO_RECHAZADO_SALARIO).append(",")
				.append(Constante.ESTATUS_CANDIDATO_RECHAZADO_DISTANCIA).append(",")
				.append(Constante.ESTATUS_CANDIDATO_RECHAZADO_DEMOGRAFICOS).append(",")
				.append(Constante.ESTATUS_CANDIDATO_RECHAZADO_ACADEMICA).append(",")
				.append(Constante.ESTATUS_CANDIDATO_RECHAZADO_LABORAL).append(",")
				.append(Constante.ESTATUS_CANDIDATO_RECHAZADO_HABILIDADES).append(")")
				.append(" and EDO_INSC.idEstatusInscripcion != ")
				.append(Constante.ESTATUS_INSCRIPCION_INACTIVO)
				.append(" and ESTATUS_OPERATIVO.idEstatusOperativo in (")
				.append(Constante.ESTATUS_CANDIDATO_OPERATIVO_INDEXADO).append(" , ")
				.append(Constante.ESTATUS_CANDIDATO_OPERATIVO_INACTIVO).append(")")
				.append(" order by  ESTATUS_CANDIDATO.idEstatusCandidato,POLITICA.idPolitica,CANDIDATO.idCandidato").
				toString()).setLong("idPosicion", idPosicion).list();
	}

	/**
	* Recupera una lista de candidados_empresas dependiendo la posicion
	* @param idPosicion, Identificador de la posicion
	* @return lista de objetos CandidatoDto 
	*/
	@SuppressWarnings("unchecked")
	public List<CandidatoDto> getCanditatosEmpresa(Long idPosicion) {
		return (List<CandidatoDto>) getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CandidatoDto(")
				.append("ESTATUS_CANDIDATO.idEstatusCandidato,CANDIDATO.idCandidato,EMPRESA.idEmpresa, ")
				.append("CANDIDATO.iapCodigo,CANDIDATO.ipgCodigo, ESTATUS_OPERATIVO.idEstatusOperativo,")
				.append("CANDIDATO.handshake,EMPRESA.nombre, CANDIDATO.calificacion )")
				.append("from Candidato as CANDIDATO ")
				.append("inner join CANDIDATO.empresa as EMPRESA ")
				.append("inner join CANDIDATO.posicion as POSICION ")
				.append("inner join CANDIDATO.estatusCandidato as ESTATUS_CANDIDATO ")
				.append("inner join CANDIDATO.estatusOperativo as ESTATUS_OPERATIVO ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.estatusCandidato.idEstatusCandidato in (")
				.append(Constante.ESTATUS_CANDIDATO_ACEPTADO).append(",")
				.append(Constante.ESTATUS_CANDIDATO_RECHAZADO_DISTANCIA).append(")")
				.append(" order by ESTATUS_CANDIDATO.idEstatusCandidato,CANDIDATO.idCandidato").toString())
				.setLong("idPosicion", idPosicion).list();
	}

	
	
	/*public List<CandidatoDto> getCanditatosEmpresa(Long idPosicion) {
		return (List<CandidatoDto>) getSession().createQuery(
				new StringBuilder("select new net.tce.dto.CandidatoDto(")
				.append("CANDIDATO.idCandidato,CANDIDATO.empresa.idEmpresa, CANDIDATO.iapCodigo,")
				.append("CANDIDATO.ipgCodigo, CANDIDATO.estatusOperativo.idEstatusOperativo,CANDIDATO.handshake,")
				.append("CANDIDATO.modificado,EMPRESA.nombre,EMPRESA.estaVerificado,EMPRESA.anioInicio,")
				.append("EMPRESA.mes.idMes,EMPRESA.diaInicio,'rechazo por salario ',AREAEMPRESA.area.idArea)")
				.append("from Candidato as CANDIDATO ")
				.append("inner join CANDIDATO.empresa as EMPRESA ")
				.append("inner join EMPRESA.areaEmpresas AREAEMPRESA ")
				.append("where CANDIDATO.posicion.idPosicion = :idPosicion ")
				.append("and CANDIDATO.estatusCandidato.idEstatusCandidato=")
				.append(Constante.ESTATUS_CANDIDATO_ACEPTADO)
				.append(" order by  CANDIDATO.idCandidato").toString())
				.setLong("idPosicion", idPosicion).list();
	}*/
		
}
