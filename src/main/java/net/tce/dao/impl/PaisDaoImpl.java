package net.tce.dao.impl;

import java.util.Iterator;
import java.util.List;
import net.tce.dao.PaisDao;
import net.tce.dto.PaisDto;
import net.tce.model.Pais;
import net.tce.util.UtilsTCE;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("paisDao")
public class PaisDaoImpl extends PersistenceGenericDaoImpl<Pais, Object> 
	implements PaisDao {
	
	Logger log4j = Logger.getLogger( this.getClass());

	

	/**
	 * 
	 * @param nombre
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<PaisDto> getPais(String nombre) throws Exception{
		List<PaisDto> lsPaisDto=(List<PaisDto>)getSession().
				createQuery(new StringBuilder().
				append("select new net.tce.dto.PaisDto(").
				append("P.idPais, P.nombre )").
				append(" from Pais as P ").
				append(" where  UPPER(P.nombre) like ").
				append("'%").append(nombre.toUpperCase()).append("%'").
				append(" order by P.nombre").toString()).list();
			
		log4j.debug("getPais() -> lsPaisDto="+lsPaisDto);
		if(lsPaisDto != null && lsPaisDto.size() > 0){
        	float resp;
			Iterator<PaisDto> itPaisDto=lsPaisDto.iterator();
        	while(itPaisDto.hasNext()){
        		PaisDto paisDto=itPaisDto.next();
        		if(paisDto.getNombre() != null){
        			//Se analiza la similitd
        			resp=UtilsTCE.similarity(UtilsTCE.replaceEspecialChars(paisDto.getNombre()).toUpperCase(), 
								UtilsTCE.replaceEspecialChars(nombre).toUpperCase());
        			log4j.debug("getPais() -> cadena="+paisDto.getNombre()+" nombre="+nombre+" similitud="+resp);
        			//No hay similitud
        			if(resp ==  0.0f){
        				itPaisDto.remove();
        			}else{
        				//Se adiciona la ponderacion de similitud, al objeto
        				UtilsTCE.executeReflexion(paisDto,"sortWeigh",resp,null,null);
        			}
        		}else{
        			itPaisDto.remove();
        		}
        	}       	
		}
		return lsPaisDto;
	}
	
	
}
