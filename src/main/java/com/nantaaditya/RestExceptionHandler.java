package com.nantaaditya;

import com.nantaaditya.helper.impl.ResponseHelper;
import com.nantaaditya.model.Response;
import com.nantaaditya.model.exception.BadRequestException;
import com.nantaaditya.model.exception.JwtInvalidTokenException;
import com.nantaaditya.model.exception.UnauthorizedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

  @ResponseBody
  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Response handleThrowable(Throwable throwable, HttpServletRequest request) {
    log.error("{}, caused by {}", throwable.getMessage(), throwable.getCause());
    return ResponseHelper.error(HttpStatus.INTERNAL_SERVER_ERROR, request.getParameter("requestId"),
        throwable.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  public Response handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
    log.error("{} Exception, caused by {}", e.getMessage(), e.getCause());
    return ResponseHelper
        .error(HttpStatus.UNAUTHORIZED, request.getParameter("requestId"), e.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(AuthenticationException.class)
  public Response handleAuthenticationException(AuthenticationException e,
      HttpServletRequest request) {
    log.error("{} Exception, caused by {}", e.getMessage(), e.getCause());
    return ResponseHelper
        .error(HttpStatus.BAD_REQUEST, request.getParameter("requestId"), e.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadCredentialsException.class)
  public Response handleBadCredentialException(BadCredentialsException e,
      HttpServletRequest request) {
    log.error("{} Exception, caused by {}", e.getMessage(), e.getCause());
    return ResponseHelper
        .error(HttpStatus.BAD_REQUEST, request.getParameter("requestId"), e.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public Response handleBadRequestException(BadRequestException e,
      HttpServletRequest request) {
    log.error("{} Exception, caused by {}", e.getMessage(), e.getCause());
    return ResponseHelper
        .error(HttpStatus.BAD_REQUEST, request.getParameter("requestId"), e.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  @ExceptionHandler(JwtInvalidTokenException.class)
  public Response handleInvalidTokenException(JwtInvalidTokenException e,
      HttpServletRequest request) {
    log.error("{} Exception, caused by {}", e.getMessage(), e.getCause());
    return ResponseHelper
        .error(HttpStatus.NOT_ACCEPTABLE, request.getParameter("requestId"), e.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(EntityNotFoundException.class)
  public Response handleEntityNotFoundException(EntityNotFoundException e,
      HttpServletRequest request) {
    log.error("{} Exception, caused by {}", e.getMessage(), e.getCause());
    return ResponseHelper
        .error(HttpStatus.NOT_FOUND, request.getParameter("requestId"), e.getMessage());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Response validationError(MethodArgumentNotValidException e, HttpServletRequest request) {
    log.error("{} Exception, caused by {}", e.getMessage(), e.getCause());
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    Map<String, String> errors = new HashMap<>();

    for (FieldError error : fieldErrors) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    return ResponseHelper
        .error(HttpStatus.BAD_REQUEST, request.getParameter("requestId"), "Bad request", errors);
  }

}
