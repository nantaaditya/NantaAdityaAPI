package com.nantaaditya.configuration.filter;

import com.nantaaditya.model.Credential;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Component
public class RequestFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String threadName = Thread.currentThread().getName();
    try {
      Thread.currentThread().setName(String.format(
          "%1$s_[started:%2$s | user:%3$s | uri:%4$s]_%1$s",
          threadName, timeNow(), user(), uri(request)));
      chain.doFilter(request, response);
    } finally {
      Thread.currentThread().setName(threadName);
    }
  }

  private String timeNow() {
    return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private String user() {
    return Optional.ofNullable(Credential.getUsername()).orElse("SYSTEM");
  }

  private String uri(ServletRequest request) {
    return ((HttpServletRequest) request).getRequestURI();
  }

  @Override
  public void destroy() {}
}
