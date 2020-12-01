package net.tce.util.comparator;

import java.util.Comparator;
import net.tce.model.ExperienciaLaboral;
import net.tce.util.DateUtily;

public class ExpLabDateComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		ExperienciaLaboral experienciaLaboral1 = (ExperienciaLaboral)o1;
		ExperienciaLaboral experienciaLaboral2 = (ExperienciaLaboral)o2; 
	       
		if(experienciaLaboral1.getFechaInicio() == null){
			experienciaLaboral1.setFechaInicio(DateUtily.creaFecha(
										String.valueOf(experienciaLaboral1.getAnioInicio()), 
										String.valueOf(experienciaLaboral1.getMesByMesInicio().getIdMes()), 
										String.valueOf(experienciaLaboral1.getDiaInicio())));
		}
		if(experienciaLaboral2.getFechaInicio() == null){
			experienciaLaboral2.setFechaInicio(DateUtily.creaFecha(
										String.valueOf(experienciaLaboral2.getAnioInicio()), 
										String.valueOf(experienciaLaboral2.getMesByMesInicio().getIdMes()), 
										String.valueOf(experienciaLaboral2.getDiaInicio())));
		}
		
	        return -experienciaLaboral1.getFechaInicio().
	                compareTo(experienciaLaboral2.getFechaInicio());
	}

}
