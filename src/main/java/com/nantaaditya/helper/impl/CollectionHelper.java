package com.nantaaditya.helper.impl;
// @formatter:off
import java.util.Collection;import java.util.Map; /**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public class CollectionHelper {

  public static boolean isEmpty(Collection<?> coll) {
    return coll == null || coll.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  public static boolean isNotEmpty(Collection<?> coll) {
    return !isEmpty(coll);
  }

  public static boolean isNotEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }

  public static int sizeOf(Collection<?> coll) {
    return isEmpty(coll) ? 0 : coll.size();
  }
}
