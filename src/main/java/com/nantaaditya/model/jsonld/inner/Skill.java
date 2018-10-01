package com.nantaaditya.model.jsonld.inner;
// @formatter:off
import com.nantaaditya.model.jsonld.AbstractSystemParameter;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor; /**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill extends AbstractSystemParameter {

  private String name;
  private String value;
}
