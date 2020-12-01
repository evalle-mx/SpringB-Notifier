package net.tce.model;

// Generated Oct 2, 2018 7:18:15 PM by Hibernate Tools 3.4.0.CR1

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

import net.tce.util.Constante;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;

/**
 * ModeloRscPos generated by hbm2java
 */
@Entity
@DynamicUpdate
@Table(name = "modelo_rsc_pos")
public class ModeloRscPos implements java.io.Serializable {

	private long idModeloRscPos;
	private Rol rol;
	private PerfilPosicion perfilPosicion;
	private String nombre;
	private String descripcion;
	private Boolean monitor;
	private Boolean principal;
	private boolean activo;
	private Set<ModeloRscPosFase> modeloRscPosFases = new HashSet<ModeloRscPosFase>(0);

	public ModeloRscPos() {
	}

	public ModeloRscPos(long idModeloRscPos, PerfilPosicion perfilPosicion, boolean activo) {
		this.idModeloRscPos = idModeloRscPos;
		this.perfilPosicion = perfilPosicion;
		this.activo = activo;
	}

	public ModeloRscPos(long idModeloRscPos, Rol rol, PerfilPosicion perfilPosicion, String nombre, String descripcion,
			Boolean monitor, Boolean principal, boolean activo, Set<ModeloRscPosFase> modeloRscPosFases) {
		this.idModeloRscPos = idModeloRscPos;
		this.rol = rol;
		this.perfilPosicion = perfilPosicion;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.monitor = monitor;
		this.principal = principal;
		this.activo = activo;
		this.modeloRscPosFases = modeloRscPosFases;
	}


	@Id
	@SequenceGenerator(name="seq_modelRSC_pos", sequenceName=Constante.SECUENCIA_BD_MODELO_RSC_POSICION, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_modelRSC_pos")
	@Column(name = "id_modelo_rsc_pos", unique = true, nullable = false)
	public long getIdModeloRscPos() {
		return this.idModeloRscPos;
	}

	public void setIdModeloRscPos(long idModeloRscPos) {
		this.idModeloRscPos = idModeloRscPos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rol")
	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_perfil_posicion", nullable = false)
	public PerfilPosicion getPerfilPosicion() {
		return this.perfilPosicion;
	}

	public void setPerfilPosicion(PerfilPosicion perfilPosicion) {
		this.perfilPosicion = perfilPosicion;
	}

	@Column(name = "nombre", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "descripcion", length = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "monitor")
	public Boolean getMonitor() {
		return this.monitor;
	}

	public void setMonitor(Boolean monitor) {
		this.monitor = monitor;
	}
	
	@Column(name = "principal")
	public Boolean getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modeloRscPos" )
	@Cascade({CascadeType.DELETE})	
	public Set<ModeloRscPosFase> getModeloRscPosFases() {
		return this.modeloRscPosFases;
	}

	public void setModeloRscPosFases(Set<ModeloRscPosFase> modeloRscPosFases) {
		this.modeloRscPosFases = modeloRscPosFases;
	}

}
