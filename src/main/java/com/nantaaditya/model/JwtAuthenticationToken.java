package com.nantaaditya.model;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Data
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

  private String token;

  public JwtAuthenticationToken(String token) {
    super(null, null);
    this.token = token;
  }
}
