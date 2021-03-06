package net.tce.model;

// Generated Sep 26, 2017 2:27:37 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import net.tce.util.Constante;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Bono generated by hbm2java
 */
@Entity
@Table(name = "bono")
@DynamicUpdate
public class Bono implements java.io.Serializable {

	private long idBono;
	private PeriodicidadBono periodicidadBono;
	private Compensacion compensacion;
	private Long valorCantidad;
	private Integer porcentajeCantidad;
	private String tipoBono;
	private String descripcion;

	public Bono() {
	}

	public Bono(long idBono, Compensacion compensacion) {
		this.idBono = idBono;
		this.compensacion = compensacion;
	}

	public Bono(long idBono, PeriodicidadBono periodicidadBono,
			Compensacion compensacion, Long valorCantidad,
			Integer porcentajeCantidad, String tipoBono, String descripcion) {
		this.idBono = idBono;
		this.periodicidadBono = periodicidadBono;
		this.compensacion = compensacion;
		this.valorCantidad = valorCantidad;
		this.porcentajeCantidad = porcentajeCantidad;
		this.tipoBono = tipoBono;
		this.descripcion = descripcion;
	}

	@Id
	@SequenceGenerator(name="seq_Bono", sequenceName=Constante.SECUENCIA_BD_BONO, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_Bono")
	@Column(name = "id_bono", unique = true, nullable = false)
	public long getIdBono() {
		return this.idBono;
	}

	public void setIdBono(long idBono) {
		this.idBono = idBono;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodicidad_bono")
	public PeriodicidadBono getPeriodicidadBono() {
		return this.periodicidadBono;
	}

	public void setPeriodicidadBono(PeriodicidadBono periodicidadBono) {
		this.periodicidadBono = periodicidadBono;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona", nullable = false)
	public Compensacion getCompensacion() {
		return this.compensacion;
	}

	public void setCompensacion(Compensacion compensacion) {
		this.compensacion = compensacion;
	}

	@Column(name = "valor_cantidad")
	public Long getValorCantidad() {
		return this.valorCantidad;
	}

	public void setValorCantidad(Long valorCantidad) {
		this.valorCantidad = valorCantidad;
	}

	@Column(name = "porcentaje_cantidad")
	public Integer getPorcentajeCantidad() {
		return this.porcentajeCantidad;
	}

	public void setPorcentajeCantidad(Integer porcentajeCantidad) {
		this.porcentajeCantidad = porcentajeCantidad;
	}

	@Column(name = "tipo_bono", length = 100)
	public String getTipoBono() {
		return this.tipoBono;
	}

	public void setTipoBono(String tipoBono) {
		this.tipoBono = tipoBono;
	}

	@Column(name = "descripcion", length = 1500)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
