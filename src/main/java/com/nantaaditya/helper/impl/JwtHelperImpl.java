package com.nantaaditya.helper.impl;

import com.nantaaditya.entity.Session;
import com.nantaaditya.entity.User;
import com.nantaaditya.helper.JwtHelper;
import com.nantaaditya.model.Credential;
import com.nantaaditya.properties.DataSourceProperties;
import com.nantaaditya.properties.SessionProperties;
import com.nantaaditya.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

// @formatter:off
/**
 * Author : Pramuditya Ananta Nur
 * www.nantaaditya.com
 * personal@nantaaditya.com
 **/
// @formatter:on

@Component
public class JwtHelperImpl implements JwtHelper {

  @Autowired
  private DataSourceProperties properties;

  @Autowired
  private SessionRepository sessionRepository;

  @Autowired
  private SessionProperties sessionProperties;

  @Autowired
  private TimeHelper timeHelper;

  @Override
  public String generateJwtToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getUsername());
    claims.put("sessionId", Credential.getSessionId());
    return Jwts.builder().setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, properties.getJwtSecretKey())
        .compact();
  }

  @Override
  public Claims parseJwtToken(String jwtToken) {
    try {
      return Jwts.parser().setSigningKey(properties.getJwtSecretKey()).parseClaimsJws(jwtToken)
          .getBody();
    } catch (JwtException | ClassCastException e) {
      return null;
    }
  }

  @Override
  public Boolean isAuthorized() {
    Session session = sessionRepository
        .findByUsernameAndSessionIdAndCreatedDateGreaterThan(Credential.getUsername(),
            Credential.getSessionId(), getSessionExpiredDate());
    return !ObjectUtils.isEmpty(session);
  }

  private Long getSessionExpiredDate() {
    return timeHelper.getCurrentTimeEpoch() - sessionProperties.getSessionExpiredTimeInMillis();
  }
}
