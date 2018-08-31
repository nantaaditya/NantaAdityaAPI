package com.nantaaditya.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.GetProjectCommandResponse;
import com.nantaaditya.model.command.SaveProjectCommandRequest;
import com.nantaaditya.model.web.GetProjectWebResponse;
import com.nantaaditya.model.web.SaveProjectWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.DeleteProjectCommand;
import com.nantaaditya.service.command.GetProjectCommand;
import com.nantaaditya.service.command.SaveProjectCommand;
import java.util.Arrays;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RunWith(SpringRunner.class)
public class ProjectControllerTest {

  @InjectMocks
  private ProjectController controller;

  @Mock
  private ControllerHelper helper;

  @Mock
  private RestExceptionHandler exceptionHandler;

  @Mock
  private MapperHelper mapperHelper;

  private MockMvc mockMvc;
  private ObjectMapper mapper;

  private EmptyRequest emptyRequest;
  private EmptyResponse emptyResponse;
  private SaveProjectWebRequest webRequest;
  private SaveProjectCommandRequest commandRequest;
  private GetProjectCommandResponse commandResponse;
  private static final String ID = "id";
  private static final String URL = "url";
  private static final String NAME = "name";
  private static final String IMAGE_URL = "image_url";
  private static final String REQUEST_ID = UUID.randomUUID().toString();
  private static final String SAVE_MESSAGE = "save project success";
  private static final String GET_MESSAGE = "get project success";
  private static final String DELETE_MESSAGE = "delete project success";

  @Before
  public void setUp() throws JsonProcessingException {
    this.mapper = new ObjectMapper();
    this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    this.emptyRequest = generateEmptyRequest();
    this.emptyResponse = generateEmptyResponse();
    this.webRequest = generateSaveProjectWebRequest();
    this.commandRequest = generaSaveProjectCommandRequest();
    this.commandResponse = generateGetProjectCommandResponse();
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(this.controller)
        .setControllerAdvice(this.exceptionHandler)
        .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
        .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
        .build();
  }

  @Test
  public void testSave() throws Exception {
    this.mockSave();
    this.mockMvc.perform(post(ApiPath.PROJECT)
        .param("requestId", REQUEST_ID)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(mapper.writeValueAsString(webRequest)))
        .andExpect(status().isOk());
    this.verifySave();
  }

  @Test
  public void testGet() throws Exception {
    this.mockConvertResponse();
    this.mockGet();
    this.mockMvc.perform(get(ApiPath.PROJECT)
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyConvertResponse();
    this.verifyGet();
  }

  @Test
  public void testDelete() throws Exception {
    this.mockDelete();
    this.mockMvc.perform(delete(ApiPath.PROJECT + "/{id}", ID)
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyDelete();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(helper);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockSave() {
    when(helper.response(SaveProjectCommand.class,
        convertToCommandRequest(webRequest), REQUEST_ID, SAVE_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, SAVE_MESSAGE, emptyResponse));
  }

  private void mockGet() {
    when(helper.response(GetProjectCommand.class, emptyRequest))
        .thenReturn(Arrays.asList(commandResponse));
  }

  private void mockDelete() {
    when(helper.response(DeleteProjectCommand.class,
        ID, REQUEST_ID, DELETE_MESSAGE)).thenReturn(
        ResponseHelper.ok(REQUEST_ID, DELETE_MESSAGE, emptyResponse));
  }

  private void mockConvertResponse() {
    when(mapperHelper.mapToList(Arrays.asList(commandResponse), GetProjectWebResponse.class))
        .thenReturn(Arrays.asList(GetProjectWebResponse.builder().build()));
  }

  private void verifySave() {
    verify(helper).response(SaveProjectCommand.class, convertToCommandRequest(webRequest),
        REQUEST_ID, SAVE_MESSAGE);
  }

  private void verifyGet() {
    verify(helper).response(GetProjectCommand.class, emptyRequest);
  }

  private void verifyDelete() {
    verify(helper).response(DeleteProjectCommand.class, ID, REQUEST_ID, DELETE_MESSAGE);
  }

  private void verifyConvertResponse() {
    verify(mapperHelper).mapToList(Arrays.asList(commandResponse), GetProjectWebResponse.class);
  }

  private SaveProjectCommandRequest convertToCommandRequest(
      SaveProjectWebRequest webRequest) {
    SaveProjectCommandRequest commandRequest = SaveProjectCommandRequest.builder().build();
    BeanUtils.copyProperties(webRequest, commandRequest);
    return commandRequest;
  }

  private EmptyRequest generateEmptyRequest() {
    return EmptyRequest.getInstance();
  }

  private EmptyResponse generateEmptyResponse() {
    return EmptyResponse.getInstance();
  }

  private SaveProjectWebRequest generateSaveProjectWebRequest() {
    return SaveProjectWebRequest.builder()
        .name(NAME)
        .url(URL)
        .build();
  }

  private SaveProjectCommandRequest generaSaveProjectCommandRequest() {
    return SaveProjectCommandRequest.builder()
        .name(NAME)
        .url(URL)
        .imageURL(IMAGE_URL)
        .build();
  }

  private GetProjectCommandResponse generateGetProjectCommandResponse() {
    return GetProjectCommandResponse
        .builder()
        .id("id")
        .imageURL(IMAGE_URL)
        .name(NAME)
        .url(URL)
        .build();
  }
}
