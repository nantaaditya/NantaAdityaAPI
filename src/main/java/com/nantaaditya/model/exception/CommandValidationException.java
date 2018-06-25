package com.nantaaditya.model.exception;

import java.util.Set;
import javax.validation.ConstraintViolation;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public class CommandValidationException extends RuntimeException {

  private Set<ConstraintViolation<?>> constraintViolations;

  public CommandValidationException(String message) {
    super(message);
  }

  @SuppressWarnings("unchecked")
  public CommandValidationException(Set constraintViolations) {
    this.constraintViolations = (Set<ConstraintViolation<?>>) constraintViolations;
  }

  public Set<ConstraintViolation<?>> getConstraintViolations() {
    return constraintViolations;
  }
}
