package com.nantaaditya.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Data
@EqualsAndHashCode
public class EmptyResponse {

  private static final EmptyResponse INSTANCE = new EmptyResponse();

  private EmptyResponse() {}

  public static EmptyResponse getInstance() {
    return INSTANCE;
  }
}
