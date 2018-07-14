package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.CurriculumVitae;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.CurriculumVitaeCommandRequest;
import com.nantaaditya.repository.CurriculumVitaeRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.SaveCurriculumVitaeCommand;
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
public class SaveCurriculumVitaeCommandImpl extends
    AbstractCommand<EmptyResponse, CurriculumVitaeCommandRequest> implements
    SaveCurriculumVitaeCommand {

  @Autowired
  private CurriculumVitaeRepository curriculumVitaeRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(CurriculumVitaeCommandRequest request) {
    this.save(toEntity(request));
    return EmptyResponse.getInstance();
  }

  private void save(CurriculumVitae entity) {
    this.curriculumVitaeRepository.save(entity);
  }

  private CurriculumVitae toEntity(CurriculumVitaeCommandRequest request) {
    return this.mapperHelper.map(request, CurriculumVitae.class);
  }
}
