package com.nantaaditya.configuration;

import java.util.Arrays;
import java.util.List;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Configuration
public class DozerConfiguration {

  @Bean
  public DozerBeanMapper mapper() throws Exception {
    List<String> mappingFiles = Arrays.asList(
        "dozer-configuration-mapping.xml"
    );

    DozerBeanMapper dozerBean = new DozerBeanMapper();
    dozerBean.setMappingFiles(mappingFiles);
    return dozerBean;
  }

}
