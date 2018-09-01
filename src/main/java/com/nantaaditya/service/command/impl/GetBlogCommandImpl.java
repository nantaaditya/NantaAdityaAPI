package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.model.web.GetBlogWebResponse;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.GetBlogCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Autowired
  private PageRepository pageRepository;

  @Value("${nanta.blog.host}")
  private String BLOG_HOST;

  @Override
  public List<GetBlogWebResponse> doExecute(String client) {
    Map<String, Blog> blogs = this.convertBlogToMap(this.findBlog(client));
    Map<String, Page> pages = this.convertPageToMap(this.findPage());
    return this.toResponse(client, blogs, pages);
  }

  private List<Blog> findBlog(String client) {
    if (client.equals("ADMIN")) {
      return blogRepository.findAll();
    } else {
      return blogRepository.findByFlagDeleteFalse();
    }
  }

  private List<Page> findPage() {
    return pageRepository.findAll();
  }

  private Map<String, Blog> convertBlogToMap(List<Blog> blogs) {
    return blogs.stream().collect(Collectors.toMap(Blog::getTitleId, item -> item));
  }

  private Map<String, Page> convertPageToMap(List<Page> pages) {
    return pages.stream().collect(Collectors.toMap(Page::getTitleId, item -> item));
  }

  private List<GetBlogWebResponse> toResponse(String client, Map<String, Blog> blogs,
      Map<String, Page> pages) {
    List<Blog> blogList = this.findBlog(client);
    List<GetBlogWebResponse> webResponses = new ArrayList<>();

    blogList.stream().forEach(blog -> {
      Blog b = blogs.get(blog.getTitleId());
      Page p = pages.get(blog.getTitleId());
      webResponses.add(GetBlogWebResponse.builder()
          .bannerURL(blog.getBannerUrl())
          .status(blog.isFlagDelete())
          .title(blog.getTitle())
          .titleId(blog.getTitleId())
          .url(BLOG_HOST.concat(blog.getTitleId()))
          .description(p.getDescription())
          .keywords(p.getKeywords())
          .build());
    });
    return webResponses;
  }
}
