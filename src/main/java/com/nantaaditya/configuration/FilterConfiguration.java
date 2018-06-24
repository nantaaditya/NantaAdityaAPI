package com.nantaaditya.configuration;

import com.nantaaditya.configuration.filter.CredentialFilter;
import com.nantaaditya.configuration.filter.RootFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class FilterConfiguration {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
        new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/api/**", corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }

  @Bean
  public FilterRegistrationBean credentialFilter() {
    CredentialFilter credentialFilter = new CredentialFilter();
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(credentialFilter);
    filterRegistrationBean.setOrder(1);
    return filterRegistrationBean;
  }

  @Bean
  public FilterRegistrationBean rootFilter() {
    RootFilter rootFilter = new RootFilter();
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(rootFilter);
    filterRegistrationBean.setOrder(2);
    return filterRegistrationBean;
  }

}
