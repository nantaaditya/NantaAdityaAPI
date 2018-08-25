package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Image;
import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.helper.FileUtils;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveImageCommandRequest;
import com.nantaaditya.properties.FileProperties;
import com.nantaaditya.repository.ImageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.SaveImageCommand;
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
public class SaveImageCommandImpl extends
    AbstractCommand<EmptyResponse, SaveImageCommandRequest> implements
    SaveImageCommand {

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private FileHelper fileHelper;

  @Autowired
  private MapperHelper mapperHelper;

  @Autowired
  private FileUtils fileUtils;

  @Override
  public EmptyResponse doExecute(SaveImageCommandRequest request) {
    String sourceImage = this.uploadFile(request.getFile(), request.getName());
    String thumbnailImage = this.generateThumbnail(request.getFile(), request.getName());
    log.info("file source {}, \nfile destination {}", sourceImage, thumbnailImage);

    try {
      this.fileHelper.resizeImage(sourceImage, request.getWidth(),
          request.getHeight(), thumbnailImage);
    } catch (Exception e) {
      log.error("error resize image {}, \nto thumbnail {}", sourceImage, thumbnailImage);
    }

    this.deleteOriginalFile(sourceImage);
    request.setUrl(this.fileUtils.generateFileURI(request.getFile(), request.getName()));
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

  private Image toEntity(SaveImageCommandRequest request) {
    return mapperHelper.map(request, Image.class);
  }

  private void save(Image image) {
    this.imageRepository.save(image);
  }
}
