package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.helper.OneSignalHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import javax.persistence.EntityNotFoundException;
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
public class ToggleBlogCommandImplTest {

  @InjectMocks
  private ToggleBlogCommandImpl command;

  @Mock
  private BlogRepository blogRepository;

  @Mock
  private PageRepository pageRepository;

  @Mock
  private OneSignalHelper oneSignalHelper;

  private Blog blog;
  private Page page;

  @Before
  public void setUp(){
    blog = Blog.builder()
        .title("title")
        .bannerUrl("bannerURL")
        .build();
    page = Page.builder()
        .url("url")
        .description("description")
        .build();
  }

  @Test
  public void test(){
    this.mockFindPage(Page.builder().build());
    this.mockFindBlog(Blog.builder().build());
    this.mockSaveBlog(false);
    this.mockSavePage(false);

    EmptyResponse result = command.doExecute("id");

    this.verifyFindPage(1);
    this.verifyFindBlog(1);
    this.verifySavePage(1);
    this.verifySaveBlog(1);
  }

  @Test(expected = EntityNotFoundException.class)
  public void test_error(){
    this.mockFindPage(null);
    this.mockFindBlog(Blog.builder().build());

    try{
      EmptyResponse result = command.doExecute("id");
    }catch (Exception e){
      this.verifyFindPage(1);
      this.verifyFindBlog(0);
      this.verifySavePage(0);
      this.verifySaveBlog(0);
      throw e;
    }
  }

  @Test(expected = EntityNotFoundException.class)
  public void test_error2(){
    this.mockFindPage(Page.builder().build());
    this.mockFindBlog(null);

    try{
      EmptyResponse result = command.doExecute("id");
    }catch (Exception e){
      this.verifyFindPage(1);
      this.verifyFindBlog(1);
      this.verifySavePage(1);
      this.verifySaveBlog(0);
      throw e;
    }
  }

  @After
  public void tearDown(){
    verifyNoMoreInteractions(blogRepository);
    verifyNoMoreInteractions(pageRepository);
  }

  private void mockFindPage(Page page){
    when(pageRepository.findByTitleId(anyString()))
        .thenReturn(page);
  }

  private void mockFindBlog(Blog blog){
    when(blogRepository.findByTitleId(anyString()))
        .thenReturn(blog);
  }

  private void verifyFindPage(int time){
    verify(pageRepository, times(time)).findByTitleId(anyString());
  }

  private void verifyFindBlog(int time){
    verify(blogRepository, times(time)).findByTitleId(anyString());
  }

  private void mockSaveBlog(boolean isDelete){
    blog.setFlagDelete(isDelete);
    when(blogRepository.save(any(Blog.class)))
        .thenReturn(blog);
  }

  private void verifySavePage(int time){
    verify(pageRepository, times(time)).save(any(Page.class));
  }

  private void mockSavePage(boolean isDelete){
    page.setFlagDelete(isDelete);
    when(pageRepository.save(any(Page.class)))
        .thenReturn(page);
  }

  private void verifySaveBlog(int time){
    verify(blogRepository, times(time)).save(any(Blog.class));
  }
}
