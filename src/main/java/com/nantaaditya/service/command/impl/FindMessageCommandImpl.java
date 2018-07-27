package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Message;
import com.nantaaditya.enumerated.MessageStateEnum;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.command.GetMessageCommandResponse;
import com.nantaaditya.repository.MessageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.FindMessageCommand;
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
public class FindMessageCommandImpl extends
    AbstractCommand<GetMessageCommandResponse, String> implements
    FindMessageCommand {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public GetMessageCommandResponse doExecute(String id) {
    Message message = this.changeState(this.find(id));
    this.save(message);
    return toResponse(message);
  }

  private Message find(String id) {
    return messageRepository.findOne(id);
  }

  private Message changeState(Message message) {
    if(message.getState().equals(MessageStateEnum.UNREAD))
      message.setState(MessageStateEnum.READ);
    return message;
  }

  private void save(Message message) {
    messageRepository.save(message);
  }

  private GetMessageCommandResponse toResponse(Message message) {
    return mapperHelper.map(message, GetMessageCommandResponse.class);
  }
}
