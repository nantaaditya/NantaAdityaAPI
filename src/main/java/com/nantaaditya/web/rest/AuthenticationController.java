package com.nantaaditya.web.rest;

import com.nantaaditya.helper.GoogleRecaptchaHelper;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.AuthenticationCommandRequest;
import com.nantaaditya.model.command.ChangePasswordCommandRequest;
import com.nantaaditya.model.web.AuthenticationWebRequest;
import com.nantaaditya.model.web.ChangePasswordWebRequest;
import com.nantaaditya.model.web.GoogleCaptchaWebResponse;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.AuthenticateCommand;
import com.nantaaditya.service.command.ChangePasswordCommand;
import com.nantaaditya.service.command.UnauthenticateCommand;
import com.nantaaditya.web.AbstractController;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  private GoogleRecaptchaHelper googleRecaptchaHelper;

  @RequestMapping(value = ApiPath.LOGIN,
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<String> login(@RequestParam String requestId,
      @RequestBody @Valid AuthenticationWebRequest webRequest) {

    GoogleCaptchaWebResponse response = googleRecaptchaHelper
        .validateCaptcha(webRequest.getCaptchaResponse());

    if (response.getSuccess()) {
      return controllerHelper.response(AuthenticateCommand.class,
          convertRequest(webRequest, new AuthenticationCommandRequest()), requestId,
          "sucessfully authenticated");
    } else {
      return ResponseHelper.status(HttpStatus.BAD_REQUEST, false, requestId,
          "please verify you're human", null);
    }
  }

  @RequestMapping(value = ApiPath.LOGOUT,
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> logout(@RequestParam String requestId) {

    return controllerHelper.response(UnauthenticateCommand.class, EmptyRequest.getInstance(),
        requestId, "successfully unauthenticated");
  }

  @RequestMapping(value = ApiPath.CHANGE_PASSWORD,
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> changePassword(@RequestParam String requestId,
      @RequestBody @Valid ChangePasswordWebRequest webRequest){

    return controllerHelper.response(ChangePasswordCommand.class,
        convertRequest(webRequest, new ChangePasswordCommandRequest()), requestId,
        "password has been changed");
  }
}
