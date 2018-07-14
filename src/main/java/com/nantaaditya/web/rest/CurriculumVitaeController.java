package com.nantaaditya.web.rest;

import com.nantaaditya.helper.impl.ControllerHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.command.CurriculumVitaeCommandRequest;
import com.nantaaditya.model.web.CurriculumVitaeWebRequest;
import com.nantaaditya.model.web.CurriculumVitaeWebResponse;
import com.nantaaditya.properties.ApiPath;
import com.nantaaditya.service.command.DeleteCurriculumVitaeCommand;
import com.nantaaditya.service.command.GetCurriculumVitaeCommand;
import com.nantaaditya.service.command.SaveCurriculumVitaeCommand;
import com.nantaaditya.web.AbstractController;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(value = ApiPath.CURRICULUM_VITAE)
public class CurriculumVitaeController extends AbstractController {

  @Autowired
  private ControllerHelper controllerHelper;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> save(@RequestParam String requestId,
      @Valid @RequestBody CurriculumVitaeWebRequest request) {
    return controllerHelper.response(SaveCurriculumVitaeCommand.class,
        convertRequest(request, new CurriculumVitaeCommandRequest()),
        requestId, "save curriculum vitae success");
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<EmptyResponse> delete(@RequestParam String requestId,
      @PathVariable String id) {
    return controllerHelper.response(DeleteCurriculumVitaeCommand.class,
        id, requestId, "delete curriculum vitae success");
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Response<List<CurriculumVitaeWebResponse>> get(
      @RequestParam String requestId) {
    return controllerHelper.response(GetCurriculumVitaeCommand.class,
        EmptyRequest.getInstance(), requestId, "get curriculum vitae success");
  }
}
