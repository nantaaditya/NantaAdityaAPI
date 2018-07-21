package com.nantaaditya.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.GetSkillCommandResponse;
import com.nantaaditya.model.command.SaveSkillCommandRequest;
import com.nantaaditya.model.command.UpdateSkillCommandRequest;
import com.nantaaditya.model.web.GetSkillWebResponse;
import com.nantaaditya.model.web.SaveSkillWebRequest;
import com.nantaaditya.model.web.UpdateSkillWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.DeleteSkillCommand;
import com.nantaaditya.service.command.GetSkillCommand;
import com.nantaaditya.service.command.SaveSkillCommand;
import com.nantaaditya.service.command.UpdateSkillCommand;
import java.util.Arrays;
import java.util.List;
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
public class SkillControllerTest {

  @InjectMocks
  private SkillController controller;

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
  private SaveSkillWebRequest saveSkillWebRequest;
  private UpdateSkillWebRequest updateSkillWebRequest;
  private List<GetSkillCommandResponse> getSkillCommandResponses;
  private static final String ID = "id";
  private static final String NAME = "name";
  private static final double PERCENTAGE = 100;
  private static final String REQUEST_ID = UUID.randomUUID().toString();
  private static final String SAVE_MESSAGE = "save skill success";
  private static final String UPDATE_MESSAGE = "update skill success";
  private static final String GET_MESSAGE = "get skill success";
  private static final String DELETE_MESSAGE = "delete skill success";

  @Before
  public void setUp() {
    this.mapper = new ObjectMapper();
    this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    this.emptyRequest = generateEmptyRequest();
    this.emptyResponse = generateEmptyResponse();
    this.saveSkillWebRequest = generateSaveSkillWebRequest();
    this.updateSkillWebRequest = generateUpdateSkillWebRequest();
    this.getSkillCommandResponses = generateGetSkillCommandResponses();
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
    mockMvc.perform(post(ApiPath.SKILL)
        .param("requestId", REQUEST_ID)
        .content(mapper.writeValueAsString(saveSkillWebRequest))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    this.verifySave();
  }

  @Test
  public void testUpdate() throws Exception {
    this.mockUpdate();
    mockMvc.perform(put(ApiPath.SKILL)
        .param("requestId", REQUEST_ID)
        .content(mapper.writeValueAsString(updateSkillWebRequest))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    this.verifyUpdate();
  }

  @Test
  public void testGet() throws Exception {
    this.mockConvertResponse();
    this.mockGet();
    mockMvc.perform(get(ApiPath.SKILL)
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyConvertResponse();
    this.verifyGet();
  }

  @Test
  public void testDelete() throws Exception {
    this.mockDelete();
    mockMvc.perform(delete(ApiPath.SKILL + "/{id}", ID)
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
    when(this.helper.response(SaveSkillCommand.class, convertSaveSkillCommandRequest(
        saveSkillWebRequest, new SaveSkillCommandRequest()), REQUEST_ID, SAVE_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, SAVE_MESSAGE, emptyResponse));
  }

  private void mockUpdate() {
    when(this.helper.response(UpdateSkillCommand.class, convertUpdateSkillCommandRequest(
        updateSkillWebRequest, new UpdateSkillCommandRequest()), REQUEST_ID, UPDATE_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, UPDATE_MESSAGE, emptyResponse));
  }

  private void mockGet() {
    when(this.helper.response(GetSkillCommand.class, emptyRequest))
        .thenReturn(getSkillCommandResponses);
  }

  private void mockDelete() {
    when(this.helper.response(DeleteSkillCommand.class, ID, REQUEST_ID, DELETE_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, DELETE_MESSAGE, emptyResponse));
  }

  private void mockConvertResponse() {
    when(mapperHelper.mapToList(getSkillCommandResponses, GetSkillWebResponse.class))
        .thenReturn(Arrays.asList(GetSkillWebResponse.builder().build()));
  }

  private void verifySave() {
    verify(this.helper).response(SaveSkillCommand.class, convertSaveSkillCommandRequest(
        saveSkillWebRequest, new SaveSkillCommandRequest()), REQUEST_ID, SAVE_MESSAGE);
  }

  private void verifyUpdate() {
    verify(this.helper).response(UpdateSkillCommand.class, convertUpdateSkillCommandRequest(
        updateSkillWebRequest, new UpdateSkillCommandRequest()), REQUEST_ID, UPDATE_MESSAGE);
  }

  private void verifyGet() {
    verify(this.helper).response(GetSkillCommand.class, emptyRequest);
  }

  private void verifyDelete() {
    verify(this.helper).response(DeleteSkillCommand.class, ID, REQUEST_ID, DELETE_MESSAGE);
  }

  private void verifyConvertResponse() {
    verify(mapperHelper).mapToList(getSkillCommandResponses, GetSkillWebResponse.class);
  }

  private SaveSkillCommandRequest convertSaveSkillCommandRequest(
      SaveSkillWebRequest webRequest, SaveSkillCommandRequest commandRequest) {
    BeanUtils.copyProperties(webRequest, commandRequest);
    return commandRequest;
  }

  private UpdateSkillCommandRequest convertUpdateSkillCommandRequest(
      UpdateSkillWebRequest webRequest, UpdateSkillCommandRequest commandRequest) {
    BeanUtils.copyProperties(webRequest, commandRequest);
    return commandRequest;
  }

  private EmptyRequest generateEmptyRequest() {
    return EmptyRequest.getInstance();
  }

  private EmptyResponse generateEmptyResponse() {
    return EmptyResponse.getInstance();
  }

  private SaveSkillWebRequest generateSaveSkillWebRequest() {
    return SaveSkillWebRequest.builder()
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
  }

  private UpdateSkillWebRequest generateUpdateSkillWebRequest() {
    return UpdateSkillWebRequest.builder()
        .id(ID)
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
  }

  private List<GetSkillCommandResponse> generateGetSkillCommandResponses() {
    GetSkillCommandResponse response = GetSkillCommandResponse.builder()
        .id(ID)
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
    return Arrays.asList(response);
  }
}
