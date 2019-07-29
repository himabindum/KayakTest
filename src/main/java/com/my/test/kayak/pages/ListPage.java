package com.my.test.kayak.pages;

import com.my.test.kayak.listeners.Logger;
import com.my.test.kayak.testbase.TestBase;
import com.my.test.kayak.utils.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/* This class contains WebElements and Page methods for the search results page*/

public class ListPage extends TestBase {

    @FindBy(xpath = "//div [@class='nearby-checkbox']")
    private List<WebElement> flightD;

    private String flightNumber = "//div[contains(@aria-label,'Result number LABEL:')]";
    private String originOutput = flightNumber+"//ol/li[1]//div[@class='section duration']/div[@class='bottom']/span[1]";
    private String destinationOutput = flightNumber+"//ol/li[1]//div[@class='bottom']/span[3]";
    private String firstFlight= flightNumber+"/descendant::li[1]//div[contains(@id,'-select-leg-wrappe')]//input";
    private String secondFlight= flightNumber+"/descendant::li[2]//div[contains(@id,'-select-leg-wrappe')]//input";

    public ListPage() {
        PageFactory.initElements(driver, this);
    }

    public String getOrigin(String flightNumber){
        String actualFlight = originOutput.replace("LABEL",flightNumber);
        CommonMethods.pollAndWait(driver,actualFlight,1,3);
        return driver.findElement(By.xpath(actualFlight)).getText();
    }

    public String getDestination(String flightNumber){
        String actualFlight = destinationOutput.replace("LABEL",flightNumber);
        CommonMethods.pollAndWait(driver,actualFlight,1,3);
        return driver.findElement(By.xpath(actualFlight)).getText();
    }


    public void selectFLight(String flightNum){

        String flight=firstFlight.replace("LABEL",flightNum);
        CommonMethods.pollAndWait(driver,flight,1,4);
        CommonMethods.click(driver.findElement(By.xpath(flight)));
        String  flight2=secondFlight.replace("LABEL",flightNum);
        CommonMethods.click(driver.findElement(By.xpath(flight2)));
        Logger.log("Flight "+flightNum+ "is Selected");
        CommonMethods.sleep(6000);

    }

}
