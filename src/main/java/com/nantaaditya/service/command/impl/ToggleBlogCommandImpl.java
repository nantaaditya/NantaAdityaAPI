package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.helper.OneSignalHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.web.PushNotificationWebRequest;
import com.nantaaditya.model.web.PushNotificationWebRequest.ContentsWebRequest;
import com.nantaaditya.model.web.PushNotificationWebRequest.HeadingsWebRequest;
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

  @Autowired
  private OneSignalHelper oneSignalHelper;

  private static final String ICON =
      "https://static.nantaaditya.com/img/icon256.png";

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(String id) {
    Page page = this.togglePage(id);
    Blog blog = this.toggleBlog(id);
    if(!blog.isFlagDelete() && !page.isFlagDelete()) {
      this.postNotification(page, blog);
    }
    return EmptyResponse.getInstance();
  }

  private Page togglePage(String id) {
    Page page = pageRepository.findByTitleId(id);
    if (!ObjectUtils.isEmpty(page)) {
      page.setFlagDelete(!page.isFlagDelete());
      return pageRepository.save(page);
    } else {
      throw new EntityNotFoundException("page not found");
    }
  }

  private Blog toggleBlog(String id) {
    Blog blog = blogRepository.findByTitleId(id);
    if (!ObjectUtils.isEmpty(blog)) {
      blog.setFlagDelete(!blog.isFlagDelete());
      return blogRepository.save(blog);
    } else {
      throw new EntityNotFoundException("blog not found");
    }
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
