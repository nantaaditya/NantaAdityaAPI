package com.nantaaditya.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.SaveImageCommandRequest;
import com.nantaaditya.model.web.GetImageWebResponse;
import com.nantaaditya.model.web.SaveImageWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.GetImageCommand;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
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
public class ImageControllerTest {

  @InjectMocks
  private ImageController controller;

  @Mock
  private ControllerHelper helper;

  @Mock
  private RestExceptionHandler exceptionHandler;

  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private String stringRequest;
  private SaveImageWebRequest webRequest;
  private SaveImageCommandRequest commandRequest;
  private List<GetImageWebResponse> webResponses;
  private MockMultipartFile file;
  private String REQUEST_ID = UUID.randomUUID().toString();
  private String GET_MESSAGE = "get image success";

  @Before
  public void setUp() {
    this.mapper = new ObjectMapper();
    this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    this.stringRequest = generateStringRequest();
    this.webRequest = generateWebRequest();
    this.commandRequest = generateCommandRequest();
    this.webResponses = generateWebResponses();
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(this.controller)
        .setControllerAdvice(this.exceptionHandler)
        .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
        .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
        .build();
  }

  @Test
  public void testGet() throws Exception {
    this.mockGet();
    this.mockMvc.perform(get(ApiPath.IMAGE)
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyGet();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(helper);
  }

  private void mockGet() {
    when(
        helper.response(GetImageCommand.class, EmptyRequest.getInstance(), REQUEST_ID, GET_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, GET_MESSAGE, webResponses));
  }

  private void verifyGet() {
    verify(helper)
        .response(GetImageCommand.class, EmptyRequest.getInstance(), REQUEST_ID, GET_MESSAGE);
  }

  private String generateStringRequest() {
    SaveImageWebRequest request = SaveImageWebRequest.builder()
        .name("name")
        .width(100)
        .height(100)
        .build();
    try {
      return mapper.writeValueAsString(request);
    } catch (Exception e) {
      return null;
    }
  }

  private SaveImageWebRequest generateWebRequest() {
    return SaveImageWebRequest.builder()
        .name("name")
        .width(100)
        .height(100)
        .build();
  }

  private SaveImageCommandRequest generateCommandRequest() {
    return SaveImageCommandRequest.builder()
        .file(file)
        .name("name")
        .url("url")
        .height(100)
        .width(100)
        .build();
  }

  private List<GetImageWebResponse> generateWebResponses() {
    GetImageWebResponse webResponse = GetImageWebResponse.builder()
        .url("url")
        .name("name")
        .id("id")
        .build();

    return Arrays.asList(webResponse);
  }
}
