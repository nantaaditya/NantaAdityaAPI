package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.helper.FileUtils;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.SaveProjectCommandRequest;
import com.nantaaditya.properties.FileProperties;
import com.nantaaditya.repository.ProjectRepository;
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
public class SaveProjectCommandImplTest {

  @InjectMocks
  private SaveProjectCommandImpl command;

  @Mock
  private ProjectRepository repository;

  @Mock
  private FileHelper fileHelper;

  @Mock
  private FileUtils fileUtils;

  @Mock
  private MapperHelper mapperHelper;


  private MockMultipartFile file;
  private SaveProjectCommandRequest request;
  private Project project;
  private static final String NAME = "name";
  private static final String URL = "url";
  private static final String IMAGE = "image";
  private static final int WIDTH = 401;
  private static final int HEIGHT = 250;
  private static final String SOURCE_IMAGE = "/resource/web/name.jpg";
  private static final String SOURCE_THUMBNAIL_IMAGE = "/resource/web/name_thumbnail.jpg";

  @Before
  public void setUp() {
    this.file = generateFile();
    this.request = generateRequest();
    this.project = generateProject();
  }


  @After
  public void tearDown() {
    verifyNoMoreInteractions(fileHelper);
    verifyNoMoreInteractions(fileUtils);
    verifyNoMoreInteractions(mapperHelper);
    verifyNoMoreInteractions(repository);
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

  private void mockUploadFile() throws Exception {
    when(fileHelper.uploadFile(file, FileProperties.WEB_PATH, NAME))
        .thenReturn(SOURCE_IMAGE);
  }

  private void mockGenerateThumbnail() {
    when(fileUtils.generateThumbnail(file, FileProperties.WEB_PATH, NAME))
        .thenReturn(SOURCE_THUMBNAIL_IMAGE);
  }

  private void mockToEntity() {
    when(mapperHelper.map(request, Project.class)).thenReturn(project);
  }

  private void mockGenerateFileURI() {
    when(fileUtils.generateFileURI(file, NAME)).thenReturn(IMAGE);
  }

  private void verifyUploadFile(int time) throws Exception {
    verify(fileHelper, times(time)).uploadFile(file, FileProperties.WEB_PATH, NAME);
  }

  private void verifyResizeImage(int time) throws Exception {
    verify(fileHelper, times(time))
        .resizeImage(SOURCE_IMAGE, WIDTH, HEIGHT, SOURCE_THUMBNAIL_IMAGE);
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
    verify(mapperHelper, times(time)).map(request, Project.class);
  }

  private void verifySave(int time) {
    verify(repository).save(project);
  }

  private MockMultipartFile generateFile() {
    return new MockMultipartFile("file", "boat.jpg",
        "image/jpeg", "Test Image".getBytes());
  }

  private SaveProjectCommandRequest generateRequest() {
    return SaveProjectCommandRequest
        .builder()
        .file(file)
        .name(NAME)
        .url(URL)
        .image(IMAGE)
        .build();
  }

  private Project generateProject() {
    return Project.builder()
        .image(IMAGE)
        .name(NAME)
        .url(URL)
        .build();
  }
}
