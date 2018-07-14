package com.nantaaditya.service.command.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.nantaaditya.repository.CurriculumVitaeRepository;
import org.junit.After;
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
public class DeleteCurriculumVitaeCommandImplTest {

  @InjectMocks
  private DeleteCurriculumVitaeCommandImpl command;

  @Mock
  private CurriculumVitaeRepository curriculumVitaeRepository;

  private static final String ID = "id";

  @Test
  public void testDoExecute() {
    this.command.doExecute(ID);
    verifyDelete();
  }

  @After
  public void tearDown() {
    this.verifyNoMoreInterraction();
  }

  private void verifyDelete() {
    verify(curriculumVitaeRepository).delete(anyString());
  }

  private void verifyNoMoreInterraction() {
    verifyNoMoreInteractions(curriculumVitaeRepository);
  }
}
