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
public class CurriculumVitaeWebRequest implements WebRequest{

  @NotBlank(message = "name is required")
  private String name;
  @NotBlank(message = "start is required")
  private String start;
  @NotBlank(message = "end is required")
  private String end;
  private String description;
}
