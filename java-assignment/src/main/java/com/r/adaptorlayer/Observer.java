package com.nordea.emarkets.fx.monitoring.adaptorlayer;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public interface Observer {
    void onSalt(byte[] salt);
    void onMessage(long id, byte[] message);
}
