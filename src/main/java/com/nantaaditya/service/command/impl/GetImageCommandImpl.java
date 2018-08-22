package com.nantaaditya.service.command.impl;

import com.nantaaditya.entity.Image;
import com.nantaaditya.helper.MapperHelper;
import com.nantaaditya.model.EmptyRequest;
import com.nantaaditya.model.web.GetImageWebResponse;
import com.nantaaditya.repository.ImageRepository;
import com.nantaaditya.service.command.AbstractCommand;
import com.nantaaditya.service.command.GetImageCommand;
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
public class GetImageCommandImpl extends
    AbstractCommand<List<GetImageWebResponse>, EmptyRequest> implements
    GetImageCommand {

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  public List<GetImageWebResponse> doExecute(EmptyRequest emptyRequest) {
    return this.toResponse(this.get());
  }

  private List<GetImageWebResponse> toResponse(List<Image> images) {
    return mapperHelper.mapToList(images, GetImageWebResponse.class);
  }

  private List<Image> get() {
    return imageRepository.findAll();
  }
}
