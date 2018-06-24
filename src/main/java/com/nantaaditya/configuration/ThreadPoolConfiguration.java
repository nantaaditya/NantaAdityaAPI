package com.nantaaditya.configuration;

import com.nantaaditya.properties.ApplicationSchedulerProperties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Configuration
public class ThreadPoolConfiguration {

  @Autowired
  private ApplicationSchedulerProperties properties;

  @Bean
  public ThreadPoolExecutor applicationThreadPool() {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        properties.getCorePoolSize(),
        properties.getMaximumPoolSize(),
        properties.getKeepAliveTime(),
        properties.getUnit(),
        new LinkedBlockingQueue<>(properties.getMaxQueue())
    );

    threadPoolExecutor.allowCoreThreadTimeOut(properties.getAllowCoreThreadTimeOut());
    return threadPoolExecutor;
  }
}
