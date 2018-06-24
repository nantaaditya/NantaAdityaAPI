package com.nantaaditya.properties;

import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.stereotype.Component;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Data
@Component
public class ApplicationSchedulerProperties {

  private Integer corePoolSize = 5;
  private Integer maximumPoolSize = 10;
  private Integer keepAliveTime = 60;
  private TimeUnit unit = TimeUnit.SECONDS;
  private Integer maxQueue = 20;
  private Boolean allowCoreThreadTimeOut = Boolean.TRUE;
}
