package net.tce.config;

import net.tce.assembler.CurriculumAssembler;
import net.tce.assembler.SearchAssembler;
import net.tce.assembler.TrackingAssembler;
import net.tce.assembler.VacancyAssembler;
import net.tce.startup.AppStartUp;
import net.tce.util.DBInterpreter;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class SeveralConfig {
	Logger log4j = Logger.getLogger( this.getClass());


	/**
	 * Libreria para convertir mensajes json a objetos, y visceversa
	 * @return
	 */
	@Bean
	public Gson gson(){
        log4j.debug("gson() -> SeveralConfig -> order:4");
		return new GsonBuilder().disableHtmlEscaping().create();		
	}
	
	/**
	 * Se ejecuta el bean al subir la aplicacion
	 * @return
	 */
	@Bean
	public AppStartUp appStartUp(){
		return new AppStartUp();		
	}
	
	/**
	 * Clases donde se inicializan propiedades de  objetos
	 * Assambler
	 */
	@Bean
	public CurriculumAssembler curriculumAssembler(){
		return new CurriculumAssembler();		
	}
	
	@Bean
	public VacancyAssembler vacancyAssembler(){
		return new VacancyAssembler();		
	}
	
	@Bean
	public SearchAssembler searchAssembler(){
		return new SearchAssembler();		
	}
	
	
	@Bean
	public DBInterpreter dbInterpreter(){
		return new DBInterpreter();		
	}
	

	@Bean
	public TrackingAssembler trackingAssembler(){
		return new TrackingAssembler();	
	}
	
}
