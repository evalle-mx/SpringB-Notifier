package net.tce.model;

// Generated Jan 24, 2017 6:32:50 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.tce.util.Constante;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;



/**
 * Posicion generated by hbm2java
 */
@Entity @SuppressWarnings("serial")
@Table(name = "POSICION")
@DynamicUpdate	
public class Posicion implements java.io.Serializable {

	private long idPosicion;
	private TipoPrestacion tipoPrestacion;
	private NivelJerarquico nivelJerarquico;
	private Mes mesByMesCaducidad;
	private PeriodicidadPago periodicidadPago;
	private Empresa empresa;
	private Mes mesByMesProgramacion;
	private AmbitoGeografico ambitoGeografico;
	private TipoContrato tipoContrato;
	private Persona persona;
	private EstatusPosicion estatusPosicion;
	private TipoJornada tipoJornada;
	private String nombre;
	private String otros;
	private Boolean sueldoNegociable;
	private Boolean esConfidencial;
	private Long salarioMin;
	private Long salarioMax;
	private Short anioProgramacion;
	private Byte diaProgramacion;
	private Date fechaProgramacion;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Date fechaUltimaBusqueda;
	private Integer periodoBusqueda;
	private String detalleEstatus;
	private Boolean asignada;
	private String claveInterna;
	private Boolean concurrente;
	private Boolean modificado;
	private Date fechaCaducidad;
	private Short anioCaducidad;
	private Short diaCaducidad;
	private Boolean clasificado;
	private Short numeroPublicaciones;
	private Set<RelacionPosicion> relacionPosicionsForIdPosicion = new HashSet<RelacionPosicion>(
			0);
	private Set<PoliticaValor> politicaValors = new HashSet<PoliticaValor>(0);
	private Set<RelacionPosicion> relacionPosicionsForIdPosicionRel = new HashSet<RelacionPosicion>(
			0);
	private Set<Domicilio> domicilios = new HashSet<Domicilio>(0);
	private Set<PerfilPosicion> perfilPosicions = new HashSet<PerfilPosicion>(0);
	private Set<DocumentoClasificacion> documentoClasificacions = new HashSet<DocumentoClasificacion>(
			0);
	private Set<Candidato> candidatos = new HashSet<Candidato>(0);
	private Set<PosicionFiltro> posicionFiltros = new HashSet<PosicionFiltro>(0);

	public Posicion() {
	}

	public Posicion(long idPosicion, Persona persona, Date fechaCreacion) {
		this.idPosicion = idPosicion;
		this.persona = persona;
		this.fechaCreacion = fechaCreacion;
	}

