import static org.openqa.selenium.support.testng.TestSession.selenium;

import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.selenium.support.testng.WebTest;
import org.testng.annotations.Test;


public class DemoSeleniumLegacy {

  @Test
  @WebTest(protocol = SeleniumProtocol.Selenium)
  public void test2() {
    // selenium legacy API
    selenium().open("http://ebay.com");
  }

}
