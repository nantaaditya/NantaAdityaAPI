package com.nantaaditya.helper.impl;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on
@Slf4j
@RunWith(SpringRunner.class)
public class TimeHelperTest {

  @InjectMocks
  private TimeHelper timeHelper;
  private LocalDateTime localDateTime = LocalDateTime.now();

  @Test
  public void testGetCurrentTime() {
    timeHelper.getCurrentTime();
  }

  @Test
  public void testToEpochMillis() {
    timeHelper.toEpochMillis(localDateTime);
  }

  @Test
  public void testGetCurrentTimeEpoch() {
    timeHelper.getCurrentTimeEpoch();
  }
}
