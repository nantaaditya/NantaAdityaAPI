package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.helper.OneSignalHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveBlogCommandRequest;
import com.nantaaditya.model.web.PushNotificationWebRequest;
import com.nantaaditya.model.web.PushNotificationWebRequest.ContentsWebRequest;
import com.nantaaditya.model.web.PushNotificationWebRequest.HeadingsWebRequest;
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

  @Autowired
  private OneSignalHelper oneSignalHelper;

  @Value("${nanta.blog.host}")
  private String BLOG_HOST;

  @Value("${nanta.robots}")
  private String ROBOTS;

  private static final String ICON =
      "https://static.nantaaditya.com/img/icon256.png";

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(SaveBlogCommandRequest commandRequest) {
    Page page = pageRepository.save(this.generatePage(commandRequest));
    Blog blog = blogRepository.save(this.generateBlog(commandRequest));
    if(commandRequest.isNotification())
      this.postNotification(page, blog);
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

  private void postNotification(Page page, Blog blog){
    oneSignalHelper.createNotification(PushNotificationWebRequest.builder()
        .url(page.getUrl())
        .smallIcon(ICON)
        .largeIcon(ICON)
        .chromeWebIcon(ICON)
        .chromeWebBadge(ICON)
        .bigPicture(blog.getBannerUrl())
        .chromeWebImage(blog.getBannerUrl())
        .chromeBigPicture(blog.getBannerUrl())
        .headings(HeadingsWebRequest.builder()
            .id(blog.getTitle())
            .en(blog.getTitle())
            .build())
        .contents(ContentsWebRequest.builder()
            .id(page.getDescription())
            .en(page.getDescription())
            .build())
        .build());
  }
}
