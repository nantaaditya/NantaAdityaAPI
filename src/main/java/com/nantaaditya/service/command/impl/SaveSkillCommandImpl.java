package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Skill;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveSkillCommandRequest;
import com.nantaaditya.repository.SkillRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.SaveSkillCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class SaveSkillCommandImpl extends
    AbstractCommand<EmptyResponse, SaveSkillCommandRequest> implements
    SaveSkillCommand {

  @Autowired
  private SkillRepository skillRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(SaveSkillCommandRequest request) {
    this.save(this.toEntity(request));
    return EmptyResponse.getInstance();
  }

  private void save(Skill skill){
    skillRepository.save(skill);
  }

  private Skill toEntity(SaveSkillCommandRequest request){
    return mapperHelper.map(request, Skill.class);
  }
}
