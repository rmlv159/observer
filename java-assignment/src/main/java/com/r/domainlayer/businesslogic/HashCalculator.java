package com.nordea.emarkets.fx.monitoring.domainlayer.businesslogic;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;

public class HashCalculator {

    private final static Logger logger = LogManager.getLogger();
    private static final int N = 20_000;
    private static final ThreadLocal<StopWatch> stopWatch = ThreadLocal.withInitial(StopWatch::new);
    private static final ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);

    public byte[] run(byte[] message, byte[] salt) {
        StopWatch stopWatch = HashCalculator.stopWatch.get();
        stopWatch.reset();
        stopWatch.start();

        MessageDigest digest;
        byte[] hash = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            hash = concatBytes(message, salt);
            for (int i = 0; i < N; i++){
                hash = digest.digest(hash);
                if (i != N - 1) hash = concatBytes(hash, salt);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        stopWatch.stop();
//        System.out.println(stopWatch.getTime());
        logger.info("|Duration|{}", stopWatch.getTime());
        Integer integer = counter.get();
        logger.info("|Count|{}", integer++);
        counter.set(integer);
        return hash;
    }

    private static byte[] concatBytes(byte[] src1, byte[] src2) {
        byte[] dest = new byte[src1.length + src2.length];
        System.arraycopy(src1, 0, dest, 0, src1.length);
        System.arraycopy(src2, 0, dest, src1.length, src2.length);
        return dest;
    }
}
