package com.nantaaditya.model.web;

import java.util.Date;
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
public class GetPostWebResponse {

  private String title;
  private String titleId;
  private String url;
  private String author;
  private Date createdDate;
  private String bannerURL;
  private String post;
  private String keywords;
  private String description;
}
