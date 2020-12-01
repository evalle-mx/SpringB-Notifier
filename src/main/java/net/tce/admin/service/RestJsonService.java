package net.tce.admin.service;

import java.util.List;

import net.tce.app.exception.SystemTCEException;
import net.tce.solr.document.SolrDocTopics;

public interface RestJsonService {
	List<SolrDocTopics> serviceRJOperNoStruc(String inputJson, String uriService) throws  SystemTCEException;
	Object serviceRJTransacStruc(String inputJson, String uriService) throws  SystemTCEException;
}
