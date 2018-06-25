package com.nantaaditya.configuration;

import com.nantaaditya.properties.DataSourceProperties;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Component
@Configuration
public class ResourceConfiguration extends WebMvcConfigurerAdapter {

  @Autowired
  private DataSourceProperties properties;

  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resource/**")
        .addResourceLocations("file:" + properties.getFilePath() + "/")
        .setCacheControl(CacheControl
            .maxAge(500, TimeUnit.SECONDS));
  }
}
