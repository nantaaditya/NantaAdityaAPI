package com.nantaaditya.model.exception;

import org.springframework.security.core.AuthenticationException;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public class JwtInvalidTokenException extends AuthenticationException {

  public JwtInvalidTokenException(String msg) {
    super(msg);
  }
}
