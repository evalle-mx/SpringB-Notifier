package net.tce.admin.service;

import net.tce.dto.CurriculumDto;
import net.tce.dto.MasivoDto;

public interface CurriculumService {
	
	Object createFull(CurriculumDto curriculumDto)  throws Exception ;
	String createMasive(MasivoDto cvMsdto)  throws Exception ;
	String createDetail(CurriculumDto curriculumDto) throws Exception;
}
