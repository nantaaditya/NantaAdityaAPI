package com.nantaaditya.configuration.filter;

import com.nantaaditya.model.JwtAuthenticationToken;
import com.nantaaditya.model.exception.JwtInvalidTokenException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

  public JwtAuthenticationProcessingFilter() {
    super("/**");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    String jwtToken = request.getHeader("Authorization");
    if (StringUtils.isEmpty(jwtToken)) {
      throw new JwtInvalidTokenException("Invalid JWT token");
    }
    return getAuthenticationManager().authenticate(new JwtAuthenticationToken(jwtToken));
  }

  @Override
  protected boolean requiresAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    return true;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response);
  }
}

