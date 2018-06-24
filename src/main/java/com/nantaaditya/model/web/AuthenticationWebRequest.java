package com.nantaaditya.model.web;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Data
@Builder
public class AuthenticationWebRequest implements WebRequest {

  @NotBlank(message = "username is required")
  private String username;
  @NotBlank(message = "password is required")
  private String password;
}
