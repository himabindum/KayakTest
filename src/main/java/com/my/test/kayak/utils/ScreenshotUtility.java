package com.my.test.kayak.utils;

import com.my.test.kayak.listeners.Logger;
import com.my.test.kayak.testbase.TestBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Utility to take screenshots for failed test cases
 */

public class ScreenshotUtility extends TestBase {

    public static String screenShotLocation() {

        String testResultPath = System.getProperty("user.dir") + File.separator + "Output_ExtentReport" + File.separator + "screenshots";
        File dir = new File(testResultPath);

        if (!dir.isDirectory()) {
            boolean dir1 = dir.mkdirs();
            System.out.println("ScreenShot Directory created" + dir1 + "   " + testResultPath);
        }
        boolean dir1 = dir.mkdirs();
        System.out.println("Deleted Old and Created new Directory= " + dir1 + "  Path=  " + testResultPath);
        return testResultPath;
    }

    public static String createScreenshot(String testName) {

        String screenshotLocation = screenShotLocation() + File.separator + testName + "_"+getCurrentDateTime()+".png";
        TakesScreenshot ts = (TakesScreenshot) driver;
        System.out.println(" Taking Screenshot of test : " + testName);
        try {
            Thread.sleep(2000);
            FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), new File(screenshotLocation));
//            takeFullPageScreenshot(testName);
            return screenshotLocation;
        } catch (WebDriverException e) {
            Logger.log(e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            Logger.log(e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * It is used to create screenshot using AShot
     * @param fileName
     */

    public static void takeFullPageScreenshot(String fileName){

        Screenshot screenshot= new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(),"PNG",new File(screenShotLocation() + File.separator + fileName + "112.png"));
        } catch (IOException e) {
            Logger.log(e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getCurrentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd_HH-mm-ss");
        Date date = new Date();
        return (dateFormat.format(date));
    }
}