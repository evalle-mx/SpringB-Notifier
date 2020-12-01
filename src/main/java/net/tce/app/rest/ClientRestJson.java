package net.tce.app.rest;

import net.tce.app.exception.SystemTCEException;

public interface ClientRestJson {
	Object getObject(String inputJson, String uriService) throws SystemTCEException;
}
