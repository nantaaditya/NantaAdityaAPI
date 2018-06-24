package com.nantaaditya.helper.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Component
public class TimeHelper {

  public LocalDateTime getCurrentTime() {
    return LocalDateTime.now();
  }

  public Long toEpochMillis(LocalDateTime localDateTime) {
    return localDateTime
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli();
  }

  public Long getCurrentTimeEpoch() {
    return toEpochMillis(getCurrentTime());
  }
}
