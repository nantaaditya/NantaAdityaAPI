package com.nantaaditya.helper.impl;
// @formatter:off
import static org.junit.Assert.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@RunWith(SpringRunner.class)
@Slf4j
public class EncryptHelperTest {

  private static final String PASSWORD = "password";
  private static final String SHA256_PASSWORD = "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8";

  @Test
  public void testEncryptSHA256() {
    try {
      String result = EncryptHelper.encryptSHA256(PASSWORD);
      assertEquals(SHA256_PASSWORD, result);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
