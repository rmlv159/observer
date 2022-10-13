package com.nordea.emarkets.fx.monitoring.domainlayer.applicationlogic;

public class SaltHandler implements com.lmax.disruptor.EventHandler<DisruptorEvent> {

    private volatile byte[] salt;

    @Override
    public void onEvent(DisruptorEvent event, long sequence, boolean endOfBatch) throws Exception {
        if (event.getEventType() == DisruptorEvent.Type.SALT) {
            salt = event.getSalt();
//            event.reset();
        }
    }

    public byte[] getLatestSalt() {
        return salt;
    }
}
