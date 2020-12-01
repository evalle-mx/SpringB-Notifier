package net.tce.admin.service;


import java.util.List;

import net.tce.dto.PasswordDto;

public interface HistoricoPasswordService {
	List<PasswordDto> dataConf(PasswordDto historicoPasswordDto);
	
}
