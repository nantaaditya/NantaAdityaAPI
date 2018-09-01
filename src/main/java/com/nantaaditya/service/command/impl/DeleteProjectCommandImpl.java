package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.repository.ImageRepository;
import com.nantaaditya.repository.ProjectRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.DeleteProjectCommand;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
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
  private ImageRepository imageRepository;

  @Autowired
  private FileHelper fileHelper;

  @Autowired
  @Setter
  private TransactionTemplate transactionTemplate;

  @Value("${nanta.resource.host}")
  private String IMAGE_HOST;

  @Override
  public EmptyResponse doExecute(String id) {
    Project project = this.findOne(id);
    this.deleteFile(project.getImageURL());
    transactionTemplate.execute(result -> delete(project));
    return EmptyResponse.getInstance();
  }

  private Project findOne(String id) {
    return Optional.ofNullable(projectRepository.findOne(id))
        .orElseThrow(() -> new EntityNotFoundException("Project not found"));
  }

  private Boolean delete(Project project) {
    imageRepository.deleteByUrl(project.getUrl());
    projectRepository.delete(project);
    return true;
  }

  private void deleteFile(String path) {
    this.fileHelper
        .deleteFile(this.fileHelper.getRootFilePath().concat(
            path.replace(IMAGE_HOST.concat("/resource"), "")));
  }

}
