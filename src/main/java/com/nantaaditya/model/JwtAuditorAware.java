package com.nantaaditya.model;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public class JwtAuditorAware implements AuditorAware<String> {

  @Override
  public String getCurrentAuditor() {
    return Optional.ofNullable(Credential.getUsername()).orElse("SYSTEM");
  }
}
