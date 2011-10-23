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


  @Override
  public void onStart(ISuite suite) {
    try {
      System.setProperty("webdriver.chrome.driver", "c:\\tmp\\chromedriver.exe");
      GridHubConfiguration config = new GridHubConfiguration();
      hub = new Hub(config);
      hub.start();

      // TODO set Hub
      RegistrationRequest req = new RegistrationRequest();
      req.setRole(GridRole.NODE);
      req.loadFromJSON("localGridNode.json");
      
      remote = new SelfRegisteringRemote(req);
      remote.startRemoteServer();
      remote.startRegistrationProcess();

      // wait for the node to be registered.
      Thread.sleep(1000);

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Override
  public void onFinish(ISuite suite) {
    try {
      remote.stopRemoteServer();
      hub.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
