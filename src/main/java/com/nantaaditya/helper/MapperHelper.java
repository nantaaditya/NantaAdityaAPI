package com.nantaaditya.helper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public interface MapperHelper {

  <K, T, S> Map<K, T> map(Map<K, S> map, Class<T> clazz);

  <S, T> T map(S s, Class<T> clazz);

  <S, T> List<T> mapToList(Collection<S> coll, Class<T> clazz);

  <T> T readValue(String source, Class<T> targetClass);

  <T> String writeToString(T t);
}

