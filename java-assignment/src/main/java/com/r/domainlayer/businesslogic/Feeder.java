package com.nordea.emarkets.fx.monitoring.domainlayer.businesslogic;

import com.nordea.emarkets.fx.monitoring.adaptorlayer.Observer;
import com.nordea.emarkets.fx.monitoring.adaptorlayer.Source;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Feeder implements Source {

  private static final Logger logger = LogManager.getLogger();
  private Observer observer;

  @Override
  public void subscribe(Observer observer) {
    this.observer = observer;
  }

  @SuppressWarnings("InfiniteLoopStatement")
  public void everySecMsg() {
    while (true) {
      try {
        submit();
        Thread.sleep(1_000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void bombardInParellel() {
    int nFeeders = 100;
    ExecutorService feederService = Executors.newFixedThreadPool(nFeeders);
    for (int i = 0; i < nFeeders; i++) feederService.submit(this::submit);
  }

  @SuppressWarnings("InfiniteLoopStatement")
  private void submit() {
    observer.onSalt(new byte[]{(byte) 0xa1, (byte) 0x98, (byte) 0x98, (byte) 0x98, (byte) 0x98});
    byte msgPart1 = (byte) 0x01;
    byte msgPart2 = (byte) 0xa1;
    byte msgPart3 = (byte) 0xb2;
    byte msgPart4 = (byte) 0x33;
    byte msgPart5 = (byte) 0x99;
    long id = 0L;
//    System.gc();
    while (true) {
      byte[] message = {msgPart1++, msgPart2++, msgPart3++, msgPart4++, msgPart5++};
      observer.onMessage(id++, message);
//      logger.info("Here I am");
    }
  }
}
