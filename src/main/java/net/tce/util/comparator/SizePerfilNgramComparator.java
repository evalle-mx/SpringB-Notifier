package net.tce.util.comparator;

import java.util.Comparator;
import net.tce.dto.PerfilNgramDto;


public class SizePerfilNgramComparator implements Comparator<Object> {
	
	/**
	 * Compara el tamanio de la palabra
	 * @param o1, primer objeto de PerfilNgramDto a comparar
	 * @param o1, segundo objeto de PerfilNgramDto a comparar
	 * @return -1 --> tamanio objeto1 > tamanio objeto2
	 * 		    1 --> tamanio objeto1 < tamanio objeto2	
	 * 			0 --> tamanio objeto1 = tamanio objeto2	
	 */
    public int compare(Object o1, Object o2) {
        PerfilNgramDto funcionDto1= (PerfilNgramDto)o1;
        PerfilNgramDto funcionDto2= (PerfilNgramDto)o2;
		if(funcionDto1.getDescripcion().length() > funcionDto2.getDescripcion().length()){
	        return -1;
        }else if(funcionDto1.getDescripcion().length() < funcionDto2.getDescripcion().length()){
        	return 1;
        }else{
        	return 0;
        } 
        
    }
}
