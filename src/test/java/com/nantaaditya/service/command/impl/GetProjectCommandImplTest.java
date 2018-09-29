package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Project;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.GetProjectCommandResponse;
import com.nantaaditya.repository.ProjectRepository;
import java.util.Arrays;
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
public class GetProjectCommandImplTest {

  @InjectMocks
  private GetProjectCommandImpl command;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private MapperHelper mapperHelper;

  private GetProjectCommandResponse commandResponse;
  private Project project;
  private static final String NAME = "name";
  private static final String IMAGE = "image";
  private static final String URL = "url";

  @Before
  public void setUp() {
    this.project = generateProject();
    this.commandResponse = generateGetProjectCommandResponse();
  }

  @Test
  public void testDoExecute(){
    this.mockGet();
    this.mockToResponse();;

    command.doExecute(EmptyRequest.getInstance());

    this.verifyGet();
    this.verifyToResponse();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(projectRepository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockGet() {
    when(projectRepository.findAll()).thenReturn(Arrays.asList(project));
  }

  private void mockToResponse() {
    when(mapperHelper.mapToList(Arrays.asList(project), GetProjectCommandResponse.class))
        .thenReturn(Arrays.asList(commandResponse));
  }

  private void verifyGet() {
    verify(projectRepository).findAll();
  }

  private void verifyToResponse() {
    verify(mapperHelper).mapToList(Arrays.asList(project), GetProjectCommandResponse.class);
  }

  private Project generateProject() {
    return Project.builder()
        .name(NAME)
        .imageURL(IMAGE)
        .url(URL)
        .build();
  }

  private GetProjectCommandResponse generateGetProjectCommandResponse() {
    return GetProjectCommandResponse.builder()
        .name(NAME)
        .imageURL(IMAGE)
        .url(URL)
        .build();
  }
}
