package net.tce.admin.service;

import java.util.List;

import net.tce.dto.MasivoDto;
import net.tce.dto.VacancyDto;

public interface VacancyService {
	List<VacancyDto> setVacancyPublication(VacancyDto vacancyDto) throws Exception;			
	
	//Se tiene que hacer una reingenieria con la nueva tabla politivaMValor
	/*Object createComplete(VacancyDto vacancyDto);
	Object createMasive(MasivoDto inDto);*/
}
