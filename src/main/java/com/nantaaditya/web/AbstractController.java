package com.nantaaditya.web;

import com.nantaaditya.helper.MapperHelper;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public class AbstractController {

  @Autowired
  private MapperHelper mapperHelper;

  protected <WEB, COMMAND> COMMAND convertRequest(WEB source, COMMAND target) {
    BeanUtils.copyProperties(source, target);
    return target;
  }

  protected <COMMAND extends Collection, WEB> List<WEB> convertResponse(COMMAND source, Class<?> target){
    return mapperHelper.mapToList(source, target);
  }
}
