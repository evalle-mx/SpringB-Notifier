package net.tce.model;

// Generated May 17, 2018 5:28:09 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import net.tce.util.Constante;

/**
 * NotificacionProgramada generated by hbm2java
 */
@Entity
@Table(name = "notificacion_programada")
@DynamicUpdate
public class NotificacionProgramada implements java.io.Serializable {

	private long idNotificacionProgramada;
	private TipoEvento tipoEvento;
	private Date fechaCreacion;
	private String json;
	private String descripcion;
	private boolean enviada;
	private short intentos;

	public NotificacionProgramada() {
	}

	public NotificacionProgramada(long idNotificacionProgramada,
			TipoEvento tipoEvento, Date fechaCreacion, String json,
			boolean enviada, short intentos) {
		this.idNotificacionProgramada = idNotificacionProgramada;
		this.tipoEvento = tipoEvento;
		this.fechaCreacion = fechaCreacion;
		this.json = json;
		this.enviada = enviada;
		this.intentos = intentos;
	}

	public NotificacionProgramada(long idNotificacionProgramada,
			TipoEvento tipoEvento, Date fechaCreacion, String json,
			String descripcion, boolean enviada, short intentos) {
		this.idNotificacionProgramada = idNotificacionProgramada;
		this.tipoEvento = tipoEvento;
		this.fechaCreacion = fechaCreacion;
		this.json = json;
		this.descripcion = descripcion;
		this.enviada = enviada;
		this.intentos = intentos;
	}

	@Id
	@SequenceGenerator(name="seq_Notificacion_programada", sequenceName=Constante.SECUENCIA_BD_NOTIFICACION_PROGRAMADA, initialValue=1, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_Notificacion_programada")
	@Column(name = "id_notificacion_programada", unique = true, nullable = false)
	public long getIdNotificacionProgramada() {
		return this.idNotificacionProgramada;
	}

	public void setIdNotificacionProgramada(long idNotificacionProgramada) {
		this.idNotificacionProgramada = idNotificacionProgramada;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_evento", nullable = false)
	public TipoEvento getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Column(name = "json", nullable = false, length = 5500)
	public String getJson() {
		return this.json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@Column(name = "descripcion", length = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "enviada", nullable = false)
	public boolean isEnviada() {
		return this.enviada;
	}

	public void setEnviada(boolean enviada) {
		this.enviada = enviada;
	}
	@Column(name = "intentos", nullable = false)
	public short getIntentos() {
		return this.intentos;
	}

	public void setIntentos(short intentos) {
		this.intentos = intentos;
	}
}