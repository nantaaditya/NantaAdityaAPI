package com.nantaaditya.helper;

import com.nantaaditya.entity.User;
import io.jsonwebtoken.Claims;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

public interface JwtHelper {

  String generateJwtToken(User user);

  Claims parseJwtToken(String jwtToken);

  Boolean isAuthorized();
}
