package com.nantaaditya.configuration;

import com.nantaaditya.configuration.filter.CredentialFilter;
import com.nantaaditya.configuration.filter.RootFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class FilterConfiguration {

  @Value("${nanta.allowed.origin}")
  private String ALLOWED_ORIGIN;

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
        new UrlBasedCorsConfigurationSource();
    String [] allowedOrigin = this.getAllowedOrigin(ALLOWED_ORIGIN);
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    for (String origin: allowedOrigin) {
      corsConfiguration.addAllowedOrigin(origin);
    }
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

  private String[] getAllowedOrigin(String data){
    return data.split(";");
  }
}
