package com.nantaaditya.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.enumerated.MessageStateEnum;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.GetMessageCommandResponse;
import com.nantaaditya.model.command.ReplyMessageCommandRequest;
import com.nantaaditya.model.command.SaveMessageCommandRequest;
import com.nantaaditya.model.web.GetMessageWebResponse;
import com.nantaaditya.model.web.ReplyMessageWebRequest;
import com.nantaaditya.model.web.SaveMessageWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.FindMessageCommand;
import com.nantaaditya.service.command.GetMessageCommand;
import com.nantaaditya.service.command.ReplyMessageCommand;
import com.nantaaditya.service.command.SaveMessageCommand;
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
public class MessageControllerTest {

  @InjectMocks
  private MessageController controller;

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
  private SaveMessageWebRequest saveMessageWebRequest;
  private GetMessageWebResponse getMessageWebResponse;
  private GetMessageCommandResponse getMessageCommandResponse;
  private ReplyMessageWebRequest replyMessageWebRequest;

  private static final String REQUEST_ID = UUID.randomUUID().toString();
  private static final String SAVE_MESSAGE = "your message has been sent";
  private static final String REPLY_MESSAGE = "reply message has been sent";

  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String EMAIL = "mine@mail.com";
  private static final String MESSAGE = "message";
  private static final String SUBJECT = "subject";
  private static final MessageStateEnum STATE = MessageStateEnum.UNREAD;


  @Before
  public void setUp() throws JsonProcessingException {
    this.mapper = new ObjectMapper();
    this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    this.emptyRequest = generateEmptyRequest();
    this.emptyResponse = generateEmptyResponse();
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(this.controller)
        .setControllerAdvice(this.exceptionHandler)
        .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
        .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
        .build();
  }

  @Test
  public void testSave() throws Exception {
    saveMessageWebRequest = generateSaveMessageWebRequest();
    this.mockSave();
    mockMvc.perform(post(ApiPath.MESSAGE)
        .param("requestId", REQUEST_ID)
        .content(mapper.writeValueAsString(saveMessageWebRequest))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    this.verifySave();
  }

  @Test
  public void testGet() throws Exception {
    getMessageCommandResponse = generateGetMessageCommandResponse();
    getMessageWebResponse = generateGetMessageWebResponse();
    this.mockConvertResponse();
    this.mockGet();
    mockMvc.perform(get(ApiPath.MESSAGE)
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyConvertResponse();
    this.verifyGet();
  }

  @Test
  public void testFind() throws Exception {
    getMessageCommandResponse = generateGetMessageCommandResponse();
    getMessageWebResponse = generateGetMessageWebResponse();
    this.mockFind();
    mockMvc.perform(get(ApiPath.MESSAGE + "/{id}", "1")
        .param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    this.verifyFind();
  }

  @Test
  public void testReply() throws Exception {
    replyMessageWebRequest = generateReplyMessageWebRequest();
    this.mockReply();
    mockMvc.perform(put(ApiPath.MESSAGE)
        .param("requestId", REQUEST_ID)
        .content(mapper.writeValueAsString(replyMessageWebRequest))
        .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
    this.verifyReply();
  }


  @After
  public void tearDown() {
    verifyNoMoreInteractions(helper);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockSave() {
    when(helper.response(SaveMessageCommand.class, convertSaveWebRequestToCommandRequest(
        saveMessageWebRequest, new SaveMessageCommandRequest()), REQUEST_ID, SAVE_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, SAVE_MESSAGE, emptyResponse));
  }

  private void verifySave() {
    verify(helper).response(SaveMessageCommand.class, convertSaveWebRequestToCommandRequest(
        saveMessageWebRequest, new SaveMessageCommandRequest()), REQUEST_ID, SAVE_MESSAGE);
  }

  private void mockGet() {
    when(helper.response(GetMessageCommand.class, emptyRequest))
        .thenReturn(Arrays.asList(getMessageCommandResponse));
  }

  private void mockConvertResponse() {
    when(mapperHelper
        .mapToList(Arrays.asList(getMessageCommandResponse), GetMessageWebResponse.class))
        .thenReturn(Arrays.asList(getMessageWebResponse));
  }

  private void verifyConvertResponse() {
    verify(mapperHelper)
        .mapToList(Arrays.asList(getMessageCommandResponse), GetMessageWebResponse.class);
  }

  private void verifyGet() {
    verify(helper).response(GetMessageCommand.class, emptyRequest);
  }

  private void mockFind() {
    when(helper.response(FindMessageCommand.class, "1"))
        .thenReturn(getMessageCommandResponse);
  }

  private void verifyFind() {
    verify(helper)
        .response(FindMessageCommand.class, "1");
  }

  private void mockReply() {
    when(helper.response(ReplyMessageCommand.class,
        convertReplyWebRequestToCommandRequest(replyMessageWebRequest,
            new ReplyMessageCommandRequest()), REQUEST_ID, REPLY_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, MESSAGE, emptyResponse));
  }

  private void verifyReply() {
    verify(helper).response(ReplyMessageCommand.class,
        convertReplyWebRequestToCommandRequest(replyMessageWebRequest,
            new ReplyMessageCommandRequest()), REQUEST_ID, REPLY_MESSAGE);
  }

  private EmptyRequest generateEmptyRequest() {
    return EmptyRequest.getInstance();
  }

  private EmptyResponse generateEmptyResponse() {
    return EmptyResponse.getInstance();
  }

  private SaveMessageWebRequest generateSaveMessageWebRequest() {
    return SaveMessageWebRequest.builder()
        .email(EMAIL)
        .message(MESSAGE)
        .name(NAME)
        .build();
  }

  private GetMessageWebResponse generateGetMessageWebResponse() {
    return GetMessageWebResponse.builder()
        .email(EMAIL)
        .id(ID)
        .message(MESSAGE)
        .name(NAME)
        .state(STATE)
        .build();
  }

  private GetMessageCommandResponse generateGetMessageCommandResponse() {
    return GetMessageCommandResponse.builder()
        .email(EMAIL)
        .id(ID)
        .message(MESSAGE)
        .name(NAME)
        .state(STATE)
        .build();
  }

  private ReplyMessageWebRequest generateReplyMessageWebRequest() {
    return ReplyMessageWebRequest.builder()
        .email(EMAIL)
        .id(ID)
        .message(MESSAGE)
        .subject(SUBJECT)
        .build();
  }

  private SaveMessageCommandRequest convertSaveWebRequestToCommandRequest(
      SaveMessageWebRequest webRequest, SaveMessageCommandRequest commandRequest) {
    BeanUtils.copyProperties(webRequest, commandRequest);
    return commandRequest;
  }

  private ReplyMessageCommandRequest convertReplyWebRequestToCommandRequest(
      ReplyMessageWebRequest webRequest, ReplyMessageCommandRequest commandRequest) {
    BeanUtils.copyProperties(webRequest, commandRequest);
    return commandRequest;
  }
}
