package net.tce.task.service.impl;

import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.tce.admin.service.SolrService;
import net.tce.cache.ThreadExceptionCache;
import net.tce.dao.EmpresaDao;
import net.tce.dao.PersonaDao;
import net.tce.dao.PosicionDao;
import net.tce.model.Empresa;
import net.tce.model.Persona;
import net.tce.model.Posicion;
import net.tce.task.service.ReclassifyDocService;

/**
 * 
 * @author dothr
 *
 */
@Transactional
@Service("reclassifyDocService")
public class ReclassifyDocServiceImpl implements ReclassifyDocService{

	Logger log4j = Logger.getLogger( this.getClass());

	@Autowired
	PersonaDao personaDao;
	
	@Autowired
	private SolrService solrService;
	
	@Autowired
	private EmpresaDao empresaDao;
	
	@Autowired
	private PosicionDao posicionDao;
	
	@Autowired
	private SimpleAsyncTaskExecutor asynchTaskExecutor;

	
	/**
	 * Genera un hilo para el proceso de reclasificacion
	 */
/*	public void threadReclassify() {
		log4j.debug("<SolrService> threadPublication.... ");
		
		log4j.debug("Ejecuta hilo (ASynchTaskExecutor) para escritura en Solr [writeInSolrPublication] ");
		
		if (this.asynchTaskExecutor != null) {
			this.asynchTaskExecutor.execute(new Runnable() {
				public void run() {
                	try {
                		log4j.debug("$$$%% El hilo de la tarea "+Thread.currentThread().getId() );
                			reclassify();
					} catch (Exception e) {
						log4j.error("Error grave al reclasificar en Solr, numHilo:"+
									Thread.currentThread().getId()+"--> error:"+e.getMessage()+
									" toString:"+e.toString());
						//TODO, enviar notificación de Error fatal a administrador
						log4j.debug("Enviar Notificación de Error fatal a Administrador>>>>>>>>>>>");
						e.printStackTrace();
					}
                	log4j.debug("$$$%% El hilo de la tarea "+Thread.currentThread().getId()+" termina ");
                }
			});
		}
		log4j.debug("regresando true");
		//return true;
	}*/
	
	
	/**
	 * Manda los objetos publicados(personas, empresas y posiciones) a 
	 * solr para su clafsificacion e indexación
	 * @throws Exception 
	 * 
	 */
	
	public  void forwardDocumentsPublished() throws Exception {
		log4j.debug("%&$ forwardDocumentsPublished  --> ENTRA ");
		//Clasificar empresas
		/*List<Empresa> lsEmpresa=empresaDao.getEnterpriseUnclassified();
		log4j.debug("%&$ forwardDocumentsPublished -> lsEmpresa="+lsEmpresa);
		if(lsEmpresa != null && lsEmpresa.size() > 0){
			Iterator<Empresa> itEmpresa=lsEmpresa.iterator();
			while(itEmpresa.hasNext()){
				solrService.writeInSolr(itEmpresa.next());			
			}
			log4j.debug("%&$ forwardDocumentsPublished -> termina con empresas");
		}*/
		//Clasificar personas
		List<Persona> lsPersona= personaDao.getPersonasSinClasificar();
		log4j.debug("%&$ forwardDocumentsPublished() -> lsPersona="+lsPersona);
		if(lsPersona != null && lsPersona.size() > 0){
			Iterator<Persona> itPersona=lsPersona.iterator();
			while(itPersona.hasNext()){
				solrService.threadwriteInSolr(itPersona.next());	
				
				//si hubo un error fatal ya no se mandan docs
				if(!ThreadExceptionCache.isEmpty()){ 
					log4j.error("forwardDocumentsPublished() -> Error grave al publicar en Solr:"+
							ThreadExceptionCache.elemens().nextElement().getMessage());
					break;
				}
			}
			log4j.debug("%&$ forwardDocumentsPublished -> termina con personas");
		}
		//Clasificar Posiciones
		/*List<Posicion>  lsPosicion=posicionDao.getPositionUnclassified();
		log4j.debug("%&$ forwardDocumentsPublished -> lsPosicion="+lsPosicion);
		if(lsPosicion != null && lsPosicion.size() > 0){
			Iterator<Posicion> itPosicion=lsPosicion.iterator();
			while(itPosicion.hasNext()){
				solrService.writeInSolr(itPosicion.next());			
			}
			log4j.debug("%&$ forwardDocumentsPublished -> termina con posicion");
		}*/
	}

}
