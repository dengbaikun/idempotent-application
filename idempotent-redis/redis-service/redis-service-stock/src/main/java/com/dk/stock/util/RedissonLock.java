package com.dk.stock.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-13 0:10
 */
@Component
public class RedissonLock {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加锁
     */
    public boolean addLock(String lockKey){

        try{
            if (redissonClient == null){
                return false;
            }

            RLock lock = redissonClient.getLock(lockKey);

            //设置锁的过期时间
            //lock.lock(1, TimeUnit.SECONDS);
            lock.tryLock(100, TimeUnit.SECONDS);

            System.out.println(Thread.currentThread().getName()+":     获取到锁");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 释放锁
     */
    public boolean releaseLock(String lockKey){
        try{
            if (redissonClient == null){
                return false;
            }

            RLock lock = redissonClient.getLock(lockKey);

            //释放锁
            lock.unlock();

            System.out.println(Thread.currentThread().getName()+":     释放锁");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

