package net.tce.admin.service;



public interface SolrService {

	 void threadwriteInSolr(Object pojoEntidad);
	 String writeOnSolrServer(Object pojoEntidad, String idSolr) throws Exception;
	 void writeInSolr(Object pojoEntidad) throws Exception;
}
