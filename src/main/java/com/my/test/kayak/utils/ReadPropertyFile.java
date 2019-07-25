package com.my.test.kayak.utils;

import java.util.ResourceBundle;

public class ReadPropertyFile {

      private static ResourceBundle rb = ResourceBundle.getBundle("config");

      public static String getProperty(String key) {
         return rb.getString(key.toLowerCase());
      }
}
