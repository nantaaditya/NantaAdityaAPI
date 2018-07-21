package com.nantaaditya.service.command;

import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.command.GetProjectCommandResponse;
import java.util.List;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public interface GetProjectCommand extends Command<List<GetProjectCommandResponse>, EmptyRequest> {

}
