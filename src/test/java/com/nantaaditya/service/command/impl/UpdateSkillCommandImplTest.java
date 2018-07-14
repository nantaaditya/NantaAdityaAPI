package com.nantaaditya.service.command.impl;
// @formatter:off
import static org.mockito.Mockito.*;

import com.nantaaditya.entity.Skill;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.UpdateSkillCommandRequest;
import com.nantaaditya.repository.SkillRepository;
import javax.persistence.EntityNotFoundException;import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner; /**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
public class UpdateSkillCommandImplTest {

  @InjectMocks
  private UpdateSkillCommandImpl command;

  @Mock
  private SkillRepository skillRepository;

  @Mock
  private MapperHelper mapperHelper;

  private UpdateSkillCommandRequest commandRequest;
  private Skill skill;
  private static final String ID = "id";
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
    this.mockFindById();
    this.mockSave();
    command.doExecute(commandRequest);
    this.verifyFindById();
    this.verifySave(1, skill);
    this.verifyToEntity(1);
  }

  @Test(expected = EntityNotFoundException.class)
  public void testDoExecuteNotFound() {
    try {
      this.mockFindByIdFailed();
      this.mockSaveFailed();
      command.doExecute(commandRequest);
    }catch (Exception e){
      this.verifyFindById();
      this.verifySave(0, null);
      this.verifyToEntity(0);
      throw e;
    }
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(skillRepository);
    verifyNoMoreInteractions(mapperHelper);
  }

  private void mockFindById(){
    when(skillRepository.findOne(anyString())).thenReturn(skill);
  }

  private void mockFindByIdFailed(){
    when(skillRepository.findOne(anyString())).thenReturn(null);
  }

  private void mockSave(){
    when(skillRepository.save(skill)).thenReturn(skill);
  }

  private void mockSaveFailed(){
    when(skillRepository.save(skill)).thenReturn(null);
  }

  private void mockToEntity() {
    when(mapperHelper.map(commandRequest, Skill.class)).thenReturn(skill);
  }

  private void verifyFindById(){
    verify(skillRepository).findOne(anyString());
  }

  private void verifyToEntity(int time) {
    verify(mapperHelper, times(time)).map(commandRequest, Skill.class);
  }

  private void verifySave(int time, Skill skill) {
    Mockito.verify(skillRepository, times(time)).save(skill);
  }

  private UpdateSkillCommandRequest generateCommandRequest() {
    return UpdateSkillCommandRequest.builder()
        .id(ID)
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
  }

  private Skill generateSkill() {
    Skill skill = Skill.builder()
        .name(NAME)
        .percentage(PERCENTAGE)
        .build();
    skill.setId(ID);
    return skill;
  }
}
