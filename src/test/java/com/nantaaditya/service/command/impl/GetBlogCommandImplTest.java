package com.nantaaditya.service.command.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.Blog;
import com.nantaaditya.entity.Page;
import com.nantaaditya.model.web.GetBlogWebResponse;
import com.nantaaditya.repository.BlogRepository;
import com.nantaaditya.repository.PageRepository;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetBlogCommandImplTest {

  @InjectMocks
  private GetBlogCommandImpl command;

  @Mock
  private BlogRepository blogRepository;

  @Mock
  private PageRepository pageRepository;

  @Before
  public void setUp() {
    ReflectionTestUtils.setField(command, "BLOG_HOST", "http://localhost");
  }

  @Test
  public void test_admin(){
    this.mockFindBlog("ADMIN", constructBlog());
    this.mockFindPage(constructPage());

    List<GetBlogWebResponse> result = command.doExecute("ADMIN");

    this.verifyFindBlog("ADMIN");
    this.verifyFindPage();

    assertEquals("bannerUrl", result.get(0).getBannerURL());
    assertEquals("description", result.get(0).getDescription());
  }

  @Test
  public void test_other(){
    this.mockFindBlog("OTHER", constructBlog());
    this.mockFindPage(constructPage());

    List<GetBlogWebResponse> result = command.doExecute("OTHER");

    this.verifyFindBlog("OTHER");
    this.verifyFindPage();

    assertEquals("bannerUrl", result.get(0).getBannerURL());
    assertEquals("description", result.get(0).getDescription());
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(blogRepository);
    verifyNoMoreInteractions(pageRepository);
  }

  private void mockFindBlog(String client, Blog blog){
    if(client.equals("ADMIN"))
      when(blogRepository.findAll()).thenReturn(Arrays.asList(blog));
    else
      when(blogRepository.findByFlagDeleteFalse()).thenReturn(Arrays.asList(blog));
  }

  private void verifyFindBlog(String client){
    if(client.equals("ADMIN"))
      verify(blogRepository, times(2)).findAll();
    else
      verify(blogRepository, times(2)).findByFlagDeleteFalse();
  }

  private void mockFindPage(Page page){
    when(pageRepository.findAll()).thenReturn(Arrays.asList(page));
  }

  private void verifyFindPage(){
    verify(pageRepository).findAll();
  }

  private Blog constructBlog(){
    Blog blog = new Blog();
    blog.setFlagDelete(false);
    blog.setCreatedBy("author");
    blog.setCreatedDate(new Date());
    blog.setBannerUrl("bannerUrl");
    blog.setTitle("title");
    blog.setTitleId("titleId");
    return blog;
  }

  private Page constructPage(){
    return Page.builder()
        .keywords("keywords")
        .description("description")
        .titleId("titleId")
        .build();
  }

}
