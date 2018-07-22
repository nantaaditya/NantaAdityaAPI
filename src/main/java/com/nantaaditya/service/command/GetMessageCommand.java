package com.nantaaditya.service.command;

import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.GetMessageCommandResponse;
import java.util.List;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public interface GetMessageCommand extends Command<List<GetMessageCommandResponse>, EmptyRequest> {

}
