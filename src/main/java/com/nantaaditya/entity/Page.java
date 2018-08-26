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
@Table(name = "N_PAGE")
public class Page extends AbstractEntity {
  private int counter;
  private String url;
  private String titleId;
  private String robots;
  private String keywords;
  private String description;
}
