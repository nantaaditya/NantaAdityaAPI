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
public class SaveBlogWebRequest {

  @NotBlank(message = "title is required")
  private String title;
  @NotBlank(message = "banner is required")
  private String bannerURL;
  private String keywords;
  private String description;
  @NotBlank(message = "post is required")
  private String post;
  private boolean notification;
}
