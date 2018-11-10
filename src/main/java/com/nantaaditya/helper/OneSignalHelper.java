package com.nantaaditya.helper;

import com.nantaaditya.model.web.PushNotificationWebRequest;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public interface OneSignalHelper {

  void createNotification(PushNotificationWebRequest webRequest);
}
