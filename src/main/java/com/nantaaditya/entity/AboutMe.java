package com.nantaaditya.entity;

import javax.persistence.Column;
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
@Table(name = "N_ABOUT_ME")
public class AboutMe extends AbstractEntity {

  @Column(columnDefinition = "TEXT")
  private String description;
}
