package com.nantaaditya.properties;

import java.util.Arrays;
import java.util.List;
// @formatter:off
/**
  * Author : Pramuditya Ananta Nur
  * www.nantaaditya.com
  * personal@nantaaditya.com
  **/
// @formatter:on

public interface FileProperties {

  String RESOURCE = "/resource";
  String WEB_PATH = "/web/";
  String RESOURCE_WEB_PATH = RESOURCE.concat(WEB_PATH);
  String IMG_POSTFIX = "_thumbnail";
  List<String> ALLOWED_IMG_FILE_TYPES =
      Arrays.asList("image/jpeg", "image/png", "image/gif");
  List<String> ALLOWED_IMG_EXTENSION =
      Arrays.asList("jpg", "jpeg", "png", "gif");
  long MAX_IMG_FILE_SIZE = 2 * 1024 * 1024;
}
