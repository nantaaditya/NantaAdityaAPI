package com.nantaaditya.web.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.AboutMeCommandRequest;
import com.nantaaditya.model.web.AboutMeWebRequest;
import com.nantaaditya.model.web.AboutMeWebResponse;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.FindAboutMeCommand;
import com.nantaaditya.service.command.UpdateAboutMeCommand;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on
@Slf4j
@RunWith(SpringRunner.class)
public class AboutMeControllerTest {

  @InjectMocks
  private AboutMeController controller;

  @Mock
  private ControllerHelper helper;

  @Mock
  private RestExceptionHandler exceptionHandler;

  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private AboutMeWebRequest aboutMeWebRequest;
  private AboutMeWebResponse aboutMeWebResponse;
  private EmptyRequest emptyRequest;
  private EmptyResponse emptyResponse;
  private static final String DESCRIPTION = "description";
  private static final String REQUEST_ID = UUID.randomUUID().toString();
  private static final String GET_SUCCESS_MESSAGE = "get about me";
  private static final String UPDATE_SUCCESS_MESSAGE = "update about me success";

  @Before
  public void setUp() {
    mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    emptyRequest = generateEmptyRequest();
    emptyResponse = generateEmptyResponse();
    mockMvc = MockMvcBuilders
        .standaloneSetup(this.controller)
        .setControllerAdvice(this.exceptionHandler)
        .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
        .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
        .build();
  }

  @Test
  public void testUpdate() throws Exception {
    aboutMeWebRequest = generateAboutMeWebRequest();
    mockUpdate();
    MvcResult result = mockMvc.perform(put(ApiPath.ABOUT_ME)
        .content(mapper.writeValueAsString(aboutMeWebRequest))
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "asdadasdassd")
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk())
        .andReturn();
    assertUpdate(result);
    verifyUpdate(1);
  }

  @Test
  public void testUpdateFailed() throws Exception {
    aboutMeWebRequest = generateFailedAboutMeWebRequest();
    mockUpdate();
    mockMvc.perform(put(ApiPath.ABOUT_ME).param("requestId", REQUEST_ID)
        .content(mapper.writeValueAsString(aboutMeWebRequest))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
    verifyUpdate(0);
  }

  @Test
  public void testGet() throws Exception {
    aboutMeWebResponse = generateAboutMeWebResponse();
    mockGet();
    mockMvc.perform(get(ApiPath.ABOUT_ME).param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    verifyGet();
  }

  private void mockUpdate() {
    when(helper.response(UpdateAboutMeCommand.class,
        convertAboutMeWebToCommandRequest(aboutMeWebRequest,
            new AboutMeCommandRequest()), REQUEST_ID, UPDATE_SUCCESS_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, UPDATE_SUCCESS_MESSAGE, emptyResponse));
  }

  private void mockGet() {
    when(helper.response(FindAboutMeCommand.class,
        EmptyRequest.getInstance(), REQUEST_ID, GET_SUCCESS_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, GET_SUCCESS_MESSAGE, aboutMeWebResponse));
  }

  private void assertUpdate(MvcResult result) throws IOException {
    String responseString = result.getResponse().getContentAsString();
    Response<String> response = mapper.readValue(responseString, Response.class);

    assertEquals(Integer.valueOf(200), response.getCode());
    assertEquals(REQUEST_ID, response.getRequestId());
    assertEquals(UPDATE_SUCCESS_MESSAGE, response.getMessage());
    assertTrue(response.getSuccess());
  }

  private void verifyUpdate(int time) {
    verify(helper, times(time)).response(UpdateAboutMeCommand.class,
        convertAboutMeWebToCommandRequest(aboutMeWebRequest, new AboutMeCommandRequest()),
        REQUEST_ID, UPDATE_SUCCESS_MESSAGE);
  }

  private void verifyGet() {
    verify(helper).response(FindAboutMeCommand.class, EmptyRequest.getInstance(),
        REQUEST_ID, GET_SUCCESS_MESSAGE);
  }

  private EmptyRequest generateEmptyRequest() {
    return EmptyRequest.getInstance();
  }

  private EmptyResponse generateEmptyResponse() {
    return EmptyResponse.getInstance();
  }

  private AboutMeWebRequest generateAboutMeWebRequest() {
    return AboutMeWebRequest.builder().description(DESCRIPTION).build();
  }

  private AboutMeWebRequest generateFailedAboutMeWebRequest() {
    return AboutMeWebRequest.builder().build();
  }

  private AboutMeWebResponse generateAboutMeWebResponse() {
    return AboutMeWebResponse.builder().description(DESCRIPTION).build();
  }

  private AboutMeCommandRequest convertAboutMeWebToCommandRequest(
      AboutMeWebRequest webRequest, AboutMeCommandRequest commandRequest) {
    BeanUtils.copyProperties(webRequest, commandRequest);
    return commandRequest;
  }
}
