package com.nantaaditya.model.command;

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
public class SaveSkillCommandRequest implements CommandRequest {

  private String name;
  private double percentage;
}
