package net.tce.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.tce.dto.PerfilNgramDto;

public class HabilidadesPosicionCache {

	 static final ConcurrentHashMap<Long, List<PerfilNgramDto> > chmHabilidadesPosicion = new ConcurrentHashMap<Long, List<PerfilNgramDto> >();
	 
	 /**
	  * 
	  * @param key
	  * @param perfilNgramDto
	  */
	 public static void put(Long key, List<PerfilNgramDto>   value){
		 chmHabilidadesPosicion.put(key, value);
	 }
	
	 /**
	  * Reemplaza el objeto dado la llave
	  * @param key
	  * @param value
	  */
	 public static void replace(Long key, List<PerfilNgramDto>   value){
		 chmHabilidadesPosicion.replace(key, value);
	 }
	 
	 /**
	  * 
	  * @param key
	  * @return
	  */
	 public static boolean containsKey(Long key){
			return chmHabilidadesPosicion.containsKey(key);
		}
	 
	 /**
	  * Se obtiene la lista, dado la llave
	  * @param key
	  * @return
	  */
	 public static List<PerfilNgramDto>  get(Long key){
				return (List<PerfilNgramDto> )chmHabilidadesPosicion.get(key);
	 }
	 
	 /**
	  * Se clona la lista de objetos PerfilNgramDto
	  * @param key, llave del objeto
	  * @return lista clonada
	  */
	 public static List<PerfilNgramDto>  getClone(Long key){
		 List<PerfilNgramDto> lsOrg= (List<PerfilNgramDto> )chmHabilidadesPosicion.get(key);
		 List<PerfilNgramDto> clone = new ArrayList<PerfilNgramDto>(lsOrg.size());
		 for(PerfilNgramDto item: lsOrg) clone.add((PerfilNgramDto) item.clone());
		 return clone;
		
		
	 }
	 
	 
}
