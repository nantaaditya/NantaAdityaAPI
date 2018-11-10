package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.helper.OneSignalHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.RepublishNotificationCommandRequest;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class RepublishNotificationCommandImplTest {

  @InjectMocks
  private RepublishNotificationCommandImpl command;

  @Mock
  private BlogRepository blogRepository;

  @Mock
  private PageRepository pageRepository;

  @Mock
  private OneSignalHelper oneSignalHelper;

  @Test
  public void test(){
    this.mockFindBlog(false);
    this.mockFindPage(false);

    EmptyResponse result = command.execute(RepublishNotificationCommandRequest.builder()
        .titleId("titleId")
        .build());

    this.verifyFindBlog();
    this.verifyFindPage();
  }

  private void mockFindBlog(boolean isNonActive){
    Blog blog = Blog.builder()
        .bannerUrl("bannerUrl")
        .title("title")
        .build();
    blog.setFlagDelete(isNonActive);
    when(blogRepository.findByTitleIdAndFlagDeleteFalse(anyString()))
        .thenReturn(blog);
  }

  private void mockFindPage(boolean isNonActive){
    Page page = Page.builder()
        .url("url")
        .description("description")
        .build();
    page.setFlagDelete(isNonActive);
    when(pageRepository.findByTitleIdAndFlagDeleteFalse(anyString()))
        .thenReturn(page);
  }

  private void verifyFindBlog(){
    verify(blogRepository).findByTitleIdAndFlagDeleteFalse(anyString());
  }

  private void verifyFindPage(){
    verify(pageRepository).findByTitleIdAndFlagDeleteFalse(anyString());
  }
}
