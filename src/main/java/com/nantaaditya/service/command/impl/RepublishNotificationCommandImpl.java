package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.helper.OneSignalHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.RepublishNotificationCommandRequest;
import com.nantaaditya.model.web.PushNotificationWebRequest;
import com.nantaaditya.model.web.PushNotificationWebRequest.ContentsWebRequest;
import com.nantaaditya.model.web.PushNotificationWebRequest.HeadingsWebRequest;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import com.nantaaditya.service.command.RepublishNotificationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class RepublishNotificationCommandImpl implements RepublishNotificationCommand {

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private PageRepository pageRepository;

  @Autowired
  private OneSignalHelper oneSignalHelper;

  private static final String ICON =
      "https://static.nantaaditya.com/img/icon256.png";

  @Override
  public EmptyResponse execute(RepublishNotificationCommandRequest commandRequest) {
    Blog blog = this.findBlog(commandRequest.getTitleId());
    Page page = this.findPage(commandRequest.getTitleId());
    if(!ObjectUtils.isEmpty(blog) && !ObjectUtils.isEmpty(page))
      this.postNotification(page, blog);
    return EmptyResponse.getInstance();
  }

  private Blog findBlog(String titleId){
    return blogRepository.findByTitleIdAndFlagDeleteFalse(titleId);
  }

  private Page findPage(String titleId){
    return pageRepository.findByTitleIdAndFlagDeleteFalse(titleId);
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
