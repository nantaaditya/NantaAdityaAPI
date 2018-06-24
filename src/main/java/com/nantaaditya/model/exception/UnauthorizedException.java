package com.nantaaditya.model.exception;

import org.springframework.security.core.AuthenticationException;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public class UnauthorizedException extends AuthenticationException {

  public UnauthorizedException(String msg) {
    super(msg);
  }

  public UnauthorizedException(String msg, Throwable cause) {
    super(msg, cause);
  }
}

