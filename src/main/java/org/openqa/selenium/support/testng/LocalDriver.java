package org.openqa.selenium.support.testng;

import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.Selenium;

public class LocalDriver {

  private final static String START_URL = "http://ebay.co.uk";
  private final static String HUB_URL = "http://localhost:4444/wd/hub";
  
  private SeleniumProtocol protocol;
  private WebDriver driver;
  private Selenium selenium;
  
  
  
}
