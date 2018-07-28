package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Message;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.GetMessageCommandResponse;
import com.nantaaditya.repository.MessageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.GetMessageCommand;
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
public class GetMessageCommandImpl extends
    AbstractCommand<List<GetMessageCommandResponse>, EmptyRequest> implements
    GetMessageCommand {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  public List<GetMessageCommandResponse> doExecute(EmptyRequest emptyRequest) {
    return this.toResponse(this.get());
  }

  private List<Message> get() {
    return messageRepository.findAllByOrderByStateDesc();
  }

  private List<GetMessageCommandResponse> toResponse(List<Message> messages) {
    return mapperHelper.mapToList(messages, GetMessageCommandResponse.class);
  }
}
