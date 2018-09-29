package com.nantaaditya.helper;

import java.io.File;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public interface FileUtils {

  public boolean isValidContentType(List<String> allowedFileType, String contentType);

  public boolean isValidExtension(List<String> allowedExtension, String extension);

  public String getFileExtension(MultipartFile file);

  public String getFileExtension(File file);

  public boolean isBelowMaxFileSize(long allowedMaxFileSize, long fileSize);

  public String generateThumbnail(MultipartFile file, String directory, String name);

  public String generateFileURI(MultipartFile file, String imageGroup, String name);

}
