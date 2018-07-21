package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.GetProjectCommandResponse;
import com.nantaaditya.repository.ProjectRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.GetProjectCommand;
import java.util.List;
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
public class GetProjectCommandImpl extends
    AbstractCommand<List<GetProjectCommandResponse>, EmptyRequest> implements
    GetProjectCommand {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private MapperHelper mapperHelper;


  @Override
  public List<GetProjectCommandResponse> doExecute(EmptyRequest emptyRequest) {
    return toResponse(get());
  }

  private List<Project> get() {
    return projectRepository.findAll();
  }

  private List<GetProjectCommandResponse> toResponse(List<Project> projects) {
    return mapperHelper.mapToList(projects, GetProjectCommandResponse.class);
  }
}
