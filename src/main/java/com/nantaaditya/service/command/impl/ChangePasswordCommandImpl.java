package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.User;
import com.nantaaditya.helper.impl.EncryptHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.ChangePasswordCommandRequest;
import com.nantaaditya.repository.UserRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.ChangePasswordCommand;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Service
@Slf4j
public class ChangePasswordCommandImpl extends
    AbstractCommand<EmptyResponse, ChangePasswordCommandRequest> implements
    ChangePasswordCommand {

  @Autowired
  private UserRepository userRepository;


  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(ChangePasswordCommandRequest changePasswordCommandRequest) {
    this.changePassword(changePasswordCommandRequest);
    return EmptyResponse.getInstance();
  }

  private String generatePassword(String password) {
    try {
      return EncryptHelper.encryptSHA256(password);
    } catch (Exception e) {
      log.error("Error generate password {}", e.getMessage());
      return null;
    }
  }

  private void changePassword(ChangePasswordCommandRequest commandRequest) {
    String oldPassword = this.generatePassword(commandRequest.getOldPassword());
    User user = userRepository
        .findByUsernameAndPasswordAndFlagDeleteFalse(commandRequest.getUsername(), oldPassword);
    if (!ObjectUtils.isEmpty(user)) {
      user.setPassword(this.generatePassword(commandRequest.getNewPassword()));
      userRepository.save(user);
    } else {
      throw new EntityNotFoundException("user data not found");
    }
  }
}
