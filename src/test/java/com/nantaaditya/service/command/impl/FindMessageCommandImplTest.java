package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Message;
import com.nantaaditya.enumerated.MessageStateEnum;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.GetMessageCommandResponse;
import com.nantaaditya.repository.MessageRepository;
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
public class FindMessageCommandImplTest {

  @InjectMocks
  private FindMessageCommandImpl command;
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
  public void testDoExecute(){
    this.mockFind();
    this.mockToResponse();

    this.command.doExecute("1");

    this.verifyFind();
    this.verifySave();
    this.verifyToResponse();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(repository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockFind() {
    when(repository.findOne(anyString())).thenReturn(message);
  }

  private void mockToResponse() {
    when(mapperHelper.map(message, GetMessageCommandResponse.class))
        .thenReturn(commandResponse);
  }

  private void verifyFind() {
    verify(repository).findOne(anyString());
  }

  private void verifyToResponse() {
    verify(mapperHelper).map(message, GetMessageCommandResponse.class);
  }

  private void verifySave() {
    verify(repository).save(message);
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
