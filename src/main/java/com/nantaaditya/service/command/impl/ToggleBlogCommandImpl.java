package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.ToggleBlogCommand;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class ToggleBlogCommandImpl extends AbstractCommand<EmptyResponse, String> implements
    ToggleBlogCommand {

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private PageRepository pageRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(String id) {
    this.togglePage(id);
    this.toggleBlog(id);
    return EmptyResponse.getInstance();
  }

  private void togglePage(String id) {
    Page page = pageRepository.findByTitleId(id);
    if (!ObjectUtils.isEmpty(page)) {
      page.setFlagDelete(!page.isFlagDelete());
      pageRepository.save(page);
    } else {
      throw new EntityNotFoundException("page not found");
    }
  }

  private void toggleBlog(String id) {
    Blog blog = blogRepository.findByTitleId(id);
    if (!ObjectUtils.isEmpty(blog)) {
      blog.setFlagDelete(!blog.isFlagDelete());
      blogRepository.save(blog);
    } else {
      throw new EntityNotFoundException("blog not found");
    }
  }
}
