package com.dk.zookeeper;

import com.dk.zookeeper.lock.HighLock;
import com.dk.zookeeper.lock.Lock;
import com.dk.zookeeper.lock.LowLock;

import java.util.concurrent.CountDownLatch;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:56
 */
public class LockTest {
    private static int count = 0;
    public  static void increment() { ++count; }
    public static int getCount() { return count; }
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        //模拟多个10个客户端
        for (int i=0;i<10;i++) {
            Thread thread = new Thread(new LockRunnable( countDownLatch));
            thread.start();
        }
        countDownLatch.await();
        System.out.println(getCount());
    }

    private static class LockRunnable implements Runnable {
        private CountDownLatch countDownLatch;
        public LockRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            //Lock lock = new LowLock();
            Lock lock = new HighLock("/test");
            try {
                lock.getLock();
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }finally {
                lock.releaseLock();
            }

            countDownLatch.countDown();
            //abstractLock.releaseLock();
        }
    }
}
