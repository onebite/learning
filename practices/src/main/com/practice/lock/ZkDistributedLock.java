package com.practice.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author lxl
 * @Date 2018/5/8 20:30
 */
public class ZkDistributedLock implements Lock,Watcher {
    private ZooKeeper zk;
    //根
    private String root = "/locks";
    //竞争资源标志
    private String lockName;
    //等待前一个锁
    private String waitNode;
    //当前锁
    private String myZnode;
    //计数器
    private CountDownLatch latch;
    private int sessionTimeout = 30000;
    private List<Exception> exceptionList = new ArrayList<>();

    public ZkDistributedLock(String config,String lockName) {
        this.lockName = lockName;
        try {
            zk = new ZooKeeper(config,sessionTimeout,this);
            Stat stat = zk.exists(root,false);
            if(stat == null){
                zk.create(root,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            exceptionList.add(e);
        } catch (InterruptedException e) {
            exceptionList.add(e);
        } catch (KeeperException e) {
            exceptionList.add(e);
        }
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(this.latch != null){
            this.latch.countDown();
        }
    }
}
