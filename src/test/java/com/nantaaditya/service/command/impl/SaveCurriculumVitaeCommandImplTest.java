package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.CurriculumVitae;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.CurriculumVitaeCommandRequest;
import com.nantaaditya.repository.CurriculumVitaeRepository;
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
public class SaveCurriculumVitaeCommandImplTest {

  @InjectMocks
  private SaveCurriculumVitaeCommandImpl command;

  @Mock
  private CurriculumVitaeRepository curriculumVitaeRepository;

  @Mock
  private MapperHelper mapperHelper;

  private CurriculumVitae curriculumVitae;
  private CurriculumVitaeCommandRequest curriculumVitaeCommandRequest;
  private static final String NAME = "name";
  private static final String TIME_START = "time start";
  private static final String TIME_END = "time end";
  private static final String DESCRIPTION = "description";

  @Before
  public void setUp() {
    this.curriculumVitae = generateCurriculumVitae();
    this.curriculumVitaeCommandRequest = generateCurriculumVitaeCommandRequest();
    mockToEntity();
  }

  @Test
  public void testDoExecute() {
    this.command.doExecute(curriculumVitaeCommandRequest);
    this.verifySave();
    this.verifyToEntity();
  }

  @After
  public void tearDown() {
    this.verifyNoMoreInteraction();
  }


  public void mockToEntity() {
    when(this.mapperHelper.map(any(CurriculumVitaeCommandRequest.class), any()))
        .thenReturn(curriculumVitae);
  }

  private void verifySave() {
    verify(this.curriculumVitaeRepository).save(curriculumVitae);
  }

  private void verifyToEntity() {
    verify(this.mapperHelper).map(any(CurriculumVitaeCommandRequest.class), any());
  }

  private void verifyNoMoreInteraction() {
    verifyNoMoreInteractions(this.curriculumVitaeRepository);
    verifyNoMoreInteractions(this.mapperHelper);
  }

  private CurriculumVitae generateCurriculumVitae() {
    return CurriculumVitae.builder().name(NAME).timeStart(TIME_START).timeEnd(TIME_END)
        .description(DESCRIPTION)
        .build();
  }

  private CurriculumVitaeCommandRequest generateCurriculumVitaeCommandRequest() {
    return CurriculumVitaeCommandRequest.builder().name(NAME).timeStart(TIME_START).timeEnd(
        TIME_END)
        .description(DESCRIPTION).build();
  }
}
