package com.nantaaditya.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveBlogCommandRequest;
import com.nantaaditya.model.web.GetBlogWebResponse;
import com.nantaaditya.model.web.SaveBlogWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.GetBlogCommand;
import com.nantaaditya.service.command.SaveBlogCommand;
import java.util.Arrays;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class BlogControllerTest {

  @InjectMocks
  private BlogController controller;

  @Mock
  private ControllerHelper helper;

  @Mock
  private RestExceptionHandler exceptionHandler;

  @Mock
  private MapperHelper mapperHelper;

  private MockMvc mockMvc;
  private ObjectMapper mapper;

  private static final String REQUEST_ID = UUID.randomUUID().toString();
  private static final String SAVE_MESSAGE = "save blog success";
  private static final String GET_MESSAGE = "get blog success";

  @Before
  public void setUp() {
    this.mapper = new ObjectMapper();
    this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(this.controller)
        .setControllerAdvice(this.exceptionHandler)
        .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
        .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
        .build();
  }

  @Test
  public void testSave() throws Exception{
    SaveBlogWebRequest webRequest = SaveBlogWebRequest.builder()
        .bannerURL("bannerUrl")
        .description("description")
        .keywords("keywords")
        .post("post")
        .title("title")
        .build();
    SaveBlogCommandRequest commandRequest = new SaveBlogCommandRequest();
    BeanUtils.copyProperties(webRequest, commandRequest);
    this.mockSave(webRequest);
    mockMvc.perform(post(ApiPath.BLOG)
        .param("requestId", REQUEST_ID)
        .content(mapper.writeValueAsString(webRequest))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    this.verifySave(commandRequest);
  }

  @Test
  public void testGet() throws Exception {
    this.mockGet();
    mockMvc.perform(get(ApiPath.BLOG)
      .param("requestId", REQUEST_ID)
      .param("client", "client"))
      .andExpect(status().isOk());
    this.verifyGet();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(helper);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockSave(SaveBlogWebRequest webRequest) {
    SaveBlogCommandRequest commandRequest = new SaveBlogCommandRequest();
    BeanUtils.copyProperties(webRequest, commandRequest);
    when(this.helper.response(SaveBlogCommand.class, commandRequest, REQUEST_ID, SAVE_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, SAVE_MESSAGE, EmptyResponse.getInstance()));
  }

  private void verifySave(SaveBlogCommandRequest commandRequest){
    verify(this.helper).response(SaveBlogCommand.class, commandRequest,
        REQUEST_ID, SAVE_MESSAGE);
  }

  private void mockGet(){
    when(this.helper.response(GetBlogCommand.class, "client", REQUEST_ID, GET_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID,
            SAVE_MESSAGE,
            Arrays.asList(GetBlogWebResponse.builder().build())));
  }

  private void verifyGet(){
    verify(this.helper).response(GetBlogCommand.class, "client", REQUEST_ID, GET_MESSAGE);
  }

}
