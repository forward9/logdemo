package logtest;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    private static Logger log = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) throws InterruptedException {
        int messageSize = 1000000;
        int threadSize = 50;
        final int everySize = messageSize / threadSize;

        final CountDownLatch cdl = new CountDownLatch(threadSize);
        long startTime = System.currentTimeMillis();
        for (int ts = 0; ts < threadSize; ts++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int es = 0; es < everySize; es++) {
                        log.info("======info");
                    }
                    cdl.countDown();
                }
            }).start();
        }

        cdl.await();
        long endTime = System.currentTimeMillis();
        System.out.println("log4j1:messageSize = " + messageSize + ",threadSize = " + threadSize
                + ",costTime = " + (endTime - startTime) + "ms");
    }
}