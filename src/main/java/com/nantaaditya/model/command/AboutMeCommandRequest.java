package com.nantaaditya.model.command;

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
public class AboutMeCommandRequest implements CommandRequest {

  @NotBlank(message = "description is required")
  private String description;
}
