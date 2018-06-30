package com.nantaaditya.service.command.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.nantaaditya.entity.CurriculumVitae;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.web.CurriculumVitaeWebResponse;
import com.nantaaditya.repository.CurriculumVitaeRepository;
import java.util.Arrays;
import java.util.List;
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
public class GetCurriculumVitaeCommandImplTest {

  @InjectMocks
  private GetCurriculumVitaeCommandImpl command;

  @Mock
  private CurriculumVitaeRepository curriculumVitaeRepository;

  @Mock
  private MapperHelper mapperHelper;

  private CurriculumVitae curriculumVitae;
  private CurriculumVitaeWebResponse curriculumVitaeWebResponse;
  private List<CurriculumVitae> curriculumVitaes;
  private List<CurriculumVitaeWebResponse> curriculumVitaeWebResponses;
  private static final String NAME = "name";
  private static final String TIME_START = "time start";
  private static final String TIME_END = "time end";
  private static final String DESCRIPTION = "description";

  @Before
  public void setUp() {
    this.curriculumVitae = generateCurriculumVitae();
    this.curriculumVitaeWebResponse = generateCurriculumVitaeWebResponse();
    this.curriculumVitaes = generateListCurriculumVitae();
    this.curriculumVitaeWebResponses = generateListCurriculumVitaeWebResponse();
    this.mockFindAll();
    this.mockToResponse();
  }

  @Test
  public void testDoExecute() {
    List<CurriculumVitaeWebResponse> result = command.doExecute(EmptyRequest.getInstance());
    this.assertResponse(result);
    this.verifyFindAll();
    this.verifyToResponse();
    this.verifyNoMoreInterraction();
  }

  @After
  public void tearDown() {

  }

  private void mockFindAll() {
    when(curriculumVitaeRepository.findAll()).thenReturn(curriculumVitaes);
  }

  private void mockToResponse() {
    when(mapperHelper.mapToList(any(List.class), any())).thenReturn(curriculumVitaeWebResponses);
  }

  private void assertResponse(List<CurriculumVitaeWebResponse> responses) {
    CurriculumVitaeWebResponse result = responses.get(0);
    assertEquals(NAME, result.getName());
    assertEquals(TIME_START, result.getTimeStart());
    assertEquals(TIME_END, result.getTimeEnd());
    assertEquals(DESCRIPTION, result.getDescription());
  }

  private void verifyFindAll() {
    verify(curriculumVitaeRepository).findAll();
  }

  private void verifyToResponse() {
    verify(mapperHelper).mapToList(any(List.class), any());
  }

  private void verifyNoMoreInterraction() {
    verifyNoMoreInteractions(curriculumVitaeRepository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private CurriculumVitae generateCurriculumVitae() {
    return CurriculumVitae.builder().name(NAME).timeStart(TIME_START).timeEnd(TIME_END)
        .description(DESCRIPTION)
        .build();
  }

  private CurriculumVitaeWebResponse generateCurriculumVitaeWebResponse() {
    return CurriculumVitaeWebResponse.builder().name(NAME).timeStart(TIME_START).timeEnd(TIME_END)
        .description(DESCRIPTION).build();
  }

  private List<CurriculumVitae> generateListCurriculumVitae() {
    return Arrays.asList(curriculumVitae);
  }

  private List<CurriculumVitaeWebResponse> generateListCurriculumVitaeWebResponse() {
    return Arrays.asList(curriculumVitaeWebResponse);
  }
}
