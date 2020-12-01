package net.tce.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import net.tce.util.UtilsTCE;

public class CandidatoDto extends ComunDto implements Comparable<Object>{

	private Long idCandidato;
	private Long idPersona;
	private Long idRelacionEmpresaPersona;
	private Long idPosibleCandidato;
	private Long idEmpresa;
	private Long idTipoContrato;
	private Long idTipoGenero;
	private Long idTipoJornada;
	private Byte idEstatusOperativo;
	private Long idGradoAcademico;
	private Short ipg;
	private Short ias;
	private Short iap;
	private String nombre;
	private String gradoAcademico;
	private String estatusEscolar;	
	private String titulo;	
	private String tiempoExperienciaLab;
	private Long idEstatusEscolar;
	private Long idPoliticaKo;
	private String areas;
	private Byte calificacion;
	private Double tiempoExperiencia;
	private Integer edad;
	private String foto;
	private String motivoRechazo;
	private List<String> notas;
	private Boolean handshake;
	private Boolean modificado;
	private Byte verificada;
	private Integer tiempoAntiguedad;
	private String significado;
//	private String salario;
	private Long idArea;
	private Byte idEstatusCandidato;	
	private Long idPosicion;	
	private Long idEmpresaCandidato;
	private List<PerfilDto> lsPerfilDto;
	private Short rangoGeografico;
	private BigDecimal distanciaReal;	
	private BigDecimal iapBrutoTotal;
	private String aliasPersona;
	private Double iapPuntos;
	private Double iapPuntosZ;
	private Integer iasDistancia;
	private Boolean iasEnCola;
	private Boolean ipgEnCola;
	private Double demografico;
	private Float demograficoPonderacion;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Long salarioMinPer;
	private Long salarioMaxPer;	
	private Long salarioAvgPer;	
	private Long salarioMinPos;
	private Long salarioMaxPos;	
	private Long salarioAvgPos;	
	private BigDecimal googleLatitudeDom;
	private BigDecimal googleLongitudeDom;
	private BigDecimal googleLatitudeDomPos;
	private BigDecimal googleLongitudeDomPos;
	private Long idMunicipio;
	private Long idMunicipioPos;
	private Date fechaModificacionPos;
	private Long idMunicipioAdyacencia;
	private Long diasExperienciaLaboral;
	private String atributoValor;
	private String atributoDescripcion;
	private Long contAreas;
	private Boolean nuevoOModificado;
	private Byte diaNacimiento;
	private Byte mesNacimiento;
	private Short anioNacimiento;
	private Short orden;
	private Boolean confirmado;
	private String comentario;
	private Boolean activo;
	
	private Boolean permisoTrabajo;
	private Boolean disponibilidadHorario;
	private Boolean cambioDomicilio;
	private Long idTipoDispViajar;
	private Long idEstadoCivil;
	private Byte gradoAcademicoNivel;
	private Byte estatusEscolarNivel;
	private Long idAmbitoGeografico;
	private String texto;
	private String distanciaAPosicion;
	
	private Long idPosicionPersona;
	private String nombrePosicionPersona;
	private Long idRepresentante;
	private String nombrePosicion;
	private String nombreEmpresa;
	private String idEmpresaExterno;
	private List<AvisoDto> avisos; 
	
	private List<IdiomaDto> idioma;
	private List<CertificacionDto> certificacion;
	


	public CandidatoDto() {
	}
	
	public CandidatoDto(long idCandidato){
		this.idCandidato = idCandidato;
	}
	
	
	public CandidatoDto(String atributoValor, String atributoDescripcion) {
		this.atributoValor = atributoValor;
		this.atributoDescripcion = atributoDescripcion;
		this.calificacion = null;
		this.motivoRechazo = null;
		this.nuevoOModificado = null;
		this.modificado = null;
	}

	public CandidatoDto(Long idPersona,Long idEmpresa,Boolean handshake) {
		this.idPersona=idPersona;
		this.idEmpresa=idEmpresa;
		this.handshake=handshake;
	}
	
	/**
	 * El constructor lo ocupa el metodo getHandCheck(CandidatoDaoImpl)
	 */
	public CandidatoDto(Long idPosicionPersona,Long idPersona,Long idRepresentante,
						String nombrePosicion,String nombreEmpresa,String nombrePosicionPersona) {
						
		this.idPosicionPersona=idPosicionPersona;
		this.idPersona=idPersona == null ? idRepresentante:idPersona;
		this.nombrePosicion=nombrePosicion;
		this.nombreEmpresa=nombreEmpresa;
		this.nombrePosicionPersona=nombrePosicionPersona;
	}

