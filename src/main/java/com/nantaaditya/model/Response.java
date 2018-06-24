package com.nantaaditya.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {

  private Integer code;
  private Boolean success = false;
  private String requestId;
  private String message;
  private T data;
  private Map<String, String> errors;

}
