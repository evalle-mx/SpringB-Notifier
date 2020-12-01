package net.tce.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import net.tce.task.scheduler.ThreadSingleTaskSchedulerNotification;
import net.tce.task.scheduler.ThreadSingleTaskSchedulerReminder;

@Configuration
@ComponentScan(basePackages = "net.tce.task.scheduler", basePackageClasses = { ThreadSingleTaskSchedulerNotification.class,
		ThreadSingleTaskSchedulerReminder.class})
public class SingleThreadScheduledExecutorConfig {


	 @Bean
	    public ScheduledExecutorService threadSingleTaskScheduler() {
		 	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	        return executor;
	    }
}
