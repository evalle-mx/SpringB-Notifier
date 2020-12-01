package net.tce.assembler;

import org.apache.log4j.Logger;
import net.tce.dto.PosicionDto;
import net.tce.model.EstatusPosicion;
import net.tce.model.Posicion;

/**
 * Contiene convertidores propios del proceso de Busqueda (Search)
 * @author tce
 *
 */
public class SearchAssembler extends CommonAssembler {
	Logger log4j = Logger.getLogger( this.getClass());
	
	/**
	 * Hace match Parcial del objeto posicionDto al objeto posicion para PosicionService.update
	 * @param posicion
	 * @param posicionDto
	 * @return
	 */
	public Posicion getPosicion(Posicion posicion, PosicionDto posicionDto){
		
		if(posicionDto.getDetalle() != null &&
		   !posicionDto.getDetalle().equals(posicion.getDetalleEstatus())){
			posicion.setDetalleEstatus(posicionDto.getDetalle());
		}
		if(posicionDto.getIdEstatusPosicion() != 0 &&
		   posicionDto.getIdEstatusPosicion() != posicion.getEstatusPosicion().getIdEstatusPosicion()){
			posicion.setEstatusPosicion(new EstatusPosicion());
			posicion.getEstatusPosicion().setIdEstatusPosicion(posicionDto.getIdEstatusPosicion());
		}
		
		return posicion;
	}

	
}
