package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.repository.ProjectRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.DeleteProjectCommand;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class DeleteProjectCommandImpl extends AbstractCommand<EmptyResponse, String> implements
    DeleteProjectCommand {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private FileHelper fileHelper;

  @Value("${nanta.resource.host}")
  private String IMAGE_HOST;

  @Override
  public EmptyResponse doExecute(String id) {
    Project project = this.findOne(id);
    this.deleteFile(project.getImage());
    this.delete(project);
    return null;
  }

  private Project findOne(String id) {
    return Optional.ofNullable(projectRepository.findOne(id))
        .orElseThrow(() -> new EntityNotFoundException("Project not found"));
  }

  private void delete(Project project) {
    this.projectRepository.delete(project);
  }

  private void deleteFile(String path) {
    this.fileHelper
        .deleteFile(this.fileHelper.getRootFilePath().concat(
            path.replace(IMAGE_HOST.concat("/resource"), "")));
  }

}
