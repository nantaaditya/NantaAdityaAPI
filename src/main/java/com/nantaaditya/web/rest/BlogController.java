package com.nantaaditya.web.rest;

import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.RepublishNotificationCommandRequest;
import com.nantaaditya.model.command.SaveBlogCommandRequest;
import com.nantaaditya.model.web.GetBlogWebResponse;
import com.nantaaditya.model.web.SaveBlogWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.GetBlogCommand;
import com.nantaaditya.service.command.RepublishNotificationCommand;
import com.nantaaditya.service.command.SaveBlogCommand;
import com.nantaaditya.web.AbstractController;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@RestController
@RequestMapping(value = ApiPath.BLOG)
public class BlogController extends AbstractController {

  @Autowired
  private ControllerHelper controllerHelper;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> save(@RequestParam String requestId, @RequestBody @Valid
      SaveBlogWebRequest webRequest) {

    return controllerHelper
        .response(SaveBlogCommand.class, convertRequest(webRequest, new SaveBlogCommandRequest()),
            requestId, "save blog success");
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<List<GetBlogWebResponse>> get(@RequestParam String requestId,
      @RequestParam(required = false) String client) {
    return controllerHelper
        .response(GetBlogCommand.class, client, requestId, "get blog success");
  }

  @PostMapping(value = "/republish/{titleId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> republishNotification(@RequestParam String requestId,
      @PathVariable String titleId){
    RepublishNotificationCommandRequest commandRequest = RepublishNotificationCommandRequest.builder()
        .titleId(titleId)
        .build();

    return controllerHelper.response(RepublishNotificationCommand.class, commandRequest,
        requestId, "Success republish notification");
  }
}
