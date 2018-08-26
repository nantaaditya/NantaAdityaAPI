package com.nantaaditya.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
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
@Table(name = "N_BLOG", indexes = {@Index(name = "title_id_index",  columnList="titleId", unique = true)})
public class Blog extends AbstractEntity {

  @Column(columnDefinition = "TEXT")
  private String post;
  private String title;
  private String titleId;
  private String bannerUrl;
}
