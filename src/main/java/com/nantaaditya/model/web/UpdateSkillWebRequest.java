package com.nantaaditya.model.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
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
public class UpdateSkillWebRequest implements WebRequest {

  @NotBlank(message = "id can not be blank")
  private String id;
  @NotBlank(message = "name is required")
  private String name;
  @Range(min = 0, max = 100, message = "value must be between 0-100")
  private double percentage;
}
