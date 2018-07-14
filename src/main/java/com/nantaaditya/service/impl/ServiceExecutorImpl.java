package com.nantaaditya.service.impl;

import com.nantaaditya.service.ServiceExecutor;
import com.nantaaditya.service.command.Command;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Component
public class ServiceExecutorImpl implements ServiceExecutor, ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public <T, R> T execute(
      Class<? extends Command<T, R>> commandClass,
      R request) {
    try {
      Command<T, R> command = applicationContext.getBean(commandClass);
      return command.execute(request);
    } catch (Throwable t) {
      throw t;
    }
  }
}
