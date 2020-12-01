package net.tce.cache;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import net.tce.dto.MensajeDto;

public class ThreadExceptionCache {
	
	private  static final ConcurrentHashMap<Long, MensajeDto> chmThreadException = new ConcurrentHashMap<Long, MensajeDto>();

	/**
	 * Adiciona un nuevo string a la lista de parámetros generales 
	 * @param key, compuesto por: Identificador del parámetro
	 * @param value, valor del parámetro
	 * @return
	 */
	public static void put(Long key, MensajeDto value){
		chmThreadException.put(key, value);
	}	
	
	/**
	 * 
	 * @return
	 */
	public static Enumeration<MensajeDto> elemens(){
		return chmThreadException.elements();
	}
	
	
	
	/**
	 * Reemplaza un nuevo string a la lista de parámetros generales 
	 * @param key, compuesto por: Identificador del parámetro
	 * @param value, valor del parámetro a reemplazar
	 * @return
	 */
	public static void replace(Long key, MensajeDto value){
		chmThreadException.replace(key, value);
	}	

	/**
	 *  Obtiene un string dependiendo de su key
	 * @param key, compuesto por: Identificador de la exception
	 * @return lista de objetos MensajeDto
	 */
	public static MensajeDto get(MensajeDto key){
		MensajeDto value = null;
		if(chmThreadException.containsKey(key)){
			value = (MensajeDto)chmThreadException.get(key);
		}
		return value;
	}	
	
	/**
	 * Verifica si el objeto chmThreadException es nulo
	 * @return true ->  null
	 * 		   false -> not null
	 */
	public static boolean isNull(){
		if(chmThreadException == null){
			return true;
		}else{
			return false;
		}
	}	

	/**
	 * Verifica si el objeto chmThreadException está vacío
	 * @return true ->  null
	 * 		   false -> not null
	 */
	public static boolean isEmpty(){
		if(chmThreadException.size() == 0){
			return true;
		}else{
			return false;
		}
	}	

	/**
	 * Se limpia el map
	 */
	public static void clean(){
		chmThreadException.clear();
	}
}
