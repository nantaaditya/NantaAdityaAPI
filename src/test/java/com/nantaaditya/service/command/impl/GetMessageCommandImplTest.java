package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Message;
import com.nantaaditya.enumerated.MessageStateEnum;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.GetMessageCommandResponse;
import com.nantaaditya.repository.MessageRepository;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class GetMessageCommandImplTest {

  @InjectMocks
  private GetMessageCommandImpl command;
  @Mock
  private MessageRepository repository;
  @Mock
  private MapperHelper mapperHelper;

  private static final String EMAIL = "email";
  private static final String MESSAGE = "message";
  private static final String NAME = "name";
  private static final MessageStateEnum STATE = MessageStateEnum.UNREAD;

  private Message message;
  private GetMessageCommandResponse commandResponse;

  @Before
  public void setUp() {
    this.message = generateMessage();
    this.commandResponse = generateCommandResponse();
  }

  @Test
  public void testDoExecute() {
    this.mockGet();
    this.mockToResponse();

    this.command.doExecute(EmptyRequest.getInstance());

    this.verifyGet();
    this.verifyToResponse();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(repository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockGet() {
    when(repository.findAllByOrderByStateDesc())
        .thenReturn(Arrays.asList(message));
  }

  private void mockToResponse() {
    when(mapperHelper.mapToList(Arrays.asList(message),
        GetMessageCommandResponse.class))
        .thenReturn(Arrays.asList(commandResponse));
  }

  private void verifyGet() {
    verify(repository).findAllByOrderByStateDesc();
  }

  private void verifyToResponse() {
    verify(mapperHelper).mapToList(Arrays.asList(message),
        GetMessageCommandResponse.class);
  }

  private Message generateMessage() {
    return Message.builder()
        .email(EMAIL)
        .message(MESSAGE)
        .name(NAME)
        .state(STATE)
        .build();
  }

  private GetMessageCommandResponse generateCommandResponse() {
    return GetMessageCommandResponse.builder()
        .email(EMAIL)
        .id("1")
        .message(MESSAGE)
        .name(NAME)
        .state(STATE)
        .build();
  }
}
