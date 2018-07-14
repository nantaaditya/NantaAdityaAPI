package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.*;

import com.nantaaditya.repository.SkillRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class DeleteSkillCommandImplTest {

  @InjectMocks
  private DeleteSkillCommandImpl command;

  @Mock
  private SkillRepository skillRepository;

  private static final String ID = "id";

  @Test
  public void testDoExecute() {
    command.doExecute(ID);
    this.verifyDelete();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(skillRepository);
  }

  private void verifyDelete() {
    Mockito.verify(skillRepository).delete(ID);
  }

}
