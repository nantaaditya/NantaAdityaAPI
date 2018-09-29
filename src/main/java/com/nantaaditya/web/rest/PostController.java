package com.nantaaditya.web.rest;

import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.UpdateBlogCommandRequest;
import com.nantaaditya.model.web.GetPostWebResponse;
import com.nantaaditya.model.web.UpdateBlogWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.GetPostCommand;
import com.nantaaditya.service.command.ToggleBlogCommand;
import com.nantaaditya.service.command.UpdateBlogCommand;
import com.nantaaditya.web.AbstractController;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
@RequestMapping(value = ApiPath.POST)
public class PostController extends AbstractController {

  @Autowired
  private ControllerHelper controllerHelper;

  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> update(@RequestParam String requestId,
      @RequestBody @Valid UpdateBlogWebRequest webRequest) {
    return controllerHelper.response(UpdateBlogCommand.class,
        convertRequest(webRequest, new UpdateBlogCommandRequest()),
        requestId,"update post success");
  }

  @GetMapping(value = "/{titleId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<GetPostWebResponse> get(@RequestParam String requestId,
      @PathVariable String titleId) {
    return controllerHelper.response(GetPostCommand.class, titleId,
        requestId, "get post success");
  }

  @PutMapping(value = "/toggle/{titleId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> toggle(@RequestParam String requestId,
      @PathVariable String titleId) {
    return controllerHelper
        .response(ToggleBlogCommand.class, titleId, requestId, "toggle blog success");
  }

}
