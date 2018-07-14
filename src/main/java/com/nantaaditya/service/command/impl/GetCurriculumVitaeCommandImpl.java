package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.CurriculumVitae;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.web.CurriculumVitaeWebResponse;
import com.nantaaditya.repository.CurriculumVitaeRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.GetCurriculumVitaeCommand;
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
public class GetCurriculumVitaeCommandImpl extends
    AbstractCommand<List<CurriculumVitaeWebResponse>, EmptyRequest> implements
    GetCurriculumVitaeCommand {

  @Autowired
  private CurriculumVitaeRepository curriculumVitaeRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  public List<CurriculumVitaeWebResponse> doExecute(EmptyRequest emptyRequest) {
    return this.toResponse(findAll());
  }

  private List<CurriculumVitae> findAll() {
    return curriculumVitaeRepository.findAll();
  }

  private List<CurriculumVitaeWebResponse> toResponse(List<CurriculumVitae> responses) {
    return mapperHelper.mapToList(responses, CurriculumVitaeWebResponse.class);
  }
}
