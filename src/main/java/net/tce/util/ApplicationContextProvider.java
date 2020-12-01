package net.tce.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Esta clase se ocupa de proporcionar una instancia del contexto (Spring) corriente
 * @author Goyo
 *
 *
 *	Por ejemplo asi se aplica:
				PaisDao paisDaotc=(PaisDao) ApplicationContextProvider.getBean("paisDao");
 */

@Component
public class ApplicationContextProvider implements ApplicationContextAware{
	
	 private static ApplicationContext CONTEXT;
	 
	    @Override
	    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	        CONTEXT = applicationContext;
	    }
	 
	    /**
	     * Get a Spring bean by type.
	     **/
	    public static <T> T getBean(Class<T> beanClass) {
	        return CONTEXT.getBean(beanClass);
	    }
	 
	    /**
	     * Get a Spring bean by name.
	     **/
	    public static Object getBean(String beanName) {
	        return CONTEXT.getBean(beanName);
	    }
	}
