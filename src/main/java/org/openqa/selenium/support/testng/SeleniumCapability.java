package org.openqa.selenium.support.testng;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.ConstructorOrMethod;

public class SeleniumCapability implements IInvokedMethodListener2 {

  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    // TODO Auto-generated method stub

  }

  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    // TODO Auto-generated method stub

  }

  public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
      WebTest web = getWebTestAnnotation(method);
      if (web !=null){
        WebTestSession.setContext(getParams(context));
        WebTestSession.start(web.protocol());
      }

  }

  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    WebTest web = getWebTestAnnotation(method);
    if (web !=null){
      WebTestSession.stop();
    }
  }
  
  private Map<String, String> getParams(ITestContext context){
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

}
