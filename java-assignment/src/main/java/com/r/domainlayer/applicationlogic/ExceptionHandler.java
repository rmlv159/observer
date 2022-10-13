package com.nordea.emarkets.fx.monitoring.domainlayer.applicationlogic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionHandler implements com.lmax.disruptor.ExceptionHandler<DisruptorEvent> {

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void handleEventException(Throwable ex, long sequence, DisruptorEvent event) {
        logger.error(ex);
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        logger.error(ex);
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        logger.error(ex);
    }
}
