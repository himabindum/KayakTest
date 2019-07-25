import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.my.test.kayak.pages.HomePage;
import com.my.test.kayak.testbase.TestBase;
import com.my.test.kayak.utils.CommonMethods;
import com.my.test.kayak.utils.DataProviderUtils;
import com.my.test.kayak.utils.ReadPropertyFile;


/*
 * Test NG class for HomePage
 */

public class HomePageTests {

    WebDriver driver;
    String URL;
    HomePage homePage;

    @BeforeMethod
    public void preConfig() {
	    try {
	        driver = TestBase.launchBrowser();
	        URL = ReadPropertyFile.getProperty("url");
	        driver.get(URL);
	        homePage= new HomePage();
	    }catch (Exception ex) {
	    	ex.printStackTrace();
	    }
    }

    @Test(description = "Search flight for a city",dataProviderClass = DataProviderUtils.class,
            dataProvider = "multipleExecutionData")
    public void TC_001(Map<String,String> map){
        homePage.enterDetails(map);
        Assert.assertTrue(map.get("Origin Code").equalsIgnoreCase(homePage.getOrigin(map.get("Search Result Number"))),
                "Expected Origin: "+map.get("Origin Code") + " But Found: " + homePage.getOrigin(map.get("Search Result Number")) );
        Assert.assertTrue(map.get("Destination Code").equalsIgnoreCase(homePage.getDestination(map.get("Search Result Number"))),
                "Expected Destination: "+map.get("Destination Code") + " But Found: " + homePage.getDestination(map.get("Search Result Number")));
        homePage.selectFLight(map.get("Search Result Number"));
        CommonMethods.sleep(6000);
    }

    @AfterMethod
    public void postConfig(){
        driver.quit();
    }
}
