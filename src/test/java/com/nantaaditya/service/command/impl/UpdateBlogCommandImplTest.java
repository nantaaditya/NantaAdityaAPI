package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.UpdateBlogCommandRequest;
import com.nantaaditya.repository.BlogRepository;
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
public class UpdateBlogCommandImplTest {

  @InjectMocks
  private UpdateBlogCommandImpl command;

  @Mock
  private BlogRepository blogRepository;

  @Before
  public void setUp(){

  }

  @Test
  public void test() {
    this.mockFind(Blog.builder()
        .bannerUrl("bannerUrl")
        .titleId("titleId")
        .title("title")
        .post("post")
        .build());
    this.mockSave();

    EmptyResponse result = command.doExecute(constructBlogCommandRequest());

    this.verifyFind(1);
    this.verifySave(1);
  }

  @Test(expected = EntityNotFoundException.class)
  public void test_error() {
    this.mockFind(null);
    this.mockSave();

    try {
      EmptyResponse result = command.doExecute(constructBlogCommandRequest());
    }catch(Exception e) {
      this.verifyFind(1);
      this.verifySave(0);
      throw e;
    }
  }

  @After
  public void tearDown(){
    verifyNoMoreInteractions(blogRepository);
  }

  private void mockFind(Blog blog){
    when(blogRepository.findByTitleId(anyString()))
        .thenReturn(blog);
  }

  private void mockSave(){
    when(blogRepository.save(any(Blog.class)))
        .thenReturn(Blog.builder().build());
  }

  private void verifyFind(int time){
    verify(blogRepository, times(time)).findByTitleId(anyString());
  }

  private void verifySave(int time){
    verify(blogRepository, times(time)).save(any(Blog.class));
  }

  private UpdateBlogCommandRequest constructBlogCommandRequest(){
    return UpdateBlogCommandRequest.builder()
        .bannerURL("bannerUrl")
        .post("post")
        .titleId("titleId")
        .build();
  }
}
