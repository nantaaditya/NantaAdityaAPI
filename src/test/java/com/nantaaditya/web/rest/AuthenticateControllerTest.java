package com.nantaaditya.web.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nantaaditya.RestExceptionHandler;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.AuthenticationCommandRequest;
import com.nantaaditya.model.web.AuthenticationWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.AuthenticateCommand;
import com.nantaaditya.service.command.UnauthenticateCommand;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Slf4j
@RunWith(SpringRunner.class)
public class AuthenticateControllerTest {

  @InjectMocks
  private AuthenticationController controller;

  @Mock
  private ControllerHelper helper;

  @Mock
  private RestExceptionHandler exceptionHandler;

  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private AuthenticationWebRequest authenticationWebRequest;
  private EmptyRequest emptyRequest;
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String SUCCESS_LOGIN_MESSAGE = "sucessfully authenticated";
  private static final String SUCCESS_LOGOUT_MESSAGE = "successfully unauthenticated";
  private static final String REQUEST_ID = UUID.randomUUID().toString();
  private static final String RESPONSE_DATA = UUID.randomUUID().toString();

  @Before
  public void setUp() {
    mapper = new ObjectMapper();
    emptyRequest = generateEmptyRequest();
    mockMvc = MockMvcBuilders
        .standaloneSetup(this.controller)
        .setControllerAdvice(this.exceptionHandler)
        .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
        .setMessageConverters(new MappingJackson2HttpMessageConverter())
        .build();
  }

  @Test
  public void testLogin() throws Exception {
    authenticationWebRequest = generateAuthenticateWebRequest();
    mockLoginController();
    MvcResult result = mockMvc.perform(
        post(ApiPath.LOGIN).param("requestId", REQUEST_ID)
            .content(mapper.writeValueAsString(authenticationWebRequest))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andReturn();
    assertLoginController(result);
    verifyLoginController(1);
  }

  @Test
  public void testLoginFailed() throws Exception {
    authenticationWebRequest = generateFailedAuthenticateWebRequest();
    mockMvc.perform(
        post(ApiPath.LOGIN).param("requestId", REQUEST_ID)
            .content(mapper.writeValueAsString(authenticationWebRequest))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
    verifyLoginController(0);
  }

  @Test
  public void testLogout() throws Exception {
    mockMvc.perform(get(ApiPath.LOGOUT).param("requestId", REQUEST_ID))
        .andExpect(status().isOk());
    verifyLogoutController();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteraction();
  }

  private void mockLoginController() {
    when(helper.response(AuthenticateCommand.class,
        convertAuthenticateWebToCommandRequest(authenticationWebRequest,
            new AuthenticationCommandRequest()), REQUEST_ID, SUCCESS_LOGIN_MESSAGE))
        .thenReturn(ResponseHelper.ok(REQUEST_ID, SUCCESS_LOGIN_MESSAGE, RESPONSE_DATA));
  }

  private void assertLoginController(MvcResult result) throws IOException {
    String responseString = result.getResponse().getContentAsString();
    Response<String> response = mapper.readValue(responseString, Response.class);

    assertEquals(Integer.valueOf(200), response.getCode());
    assertEquals(REQUEST_ID, response.getRequestId());
    assertEquals(SUCCESS_LOGIN_MESSAGE, response.getMessage());
    assertEquals(RESPONSE_DATA, response.getData());
    assertTrue(response.getSuccess());
  }

  private void verifyLoginController(int times) {
    verify(helper, times(times)).response(AuthenticateCommand.class,
        convertAuthenticateWebToCommandRequest(authenticationWebRequest,
            new AuthenticationCommandRequest()), REQUEST_ID, SUCCESS_LOGIN_MESSAGE);
  }

  private void verifyLogoutController() {
    verify(helper).response(UnauthenticateCommand.class, emptyRequest, REQUEST_ID,
        SUCCESS_LOGOUT_MESSAGE);
  }

  private void verifyNoMoreInteraction() {
    verifyNoMoreInteractions(helper);
  }

  private AuthenticationWebRequest generateAuthenticateWebRequest() {
    return AuthenticationWebRequest.builder().username(USERNAME).password(PASSWORD).build();
  }

  private AuthenticationWebRequest generateFailedAuthenticateWebRequest() {
    return AuthenticationWebRequest.builder().build();
  }

  private EmptyRequest generateEmptyRequest() {
    return EmptyRequest.getInstance();
  }

  private AuthenticationCommandRequest convertAuthenticateWebToCommandRequest(
      AuthenticationWebRequest webRequest,
      AuthenticationCommandRequest commandRequest) {
    BeanUtils.copyProperties(webRequest, commandRequest);
    return commandRequest;
  }
}
