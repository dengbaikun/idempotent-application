package com.dk.zookeeper.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author DK
 * @version 1.0
 * @implSpec 描述
 * @since 2022-05-11 23:54
 */
public abstract class AbstractLock implements Lock{
    //zk服务器地址
    public static final String ZK_SERVER_ADDR="192.168.117.3:2181";

    //zk超时时间：连接超时、session有效时间
    public static final int CONNECTION_TIME_OUT=30000;
    public static final int SESSION_TIME_OUT=30000;

    //创建zk客户端
    protected ZkClient zkClient = new ZkClient(ZK_SERVER_ADDR,SESSION_TIME_OUT,CONNECTION_TIME_OUT);


    public void getLock(){

        String threadName = Thread.currentThread().getName();

        if (tryLock()){
            System.out.println(threadName+"    :获取锁成功");
        }else {
            System.out.println(threadName+"     :获取锁失败，进入等待");
            waitLock();
            getLock();
        }
    }
}
