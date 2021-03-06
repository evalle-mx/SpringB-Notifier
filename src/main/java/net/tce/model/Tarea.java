package net.tce.model;
// Generated Nov 25, 2019 11:54:28 AM by Hibernate Tools 3.5.0.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicUpdate;

import net.tce.util.Constante;

/**
 * Tarea generated by hbm2java
 */
@Entity
@Table(name = "tarea")
@DynamicUpdate
public class Tarea implements java.io.Serializable {

	private long idTarea;
	private String nombre;
	private String descripcion;
	private Set<RolTareaPermiso> rolTareaPermisos = new HashSet<RolTareaPermiso>(0);

	public Tarea() {
	}

	public Tarea(long idTarea) {
		this.idTarea = idTarea;
	}

	public Tarea(long idTarea, String nombre, String descripcion, Set<RolTareaPermiso> rolTareaPermisos) {
		this.idTarea = idTarea;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.rolTareaPermisos = rolTareaPermisos;
	}

	@Id
	@Column(name = "id_tarea", unique = true, nullable = false)
	@SequenceGenerator(name="seq_tarea", sequenceName=Constante.SECUENCIA_BD_TAREA, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_tarea")
	public long getIdTarea() {
		return this.idTarea;
	}

	public void setIdTarea(long idTarea) {
		this.idTarea = idTarea;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tarea")
	@Cascade({CascadeType.DELETE})
	public Set<RolTareaPermiso> getRolTareaPermisos() {
		return this.rolTareaPermisos;
	}

	public void setRolTareaPermisos(Set<RolTareaPermiso> rolTareaPermisos) {
		this.rolTareaPermisos = rolTareaPermisos;
	}

}
