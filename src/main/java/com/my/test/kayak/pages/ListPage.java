package com.my.test.kayak.pages;

import com.my.test.kayak.testbase.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ListPage extends TestBase {

    @FindBy(xpath = "//div [@class='nearby-checkbox']")
    private List<WebElement> flightD;

    private String suggestedPlace="//ul[@class='flight-smarty']//div[text()='LABEL']";

    private String parentTab;

    public ListPage() {
        PageFactory.initElements(driver, this);
    }
}
