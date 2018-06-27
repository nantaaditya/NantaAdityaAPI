package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.AboutMe;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.web.AboutMeWebResponse;
import com.nantaaditya.repository.AboutMeRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.FindAboutMeCommand;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class FindAboutMeCommandImpl extends
    AbstractCommand<AboutMeWebResponse, EmptyRequest> implements
    FindAboutMeCommand {

  @Autowired
  private AboutMeRepository aboutMeRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  public AboutMeWebResponse doExecute(EmptyRequest request) {
    return toWebResponse(find());
  }

  private AboutMe find() {
    return Optional.ofNullable(aboutMeRepository.findAll())
        .map(result -> result.get(0))
        .get();
  }

  private AboutMeWebResponse toWebResponse(AboutMe aboutMe) {
    return mapperHelper.map(aboutMe, AboutMeWebResponse.class);
  }
}
