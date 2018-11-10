package com.nantaaditya.model.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

@Data
@Builder
public class PushNotificationWebRequest {
  @JsonProperty("app_id")
  private String appId;
  private String url;
  private int ttl;
  @JsonProperty("small_icon")
  private String smallIcon;
  @JsonProperty("large_icon")
  private String largeIcon;
  @JsonProperty("chrome_web_icon")
  private String chromeWebIcon;
  @JsonProperty("chrome_web_badge")
  private String chromeWebBadge;
  @JsonProperty("big_picture")
  private String bigPicture;
  @JsonProperty("chrome_web_image")
  private String chromeWebImage;
  @JsonProperty("chrome_big_picture")
  private String chromeBigPicture;
  @JsonProperty("included_segments")
  private List<String> includedSegments;
  @JsonProperty("headings")
  private HeadingsWebRequest headings;
  @JsonProperty("contents")
  private ContentsWebRequest contents;

  @Data
  @Builder
  public static class HeadingsWebRequest{
    private String id;
    private String en;
  }

  @Data
  @Builder
  public static class ContentsWebRequest{
    private String id;
    private String en;

  }
}
