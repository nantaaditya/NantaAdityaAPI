package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.model.web.GetPostWebResponse;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.GetPostCommand;
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
public class GetPostCommandImpl extends AbstractCommand<GetPostWebResponse, String> implements
    GetPostCommand {

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private PageRepository pageRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public GetPostWebResponse doExecute(String id) {
    return this.findPost(id);
  }

  private GetPostWebResponse findPost(String id) {
    Blog blog = blogRepository.findByTitleIdAndFlagDeleteFalse(id);
    Page page = pageRepository.findByTitleIdAndFlagDeleteFalse(id);

    if (ObjectUtils.isEmpty(blog) || ObjectUtils.isEmpty(page)) {
      throw new EntityNotFoundException("page not found");
    }

    page.setCounter(page.getCounter() + 1);
    pageRepository.save(page);

    return GetPostWebResponse.builder()
        .author("Nanta Aditya")
        .title(blog.getTitle())
        .titleId(blog.getTitleId())
        .url(page.getUrl())
        .createdDate(blog.getCreatedDate())
        .bannerURL(blog.getBannerUrl())
        .post(blog.getPost())
        .keywords(page.getKeywords())
        .description(page.getDescription())
        .build();
  }

}
