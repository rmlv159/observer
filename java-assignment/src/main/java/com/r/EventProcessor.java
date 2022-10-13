package com.nordea.emarkets.fx.monitoring;

import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.Disruptor;
import com.nordea.emarkets.fx.monitoring.adaptorlayer.Observer;
import com.nordea.emarkets.fx.monitoring.adaptorlayer.Sink;
import com.nordea.emarkets.fx.monitoring.domainlayer.applicationlogic.*;
import com.nordea.emarkets.fx.monitoring.domainlayer.businesslogic.HashCalculator;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class EventProcessor implements Observer {

    private static final int BUFFER_SIZE = 1024;
    private final Disruptor<DisruptorEvent> inputDisruptor;
    private final static int N_WORKERS = 5;

    public EventProcessor(Sink sink) {
        ThreadFactory inputThreadFactory = Executors.defaultThreadFactory();
        inputDisruptor = new Disruptor<>(DisruptorEvent::new, BUFFER_SIZE, inputThreadFactory);

        ExceptionHandler exceptionHandler = new ExceptionHandler();
        MessageWorkHandler[] messageWorkers = new MessageWorkHandler[N_WORKERS];
        SaltHandler saltHandler = new SaltHandler();
        for (int i = 0; i < N_WORKERS; i++) {
            messageWorkers[i] = new MessageWorkHandler(sink, saltHandler, new HashCalculator());
        }
        WorkerPool<DisruptorEvent> workerPool = new WorkerPool<>(DisruptorEvent::new, exceptionHandler, messageWorkers);
        workerPool.start(Executors.newFixedThreadPool(N_WORKERS));

        inputDisruptor.handleEventsWith(saltHandler)
            .handleEventsWithWorkerPool(messageWorkers);
        inputDisruptor.setDefaultExceptionHandler(exceptionHandler);
        inputDisruptor.start();
    }

    @Override
    public void onSalt(byte[] salt) {
        //TODO RK optimize
        inputDisruptor.publishEvent((event, sequence) -> event.onSalt(salt));
    }

    @Override
    public void onMessage(long id, byte[] message) {
        //TODO RK optimize
        inputDisruptor.publishEvent((event, sequence) -> event.onMessage(id, message));
    }
}
