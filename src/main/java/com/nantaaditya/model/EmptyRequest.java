package com.nantaaditya.model;

import com.nantaaditya.model.command.CommandRequest;
import lombok.EqualsAndHashCode;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@EqualsAndHashCode
public class EmptyRequest implements CommandRequest {

  private static final EmptyRequest INSTANCE = new EmptyRequest();

  private EmptyRequest() {
  }

  public static EmptyRequest getInstance() {
    return INSTANCE;
  }
}
