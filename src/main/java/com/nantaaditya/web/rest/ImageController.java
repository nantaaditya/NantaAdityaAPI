package com.nantaaditya.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.SaveImageCommandRequest;
import com.nantaaditya.model.web.GetImageWebResponse;
import com.nantaaditya.model.web.SaveImageWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.GetImageCommand;
import com.nantaaditya.service.command.SaveImageCommand;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Slf4j
@RestController
@RequestMapping(value = ApiPath.IMAGE)
public class ImageController {

  @Autowired
  private ControllerHelper controllerHelper;
  @Autowired
  private ObjectMapper objectMapper;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> save(@RequestParam String requestId,
      @RequestPart String request, @RequestPart MultipartFile file) {

    return controllerHelper
        .response(SaveImageCommand.class, this.generateCommandRequest(request, file),
            requestId, "save image success");
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<List<GetImageWebResponse>> get(@RequestParam String requestId) {

    return controllerHelper.response(GetImageCommand.class, EmptyRequest.getInstance(),
        requestId, "get image success");
  }

  private SaveImageCommandRequest generateCommandRequest(String request, MultipartFile file) {
    SaveImageWebRequest webRequest = SaveImageWebRequest.builder().build();
    SaveImageCommandRequest commandRequest = SaveImageCommandRequest.builder().build();
    try {
      webRequest = objectMapper.readValue(request, SaveImageWebRequest.class);
    } catch (IOException e) {
      log.error("error converting request to object {}", request);
    }
    BeanUtils.copyProperties(webRequest, commandRequest);
    commandRequest.setFile(file);
    return commandRequest;
  }
}
