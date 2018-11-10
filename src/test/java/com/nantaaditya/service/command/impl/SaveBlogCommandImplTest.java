package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.helper.OneSignalHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveBlogCommandRequest;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class SaveBlogCommandImplTest {

  @InjectMocks
  private SaveBlogCommandImpl command;

  @Mock
  private PageRepository pageRepository;

  @Mock
  private BlogRepository blogRepository;

  @Mock
  private OneSignalHelper oneSignalHelper;

  @Before
  public void setUp(){
  }

  @Test
  public void test(){
    this.mockBlog();
    this.mockPage();

    EmptyResponse result = command.doExecute(constructCommandRequest());

    this.verifyBlog();
    this.verifyPage();
  }

  @After
  public void tearDown(){
    verifyNoMoreInteractions(pageRepository);
    verifyNoMoreInteractions(blogRepository);
  }

  private void mockBlog(){
    when(blogRepository.save(any(Blog.class))).thenReturn(Blog.builder().build());
  }

  private void mockPage(){
    when(pageRepository.save(any(Page.class))).thenReturn(Page.builder().build());
  }

  private void verifyBlog(){
    verify(blogRepository).save(any(Blog.class));
  }

  private void verifyPage(){
    verify(pageRepository).save(any(Page.class));
  }

  private SaveBlogCommandRequest constructCommandRequest(){
    return SaveBlogCommandRequest.builder()
        .bannerURL("bannerUrl")
        .description("descrition")
        .keywords("keywords")
        .post("post")
        .title("title")
        .notification(true)
        .build();
  }
}
