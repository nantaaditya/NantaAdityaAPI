package com.nantaaditya.model;

import org.slf4j.MDC;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public final class Credential {

  private static final String CREDENTIAL_USERNAME = "USERNAME";
  private static final String CREDENTIAL_SESSION_ID = "SESSION_ID";
  private static final String CREDENTIAL_HOSTNAME = "HOSTNAME";
  private static final String CREDENTIAL_REQUEST_ID = "REQUEST_ID";

  private Credential() {
  }

  public static String getUsername() {
    return MDC.get(Credential.CREDENTIAL_USERNAME);
  }

  public static void setUsername(String username) {
    MDC.put(Credential.CREDENTIAL_USERNAME, username);
  }

  public static String getSessionId() {
    return MDC.get(Credential.CREDENTIAL_SESSION_ID);
  }

  public static void setSessionId(String sessionId) {
    MDC.put(Credential.CREDENTIAL_SESSION_ID, sessionId);
  }

  public static String getHostname() {
    return MDC.get(Credential.CREDENTIAL_HOSTNAME);
  }

  public static void setHostname(String hostname) {
    MDC.put(Credential.CREDENTIAL_HOSTNAME, hostname);
  }

  public static String getRequestId() {
    return MDC.get(Credential.CREDENTIAL_REQUEST_ID);
  }

  public static void setRequestId(String requestId) {
    MDC.put(Credential.CREDENTIAL_REQUEST_ID, requestId);
  }

}
