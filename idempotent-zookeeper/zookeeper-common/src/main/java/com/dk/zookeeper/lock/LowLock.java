package com.dk.zookeeper.lock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author DK
 * @version 1.0
 * @implSpec zk低效锁实现
 * @since 2022-05-11 23:57
 */
public class LowLock extends AbstractLock{

    public static final String LOCK_NODE_NAME="/lock_node";

    private CountDownLatch countDownLatch;

    @Override
    public boolean tryLock() {
        if (zkClient == null){
            return false;
        }

        try {
            zkClient.createEphemeral(LOCK_NODE_NAME);
            return true;
        } catch (RuntimeException e) {
            //获取锁失败
            return false;
        }

    }

    @Override
    public void waitLock() {

        IZkDataListener zkDataListener = new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            //节点被删除时触发
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                if (countDownLatch !=null){
                    countDownLatch.countDown();
                }
            }
        };

        //注册监听器
        zkClient.subscribeDataChanges(LOCK_NODE_NAME,zkDataListener);

        //如果锁节点存在，阻塞当前线程
        if (zkClient.exists(LOCK_NODE_NAME)){
            countDownLatch = new CountDownLatch(1);

            try {
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName()+":     等待获取锁");
            } catch (InterruptedException e) {
            }
        }

        //如果锁节点不存在
        //删除监听
        zkClient.unsubscribeDataChanges(LOCK_NODE_NAME,zkDataListener);

    }

    @Override
    public void releaseLock() {

        zkClient.delete(LOCK_NODE_NAME);
        zkClient.close();
        System.out.println(Thread.currentThread().getName()+":      释放锁");
    }
}