	public Posicion(long idPosicion, TipoPrestacion tipoPrestacion,
			NivelJerarquico nivelJerarquico, Mes mesByMesCaducidad,
			PeriodicidadPago periodicidadPago, Empresa empresa,
			Mes mesByMesProgramacion, AmbitoGeografico ambitoGeografico,
			TipoContrato tipoContrato, Persona persona,
			EstatusPosicion estatusPosicion, TipoJornada tipoJornada,
			String nombre, String otros, Boolean sueldoNegociable,
			Boolean esConfidencial, Long salarioMin, Long salarioMax,
			Short anioProgramacion, Byte diaProgramacion,
			Date fechaProgramacion, Date fechaCreacion, Date fechaModificacion,
			Date fechaUltimaBusqueda, Integer periodoBusqueda,
			String detalleEstatus, Boolean asignada, String claveInterna,
			Boolean concurrente, Boolean modificado, Date fechaCaducidad,
			Short anioCaducidad, Short diaCaducidad, Boolean clasificado,
			Short numeroPublicaciones,
			Set<RelacionPosicion> relacionPosicionsForIdPosicion,
			Set<PoliticaValor> politicaValors,
			Set<RelacionPosicion> relacionPosicionsForIdPosicionRel,
			Set<Domicilio> domicilios, Set<PerfilPosicion> perfilPosicions,
			Set<DocumentoClasificacion> documentoClasificacions,
			Set<Candidato> candidatos, Set<PosicionFiltro> posicionFiltros) {
		this.idPosicion = idPosicion;
		this.tipoPrestacion = tipoPrestacion;
		this.nivelJerarquico = nivelJerarquico;
		this.mesByMesCaducidad = mesByMesCaducidad;
		this.periodicidadPago = periodicidadPago;
		this.empresa = empresa;
		this.mesByMesProgramacion = mesByMesProgramacion;
		this.ambitoGeografico = ambitoGeografico;
		this.tipoContrato = tipoContrato;
		this.persona = persona;
		this.estatusPosicion = estatusPosicion;
		this.tipoJornada = tipoJornada;
		this.nombre = nombre;
		this.otros = otros;
		this.sueldoNegociable = sueldoNegociable;
		this.esConfidencial = esConfidencial;
		this.salarioMin = salarioMin;
		this.salarioMax = salarioMax;
		this.anioProgramacion = anioProgramacion;
		this.diaProgramacion = diaProgramacion;
		this.fechaProgramacion = fechaProgramacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.fechaUltimaBusqueda = fechaUltimaBusqueda;
		this.periodoBusqueda = periodoBusqueda;
		this.detalleEstatus = detalleEstatus;
		this.asignada = asignada;
		this.claveInterna = claveInterna;
		this.concurrente = concurrente;
		this.modificado = modificado;
		this.fechaCaducidad = fechaCaducidad;
		this.anioCaducidad = anioCaducidad;
		this.diaCaducidad = diaCaducidad;
		this.clasificado = clasificado;
		this.numeroPublicaciones = numeroPublicaciones;
		this.relacionPosicionsForIdPosicion = relacionPosicionsForIdPosicion;
		this.politicaValors = politicaValors;
		this.relacionPosicionsForIdPosicionRel = relacionPosicionsForIdPosicionRel;
		this.domicilios = domicilios;
		this.perfilPosicions = perfilPosicions;
		this.documentoClasificacions = documentoClasificacions;
		this.candidatos = candidatos;
		this.posicionFiltros = posicionFiltros;
	}

