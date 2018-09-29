package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.repository.ImageRepository;
import com.nantaaditya.repository.ProjectRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class DeleteProjectCommandImplTest {
  @InjectMocks
  private DeleteProjectCommandImpl command;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private ImageRepository imageRepository;

  @Mock
  private FileHelper fileHelper;

  private Project project;
  private static final String IMAGE = "image";
  private static final String NAME = "name";
  private static final String URL = "url";

  @Before
  public void setUp(){
    this.project = generateProject();
    ReflectionTestUtils.setField(command, "IMAGE_HOST", "http://localhost");
  }

  @Test
  public void testDoExecute(){
    this.mockFindOne();
    this.mockGetRootFilePath();

    command.doExecute("1");

    this.verifyDelete();
    this.verifyFindOne();
    this.verifyDeleteFile();
    this.verifyGetRootFilePath();
  }

  @After
  public void tearDown(){
    verifyNoMoreInteractions(projectRepository);
    verifyNoMoreInteractions(fileHelper);
  }

  private void mockFindOne(){
    when(projectRepository.findOne(anyString())).thenReturn(project);
  }

  private void mockGetRootFilePath(){
    when(fileHelper.getRootFilePath()).thenReturn(anyString());
  }

  private void verifyFindOne(){
    verify(projectRepository).findOne(anyString());
  }

  private void verifyDelete(){
    verify(projectRepository).delete(project);
    verify(imageRepository).deleteByUrl(anyString());
  }

  private void verifyDeleteFile(){
    verify(fileHelper).deleteFile(anyString());
  }

  private void verifyGetRootFilePath(){
    verify(fileHelper).getRootFilePath();
  }

  private Project generateProject(){
    return Project.builder()
        .url(URL)
        .imageURL(IMAGE)
        .name(NAME)
        .build();
  }
}
