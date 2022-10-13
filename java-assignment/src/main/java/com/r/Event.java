package com.nordea.emarkets.fx.monitoring;

public class Event {
    private final byte[] hash;

    public Event(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getHash() {
        return hash;
    }
}
