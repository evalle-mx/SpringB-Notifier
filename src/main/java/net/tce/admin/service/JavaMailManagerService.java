package net.tce.admin.service;

import net.tce.app.exception.SystemTCEException;
import net.tce.dto.CorreoTceDto;

public interface JavaMailManagerService {
	
//	void sendMail(CorreoTceDto correo)  throws SystemTCEException;
	void sendMailBySES(CorreoTceDto correo)  throws SystemTCEException;

}