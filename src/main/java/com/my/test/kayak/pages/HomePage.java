package com.my.test.kayak.pages;

import com.my.test.kayak.listeners.Logger;
import com.my.test.kayak.testbase.TestBase;
import com.my.test.kayak.utils.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* This class contains WebElements and Page methods */

public class HomePage extends TestBase {

    @FindBy(xpath = "//div[contains(@id,'-origin-airport-display')]")
    private WebElement origin;

    @FindBy(xpath = "//input[contains(@id,'-origin-airport')]")
    private WebElement originInput;

    @FindBy(xpath = "//div[contains(@id,'-destination-airport-display')]")
    private WebElement destination;

    @FindBy(xpath = "//input[contains(@id,'-destination-airport')]")
    private WebElement destinationInput;

    @FindBy(xpath = "//div[@class='search-form-inner']/div/div/button[@aria-label='Search flights']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='search-form-inner']/div/div/button[@aria-label='Search flights']")
    private WebElement title;

    @FindBy(xpath = "//div[contains(@id,'-origin-airport-smarty-multi-container')]/div[1]/div[2]")
    private WebElement defaultTo;

    @FindBy(xpath = "//div [@class='nearby-checkbox']/descendant::input")
    private List<WebElement> includeNearby;

    @FindBy(xpath = "//div[contains(@id,'dateRangeInput-display-start-inner')]")
    private WebElement calendar;

    @FindBy(xpath = "//div[contains(@id,'-depart-input')]")
    private WebElement departDate;

    @FindBy(xpath = "//div[contains(@id,'-return-input')]")
    private WebElement returnDate;


    private String suggestedPlace="//ul[@class='flight-smarty']//div[contains(text(),'LABEL,')]";

    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    public void enterOrigin(String from){
        String city=suggestedPlace.replace("LABEL",from);
        origin.click();
        clearDefaultOrigin();
        CommonMethods.pollAndWait(driver,originInput,1,3);
        originInput.sendKeys(from);
        CommonMethods.pollAndWait(driver,city,1,4);
        driver.findElement(By.xpath(city)).click();
        Logger.log("Origin is "+from);
    }

    public void enterDestination(String to){
        String city=suggestedPlace.replace("LABEL",to);
        CommonMethods.pollAndWait(driver,destination,1,3);
        destination.click();
        CommonMethods.pollAndWait(driver,destinationInput,1,3);
        destinationInput.sendKeys(to);
        CommonMethods.pollAndWait(driver,city,1,4);
        driver.findElement(By.xpath(city)).click();
        Logger.log("Destination is " +to);
    }

    public void clickSearch(){
        searchButton.click();
        CommonMethods.sleep(6000);
        try {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ESCAPE).build().perform();
            changeTab();
        }
        catch (Exception e){
        	Logger.log("Error while sending escape keys ");
        }
    }

    public void includeNearbyAirport(String location){

        if(location.equalsIgnoreCase("origin")){
            includeNearby.get(0).click();
        }
        else if(location.equalsIgnoreCase("destination")){
            includeNearby.get(1).click();
        }

    }

    public void enterDetails(Map<String,String> map){
        enterOrigin(map.get("Origin City"));
        enterDestination(map.get("Destination City"));
        startDate(map.get("Departure Date"),map.get("Return Date"));
        clickSearch();

    }

    public void clearDefaultOrigin(){
        try {
            defaultTo.click();
        }
        catch (Exception e){
        	Logger.log("Error while clicking the default origin input element ");
        }
        try {
            origin.sendKeys(Keys.DELETE);
        }
        catch (Exception e){
        	Logger.log("Error while deleting the default origin input element ");
        }

    }

    public void changeTab(){
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        CommonMethods.sleep(6000);
        try {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ESCAPE).build().perform();
        }
        catch (Exception e){
        	Logger.log("Error while changing tabs to close the modal window ");
        }
    }



    public void startDate(String sDate, String eDate){

        CommonMethods.pollAndWait(driver,calendar,1,7);
        CommonMethods.click(calendar);
        CommonMethods.pollAndWait(driver,departDate,1,4);
        departDate.sendKeys(sDate,Keys.TAB);
        endDate(eDate);
    }

    public void endDate(String date){
        CommonMethods.pollAndWait(driver,returnDate,1,4);
        returnDate.sendKeys(date,Keys.ENTER);
    }

}
