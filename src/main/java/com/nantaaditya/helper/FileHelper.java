package com.nantaaditya.helper;
// @formatter:off
import org.springframework.web.multipart.MultipartFile; /**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public interface FileHelper {
  public String getRootFilePath();

  public String uploadFile(MultipartFile file, String path, String name) throws Exception;

  public void deleteFile(String file);

  public void resizeImage(String source, int width, int height, String destination) throws Exception;
}
