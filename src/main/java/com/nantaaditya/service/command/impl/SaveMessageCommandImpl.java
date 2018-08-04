package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Message;
import com.nantaaditya.enumerated.MessageStateEnum;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.SaveMessageCommandRequest;
import com.nantaaditya.repository.MessageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.SaveMessageCommand;
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
public class SaveMessageCommandImpl extends
    AbstractCommand<EmptyResponse, SaveMessageCommandRequest>
    implements SaveMessageCommand {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EmptyResponse doExecute(SaveMessageCommandRequest request) {
    this.save(this.toEntity(request));
    return EmptyResponse.getInstance();
  }

  private void save(Message message) {
    messageRepository.save(message);
  }

  private Message toEntity(SaveMessageCommandRequest request) {
    Message message = mapperHelper.map(request, Message.class);
    message.setState(MessageStateEnum.UNREAD);
    return message;
  }
}
