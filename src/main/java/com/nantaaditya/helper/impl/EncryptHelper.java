package com.nantaaditya.helper.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import org.springframework.stereotype.Component;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Component
public class EncryptHelper {

  public static String encryptSHA256(String password) throws Exception {
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    return (new HexBinaryAdapter())
        .marshal(messageDigest.digest(password.getBytes(StandardCharsets.UTF_8)));
  }
}
