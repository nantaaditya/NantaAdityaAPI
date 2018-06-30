package com.nantaaditya.model.command;

import javax.persistence.Column;
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
public class CurriculumVitaeCommandRequest implements CommandRequest {

  @Column(name = "name is required")
  private String name;
  @Column(name = "time start is required")
  private String timeStart;
  @Column(name = "time end is required")
  private String timeEnd;
  private String description;
}
