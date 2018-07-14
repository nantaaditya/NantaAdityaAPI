package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.AboutMe;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.AboutMeCommandRequest;
import com.nantaaditya.repository.AboutMeRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.SaveAboutMeCommand;
import javax.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class SaveAboutMeCommandImpl extends
    AbstractCommand<EmptyResponse, AboutMeCommandRequest> implements
    SaveAboutMeCommand {

  @Autowired
  private AboutMeRepository aboutMeRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(AboutMeCommandRequest request) {
    save(toEntity(request));
    return EmptyResponse.getInstance();
  }

  private void save(AboutMe aboutMe) {
    if (isNotExist()) {
      aboutMeRepository.save(aboutMe);
    } else {
      throw new EntityExistsException("entity about me is already exist");
    }
  }

  private boolean isNotExist() {
    return aboutMeRepository.findAll().isEmpty();
  }

  private AboutMe toEntity(AboutMeCommandRequest request) {
    return mapperHelper.map(request, AboutMe.class);
  }
}
