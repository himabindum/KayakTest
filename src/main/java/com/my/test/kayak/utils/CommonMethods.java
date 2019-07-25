package com.my.test.kayak.utils;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.my.test.kayak.listeners.Logger;
import com.my.test.kayak.testbase.TestBase;

/*
 * This class handles the functions which can be commonly used across the entire framework
 */

public class CommonMethods extends TestBase {

    static WebDriverWait wait;
    Select select;;
    private static int TIMEOUT_COUNT=40;
    private static Exception exception;


    public static void waitForElementToLoad(int time){
        driver.manage().timeouts().implicitlyWait(time, SECONDS);

    }

    public static void waitForPageToLoad(){
        driver.manage().timeouts().pageLoadTimeout(6, SECONDS);

    }

    public static void sleep(long milliSeconds){
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForElementToLoad(){
        driver.manage().timeouts().implicitlyWait(3, SECONDS);

    }

  /*  public static void waitForElementAvailability(WebElement element){
        wait = new WebDriverWait(driver,10);
        WebElement element1=  wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void enterValue(WebElement element, String inputText){
        waitForElementAvailability(element);
        element.sendKeys(inputText);
    }
*/
    public static List getDate(int noOfDays){
        String date =DateUtility.generateDate(noOfDays);
        List <String> date1=new ArrayList();
        date1.add(date.substring(0,2));
        date1.add(date.substring(3));
        return date1;
    }

    public void select(WebElement element, String text){
        select = new Select(element);
        select.selectByVisibleText(text);
    }

    public void navigateBack(){
        driver.navigate().back();
    }

    public void navigateForward(){
        driver.navigate().forward();
    }

    public String getTitleOfPage(){
        return driver.getTitle();
    }

    public String findCurrentURL(){
        return driver.getCurrentUrl();
    }

    public void refreshPage(){
        driver.navigate().refresh();
    }

    public static void click(WebElement element){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public boolean checkIfButtonIsEnabled(WebElement button){
        return checkIfElementIsEnabled(button);
    }

    public  boolean checkIfElementIsEnabled(WebElement element){
        return element.isEnabled();
    }

    public static boolean checkIfElementIsDisplayed(WebElement element){
        return element.isDisplayed();
    }

    public boolean checkIfElementIsSelected(WebElement element){
        return element.isSelected();
    }

    public static WebElement pollAndWait(WebDriver driver,String elementXpath ) {
        List<WebElement> ls=pollAndWait(driver,elementXpath,0,1,TIMEOUT_COUNT);
        return ls.get(0);
    }


    public static WebElement pollAndWait(WebDriver driver,String elementXpath,int interval, int maxTime) {
        List<WebElement> ls=pollAndWait(driver,elementXpath,0,interval,maxTime);
        if(Objects.nonNull(ls)) {
            return ls.get(0);
        }
        else
            return null;
    }

    public static List<WebElement> pollAndWait(WebDriver driver, String elementXpath, int flag, int interval, int maxTime){
        int i=0;
        while(i<maxTime)
        {
            List<WebElement> ls=new ArrayList<WebElement>();
            WebElement element;
            try {
                Thread.sleep(new Long(interval*1000));
                if(flag==1){
                    ls=driver.findElements(By.xpath(elementXpath));
                    if (ls.isEmpty()){
                        continue;
                    }
                    return ls;
                }
                else{
                    element=driver.findElement(By.xpath(elementXpath));
                    if(element != null){
                        ls.add(element);
                        return(ls);
                    }
                }
            }
            catch(NoSuchElementException e)
            {
                exception=e;

            }
            catch (Exception e){
                Logger.log(e.getMessage());
                Logger.log("String: -> "+e.toString());
            }
            i++;
        }
        Logger.log(exception.getMessage());
        return null;
    }

    public static boolean pollAndWait(WebDriver driver, WebElement element, int interval, int maxtime ) {
        int i=0;
        while(i<maxtime){
            try{
                Thread.sleep(new Long(interval*1000));
                boolean flag= element.isDisplayed();
                if(flag==true)
                    return true;
            }
            catch (Exception e){
                exception=e;
            }
            i++;
        }
        return false;
    }


    public static List<String> getElementTextList(List<WebElement> listElement){

        List<String> elementList = new ArrayList<String>();

        Logger.log("Total search results : "+elementList.size());
        System.out.println("Total search results : "+elementList.size());

        for (WebElement element : listElement) {
            elementList.add(element.getText());
        }
        return elementList;

    }

    public static List<String> getElementAttribute(List<WebElement> listElement, String attribute){

        List<String> elementList = new ArrayList<String>();

        Logger.log("Total search results : "+elementList.size());
        System.out.println("Total search results : "+elementList.size());

        for (WebElement element : listElement) {
            elementList.add(element.getAttribute(attribute));
        }
        return elementList;

    }

    public static WebElement findElement(WebDriver driver, String xpath){

        return driver.findElement(By.xpath(xpath));

    }

    public static List<WebElement> findElements(WebDriver driver, String xpath){

        return driver.findElements(By.xpath(xpath));

    }

}
