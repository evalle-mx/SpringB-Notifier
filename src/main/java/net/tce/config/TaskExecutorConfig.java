package net.tce.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class TaskExecutorConfig {
	Logger log4j = Logger.getLogger( this.getClass());

	
	@Bean
	public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor(){
		return new SimpleAsyncTaskExecutor();
		
	}
	
}
