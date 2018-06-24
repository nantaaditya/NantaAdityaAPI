package com.nantaaditya.web.rest;

import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.AuthenticationCommandRequest;
import com.nantaaditya.model.web.AuthenticationWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.AuthenticateCommand;
import com.nantaaditya.service.command.UnauthenticateCommand;
import com.nantaaditya.web.AbstractController;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Slf4j
@RestController
public class AuthenticationController extends AbstractController {

  @Autowired
  private ControllerHelper controllerHelper;

  @RequestMapping(value = ApiPath.LOGIN,
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<String> login(@RequestParam String requestId,
      @RequestBody @Valid AuthenticationWebRequest webRequest) {

    return controllerHelper.response(AuthenticateCommand.class,
        convertRequest(webRequest, new AuthenticationCommandRequest()), requestId,
        "sucessfully authenticated");
  }

  @RequestMapping(value = ApiPath.LOGOUT,
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> logout(@RequestParam String requestId) {

    return controllerHelper.response(UnauthenticateCommand.class, EmptyRequest.getInstance(),
        requestId, "successfully unauthenticated");
  }

}
