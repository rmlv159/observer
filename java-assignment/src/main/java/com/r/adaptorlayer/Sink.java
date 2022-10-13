package com.nordea.emarkets.fx.monitoring.adaptorlayer;

public interface Sink {
    void publishHash(long id, byte[] message, byte[] salt, byte[] hash);
}
