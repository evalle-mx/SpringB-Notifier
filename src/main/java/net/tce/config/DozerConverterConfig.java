package net.tce.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.tce.util.converter.DozerConverter;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

@Configuration
public class DozerConverterConfig {
	Logger log4j = Logger.getLogger( this.getClass());


	@Bean
    public ConversionServiceFactoryBean converter() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        Set<DozerConverter> converters = new HashSet<DozerConverter>();
        DozerConverter dozerConverter=new DozerConverter();
        DozerBeanMapper mapper = new DozerBeanMapper();
        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/dozer-applicant-mapping.xml");
        myMappingFiles.add("dozer/dozer-catalogos-mapping.xml");
        myMappingFiles.add("dozer/dozer-curriculum-mapping.xml");
        myMappingFiles.add("dozer/dozer-employer-mapping.xml");
        myMappingFiles.add("dozer/dozer-solrdocs-mapping.xml");
        myMappingFiles.add("dozer/dozer-btc-posicion-mapping.xml");
        myMappingFiles.add("dozer/dozer-btc-tracking-mapping.xml");
        myMappingFiles.add("dozer/dozer-tracking-mapping.xml");        
        mapper.setMappingFiles(myMappingFiles);
        dozerConverter.setMapper(mapper);	        
        converters.add(dozerConverter);
        factoryBean.setConverters(converters);
        log4j.debug("converter() -> DozerConverterConfig -> vamos a ver si order:2");

        return factoryBean;
    }
}
