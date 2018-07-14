package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Skill;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.SaveSkillCommandRequest;
import com.nantaaditya.repository.SkillRepository;
import org.junit.After;
import org.junit.Before;
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
public class SaveSkillCommandImplTest {

  @InjectMocks
  private SaveSkillCommandImpl command;

  @Mock
  private SkillRepository skillRepository;

  @Mock
  private MapperHelper mapperHelper;

  private SaveSkillCommandRequest commandRequest;
  private Skill skill;
  private static final String NAME = "name";
  private static final double PERCENTAGE = 100;

  @Before
  public void setUp() {
    commandRequest = generateCommandRequest();
    skill = generateSkill();
    this.mockToEntity();
  }

  @Test
  public void testDoExecute() {
    command.doExecute(commandRequest);
    this.verifySave();
    this.verifyToEntity();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(skillRepository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockToEntity() {
    when(mapperHelper.map(commandRequest, Skill.class)).thenReturn(skill);
  }

  private void verifyToEntity() {
    verify(mapperHelper).map(commandRequest, Skill.class);
  }

  private void verifySave() {
    Mockito.verify(skillRepository).save(skill);
  }

  private SaveSkillCommandRequest generateCommandRequest() {
    return SaveSkillCommandRequest.builder()
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
  }

  private Skill generateSkill() {
    return Skill.builder()
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
  }
}
