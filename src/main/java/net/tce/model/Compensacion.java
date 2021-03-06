package net.tce.model;

// Generated Sep 26, 2017 2:27:37 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Compensacion generated by hbm2java
 */
@Entity
@Table(name = "compensacion")
public class Compensacion implements java.io.Serializable {

	private long idPersona;
	private Persona persona;
	private Short diasAguinaldo;
	private Integer cantidadFondoAhorro;
	private Integer comedor;
	private Boolean celular;
	private Boolean clubGym;
	private Boolean checkUp;
	private Integer ultimoMontoUtilidades;
	private String otro;
	private String curp;
	private Date fechaCaptura;
	private Sueldo sueldo;
	private Set<Plan> plans = new HashSet<Plan>(0);
	private Set<Seguro> seguros = new HashSet<Seguro>(0);
	private Set<Vale> vales = new HashSet<Vale>(0);
	private Vacaciones vacaciones;
	private Set<Bono> bonos = new HashSet<Bono>(0);
	private Automovil automovil;

	public Compensacion() {
	}

	public Compensacion(Persona persona) {
		this.persona = persona;
	}

	public Compensacion(Persona persona, Short diasAguinaldo,
			Integer cantidadFondoAhorro, Integer comedor, Boolean celular,
			Boolean clubGym, Boolean checkUp, Integer ultimoMontoUtilidades,
			String otro, String curp, Date fechaCaptura, Sueldo sueldo,
			Set<Plan> plans, Set<Seguro> seguros, Set<Vale> vales,
			Vacaciones vacaciones, Set<Bono> bonos, Automovil automovil) {
		this.persona = persona;
		this.diasAguinaldo = diasAguinaldo;
		this.cantidadFondoAhorro = cantidadFondoAhorro;
		this.comedor = comedor;
		this.celular = celular;
		this.clubGym = clubGym;
		this.checkUp = checkUp;
		this.ultimoMontoUtilidades = ultimoMontoUtilidades;
		this.otro = otro;
		this.curp = curp;
		this.fechaCaptura = fechaCaptura;
		this.sueldo = sueldo;
		this.plans = plans;
		this.seguros = seguros;
		this.vales = vales;
		this.vacaciones = vacaciones;
		this.bonos = bonos;
		this.automovil = automovil;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "persona"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_persona", unique = true, nullable = false)
	public long getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Column(name = "dias_aguinaldo")
	public Short getDiasAguinaldo() {
		return this.diasAguinaldo;
	}

	public void setDiasAguinaldo(Short diasAguinaldo) {
		this.diasAguinaldo = diasAguinaldo;
	}

	@Column(name = "cantidad_fondo_ahorro")
	public Integer getCantidadFondoAhorro() {
		return this.cantidadFondoAhorro;
	}

	public void setCantidadFondoAhorro(Integer cantidadFondoAhorro) {
		this.cantidadFondoAhorro = cantidadFondoAhorro;
	}

	@Column(name = "comedor")
	public Integer getComedor() {
		return this.comedor;
	}

	public void setComedor(Integer comedor) {
		this.comedor = comedor;
	}

	@Column(name = "celular")
	public Boolean getCelular() {
		return this.celular;
	}

	public void setCelular(Boolean celular) {
		this.celular = celular;
	}

	@Column(name = "club_gym")
	public Boolean getClubGym() {
		return this.clubGym;
	}

	public void setClubGym(Boolean clubGym) {
		this.clubGym = clubGym;
	}

	@Column(name = "check_up")
	public Boolean getCheckUp() {
		return this.checkUp;
	}

	public void setCheckUp(Boolean checkUp) {
		this.checkUp = checkUp;
	}

	@Column(name = "ultimo_monto_utilidades")
	public Integer getUltimoMontoUtilidades() {
		return this.ultimoMontoUtilidades;
	}

	public void setUltimoMontoUtilidades(Integer ultimoMontoUtilidades) {
		this.ultimoMontoUtilidades = ultimoMontoUtilidades;
	}

	@Column(name = "otro", length = 250)
	public String getOtro() {
		return this.otro;
	}

	public void setOtro(String otro) {
		this.otro = otro;
	}

	@Column(name = "curp", length = 100)
	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_captura", length = 29)
	public Date getFechaCaptura() {
		return this.fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "compensacion")
	public Sueldo getSueldo() {
		return this.sueldo;
	}

	public void setSueldo(Sueldo sueldo) {
		this.sueldo = sueldo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compensacion")
	public Set<Plan> getPlans() {
		return this.plans;
	}

	public void setPlans(Set<Plan> plans) {
		this.plans = plans;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compensacion")
	public Set<Seguro> getSeguros() {
		return this.seguros;
	}

	public void setSeguros(Set<Seguro> seguros) {
		this.seguros = seguros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compensacion")
	public Set<Vale> getVales() {
		return this.vales;
	}

	public void setVales(Set<Vale> vales) {
		this.vales = vales;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "compensacion")
	public Vacaciones getVacaciones() {
		return this.vacaciones;
	}

	public void setVacaciones(Vacaciones vacaciones) {
		this.vacaciones = vacaciones;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compensacion")
	public Set<Bono> getBonos() {
		return this.bonos;
	}

	public void setBonos(Set<Bono> bonos) {
		this.bonos = bonos;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "compensacion")
	public Automovil getAutomovil() {
		return this.automovil;
	}

	public void setAutomovil(Automovil automovil) {
		this.automovil = automovil;
	}

}
