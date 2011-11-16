package org.openqa.selenium.support.testng;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class WebTestSession {

  private final static String WEBDRIVER = "webdriver";
  private final static String SELENIUM = "selenium";
  private final static String START_URL = "http://ebay.co.uk";
  private final static String PROTOCOL = "protocol";

  private static ThreadLocal<Map<String, Object>> sessions =
      new InheritableThreadLocal<Map<String, Object>>();


  public static WebDriver webdriver() {
    if (getProtocol() == SeleniumProtocol.Selenium) {
      throw new RuntimeException();
    } else {
      WebDriver driver = (WebDriver) get(WEBDRIVER);
      return driver;
    }
  }

  public static Selenium selenium() {
    return (Selenium) get(SELENIUM);
  }

  public static void start(SeleniumProtocol protocol) {
    put(PROTOCOL, protocol);

    URL url = getGridUrl();
    if (getProtocol() == SeleniumProtocol.Selenium) {
      Selenium selenium = new DefaultSelenium(url.getHost(), url.getPort(), "*firefox", START_URL);
      selenium.start();
      put(SELENIUM, selenium);
    } else {
      WebDriver driver = new RemoteWebDriver(url, getCapability());
      put(WEBDRIVER, driver);
      WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, START_URL);
      put(SELENIUM, selenium);
    }

  }


  private static DesiredCapabilities getCapability(){
    DesiredCapabilities cap = new DesiredCapabilities();
    cap.setBrowserName((String)get(CapabilityType.BROWSER_NAME));
    cap.setVersion((String)get(CapabilityType.VERSION));
    String p = (String)get(CapabilityType.PLATFORM);
    if (p!=null){
      Platform pl = Platform.valueOf(p);
      cap.setPlatform(pl);
    }
    return cap;
  }
  
  
  public static void stop() {
    if (getProtocol() == SeleniumProtocol.Selenium) {
      selenium().close();
    } else {
      webdriver().quit();
    }
  }

  private static URL getGridUrl() {
    try {
      return new URL("http://localhost:4444/wd/hub");
    } catch (MalformedURLException e) {
      return null;
    }
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

  private static void put(String key, Object value) {
    getSession().put(key, value);
  }
  private static void putAll(Map<String, ? extends Object> params) {
    getSession().putAll(params);
  }

  public static void setContext(Map<String, String> params) {
    putAll(params);
  }
}
