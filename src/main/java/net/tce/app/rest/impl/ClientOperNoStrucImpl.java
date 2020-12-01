package net.tce.app.rest.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import net.tce.app.exception.SystemTCEException;
import net.tce.app.rest.ClientOperNoStruc;
import net.tce.solr.document.SolrDocTopics;
import net.tce.util.Constante;
import java.util.List;
import com.google.gson.reflect.TypeToken;


@Service("clientOperNoStruc")
public class ClientOperNoStrucImpl implements ClientOperNoStruc{
	Logger log4j = Logger.getLogger( this.getClass());
	
	@Inject
	Gson gson;
	
	@Value("${uri.operational.no.estructured}") String uriOperationalNoEstructured;
	
	private WebTarget targetONS;
	private Response responseONS;
	private StringBuffer sbError;
	
	/**
	 * Constructor que inicia cliente y recurso Web en base a la URL en constantes
	 */
	 @PostConstruct
	 public void initIt()  {
		log4j.info(" ClientOperNoStrucImpl() -> uriOperationalNoEstructured="+uriOperationalNoEstructured);
		targetONS =ClientBuilder.newClient().target(uriOperationalNoEstructured);		
		log4j.info("Se inicia el cliente de los servicios Operativos No Estructurados:" + targetONS.getUri().getHost());
	}
	

	/**
	 * Metodo comun a todos los Clientes Rest para consumir recursos del servicio Operational (OperationalTCE)
	 * supone que los parametros json y uri son validos y no nulos
	 * @param outJson
	 * @param uriService
	 * @return
	 * @throws SystemTCEException
	 */
	public List<SolrDocTopics> getObject(String outJson, String uriService) throws SystemTCEException {
		log4j.debug("<ClientOperNoStrucImpl> uriService :" + uriService+" outJson="+outJson);
		responseONS = targetONS.path(uriService).
	        		request(MediaType.APPLICATION_JSON+Constante.CHARSET).
	        		post(Entity.entity(outJson, 
	        		MediaType.APPLICATION_JSON+Constante.CHARSET),
	        		Response.class);
		log4j.debug("<ClientOperNoStrucImpl> OK ");
		//Se analiza la respuesta
		if (responseONS.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			log4j.debug("<ClientOperNoStrucImpl> Llamada exitosa");
			return gson.fromJson(responseONS.readEntity(String.class),
					new TypeToken<List<SolrDocTopics>>(){}.getType());
			
			//return  gson.fromJson(responseONS.readEntity(String.class),Object.class);
		} else {
			 sbError= new StringBuffer(String.valueOf(responseONS.getStatus())).
						append(Constante.DIVISOR_HTTP).append(Constante.ERROR_LABEL_SERVICE)
						.append(uriService);
				
			 log4j.error("<getJsonFromService> Rest.ERROR -->  respuesta= "+responseONS.readEntity(String.class));
			 log4j.error("<getJsonFromService> Rest.ERROR -->  sbError= "+sbError.toString());
			 
			 throw new SystemTCEException(sbError.toString());
		}
	}

}
