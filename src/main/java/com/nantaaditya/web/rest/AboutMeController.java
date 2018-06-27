package com.nantaaditya.web.rest;

import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.AboutMeCommandRequest;
import com.nantaaditya.model.web.AboutMeWebRequest;
import com.nantaaditya.model.web.AboutMeWebResponse;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.FindAboutMeCommand;
import com.nantaaditya.service.command.UpdateAboutMeCommand;
import com.nantaaditya.web.AbstractController;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = ApiPath.ABOUT_ME)
public class AboutMeController extends AbstractController {

  @Autowired
  private ControllerHelper controllerHelper;

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> update(@RequestParam String requestId,
      @Valid @RequestBody AboutMeWebRequest webRequest) {
    return controllerHelper.response(UpdateAboutMeCommand.class,
        convertRequest(webRequest, new AboutMeCommandRequest()), requestId,
        "update about me success");
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<AboutMeWebResponse> get(@RequestParam String requestId) {
    return controllerHelper
        .response(FindAboutMeCommand.class, EmptyRequest.getInstance(), requestId,
            "get about me");
  }
}
