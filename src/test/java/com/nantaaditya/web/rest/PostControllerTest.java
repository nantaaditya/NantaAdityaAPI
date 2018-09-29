package com.nantaaditya.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.UpdateBlogCommandRequest;
import com.nantaaditya.model.web.GetPostWebResponse;
import com.nantaaditya.model.web.UpdateBlogWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.GetPostCommand;
import com.nantaaditya.service.command.ToggleBlogCommand;
import com.nantaaditya.service.command.UpdateBlogCommand;
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
public class PostControllerTest {

  @InjectMocks
  private PostController controller;

  @Mock
  private ControllerHelper helper;

  @Mock
  private RestExceptionHandler exceptionHandler;

  @Mock
  private MapperHelper mapperHelper;

  private MockMvc mockMvc;
  private ObjectMapper mapper;
  UpdateBlogCommandRequest commandRequest;
  UpdateBlogWebRequest webRequest;
  private static final String REQUEST_ID = UUID.randomUUID().toString();
  private static final String UPDATE_MESSAGE = "update post success";
  private static final String GET_MESSAGE = "get post success";
  private static final String TOGGLE_MESSAGE = "toggle blog success";

  @Before
  public void setUp() {
    commandRequest = new UpdateBlogCommandRequest();
    webRequest = UpdateBlogWebRequest.builder()
        .bannerURL("bannerUrl")
        .post("post")
        .titleId("titleId")
        .build();
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
    this.mockUpdate();
    mockMvc.perform(put(ApiPath.POST)
      .param("requestId", REQUEST_ID)
      .content(mapper.writeValueAsString(webRequest))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    this.verifyUpdate();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(helper);
    verifyNoMoreInteractions(mapperHelper);
  }

  @Test
  public void testGet() throws Exception{
    this.mockGet();
    mockMvc.perform(get(ApiPath.POST+"/titleId")
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyGet();
  }

  @Test
  public void testToggle() throws Exception{
    this.mockToggle();
    mockMvc.perform(put(ApiPath.POST+"/toggle/titleId")
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyToggle();
  }

  private void mockUpdate() {
    BeanUtils.copyProperties(webRequest, commandRequest);
    when(this.helper.response(UpdateBlogCommand.class, commandRequest, REQUEST_ID, UPDATE_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, UPDATE_MESSAGE, EmptyResponse.getInstance()));
  }

  private void verifyUpdate() {
    verify(this.helper)
        .response(UpdateBlogCommand.class, commandRequest, REQUEST_ID, UPDATE_MESSAGE);
  }

  private void mockGet(){
    when(this.helper.response(GetPostCommand.class, "titleId", REQUEST_ID, GET_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID,
            UPDATE_MESSAGE,
            GetPostWebResponse.builder().build()));
  }

  private void verifyGet(){
    verify(this.helper).response(GetPostCommand.class, "titleId", REQUEST_ID, GET_MESSAGE);
  }

  private void mockToggle(){
    when(this.helper.response(ToggleBlogCommand.class, "titleId", REQUEST_ID, TOGGLE_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID,
            TOGGLE_MESSAGE,
            EmptyResponse.getInstance()));
  }

  private void verifyToggle(){
    verify(this.helper).response(ToggleBlogCommand.class, "titleId", REQUEST_ID, TOGGLE_MESSAGE);
  }
}
