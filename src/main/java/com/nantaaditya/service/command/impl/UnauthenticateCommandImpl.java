package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Session;
import com.nantaaditya.model.Credential;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.repository.SessionRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.UnauthenticateCommand;
import java.util.Optional;
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
public class UnauthenticateCommandImpl extends
    AbstractCommand<EmptyResponse, EmptyRequest> implements
    UnauthenticateCommand {

  @Autowired
  private SessionRepository sessionRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(EmptyRequest request) {
    removeSession();
    return EmptyResponse.getInstance();
  }

  private void removeSession() {
    Optional.ofNullable(this.findCurrentActiveSession())
        .ifPresent(session -> sessionRepository.delete(session));
  }

  private Session findCurrentActiveSession() {
    return this.sessionRepository.findByUsernameAndSessionId(Credential.getUsername(),
        Credential.getSessionId());
  }
}
