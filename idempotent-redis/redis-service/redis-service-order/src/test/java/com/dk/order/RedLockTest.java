package com.dk.order;

import com.dk.order.config.RedissonRedLockConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.RedissonRedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-13 0:11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedLockTest {

    @Autowired
    private RedissonRedLockConfig redissonRedLockConfig;

    @Test
    public void easyLock(){
        //模拟多个10个客户端
        for (int i=0;i<10;i++) {
            Thread thread = new Thread(new RedLockTest.RedLockRunnable());
            thread.start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class RedLockRunnable implements Runnable {
        @Override
        public void run() {
            RedissonRedLock redissonRedLock = redissonRedLockConfig.initRedissonRedLock("demo");

            try {
                boolean lockResult = redissonRedLock.tryLock(100, 10, TimeUnit.SECONDS);

                if (lockResult){
                    System.out.println("获取锁成功");
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                redissonRedLock.unlock();
                System.out.println("释放锁");
            }
        }
    }
}
