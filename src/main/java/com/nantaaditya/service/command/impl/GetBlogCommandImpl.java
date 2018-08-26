package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.model.web.GetBlogWebResponse;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.GetBlogCommand;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class GetBlogCommandImpl extends
    AbstractCommand<List<GetBlogWebResponse>, String> implements
    GetBlogCommand {

  @Autowired
  private BlogRepository blogRepository;


  @Override
  public List<GetBlogWebResponse> doExecute(String client) {
    return this.toResponse(this.findAll(client));
  }

  private List<Blog> findAll(String client) {
    if (client.equals("ADMIN")) {
      return blogRepository.findAll();
    } else {
      return blogRepository.findByFlagDeleteFalse();
    }
  }

  private List<GetBlogWebResponse> toResponse(List<Blog> blogs) {
    return blogs.stream()
        .map(r -> new GetBlogWebResponse(r.getTitle(), r.getTitleId(),
            r.isFlagDelete(), r.getBannerUrl()))
        .collect(Collectors.toList());
  }
}
