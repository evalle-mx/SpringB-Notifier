package net.tce.model;

// Generated Dec 21, 2017 7:05:22 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EstadoTracking generated by hbm2java
 */
@Entity
@Table(name = "estado_tracking")
public class EstadoTracking implements java.io.Serializable {

	private long idEstadoTracking;
	private String descripcion;
	private String color;
	private Set<TrackingMonitor> trackingMonitors = new HashSet<TrackingMonitor>(
			0);

	private Set<TrackingPostulante> trackingPostulantes = new HashSet<TrackingPostulante>(
			0);

	public EstadoTracking() {
	}

	public EstadoTracking(long idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}

	public EstadoTracking(long idEstadoTracking, String descripcion,
			String color, Set<TrackingMonitor> trackingMonitors,
			Set<TrackingPostulante> trackingPostulantes) {
		this.idEstadoTracking = idEstadoTracking;
		this.descripcion = descripcion;
		this.color = color;
		this.trackingMonitors = trackingMonitors;
		this.trackingPostulantes = trackingPostulantes;
	}

	@Id
	@Column(name = "id_estado_tracking", unique = true, nullable = false)
	public long getIdEstadoTracking() {
		return this.idEstadoTracking;
	}

	public void setIdEstadoTracking(long idEstadoTracking) {
		this.idEstadoTracking = idEstadoTracking;
	}

	@Column(name = "descripcion", length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "color", length = 100)
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoTracking")
	public Set<TrackingMonitor> getTrackingMonitors() {
		return this.trackingMonitors;
	}

	public void setTrackingMonitors(Set<TrackingMonitor> trackingMonitors) {
		this.trackingMonitors = trackingMonitors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoTracking")
	public Set<TrackingPostulante> getTrackingPostulantes() {
		return this.trackingPostulantes;
	}

	public void setTrackingPostulantes(
			Set<TrackingPostulante> trackingPostulantes) {
		this.trackingPostulantes = trackingPostulantes;
	}

}