package com.dk.order;

import com.dk.order.util.RedissonLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-13 0:10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedissonLockTest {

    @Autowired
    private RedissonLock redissonLock;

    @Test
    public void easyLock(){
        //模拟多个10个客户端
        for (int i=0;i<10;i++) {
            Thread thread = new Thread(new LockRunnable());
            thread.start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class LockRunnable implements Runnable {
        @Override
        public void run() {
            redissonLock.addLock("demo");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redissonLock.releaseLock("demo");
        }
    }
}

