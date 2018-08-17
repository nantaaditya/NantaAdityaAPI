package com.nantaaditya.model.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
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
public class SaveMessageWebRequest {

  @NotBlank(message = "name is required")
  private String name;
  @Email(message = "enter valid email address")
  private String email;
  @NotBlank(message = "message is required")
  private String message;
  @NotBlank(message = "please verify you're human")
  private String captchaResponse;
}
