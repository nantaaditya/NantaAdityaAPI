package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Skill;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.GetSkillCommandResponse;
import com.nantaaditya.repository.SkillRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.GetSkillCommand;
import java.util.List;
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
public class GetSkillCommandImpl extends
    AbstractCommand<List<GetSkillCommandResponse>, EmptyRequest> implements
    GetSkillCommand {

  @Autowired
  private SkillRepository skillRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  public List<GetSkillCommandResponse> doExecute(EmptyRequest emptyRequest) {
    return this.toResponse(this.get());
  }

  private List<GetSkillCommandResponse> toResponse(List<Skill> skills) {
    return mapperHelper.mapToList(skills, GetSkillCommandResponse.class);
  }

  private List<Skill> get() {
    return skillRepository.findAll();
  }
}
