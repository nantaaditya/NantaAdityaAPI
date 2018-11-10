package com.nantaaditya.configuration;

import com.nantaaditya.configuration.filter.JwtAuthenticationProcessingFilter;
import com.nantaaditya.configuration.security.JwtAuthenticationEntryPoint;
import com.nantaaditya.configuration.security.JwtAuthenticationProvider;
import com.nantaaditya.configuration.security.JwtAuthenticationSuccessHandler;
import com.nantaaditya.properties.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  public JwtAuthenticationProcessingFilter authenticationProcessingFilter() throws Exception {
    JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter =
        new JwtAuthenticationProcessingFilter();
    jwtAuthenticationProcessingFilter.setAuthenticationManager(this.authenticationManager());
    jwtAuthenticationProcessingFilter
        .setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
    return jwtAuthenticationProcessingFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(this.jwtAuthenticationProvider);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/*", "/nanta-aditya-api/**", "/resource/**", "/static/**",
            "/swagger-ui*", "/webjars/springfox-swagger-ui/**", "/swagger-resources/**",
            "/configuration/**", "/v2/api-docs", "favicon.ico", "/WEB-INF/jsp/**", "/css/**",
            "/js/**", "/img/**")
        .antMatchers(HttpMethod.POST, ApiPath.LOGIN)
        .antMatchers(HttpMethod.POST, ApiPath.MESSAGE)
        .antMatchers(HttpMethod.GET, ApiPath.BLOG)
        .antMatchers(HttpMethod.GET, ApiPath.SKILL)
        .antMatchers(HttpMethod.GET, ApiPath.PROJECT)
        .antMatchers(HttpMethod.GET, ApiPath.ABOUT_ME)
        .antMatchers(HttpMethod.GET, ApiPath.BLOG)
        .antMatchers(HttpMethod.GET, ApiPath.CURRICULUM_VITAE)
        .antMatchers(HttpMethod.GET, ApiPath.POST+ "/*")
        .antMatchers(HttpMethod.OPTIONS, ApiPath.API+"/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
        .authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(this.authenticationProcessingFilter(),
        UsernamePasswordAuthenticationFilter.class);
    http.headers().cacheControl();
  }
}
