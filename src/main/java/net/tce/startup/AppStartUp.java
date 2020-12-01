package net.tce.startup;


import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import net.tce.cache.AvisoCache;
import net.tce.cache.EmpresaConfCache;
import net.tce.cache.ParametrosCache;
import net.tce.dao.AvisoDao;
import net.tce.dao.EmpresaConfDao;
import net.tce.dto.AvisoDto;
import net.tce.dto.EmpresaConfDto;
import net.tce.dto.SchedulerDto;
import net.tce.model.Aviso;
import net.tce.model.EmpresaConf;
import net.tce.task.service.ReclassifyDocService;
import net.tce.task.service.SchedulerClassifiedDocService;
import net.tce.util.Constante;

/**
 * Clase que funciona como StartUp para inicializar condiciones necesarias al sistema
 * @author Goyo
 *
 */
public class AppStartUp {
	Logger log4j = Logger.getLogger( this.getClass());
	
	
	@Autowired
	private  ReclassifyDocService reclassifyDocService;
	
	@Autowired
	private SchedulerClassifiedDocService schedulerClassifiedDocService;		
	
	@Autowired
	private EmpresaConfDao empresaConfDao;
	
	@Autowired
	private AvisoDao avisofDao;

		
	/**
	 * Se ejecuta el metodo despues de la inyección de dependencias de Spring 
	 */
	@PostConstruct
	public void initIt() {
		log4j.debug("<TCEStartUp,initIt>  reclassifyDocService="+reclassifyDocService+
				" empresaConfDao="+empresaConfDao+
				" EmpresaConfCache="+EmpresaConfCache.isEmpty()); 

		//si es nulo el objeto_cache(ChmGeneral), hay que obtenerlo
	    log4j.debug("<TCEStartUp,initIt> Verificando si ParametrosCache.chmGeneral existe");
	    if(ParametrosCache.isEmptyChmGeneral()){
		    log4j.debug("<TCEStartUp,initIt> ParametrosCache.chmGeneral no existe, asignado valores");
		    //Se obtienen los parametros generales
		    SchedulerDto schedulerDto = new SchedulerDto();
		    schedulerDto.setIdTipoProceso(Constante.TIPO_PROCESO_REMODEL_CLASS.longValue());	    
		    log4j.info("<TCEStartUp,initIt> currentModel :" + schedulerClassifiedDocService.getCurrentModelVersion(schedulerDto));
		    log4j.info("<TCEStartUp,initIt> previousModel :" + schedulerClassifiedDocService.getPreviousModelVersion());
		    log4j.info("<TCEStartUp,initIt> newModel :" + schedulerClassifiedDocService.getNewModelVersion());

	    }else{
		    log4j.debug("<TCEStartUp,initIt> ParametrosCache.chmGeneral ya existe");	    	
	    }
		
	   
	    
	    //Se obtienen los datos de empresaconf y se ponen en cache
	    //si es nulo el objeto_cache(EmpresaConf), hay que obtenerlo
	    if(EmpresaConfCache.isEmpty()){
	    	List<EmpresaConf> lsEmpresaConf=empresaConfDao.findAll();
		    log4j.debug("<TCEStartUp,initIt> lsEmpresaConf="+lsEmpresaConf);
			if(lsEmpresaConf != null && lsEmpresaConf.size() > 0){
				Iterator<EmpresaConf> ltEmpresaConf=lsEmpresaConf.iterator();
				while(ltEmpresaConf.hasNext()){
					EmpresaConf empresaConf=ltEmpresaConf.next();
					EmpresaConfCache.put(empresaConf.getIdEmpresaConf(),
										new EmpresaConfDto(empresaConf.getIdEmpresaConf(),
										empresaConf.getEmpresa().getIdEmpresa(),
										empresaConf.getConf().getIdConf()) );
				}
			    log4j.info("%%%  SE OBTIENEN DE LA BASE DE DATOS, LOS OBJETOS DE EMPRESA_CONF SATISFACTORIAMENTE  %%%" );
			}else{
				log4j.fatal("No hay información en la tabla EMPRESA_CONF");
			}	
	    }
	    
	    //Se obtienen los objetos Aviso y se ponen en cache
	    if(AvisoCache.isEmpty()){
	    	List<Aviso> lsAviso=avisofDao.findAll();
	    	log4j.debug("<TCEStartUp,initIt> lsAviso="+lsAviso);
	    	if(lsAviso != null && lsAviso.size() > 0){
				Iterator<Aviso> ltAviso=lsAviso.iterator();
				while(ltAviso.hasNext()){
					Aviso aviso=ltAviso.next();
					AvisoCache.put(aviso.getClaveAviso(),
									new AvisoDto(aviso.getIdAviso(),
									aviso.getClaveAviso(),null,null) );
				}
			    log4j.info("%%%  SE OBTIENEN DE LA BASE DE DATOS, LOS OBJETOS DE AVISO SATISFACTORIAMENTE  %%%" );
			}else{
				log4j.fatal("No hay información en la tabla AVISO");
			}	
	    	
	    }
	    
	    //Se tiene que crear un demonio ya que se presenta un error al mandar el documento a Solr
	   /* try {
		   
		    log4j.info("%%% SE MANDAN LAS POSICIONES Y CV'S : PERSONAS Y EMPRESAS, A SOLR, PARA RECLASIFICARLOS %%%");
			reclassifyDocService.forwardDocumentsPublished();
		} catch (Exception e) {
			log4j.error("Error al reenvian posiciones y cvs(Personas y Empresas) para su reclasificacion"+e.toString());
			e.printStackTrace();
			throw new SystemTCEException("Error al reenvian posiciones y cvs(Personas y Empresas) para su reclasificacion: "+e);		
		}*/	
	       
	}
	
}
