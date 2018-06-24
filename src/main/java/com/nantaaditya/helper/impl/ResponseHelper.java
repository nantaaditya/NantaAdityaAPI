package com.nantaaditya.helper.impl;

import com.nantaaditya.model.Response;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public class ResponseHelper {

  public static <T> Response<T> ok(String requestId, String message, T data) {
    Assert.notNull(data, "Data must not null");
    return ResponseHelper.status(HttpStatus.OK, true, requestId, message, data);
  }

  public static <T> Response<T> error(HttpStatus httpStatus, String requestId, String message) {
    return ResponseHelper.status(httpStatus, false, requestId, message, null);
  }

  public static <T> Response<T> error(HttpStatus httpStatus, String requestId, String message,
      Map<String, String> error) {
    return Response.<T>builder()
        .success(false)
        .requestId(requestId)
        .code(httpStatus.value())
        .message(message)
        .errors(error)
        .build();
  }

  public static <T> Response<T> status(HttpStatus httpStatus, Boolean success, String requestId,
      String message, T data) {
    return Response.<T>builder()
        .code(httpStatus.value())
        .success(success)
        .requestId(requestId)
        .message(message)
        .data(data)
        .build();
  }

}
