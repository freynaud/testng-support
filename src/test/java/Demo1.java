import static org.openqa.selenium.support.testng.TestSession.selenium;
import static org.openqa.selenium.support.testng.TestSession.webdriver;

import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.selenium.support.testng.WebTest;
import org.testng.annotations.Test;


public class Demo1 {


  @Test
  @WebTest(protocol = SeleniumProtocol.WebDriver)
  public void test() {
    // WebDriver protocol allow the webdriver API
    webdriver().get("http://www.ebay.co.uk");
    // but also the selenium legacy API
    selenium().open("http://google.com");
  }


 

  // works with all the normal testng features, dependencies, invoc count etc.
  @Test(invocationCount = 2, threadPoolSize = 2)
  @WebTest
  public void testParallel() {
    webdriver().get("http://www.ebay.co.uk");
  }

}