	@Id
	@Column(name = "ID_POSICION", unique = true, nullable = false)
	@SequenceGenerator(name="seq_Posicion", sequenceName=Constante.SECUENCIA_BD_POSICION, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_Posicion")
	public long getIdPosicion() {
		return this.idPosicion;
	}

	public void setIdPosicion(long idPosicion) {
		this.idPosicion = idPosicion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_PRESTACION")
	public TipoPrestacion getTipoPrestacion() {
		return this.tipoPrestacion;
	}

	public void setTipoPrestacion(TipoPrestacion tipoPrestacion) {
		this.tipoPrestacion = tipoPrestacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_NIVEL_JERARQUICO")
	public NivelJerarquico getNivelJerarquico() {
		return this.nivelJerarquico;
	}

	public void setNivelJerarquico(NivelJerarquico nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERIODICIDAD_PAGO")
	public PeriodicidadPago getPeriodicidadPago() {
		return this.periodicidadPago;
	}

	public void setPeriodicidadPago(PeriodicidadPago periodicidadPago) {
		this.periodicidadPago = periodicidadPago;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPRESA")
	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mes_caducidad")
	public Mes getMesByMesCaducidad() {
		return this.mesByMesCaducidad;
	}

	public void setMesByMesCaducidad(Mes mesByMesCaducidad) {
		this.mesByMesCaducidad = mesByMesCaducidad;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mes_programacion")
	public Mes getMesByMesProgramacion() {
		return this.mesByMesProgramacion;
	}

	public void setMesByMesProgramacion(Mes mesByMesProgramacion) {
		this.mesByMesProgramacion = mesByMesProgramacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AMBITO_GEOGRAFICO")
	public AmbitoGeografico getAmbitoGeografico() {
		return this.ambitoGeografico;
	}

	public void setAmbitoGeografico(AmbitoGeografico ambitoGeografico) {
		this.ambitoGeografico = ambitoGeografico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_CONTRATO")
	public TipoContrato getTipoContrato() {
		return this.tipoContrato;
	}

	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERSONA", nullable = false)
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTATUS_POSICION")
	public EstatusPosicion getEstatusPosicion() {
		return this.estatusPosicion;
	}

	public void setEstatusPosicion(EstatusPosicion estatusPosicion) {
		this.estatusPosicion = estatusPosicion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_JORNADA")
	public TipoJornada getTipoJornada() {
		return this.tipoJornada;
	}

	public void setTipoJornada(TipoJornada tipoJornada) {
		this.tipoJornada = tipoJornada;
	}

	@Column(name = "NOMBRE", length = 250)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "OTROS", length = 1500)
	public String getOtros() {
		return this.otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	@Column(name = "SUELDO_NEGOCIABLE")
	public Boolean getSueldoNegociable() {
		return this.sueldoNegociable;
	}

	public void setSueldoNegociable(Boolean sueldoNegociable) {
		this.sueldoNegociable = sueldoNegociable;
	}

	@Column(name = "ES_CONFIDENCIAL")
	public Boolean getEsConfidencial() {
		return this.esConfidencial;
	}

	public void setEsConfidencial(Boolean esConfidencial) {
		this.esConfidencial = esConfidencial;
	}

	@Column(name = "SALARIO_MIN")
	public Long getSalarioMin() {
		return this.salarioMin;
	}

	public void setSalarioMin(Long salarioMin) {
		this.salarioMin = salarioMin;
	}

	@Column(name = "SALARIO_MAX")
	public Long getSalarioMax() {
		return this.salarioMax;
	}

	public void setSalarioMax(Long salarioMax) {
		this.salarioMax = salarioMax;
	}

	@Column(name = "ANIO_PROGRAMACION")
	public Short getAnioProgramacion() {
		return this.anioProgramacion;
	}

	public void setAnioProgramacion(Short anioProgramacion) {
		this.anioProgramacion = anioProgramacion;
	}

	@Column(name = "DIA_PROGRAMACION")
	public Byte getDiaProgramacion() {
		return this.diaProgramacion;
	}

	public void setDiaProgramacion(Byte diaProgramacion) {
		this.diaProgramacion = diaProgramacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PROGRAMACION", length = 29)
	public Date getFechaProgramacion() {
		return this.fechaProgramacion;
	}

	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CREACION", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_MODIFICACION", length = 29)
	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ULTIMA_BUSQUEDA", length = 29)
	public Date getFechaUltimaBusqueda() {
		return this.fechaUltimaBusqueda;
	}

	public void setFechaUltimaBusqueda(Date fechaUltimaBusqueda) {
		this.fechaUltimaBusqueda = fechaUltimaBusqueda;
	}

	@Column(name = "PERIODO_BUSQUEDA")
	public Integer getPeriodoBusqueda() {
		return this.periodoBusqueda;
	}

	public void setPeriodoBusqueda(Integer periodoBusqueda) {
		this.periodoBusqueda = periodoBusqueda;
	}

	@Column(name = "DETALLE_ESTATUS", length = 1500)
	public String getDetalleEstatus() {
		return this.detalleEstatus;
	}

	public void setDetalleEstatus(String detalleEstatus) {
		this.detalleEstatus = detalleEstatus;
	}

	@Column(name = "ASIGNADA")
	public Boolean getAsignada() {
		return this.asignada;
	}

	public void setAsignada(Boolean asignada) {
		this.asignada = asignada;
	}

	@Column(name = "CLAVE_INTERNA", length = 50)
	public String getClaveInterna() {
		return this.claveInterna;
	}

	public void setClaveInterna(String claveInterna) {
		this.claveInterna = claveInterna;
	}
	
	@Column(name = "CONCURRENTE", precision = 1, scale = 0)
	public Boolean getConcurrente() {
		return this.concurrente;
	}

	public void setConcurrente(Boolean concurrente) {
		this.concurrente = concurrente;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CADUCIDAD", length = 7)
	public Date getFechaCaducidad() {
		return this.fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	@Column(name = "ANIO_CADUCIDAD", precision = 4, scale = 0)
	public Short getAnioCaducidad() {
		return this.anioCaducidad;
	}

	public void setAnioCaducidad(Short anioCaducidad) {
		this.anioCaducidad = anioCaducidad;
	}

	
	@Column(name = "DIA_CADUCIDAD", precision = 2, scale = 0)
	public Short getDiaCaducidad() {
		return this.diaCaducidad;
	}

	public void setDiaCaducidad(Short diaCaducidad) {
		this.diaCaducidad = diaCaducidad;
	}
	
	@Column(name = "MODIFICADO", precision = 1, scale = 0)
	public Boolean getModificado() {
		return this.modificado;
	}

	public void setModificado(Boolean modificado) {
		this.modificado = modificado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posicionByIdPosicion")
	@Cascade({CascadeType.DELETE})	
	public Set<RelacionPosicion> getRelacionPosicionsForIdPosicion() {
		return this.relacionPosicionsForIdPosicion;
	}

	public void setRelacionPosicionsForIdPosicion(
			Set<RelacionPosicion> relacionPosicionsForIdPosicion) {
		this.relacionPosicionsForIdPosicion = relacionPosicionsForIdPosicion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posicion")
	@Cascade({CascadeType.DELETE})	
	public Set<PoliticaValor> getPoliticaValors() {
		return this.politicaValors;
	}

	public void setPoliticaValors(Set<PoliticaValor> politicaValors) {
		this.politicaValors = politicaValors;
	}

	@Column(name = "clasificado")
	public Boolean getClasificado() {
		return this.clasificado;
	}

	public void setClasificado(Boolean clasificado) {
		this.clasificado = clasificado;
	}
	
	@Column(name = "numero_publicaciones")
	public Short getNumeroPublicaciones() {
		return this.numeroPublicaciones;
	}

	public void setNumeroPublicaciones(Short numeroPublicaciones) {
		this.numeroPublicaciones = numeroPublicaciones;
	}
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posicionByIdPosicionRel")
	@Cascade({CascadeType.DELETE})	
	public Set<RelacionPosicion> getRelacionPosicionsForIdPosicionRel() {
		return this.relacionPosicionsForIdPosicionRel;
	}

	public void setRelacionPosicionsForIdPosicionRel(
			Set<RelacionPosicion> relacionPosicionsForIdPosicionRel) {
		this.relacionPosicionsForIdPosicionRel = relacionPosicionsForIdPosicionRel;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posicion")
	@Cascade({CascadeType.DELETE})	
	public Set<Domicilio> getDomicilios() {
		return this.domicilios;
	}

	public void setDomicilios(Set<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posicion")
	@Cascade({CascadeType.DELETE})	
	public Set<PerfilPosicion> getPerfilPosicions() {
		return this.perfilPosicions;
	}

	public void setPerfilPosicions(Set<PerfilPosicion> perfilPosicions) {
		this.perfilPosicions = perfilPosicions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posicion")
	@Cascade({CascadeType.DELETE})	
	public Set<DocumentoClasificacion> getDocumentoClasificacions() {
		return this.documentoClasificacions;
	}

	public void setDocumentoClasificacions(
			Set<DocumentoClasificacion> documentoClasificacions) {
		this.documentoClasificacions = documentoClasificacions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posicion")
	@Cascade({CascadeType.DELETE})	
	public Set<Candidato> getCandidatos() {
		return this.candidatos;
	}

	public void setCandidatos(Set<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "posicion")
	@Cascade({CascadeType.DELETE})	
	public Set<PosicionFiltro> getPosicionFiltros() {
		return this.posicionFiltros;
	}

	public void setPosicionFiltros(Set<PosicionFiltro> posicionFiltros) {
		this.posicionFiltros = posicionFiltros;
	}

}
