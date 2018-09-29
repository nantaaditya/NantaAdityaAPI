package com.nantaaditya.helper.impl;

import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.helper.FileUtils;
import com.nantaaditya.properties.FileProperties;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class FileHelperImpl implements FileHelper {

  @Value("${nanta.file.path}")
  private String filePath;

  @Autowired
  private FileUtils fileUtils;

  @Override
  public String getRootFilePath() {
    return this.filePath;
  }

  @Override
  public String uploadFile(MultipartFile file, String path, String name) throws Exception {
    String fileType = this.getFileType(file);
    String extension = this.fileUtils.getFileExtension(file);
    long fileSize = file.getSize();

    File directory = new File(this.getDirectoryPath(path));
    directory.mkdirs();
    log.info("upload file \npath {}, \nfile name {}, \nfull path {}", path, name,
        directory.getPath());

    if (this.fileUtils.isValidContentType(FileProperties.ALLOWED_IMG_FILE_TYPES, fileType)) {
      if (this.fileUtils.isBelowMaxFileSize(FileProperties.MAX_IMG_FILE_SIZE, fileSize)) {
        String filePath = this.setFileAbsolutePath(directory, name, extension);
        File destinationFile = new File(filePath);
        file.transferTo(destinationFile);
        log.info("File successfully uploaded to {}", filePath);
        return destinationFile.getAbsolutePath();
      } else {
        throw new FileUploadBase.FileSizeLimitExceededException("File size must below 2 MB",
            fileSize, FileProperties.MAX_IMG_FILE_SIZE);
      }
    } else {
      throw new FileUploadBase.InvalidContentTypeException("File type not valid (image only)");
    }
  }

  @Override
  public void deleteFile(String image) {
    File file = new File(image);
    file.delete();
  }

  @Override
  public void resizeImage(String source, int width, int height, String destination)
      throws Exception {
    File file = new File(source);
    String extension = this.fileUtils.getFileExtension(file);

    if (this.fileUtils.isValidExtension(FileProperties.ALLOWED_IMG_EXTENSION, extension)) {
      Thumbnails.of(source)
          .forceSize(width, height)
          .outputFormat(extension)
          .toFile(destination);
    } else {
      throw new FileUploadBase.InvalidContentTypeException("File type not valid {image only");
    }

  }

  private String getFileType(MultipartFile file) {
    return file.getContentType().toString().toLowerCase();
  }

  private String getDirectoryPath(String directory) {
    return filePath.concat(directory);
  }

  private String setFileAbsolutePath(File directory, String name, String extension) {
    return directory.getAbsolutePath()
        .concat("/")
        .concat(name)
        .concat(".")
        .concat(extension);
  }

}
