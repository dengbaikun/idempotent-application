package com.dk.redis;

import com.dk.redis.lock.SingleRedisLock;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-12 23:37
 */
public class LockTest {

    public static void main(String[] args) {

        //模拟多个5个客户端
        for (int i=0;i<5;i++) {
            Thread thread = new Thread(new LockRunnable());
            thread.start();
        }
    }

    private static class LockRunnable implements Runnable {
        @Override
        public void run() {

            SingleRedisLock singleRedisLock = new SingleRedisLock();

            String requestId = UUID.randomUUID().toString();

            boolean lockResult = singleRedisLock.tryLock("lock", requestId);

            if (lockResult){

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            singleRedisLock.releaseLock("lock",requestId);
        }
    }
}
