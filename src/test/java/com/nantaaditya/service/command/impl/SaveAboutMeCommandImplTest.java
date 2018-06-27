package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.AboutMe;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.AboutMeCommandRequest;
import com.nantaaditya.repository.AboutMeRepository;
import java.util.Arrays;
import javax.persistence.EntityExistsException;
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
public class SaveAboutMeCommandImplTest {

  @InjectMocks
  private SaveAboutMeCommandImpl command;

  @Mock
  private AboutMeRepository aboutMeRepository;

  @Mock
  private MapperHelper mapperHelper;

  private AboutMeCommandRequest request;
  private AboutMe aboutMe;
  private static final String DESCRIPTION = "description";

  @Before
  public void setUp() {
    aboutMe = generateAboutMe();
    request = generateAboutMeCommandRequest();
    mockToEntity();
  }

  @Test
  public void testDoExecute() {
    mockIsNotExistTrue();
    command.doExecute(request);
    verifyToEntity();
    verifyIsNotExist();
    verifySave(1);
  }

  @Test(expected = EntityExistsException.class)
  public void testDoExecuteExist() {
    mockIsNotExistFalse();
    try {
      command.doExecute(request);
    } catch (Exception e) {
      verifyToEntity();
      verifyIsNotExist();
      verifySave(0);
      throw e;
    }
  }

  @After
  public void tearDown() {
    verifyNoMoreInteraction();
  }

  private void mockIsNotExistFalse() {
    when(aboutMeRepository.findAll()).thenReturn(Arrays.asList(aboutMe));
  }

  private void mockIsNotExistTrue() {
    when(aboutMeRepository.findAll()).thenReturn(Arrays.asList());
  }

  private void mockToEntity() {
    when(mapperHelper.map(any(AboutMeCommandRequest.class), any())).thenReturn(aboutMe);
  }

  private void verifyToEntity() {
    verify(mapperHelper).map(any(AboutMeCommandRequest.class), any());
  }

  private void verifyIsNotExist() {
    verify(aboutMeRepository).findAll();
  }

  private void verifySave(int time) {
    verify(aboutMeRepository, times(time)).save(any(AboutMe.class));
  }

  private void verifyNoMoreInteraction() {
    verifyNoMoreInteractions(mapperHelper);
    verifyNoMoreInteractions(aboutMeRepository);
  }

  private AboutMeCommandRequest generateAboutMeCommandRequest() {
    return AboutMeCommandRequest.builder().description(DESCRIPTION).build();
  }

  private AboutMe generateAboutMe() {
    return AboutMe.builder().description(DESCRIPTION).build();
  }
}
