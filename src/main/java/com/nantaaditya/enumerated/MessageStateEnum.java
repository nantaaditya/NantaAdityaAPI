package com.nantaaditya.enumerated;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public enum MessageStateEnum {
  UNREAD("unread"), READ("read"), REPLIED("replied");

  private final String text;

  MessageStateEnum(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
