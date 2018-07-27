package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Message;
import com.nantaaditya.enumerated.MessageStateEnum;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.SaveMessageCommandRequest;
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
public class SaveMessageCommandImplTest {

  @InjectMocks
  private SaveMessageCommandImpl command;
  @Mock
  private MessageRepository repository;
  @Mock
  private MapperHelper mapperHelper;

  private static final String EMAIL = "email";
  private static final String MESSAGE = "message";
  private static final String NAME = "name";
  private static final MessageStateEnum STATE = MessageStateEnum.UNREAD;

  private Message message;
  private SaveMessageCommandRequest commandRequest;

  @Before
  public void setUp() {
    this.message = generateMessage();
    this.commandRequest = generateCommandRequest();
  }

  @Test
  public void testDoExecute() {
    this.mockToEntity();

    this.command.doExecute(commandRequest);

    this.verifySave();
    this.verifyToEntity();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(repository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockToEntity() {
    when(mapperHelper.map(commandRequest, Message.class))
        .thenReturn(message);
  }

  private void verifySave() {
    verify(repository).save(message);
  }

  private void verifyToEntity() {
    verify(mapperHelper).map(commandRequest, Message.class);
  }

  private Message generateMessage() {
    return Message.builder()
        .email(EMAIL)
        .message(MESSAGE)
        .name(NAME)
        .state(STATE)
        .build();
  }

  private SaveMessageCommandRequest generateCommandRequest() {
    return SaveMessageCommandRequest.builder()
        .email(EMAIL)
        .message(MESSAGE)
        .name(NAME)
        .state(STATE)
        .build();
  }
}
