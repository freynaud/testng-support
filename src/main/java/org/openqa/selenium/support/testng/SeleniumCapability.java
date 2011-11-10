package org.openqa.selenium.support.testng;

import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.ConstructorOrMethod;


/**
 * TestNG listener that automatically starts/closes browsers around methods.
 * 
 * @author freynaud
 * 
 */
public class SeleniumCapability implements IInvokedMethodListener2 {



  /**
   * hooks before the method start, and make sure a browser has been started using the info
   * specified in the testng.xml file.
   */
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    WebTest web = getWebTestAnnotation(method);
    if (web != null) {
      TestSession.setContext(getParams(context));
      String browserName = (String) context.getCurrentXmlTest().getParameter("browserName");
      String version = (String) context.getCurrentXmlTest().getParameter("version");
      String p = (String) context.getCurrentXmlTest().getParameter("platform");
      Platform platform = Platform.ANY;
      try {
        if (p != null) {
          platform = Platform.valueOf(p);
        }
      } catch (Exception e) {
        TestSession.put(TestSession.ERROR, new RuntimeException(p
            + "isn't a platform selenium recognize :  " + e.getMessage()));
        return;
      }

      TestSession.start(web.protocol(), browserName, version, platform);
    }

  }

  /**
   * closes the browser after the method.
   */
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    WebTest web = getWebTestAnnotation(method);
    if (web != null) {
      TestSession.stop();
    }
  }

  private Map<String, String> getParams(ITestContext context) {
    Map<String, String> params = context.getCurrentXmlTest().getParameters();
    return params;
  }

  private WebTest getWebTestAnnotation(IInvokedMethod method) {
    if (!method.isTestMethod()) {
      return null;
    }
    ConstructorOrMethod com = method.getTestMethod().getConstructorOrMethod();
    if (com.getMethod() == null) {
      return null;
    }
    Method m = com.getMethod();
    WebTest annotation = m.getAnnotation(WebTest.class);
    return annotation;
  }


  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    // TODO Auto-generated method stub

  }

  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    // TODO Auto-generated method stub

  }

}
