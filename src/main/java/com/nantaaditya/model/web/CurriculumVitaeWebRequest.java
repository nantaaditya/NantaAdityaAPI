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
public class CurriculumVitaeWebRequest implements WebRequest {

  @NotBlank(message = "name is required")
  private String name;
  @NotBlank(message = "time is required")
  private String timeStart;
  @NotBlank(message = "time timeEnd is required")
  private String timeEnd;
  private String description;
}
