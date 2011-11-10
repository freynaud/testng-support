package org.openqa.selenium.support.testng;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * TestNG garantees that each test method will run in its own thread, so we can use threadLocal to
 * store the objects that are in the test scope. Typically the webdriver/selenium driver instance.
 * For most use cases, 1 driver per test method is enough.
 * 
 * @author freynaud
 * 
 */
public class TestSession {

  public final static String WEBDRIVER = "webdriver";
  public final static String SELENIUM = "selenium";
  public final static String START_URL = "http://ebay.co.uk";
  public final static String PROTOCOL = "protocol";
  public final static String ERROR = "error";

  private static String HUB_URL = "http://localhost:4444/wd/hub";

  private static ThreadLocal<Map<String, Object>> sessions =
      new InheritableThreadLocal<Map<String, Object>>();

  /**
   * 
   * @return the webdriver object bound to the current test.
   */
  public static WebDriver webdriver() {
    if (getProtocol() == SeleniumProtocol.Selenium) {
      throw new RuntimeException("You cannot use the webdriver API on a selenium legacy server.");
    } else {
      WebDriver driver = (WebDriver) get(WEBDRIVER);
      if (driver == null) {
        printListenerHelp();
      }
      return driver;
    }
  }


  /**
   * If the test was marked as a WebDriver test @WebTest(protocol = SeleniumProtocol.WebDriver), a
   * WebDriverBackedSelenium will be returned.
   * 
   * @return the selenium object bound to the current test.
   */
  public static Selenium selenium() {
    Selenium sel = (Selenium) get(SELENIUM);
    if (sel == null) {
      printListenerHelp();
    }
    return sel;
  }

  /**
   * start the browser for the current test.
   * 
   * @param protocol Selenium legacy or webdriver
   * @param browserName firefox,chrome ... for webdriver, *firefox, *googlechrome for selenium
   *        legacy.
   * @param version only relevant for webdriver.
   * @param p only relevant for webdriver.
   */
  public static void start(SeleniumProtocol protocol, String browserName, String version, Platform p) {
    put(PROTOCOL, protocol);

    URL url = getGridUrl();
    if (getProtocol() == SeleniumProtocol.Selenium) {
      Selenium selenium = new DefaultSelenium(url.getHost(), url.getPort(), browserName, START_URL);
      selenium.start();
      put(SELENIUM, selenium);
    } else {
      DesiredCapabilities cap = new DesiredCapabilities();
      cap.setBrowserName(browserName);
      if (version != null) {
        cap.setVersion(version);
      }
      if (p != null) {
        cap.setPlatform(p);
      }
      try {
        WebDriver driver = new RemoteWebDriver(url, cap);
        put(WEBDRIVER, driver);
        WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, START_URL);
        put(SELENIUM, selenium);
      } catch (WebDriverException e) {
        put(ERROR, e);
      }

    }

  }


  /**
   * terminates the current test, closing the browser.
   */
  public static void stop() {
    if (getProtocol() == SeleniumProtocol.Selenium) {
      selenium().close();
    } else {
      webdriver().quit();
    }
  }

  /**
   * local for debugging or remote.
   * @return the url the grid listen on.
   */
  private static URL getGridUrl() {
    try {
      return new URL(HUB_URL);
    } catch (MalformedURLException e) {
      throw new InvalidParameterException(HUB_URL + " isn't a valid url for the hub..");
    }
  }

  /**
   * set the URL that will be used during the test. Default to a local grid.
   * @param url
   */
  public static void setHubUrl(String url) {
    HUB_URL = url;
  }

  private static SeleniumProtocol getProtocol() {
    return (SeleniumProtocol) get(PROTOCOL);
  }


  private static Map<String, Object> getSession() {
    Map<String, Object> res = sessions.get();
    if (res == null) {
      res = new HashMap<String, Object>();
      sessions.set(res);
    }
    return res;
  }

  private static Object get(String key) {
    return getSession().get(key);
  }

  public static void put(String key, Object value) {
    getSession().put(key, value);
  }

  private static void putAll(Map<String, ? extends Object> params) {
    getSession().putAll(params);
  }

  public static void setContext(Map<String, String> params) {
    putAll(params);
  }

  private static void printListenerHelp() {
    if (get(ERROR) != null) {
      throw (WebDriverException) get(ERROR);
    } else {
      String msg =
          "There is no selenium/webdriver currently bound to this thread.\n"
              + "Have you attached the SeleniumCapability listener and annotated your"
              + " test with @WebTest ?";
      System.err.println(msg);
      throw new RuntimeException(
          "Selenium Listener Exception. Most likely resulting from a config error.");
    }



  }
}
