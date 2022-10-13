package com.nordea.emarkets.fx.monitoring.domainlayer.applicationlogic;

import com.nordea.emarkets.fx.monitoring.adaptorlayer.Sink;
import com.nordea.emarkets.fx.monitoring.domainlayer.businesslogic.HashCalculator;

public class MessageWorkHandler implements com.lmax.disruptor.WorkHandler<DisruptorEvent> {
    private final Sink sink;
    private final SaltHandler saltHandler;
    private final HashCalculator hashCalculator;

    public MessageWorkHandler(Sink sink, SaltHandler saltHandler, HashCalculator hashCalculator) {
        this.sink = sink;
        this.saltHandler = saltHandler;
        this.hashCalculator = hashCalculator;
    }

    @Override
    public void onEvent(DisruptorEvent event) throws Exception {
        if (event.getEventType() == DisruptorEvent.Type.MESSAGE) {
            byte[] latestSalt = saltHandler.getLatestSalt();
            byte[] hash = hashCalculator.run(event.getMessage(), latestSalt);
            sink.publishHash(event.getId(), event.getMessage(), latestSalt, hash);
        }
    }
}
