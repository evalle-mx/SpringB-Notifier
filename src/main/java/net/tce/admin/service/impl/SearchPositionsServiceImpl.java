package net.tce.admin.service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import net.tce.admin.service.ApplicantService;
import net.tce.admin.service.PosicionService;
import net.tce.admin.service.SearchPositionsService;
import net.tce.dto.PosicionDto;
import net.tce.util.Constante;

@Service("searchPositionsService")
public class SearchPositionsServiceImpl implements SearchPositionsService  {
	Logger log4j = Logger.getLogger( this.getClass());
	
	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ApplicantService applicantService;

	@Autowired
	private PosicionService posicionService;
	/**
	 * Se buscan cv's para las posiciones correspondientes. Una vez encontradas se persisten en la bd
	 * @param lsPosicionDto, es la lista de posiciones
	 * @throws ClassNotFoundException 
	 */
	public boolean searchPositions(List<PosicionDto> lsPosicionDto) {
		
		log4j.debug("Se ejecuta hilos -> taskExecutor="+taskExecutor+
				" applicantService1="+applicantService+
				" lsPosicionDto="+lsPosicionDto.size());
		if (this.taskExecutor != null) {
			for(final PosicionDto posicionDto: lsPosicionDto) {
	            this.taskExecutor.execute(new Runnable() {
	                public void run() {
	                	try {
	                		log4j.debug("$$$%% El hilo de la tarea "+Thread.currentThread().getId()+
	                				  " getIdPosicion="+posicionDto.getIdPosicion());
							applicantService.searchApplicants(posicionDto);
						} catch (Exception e) {
							log4j.error("No se pudo hacer la busqueda de los CVs para la posicion: "+
										posicionDto.getIdPosicion()+", numHilo:"+
										Thread.currentThread().getId()+"--> error:"+e.getMessage()+
										" toString:"+e.toString());
							log4j.debug("$$$%% posicionService="+posicionService);
							posicionService.update(new PosicionDto(posicionDto.getIdPosicion(),
												   Constante.ESTATUS_POSICION_ERROR.longValue(),
												   e.toString()));
							e.printStackTrace();
						}
	                	log4j.debug("$$$%% El hilo de la tarea "+Thread.currentThread().getId()+" termina ");
	                }
	            });
            }
        }
		 log4j.debug("Termina la iteracion(posiciones) ");
		return true;
	}

}
