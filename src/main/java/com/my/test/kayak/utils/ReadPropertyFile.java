package com.my.test.kayak.utils;

import java.util.ResourceBundle;

/*
 * Utility to read the properties from configuration file.
 */

public class ReadPropertyFile {

      private static ResourceBundle rb = ResourceBundle.getBundle("config");

      public static String getProperty(String key) {
         return rb.getString(key.toLowerCase());
      }
}
