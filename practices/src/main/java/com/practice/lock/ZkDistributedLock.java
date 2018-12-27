package com.practice.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private final static String PATH_SLASH = "/";
    private final static Logger log = LoggerFactory.getLogger(ZkDistributedLock.class);
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
        if(exceptionList.size() > 0){
            throw new LockException(exceptionList.get(0));
        }
        try {
            if(this.tryLock()){
                System.out.println("Thread " + Thread.currentThread().getId() + " get lock true");
                return;
            }else{
                waitForLock(waitNode,sessionTimeout);
            }
        } catch (InterruptedException e) {
            exceptionList.add(e);
        } catch (KeeperException e) {
            exceptionList.add(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }

    @Override
    public boolean tryLock() {
        String splitStr = "_lock_";
        if(lockName.contains(splitStr)){
            throw new LockException("lockName can not contain _lock_");
        }
        try {
            myZnode = zk.create(root + PATH_SLASH + lockName + splitStr,new byte[0],ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(myZnode + "is created");
            List<String> subNodes = zk.getChildren(root,false);
            List<String> lockObjNodes = new ArrayList<>();
            for(String node:subNodes){
                String _node = node.split(splitStr)[0];
                if(_node.equals(lockName)){
                    lockObjNodes.add(node);
                }
            }

            Collections.sort(lockObjNodes);
            System.out.println(myZnode + "==" + lockObjNodes.get(0));
            if(myZnode.equals(root+PATH_SLASH+lockObjNodes.get(0))){
                return true;
            }
            String subMyZnode = myZnode.substring(myZnode.lastIndexOf(PATH_SLASH)+1);
            waitNode = lockObjNodes.get(Collections.binarySearch(lockObjNodes,subMyZnode)-1);

        } catch (KeeperException e) {
            exceptionList.add(e);
        } catch (InterruptedException e) {
            exceptionList.add(e);
        }

        return false;
    }


    private boolean waitForLock(String lower,long waitTime) throws InterruptedException,KeeperException{
        Stat stat = zk.exists(root+PATH_SLASH+lower,true);
        if(stat != null){
            log.info("Thread {} waiting for {} ",Thread.currentThread().getName(),root+PATH_SLASH+lower);;
            latch = new CountDownLatch(1);
            latch.await(waitTime,TimeUnit.SECONDS);
            latch = null;
        }

        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if(this.tryLock()){
            return true;
        }
        try {
            return waitForLock(waitNode,time);
        } catch (KeeperException e) {
            exceptionList.add(e);
        }

        return false;
    }

    @Override
    public void unlock() {
        try {
            zk.delete(myZnode,-1);
            myZnode = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
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

    public class LockException extends RuntimeException{
        private static final long serialVersionUID = 1L;

        public LockException(String message) {
            super(message);
        }

        public LockException(Exception e) {
            super(e);
        }
    }
}
