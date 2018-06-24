package com.nantaaditya.configuration;

import com.nantaaditya.model.JwtAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {

  @Bean
  public JwtAuditorAware auditorAware() throws Exception {
    return new JwtAuditorAware();
  }
}
