package com.my.test.kayak.listeners;

import com.aventstack.extentreports.Status;

public class Logger extends ExtentTestManager{

    public static void log(String message){
        ExtentTestManager.getTest().log(Status.INFO, message);
    }

    public static void logError(String message){
        ExtentTestManager.getTest().log(Status.ERROR, message);
    }

    public static void logFatalError(String message){
        ExtentTestManager.getTest().log(Status.FATAL, message);
    }


}
