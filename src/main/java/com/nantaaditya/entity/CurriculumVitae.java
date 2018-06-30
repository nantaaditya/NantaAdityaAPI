package com.nantaaditya.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
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
@Entity
@Table(name = "N_EDUCATION")
public class CurriculumVitae extends AbstractEntity {

  private String name;
  private String timeStart;
  private String timeEnd;
  private String description;
}
