package com.nantaaditya.repository;

import com.nantaaditya.entity.User;
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
public interface UserRepository extends JpaRepository<User, String> {

  User findByUsername(String username);

  User findByUsernameAndPasswordAndFlagDeleteFalse(String username, String password);
}
