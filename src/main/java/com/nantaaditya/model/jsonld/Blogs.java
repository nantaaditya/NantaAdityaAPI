package com.nantaaditya.model.jsonld;

import com.nantaaditya.model.jsonld.inner.Author;
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
public class Blogs extends AbstractSystemParameter {
  private String url;
  private String name;
  private String description;
  private String createdDate;
  private Author author;
}
