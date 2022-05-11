package com.dk.zookeeper.lock;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:54
 */
public interface Lock {
    /**
     * 获取锁
     */
     boolean tryLock();
    /**
     * 获取锁
     */
    public void getLock();
    /**
     * 等待获取锁
     */
     void waitLock();

    /**
     * 释放锁
     */
     void releaseLock();
}
