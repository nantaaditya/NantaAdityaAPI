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
public class ReplyMessageWebRequest {

  @NotBlank(message = "id is required")
  private String id;
  @Email(message = "enter valid email address")
  private String email;
  @NotBlank(message = "subject is required")
  private String subject;
  @NotBlank(message = "message is required")
  private String message;
}
