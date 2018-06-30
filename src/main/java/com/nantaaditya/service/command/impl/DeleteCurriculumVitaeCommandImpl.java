package com.nantaaditya.service.command.impl;

import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.repository.CurriculumVitaeRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.DeleteCurriculumVitaeCommand;
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
public class DeleteCurriculumVitaeCommandImpl extends
    AbstractCommand<EmptyResponse, String> implements
    DeleteCurriculumVitaeCommand {

  @Autowired
  private CurriculumVitaeRepository curriculumVitaeRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(String id) {
    this.deleteById(id);
    return EmptyResponse.getInstance();
  }

  private void deleteById(String id) {
    curriculumVitaeRepository.delete(id);
  }
}
