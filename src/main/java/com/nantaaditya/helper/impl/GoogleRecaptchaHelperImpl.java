package com.nantaaditya.helper.impl;

import com.nantaaditya.helper.GoogleRecaptchaHelper;
import com.nantaaditya.model.web.GoogleCaptchaWebResponse;
import com.nantaaditya.properties.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Component
@Slf4j
public class GoogleRecaptchaHelperImpl implements GoogleRecaptchaHelper {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private DataSourceProperties properties;

  private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

  @Override
  public GoogleCaptchaWebResponse validateCaptcha(String response) {
    MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
    request.add("secret", properties.getCaptchaServerKey());
    request.add("response", response);
    try {
      return restTemplate
          .postForObject(RECAPTCHA_VERIFY_URL, request, GoogleCaptchaWebResponse.class);
    } catch (Exception e) {
      log.error("google recaptcha error {}", e.getMessage());
      return null;
    }
  }
}
