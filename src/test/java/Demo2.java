import java.text.ParseException;

import org.openqa.selenium.support.testng.TestSession;
import org.openqa.selenium.support.testng.WebTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.SearchResultPage;


public class Demo2 {

  
  @Test
  @WebTest
  public void testSearch() throws ParseException{
    TestSession.webdriver().get("http://www.ebay.co.uk/");
    
    HomePage homePage = new HomePage();
    SearchResultPage result = homePage.search("ipod");
    
   int totalIpod = result.getResultCount();
   
   Assert.assertTrue(totalIpod>1000,"there should be more than "+totalIpod+" on the site.");
    
  }
}
