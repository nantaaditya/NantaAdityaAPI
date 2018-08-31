package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveProjectCommandRequest;
import com.nantaaditya.repository.ProjectRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.SaveProjectCommand;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SaveProjectCommandImpl extends
    AbstractCommand<EmptyResponse, SaveProjectCommandRequest> implements
    SaveProjectCommand {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  public EmptyResponse doExecute(SaveProjectCommandRequest request) {
    this.save(this.toEntity(request));
    return EmptyResponse.getInstance();
  }

  private Project toEntity(SaveProjectCommandRequest request) {
    return mapperHelper.map(request, Project.class);
  }

  private void save(Project project) {
    this.projectRepository.save(project);
  }
}
