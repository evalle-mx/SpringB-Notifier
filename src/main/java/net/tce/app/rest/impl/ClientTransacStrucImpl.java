package net.tce.app.rest.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.tce.app.exception.SystemTCEException;
import net.tce.app.rest.ClientTransacStruc;
import net.tce.util.Constante;

@Service("clientTransacStruc")
public class ClientTransacStrucImpl implements ClientTransacStruc {
	Logger log4j = Logger.getLogger( this.getClass());
	
	@Inject
	Gson gson;
	
	@Value("${uri.transactional.estructured}")
	private String uriTransactionalEestructired;

	WebTarget targetTS;
	Response responseTS;
	
	 @PostConstruct
	 public void initIt()  {
		log4j.info(" ClientOperNoStrucImpl() -> uriTransactionalEestructired="+uriTransactionalEestructired);
		targetTS =ClientBuilder.newClient().target(uriTransactionalEestructired);		
		log4j.info("Se inicia el cliente de los servicios Transaccionales Estructurados:" + targetTS.getUri().getHost());
	}

	/**
	 * Metodo comun a todos los Clientes Rest para consumir recursos de los servicios TransactionalStructured
	 * supone que los parametros json y uri son validos y no nulos
	 * @param outJson
	 * @param uriService
	 * @return
	 * @throws SystemTCEException
	 */
	public String getObject(String outJson, String uriService) throws SystemTCEException {
		log4j.debug("<ClientTransacStrucImpl> uriService :" + uriService);
		responseTS = targetTS.path(uriService).
	        		request(MediaType.APPLICATION_JSON+Constante.CHARSET).
	        		post(Entity.entity(outJson, 
	        		MediaType.APPLICATION_JSON+Constante.CHARSET),
	        		Response.class);
		log4j.debug("<ClientTransacStrucImpl> OK");
		//Se analiza la respuesta
		if (responseTS.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			log4j.debug("<ClientTransacStrucImpl> Llamada exitosa");
			return  responseTS.readEntity(String.class).toString();
		} else {
			 log4j.error("ERROR -->  responseTS= "+responseTS.readEntity(String.class));
			 throw new SystemTCEException(String.valueOf(responseTS.getStatus()), 
					 					  Constante.ERROR_LABEL_SERVICE.concat(uriService));
		}
	}
}
