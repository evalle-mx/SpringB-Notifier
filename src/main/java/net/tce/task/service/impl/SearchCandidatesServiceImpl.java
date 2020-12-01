package net.tce.task.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import net.tce.admin.service.ApplicantService;
import net.tce.dto.PosicionDto;
import net.tce.dto.SchedulerDto;
import net.tce.task.service.SearchCandidatesService;
import net.tce.util.DateUtily;
import net.tce.dao.ControlProcesoDao;
import net.tce.dao.PosicionDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("searchCandidatesService")
public class SearchCandidatesServiceImpl implements SearchCandidatesService {

	static final Logger log4j = Logger.getLogger("QUARTZ");

	@Autowired
	ControlProcesoDao controlProcesoDao;
	
	@Autowired
	PosicionDao posicionDao;
	
	@Autowired
	ApplicantService applicantService;
	
	//@Autowired
	//SearchPositionsService searchPositionsService;

	@Inject
	private ConversionService converter;	

	/**
	 * Llama el proceso de búsqueda (índices) a partir de una posición dada o en ausencia de ella sobre las posiciones publicadas.
	 * @param SchedulerDto schedulerDto
	 * @return String
	 * @author olm
	 */		
	public String searchCandidates(SchedulerDto schedulerDto) throws  Exception{
		log4j.debug("<searchCandidates> Inicio... getIdEmpresaConf="+schedulerDto.getIdEmpresaConf());
		
		//Se obtienen las posiciones programadas
		List<PosicionDto> lsPosicionDto=posicionDao.getPosicionConcurrente();
		log4j.debug("<searchCandidates> Inicio... lsPosicionDto="+lsPosicionDto.size());
		if(lsPosicionDto != null && lsPosicionDto.size() > 0 ){			
			Date ahora=DateUtily.getToday();
			int resFechaCaducidad=0;
			Iterator<PosicionDto> itPosicionDto =lsPosicionDto.iterator();
			while(itPosicionDto.hasNext()){
				PosicionDto posicionDto=itPosicionDto.next();
				
				//si hay fechacaducidad
				if(posicionDto.getFechaCaducidad() != null){
					resFechaCaducidad=DateUtily.compareDt1Dt2(posicionDto.getFechaCaducidad(),ahora);
				}
				log4j.debug("<searchCandidates> getIdPosicion="+posicionDto.getIdPosicion()+
						" getFechaCaducidad="+posicionDto.getFechaCaducidad()+
						" resFechaCaducidad="+resFechaCaducidad+
						" getFechaUltimaBusqueda="+posicionDto.getFechaUltimaBusqueda()+
						" getPeriodoBusqueda="+posicionDto.getPeriodoBusqueda()+
						" ahora="+ahora+
						" isHuboCambioPosicion="+posicionDto.isHuboCambioPosicion());
				
				//Se analiza la caducidad
				if(posicionDto.getFechaCaducidad() == null || (resFechaCaducidad == 1 || resFechaCaducidad == 0)){
					//log4j.debug("<searchCandidates> Pasa filtro fecha caducidad");
					//log4j.debug("<searchCandidates> compare="+(DateUtily.compareDt1Dt2(DateUtily.addHours(posicionDto.getFechaUltimaBusqueda(),
					//		 posicionDto.getPeriodoBusqueda()), ahora) == 1));
					
					//Se analiza si se ejecuta la busqueda para la posicion
					if(posicionDto.getFechaUltimaBusqueda() == null || 
					   !(DateUtily.compareDt1Dt2(DateUtily.addHours(posicionDto.getFechaUltimaBusqueda(),
							   							posicionDto.getPeriodoBusqueda()), ahora) == 1)){
						log4j.debug("<searchCandidates> Se ejecuta el search");
						//a nulos
						posicionDto.setFechaCaducidad(null);
						posicionDto.setFechaUltimaBusqueda(null);
						posicionDto.setPeriodoBusqueda(null);
						
						//Se llama el servico de busqueda de candidatos
						posicionDto.setIdEmpresaConf(schedulerDto.getIdEmpresaConf());
						schedulerDto.setMessages(applicantService.searchApplicants(posicionDto));
						
						//Se actualiza la FechaUltimaBusqueda
						posicionDao.updateFechaUltimaBusqueda(posicionDto.getIdPosicion(), ahora);
						
						//Se actualiza el estatus: modificado
						//posicionDao.updateModificado(posicionDto.getIdPosicion(),false);
					}
				}
			}		
		}
		
		log4j.debug("<searchCandidates> Fin... getMessages="+schedulerDto.getMessages());
		return schedulerDto.getMessages();
		
	}	
}