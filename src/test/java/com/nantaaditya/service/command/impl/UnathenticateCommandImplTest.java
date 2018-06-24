package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.Session;
import com.nantaaditya.model.Credential;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.repository.SessionRepository;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class UnathenticateCommandImplTest {

  @InjectMocks
  private UnauthenticateCommandImpl unauthenticateCommand;

  @Mock
  private SessionRepository sessionRepository;

  private Session session;
  private static final String USERNAME = "username";
  private static final String SESSION_ID = UUID.randomUUID().toString();

  @Before
  public void setUp() {
    session = generateSession();
    mockCredential();
  }

  @Test
  public void testDoExecute() {
    mockFindSession();
    unauthenticateCommand.doExecute(EmptyRequest.getInstance());
    verifyFindSession();
    verifyDeleteSession();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(sessionRepository);
  }

  private void mockFindSession() {
    when(sessionRepository.findByUsernameAndSessionId(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(session);
  }

  private void mockCredential() {
    Credential.setUsername(USERNAME);
    Credential.setSessionId(SESSION_ID);
  }

  private void verifyFindSession() {
    verify(sessionRepository).findByUsernameAndSessionId(Mockito.anyString(), Mockito.anyString());
  }

  private void verifyDeleteSession(){
    verify(sessionRepository).delete(session);
  }
  private Session generateSession() {
    return Session.builder().username(USERNAME).sessionId(SESSION_ID).build();
  }

}
