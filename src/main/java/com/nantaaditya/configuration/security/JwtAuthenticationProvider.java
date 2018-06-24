package com.nantaaditya.configuration.security;

import com.nantaaditya.helper.JwtHelper;
import com.nantaaditya.model.Credential;
import com.nantaaditya.model.JwtAuthenticationToken;
import com.nantaaditya.model.JwtUserDetail;
import com.nantaaditya.model.exception.JwtInvalidTokenException;
import com.nantaaditya.model.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Component
@Slf4j
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
  @Autowired
  private JwtHelper jwtHelper;
  private boolean isAuthorized = false;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {}

  @Override
  protected UserDetails retrieveUser(String username,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
    String jwtToken = jwtAuthenticationToken.getToken();
    Claims claims = this.jwtHelper.parseJwtToken(jwtToken);

    if (claims == null) {
      throw new JwtInvalidTokenException("Invalid JWT token");
    }

    if (StringUtils.isEmpty(claims.getSubject())) {
      throw new JwtInvalidTokenException("Invalid JWT token");
    }

    try {
      Credential.setUsername(claims.getSubject());
      Credential.setSessionId(String.valueOf(claims.get("sessionId")));
      this.isAuthorized = this.jwtHelper.isAuthorized();
    } catch (Exception e) {
      throw new UnauthorizedException(e.getMessage());
    }

    if (!this.isAuthorized) {
      throw new UnauthorizedException("Unauthorized api access");
    }

    return new JwtUserDetail(claims.getSubject(), jwtToken);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
