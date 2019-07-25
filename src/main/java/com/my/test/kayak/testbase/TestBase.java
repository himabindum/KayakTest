package com.my.test.kayak.testbase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.my.test.kayak.utils.ReadPropertyFile;


public class TestBase {

    public static WebDriver driver;
    
    public static WebDriver launchBrowser() {
        if (ReadPropertyFile.getProperty("browser").equalsIgnoreCase("chrome")) {      
            System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().pageLoadTimeout(6000, TimeUnit.SECONDS);
            try {
                driver.manage().window().maximize();
            }
            catch (Exception e){
            	e.printStackTrace();
            }
        }
        else if (ReadPropertyFile.getProperty("browser").equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver","src/main/resources/geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().timeouts().pageLoadTimeout(6000, TimeUnit.SECONDS);
            try {
                driver.manage().window().maximize();
            }
            catch (Exception e){
            	e.printStackTrace();
            }
        }
        return driver;
    }
 }
