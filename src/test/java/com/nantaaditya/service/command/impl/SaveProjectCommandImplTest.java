package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.SaveProjectCommandRequest;
import com.nantaaditya.repository.ProjectRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
  private MapperHelper mapperHelper;


  private SaveProjectCommandRequest request;
  private Project project;
  private static final String NAME = "name";
  private static final String URL = "url";
  private static final String IMAGE = "image";

  @Before
  public void setUp() {
    this.request = generateRequest();
    this.project = generateProject();
  }


  @After
  public void tearDown() {
    verifyNoMoreInteractions(mapperHelper);
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void testDoExecute() throws Exception {
    this.mockToEntity();

    this.command.doExecute(request);

    this.verifySave(1);
    this.verifyToEntity(1);
  }

  private void mockToEntity() {
    when(mapperHelper.map(request, Project.class)).thenReturn(project);
  }

  private void verifyToEntity(int time) {
    verify(mapperHelper, times(time)).map(request, Project.class);
  }

  private void verifySave(int time) {
    verify(repository).save(project);
  }

  private SaveProjectCommandRequest generateRequest() {
    return SaveProjectCommandRequest
        .builder()
        .name(NAME)
        .url(URL)
        .imageURL(IMAGE)
        .build();
  }

  private Project generateProject() {
    return Project.builder()
        .imageURL(IMAGE)
        .name(NAME)
        .url(URL)
        .build();
  }
}
