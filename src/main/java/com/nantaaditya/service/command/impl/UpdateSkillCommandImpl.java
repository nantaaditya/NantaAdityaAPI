package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Skill;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.UpdateSkillCommandRequest;
import com.nantaaditya.repository.SkillRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.UpdateSkillCommand;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
public class UpdateSkillCommandImpl extends
    AbstractCommand<EmptyResponse, UpdateSkillCommandRequest> implements
    UpdateSkillCommand {

  @Autowired
  private SkillRepository skillRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  public EmptyResponse doExecute(UpdateSkillCommandRequest request) {
    Optional.ofNullable(findById(request.getId()))
        .map(skill -> this.save(this.toEntity(request)))
        .orElseThrow(() -> new EntityNotFoundException("Skill not found"));
    return EmptyResponse.getInstance();
  }

  private Skill findById(String id) {
    return skillRepository.findOne(id);
  }

  private Skill save(Skill skill) {
    return skillRepository.save(skill);
  }

  private Skill toEntity(UpdateSkillCommandRequest request) {
    return mapperHelper.map(request, Skill.class);
  }
}
