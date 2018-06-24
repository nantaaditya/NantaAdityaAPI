package com.nantaaditya.repository;

import com.nantaaditya.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

  Session findByUsername(String username);

  Session findByUsernameAndSessionIdAndCreatedDateGreaterThan(String username, String sessionId,
      Long createdDate);

  Session findByUsernameAndSessionId(String username, String sessionId);
}
