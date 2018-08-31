package com.nantaaditya.repository;

import com.nantaaditya.entity.Image;
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
public interface ImageRepository extends JpaRepository<Image, String> {
  Image findByName(String name);
  void deleteByUrl(String url);
}
