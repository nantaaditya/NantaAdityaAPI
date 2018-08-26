package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveBlogCommandRequest;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.SaveBlogCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class SaveBlogCommandImpl extends
    AbstractCommand<EmptyResponse, SaveBlogCommandRequest> implements
    SaveBlogCommand {

  @Autowired
  private PageRepository pageRepository;

  @Autowired
  private BlogRepository blogRepository;

  @Value("${nanta.blog.host}")
  private String BLOG_HOST;

  @Value("${nanta.robots}")
  private String ROBOTS;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(SaveBlogCommandRequest commandRequest) {
    pageRepository.save(this.generatePage(commandRequest));
    blogRepository.save(this.generateBlog(commandRequest));
    return EmptyResponse.getInstance();
  }

  private Page generatePage(SaveBlogCommandRequest commandRequest) {
    String title = this.generateTitle(commandRequest.getTitle());
    return Page.builder()
        .url(BLOG_HOST + this.generateTitle(title))
        .titleId(this.generateTitle(title))
        .robots(ROBOTS)
        .counter(0)
        .keywords(commandRequest.getKeywords())
        .description(commandRequest.getDescription())
        .build();
  }

  private Blog generateBlog(SaveBlogCommandRequest commandRequest) {
    return Blog.builder()
        .bannerUrl(commandRequest.getBannerURL())
        .post(commandRequest.getPost())
        .title(commandRequest.getTitle())
        .titleId(this.generateTitle(commandRequest.getTitle()))
        .build();
  }


  private String generateTitle(String title) {
    return title.replace(" ", "-");
  }
}
