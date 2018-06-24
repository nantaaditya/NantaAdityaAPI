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
public class SessionProperties {

  private Long sessionExpiredTime = 3L;
  private TimeUnit sessionExpiredTimeUnit = TimeUnit.DAYS;

  public Long getSessionExpiredTimeInMillis() {
    return sessionExpiredTimeUnit.toMillis(sessionExpiredTime);
  }
}
