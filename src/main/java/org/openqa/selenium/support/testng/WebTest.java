package org.openqa.selenium.support.testng;


import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.openqa.grid.common.SeleniumProtocol;

/**
 * marks a method as a selenium web test.
 * 
 * @author freynaud
 * 
 */
@Retention(RUNTIME)
@Target({CONSTRUCTOR, METHOD, TYPE})
public @interface WebTest {

  /**
   * 
   * @return the type of protocol the browser will use to simulate user interaction.
   */
  SeleniumProtocol protocol() default SeleniumProtocol.WebDriver;

}
