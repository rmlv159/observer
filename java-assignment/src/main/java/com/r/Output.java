package com.nordea.emarkets.fx.monitoring;

import com.nordea.emarkets.fx.monitoring.adaptorlayer.Sink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class Output implements Sink {

    private final Queue<Event> q = new LinkedList<>(); //TODO RK optimize
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void publishHash(long id, byte[] message, byte[] salt, byte[] hash) {
//        Event event = new Event(hash);
//        q.add(event);
    }

    public byte[] poll() {
        Event event = q.poll();
        return event == null ? new byte[0] : event.getHash();
    }
}
