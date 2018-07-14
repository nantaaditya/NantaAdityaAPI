package com.nantaaditya.service.command.impl;

import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Skill;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.GetSkillCommandResponse;
import com.nantaaditya.repository.SkillRepository;
import java.util.Arrays;
import java.util.List;
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
public class GetSkillCommandImplTest {

  @InjectMocks
  private GetSkillCommandImpl command;

  @Mock
  private SkillRepository skillRepository;

  @Mock
  private MapperHelper mapperHelper;

  private GetSkillCommandResponse commandResponse;
  private Skill skill;
  private List<GetSkillCommandResponse> commandResponses;
  private List<Skill> skills;
  private static final String ID = "id";
  private static final String NAME = "name";
  private static final double PERCENTAGE = 100;

  @Before
  public void setUp() {
    commandResponses = generateCommandResponses();
    skills = generateSkills();
    this.mockGet();
    this.mockToResponse();
  }

  @Test
  public void testDoExecute() {
    command.doExecute(EmptyRequest.getInstance());
    this.verifyGet();
    this.verifyToResponse();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(skillRepository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockGet(){
    when(skillRepository.findAll()).thenReturn(skills);
  }

  private void mockToResponse() {
    when(mapperHelper.mapToList(skills, GetSkillCommandResponse.class))
        .thenReturn(commandResponses);
  }

  private void verifyToResponse() {
    verify(mapperHelper).mapToList(skills, GetSkillCommandResponse.class);
  }

  private void verifyGet() {
    Mockito.verify(skillRepository).findAll();
  }

  private List<GetSkillCommandResponse> generateCommandResponses() {
    commandResponse = GetSkillCommandResponse.builder()
        .id(ID)
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
    return Arrays.asList(commandResponse);
  }

  private List<Skill> generateSkills() {
    skill = Skill.builder()
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
    skill.setId(ID);
    return Arrays.asList(skill);
  }
}
