import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.my.test.kayak.pages.HomePage;
import com.my.test.kayak.pages.ListPage;
import com.my.test.kayak.testbase.TestBase;
import com.my.test.kayak.utils.DataProviderUtils;
import com.my.test.kayak.utils.ReadPropertyFile;



public class HomePageTests {

    WebDriver driver;
    String URL;
    HomePage homePage;
    ListPage listPage;

    @BeforeMethod
    public void preConfig() {

        driver = TestBase.launchBrowser();
        URL = ReadPropertyFile.getProperty("url");
        driver.get(URL);
        homePage= new HomePage();
        listPage= new ListPage();
    }

    @Test(description = "Search flight for a city",dataProviderClass = DataProviderUtils.class,
            dataProvider = "multipleExecutionData")
    public void TC_001(Map<String,String> map){
        homePage.enterDetails(map);
        Assert.assertTrue(map.get("Origin Code").equalsIgnoreCase(listPage.getOrigin(map.get("Search Result Number"))),
                "Expected: "+map.get("Origin Code") + " But Found: " + listPage.getOrigin(map.get("Search Result Number")) );
        Assert.assertTrue(map.get("Destination Code").equalsIgnoreCase(listPage.getDestination(map.get("Search Result Number"))),
                "Expected: "+map.get("Destination Code") + " But Found: " + listPage.getDestination(map.get("Search Result Number")));
        listPage.selectFLight(map.get("Search Result Number"));
    }

    @AfterMethod
    public void postConfig(){
        driver.quit();
    }
}
