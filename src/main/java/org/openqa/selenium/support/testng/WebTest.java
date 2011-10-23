package org.openqa.selenium.support.testng;


import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.openqa.grid.common.SeleniumProtocol;

@Retention(RUNTIME)
@Target({CONSTRUCTOR, METHOD, TYPE})
public @interface WebTest {

  SeleniumProtocol protocol() default SeleniumProtocol.WebDriver;

}
