package com.nantaaditya.helper;

import com.nantaaditya.model.web.GoogleCaptchaWebResponse;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public interface GoogleRecaptchaHelper {

  GoogleCaptchaWebResponse validateCaptcha(String response);
}
