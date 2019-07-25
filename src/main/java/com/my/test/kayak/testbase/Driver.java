package com.my.test.kayak.testbase;

import com.my.test.kayak.utils.ReadPropertyFile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Driver extends TestBase{


    public static WebDriver launchBrowser() {

        if (ReadPropertyFile.getProperty("browser").equalsIgnoreCase("chrome")) {
//            WebDriverManager.chromedriver().setup();    // It is used to download chromedriver automatically.
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
//            WebDriverManager.chromedriver().setup();    // It is used to download chromedriver automatically.
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
