package com.nantaaditya.configuration.filter;

import com.nantaaditya.model.Credential;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on
public class CredentialFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    String sessionId = Optional.ofNullable(Credential.getSessionId())
        .orElse(String.valueOf(System.currentTimeMillis()) + "-"
            + UUID.randomUUID().toString());

    Credential.setSessionId(sessionId);
    Credential.setHostname(httpServletRequest.getRemoteHost());
    Credential.setRequestId(httpServletRequest.getParameter("requestId"));
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}

