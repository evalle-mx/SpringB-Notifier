package net.tce.admin.service;

import net.tce.dto.DocumentoClasificacionDto;

public interface DocumentoClasificacionService {
	
	
	Object loadTokens(String dto)throws Exception;
	
	String classifyByLot(DocumentoClasificacionDto docClasificacionDto)throws Exception;

}
