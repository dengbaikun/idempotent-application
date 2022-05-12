package com.dk.redis.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-12 23:38
 */
public class SingleRedisLock {

    JedisPool jedisPool = new JedisPool("192.168.117.3",6379);

    //锁过期时间
    private long lockLeaseTime = 30000;

    //获取锁的超时时间
    private long timeOut=30000;

    /**
     * 加锁
     * @param lockKey
     * @param requestId
     * @return
     */
    public boolean tryLock(String lockKey,String requestId){

        String threadName = Thread.currentThread().getName();

        Jedis jedis = this.jedisPool.getResource();
        jedis.auth("123456");
        //获取当前时间
        long start = System.currentTimeMillis();

        try{

            //获取锁
            for (;;){

                String lockResult = jedis.set(lockKey, requestId, SetParams.setParams().nx().px(lockLeaseTime));

                if ("OK".equals(lockResult)){
                    System.out.println(threadName+":    获取锁成功");
                    return true;
                }

                //获取锁失败，在一定的时间内不断尝试的获取锁，当超时了之后，则退出自旋
                System.out.println(threadName+":    获取锁失败，进入自旋等待");
                long l = System.currentTimeMillis() - start;
                if (l>=timeOut){
                    System.out.println(threadName+":   在规定时间内，没有获取到锁，退出自旋");
                    return false;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            jedis.close();
        }
    }

    /**
     * 解锁
     * @param lockKey
     * @param requestId
     * @return
     */
    public boolean releaseLock(String lockKey,String requestId){

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+":  释放锁");

        Jedis jedis = this.jedisPool.getResource();
        jedis.auth("123456");
        String lua =
                "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                        "   return redis.call('del',KEYS[1]) " +
                        "else" +
                        "   return 0 " +
                        "end";

        try {
            Object result = jedis.eval(lua, Collections.singletonList(lockKey), Collections.singletonList(requestId));

            if ("1".equals(result.toString())){
                return true;
            }

            return false;
        }finally {
            jedis.close();
        }
    }
}

