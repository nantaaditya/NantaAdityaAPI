package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Image;
import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.helper.FileUtils;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.SaveImageCommandRequest;
import com.nantaaditya.properties.FileProperties;
import com.nantaaditya.repository.ImageRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class SaveImageCommandImplTest {
  @InjectMocks
  private SaveImageCommandImpl command;

  @Mock
  private ImageRepository imageRepository;

  @Mock
  private FileHelper fileHelper;

  @Mock
  private MapperHelper mapperHelper;

  @Mock
  private FileUtils fileUtils;

  private MockMultipartFile file;
  private SaveImageCommandRequest request;
  private Image image;
  private static final String NAME = "name";
  private static final String URL = "url";
  private static final String IMAGE_NAME = "image";
  private static final String SOURCE_IMAGE = "/resource/web/name.jpg";
  private static final String SOURCE_THUMBNAIL_IMAGE = "/resource/web/name_thumbnail.jpg";

  @Before
  public void setUp(){
    this.file = generateFile();
    this.request = generateWebRequest();
    this.image = generateImage();
  }

  @Test
  public void testDoExecute() throws Exception {
    this.mockUploadFile();
    this.mockGenerateThumbnail();
    this.mockToEntity();
    this.mockGenerateFileURI();

    this.command.doExecute(request);

    this.verifyGenerateThumbnail();
    this.verifySave(1);
    this.verifyToEntity(1);
    this.verifyUploadFile(1);
    this.verifyResizeImage(1);
    this.verifyGenerateFileURI(1);
    this.verifyDeleteOriginalFile(1);
  }

  @After
  public void tearDown(){
    verifyNoMoreInteractions(fileHelper);
    verifyNoMoreInteractions(fileUtils);
    verifyNoMoreInteractions(mapperHelper);
    verifyNoMoreInteractions(imageRepository);
  }

  private void mockUploadFile() throws Exception {
    when(fileHelper.uploadFile(file, FileProperties.WEB_PATH, NAME))
        .thenReturn(SOURCE_IMAGE);
  }

  private void mockGenerateThumbnail() {
    when(fileUtils.generateThumbnail(file, FileProperties.WEB_PATH, NAME))
        .thenReturn(SOURCE_THUMBNAIL_IMAGE);
  }

  private void mockToEntity() {
    when(mapperHelper.map(request, Image.class)).thenReturn(image);
  }

  private void mockGenerateFileURI() {
    when(fileUtils.generateFileURI(file, NAME)).thenReturn(IMAGE_NAME);
  }

  private void verifyUploadFile(int time) throws Exception {
    verify(fileHelper, times(time)).uploadFile(file, FileProperties.WEB_PATH, NAME);
  }

  private void verifyResizeImage(int time) throws Exception {
    verify(fileHelper, times(time))
        .resizeImage(SOURCE_IMAGE, 300, 300, SOURCE_THUMBNAIL_IMAGE);
  }

  private void verifyDeleteOriginalFile(int time) throws Exception {
    verify(fileHelper, times(time)).deleteFile(SOURCE_IMAGE);
  }

  private void verifyGenerateThumbnail() {
    verify(fileUtils).generateThumbnail(file, FileProperties.WEB_PATH, NAME);
  }

  private void verifyGenerateFileURI(int time) {
    verify(fileUtils, times(time)).generateFileURI(file, NAME);
  }

  private void verifyToEntity(int time) {
    verify(mapperHelper, times(time)).map(request, Image.class);
  }

  private void verifySave(int time) {
    verify(imageRepository).save(image);
  }

  private MockMultipartFile generateFile() {
    return new MockMultipartFile("file", "boat.jpg",
        "image/jpeg", "Test Image".getBytes());
  }

  private SaveImageCommandRequest generateWebRequest(){
    return SaveImageCommandRequest.builder()
        .file(file)
        .name(NAME)
        .url(URL)
        .width(300)
        .height(300)
        .build();
  }

  private Image generateImage(){
    return Image.builder()
        .url(URL)
        .name(NAME)
        .build();
  }
}
