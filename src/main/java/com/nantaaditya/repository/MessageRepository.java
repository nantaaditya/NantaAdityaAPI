package com.nantaaditya.repository;

import com.nantaaditya.entity.Message;
import java.util.List;
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
public interface MessageRepository extends JpaRepository<Message, String> {
  List<Message> findAllByOrderByStateDesc();
}
