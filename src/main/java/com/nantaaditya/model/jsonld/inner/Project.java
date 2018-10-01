package com.nantaaditya.model.jsonld.inner;

import com.nantaaditya.model.jsonld.AbstractSystemParameter;
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
public class Project extends AbstractSystemParameter {

  private String name;
  private String image;
  private String url;
}
