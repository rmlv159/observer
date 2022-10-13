package com.nordea.emarkets.fx.monitoring;

import com.nordea.emarkets.fx.monitoring.domainlayer.businesslogic.Feeder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  private final static Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    Feeder feeder = new Feeder();
    try {
      Application application = new Application(feeder);
      application.start();

      feeder.bombardInParellel();
    } catch (Throwable t) {
      logger.error(t);
    }
    try {
      Thread.sleep(35_000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.exit(0);
  }
}
