package com.nordea.emarkets.fx.monitoring.domainlayer.applicationlogic;

public class DisruptorEvent {
    public enum Type {
        MESSAGE,
        SALT
    }

    private Type eventType = null;
    private byte[] salt;
    private long id;
    private byte[] message;

    public void onSalt(byte[] salt) {
        eventType = Type.SALT;
        this.salt = salt;
        this.id = 0L;
        this.message = null;
    }

    public void onMessage(long id, byte[] message) {
        eventType = Type.MESSAGE;
        this.salt = null;
        this.id = id;
        this.message = message;
    }

    public void reset() {
        eventType = null;
        this.salt = null;
        this.id = 0;
        this.message = null;
    }

    public Type getEventType() {
        return eventType;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }
}
