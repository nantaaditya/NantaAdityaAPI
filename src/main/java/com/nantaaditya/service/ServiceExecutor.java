package com.nantaaditya.service;

import com.nantaaditya.model.command.CommandRequest;
import com.nantaaditya.service.command.Command;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public interface ServiceExecutor {

  <T, R extends CommandRequest> T execute(Class<? extends Command<T, R>> commandClass,
      R request);

}
