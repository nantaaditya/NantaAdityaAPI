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
public class Post extends AbstractSystemParameter {

  private String headline;
  private String alternativeHeadline;
  private String image;
  private String keywords;
  private String wordcount;
  private String publisher;
  private String url;
  private String datePublished;
  private String dateCreated;
  private String dateModified;
  private String description;
  private String articleBody;
  private Author author;
}
