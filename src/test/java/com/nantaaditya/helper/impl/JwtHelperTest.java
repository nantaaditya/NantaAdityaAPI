package com.nantaaditya.helper.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.Session;
import com.nantaaditya.entity.User;
import com.nantaaditya.model.Credential;
import com.nantaaditya.properties.DataSourceProperties;
import com.nantaaditya.properties.SessionProperties;
import com.nantaaditya.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RunWith(SpringRunner.class)
public class JwtHelperTest {

  @InjectMocks
  private JwtHelperImpl jwtHelper;

  @Mock
  private DataSourceProperties sourceProperties;

  @Mock
  private SessionProperties sessionProperties;

  @Mock
  private SessionRepository sessionRepository;

  @Mock
  private TimeHelper timeHelper;

  private User user;
  private String jwtToken;
  private Session session;
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String HOSTNAME = "hostname";
  private static final Long SESSION_EXPIRED_TIME = 600L;
  private static final String ID = UUID.randomUUID().toString();
  private static final String SESSION_ID = UUID.randomUUID().toString();

  @Before
  public void setUp() {
    mockCredential();
    mockGetCurrentTimeEpoch();
    mockGetJwtSecretKey();
    mockGetSessionExpiredTime();

    user = generateUser();
    jwtToken = jwtHelper.generateJwtToken(user);
  }

  @Test
  public void testParseJwtToken() {
    jwtHelper.parseJwtToken(jwtToken);
    verifyGetJwtSecretKey(2);
  }

  @Test
  public void testParseJwtTokenNull() {
    Claims result = jwtHelper.parseJwtToken(UUID.randomUUID().toString());
    assertNull(result);
    verifyGetJwtSecretKey(2);
  }

  @Test
  public void tesIsAuthorized() {
    session = generateSession();
    mockFindSession();
    Boolean result = jwtHelper.isAuthorized();
    assertTrue(result.booleanValue());
    verifyFindSession();
    verifyGetJwtSecretKey(1);
    verifyGetCurrentTimeEpoch();
    verifyGetSessionExpiredTime();
  }

  @Test
  public void testIsAuthorizedFalse() {
    mockFindExpiredSession();
    Boolean result = jwtHelper.isAuthorized();
    assertFalse(result.booleanValue());
    verifyFindSession();
    verifyGetJwtSecretKey(1);
    verifyGetCurrentTimeEpoch();
    verifyGetSessionExpiredTime();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteraction();
  }

  private void mockCredential() {
    Credential.setSessionId(SESSION_ID);
    Credential.setUsername(USERNAME);
  }

  private void mockGetCurrentTimeEpoch() {
    when(timeHelper.getCurrentTimeEpoch()).thenReturn(1000L);
  }

  private void mockGetJwtSecretKey() {
    when(sourceProperties.getJwtSecretKey()).thenReturn(UUID.randomUUID().toString());
  }

  private void mockGetSessionExpiredTime() {
    when(sessionProperties.getSessionExpiredTimeInMillis()).thenReturn(500L);
  }

  private void mockFindSession() {
    when(sessionRepository
        .findByUsernameAndSessionIdAndCreatedDateGreaterThan(Mockito.anyString(),
            Mockito.anyString(), Mockito.anyLong())).thenReturn(session);
  }

  private void mockFindExpiredSession() {
    when(sessionRepository
        .findByUsernameAndSessionIdAndCreatedDateGreaterThan(Mockito.anyString(),
            Mockito.anyString(), Mockito.anyLong())).thenReturn(null);
  }

  private void verifyGetCurrentTimeEpoch() {
    verify(timeHelper).getCurrentTimeEpoch();
  }

  private void verifyGetJwtSecretKey(int times) {
    verify(sourceProperties, times(times)).getJwtSecretKey();
  }

  private void verifyGetSessionExpiredTime() {
    verify(sessionProperties).getSessionExpiredTimeInMillis();
  }

  private void verifyFindSession() {
    verify(sessionRepository)
        .findByUsernameAndSessionIdAndCreatedDateGreaterThan(Mockito.anyString(),
            Mockito.anyString(), Mockito.anyLong());
  }

  private void verifyNoMoreInteraction() {
    verifyNoMoreInteractions(timeHelper);
    verifyNoMoreInteractions(sourceProperties);
    verifyNoMoreInteractions(sessionProperties);
    verifyNoMoreInteractions(sessionRepository);
  }

  private User generateUser() {
    return User.builder().username(USERNAME).password(PASSWORD).build();
  }

  private Session generateSession() {
    return Session.builder()
        .id(ID)
        .hostname(HOSTNAME)
        .createdDate(SESSION_EXPIRED_TIME)
        .username(Credential.getUsername())
        .sessionId(Credential.getSessionId())
        .build();
  }

}
