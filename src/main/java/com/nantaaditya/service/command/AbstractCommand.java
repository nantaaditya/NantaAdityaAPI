package com.nantaaditya.service.command;

import com.nantaaditya.model.command.CommandRequest;
import com.nantaaditya.model.exception.CommandValidationException;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public abstract class AbstractCommand<RESPONSE, REQUEST extends CommandRequest>
    implements Command<RESPONSE, REQUEST>, ApplicationContextAware, InitializingBean {

  protected Validator validator;

  protected ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.validator = applicationContext.getBean(Validator.class);
  }

  @Override
  public RESPONSE execute(REQUEST request) {
    Set<ConstraintViolation<REQUEST>> constraintViolations = validator.validate(request);
    if (constraintViolations.isEmpty()) {
      return doExecute(request);
    } else {
      throw new CommandValidationException(constraintViolations);
    }
  }

  protected <REQUEST, RESPONSE> RESPONSE copyProperties(REQUEST source, RESPONSE target) {
    BeanUtils.copyProperties(source, target);
    return target;
  }

  protected <REQUEST, RESPONSE> List<RESPONSE> copyProperties(List<REQUEST> sources,
      Supplier<RESPONSE> target) {
    return sources.stream()
        .map(source -> copyProperties(source, target.get()))
        .collect(Collectors.toList());
  }

  public abstract RESPONSE doExecute(REQUEST request);

}
