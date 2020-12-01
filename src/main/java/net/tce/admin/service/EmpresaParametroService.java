package net.tce.admin.service;

//import org.json.JSONArray;

import net.tce.dto.EmpresaParametroDto;

public interface EmpresaParametroService {
	
	Object get(EmpresaParametroDto empresaParametroDto,boolean usarCache)throws Exception;
	Object getExt(EmpresaParametroDto empresaParametroDto)throws Exception;
	Object create(EmpresaParametroDto empresaParametroDto)throws Exception;
	Object update(EmpresaParametroDto empresaParametroDto)throws Exception;
	Object delete(EmpresaParametroDto empresaParametroDto)throws Exception;
	Object updateMultiple(String jsonArray);
		//JSONArray arrayDto);
	Object reloadCache(EmpresaParametroDto empresaParametroDto)throws Exception;
}
