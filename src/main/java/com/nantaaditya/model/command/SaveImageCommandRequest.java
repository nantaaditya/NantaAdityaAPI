package com.nantaaditya.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
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
public class SaveImageCommandRequest {

  private MultipartFile file;
  @NotBlank(message = "name is required")
  private String name;
  private String url;
  private String imageGroup;
  private int width;
  private int height;
}
