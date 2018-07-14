package com.nantaaditya.web.rest;

import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.GetSkillCommandResponse;
import com.nantaaditya.model.command.SaveSkillCommandRequest;
import com.nantaaditya.model.command.UpdateSkillCommandRequest;
import com.nantaaditya.model.web.GetSkillWebResponse;
import com.nantaaditya.model.web.SaveSkillWebRequest;
import com.nantaaditya.model.web.UpdateSkillWebRequest;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.DeleteSkillCommand;
import com.nantaaditya.service.command.GetSkillCommand;
import com.nantaaditya.service.command.SaveSkillCommand;
import com.nantaaditya.service.command.UpdateSkillCommand;
import com.nantaaditya.web.AbstractController;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@RestController
@RequestMapping(value = ApiPath.SKILL)
public class SkillController extends AbstractController {

  @Autowired
  private ControllerHelper controllerHelper;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> save(@RequestParam String requestId,
      @Valid @RequestBody SaveSkillWebRequest request) {
    return controllerHelper.response(SaveSkillCommand.class,
        convertRequest(request, new SaveSkillCommandRequest()),
        requestId, "save skill success");
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> update(@RequestParam String requestId,
      @Valid @RequestBody UpdateSkillWebRequest request) {
    return controllerHelper.response(UpdateSkillCommand.class,
        convertRequest(request, new UpdateSkillCommandRequest()),
        requestId, "update skill success");
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<List<GetSkillWebResponse>> get(
      @RequestParam String requestId) {
    List<GetSkillCommandResponse> data = controllerHelper
        .response(GetSkillCommand.class, EmptyRequest.getInstance());
    return ResponseHelper.ok(requestId, "get skill success",
        convertResponse(data, GetSkillWebResponse.class));
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> delete(@RequestParam String requestId,
      @PathVariable String id) {
    return controllerHelper.response(DeleteSkillCommand.class, id,
        requestId, "delete skill success");
  }
}