	public CandidatoDto(String code,String type,String message) {
		this.setCode(code);
		this.setType(type);
		this.setMessage(message);
	}
	
	
	/**
	 * constructor se usa en candidatoDaoImpl.get()
	 * @param idCandidato
	 * @param idRelacionEmpresaPersona
	 * @param idPosibleCandidato
	 * @param dummy
	 */
	public CandidatoDto(Long idRelacionEmpresaPersona, Long idPosibleCandidato, Long idCandidato){
		this.idCandidato=idCandidato;
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
		this.idPosibleCandidato = idPosibleCandidato;
	}	
	
	public CandidatoDto(long idCandidato, Byte idEstatusCandidato,
						Long idPosicion, Date fechaCreacion) {
		this.idCandidato = idCandidato;
		this.idEstatusCandidato = idEstatusCandidato;
		this.idPosicion = idPosicion;
		this.fechaCreacion = fechaCreacion;
	}
	
	public CandidatoDto(long idCandidato,String aliasPersona, Short ipg, Short ias,Short iap){
		this.idCandidato = idCandidato;
		this.aliasPersona = aliasPersona;
		this.ipg=ipg;
		this.ias=ias;
		this.iap=iap;
		this.setNuevoOModificado(null);
	}
	
	public CandidatoDto(Long idMunicipioAdyacencia,Long idMunicipio, Long idMunicipioPos, 
						BigDecimal distanciaReal, Short rangoGeografico){
		this.idMunicipioAdyacencia = idMunicipioAdyacencia;
		this.setIdMunicipio(idMunicipio);
		this.idMunicipioPos = idMunicipioPos;
		this.distanciaReal = distanciaReal;
		this.rangoGeografico = rangoGeografico;
	}

