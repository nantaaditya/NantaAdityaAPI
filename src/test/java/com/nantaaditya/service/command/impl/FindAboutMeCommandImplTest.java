package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.AboutMe;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.web.AboutMeWebResponse;
import com.nantaaditya.repository.AboutMeRepository;
import java.util.Arrays;
import java.util.List;
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
public class FindAboutMeCommandImplTest {

  @InjectMocks
  private FindAboutMeCommandImpl command;

  @Mock
  private AboutMeRepository aboutMeRepository;

  @Mock
  private MapperHelper mapperHelper;

  private AboutMe aboutMe;
  private AboutMeWebResponse response;
  private List<AboutMe> aboutMeList;
  private static final String DESCRIPTION = "description";

  @Before
  public void setUp() {
    aboutMe = generateAboutMe();
    response = generateAboutMeWebResponse();
    aboutMeList = generateAboutMeList();
    mockFind();
    mockToWebResponse();
  }

  @Test
  public void testDoExecute() {
    command.doExecute(EmptyRequest.getInstance());
    verifyFind();
    verifyToWebResponse();
  }

  private void mockToWebResponse() {
    when(mapperHelper.map(any(AboutMe.class), any())).thenReturn(response);
  }

  private void mockFind() {
    when(aboutMeRepository.findAll()).thenReturn(aboutMeList);
  }

  private void verifyToWebResponse() {
    verify(mapperHelper).map(any(AboutMe.class), any());
  }

  private void verifyFind() {
    verify(aboutMeRepository).findAll();
  }

  private AboutMeWebResponse generateAboutMeWebResponse() {
    return AboutMeWebResponse.builder().description(DESCRIPTION).build();
  }

  private AboutMe generateAboutMe() {
    return AboutMe.builder().description(DESCRIPTION).build();
  }

  private List<AboutMe> generateAboutMeList() {
    return Arrays.asList(aboutMe);
  }
}
