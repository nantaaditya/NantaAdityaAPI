package com.nantaaditya.repository;

import com.nantaaditya.entity.Page;
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
public interface PageRepository extends JpaRepository<Page, String> {

  Page findByTitleIdAndFlagDeleteFalse(String id);

  Page findByTitleId(String id);

  List<Page> findByFlagDeleteFalse();
}
