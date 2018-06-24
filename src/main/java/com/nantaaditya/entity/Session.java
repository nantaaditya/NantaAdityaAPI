package com.nantaaditya.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "N_SESSION",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class Session implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @org.springframework.data.annotation.Id
  private String id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "session_id", nullable = false)
  private String sessionId;

  @Column(name = "hostname", nullable = false)
  private String hostname;

  @CreatedDate
  @Column(name = "created_date", nullable = false)
  private Long createdDate;
}
