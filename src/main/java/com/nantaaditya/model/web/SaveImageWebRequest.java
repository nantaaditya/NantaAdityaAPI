package com.nantaaditya.model.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class SaveImageWebRequest {

  @NotBlank(message = "name is required")
  private String name;
  @Min(value = 1, message = "minimum 1 px")
  @Max(value = Integer.MAX_VALUE, message = "maximum " + Integer.MAX_VALUE + " px")
  private int width;
  @Min(value = 1, message = "minimum 1 px")
  @Max(value = Integer.MAX_VALUE, message = "maximum " + Integer.MAX_VALUE + " px")
  private int height;
}
