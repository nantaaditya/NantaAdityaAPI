package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Session;
import com.nantaaditya.entity.User;
import com.nantaaditya.helper.JwtHelper;
import com.nantaaditya.helper.impl.EncryptHelper;
import com.nantaaditya.model.Credential;
import com.nantaaditya.model.command.AuthenticationCommandRequest;
import com.nantaaditya.repository.SessionRepository;
import com.nantaaditya.repository.UserRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.AuthenticateCommand;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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
@Slf4j
public class AuthenticateCommandImpl extends
    AbstractCommand<String, AuthenticationCommandRequest> implements
    AuthenticateCommand {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionRepository sessionRepository;

  @Autowired
  private JwtHelper jwtHelper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String doExecute(AuthenticationCommandRequest request) {
    Optional<String> jwtToken = Optional.of(checkUserExist(request)).map(user -> {
      removeOldSession(request);
      createNewSession(request);
      return jwtHelper.generateJwtToken(user);
    });

    return jwtToken.get();
  }

  private User checkUserExist(AuthenticationCommandRequest request) {
    String password = this.encryptPassword(request);
    return Optional.ofNullable(userRepository
        .findByUsernameAndPasswordAndFlagDeleteFalse(request.getUsername(), password))
        .orElseThrow(() -> new BadCredentialsException("Invalid username/password"));
  }

  private String encryptPassword(AuthenticationCommandRequest request) {
    try {
      return EncryptHelper.encryptSHA256(request.getPassword());
    } catch (Exception e) {
      log.error("{} : Error generate password {}", this.getClass(), e.getCause());
      return null;
    }
  }

  private void removeOldSession(AuthenticationCommandRequest request) {
    Optional.ofNullable(request.getUsername())
        .map(sessionRepository::findByUsername)
        .ifPresent(sessionRepository::delete);
  }

  private void createNewSession(AuthenticationCommandRequest request) {
    Session session = Session.builder()
        .username(request.getUsername())
        .hostname(Credential.getHostname())
        .sessionId(Credential.getSessionId())
        .build();

    sessionRepository.saveAndFlush(session);
  }

}
