package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.AboutMe;
import com.nantaaditya.model.command.AboutMeCommandRequest;
import com.nantaaditya.repository.AboutMeRepository;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityNotFoundException;
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
public class UpdateAboutMeCommandImplTest {

  @InjectMocks
  private UpdateAboutMeCommandImpl command;

  @Mock
  private AboutMeRepository aboutMeRepository;

  private AboutMeCommandRequest request;
  private AboutMe aboutMe;
  private List<AboutMe> aboutMeList;
  private static final String DESCRIPTION = "description";

  @Before
  public void setUp() {
    aboutMe = generateAboutMe();
    request = generateAboutMeCommandRequest();
    aboutMeList = generateAboutMeList();
  }

  @Test
  public void doExecute() {
    mockFind();
    command.doExecute(request);
    verifyFind();
    verifyUpdateAboutMe(1);
  }

  @Test(expected = EntityNotFoundException.class)
  public void doExecuteThrowError() {
    mockFindNull();
    try {
      command.doExecute(request);
    } catch (Exception e) {
      verifyFind();
      verifyUpdateAboutMe(0);
      throw e;
    }
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(aboutMeRepository);
  }

  private void mockFind() {
    when(aboutMeRepository.findAll()).thenReturn(aboutMeList);
  }

  private void mockFindNull() {
    when(aboutMeRepository.findAll()).thenReturn(null);
  }

  private void verifyFind() {
    verify(aboutMeRepository).findAll();
  }

  private void verifyUpdateAboutMe(int time) {
    verify(aboutMeRepository, times(time)).save(any(AboutMe.class));
  }

  private AboutMeCommandRequest generateAboutMeCommandRequest() {
    return AboutMeCommandRequest.builder().description(DESCRIPTION).build();
  }

  private AboutMe generateAboutMe() {
    return AboutMe.builder().description(DESCRIPTION).build();
  }

  private List<AboutMe> generateAboutMeList() {
    return Arrays.asList(aboutMe);
  }
}
