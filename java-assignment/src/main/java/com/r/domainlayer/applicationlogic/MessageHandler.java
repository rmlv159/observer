package com.nordea.emarkets.fx.monitoring.domainlayer.applicationlogic;

import com.lmax.disruptor.RingBuffer;
import com.nordea.emarkets.fx.monitoring.domainlayer.businesslogic.HashCalculator;
import com.nordea.emarkets.fx.monitoring.adaptorlayer.Sink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageHandler implements com.lmax.disruptor.EventHandler<DisruptorEvent> {

//    private final static Logger logger = LogManager.getLogger();
    private final Sink sink;
    private final SaltHandler saltHandler;
//    private final RingBuffer<DisruptorEvent> ringBuffer;
    private final int workerOrdinal;
    private final int nWorkers;
    private final HashCalculator hashCalculator;

    public MessageHandler(Sink sink, SaltHandler saltHandler, RingBuffer<DisruptorEvent> ringBuffer, int workerOrdinal, int nWorkers) {
        this.sink = sink;
        this.saltHandler = saltHandler;
//        this.ringBuffer = ringBuffer;
        this.workerOrdinal = workerOrdinal;
        this.nWorkers = nWorkers;
        hashCalculator = new HashCalculator();
    }

    @Override
    public void onEvent(DisruptorEvent event, long sequence, boolean endOfBatch) {
        //raw version prior 3.0
//            long nextFromSequencer = ringBuffer.next();
//            if (ringBuffer.get(nextFromSequencer).getEventType() == DisruptorEvent.Type.MESSAGE) {

        if (event.getEventType() == DisruptorEvent.Type.MESSAGE && (sequence % nWorkers) == workerOrdinal) {
            byte[] latestSalt = saltHandler.getLatestSalt();
            byte[] hash = hashCalculator.run(event.getMessage(), latestSalt);
            sink.publishHash(event.getId(), event.getMessage(), latestSalt, hash);

            //raw version prior 3.0
//                byte[] hash = hashCalculator.run(ringBuffer.get(nextFromSequencer).getMessage(), latestSalt);
//                sink.publishHash(event.getId(), ringBuffer.get(nextFromSequencer).getMessage(), latestSalt, hash);
//                ringBuffer.publish(nextFromSequencer++);
        }
    }
}
