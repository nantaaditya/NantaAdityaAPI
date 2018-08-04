package com.nantaaditya.model.web;

import com.nantaaditya.enumerated.MessageStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class GetMessageWebResponse {

  private String id;
  private String name;
  private String email;
  private String message;
  private MessageStateEnum state;
}
