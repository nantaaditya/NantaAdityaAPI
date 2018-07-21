package com.nantaaditya.helper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nantaaditya.helper.MapperHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on
@Slf4j
@Component
public class MapperHelperImpl implements MapperHelper {

  @Autowired
  private Mapper mapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public <K, T, S> Map<K, T> map(Map<K, S> map, Class<T> clazz) {
    if (!map.isEmpty()) {
      Map<K, T> result = new HashMap<>();

      if (CollectionHelper.isNotEmpty(map)) {
        map.entrySet().forEach(entry -> {
          result.put(entry.getKey(), this.map(entry.getValue(), clazz));
        });
      }
      return result;
    }
    return null;
  }

  @Override
  public <S, T> T map(S s, Class<T> clazz) {
    return s == null ? null : this.mapper.map(s, clazz);
  }

  @Override
  public <S, T> List<T> mapToList(Collection<S> coll, Class<T> clazz) {
    if (!coll.isEmpty()) {
      List<T> result = new ArrayList<>();

      if (CollectionHelper.isNotEmpty(coll)) {
        coll.forEach(s -> {
          result.add(this.map(s, clazz));
        });
      }
      return result;
    }
    return Collections.EMPTY_LIST;
  }

  @Override
  public <T> T readValue(String source, Class<T> targetClass) {
    try {
      return source == null ? null : this.objectMapper.readValue(source, targetClass);
    } catch (IOException e) {
      log.error("could not write value from {}", source);
      return null;
    }
  }

  @Override
  public <T> String writeToString(T t) {
    try {
      return t == null ? null : this.objectMapper.writeValueAsString(t);
    } catch (JsonProcessingException e) {
      log.error("could not write value from {}", t);
      return null;
    }
  }
}
