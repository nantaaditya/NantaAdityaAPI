package com.nantaaditya.service.command.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.model.web.GetPostWebResponse;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import java.util.Date;
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
@SpringBootTest
public class GetPostCommandImplTest {

  @InjectMocks
  private GetPostCommandImpl command;

  @Mock
  private BlogRepository blogRepository;

  @Mock
  private PageRepository pageRepository;

  @Before
  public void setUp(){

  }

  @Test
  public void test(){
    this.mockFindBlog(constructBlog());
    this.mockFindPage(constructPage());

    GetPostWebResponse result = command.doExecute("id");

    this.verifyFindBlog(1);
    this.verifyFindPage(1);
    this.verifySave(1);
    assertEquals("title", result.getTitle());
    assertEquals("description", result.getDescription());
  }

  @Test(expected = EntityNotFoundException.class)
  public void test_errorBlog(){
    this.mockFindBlog(null);
    this.mockFindPage(null);

    try {
      GetPostWebResponse result = command.doExecute("id");

    }catch (Exception e) {
      this.verifyFindBlog(1);
      this.verifyFindPage(1);
      this.verifySave(0);
      throw e;
    }
  }

  @Test(expected = EntityNotFoundException.class)
  public void test_errorPage(){
    this.mockFindBlog(constructBlog());
    this.mockFindPage(null);

    try {
      GetPostWebResponse result = command.doExecute("id");

    }catch (Exception e) {
      this.verifyFindBlog(1);
      this.verifyFindPage(1);
      this.verifySave(0);
      throw e;
    }
  }

  @After
  public void tearDown(){
    verifyNoMoreInteractions(blogRepository);
    verifyNoMoreInteractions(pageRepository);
  }

  private void mockFindBlog(Blog blog){
    when(blogRepository.findByTitleIdAndFlagDeleteFalse(anyString()))
        .thenReturn(blog);
  }

  private void mockFindPage(Page page){
    when(pageRepository.findByTitleIdAndFlagDeleteFalse(anyString()))
        .thenReturn(page);
  }

  private void verifyFindBlog(int time){
    verify(blogRepository, times(time))
        .findByTitleIdAndFlagDeleteFalse(anyString());
  }

  private void verifyFindPage(int time){
    verify(pageRepository, times(time))
        .findByTitleIdAndFlagDeleteFalse(anyString());
  }

  private void verifySave(int time){
    verify(pageRepository, times(time)).save(any(Page.class));
  }

  private Blog constructBlog(){
    Blog blog = new Blog();
    blog.setTitleId("titleId");
    blog.setTitle("title");
    blog.setCreatedDate(new Date());
    blog.setBannerUrl("bannerUrl");
    blog.setPost("post");
    return blog;
  }

  private Page constructPage(){
    return Page.builder()
        .url("url")
        .keywords("keywords")
        .description("description")
        .counter(1)
        .build();
  }
}
