package com.nantaaditya.model.command;

import com.nantaaditya.enumerated.MessageStateEnum;
import javax.persistence.Entity;
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
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveMessageCommandRequest {

  private String name;
  private String email;
  private String message;
  private MessageStateEnum state;
}
