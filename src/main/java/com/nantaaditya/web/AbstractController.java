package com.nantaaditya.web;

import org.springframework.beans.BeanUtils;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public class AbstractController {

  protected <WEB, COMMAND> COMMAND convertRequest(WEB source, COMMAND target) {
    BeanUtils.copyProperties(source, target);
    return target;
  }

}
