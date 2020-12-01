package net.tce.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
public class EscolaridadCache {
	static Logger log4j = Logger.getLogger("EscolaridadCache".getClass());
 static final ConcurrentHashMap<Long, Byte > chNivelGradoAcademico = new ConcurrentHashMap<Long, Byte>();
 static final ConcurrentHashMap<Long, Byte > chNivelEstatusEscolar = new ConcurrentHashMap<Long, Byte>();	
 
 	/**
 	 * 
 	 * @return
 	 */
 	public static boolean isEmptyNivelGradoAcademico(){
 		return chNivelGradoAcademico.isEmpty();
 	}
	
 	/**
	  * Se adiciona un objeto Byte al map correspondiente
	  * @param key, el id_Grado_Academico 
	  * @param Byte, el id_NivelGradoAcademico
	  */
	 public static void putNivelGradoAcademico(Long key, Byte value){
		 chNivelGradoAcademico.put(key, value);
	 }
	
		 
	 /**
	  * Se verifica si existe el objeto GradoAcademicoValidacionDto en el map correspondiente
	  * @param key, el id_Grado_Academico
	  * @return
	  */
	 public static boolean containsKeyNivelGradoAcademico(Long key){
		 log4j.debug("chNivelGradoAcademico="+chNivelGradoAcademico+" key="+key);
		  return chNivelGradoAcademico.containsKey(key);
		}
	 
	 /**
	  * Se obtiene la lista, dado la llave
	  * @param key, el id_Grado_Academico
	  * @return el id_NivelGradoAcademico
	  */
	 public static Byte  getNivelGradoAcademico(Long key){
				return (Byte )chNivelGradoAcademico.get(key);
	 }
	 
	 
	 /**
	  * Se adiciona un objeto Byte al map correspondiente
	  * @param key, el id_EstatusEscolar
	  * @param Byte, el id_NivelEstatusEscolar
	  */
	 public static void putNivelEstatusEscolar(Long key, Byte value){
		 chNivelEstatusEscolar.put(key, value);
	 }
	
		 
	 /**
	  * Se verifica si existe el objeto GradoAcademicoValidacionDto en el map correspondiente
	  * @param key, el id_EstatusEscolar
	  * @return
	  */
	 public static boolean containsKeyNivelEstatusEscolar(Long key){
			return chNivelEstatusEscolar.containsKey(key);
		}
	 
	 /**
	  * Se obtiene la lista, dado la llave
	  * @param key, el id_EstatusEscolar
	  * @return el id_NivelEstatusEscolar
	  */
	 public static Byte  getNivelEstatusEscolar(Long key){
				return (Byte )chNivelEstatusEscolar.get(key);
	 }
	 
	 /**
	  * 
	  * @return
	  */
	 public static boolean isEmptyNivelEstatusEscolar(){
	 		return chNivelEstatusEscolar.isEmpty();
	 	}
}
