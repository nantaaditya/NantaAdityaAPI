package com.nantaaditya.helper.impl;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

import com.nantaaditya.model.Response;
import com.nantaaditya.service.ServiceExecutor;
import com.nantaaditya.service.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControllerHelper {

  @Autowired
  private ServiceExecutor serviceExecutor;

  public <T, R> Response<T> response(Class<? extends Command<T, R>> command,
      R request, String requestId, String message) {
    T response = serviceExecutor.execute(command, request);
    return ResponseHelper.ok(requestId, message, response);
  }
}
