package com.nantaaditya.helper.impl;

import com.nantaaditya.helper.OneSignalHelper;
import com.nantaaditya.model.web.PushNotificationWebRequest;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
public class OneSignalHelperImpl implements OneSignalHelper {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${nanta.onesignal.key}")
  private String ONE_SIGNAL_KEY;

  @Value("${nanta.onesignal.id}")
  private String ONE_SIGNAL_ID;

  @Value("${nanta.onesignal.url}")
  private String ONE_SIGNAL_URL;

  @Override
  public void createNotification(PushNotificationWebRequest webRequest) {
    try {
      HttpEntity<PushNotificationWebRequest> httpEntity =
          new HttpEntity<>(constructRequest(webRequest), constructHeader());
      ResponseEntity response = restTemplate.exchange(ONE_SIGNAL_URL, HttpMethod.POST, httpEntity, Object.class);
      log.info("Response One Signal {}, {}",response.getStatusCode().value(), response.getBody().toString());
    } catch(Exception e) {
      log.info("Failed to create notification {}", e);
    }
  }

  private HttpHeaders constructHeader(){
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", "Basic " + ONE_SIGNAL_KEY);
    return headers;
  }

  private PushNotificationWebRequest constructRequest(PushNotificationWebRequest webRequest){
    webRequest.setAppId(ONE_SIGNAL_ID);
    webRequest.setTtl(60);
    webRequest.setIncludedSegments(Arrays.asList("Subscribed Users"));
    log.info("One Signal Request {}", webRequest);
    return webRequest;
  }
}
