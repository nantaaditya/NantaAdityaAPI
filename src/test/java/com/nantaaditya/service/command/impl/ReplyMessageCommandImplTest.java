package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Message;
import com.nantaaditya.enumerated.MessageStateEnum;
import com.nantaaditya.model.command.ReplyMessageCommandRequest;
import com.nantaaditya.repository.MessageRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class ReplyMessageCommandImplTest {

  @InjectMocks
  private ReplyMessageCommandImpl command;
  @Mock
  private MessageRepository repository;
  @Mock
  public JavaMailSender javaMailSender;

  private static final String EMAIL = "email";
  private static final String MESSAGE = "message";
  private static final String NAME = "name";
  private static final String SUBJECT = "subject";
  private static final MessageStateEnum STATE = MessageStateEnum.READ;

  private Message message;
  private ReplyMessageCommandRequest commandRequest;

  @Before
  public void setUp() {
    this.message = generateMessage();
    this.commandRequest = generateCommandRequest();
    ReflectionTestUtils.setField(command, "fromEmail", "email");
    ReflectionTestUtils.setField(command, "ccEmail", "email");
  }

  @Test
  public void testDoExecute() {
    this.mockFind();

    this.command.doExecute(commandRequest);

    this.verifyFind();
    this.verifySave();
    this.verifyReply();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(repository);
    verifyNoMoreInteractions(javaMailSender);
  }

  private void mockFind() {
    when(repository.findOne(anyString()))
        .thenReturn(message);
  }

  private void verifyFind() {
    verify(repository).findOne(anyString());
  }

  private void verifySave() {
    verify(repository).save(message);
  }

  private void verifyReply(){
    verify(javaMailSender).send(any(SimpleMailMessage.class));
  }

  private Message generateMessage() {
    return Message.builder()
        .email(EMAIL)
        .message(MESSAGE)
        .name(NAME)
        .state(STATE)
        .build();
  }

  private ReplyMessageCommandRequest generateCommandRequest() {
    return ReplyMessageCommandRequest.builder()
        .email(EMAIL)
        .message(MESSAGE)
        .id("1")
        .subject(SUBJECT)
        .build();
  }
}
