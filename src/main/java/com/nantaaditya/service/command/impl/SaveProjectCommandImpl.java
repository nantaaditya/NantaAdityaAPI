package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.helper.FileUtils;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveProjectCommandRequest;
import com.nantaaditya.properties.FileProperties;
import com.nantaaditya.repository.ProjectRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.SaveProjectCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
  private FileHelper fileHelper;

  @Autowired
  private FileUtils fileUtils;

  @Autowired
  private MapperHelper mapperHelper;

  private static final int WIDTH_PROJECT_IMAGE = 401;
  private static final int HEIGHT_PROJECT_IMAGE = 250;

  @Override
  public EmptyResponse doExecute(SaveProjectCommandRequest request) {
    String sourceImage = this.uploadFile(request.getFile(), request.getName());
    String thumbnailImage = this.generateThumbnail(request.getFile(), request.getName());
    log.info("file source {}, \nfile destination {}", sourceImage, thumbnailImage);

    try {
      this.fileHelper.resizeImage(sourceImage,
          WIDTH_PROJECT_IMAGE, HEIGHT_PROJECT_IMAGE, thumbnailImage);
    } catch (Exception e) {
      log.error("error resize image {}, \nto thumbnail {}", sourceImage, thumbnailImage);
    }

    this.deleteOriginalFile(sourceImage);
    request.setImage(this.fileUtils.generateFileURI(request.getFile(), request.getName()));
    this.save(this.toEntity(request));

    return EmptyResponse.getInstance();
  }

  private String uploadFile(MultipartFile file, String fileName) {
    try {
      return this.fileHelper.uploadFile(file, FileProperties.WEB_PATH, fileName);
    } catch (Exception e) {
      log.error("error uploading file {}", fileName);
      return null;
    }
  }

  private void deleteOriginalFile(String fileName) {
    this.fileHelper.deleteFile(fileName);
    log.info("delete file {}", fileName);
  }

  private String generateThumbnail(MultipartFile file, String fileName) {
    return this.fileUtils.generateThumbnail(file, FileProperties.WEB_PATH, fileName);
  }

  private Project toEntity(SaveProjectCommandRequest request) {
    return mapperHelper.map(request, Project.class);
  }

  private void save(Project project) {
    this.projectRepository.save(project);
  }
}
