package com.nantaaditya.repository;

import com.nantaaditya.entity.Blog;
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
public interface BlogRepository extends JpaRepository<Blog, String> {

  Blog findByTitleIdAndFlagDeleteFalse(String titleId);

  Blog findByTitleId(String id);

  List<Blog> findByFlagDeleteFalse();
}
