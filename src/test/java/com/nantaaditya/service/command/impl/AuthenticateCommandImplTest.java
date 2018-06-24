package com.nantaaditya.service.command.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import com.nantaaditya.entity.Session;
import com.nantaaditya.entity.User;
import com.nantaaditya.helper.JwtHelper;
import com.nantaaditya.model.Credential;
import com.nantaaditya.model.command.AuthenticationCommandRequest;
import com.nantaaditya.repository.SessionRepository;
import com.nantaaditya.repository.UserRepository;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class AuthenticateCommandImplTest {

  @InjectMocks
  private AuthenticateCommandImpl authenticateCommand;

  @Mock
  private UserRepository userRepository;

  @Mock
  private SessionRepository sessionRepository;

  @Mock
  private JwtHelper jwtHelper;

  private User user;
  private Session session;
  private AuthenticationCommandRequest commandRequest;
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String HOSTNAME = "localhost";
  private static final String SESSION_ID = UUID.randomUUID().toString();
  private static final String JWT_TOKEN = UUID.randomUUID().toString();
  private static final String ENCRYPTED_PASSWORD = "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8";

  @Before
  public void setUp() {
    user = generateUser();
    session = generateSession();
    commandRequest = generateCommandRequest();

    mockCredential();
  }

  @Test
  public void testDoExecute() {
    mockFindUser();
    mockFindSession();
    mockGenerateJwtToken();

    String result = authenticateCommand.doExecute(commandRequest);
    assertEquals(JWT_TOKEN, result);

    verifyFindUser();
    verifyFindSession(1);
    verifyGenerateJwtToken(1);
    verifyDeleteSession(1);
    verifySaveSession(1);
  }

  @Test(expected = BadCredentialsException.class)
  public void testDoExecuteFailed() {
    mockFindUserNull();

    try {
      authenticateCommand.doExecute(commandRequest);
    } catch (BadCredentialsException e) {
      verifyFindUser();
      verifyFindSession(0);
      verifyGenerateJwtToken(0);
      verifyDeleteSession(0);
      verifySaveSession(0);
      throw e;
    }

  }

  @After
  public void tearDown(){
    verifyNoMoreInteractions(userRepository);
    verifyNoMoreInteractions(sessionRepository);
    verifyNoMoreInteractions(jwtHelper);
  }

  private void mockFindUser() {
    when(userRepository
        .findByUsernameAndPasswordAndFlagDeleteFalse(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(user);
  }

  private void mockFindUserNull() {
    when(userRepository
        .findByUsernameAndPasswordAndFlagDeleteFalse(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(null);
  }

  private void mockFindSession() {
    when(sessionRepository.findByUsername(Mockito.anyString())).thenReturn(session);
  }

  private void mockCredential() {
    Credential.setHostname(HOSTNAME);
    Credential.setSessionId(SESSION_ID);
  }

  private void mockGenerateJwtToken() {
    when(jwtHelper.generateJwtToken(user)).thenReturn(JWT_TOKEN);
  }

  private void verifyFindUser() {
    verify(userRepository)
        .findByUsernameAndPasswordAndFlagDeleteFalse(Mockito.anyString(), Mockito.anyString());
  }

  private void verifyFindSession(int times) {
    verify(sessionRepository, times(times)).findByUsername(Mockito.anyString());
  }

  private void verifyGenerateJwtToken(int times) {
    verify(jwtHelper, times(times)).generateJwtToken(user);
  }

  private void verifyDeleteSession(int times) {
    verify(sessionRepository, times(times)).delete(session);
  }

  private void verifySaveSession(int times) {
    verify(sessionRepository, times(times)).saveAndFlush(session);
  }

  private User generateUser() {
    return User.builder().username(USERNAME).password(ENCRYPTED_PASSWORD).build();
  }

  private Session generateSession() {
    return Session.builder().username(USERNAME).hostname(HOSTNAME).sessionId(SESSION_ID).build();
  }

  private AuthenticationCommandRequest generateCommandRequest() {
    return AuthenticationCommandRequest.builder().username(USERNAME).password(PASSWORD).build();
  }
}
