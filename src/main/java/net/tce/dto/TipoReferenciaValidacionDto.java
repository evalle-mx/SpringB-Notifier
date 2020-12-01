package net.tce.dto;

import java.util.HashSet;
import java.util.Set;


public class TipoReferenciaValidacionDto {

	private Long idTipoReferencia;
	private Set<ReferenciaValidacionDto> referencias = new HashSet<ReferenciaValidacionDto>(0);

	public Long getIdTipoReferencia() {
		return this.idTipoReferencia;
	}

	public void setIdTipoReferencia(Long idTipoReferencia) {
		this.idTipoReferencia = idTipoReferencia;
	}

	public Set<ReferenciaValidacionDto> getReferencias() {
		return this.referencias;
	}

	public void setReferencias(Set<ReferenciaValidacionDto> referencias) {
		this.referencias = referencias;
	}

}
