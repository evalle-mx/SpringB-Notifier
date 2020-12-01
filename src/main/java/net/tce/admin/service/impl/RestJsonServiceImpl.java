package net.tce.admin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.tce.admin.service.RestJsonService;
import net.tce.app.exception.SystemTCEException;
import net.tce.app.rest.ClientOperNoStruc;
import net.tce.app.rest.ClientTransacStruc;
import net.tce.solr.document.SolrDocTopics;

@Service("restJsonService")
public class RestJsonServiceImpl implements RestJsonService {
	//private Logger log4j = Logger.getLogger(RestJsonServiceImpl.class);
	 
	@Autowired
	private ClientOperNoStruc clientOperNoStruc;
	
	@Autowired
	private ClientTransacStruc clientTransacStruc;
	
	
	/**
	 * Envia directamente el mensaje Json desde el cliente y obtiene respuesta del servicio en  mensaje Json
	 * @param inputJson, mensaje json a mandar
	 * @param uriService, es el URI del servicio
	 * @return un mensaje json
	 * @throws SystemTCEException
	 */
	@SuppressWarnings("unchecked")
	public List<SolrDocTopics> serviceRJOperNoStruc(String inputJson, String uriService)
			throws SystemTCEException {
		return (List<SolrDocTopics>)clientOperNoStruc.getObject(inputJson, uriService);
	}

	/**
	 * Envia directamente el mensaje Json desde el cliente y obtiene respuesta del servicio en  mensaje Json
	 * @param inputJson, mensaje json a mandar
	 * @param uriService, es el URI del servicio
	 * @return un mensaje json
	 * @throws SystemTCEException
	 */
	public String serviceRJTransacStruc(String inputJson, String uriService)
			throws SystemTCEException {
		return (String)clientTransacStruc.getObject(inputJson, uriService);
	}

}
