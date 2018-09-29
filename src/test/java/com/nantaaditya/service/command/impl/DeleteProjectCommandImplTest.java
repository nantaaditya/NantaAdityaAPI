package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.FileHelper;
import com.nantaaditya.repository.ImageRepository;
import com.nantaaditya.repository.ProjectRepository;
import lombok.Setter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionTemplate;
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
  @Setter
  private TransactionTemplate transactionTemplate;

  @Mock
  private FileHelper fileHelper;

  private Project project;
  private static final String IMAGE = "image";
  private static final String NAME = "name";
  private static final String URL = "url";

  @Before
  public void setUp() {
    this.project = generateProject();
    ReflectionTestUtils.setField(command, "IMAGE_HOST", "http://localhost");
  }

  @Test
  public void testDoExecute() {
    this.mockTransactionTemplate();
    this.mockFindOne();
    this.mockGetRootFilePath();

    command.doExecute("1");

    this.verifyTransactionTemplate();
    this.verifyFindOne();
    this.verifyDeleteFile();
    this.verifyGetRootFilePath();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(projectRepository);
    verifyNoMoreInteractions(fileHelper);
  }

  private void mockFindOne() {
    when(projectRepository.findOne(anyString())).thenReturn(project);
  }

  private void mockGetRootFilePath() {
    when(fileHelper.getRootFilePath()).thenReturn(anyString());
  }

  private void mockTransactionTemplate() {
    when(transactionTemplate.execute(any())).thenReturn(true);
  }

  private void verifyFindOne() {
    verify(projectRepository).findOne(anyString());
  }

  private void verifyTransactionTemplate() {
    verify(transactionTemplate).execute(any());
  }

  private void verifyDeleteFile() {
    verify(fileHelper).deleteFile(anyString());
  }

  private void verifyGetRootFilePath() {
    verify(fileHelper).getRootFilePath();
  }

  private Project generateProject() {
    return Project.builder()
        .url(URL)
        .imageURL(IMAGE)
        .name(NAME)
        .build();
  }
}
