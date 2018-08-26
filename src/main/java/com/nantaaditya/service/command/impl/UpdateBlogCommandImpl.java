package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.UpdateBlogCommandRequest;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.UpdateBlogCommand;
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
public class UpdateBlogCommandImpl extends
    AbstractCommand<EmptyResponse, UpdateBlogCommandRequest> implements
    UpdateBlogCommand {

  @Autowired
  private BlogRepository blogRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(UpdateBlogCommandRequest commandRequest) {
    this.update(findOne(commandRequest.getTitleId()), commandRequest);
    return EmptyResponse.getInstance();
  }

  private void update(Blog blog, UpdateBlogCommandRequest commandRequest) {
    if (ObjectUtils.isEmpty(blog)) {
      throw new EntityNotFoundException("post data not found");
    }

    blog.setBannerUrl(commandRequest.getBannerURL());
    blog.setPost(commandRequest.getPost());
    blogRepository.save(blog);
  }

  private Blog findOne(String id) {
    return blogRepository.findByTitleId(id);
  }

}
