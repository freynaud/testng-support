package org.openqa.selenium.support.testng;

import java.net.MalformedURLException;

import org.openqa.grid.common.GridRole;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.GridHubConfiguration;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.web.Hub;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class LocalGrid implements ISuiteListener {

  private static Hub hub;
  private static SelfRegisteringRemote remote;

  /**
   * starts a local grid with 1 node locally.
   */
  @Override
  public void onStart(ISuite suite) {
    try {

      if (isLocalGrid(suite)) {
        setGridURL(suite);
        setupChromeDriver(suite);

        GridHubConfiguration config = new GridHubConfiguration();
        hub = new Hub(config);
        hub.start();

        RegistrationRequest req = new RegistrationRequest();
        req.setRole(GridRole.NODE);
        req.loadFromJSON("localGridNode.json");


        remote = new SelfRegisteringRemote(req);
        remote.startRemoteServer();
        remote.startRegistrationProcess();


        // TODO freynaud API call for node check
        Thread.sleep(1000);
      }

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * release resources.
   */
  @Override
  public void onFinish(ISuite suite) {
    try {
      if (isLocalGrid(suite)) {
        remote.stopRemoteServer();
        hub.stop();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * if a "url" parameter is specified in the testng.xml file, it will use that. and expect a grid
   * to be running there. Otherwise it will start and use a local instance.
   * 
   * @param suite
   */
  private boolean isLocalGrid(ISuite suite) {
    String url = (String) suite.getParameter("hub");
    if (url != null) {
      return false;
    } else {
      return true;
    }
  }

  private void setGridURL(ISuite suite) {
    String url = (String) suite.getParameter("hub");
    if (url != null) {
      TestSession.setHubUrl(url);
    }
  }

  /**
   * add the chrome driver server to the path if it was specified in the xml file.
   * 
   * @param suite
   */
  private void setupChromeDriver(ISuite suite) {
    String chromeDriver = (String) suite.getParameter("webdriver.chrome.driver");
    if (chromeDriver != null) {
      System.setProperty("webdriver.chrome.driver", chromeDriver);
    }
  }



}
