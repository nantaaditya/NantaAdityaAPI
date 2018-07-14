package com.nantaaditya.service.command;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public interface Command<RESPONSE, REQUEST> {

  RESPONSE execute(REQUEST request) ;

}