	public CandidatoDto(long idCandidato, Byte idEstatusCandidato,
			Long idPosicion, Short rangoGeografico,
			BigDecimal distanciaReal, Short ipg, Short ias, Short iapCodigo,
			Date fechaCreacion, Date fechaModificacion) {
		this.idCandidato = idCandidato;
		this.idEstatusCandidato = idEstatusCandidato;
		this.idPosicion = idPosicion;
		this.rangoGeografico = rangoGeografico;
		this.distanciaReal = distanciaReal;
		this.ipg = ipg;
		this.ias = ias;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * El constructor lo ocupa el metodo getCanditatosPersonas(ApplicantDao)
	 */
	public CandidatoDto(Long idEstatusCandidato, Long idPolitica,String significado,Long idCandidato, Long idPersona,Short iapCodigo,
						Short ipgCodigo,Short iasCodigo,Long idEstatusOperativo,Boolean handshake,String nombre,
						BigDecimal calificacion,Byte diaNacimiento,Byte mesNacimiento,Short anioNacimiento,Byte edad,
						String gradoAcademico, String estatusEscolar,Long diasExperienciaLaboral, Long salarioMin, Long salarioMax,
						BigDecimal distanciaReal, String titulo){			
				this.idEstatusCandidato=idEstatusCandidato.byteValue();
				this.idPoliticaKo=idPolitica;
				this.setSignificado(significado);
				this.idCandidato = idCandidato;
				this.setIdPersona(idPersona);
				this.ias=iasCodigo;
				this.ipg=ipgCodigo;
				this.iap=iapCodigo;
				this.idEstatusOperativo=idEstatusOperativo == null ? null:idEstatusOperativo.byteValue();
				this.handshake=handshake;
				this.nombre=nombre;
				this.calificacion=calificacion == null ? 0:calificacion.byteValue();
				this.diaNacimiento=diaNacimiento;
				this.mesNacimiento=mesNacimiento;
				this.anioNacimiento=anioNacimiento;
				this.edad=edad == null ? null:edad.intValue();
				this.gradoAcademico=gradoAcademico;
				this.setEstatusEscolar(estatusEscolar);
				this.diasExperienciaLaboral=diasExperienciaLaboral;
				this.titulo=titulo;
				this.salarioMinPer=salarioMin;
				this.salarioMaxPer=salarioMax;
				//this.distanciaAPosicion= distanciaReal != null ? UtilsTCE.redondear(distanciaReal,2,RoundingMode.CEILING).toString().concat(" Km"):"No hay dato";
				this.distanciaAPosicion=distanciaReal != null ? UtilsTCE.redondear(distanciaReal,2,RoundingMode.CEILING).toString():"-1";
		}	
	
	
	/**
	 * El constructor lo ocupa el metodo getCanditatosPersonas(ApplicantDao)
	 */
	/*public CandidatoDto(Long idCandidato, Long idPersona,Short iapCodigo,Short ipgCodigo,Short iasCodigo,
						Long idEstatusOperativo,Boolean handshake,Boolean modificado,String nombre,
						Long idTipoGenero,Long diasExperienciaLaboral,Long idGradoAcademico,Long idEstatusEscolar,
						Byte diaNacimiento,Byte mesNacimiento,Short anioNacimiento,Byte edad,
						String calificacion,String motivoRechazo,Long idArea,Long idTipoContrato){
		this.idCandidato = idCandidato;
		this.setIdPersona(idPersona);
		this.ias=iasCodigo;
		this.ipg=ipgCodigo;
		this.iap=iapCodigo;
		this.idEstatusOperativo=idEstatusOperativo == null ? null:idEstatusOperativo.byteValue();
		this.handshake=handshake;
		this.nombre=nombre;
		this.idTipoGenero=idTipoGenero;
		this.diasExperienciaLaboral=diasExperienciaLaboral;
		this.diaNacimiento=diaNacimiento;
		this.mesNacimiento=mesNacimiento;
		this.anioNacimiento=anioNacimiento;
		this.edad=edad == null ? null:edad.intValue();
		this.modificado=modificado;
		this.idGradoAcademico=idGradoAcademico;
		this.idEstatusEscolar=idEstatusEscolar;
		this.calificacion=Byte.valueOf(calificacion);
		this.motivoRechazo=motivoRechazo;
		this.idArea=idArea;
		this.idTipoContrato=idTipoContrato;
	}	*/
	
	/**
	 * El constructor lo ocupa el metodo getPeople(ApplicantDao)
	 */
	public CandidatoDto(Long idPersona, Long salarioMinPer, Long salarioMaxPer, Long salarioAvgPer,
			BigDecimal googleLatitudeDom, BigDecimal googleLongitudeDom, Long idMunicipio,
			String nombre,Long idTipoGenero,Long idGradoAcademico,Long idEstatusEscolar,
			Long diasExperienciaLaboral,Byte diaNacimiento,Byte mesNacimiento,Short anioNacimiento,Byte edad,
			Boolean permisoTrabajo,Boolean disponibilidadHorario,Boolean cambioDomicilio,Long idTipoDispViajar,
			Long idEstadoCivil,Byte gradoAcademicoNivel,Byte estatusEscolarNivel, Long idTipoJornada){
		this.setIdPersona(idPersona);
		this.salarioMinPer = salarioMinPer;
		this.salarioMaxPer = salarioMaxPer;
		this.salarioAvgPer = salarioAvgPer;
		this.setGoogleLatitudeDom(googleLatitudeDom);
		this.setGoogleLongitudeDom(googleLongitudeDom);
		this.setIdMunicipio(idMunicipio);
		this.nombre=nombre;
		this.idTipoGenero=idTipoGenero;
		this.idGradoAcademico=idGradoAcademico;
		this.idEstatusEscolar=idEstatusEscolar;
		this.diasExperienciaLaboral=diasExperienciaLaboral;
		this.diaNacimiento=diaNacimiento;
		this.mesNacimiento=mesNacimiento;
		this.anioNacimiento=anioNacimiento;
		this.edad=edad == null ? null:edad.intValue();
		this.permisoTrabajo=permisoTrabajo;
		this.disponibilidadHorario=disponibilidadHorario;
		this.cambioDomicilio=cambioDomicilio;
		this.idTipoDispViajar=idTipoDispViajar;
		this.idEstadoCivil=idEstadoCivil;
		this.gradoAcademicoNivel=gradoAcademicoNivel;
		this.estatusEscolarNivel=estatusEscolarNivel;
		this.idTipoJornada=idTipoJornada;
	}	
	
	/**
	 * El constructor lo ocupa el metodo getPeopleCandidato(ApplicantDao)
	 */
	public CandidatoDto(Long idCandidato, Long idPersona, Long salarioMinPer, Long salarioMaxPer, Long salarioAvgPer,
			BigDecimal googleLatitudeDom, BigDecimal googleLongitudeDom, Long idMunicipio,
			Date fechaCreacion, Long idEstatusCandidato, Long idEstatusOperativo,
			BigDecimal iap,BigDecimal iapBrutoTotal,Short iasCodigo,Short ipgCodigo,BigDecimal distanciaReal,
			BigDecimal demograficoBruto,Short iapCodigo,Short rangoGeografico,Boolean handshake,
			Boolean modificado, String nombre,Long idTipoGenero,Long idGradoAcademico,Long idEstatusEscolar,
			Long diasExperienciaLaboral,Byte diaNacimiento,Byte mesNacimiento,Short anioNacimiento,Byte edad,
			Boolean permisoTrabajo,Boolean disponibilidadHorario,Boolean cambioDomicilio,Long idTipoDispViajar,
			Long idEstadoCivil,Byte gradoAcademicoNivel,Byte estatusEscolarNivel, Long idTipoJornada, 
			Short orden, Boolean confirmado, String comentario){
		this.idCandidato = idCandidato;
		this.setIdPersona(idPersona);
		this.salarioMinPer = salarioMinPer;
		this.salarioMaxPer = salarioMaxPer;
		this.salarioAvgPer = salarioAvgPer;
		this.setGoogleLatitudeDom(googleLatitudeDom);
		this.setGoogleLongitudeDom(googleLongitudeDom);
		this.setIdMunicipio(idMunicipio);
		this.fechaCreacion = fechaCreacion;
		this.iapPuntosZ=iap == null ? null:iap.doubleValue();
		this.idEstatusCandidato=idEstatusCandidato == null ? null:idEstatusCandidato.byteValue();
		this.idEstatusOperativo=idEstatusOperativo == null ? null:idEstatusOperativo.byteValue();
		this.setIapBrutoTotal(iapBrutoTotal);
		this.ias=iasCodigo;
		this.ipg=ipgCodigo;
		this.iap=iapCodigo;
		this.distanciaReal=distanciaReal;
		this.demografico=demograficoBruto == null ? null:demograficoBruto.doubleValue();
		this.rangoGeografico=rangoGeografico;
		this.handshake=handshake;
		this.modificado=modificado;
		this.nombre=nombre;
		this.idTipoGenero=idTipoGenero;
		this.idGradoAcademico=idGradoAcademico;
		this.idEstatusEscolar=idEstatusEscolar;
		this.diasExperienciaLaboral=diasExperienciaLaboral;
		this.diaNacimiento=diaNacimiento;
		this.mesNacimiento=mesNacimiento;
		this.anioNacimiento=anioNacimiento;
		this.edad=edad == null ? null:edad.intValue();
		this.permisoTrabajo=permisoTrabajo;
		this.disponibilidadHorario=disponibilidadHorario;
		this.cambioDomicilio=cambioDomicilio;
		this.idTipoDispViajar=idTipoDispViajar;
		this.idEstadoCivil=idEstadoCivil;
		this.gradoAcademicoNivel=gradoAcademicoNivel;
		this.estatusEscolarNivel=estatusEscolarNivel;
		this.idTipoJornada=idTipoJornada;
		this.orden=orden;
		this.confirmado=confirmado;
		this.comentario=comentario;
	}	
	
	
	/**
	 * El constructor lo ocupa el metodo getCanditatosempresas(ApplicantDao)
	 */
	public CandidatoDto(Long idEstatusCandidato,Long idCandidato,Long idEmpresa,Short iapCodigo,
			Short ipgCodigo,Long idEstatusOperativo,Boolean handshake,String nombre,
			BigDecimal calificacion){			
	this.idEstatusCandidato=idEstatusCandidato.byteValue();
	this.idCandidato = idCandidato;
	this.idEmpresa=idEmpresa;
	this.ipg=ipgCodigo;
	this.iap=iapCodigo;
	this.idEstatusOperativo=idEstatusOperativo == null ? null:idEstatusOperativo.byteValue();
	this.handshake=handshake;
	this.nombre=nombre;
	this.calificacion=calificacion == null ? 0:calificacion.byteValue();
}
	/*public CandidatoDto(Long idCandidato, Long idEmpresa,Short iapCodigo,Short ipgCodigo,
						Long idEstatusOperativo,Boolean handshake,Boolean modificado,String nombre,
						Boolean estaVerificado,Short anioNacimiento,Byte mesNacimiento,Byte diaNacimiento,
						String motivoRechazo,Long idArea){
		this.idCandidato = idCandidato;
		this.idEmpresa=idEmpresa;;
		this.iap=iapCodigo;
		this.ipg=ipgCodigo;
		this.idEstatusOperativo=idEstatusOperativo == null ? null:idEstatusOperativo.byteValue();
		this.handshake=handshake;
		this.modificado=modificado;
		this.nombre=nombre;
		this.verificada=(byte) (estaVerificado ? 1:0);
		this.diaNacimiento=diaNacimiento;
		this.mesNacimiento=mesNacimiento;
		this.anioNacimiento=anioNacimiento;
		this.motivoRechazo=motivoRechazo;
		this.idArea=idArea;
	}	*/
	
	
	public CandidatoDto(Long idCandidato, Long idEmpresaCandidato, BigDecimal googleLatitudeDom,
			BigDecimal googleLongitudeDom, Long idMunicipio, Long contAreas,Long idEstatusCandidato,
			Long idEstatusOperativo,BigDecimal iap,BigDecimal iapBrutoTotal,BigDecimal distanciaReal,
			Short iapCodigo,Short ipgCodigo,Short rangoGeografico,Boolean handshake,Boolean modificado, 
			Date fechaCreacion,String nombre,String texto){
		this.idCandidato = idCandidato;
		this.idEmpresaCandidato=idEmpresaCandidato;
		this.setGoogleLatitudeDom(googleLatitudeDom);
		this.setGoogleLongitudeDom(googleLongitudeDom);
		this.setIdMunicipio(idMunicipio);
		this.contAreas=contAreas;
		this.iapPuntosZ=iap == null ? null:iap.doubleValue();
		this.idEstatusCandidato=idEstatusCandidato == null ? null:idEstatusCandidato.byteValue();
		this.idEstatusOperativo=idEstatusOperativo == null ? null:idEstatusOperativo.byteValue();
		this.setIapBrutoTotal(iapBrutoTotal);
		this.iap=iapCodigo;
		this.ipg=ipgCodigo;
		this.distanciaReal=distanciaReal;
		this.rangoGeografico=rangoGeografico;
		this.handshake=handshake;
		this.modificado=modificado;
		this.nombre=nombre;
		this.texto=texto;
		this.fechaCreacion = fechaCreacion;
	}	
	
	/**
	 * El constructor lo ocupa el metodo getPositionInfo(ApplicantDao)
	 */
	public CandidatoDto(Long idEmpresa,Long idPosicion, Long salarioMinPos, Long salarioMaxPos,
			Long salarioAvgPos,BigDecimal googleLatitudeDomPos, BigDecimal googleLongitudeDomPos,
			Long idMunicipioPos, Date fechaModificacionPos,Long idAmbitoGeografico,Boolean modificado){
		
		this.idEmpresa = idEmpresa;
		this.idPosicion = idPosicion;
		this.salarioMinPos = salarioMinPos;
		this.salarioMaxPos = salarioMaxPos;
		this.salarioAvgPos = salarioAvgPos;
		this.googleLatitudeDomPos = googleLatitudeDomPos;
		this.googleLongitudeDomPos = googleLongitudeDomPos;
		this.idMunicipioPos = idMunicipioPos;
		this.fechaModificacionPos = fechaModificacionPos;	
		this.setIdAmbitoGeografico(idAmbitoGeografico);
		this.modificado=modificado;
	}	

			

	
	public Long getIdCandidato() {
		return this.idCandidato;
	}

	public void setIdCandidato(Long idCandidato) {
		this.idCandidato = idCandidato;
	}

	public Byte getIdEstatusCandidato() {
		return this.idEstatusCandidato;
	}

	public void setIdEstatusCandidato(Byte idEstatusCandidato) {
		this.idEstatusCandidato = idEstatusCandidato;
	}

	public Byte getIdEstatusOperativo() {
		return idEstatusOperativo;
	}

	public void setIdEstatusOperativo(Byte idEstatusOperativo) {
		this.idEstatusOperativo = idEstatusOperativo;
	}

	public Long getIdPosicion() {
		return this.idPosicion;
	}

	public void setIdPosicion(Long idPosicion) {
		this.idPosicion = idPosicion;
	}

	public Short getRangoGeografico() {
		return this.rangoGeografico;
	}

	public void setRangoGeografico(Short rangoGeografico) {
		this.rangoGeografico = rangoGeografico;
	}

	public BigDecimal getDistanciaReal() {
		return this.distanciaReal;
	}

	public void setDistanciaReal(BigDecimal distanciaReal) {
		this.distanciaReal = distanciaReal;
	}

	public Short getIpg() {
		return this.ipg;
	}

	public void setIpg(Short ipg) {
		this.ipg = ipg;
	}

	public Short getIas() {
		return this.ias;
	}

	public void setIas(Short ias) {
		this.ias = ias;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setLsPerfilDto(List<PerfilDto> lsPerfilDto) {
		this.lsPerfilDto = lsPerfilDto;
	}

	public List<PerfilDto> getLsPerfilDto() {
		return lsPerfilDto;
	}	

	/*public void setMaxAdjacency(Short maxAdjacency) {
		this.maxAdjacency = maxAdjacency;
	}

	public Short getMaxAdjacency() {
		return maxAdjacency;
	}*/

	public void setDemograficoPonderacion(Float demograficoPonderacion) {
		this.demograficoPonderacion = demograficoPonderacion;
	}

	public Float getDemograficoPonderacion() {
		return demograficoPonderacion;
	}

	public void setDemografico(Double demografico) {
		this.demografico = demografico;
	}

	public Double getDemografico() {
		return demografico;
	}

	public Long getSalarioMinPer() {
		return this.salarioMinPer;
	}
	public void setSalarioMinPer(Long salarioMinPer) {
		this.salarioMinPer = salarioMinPer;
	}

	public Long getSalarioMaxPer() {
		return this.salarioMaxPer;
	}
	public void setSalarioMaxPer(Long salarioMaxPer) {
		this.salarioMaxPer = salarioMaxPer;
	}

	public Long getSalarioAvgPer() {
		return this.salarioAvgPer;
	}
	public void setSalarioAvgPer(Long salarioAvgPer) {
		this.salarioAvgPer = salarioAvgPer;
	}
	
	public Long getSalarioMinPos() {
		return this.salarioMinPos;
	}
	public void setSalarioMinPos(Long salarioMinPos) {
		this.salarioMinPos = salarioMinPos;
	}

	public Long getSalarioMaxPos() {
		return this.salarioMaxPos;
	}
	public void setSalarioMaxPos(Long salarioMaxPos) {
		this.salarioMaxPos = salarioMaxPos;
	}

	public Long getSalarioAvgPos() {
		return this.salarioAvgPos;
	}
	public void setSalarioAvgPos(Long salarioAvgPos) {
		this.salarioAvgPos = salarioAvgPos;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setGoogleLatitudeDomPos(BigDecimal googleLatitudeDomPos) {
		this.googleLatitudeDomPos = googleLatitudeDomPos;
	}
	public BigDecimal getGoogleLatitudeDomPos() {
		return googleLatitudeDomPos;
	}

	public void setGoogleLongitudeDomPos(BigDecimal googleLongitudeDomPos) {
		this.googleLongitudeDomPos = googleLongitudeDomPos;
	}
	public BigDecimal getGoogleLongitudeDomPos() {
		return googleLongitudeDomPos;
	}
	

	public void setIdMunicipioPos(Long idMunicipioPos) {
		this.idMunicipioPos = idMunicipioPos;
	}
	public Long getIdMunicipioPos() {
		return idMunicipioPos;
	}

	public Date getFechaModificacionPos() {
		return this.fechaModificacionPos;
	}
	public void setFechaModificacionPos(Date fechaModificacionPos) {
		this.fechaModificacionPos = fechaModificacionPos;
	}
	
	public void setIdMunicipioAdyacencia(Long idMunicipioAdyacencia) {
		this.idMunicipioAdyacencia = idMunicipioAdyacencia;
	}
	public Long getIdMunicipioAdyacencia() {
		return idMunicipioAdyacencia;
	}

	public Long getIdGradoAcademico() {
		return this.idGradoAcademico;
	}
	public void setIdGradoAcademico(Long idGradoAcademico) {
		this.idGradoAcademico = idGradoAcademico;
	}

	public Long getIdEstatusEscolar() {
		return this.idEstatusEscolar;
	}
	public void setIdEstatusEscolar(Long idEstatusEscolar) {
		this.idEstatusEscolar = idEstatusEscolar;
	}

	public Long getDiasExperienciaLaboral() {
		return diasExperienciaLaboral;
	}
	public void setDiasExperienciaLaboral(Long diasExperienciaLaboral) {
		this.diasExperienciaLaboral = diasExperienciaLaboral;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIapPuntos(Double iapPuntos) {
		this.iapPuntos = iapPuntos;
	}

	public Double getIapPuntos() {
		return iapPuntos;
	}

	public void setIap(Short iap) {
		this.iap = iap;
	}

	public Short getIap() {
		return iap;
	}

	public void setAliasPersona(String aliasPersona) {
		this.aliasPersona = aliasPersona;
	}

	public String getAliasPersona() {
		return aliasPersona;
	}

	public String getAtributoValor() {
		return this.atributoValor;
	}

	public void setAtributoValor(String atributoValor) {
		this.atributoValor = atributoValor;
	}

	public String getAtributoDescripcion() {
		return this.atributoDescripcion;
	}

	public void setAtributoDescripcion(String atributoDescripcion) {
		this.atributoDescripcion = atributoDescripcion;
	}


	public void setIapPuntosZ(Double iapPuntosZ) {
		this.iapPuntosZ = iapPuntosZ;
	}

	public Double getIapPuntosZ() {
		return iapPuntosZ;
	}


	public Long getContAreas() {
		return contAreas;
	}

	public void setContAreas(Long contAreas) {
		this.contAreas = contAreas;
	}

	public BigDecimal getIapBrutoTotal() {
		return iapBrutoTotal;
	}

	public void setIapBrutoTotal(BigDecimal iapBrutoTotal) {
		this.iapBrutoTotal = iapBrutoTotal;
	}
	public Boolean getNuevoOModificado() {
		return nuevoOModificado;
	}

	public void setNuevoOModificado(Boolean nuevoOModificado) {
		this.nuevoOModificado = nuevoOModificado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public Long getIdTipoGenero() {
		return idTipoGenero;
	}

	public void setIdTipoGenero(Long idTipoGenero) {
		this.idTipoGenero = idTipoGenero;
	}

	public Byte getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Byte calificacion) {
		this.calificacion = calificacion;
	}

	public Double getTiempoExperiencia() {
		return tiempoExperiencia;
	}

	public void setTiempoExperiencia(Double tiempoExperiencia) {
		this.tiempoExperiencia = tiempoExperiencia;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public Boolean getHandshake() {
		return handshake;
	}

	public void setHandshake(Boolean handshake) {
		this.handshake = handshake;
	}

	public List<String> getNotas() {
		return notas;
	}

	public void setNotas(List<String> notas) {
		this.notas = notas;
	}
	public Boolean getModificado() {
		return modificado;
	}
	public void setModificado(Boolean modificado) {
		this.modificado = modificado;
	}
	public Byte getMesNacimiento() {
		return mesNacimiento;
	}
	public void setMesNacimiento(Byte mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}
	public Short getAnioNacimiento() {
		return anioNacimiento;
	}
	public void setAnioNacimiento(Short anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}
	public Byte getDiaNacimiento() {
		return diaNacimiento;
	}
	public void setDiaNacimiento(Byte diaNacimiento) {
		this.diaNacimiento = diaNacimiento;
	}
	public Boolean getPermisoTrabajo() {
		return permisoTrabajo;
	}
	public void setPermisoTrabajo(Boolean permisoTrabajo) {
		this.permisoTrabajo = permisoTrabajo;
	}
	public Boolean getDisponibilidadHorario() {
		return disponibilidadHorario;
	}
	public void setDisponibilidadHorario(Boolean disponibilidadHorario) {
		this.disponibilidadHorario = disponibilidadHorario;
	}
	public Boolean getCambioDomicilio() {
		return cambioDomicilio;
	}
	public void setCambioDomicilio(Boolean cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}
	public Long getIdTipoDispViajar() {
		return idTipoDispViajar;
	}
	public void setIdTipoDispViajar(Long idTipoDispViajar) {
		this.idTipoDispViajar = idTipoDispViajar;
	}
	public Long getIdEstadoCivil() {
		return idEstadoCivil;
	}
	public void setIdEstadoCivil(Long idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}
	public Byte getGradoAcademicoNivel() {
		return gradoAcademicoNivel;
	}
	public void setGradoAcademicoNivel(Byte gradoAcademicoNivel) {
		this.gradoAcademicoNivel = gradoAcademicoNivel;
	}
	public Byte getEstatusEscolarNivel() {
		return estatusEscolarNivel;
	}
	public void setEstatusEscolarNivel(Byte estatusEscolarNivel) {
		this.estatusEscolarNivel = estatusEscolarNivel;
	}
	public Long getIdEmpresaCandidato() {
		return idEmpresaCandidato;
	}
	public void setIdEmpresaCandidato(Long idEmpresaCandidato) {
		this.idEmpresaCandidato = idEmpresaCandidato;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public BigDecimal getGoogleLatitudeDom() {
		return googleLatitudeDom;
	}
	public void setGoogleLatitudeDom(BigDecimal googleLatitudeDom) {
		this.googleLatitudeDom = googleLatitudeDom;
	}
	public BigDecimal getGoogleLongitudeDom() {
		return googleLongitudeDom;
	}
	public void setGoogleLongitudeDom(BigDecimal googleLongitudeDom) {
		this.googleLongitudeDom = googleLongitudeDom;
	}
	public Long getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	

	public Integer getTiempoAntiguedad() {
		return tiempoAntiguedad;
	}

	public void setTiempoAntiguedad(Integer tiempoAntiguedad) {
		this.tiempoAntiguedad = tiempoAntiguedad;
	}

	public Long getIdAmbitoGeografico() {
		return idAmbitoGeografico;
	}

	public void setIdAmbitoGeografico(Long idAmbitoGeografico) {
		this.idAmbitoGeografico = idAmbitoGeografico;
	}

	public Byte getVerificada() {
		return verificada;
	}

	public void setVerificada(Byte verificada) {
		this.verificada = verificada;
	}

	public Long getIdTipoContrato() {
		return idTipoContrato;
	}

	public void setIdTipoContrato(Long idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}



	public Long getIdPosicionPersona() {
		return idPosicionPersona;
	}

	public void setIdPosicionPersona(Long idPosicionPersona) {
		this.idPosicionPersona = idPosicionPersona;
	}

	public Long getIdRepresentante() {
		return idRepresentante;
	}

	public void setIdRepresentante(Long idRepresentante) {
		this.idRepresentante = idRepresentante;
	}

	public String getNombrePosicion() {
		return nombrePosicion;
	}

	public void setNombrePosicion(String nombrePosicion) {
		this.nombrePosicion = nombrePosicion;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getNombrePosicionPersona() {
		return nombrePosicionPersona;
	}

	public void setNombrePosicionPersona(String nombrePosicionPersona) {
		this.nombrePosicionPersona = nombrePosicionPersona;
	}
	
	public String getIdEmpresaExterno() {
		return idEmpresaExterno;
	}

	public void setIdEmpresaExterno(String idEmpresaExterno) {
		this.idEmpresaExterno = idEmpresaExterno;
	}

	public List<AvisoDto> getAvisos() {
		return avisos;
	}

	public void setAvisos(List<AvisoDto> avisos) {
		this.avisos = avisos;
	}

	public Long getIdTipoJornada() {
		return idTipoJornada;
	}

	public void setIdTipoJornada(Long idTipoJornada) {
		this.idTipoJornada = idTipoJornada;
	}

	public Integer getIasDistancia() {
		return iasDistancia;
	}

	public void setIasDistancia(Integer iasDistancia) {
		this.iasDistancia = iasDistancia;
	}

	public Boolean getIasEnCola() {
		return iasEnCola;
	}

	public void setIasEnCola(Boolean iasEnCola) {
		this.iasEnCola = iasEnCola;
	}

	public Boolean getIpgEnCola() {
		return ipgEnCola;
	}

	public void setIpgEnCola(Boolean ipgEnCola) {
		this.ipgEnCola = ipgEnCola;
	}

	public Long getIdPoliticaKo() {
		return idPoliticaKo;
	}

	public void setIdPoliticaKo(Long idPoliticaKo) {
		this.idPoliticaKo = idPoliticaKo;
	}

	public String getSignificado() {
		return significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}

	@Override
	public int compareTo(Object o) {
		CandidatoDto candidatoDto=(CandidatoDto)o;
		if(this.distanciaReal != null && candidatoDto.distanciaReal != null){
			return this.distanciaReal.compareTo(candidatoDto.distanciaReal);
			/*if(this.distanciaReal.compareTo(candidatoDto.distanciaReal) == 1 ){
				return -1;
			}else if(this.distanciaReal.compareTo(candidatoDto.distanciaReal) == -1){
				return 1;
			}else{
				return 0;
			}*/
		}else if(this.distanciaReal == null && candidatoDto.distanciaReal == null){
			return 0;
		}else{
			return (this.distanciaReal == null) ? -1 : 1;
		}
	}



	
	public String getGradoAcademico() {
		return gradoAcademico;
	}

	public void setGradoAcademico(String gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}

	public String getEstatusEscolar() {
		return estatusEscolar;
	}

	public void setEstatusEscolar(String estatusEscolar) {
		this.estatusEscolar = estatusEscolar;
	}

	public String getDistanciaAPosicion() {
		return distanciaAPosicion;
	}

	public void setDistanciaAPosicion(String distanciaAPosicion) {
		this.distanciaAPosicion = distanciaAPosicion;
	}

	public String getTiempoExperienciaLab() {
		return tiempoExperienciaLab;
	}

	public void setTiempoExperienciaLab(String tiempoExperienciaLab) {
		this.tiempoExperienciaLab = tiempoExperienciaLab;
	}

//	public String getSalario() {
//		return salario;
//	}
//
//	public void setSalario(String salario) {
//		this.salario = salario;
//	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<IdiomaDto> getIdioma() {
		return idioma;
	}

	public void setIdioma(List<IdiomaDto> idioma) {
		this.idioma = idioma;
	}

	public List<CertificacionDto> getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(List<CertificacionDto> certificacion) {
		this.certificacion = certificacion;
	}


	public Short getOrden() {
		return orden;
	}

	public void setOrden(Short orden) {
		this.orden = orden;
	}

	public Boolean getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}

	public void setIdRelacionEmpresaPersona(Long idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}

	public Long getIdPosibleCandidato() {
		return idPosibleCandidato;
	}

	public void setIdPosibleCandidato(Long idPosibleCandidato) {
		this.idPosibleCandidato = idPosibleCandidato;
	}
	

	
	
}