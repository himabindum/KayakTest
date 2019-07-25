package com.my.test.kayak.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.my.test.kayak.listeners.Logger;
import com.my.test.kayak.testbase.TestBase;
import com.my.test.kayak.utils.CommonMethods;

/* This class containts WebElements and Page methods */

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

    @FindBy(xpath = "//div [@class='nearby-checkbox']")
    private List<WebElement> includeNearby;

    private String suggestedPlace="//ul[@class='flight-smarty']//div[contains(text(),'LABEL,')]";

    private String flightNumber = "//div[contains(@aria-label,'Result number LABEL:')]";
    
    private String originOutput = "//div[contains(@aria-label,'Result number LABEL:')]/div[@class='resultWrapper']" +
            "/div[@class='resultInner']/div/div[1]//div[@class='mainInfo']/div/ol/li[1]/div/div/" +
            "div[@class='section duration']/div[@class='bottom']/span[1]";
    
    private String destinationOutput = "//div[contains(@aria-label,'Result number LABEL:')]/div[@class='resultWrapper']" +
            "/div[@class='resultInner']/div/div[1]//div[@class='mainInfo']/div/ol/li[1]/div/div/" +
            "div[@class='section duration']/div[@class='bottom']/span[3]";

    private String firstFlight= flightNumber+"/descendant::li[1]//div[contains(@id,'-select-leg-wrappe')]//input";
    
    private String secondFlight= flightNumber+"/descendant::li[2]//div[contains(@id,'-select-leg-wrappe')]//input";

    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    public void enterOrigin(String from){
        String city=suggestedPlace.replace("LABEL",from);
        origin.click();
        clearDefaultOrigin();
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
        CommonMethods.sleep(7000);
        try {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ESCAPE).build().perform();
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
    public String getOrigin(String flightNumber){
        try{
            changeTab();
        }
        catch (Exception e ){

        }
        String actualFlight = originOutput.replace("LABEL",flightNumber);
        CommonMethods.pollAndWait(driver,actualFlight,1,3);
        return driver.findElement(By.xpath(actualFlight)).getText();
    }

    public String getDestination(String flightNumber){
        String actualFlight = destinationOutput.replace("LABEL",flightNumber);
        CommonMethods.pollAndWait(driver,actualFlight,1,3);

        String t="Current Destin "+driver.findElement(By.xpath(actualFlight)).getText();
        System.out.println(t);
        return driver.findElement(By.xpath(actualFlight)).getText();
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

    public void selectFLight(String flightNum){
        String flight=firstFlight.replace("LABEL",flightNum);
        CommonMethods.pollAndWait(driver,flight,1,4);
        CommonMethods.click(driver.findElement(By.xpath(flight)));
        String  flight2=secondFlight.replace("LABEL",flightNum);
        CommonMethods.click(driver.findElement(By.xpath(flight2)));
        Logger.log("Flight "+flightNum+ "is Selected");
    }

    public void startDate(String sDate, String eDate){

        CommonMethods.pollAndWait(driver,"//div[contains(@id,'dateRangeInput-display-start-inner')]",1,7);
        CommonMethods.click(driver.findElement(By.xpath("//div[contains(@id,'dateRangeInput-display-start-inner')]")));
        CommonMethods.pollAndWait(driver,"//div[contains(@id,'-depart-input')]",1,4);
        driver.findElement(By.xpath("//div[contains(@id,'-depart-input')]")).sendKeys(sDate,Keys.TAB);
        endDate(eDate);

    }

    public void endDate(String date){
        CommonMethods.pollAndWait(driver,"//div[contains(@id,'-return-input')]",1,4);
        driver.findElement(By.xpath("//div[contains(@id,'-return-input')]")).sendKeys(date,Keys.ENTER);

    }
}
