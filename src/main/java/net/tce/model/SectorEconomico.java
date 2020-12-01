package net.tce.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @SuppressWarnings("serial")
@Table(name = "SECTOR_ECONOMICO")
public class SectorEconomico implements java.io.Serializable {

	private long idSectorEconomico;
	private String descripcion;
	
	private Set<Persona> personas = new HashSet<Persona>(0);
	
	public SectorEconomico() {
	}
	
	public SectorEconomico(long idSectorEconomico, String descripcion) {
		this.idSectorEconomico=idSectorEconomico;
		this.descripcion=descripcion;
	}
	
	public SectorEconomico(long idSectorEconomico, String descripcion
			, Set<Persona> personas) {
		this.descripcion=descripcion;
		this.idSectorEconomico=idSectorEconomico;
		this.personas=personas;
	}

	@Id
	@Column(name = "ID_SECTOR_ECONOMICO", unique = true, nullable = false)
	public long getIdSectorEconomico() {
		return idSectorEconomico;
	}

	public void setIdSectorEconomico(long idSectorEconomico) {
		this.idSectorEconomico = idSectorEconomico;
	}

	@Column(name = "DESCRIPCION", nullable = false, length = 100)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sectorEconomico")
	public Set<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}
	
}
