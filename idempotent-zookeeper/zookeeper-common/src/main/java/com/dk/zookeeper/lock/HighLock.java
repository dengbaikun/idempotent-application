package com.dk.zookeeper.lock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author DK
 * @version 1.0
 * @implSpec 高效低效锁实现
 * @since 2022-05-11 23:57
 */
public class HighLock extends AbstractLock{

    private static String PARENT_NODE_PATH="";

    public HighLock(String parentNodePath){
        PARENT_NODE_PATH = parentNodePath;
    }

    //当前节点路径
    private String currentNodePath;

    //前一个节点路径
    private String preNodePath;

    private CountDownLatch countDownLatch;

    @Override
    public boolean tryLock() {

        //判断父节点是否存在
        try {
            if (!zkClient.exists(PARENT_NODE_PATH)){
                zkClient.createPersistent(PARENT_NODE_PATH);
            }
        } catch (RuntimeException e) {
        }


        //创建临时有序节点
        if (currentNodePath == null || "".equals(currentNodePath)){
            //根节点下创建第一个临时有序的子节点
            currentNodePath = zkClient.createEphemeralSequential(PARENT_NODE_PATH+"/","lock");
        }

        //不是第一个子节点，获取父节点下的所有子节点
        List<String> childrenNodeList = zkClient.getChildren(PARENT_NODE_PATH);

        //子节点排序
        Collections.sort(childrenNodeList);

        //判断是否加锁成功
        if (currentNodePath.equals(PARENT_NODE_PATH+"/"+childrenNodeList.get(0))){
            return true;
        }else {
            //获取前面的节点名称，并赋值
            int length = PARENT_NODE_PATH.length();
            int currentNodeNumber = Collections.binarySearch(childrenNodeList, currentNodePath.substring(length + 1));
            preNodePath = PARENT_NODE_PATH+"/"+childrenNodeList.get(currentNodeNumber-1);
        }

        return false;
    }

    @Override
    public void waitLock() {

        IZkDataListener zkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

                if (countDownLatch != null){
                    countDownLatch.countDown();
                }
            }
        };

        //监听前一个节点的改变
        zkClient.subscribeDataChanges(preNodePath,zkDataListener);

        if (zkClient.exists(preNodePath)){
            countDownLatch = new CountDownLatch(1);

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {

            }
        }

        zkClient.unsubscribeDataChanges(preNodePath,zkDataListener);
    }

    @Override
    public void releaseLock() {
        zkClient.delete(currentNodePath);
        zkClient.close();
    }
}

