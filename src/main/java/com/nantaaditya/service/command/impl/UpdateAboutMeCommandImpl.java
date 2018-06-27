package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.AboutMe;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.AboutMeCommandRequest;
import com.nantaaditya.repository.AboutMeRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.UpdateAboutMeCommand;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
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
public class UpdateAboutMeCommandImpl extends
    AbstractCommand<EmptyResponse, AboutMeCommandRequest> implements
    UpdateAboutMeCommand {

  @Autowired
  private AboutMeRepository aboutMeRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(AboutMeCommandRequest request) {
    this.updateAboutMe(this.find(), request);
    return EmptyResponse.getInstance();
  }

  private AboutMe find() {
    return Optional.ofNullable(aboutMeRepository.findAll())
        .map(result -> result.get(0))
        .orElseThrow(() -> new EntityNotFoundException("entity about is empty"));
  }

  private void updateAboutMe(AboutMe aboutMe, AboutMeCommandRequest request) {
    aboutMe.setDescription(request.getDescription());
    aboutMeRepository.save(aboutMe);
  }
}
