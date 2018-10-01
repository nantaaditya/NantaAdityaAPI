package com.nantaaditya.model.jsonld;

import com.nantaaditya.model.jsonld.inner.Skill;
import java.util.List;
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
public class Skills extends AbstractSystemParameter {
  private List<Skill> skills;
}
