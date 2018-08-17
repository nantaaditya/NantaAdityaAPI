package com.nantaaditya.model.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationWebRequest implements WebRequest {

  @NotBlank(message = "username is required")
  private String username;
  @NotBlank(message = "password is required")
  private String password;
  @NotBlank(message = "please verify you're human")
  private String captchaResponse;
}
