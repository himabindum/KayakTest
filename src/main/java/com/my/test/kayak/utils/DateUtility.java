package com.my.test.kayak.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtility {

        public static String generateDate(int noOfDays){

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            localDate= localDate.plusDays(noOfDays);
            DateTimeFormatter dateFormatter3 = DateTimeFormatter.ofPattern("dd/mm/yyyy");
            String s1=localDate.format(dateFormatter3);
            return s1;
        }

        public static void getDateRange(int noOfNights){

        }

        public static String getDate(int noOfdays){
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/uuuu");
            localDate=localDate.plusDays(noOfdays);
            return localDate.format(dateFormatter);
        }

}