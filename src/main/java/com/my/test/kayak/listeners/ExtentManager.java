package com.my.test.kayak.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.openqa.selenium.io.FileHandler.delete;

public class ExtentManager {

    private static ExtentReports extent;
    private static String reportFileName = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) +
            "-UI-Test-Automaton-Report.html";
    private static String fileSeparator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") +fileSeparator+ "Output_ExtentReport";
    private static String reportFileLocation =  reportFilepath +fileSeparator+ reportFileName;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    public static ExtentReports createInstance() {

        String fileName = getReportPath(reportFilepath);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }

    private static String getReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            delete(testDirectory);
            testDirectory.mkdir();
            System.out.println("Deleting old and creating new directory: " + path);
        }
        return reportFileLocation;
    }



}