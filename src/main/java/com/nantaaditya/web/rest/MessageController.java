package com.nantaaditya.web.rest;

import com.nantaaditya.helper.GoogleRecaptchaHelper;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.GetMessageCommandResponse;
import com.nantaaditya.model.command.ReplyMessageCommandRequest;
import com.nantaaditya.model.command.SaveMessageCommandRequest;
import com.nantaaditya.model.web.GetMessageWebResponse;
import com.nantaaditya.model.web.GoogleCaptchaWebResponse;
import com.nantaaditya.model.web.ReplyMessageWebRequest;
import com.nantaaditya.model.web.SaveMessageWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.FindMessageCommand;
import com.nantaaditya.service.command.GetMessageCommand;
import com.nantaaditya.service.command.ReplyMessageCommand;
import com.nantaaditya.service.command.SaveMessageCommand;
import com.nantaaditya.web.AbstractController;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value = ApiPath.MESSAGE)
public class MessageController extends AbstractController {

  @Autowired
  private ControllerHelper controllerHelper;

  @Autowired
  private GoogleRecaptchaHelper googleRecaptchaHelper;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> save(@RequestParam String requestId, @Valid @RequestBody
      SaveMessageWebRequest webRequest) {

    GoogleCaptchaWebResponse response = googleRecaptchaHelper
        .validateCaptcha(webRequest.getCaptchaResponse());

    if(response.getSuccess()) {
      return controllerHelper.response(SaveMessageCommand.class,
          convertRequest(webRequest, new SaveMessageCommandRequest()),
          requestId, "your message has been sent");
    } else {
      return ResponseHelper.status(HttpStatus.BAD_REQUEST, false, requestId,
          "please verify you're human", null);
    }
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<List<GetMessageWebResponse>> get(@RequestParam String requestId) {
    List<GetMessageCommandResponse> commandResponse = controllerHelper.response(
        GetMessageCommand.class, EmptyRequest.getInstance());
    return ResponseHelper.ok(requestId, "get message success",
        convertResponse(commandResponse, GetMessageWebResponse.class));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<GetMessageWebResponse> find(@RequestParam String requestId,
      @PathVariable String id) {
    GetMessageCommandResponse commandResponse = controllerHelper.response(
        FindMessageCommand.class, id);
    return ResponseHelper.ok(requestId, "find message success",
        convertResponse(commandResponse, new GetMessageWebResponse()));
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> reply(@RequestParam String requestId,
      @Valid @RequestBody ReplyMessageWebRequest webRequest) {
    return controllerHelper.response(ReplyMessageCommand.class,
        convertRequest(webRequest, new ReplyMessageCommandRequest()),
        requestId, "reply message has been sent");
  }
}
