import static org.openqa.selenium.support.testng.TestSession.selenium;
import static org.openqa.selenium.support.testng.TestSession.webdriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.testng.WebTest;
import org.testng.annotations.Test;


public class Demo1 {


  @Test(groups = "run")
  @WebTest(protocol = SeleniumProtocol.WebDriver)
  public void test() {
    // WebDriver protocol allow the webdriver API
    webdriver().get("http://www.ebay.co.uk");
    // but also the selenium legacy API
    selenium().open("http://google.com");
  }


 

  // works with all the normal testng features, dependencies, invoc count etc.
  @Test(invocationCount = 5, threadPoolSize = 5)
  @WebTest
  public void testParallel() throws InterruptedException {
    webdriver().get("http://www.ebay.co.uk");
    Thread.sleep(10000);
    webdriver().getCurrentUrl();
  }
  
  
  

}
