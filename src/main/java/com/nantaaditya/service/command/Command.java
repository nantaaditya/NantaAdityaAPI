package com.nantaaditya.service.command;

import com.nantaaditya.model.command.CommandRequest;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public interface Command<RESPONSE, REQUEST extends CommandRequest> {

  RESPONSE execute(REQUEST request) ;

}
