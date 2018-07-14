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
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "N_SKILL")
public class Skill extends AbstractEntity {

  private String name;
  private double percentage;
}
