import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.testng.WebTest;
import org.openqa.selenium.support.testng.WebTestSession;
import org.testng.annotations.Test;


public class Demo {

  @Test
  @WebTest(protocol = SeleniumProtocol.WebDriver)
  public void test() {
    WebDriver driver = WebTestSession.webdriver();
    driver.get("http://www.ebay.co.uk");
    WebTestSession.selenium().open("http://google.com");
  }
  
  
  @Test
  @WebTest(protocol = SeleniumProtocol.Selenium)
  public void test2() {
    WebTestSession.selenium().open("http://ebay.com");
  }

}
