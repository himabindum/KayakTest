package com.my.test.kayak.listeners;

import com.aventstack.extentreports.Status;
import com.my.test.kayak.utils.ScreenshotUtility;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class TestListener implements ITestListener {

    public void onStart(ITestContext context) {
        System.out.println("*** Test Suite " + context.getName() + " started ***");
    }

    public void onFinish(ITestContext context) {
        System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
        ExtentTestManager.endTest();
        ExtentManager.getInstance().flush();
    }

    public void onTestStart(ITestResult result) {
        System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
        ExtentTestManager.startTest(result.getMethod().getMethodName()+ "->" +
                result.getMethod().getDescription());
        Logger.log(result.getMethod().getMethodName() + "_" + result.getMethod().getDescription() + " test is started");
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfull...");
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }

    public void onTestFailure(ITestResult result){

        System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
        try {
            String fileName = ScreenshotUtility.createScreenshot(result.getMethod().getMethodName() + "_" +
                    result.getMethod().getDescription());
            String stackTrace= new String();

            for(StackTraceElement err: result.getThrowable().getStackTrace()){
                stackTrace=stackTrace+"<br/>"+err.toString();
            }
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed <br/><br/>"+ result.getThrowable()+"<br/>"
                    + stackTrace);
            Logger.log("Screenshot location : " + fileName);
            ExtentTestManager.getTest().fail("Screenshot below: " +
                    ExtentTestManager.getTest().addScreenCaptureFromPath(fileName));
        } catch (IOException e) {
            Logger.log("Unable to attach screenshot");
        }
        catch (Exception e){
            
        }
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }
}
