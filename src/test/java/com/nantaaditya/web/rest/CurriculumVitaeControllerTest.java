package com.nantaaditya.web.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.CurriculumVitaeCommandRequest;
import com.nantaaditya.model.web.CurriculumVitaeWebRequest;
import com.nantaaditya.model.web.CurriculumVitaeWebResponse;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.DeleteCurriculumVitaeCommand;
import com.nantaaditya.service.command.GetCurriculumVitaeCommand;
import com.nantaaditya.service.command.SaveCurriculumVitaeCommand;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
public class CurriculumVitaeControllerTest {

  @InjectMocks
  private CurriculumVitaeController controller;

  @Mock
  private ControllerHelper helper;

  @Mock
  private RestExceptionHandler exceptionHandler;

  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private CurriculumVitaeWebRequest curriculumVitaeWebRequest;
  private CurriculumVitaeWebResponse curriculumVitaeWebResponse;
  private List<CurriculumVitaeWebResponse> curriculumVitaeWebResponses;
  private EmptyRequest emptyRequest;
  private EmptyResponse emptyResponse;
  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String TIME_START = "time start";
  private static final String TIME_END = "time end";
  private static final String DESCRIPTION = "description";
  private static final String REQUEST_ID = UUID.randomUUID().toString();
  private static final String SAVE_SUCCESS_MESSAGE = "save curriculum vitae success";
  private static final String DELETE_SUCCESS_MESSAGE = "delete curriculum vitae success";
  private static final String GET_SUCCESS_MESSAGE = "get curriculum vitae success";

  @Before
  public void setUp() {
    this.mapper = new ObjectMapper();
    this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    this.emptyRequest = generateEmptyRequest();
    this.emptyResponse = generateEmptyResponse();
    this.curriculumVitaeWebResponse = generateCurriculumVitaeWebResponse();
    this.curriculumVitaeWebResponses = generateListCurriculumVitaeWebResponse();
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(this.controller)
        .setControllerAdvice(this.exceptionHandler)
        .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
        .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
        .build();
  }

  @Test
  public void testSave() throws Exception {
    curriculumVitaeWebRequest = generateCurriculumVitaeWebRequest();
    this.mockSave();
    this.mockMvc.perform(post(ApiPath.CURRICULUM_VITAE)
        .param("requestId", REQUEST_ID)
        .content(mapper.writeValueAsString(curriculumVitaeWebRequest))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    this.verifySave(1);
  }

  @Test
  public void testSaveFailed() throws Exception {
    curriculumVitaeWebRequest = generateFailedCurriculumVitaeWebRequest();
    this.mockSave();
    this.mockMvc.perform(post(ApiPath.CURRICULUM_VITAE)
        .param("requestId", REQUEST_ID)
        .content(mapper.writeValueAsString(curriculumVitaeWebRequest))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
    this.verifySave(0);
  }

  @Test
  public void testDelete() throws Exception {
    this.mockDelete();
    this.mockMvc
        .perform(delete(ApiPath.CURRICULUM_VITAE + "/{id}", ID)
            .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyDelete();
  }

  @Test
  public void testGet() throws Exception {
    this.mockGet();
    this.mockMvc
        .perform(get(ApiPath.CURRICULUM_VITAE)
            .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyGet();
  }

  private void mockSave() {
    when(this.helper.response(SaveCurriculumVitaeCommand.class,
        convertCurriculumVitaeWebToCommandRequest(curriculumVitaeWebRequest,
            new CurriculumVitaeCommandRequest()), REQUEST_ID, SAVE_SUCCESS_MESSAGE))
        .thenReturn(ResponseHelper
            .ok(REQUEST_ID, SAVE_SUCCESS_MESSAGE, EmptyResponse.getInstance()));
  }

  private void mockDelete() {
    when(this.helper.response(DeleteCurriculumVitaeCommand.class,
        ID, REQUEST_ID, DELETE_SUCCESS_MESSAGE)).thenReturn(
        ResponseHelper.ok(REQUEST_ID, DELETE_SUCCESS_MESSAGE, EmptyResponse.getInstance()));
  }

  private void mockGet() {
    when(this.helper.response(GetCurriculumVitaeCommand.class,
        EmptyRequest.getInstance(), REQUEST_ID, GET_SUCCESS_MESSAGE)).thenReturn(
        ResponseHelper.ok(REQUEST_ID, GET_SUCCESS_MESSAGE, curriculumVitaeWebResponses));
  }

  private void verifySave(int time) {
    verify(this.helper, times(time)).response(SaveCurriculumVitaeCommand.class,
        convertCurriculumVitaeWebToCommandRequest(curriculumVitaeWebRequest,
            new CurriculumVitaeCommandRequest()), REQUEST_ID, SAVE_SUCCESS_MESSAGE);
  }

  private void verifyDelete() {
    verify(this.helper)
        .response(DeleteCurriculumVitaeCommand.class, ID, REQUEST_ID, DELETE_SUCCESS_MESSAGE);
  }

  private void verifyGet() {
    verify(this.helper)
        .response(GetCurriculumVitaeCommand.class, EmptyRequest.getInstance(), REQUEST_ID,
            GET_SUCCESS_MESSAGE);
  }

  private EmptyRequest generateEmptyRequest() {
    return EmptyRequest.getInstance();
  }

  private EmptyResponse generateEmptyResponse() {
    return EmptyResponse.getInstance();
  }

  private CurriculumVitaeWebRequest generateCurriculumVitaeWebRequest() {
    return CurriculumVitaeWebRequest.builder().name(NAME).timeStart(TIME_START).timeEnd(TIME_END)
        .description(DESCRIPTION).build();
  }

  private CurriculumVitaeWebRequest generateFailedCurriculumVitaeWebRequest() {
    return CurriculumVitaeWebRequest.builder().build();
  }

  private CurriculumVitaeWebResponse generateCurriculumVitaeWebResponse() {
    return CurriculumVitaeWebResponse.builder().name(NAME).timeStart(TIME_START).timeEnd(TIME_END)
        .description(DESCRIPTION).build();
  }

  private List<CurriculumVitaeWebResponse> generateListCurriculumVitaeWebResponse() {
    return Arrays.asList(curriculumVitaeWebResponse);
  }

  private CurriculumVitaeCommandRequest convertCurriculumVitaeWebToCommandRequest(
      CurriculumVitaeWebRequest webRequest, CurriculumVitaeCommandRequest commandRequest) {
    BeanUtils.copyProperties(webRequest, commandRequest);
    return commandRequest;
  }
}
