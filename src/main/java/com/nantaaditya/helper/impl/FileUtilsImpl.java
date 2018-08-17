package com.nantaaditya.helper.impl;

import com.nantaaditya.helper.FileUtils;
import com.nantaaditya.properties.FileProperties;
import java.io.File;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Component
public class FileUtilsImpl implements FileUtils {

  @Value("${nanta.file.path}")
  private String filePath;

  @Value("${nanta.resource.host}")
  private String IMAGE_HOST;

  @Override
  public boolean isValidContentType(List<String> allowedFileType, String contentType) {
    return allowedFileType.contains(contentType) ? true : false;
  }

  @Override
  public boolean isValidExtension(List<String> allowedExtension, String extension) {
    return allowedExtension.contains(extension) ? true : false;
  }

  @Override
  public String getFileExtension(MultipartFile file) {
    return FilenameUtils.getExtension(file.getOriginalFilename());
  }

  @Override
  public String getFileExtension(File file) {
    String fileName = file.getName();
    if ((fileName.lastIndexOf(".") != -1) && (fileName.lastIndexOf(".") != 0)) {
      return fileName.substring(fileName.lastIndexOf(".") + 1);
    } else {
      return "";
    }
  }

  @Override
  public boolean isBelowMaxFileSize(long allowedMaxFileSize, long fileSize) {
    return fileSize <= allowedMaxFileSize ? true : false;
  }

  @Override
  public String generateThumbnail(MultipartFile file, String directory, String name) {
    String fileExtension = this.getFileExtension(file);
    name = name.replace(" ", "-");
    return filePath.concat(directory)
        .concat(name)
        .concat(FileProperties.IMG_POSTFIX)
        .concat(fileExtension);
  }

  @Override
  public String generateFileURI(MultipartFile file, String name) {
    String fileExtension = this.getFileExtension(file);
    name = name.replace(" ", "-");
    return IMAGE_HOST
        .concat(FileProperties.RESOURCE_WEB_PATH)
        .concat(name)
        .concat(FileProperties.IMG_POSTFIX)
        .concat(fileExtension);
  }

}
