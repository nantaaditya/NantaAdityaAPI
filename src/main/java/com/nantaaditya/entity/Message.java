package com.nantaaditya.entity;

import com.nantaaditya.enumerated.MessageStateEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "N_MESSAGE")
public class Message extends AbstractEntity {

  private String name;
  private String email;
  @Column(columnDefinition = "TEXT")
  private String message;
  @Enumerated(EnumType.STRING)
  private MessageStateEnum state;
}
