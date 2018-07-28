package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Message;
import com.nantaaditya.enumerated.MessageStateEnum;
import com.nantaaditya.model.EmptyResponse;
import com.nantaaditya.model.command.ReplyMessageCommandRequest;
import com.nantaaditya.repository.MessageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.ReplyMessageCommand;
import java.util.Date;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Slf4j
@Service
public class ReplyMessageCommandImpl extends
    AbstractCommand<EmptyResponse, ReplyMessageCommandRequest> implements ReplyMessageCommand {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  public JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String fromEmail;

  @Value("${nanta.email.cc}")
  private String ccEmail;

  @Override
  @Transactional(rollbackFor = Exception.class)
  @Async
  public EmptyResponse doExecute(ReplyMessageCommandRequest request) {
    this.save(this.find(request.getId()));
    this.reply(request);
    return EmptyResponse.getInstance();
  }

  private Message find(String id) {
    return Optional.ofNullable(messageRepository.findOne(id))
        .orElseThrow(() -> new EntityNotFoundException("Message not found"));
  }

  private void save(Message message) {
    if (message.getState().equals(MessageStateEnum.READ)) {
      message.setState(MessageStateEnum.REPLIED);
      messageRepository.save(message);
    }
  }

  private void reply(ReplyMessageCommandRequest request) {
    SimpleMailMessage message = new SimpleMailMessage();
    try {
      message.setFrom(fromEmail);
      message.setTo(request.getEmail());
      message.setCc(ccEmail);
      message.setSentDate(new Date());
      message.setSubject(request.getSubject());
      message.setText(request.getMessage());

      javaMailSender.send(message);
    } catch (Exception e) {
      log.error("Error sending email to {} caused by {}", request.getEmail(), e.getMessage());
    }
  }
}
